package examples.pgg.modules.enforcement;

import emilia.modules.enforcement.DeviationAbstract;

public class ComplianceDeviation extends DeviationAbstract {
	
	/**
	 * Create a compliance deviation
	 * 
	 * @param none
	 * @return none
	 */
	public ComplianceDeviation() {
		super(Type.COMPLIANCE);
	}
}