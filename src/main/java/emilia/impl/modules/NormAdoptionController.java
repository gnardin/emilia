package emilia.impl.modules;

import emilia.board.NormativeBoardInterface;
import emilia.board.NormativeEventType;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.Status;
import emilia.modules.adoption.NormAdoptionAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NormAdoptionController extends NormAdoptionAbstract {
	
	private static final Logger	logger	= LoggerFactory
																					.getLogger(NormAdoptionController.class);
	
	
	/**
	 * Create norm adoption
	 * 
	 * @param normativeBoard
	 *          Normative board
	 * @return none
	 */
	public NormAdoptionController(NormativeBoardInterface normativeBoard) {
		super(normativeBoard);
	}
	
	
	@Override
	public void receive(NormativeEventType type, NormEntityAbstract oldNorm,
			NormEntityAbstract newNorm) {
		
		String str = new String();
		
		if ((newNorm != null) && (newNorm.getStatus() != Status.GOAL)) {
			
			str = type.name() + " " + newNorm.getContent().toString() + " "
					+ newNorm.getStatus().name();
			logger.debug(str);
			
			newNorm.setStatus(Status.GOAL);
			this.normativeBoard.setNorm(newNorm);
			
			str = type.name() + " " + newNorm.getContent().toString() + " "
					+ newNorm.getStatus().name();
			logger.debug(str);
		} else {
			str = type.name() + " " + newNorm.getContent().toString() + " "
					+ newNorm.getStatus().name();
			logger.debug(str);
		}
	}
}