package emilia.modules.classifier;

import emilia.entity.event.NormativeEventEntityAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class EventClassifierAbstract {
	
	@SuppressWarnings("unused")
	private static final Logger	logger	= LoggerFactory
																					.getLogger(EventClassifierAbstract.class);
	
	// Agent identification
	protected Integer						agentId;
	
	
	/**
	 * Create an Event Classifier
	 * 
	 * @param agentId
	 *          Agent identification
	 * @return none
	 */
	public EventClassifierAbstract(Integer agentId) {
		this.agentId = agentId;
	}
	
	
	/**
	 * Classify events
	 * 
	 * @param event
	 *          Event to classify
	 * @return Classified event
	 */
	public abstract NormativeEventEntityAbstract classify(Object event);
}