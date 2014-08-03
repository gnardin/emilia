package examples.pgg.modules.enforcement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import emilia.modules.enforcement.DeviationAbstract;

public class ViolationDeviation extends DeviationAbstract {
	
	@SuppressWarnings("unused")
	private static final Logger	logger	= LoggerFactory
																					.getLogger(ViolationDeviation.class);
	
	
	/**
	 * Create a violation deviation
	 * 
	 * @param none
	 * @return none
	 */
	public ViolationDeviation() {
		super(Type.VIOLATION);
	}
}