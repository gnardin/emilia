package emilia.entity.event.type;

import emilia.entity.event.EventEntityAbstract;
import emilia.entity.event.EventType;

public class NormativeEvent extends EventEntityAbstract {
	
	// Norm identification
	protected Integer	normId;
	
	
	/**
	 * Create a Normative event entity
	 * 
	 * @param sourceId
	 *          Agent source identification of the reported action
	 * @param targetId
	 *          Agent target identification of the reported action
	 * @param informerId
	 *          Agent identification informing the event
	 * @param type
	 *          Type of the Event
	 * @param normId
	 *          Norm identification
	 * @return none
	 */
	public NormativeEvent(Integer sourceId, Integer targetId, Integer informerId,
			EventType type, Integer normId) {
		super(sourceId, targetId, informerId, type);
		
		this.normId = normId;
	}
	
	
	/**
	 * Get the norm identification
	 * 
	 * @param none
	 * @return Norm identification
	 */
	public Integer getNormId() {
		return this.normId;
	}
}