package emilia.entity.event.type;

import emilia.entity.action.ActionInterface;
import emilia.entity.event.EventEntityAbstract;
import emilia.entity.event.EventType;

public class ActionEvent extends EventEntityAbstract {
	
	// Action performed in the environment
	protected ActionInterface	action;
	
	
	/**
	 * Create an Action event entity
	 * 
	 * @param sourceId
	 *          Agent source of the reported action
	 * @param targetId
	 *          Agent target of the reported action
	 * @param informerId
	 *          Agent informing the event
	 * @param action
	 *          Performed action
	 * @return none
	 */
	public ActionEvent(Integer sourceId, Integer targetId, Integer informerId,
			ActionInterface action) {
		super(sourceId, targetId, informerId, EventType.ACTION);
		
		this.action = action;
	}
	
	
	/**
	 * Obtain the action performed
	 * 
	 * @param none
	 * @return Performed action
	 */
	public ActionInterface getAction() {
		return this.action;
	}
}