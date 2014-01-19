package emilia.modules.enforcement;

import emilia.board.normative.NormativeBoardInterface;
import emilia.board.sanction.SanctionBoardInterface;
import emilia.entity.event.EventEntity;

public class NormEnforcementController extends NormEnforcementAbstract {

	protected NormativeBoardInterface normativeBoard;

	protected SanctionBoardInterface sanctionBoard;

	public NormEnforcementController(int id,
			NormativeBoardInterface normativeBoard,
			SanctionBoardInterface sanctionBoard) {
		super(id);
		this.normativeBoard = normativeBoard;
		this.sanctionBoard = sanctionBoard;
	}

	@Override
	public void monitor() {
	}

	@Override
	public void detect() {
	}

	@Override
	public void evaluate() {
	}

	@Override
	public void enforce() {
	}

	@Override
	public void adapt() {
	}

	@Override
	public void receive(EventEntity event) {
	}
}