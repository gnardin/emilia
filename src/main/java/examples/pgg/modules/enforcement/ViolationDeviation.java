package examples.pgg.modules.enforcement;

import emilia.modules.enforcement.DeviationAbstract;

public class ViolationDeviation extends DeviationAbstract {
	
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