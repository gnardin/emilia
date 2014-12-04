package emilia.entity.event.type;

import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.NormativeEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NormativeEvent extends NormativeEventEntityAbstract {
	
	@SuppressWarnings("unused")
	private static final Logger	logger	= LoggerFactory
																					.getLogger(NormativeEvent.class);
	
	// Norm identification
	protected int								normId;
	
	
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
	public NormativeEvent(double time, int sourceId, int targetId,
			int informerId, NormativeEventType type, int normId) {
		super(time, sourceId, targetId, informerId, type);
		
		this.normId = normId;
	}
	
	
	/**
	 * Get the norm identification
	 * 
	 * @param none
	 * @return Norm identification
	 */
	public int getNormId() {
		return this.normId;
	}
}