package emilia.entity.event.content;

import emilia.entity.event.EventContentAbstract;

public class PunishmentEvent extends EventContentAbstract {

	/**
	 * Constructor
	 * 
	 * @param normId
	 *            Norm identification
	 * @return none
	 */
	public PunishmentEvent(int normId) {
		super(Type.PUNISHMENT, normId);
	}
}