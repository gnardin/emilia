package emilia;

import java.util.Calendar;
import org.junit.Test;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.NormativeEventType;
import emilia.entity.event.type.NormativeEvent;
import emilia.entity.norm.NormEntityAbstract.NormSource;
import emilia.entity.norm.NormEntityAbstract.NormStatus;
import emilia.entity.norm.NormEntityAbstract.NormType;
import emilia.impl.board.NormativeBoard;
import emilia.impl.entity.norm.NormContent;
import emilia.impl.entity.norm.NormEntity;
import emilia.impl.modules.NormSalienceController;
import emilia.modules.salience.NormSalienceAbstract;

public class EmiliaSalienceTest {
	
	@Test
	public void emiliaSalienceTest() throws Exception {
		NormativeBoard normativeBoard = new NormativeBoard();
		Integer normId = new Integer(1);
		NormEntity norm = new NormEntity(normId, NormType.SOCIAL,
				NormSource.DISTRIBUTED, NormStatus.GOAL, new NormContent("COOPERATE"),
				new Double(0.0));
		normativeBoard.setNorm(norm);
		
		NormSalienceAbstract normSalience = new NormSalienceController(
				normativeBoard);
		
		NormativeEventEntityAbstract eventEntity;
		
		System.out.println("A " + normativeBoard.getSalience(normId));
		
		// EMILIA receives COMPLIANCE Event
		eventEntity = new NormativeEvent(Calendar.getInstance().getTimeInMillis(),
				1, 1, 1, NormativeEventType.COMPLIANCE, normId);
		normSalience.receive(eventEntity);
		System.out.println("B " + normativeBoard.getSalience(normId));
		
		// EMILIA receives COMPLIANCE Event
		eventEntity = new NormativeEvent(Calendar.getInstance().getTimeInMillis(),
				2, 2, 1, NormativeEventType.COMPLIANCE_INFORMED, normId);
		normSalience.receive(eventEntity);
		System.out.println("C " + normativeBoard.getSalience(normId));
		
		// EMILIA receives VIOLATION Event
		eventEntity = new NormativeEvent(Calendar.getInstance().getTimeInMillis(),
				1, 1, 1, NormativeEventType.VIOLATION, normId);
		normSalience.receive(eventEntity);
		System.out.println("D " + normativeBoard.getSalience(normId));
		
		// EMILIA receives SANCTION Event
		eventEntity = new NormativeEvent(Calendar.getInstance().getTimeInMillis(),
				1, 2, 1, NormativeEventType.SANCTION, normId);
		normSalience.receive(eventEntity);
		System.out.println("E " + normativeBoard.getSalience(normId));
		
		// EMILIA receives VIOLATION INVOCATION Event
		eventEntity = new NormativeEvent(Calendar.getInstance().getTimeInMillis(),
				1, 2, 1, NormativeEventType.VIOLATION_INVOCATION, normId);
		normSalience.receive(eventEntity);
		System.out.println("F " + normativeBoard.getSalience(normId));
	}
}