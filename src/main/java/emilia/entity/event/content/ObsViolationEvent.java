package emilia.entity.event.content;

import emilia.entity.event.EventContentAbstract;

public class ObsViolationEvent extends EventContentAbstract {

	/**
	 * Constructor
	 * 
	 * @param normId
	 *            Norm identification
	 * @return none
	 */
	public ObsViolationEvent(int normId) {
		super(Type.OBSERVED_VIOLATION, normId);
	}
}