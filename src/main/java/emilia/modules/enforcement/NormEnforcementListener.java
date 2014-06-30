package emilia.modules.enforcement;

import emilia.entity.event.NormativeEventType;

public interface NormEnforcementListener {
	
	/**
	 * Submit a norm to the listener
	 * 
	 * @param type
	 *          Event type
	 * @return none
	 */
	public void receive(NormativeEventType type);
}