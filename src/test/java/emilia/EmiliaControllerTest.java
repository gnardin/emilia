package emilia;

import emilia.defaultImpl.entity.norm.NormContent;
import emilia.defaultImpl.entity.norm.NormEntity;
import emilia.defaultImpl.entity.sanction.SanctionContent;
import emilia.defaultImpl.entity.sanction.SanctionContent.Sanction;
import emilia.defaultImpl.entity.sanction.SanctionEntity;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.NormativeEventType;
import emilia.entity.event.type.ActionEvent;
import emilia.entity.event.type.NormativeEvent;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.NormSource;
import emilia.entity.norm.NormEntityAbstract.NormStatus;
import emilia.entity.norm.NormEntityAbstract.NormType;
import emilia.entity.sanction.SanctionCategory;
import emilia.entity.sanction.SanctionCategory.Discernability;
import emilia.entity.sanction.SanctionCategory.Locus;
import emilia.entity.sanction.SanctionCategory.Mode;
import emilia.entity.sanction.SanctionCategory.Polarity;
import emilia.entity.sanction.SanctionCategory.Issuer;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.entity.sanction.SanctionEntityAbstract.SanctionStatus;
import emilia.modules.enforcement.NormEnforcementListener;
import examples.pgg.actions.CooperateAction;
import examples.pgg.actions.DefectAction;
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
				"src/main/resources/conf/pgg/emilia.xml",
				"src/main/resources/conf/emilia.xsd");
		
		emilia.init();
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
		SanctionCategory sanctionCategory = new SanctionCategory(Issuer.INFORMAL,
				Locus.OTHER_DIRECTED, Mode.DIRECT, Polarity.NEGATIVE,
				Discernability.UNOBSTRUSIVE);
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
		
		System.out.println("A " + emilia.getNormSalience(normId));
		
		// EMILIA receives COMPLIANCE Event
		eventEntity = new NormativeEvent(Calendar.getInstance().getTimeInMillis(),
				1, 1, 1, NormativeEventType.COMPLIANCE, normId);
		emilia.input(eventEntity);
		System.out.println("B " + emilia.getNormSalience(normId));
		
		// EMILIA receives COMPLIANCE Event
		eventEntity = new NormativeEvent(Calendar.getInstance().getTimeInMillis(),
				2, 2, 1, NormativeEventType.COMPLIANCE_INFORMED, normId);
		emilia.input(eventEntity);
		System.out.println("C " + emilia.getNormSalience(normId));
		
		// EMILIA receives VIOLATION Event
		eventEntity = new NormativeEvent(Calendar.getInstance().getTimeInMillis(),
				1, 1, 1, NormativeEventType.VIOLATION, normId);
		emilia.input(eventEntity);
		System.out.println("D " + emilia.getNormSalience(normId));
		
		// EMILIA receives SANCTION Event
		eventEntity = new NormativeEvent(Calendar.getInstance().getTimeInMillis(),
				1, 2, 1, NormativeEventType.SANCTION, normId);
		emilia.input(eventEntity);
		System.out.println("E " + emilia.getNormSalience(normId));
		
		// EMILIA receives VIOLATION INVOCATION Event
		eventEntity = new NormativeEvent(Calendar.getInstance().getTimeInMillis(),
				1, 2, 1, NormativeEventType.VIOLATION_INVOCATION, normId);
		emilia.input(eventEntity);
		System.out.println("F " + emilia.getNormSalience(normId));
		
		// EMILIA receives COMPLIANCE ACTION Event
		eventEntity = new ActionEvent(Calendar.getInstance().getTimeInMillis(), 1,
				2, 1, new CooperateAction());
		emilia.input(eventEntity);
		System.out.println("G " + emilia.getNormSalience(normId));
		
		// EMILIA receives VIOLATION ACTION Event
		eventEntity = new ActionEvent(Calendar.getInstance().getTimeInMillis(), 1,
				2, 1, new DefectAction());
		emilia.input(eventEntity);
		System.out.println("H " + emilia.getNormSalience(normId));
	}
	
	
	@Override
	public void receive(NormativeEventEntityAbstract entity,
			NormEntityAbstract norm, SanctionEntityAbstract sanction) {
		
		if(sanction.getContent() instanceof SanctionContent) {
			SanctionContent sanctionContent = (SanctionContent) sanction.getContent();
			
			System.out.println("EMILIA Controller receive "
					+ sanctionContent.getAction() + " " + sanctionContent.getAmount());
		}
		
	}
}