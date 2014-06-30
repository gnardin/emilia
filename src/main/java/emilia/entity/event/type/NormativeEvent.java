package emilia.entity.event.type;

import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.NormativeEventType;

public class NormativeEvent extends NormativeEventEntityAbstract {
	
	// Norm identification
	protected Integer	normId;
	
	
	/**
	 * Create a Normative event entity
	 * 
	 * @param time
	 *          Event time
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
	public NormativeEvent(Long time, Integer sourceId, Integer targetId,
			Integer informerId, NormativeEventType type, Integer normId) {
		super(time, sourceId, targetId, informerId, type);
		
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