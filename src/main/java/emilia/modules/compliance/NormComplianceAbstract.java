package emilia.modules.compliance;

import emilia.board.NormativeBoardInterface;

public abstract class NormComplianceAbstract {
	
	// Normative Board
	protected NormativeBoardInterface	normativeBoard;
	
	
	/**
	 * Create a norm compliance
	 * 
	 * @param normativeBoard
	 *          Normative board
	 * @return none
	 */
	public NormComplianceAbstract(NormativeBoardInterface normativeBoard) {
		this.normativeBoard = normativeBoard;
	}
	
	
	/**
	 * Get normative drive
	 * 
	 * @param normId
	 *          Norm identification
	 * @return Normative drive
	 */
	public abstract Double getNormativeDrive(Integer normId);
}