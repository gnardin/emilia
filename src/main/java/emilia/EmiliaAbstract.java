package emilia;

import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.enforcement.NormEnforcementListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class EmiliaAbstract {
	
	private static final Logger				logger	= LoggerFactory
																								.getLogger(EmiliaAbstract.class);
	
	// Norm Enforcement callback
	protected NormEnforcementListener	callback;
	
	
	/**
	 * Instantiate normative architectire
	 * 
	 * @param none
	 * @return none
	 */
	public EmiliaAbstract() {
		this.callback = null;
	}
	
	
	/**
	 * Input event message
	 * 
	 * @param event
	 *          Event message
	 * @return none
	 */
	public abstract void input(Object event);
	
	
	/**
	 * Get the normative drive
	 * 
	 * @param normId
	 *          Norm identification
	 * @return Normative drive
	 */
	public abstract Double getNormativeDrive(Integer normId);
	
	
	/**
	 * Output sanction action
	 * 
	 * @param sanction
	 *          Sanction object
	 * @return none
	 */
	public void sendSanction(SanctionEntityAbstract sanction) {
		if ((this.callback != null) && (sanction != null)) {
			this.callback.receive(sanction);
		}
	}
	
	
	/**
	 * Register to receive sanction information
	 * 
	 * @param NormEnforcementListener
	 *          Method to be called
	 * @return none
	 */
	public void registerNormEnforcement(NormEnforcementListener listener) {
		this.callback = listener;
		
		logger.debug("CALLBACK REGISTERED [ Norm Enforcement ]");
	}
	
	
	/**
	 * Unregister to receive sanction information
	 * 
	 * @param none
	 * @return none
	 */
	public void unregisterNormEnforcement() {
		this.callback = null;
		
		logger.debug("CALLBACK UNREGISTERED [ Norm Enforcement ]");
	}
}