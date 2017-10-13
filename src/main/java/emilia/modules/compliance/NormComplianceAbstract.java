package emilia.modules.compliance;

import emilia.board.NormativeBoardInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class NormComplianceAbstract {
  
  
  @SuppressWarnings("unused")
  private static final Logger       logger = LoggerFactory
      .getLogger(NormComplianceAbstract.class);
  
  // Agent identification
  protected int                     agentId;
  
  // Normative Board
  protected NormativeBoardInterface normativeBoard;
  
  
  /**
   * Create a norm compliance
   * 
   * @param agentId
   *          Agent identification
   * @param normativeBoard
   *          Normative board
   * @return none
   */
  public NormComplianceAbstract(Integer agentId,
      NormativeBoardInterface normativeBoard) {
    this.agentId = agentId;
    this.normativeBoard = normativeBoard;
  }
  
  
  /**
   * Get normative drive
   * 
   * @param normId
   *          Norm identification
   * @return Normative drive
   */
  public abstract double getNormativeDrive(int normId);
}