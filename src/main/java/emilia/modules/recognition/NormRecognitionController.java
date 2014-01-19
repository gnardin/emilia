package emilia.modules.recognition;

import emilia.board.normative.NormativeBoardInterface;
import emilia.board.sanction.SanctionBoardInterface;
import emilia.entity.event.EventEntity;

public class NormRecognitionController extends NormRecognitionAbstract {

	protected NormativeBoardInterface normativeBoard;

	protected SanctionBoardInterface sanctionBoard;

	public NormRecognitionController(int id,
			NormativeBoardInterface normativeBoard,
			SanctionBoardInterface sanctionBoard) {
		super(id);
		this.normativeBoard = normativeBoard;
		this.sanctionBoard = sanctionBoard;
	}

	@Override
	public void receive(EventEntity event) {
	}
}