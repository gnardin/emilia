package emilia;

import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.enforcement.NormEnforcementListener;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class EmiliaAbstract {
	
	private static final Logger				logger	= LoggerFactory
																								.getLogger(EmiliaAbstract.class);
	
	// Agent identification
	protected Integer									agentId;
	
	// Norm Enforcement callback
	protected NormEnforcementListener	callback;
	
	
	/**
	 * Instantiate normative architectire
	 * 
	 * @param none
	 * @return none
	 */
	public EmiliaAbstract(Integer agentId) {
		this.agentId = agentId;
		this.callback = null;
	}
	
	
	/**
	 * Get agent identification
	 * 
	 * @param none
	 * @return none
	 */
	public Integer getId() {
		return this.agentId;
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
	 * Update norm salience
	 * 
	 * @param none
	 * @return none
	 */
	public abstract void update();
	
	
	/**
	 * Get the norm salience
	 * 
	 * @param normId
	 *          Norm identification
	 * @return Norm salience
	 */
	public abstract Double getNormSalience(Integer normId);
	
	
	/**
	 * Add norms and associated sanctions
	 * 
	 * @param normsSanctions
	 *          Norms and associated sanctions
	 * @return none
	 */
	public abstract void addNormsSanctions(
			Map<NormEntityAbstract, List<SanctionEntityAbstract>> normsSanctions);
	
	
	/**
	 * Get norms and associated sanctions
	 * 
	 * @param none
	 * @return Norms and associated sanctions
	 */
	public abstract Map<NormEntityAbstract, List<SanctionEntityAbstract>> getNormsSanctions();
	
	
	/**
	 * Update the norms and associated sanctions
	 * 
	 * @param normsSanctions
	 *          Norms and associated sanctions
	 * @return none
	 */
	public abstract void updateNormsSanctions(
			Map<NormEntityAbstract, List<SanctionEntityAbstract>> normsSanctions);
	
	
	/**
	 * Get the norm
	 * 
	 * @param normId
	 *          Norm identification
	 * @return Norm
	 */
	public abstract NormEntityAbstract getNorm(Integer normId);
	
	
	/**
	 * Output sanction action
	 * 
	 * @param sanction
	 *          Sanction object
	 * @return none
	 */
	public void sendSanction(NormativeEventEntityAbstract event,
			NormEntityAbstract norm, SanctionEntityAbstract sanction) {
		if((this.callback != null) && (sanction != null)) {
			this.callback.receive(event, norm, sanction);
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