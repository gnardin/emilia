package emilia.modules.enforcement;

import emilia.entity.sanction.SanctionEntityAbstract;

public interface NormEnforcementListener {
	
	/**
	 * Submit a sanction to the listener
	 * 
	 * @param Sanction
	 *          Sanction
	 * @return none
	 */
	public void receive(SanctionEntityAbstract sanction);
}