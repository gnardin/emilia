package emilia.entity.event;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class NormativeEventEntityAbstract {
	
	@SuppressWarnings("unused")
	private static final Logger		logger	= LoggerFactory
																						.getLogger(NormativeEventEntityAbstract.class);
	
	// Time in milliseconds
	protected Long								time;
	
	// Agent source identification of the reported action
	protected Integer							sourceId;
	
	// Agent target identification of the reported action
	protected Integer							targetId;
	
	// Agent identification informing the event
	protected Integer							informerId;
	
	// Type of the content
	protected NormativeEventType	type;
	
	// Context attributes
	protected Map<String, Object>	contextAttrs;
	
	
	/**
	 * Create an event entity
	 * 
	 * @param time
	 *          Event time
	 * @param sourceId
	 *          Agent source of the reported action
	 * @param targetId
	 *          Agent target of the reported action
	 * @param informerId
	 *          Agent informing the event
	 * @param type
	 *          Type of the Event
	 * @return none
	 */
	public NormativeEventEntityAbstract(Long time, Integer sourceId,
			Integer targetId, Integer informerId, NormativeEventType type) {
		this.time = time;
		this.sourceId = sourceId;
		this.targetId = targetId;
		this.informerId = informerId;
		this.type = type;
		this.contextAttrs = new HashMap<String, Object>();
	}
	
	
	/**
	 * Create an event entity
	 * 
	 * @param time
	 *          Event time
	 * @param sourceId
	 *          Agent source of the reported action
	 * @param targetId
	 *          Agent target of the reported action
	 * @param informerId
	 *          Agent informing the event
	 * @param type
	 *          Type of the Event
	 * @param contextAttrs
	 *          Context attributes and values
	 * @return none
	 */
	public NormativeEventEntityAbstract(Long time, Integer sourceId,
			Integer targetId, Integer informerId, NormativeEventType type,
			Map<String, Object> contextAttrs) {
		this.time = time;
		this.sourceId = sourceId;
		this.targetId = targetId;
		this.informerId = informerId;
		this.type = type;
		this.contextAttrs = contextAttrs;
	}
	
	
	/**
	 * Get the event time
	 * 
	 * @param none
	 * @return Event time
	 */
	public Long getTime() {
		return this.time;
	}
	
	
	/**
	 * Get the agent source of the action
	 * 
	 * @param none
	 * @return Agent source identification of the reported action
	 */
	public Integer getSource() {
		return this.sourceId;
	}
	
	
	/**
	 * Get the agent target of the action
	 * 
	 * @param none
	 * @return Agent target identification of the reported action
	 */
	public Integer getTarget() {
		return this.targetId;
	}
	
	
	/**
	 * Get agent informing the event
	 * 
	 * @param none
	 * @return Agent identification informing the event
	 */
	public Integer getInformer() {
		return this.informerId;
	}
	
	
	/**
	 * Get the type of the event entity
	 * 
	 * @param none
	 * @return Event entity type
	 */
	public NormativeEventType getType() {
		return this.type;
	}
	
	
	/**
	 * Get context attributes
	 * 
	 * @param none
	 * @return Context attributes and values
	 */
	public Map<String, Object> getContextAttrs() {
		return this.contextAttrs;
	}
	
	
	/**
	 * Check whether an attribute is part of the context attributes list
	 * 
	 * @param attr
	 *          Attribute name
	 * @return True if the attribute exists, False otherwise
	 */
	public Boolean hasContextAttr(String attr) {
		return this.contextAttrs.containsKey(attr);
	}
	
	
	/**
	 * Get an attribute value
	 * 
	 * @param attr
	 *          Attribute name
	 * @return Attribute value if it exists, NULL otherwise
	 */
	public Object getContextAttribute(String attr) {
		Object result = null;
		
		if(this.contextAttrs.containsKey(attr)) {
			result = this.contextAttrs.get(attr);
		}
		
		return result;
	}
	
	
	/**
	 * Set an attribute value
	 * 
	 * @param attr
	 *          Attribute name
	 * @param value
	 *          Attribute value
	 * @return none
	 */
	public void setContextAttribute(String attr, Object value) {
		this.contextAttrs.put(attr, value);
	}
	
	
	/**
	 * String of the event entity
	 * 
	 * @param none
	 * @return Event entity string
	 */
	@Override
	public String toString() {
		String str;
		
		str = this.time + " " + this.sourceId + " " + this.targetId + " "
				+ this.informerId + " " + this.type;
		for(String attr : this.contextAttrs.keySet()) {
			str += " [" + attr + "|" + this.contextAttrs.get(attr).toString() + "]";
		}
		
		return str;
	}
}