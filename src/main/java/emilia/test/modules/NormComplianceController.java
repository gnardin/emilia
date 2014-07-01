package emilia.test.modules;

import emilia.board.NormativeBoardInterface;
import emilia.modules.compliance.NormComplianceAbstract;

public class NormComplianceController extends NormComplianceAbstract {
	
	public NormComplianceController(Integer agentId,
			NormativeBoardInterface normativeBoard) {
		super(agentId, normativeBoard);
	}
	
	
	/**
	 * Get normative drive
	 * 
	 * @param normId
	 *          Norm identification
	 * @return Normative drive
	 */
	@Override
	public Double getNormativeDrive(Integer normId) {
		if (this.normativeBoard.hasNorm(normId)) {
			return this.normativeBoard.getSalience(normId);
		}
		
		return null;
	}
}