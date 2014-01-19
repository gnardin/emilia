package emilia.entity.event.content;

import emilia.entity.event.EventContentAbstract;

public class NormInvocationViolationEvent extends EventContentAbstract {

	/**
	 * Constructor
	 * 
	 * @param normId
	 *            Norm identification
	 * @return none
	 */
	public NormInvocationViolationEvent(int normId) {
		super(Type.NORM_INVOCATION_COMPLIANCE, normId);
	}
}