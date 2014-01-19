package emilia.entity.event.content;

import emilia.entity.event.EventContentAbstract;

public class ObsComplianceEvent extends EventContentAbstract {

	/**
	 * Constructor
	 * 
	 * @param normId
	 *            Norm identification
	 * @return none
	 */
	public ObsComplianceEvent(int normId) {
		super(Type.OBSERVED_COMPLIANCE, normId);
	}
}