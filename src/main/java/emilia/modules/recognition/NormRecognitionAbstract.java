package emilia.modules.recognition;

import emilia.board.NormativeBoardInterface;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.NormativeEventType;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.EventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class NormRecognitionAbstract {
	
	private static final Logger																						logger	= LoggerFactory
																																										.getLogger(NormRecognitionAbstract.class);
	
	// Agent identification
	protected int																													agentId;
	
	// Normative Board
	protected NormativeBoardInterface																			normativeBoard;
	
	// Callbacks
	protected Map<Boolean, Map<NormativeEventType, List<EventListener>>>	callbacks;
	
	
	/**
	 * Create a Norm Recognition
	 * 
	 * @param agentId
	 *          Agent identification
	 * @param normativeBoard
	 *          Normative Board
	 * @return none
	 */
	public NormRecognitionAbstract(Integer agentId,
			NormativeBoardInterface normativeBoard) {
		this.agentId = agentId;
		this.normativeBoard = normativeBoard;
		this.callbacks = new HashMap<Boolean, Map<NormativeEventType, List<EventListener>>>();
	}
	
	
	/**
	 * Register a callback method
	 * 
	 * @param matches
	 *          List of possible matches
	 * @param types
	 *          List of event type
	 * @param eventListener
	 *          Method to be called
	 * @return none
	 */
	public void registerCallback(List<Boolean> matches,
			List<NormativeEventType> types, EventListener eventListener) {
		
		for(Boolean match : matches) {
			
			Map<NormativeEventType, List<EventListener>> eventListeners;
			if(this.callbacks.containsKey(match)) {
				eventListeners = this.callbacks.get(match);
			} else {
				eventListeners = new HashMap<NormativeEventType, List<EventListener>>();
			}
			
			for(NormativeEventType type : types) {
				List<EventListener> listener;
				if(eventListeners.containsKey(type)) {
					listener = eventListeners.get(type);
				} else {
					listener = new ArrayList<EventListener>();
				}
				
				listener.add(eventListener);
				eventListeners.put(type, listener);
				
				logger.debug("CALLBACK REGISTERED [" + type + "]");
			}
			
			this.callbacks.put(match, eventListeners);
		}
	}
	
	
	/**
	 * Unregister a callback method
	 * 
	 * @param matches
	 *          List of possible matches
	 * @param types
	 *          List of event type
	 * @param eventListener
	 *          Method to be called
	 * @return none
	 */
	public void unregisterCallback(List<Boolean> matches,
			List<NormativeEventType> types, EventListener eventListener) {
		
		for(Boolean match : matches) {
			
			Map<NormativeEventType, List<EventListener>> eventListeners = this.callbacks
					.get(match);
			
			for(NormativeEventType type : types) {
				if(eventListeners.containsKey(type)) {
					List<EventListener> listener = eventListeners.get(type);
					
					if(listener.contains(eventListener)) {
						listener.remove(eventListener);
						eventListeners.put(type, listener);
						this.callbacks.put(match, eventListeners);
						
						logger.debug("CALLBACK UNREGISTERED [" + type + "]");
					}
				}
			}
		}
	}
	
	
	/**
	 * Send event to all callbacks
	 * 
	 * @param event
	 *          Event content
	 * @param norms
	 *          Norms matched
	 * @return none
	 */
	protected void processEvent(NormativeEventEntityAbstract event,
			Map<NormEntityAbstract, List<SanctionEntityAbstract>> normSanctions) {
		boolean found = false;
		
		boolean matches = false;
		if((normSanctions != null) && (normSanctions.size() > 0)) {
			matches = true;
		}
		
		if(this.callbacks.containsKey(matches)) {
			
			Map<NormativeEventType, List<EventListener>> eventListeners = this.callbacks
					.get(matches);
			
			if(eventListeners.containsKey(event.getType())) {
				List<EventListener> listener = eventListeners.get(event.getType());
				
				for(EventListener eventListener : listener) {
					eventListener.receive(event, normSanctions);
					found = true;
				}
			}
		}
		
		if(!found) {
			logger.debug("EVENT NOT PROCESSED [" + event.toString() + "]");
		}
	}
	
	
	/**
	 * Event matcher
	 * 
	 * @param event
	 *          Event entity content
	 * @return none
	 */
	public abstract void matchEvent(NormativeEventEntityAbstract event);
	
	
	/**
	 * Process the norm recognition
	 * 
	 * @param event
	 *          Event entity content
	 * @return none
	 */
	public abstract void recognizeNorm(NormativeEventEntityAbstract event);
	
	
	/**
	 * Process the sanction recognition
	 * 
	 * @param event
	 *          Event entity content
	 * @return none
	 */
	public abstract void recognizeSanction(NormativeEventEntityAbstract event);
}