package emilia.modules.compliance;

import emilia.board.normative.NormativeBoardInterface;

public class NormComplianceController extends NormComplianceAbstract {

	public NormComplianceController(NormativeBoardInterface normativeBoard) {
		super(normativeBoard);
	}

	/**
	 * Get normative drive
	 * 
	 * @param normId
	 *            Norm identification
	 * @return Normative drive
	 */
	@Override
	public double getNormativeDrive(int normId) {
		return this.normativeBoard.getSalience(normId);
	}
}