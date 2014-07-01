package emilia.modules.recognition;

import emilia.entity.event.NormativeEventEntityAbstract;

public interface EventListener {
	
	/**
	 * Submit an event to the listener
	 * 
	 * @param event
	 *          Event entity content
	 * @return none
	 */
	public void receive(NormativeEventEntityAbstract event);
}