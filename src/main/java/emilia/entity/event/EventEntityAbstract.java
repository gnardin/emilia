package emilia.entity.event;

public abstract class EventEntityAbstract {
	
	// Agent source identification of the reported action
	protected Integer		sourceId;
	
	// Agent target identification of the reported action
	protected Integer		targetId;
	
	// Agent identification informing the event
	protected Integer		informerId;
	
	// Type of the content
	protected EventType	type;
	
	
	/**
	 * Create an event entity
	 * 
	 * @param sourceId
	 *          Agent source of the reported action
	 * @param targetId
	 *          Agent target of the reported action
	 * @param informerId
	 *          Agent informing the event
	 * @param type
	 *          Type of the Event
	 */
	public EventEntityAbstract(Integer sourceId, Integer targetId,
			Integer informerId, EventType type) {
		this.sourceId = sourceId;
		this.targetId = targetId;
		this.informerId = informerId;
		this.type = type;
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
	public EventType getType() {
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
		
		str = this.sourceId + " " + this.targetId + " " + this.informerId + " "
				+ this.type;
		
		return str;
	}
}