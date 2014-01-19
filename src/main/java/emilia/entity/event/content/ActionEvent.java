package emilia.entity.event.content;

import emilia.entity.event.EventContentAbstract;

public class ActionEvent extends EventContentAbstract {

	/**
	 * Constructor
	 * 
	 * @param normId
	 *            Norm identification
	 * @return none
	 */
	public ActionEvent(int normId) {
		super(Type.ACTION, normId);
	}
}