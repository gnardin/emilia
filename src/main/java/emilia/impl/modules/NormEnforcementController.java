package emilia.impl.modules;

import emilia.board.NormativeBoardInterface;
import emilia.entity.event.EventEntityAbstract;
import emilia.modules.enforcement.NormEnforcementAbstract;

public class NormEnforcementController extends NormEnforcementAbstract {
	
	protected NormativeBoardInterface	normativeBoard;
	
	
	public NormEnforcementController(NormativeBoardInterface normativeBoard) {
		this.normativeBoard = normativeBoard;
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
	public void receive(EventEntityAbstract event) {
	}
}