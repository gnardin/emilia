package emilia;

import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.NormativeEventType;
import emilia.entity.event.type.ActionEvent;
import emilia.entity.event.type.NormativeEvent;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.NormSource;
import emilia.entity.norm.NormEntityAbstract.NormStatus;
import emilia.entity.norm.NormEntityAbstract.NormType;
import emilia.entity.sanction.SanctionCategory;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.entity.sanction.SanctionCategory.Discernibility;
import emilia.entity.sanction.SanctionCategory.Locus;
import emilia.entity.sanction.SanctionCategory.Mode;
import emilia.entity.sanction.SanctionCategory.Polarity;
import emilia.entity.sanction.SanctionCategory.Source;
import emilia.entity.sanction.SanctionEntityAbstract.SanctionStatus;
import emilia.modules.enforcement.NormEnforcementListener;
import examples.pgg.entity.action.CooperateAction;
import examples.pgg.entity.action.DefectAction;
import examples.pgg.entity.norm.NormContent;
import examples.pgg.entity.norm.NormEntity;
import examples.pgg.entity.sanction.SanctionContent;
import examples.pgg.entity.sanction.SanctionEntity;
import examples.pgg.entity.sanction.SanctionContent.Sanction;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class EmiliaControllerTest implements NormEnforcementListener {
	
	@Test
	public void emiliaControllerTest() {
		// Create the Emilia agent
		EmiliaController emilia = new EmiliaController(1,
				"src/main/resources/conf/emilia.xml",
				"src/main/resources/conf/emilia.xsd");
		
		emilia.registerNormEnforcement(this);
		
		// COOPERATE norm
		Integer normId = 1;
		NormContent normContent = new NormContent(new CooperateAction(),
				new DefectAction());
		NormEntityAbstract norm = new NormEntity(normId, NormType.SOCIAL,
				NormSource.DISTRIBUTED, NormStatus.GOAL, normContent, 0.0);
		
		// PUNISHMENT sanction
		Integer sanctionId = 1;
		SanctionContent sanctionContent = new SanctionContent(Sanction.PUNISHMENT,
				new Double(1.5), new Double(3.0));
		SanctionCategory sanctionCategory = new SanctionCategory(Source.INFORMAL,
				Locus.OTHER_DIRECTED, Mode.DIRECT, Polarity.NEGATIVE,
				Discernibility.UNOBSTRUSIVE);
		SanctionEntityAbstract sanction = new SanctionEntity(sanctionId,
				sanctionCategory, SanctionStatus.ACTIVE, sanctionContent);
		
		// Norms X Sanctions
		Map<NormEntityAbstract, List<SanctionEntityAbstract>> normsSanctions = new HashMap<NormEntityAbstract, List<SanctionEntityAbstract>>();
		List<SanctionEntityAbstract> sanctions = new ArrayList<SanctionEntityAbstract>();
		sanctions.add(sanction);
		normsSanctions.put(norm, sanctions);
		
		emilia.addNormsSanctions(normsSanctions);
		
		// Event entities
		NormativeEventEntityAbstract eventEntity;
		
		System.out.println("A " + emilia.getNormativeDrive(normId));
		
		// EMILIA receives COMPLIANCE Event
		eventEntity = new NormativeEvent(Calendar.getInstance().getTimeInMillis(),
				1, 1, 1, NormativeEventType.COMPLIANCE, normId);
		emilia.input(eventEntity);
		System.out.println("B " + emilia.getNormativeDrive(normId));
		
		// EMILIA receives COMPLIANCE Event
		eventEntity = new NormativeEvent(Calendar.getInstance().getTimeInMillis(),
				2, 2, 1, NormativeEventType.COMPLIANCE_INFORMED, normId);
		emilia.input(eventEntity);
		System.out.println("C " + emilia.getNormativeDrive(normId));
		
		// EMILIA receives VIOLATION Event
		eventEntity = new NormativeEvent(Calendar.getInstance().getTimeInMillis(),
				1, 1, 1, NormativeEventType.VIOLATION, normId);
		emilia.input(eventEntity);
		System.out.println("D " + emilia.getNormativeDrive(normId));
		
		// EMILIA receives SANCTION Event
		eventEntity = new NormativeEvent(Calendar.getInstance().getTimeInMillis(),
				1, 2, 1, NormativeEventType.SANCTION, normId);
		emilia.input(eventEntity);
		System.out.println("E " + emilia.getNormativeDrive(normId));
		
		// EMILIA receives VIOLATION INVOCATION Event
		eventEntity = new NormativeEvent(Calendar.getInstance().getTimeInMillis(),
				1, 2, 1, NormativeEventType.VIOLATION_INVOCATION, normId);
		emilia.input(eventEntity);
		System.out.println("F " + emilia.getNormativeDrive(normId));
		
		// EMILIA receives COMPLIANCE ACTION Event
		eventEntity = new ActionEvent(Calendar.getInstance().getTimeInMillis(), 1,
				2, 1, new CooperateAction());
		emilia.input(eventEntity);
		System.out.println("G " + emilia.getNormativeDrive(normId));
		
		// EMILIA receives VIOLATION ACTION Event
		eventEntity = new ActionEvent(Calendar.getInstance().getTimeInMillis(), 1,
				2, 1, new DefectAction());
		emilia.input(eventEntity);
		System.out.println("H " + emilia.getNormativeDrive(normId));
	}
	
	
	@Override
	public void receive(NormativeEventEntityAbstract entity,
			NormEntityAbstract norm, SanctionEntityAbstract sanction) {
		
		if (sanction.getContent() instanceof SanctionContent) {
			SanctionContent sanctionContent = (SanctionContent) sanction.getContent();
			
			System.out.println("EMILIA Controller receive "
					+ sanctionContent.getAction() + " " + sanctionContent.getAmount());
		}
		
	}
}