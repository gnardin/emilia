package emilia.impl.modules;

import java.util.List;
import emilia.board.NormativeBoardInterface;
import emilia.entity.event.EventEntityAbstract;
import emilia.entity.event.type.ActionEvent;
import emilia.entity.event.type.NormativeEvent;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.Source;
import emilia.entity.norm.NormEntityAbstract.Status;
import emilia.entity.norm.NormEntityAbstract.Type;
import emilia.impl.entity.norm.NormContent;
import emilia.impl.entity.norm.NormEntity;
import emilia.modules.recognition.NormRecognitionAbstract;

public class NormRecognitionController extends NormRecognitionAbstract {
	
	public NormRecognitionController(Integer agentId,
			NormativeBoardInterface normativeBoard) {
		super(agentId, normativeBoard);
		
		// COOPERATE norm
		NormContent content = new NormContent("COOPERATE");
		NormEntityAbstract norm = new NormEntity(1, Type.SOCIAL, Source.IMPERSONAL,
				Status.GOAL, "All", content, 0.0);
		
		this.normativeBoard.setNorm(norm);
	}
	
	
	@Override
	public void recognizeNorm(EventEntityAbstract event) {
	}
	
	
	@Override
	public void recognizeSanction(EventEntityAbstract event) {
	}
	
	
	@Override
	public void matchEvent(EventEntityAbstract event) {
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