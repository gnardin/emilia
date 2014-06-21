package emilia.modules.adoption;

import emilia.board.NormativeBoardInterface;
import emilia.board.NormativeBoardListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class NormAdoptionAbstract implements NormativeBoardListener {
	
	private static final Logger				logger	= LoggerFactory
																								.getLogger(NormAdoptionAbstract.class);
	
	// Normative Board
	protected NormativeBoardInterface	normativeBoard;
	
	
	/**
	 * Create a Norm Adoption module
	 * 
	 * @param normativeBoard
	 *          Normative board
	 * @return none
	 */
	public NormAdoptionAbstract(NormativeBoardInterface normativeBoard) {
		this.normativeBoard = normativeBoard;
		
		logger.debug("NORM ADOPTION INITIALIZED");
	}
}