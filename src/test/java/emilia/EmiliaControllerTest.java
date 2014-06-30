package emilia;

import java.util.Calendar;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.NormativeEventType;
import emilia.entity.event.type.NormativeEvent;
import org.junit.Test;

public class EmiliaControllerTest {
	
	@Test
	public void emiliaControllerTest() {
		// Create the Emilia agent
		EmiliaController emilia = new EmiliaController(1,
				"src/main/resources/conf/emilia.xml",
				"src/main/resources/conf/emilia.xsd");
		
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
	}
}