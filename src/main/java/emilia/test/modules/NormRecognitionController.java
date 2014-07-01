package emilia.test.modules;

import emilia.board.NormativeBoardInterface;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.type.ActionEvent;
import emilia.entity.event.type.NormativeEvent;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.NormSource;
import emilia.entity.norm.NormEntityAbstract.NormStatus;
import emilia.entity.norm.NormEntityAbstract.NormType;
import emilia.entity.sanction.SanctionCategory;
import emilia.entity.sanction.SanctionCategory.Discernibility;
import emilia.entity.sanction.SanctionCategory.Locus;
import emilia.entity.sanction.SanctionCategory.Mode;
import emilia.entity.sanction.SanctionCategory.Polarity;
import emilia.entity.sanction.SanctionCategory.Source;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.entity.sanction.SanctionEntityAbstract.SanctionStatus;
import emilia.modules.recognition.NormRecognitionAbstract;
import emilia.test.entity.action.CooperateAction;
import emilia.test.entity.action.DefectAction;
import emilia.test.entity.norm.NormContent;
import emilia.test.entity.norm.NormEntity;
import emilia.test.entity.sanction.SanctionContent;
import emilia.test.entity.sanction.SanctionContent.Sanction;
import emilia.test.entity.sanction.SanctionEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NormRecognitionController extends NormRecognitionAbstract {
	
	public NormRecognitionController(Integer agentId,
			NormativeBoardInterface normativeBoard) {
		super(agentId, normativeBoard);
		
		// COOPERATE norm
		Integer normId = 1;
		NormContent normContent = new NormContent(new CooperateAction(),
				new DefectAction());
		NormEntityAbstract norm = new NormEntity(normId, NormType.SOCIAL,
				NormSource.DISTRIBUTED, NormStatus.GOAL, normContent, 0.0);
		this.normativeBoard.setNorm(norm);
		
		// PUNISHMENT sanction
		Integer sanctionId = 1;
		SanctionContent sanctionContent = new SanctionContent(Sanction.PUNISHMENT,
				new Double(3.0));
		SanctionCategory sanctionCategory = new SanctionCategory(Source.INFORMAL,
				Locus.OTHER_DIRECTED, Mode.DIRECT, Polarity.NEGATIVE,
				Discernibility.UNOBSTRUSIVE);
		SanctionEntityAbstract sanction = new SanctionEntity(sanctionId,
				sanctionCategory, SanctionStatus.ACTIVE, sanctionContent);
		this.normativeBoard.setSanction(sanction);
		
		// Norm X Sanction
		this.normativeBoard.setNormSanction(normId, sanctionId);
	}
	
	
	@Override
	public void matchEvent(NormativeEventEntityAbstract event) {
		List<NormEntityAbstract> norms = null;
		
		this.recognizeNorm(event);
		this.recognizeSanction(event);
		
		if (event instanceof ActionEvent) {
			String action = ((ActionEvent) event).getAction().getDescription();
			norms = this.normativeBoard.match(action);
		} else if (event instanceof NormativeEvent) {
			Integer normId = ((NormativeEvent) event).getNormId();
			norms = this.normativeBoard.match(normId);
		}
		
		if (norms != null) {
			List<SanctionEntityAbstract> sanctions;
			Map<NormEntityAbstract, List<SanctionEntityAbstract>> normSanctions = new HashMap<NormEntityAbstract, List<SanctionEntityAbstract>>();
			
			for(NormEntityAbstract norm : norms) {
				sanctions = this.normativeBoard.getSanctions(norm.getId());
				normSanctions.put(norm, sanctions);
			}
			if (normSanctions.size() > 0) {
				this.processEvent(event, normSanctions);
			}
		}
	}
	
	
	@Override
	public void recognizeNorm(NormativeEventEntityAbstract event) {
		// Intended to do nothing in this implementation
	}
	
	
	@Override
	public void recognizeSanction(NormativeEventEntityAbstract event) {
		// Intended to do nothing in this implementation
	}
}