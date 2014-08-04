package examples.ijcai11.modules.enforcement;

import emilia.modules.enforcement.DeviationAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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