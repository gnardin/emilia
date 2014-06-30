package emilia.board;

import emilia.entity.norm.NormEntityAbstract;

public interface NormativeBoardListener {
	
	/**
	 * Submit a norm to the listener
	 * 
	 * @param type
	 *          Normative type
	 * @param oldNorm
	 *          Old norm entity
	 * @param newNorm
	 *          New norm entity
	 * @return none
	 */
	public void receive(NormativeBoardEventType type, NormEntityAbstract oldNorm,
			NormEntityAbstract newNorm);
}