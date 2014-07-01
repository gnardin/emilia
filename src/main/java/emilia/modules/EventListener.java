package emilia.modules;

import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.sanction.SanctionEntityAbstract;
import java.util.List;
import java.util.Map;

public interface EventListener {
	
	/**
	 * Submit an event to the listener
	 * 
	 * @param event
	 *          Event entity content
	 * @return none
	 */
	public void receive(NormativeEventEntityAbstract event,
			Map<NormEntityAbstract, List<SanctionEntityAbstract>> normSanctions);
}