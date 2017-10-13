package emilia.defaultImpl.modules.compliance;

import emilia.board.NormativeBoardInterface;
import emilia.modules.compliance.NormComplianceAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NormComplianceController extends NormComplianceAbstract {
  
  
  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory
      .getLogger(NormComplianceController.class);
  
  
  /**
   * Create a normative compliance controller
   * 
   * @param agentId
   *          Agent identification
   * @param normativeBoard
   *          Normative board
   */
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
  public double getNormativeDrive(int normId) {
    if(this.normativeBoard.hasNorm(normId)) {
      return this.normativeBoard.getSalience(normId);
    }
    
    return -1;
  }
}