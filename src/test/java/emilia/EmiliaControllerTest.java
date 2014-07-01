package emilia;

import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.NormativeEventType;
import emilia.entity.event.type.ActionEvent;
import emilia.entity.event.type.NormativeEvent;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.enforcement.NormEnforcementListener;
import emilia.test.entity.action.CooperateAction;
import emilia.test.entity.action.DefectAction;
import emilia.test.entity.sanction.SanctionContent;
import java.util.Calendar;
import org.junit.Test;

public class EmiliaControllerTest implements NormEnforcementListener {
	
	@Test
	public void emiliaControllerTest() {
		// Create the Emilia agent
		EmiliaController emilia = new EmiliaController(1,
				"src/main/resources/conf/emilia.xml",
				"src/main/resources/conf/emilia.xsd");
		
		emilia.registerNormEnforcement(this);
		
		Integer normId = new Integer(1);
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
	public void receive(SanctionEntityAbstract sanction) {
		
		if (sanction.getContent() instanceof SanctionContent) {
			SanctionContent sanctionContent = (SanctionContent) sanction.getContent();
			
			System.out.println("EMILIA Controller receive "
					+ sanctionContent.getAction() + " " + sanctionContent.getAmount());
		}
		
	}
}