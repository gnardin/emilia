package emilia.modules.compliance;

import emilia.board.normative.NormativeBoardInterface;

public abstract class NormComplianceAbstract {

	protected NormativeBoardInterface normativeBoard;

	/**
	 * Constructor
	 * 
	 * @param normativeBoard
	 *            Normative board
	 * @return none
	 */
	public NormComplianceAbstract(NormativeBoardInterface normativeBoard) {
		this.normativeBoard = normativeBoard;
	}

	/**
	 * Get normative drive
	 * 
	 * @param normId
	 *            Norm identification
	 * @return Normative drive
	 */
	public abstract double getNormativeDrive(int normId);
}