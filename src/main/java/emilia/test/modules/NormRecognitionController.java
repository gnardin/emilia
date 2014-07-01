package emilia.impl.modules;

import java.util.List;
import emilia.board.NormativeBoardInterface;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.type.ActionEvent;
import emilia.entity.event.type.NormativeEvent;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.NormSource;
import emilia.entity.norm.NormEntityAbstract.NormStatus;
import emilia.entity.norm.NormEntityAbstract.NormType;
import emilia.impl.entity.norm.NormContent;
import emilia.impl.entity.norm.NormEntity;
import emilia.impl.entity.sanction.SanctionContent;
import emilia.modules.recognition.NormRecognitionAbstract;

public class NormRecognitionController extends NormRecognitionAbstract {
	
	public NormRecognitionController(Integer agentId,
			NormativeBoardInterface normativeBoard) {
		super(agentId, normativeBoard);
		
		// COOPERATE norm
		NormContent normContent = new NormContent("COOPERATE");
		NormEntityAbstract norm = new NormEntity(1, NormType.SOCIAL,
				NormSource.DISTRIBUTED, NormStatus.GOAL, normContent, 0.0);
		
		SanctionContent sanctionContent = new SanctionContent("");
		
		this.normativeBoard.setNorm(norm);
	}
	
	
	@Override
	public void recognizeNorm(NormativeEventEntityAbstract event) {
	}
	
	
	@Override
	public void recognizeSanction(NormativeEventEntityAbstract event) {
	}
	
	
	@Override
	public void matchEvent(NormativeEventEntityAbstract event) {
		List<NormEntityAbstract> norms;
		
		if (event instanceof ActionEvent) {
			String action = ((ActionEvent) event).getAction().toString();
			norms = this.normativeBoard.match(action);
			if (norms.size() > 0) {
				this.processEvent(event, norms);
			}
		} else if (event instanceof NormativeEvent) {
			Integer normId = ((NormativeEvent) event).getNormId();
			norms = this.normativeBoard.match(normId);
			if (norms.size() > 0) {
				this.processEvent(event, norms);
			}
		}
	}
}