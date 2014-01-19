package emilia.entity.event.content;

import emilia.entity.event.EventContentAbstract;

public class SanctionEvent extends EventContentAbstract {

	/**
	 * Constructor
	 * 
	 * @param normId
	 *            Norm identification
	 * @return none
	 */
	public SanctionEvent(int normId) {
		super(Type.SANCTION, normId);
	}
}