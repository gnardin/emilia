package emilia.modules.adoption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import emilia.board.NormativeBoardInterface;
import emilia.board.NormativeBoardListener;

public abstract class NormAdoptionAbstract implements NormativeBoardListener {
  
  private static final Logger       logger = LoggerFactory
      .getLogger( NormAdoptionAbstract.class );
  
  // Agent identification
  protected int                     agentId;
  
  // Normative Board
  protected NormativeBoardInterface normativeBoard;
  
  
  /**
   * Create a Norm Adoption module
   * 
   * @param agentId
   *          Agent identification
   * @param normativeBoard
   *          Normative board
   * @return none
   */
  public NormAdoptionAbstract( Integer agentId, NormativeBoardInterface normativeBoard ) {
    this.agentId = agentId;
    this.normativeBoard = normativeBoard;
    
    logger.debug( "NORM ADOPTION INITIALIZED" );
  }
}