package emilia.entity.event;

public abstract class NormativeEventEntityAbstract {
	
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
	 */
	public NormativeEventEntityAbstract(Long time, Integer sourceId, Integer targetId,
			Integer informerId, NormativeEventType type) {
		this.time = time;
		this.sourceId = sourceId;
		this.targetId = targetId;
		this.informerId = informerId;
		this.type = type;
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
		
		return str;
	}
}