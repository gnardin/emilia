package emilia.entity.event.content;

import emilia.entity.event.EventContentAbstract;

public class NormInvocationComplianceEvent extends EventContentAbstract {

	/**
	 * Constructor
	 * 
	 * @param normId
	 *            Norm identification
	 * @return none
	 */
	public NormInvocationComplianceEvent(int normId) {
		super(Type.NORM_INVOCATION_COMPLIANCE, normId);
	}
}