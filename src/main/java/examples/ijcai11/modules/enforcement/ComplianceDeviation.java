package examples.ijcai11.modules.enforcement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import emilia.modules.enforcement.DeviationAbstract;

public class ComplianceDeviation extends DeviationAbstract {
	
	@SuppressWarnings("unused")
	private static final Logger	logger	= LoggerFactory
																					.getLogger(ComplianceDeviation.class);
	
	
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