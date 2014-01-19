package emilia.entity.event.content;

import emilia.entity.event.EventContentAbstract;

public class ViolationEvent extends EventContentAbstract {

	/**
	 * Constructor
	 * 
	 * @param normId
	 *            Norm identification
	 * @return none
	 */
	public ViolationEvent(int normId) {
		super(Type.VIOLATION, normId);
	}
}