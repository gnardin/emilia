package emilia;

import emilia.entity.event.EventEntityAbstract;
import emilia.entity.event.EventType;
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
		EventEntityAbstract eventEntity;
		
		System.out.println(emilia.getNormativeDrive(normId));
		
		// EMILIA receives COMPLIANCE Event
		eventEntity = new NormativeEvent(1, 1, 1, EventType.COMPLIANCE, normId);
		emilia.input(eventEntity);
		System.out.println(emilia.getNormativeDrive(normId));
		
		// EMILIA receives COMPLIANCE Event
		eventEntity = new NormativeEvent(2, 2, 1, EventType.COMPLIANCE_INFORMED,
				normId);
		emilia.input(eventEntity);
		System.out.println(emilia.getNormativeDrive(normId));
		
		// EMILIA receives VIOLATION Event
		eventEntity = new NormativeEvent(1, 1, 1, EventType.VIOLATION, normId);
		emilia.input(eventEntity);
		System.out.println(emilia.getNormativeDrive(normId));
		
		// EMILIA receives SANCTION Event
		eventEntity = new NormativeEvent(1, 2, 1, EventType.SANCTION, normId);
		emilia.input(eventEntity);
		System.out.println(emilia.getNormativeDrive(normId));
	}
}