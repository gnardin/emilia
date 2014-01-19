package emilia.entity.event.content;

import emilia.entity.event.EventContentAbstract;

public class ComplianceEvent extends EventContentAbstract {

	/**
	 * Constructor
	 * 
	 * @param normId
	 *            Norm identification
	 * @return none
	 */
	public ComplianceEvent(int normId) {
		super(Type.COMPLIANCE, normId);
	}
}