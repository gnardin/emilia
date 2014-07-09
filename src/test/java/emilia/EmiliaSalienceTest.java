package emilia;

import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.NormativeEventType;
import emilia.entity.event.type.NormativeEvent;
import emilia.entity.norm.NormEntityAbstract.NormSource;
import emilia.entity.norm.NormEntityAbstract.NormStatus;
import emilia.entity.norm.NormEntityAbstract.NormType;
import emilia.modules.salience.NormSalienceAbstract;
import examples.pgg.board.NormativeBoard;
import examples.pgg.entity.action.CooperateAction;
import examples.pgg.entity.action.DefectAction;
import examples.pgg.entity.norm.NormContent;
import examples.pgg.entity.norm.NormEntity;
import examples.pgg.modules.salience.NormSalienceController;
import java.util.Calendar;
import org.junit.Test;

public class EmiliaSalienceTest {
	
	@Test
	public void normSalienceTest() throws Exception {
		NormativeBoard normativeBoard = new NormativeBoard();
		Integer normId = new Integer(1);
		NormEntity norm = new NormEntity(normId, NormType.SOCIAL,
				NormSource.DISTRIBUTED, NormStatus.GOAL, new NormContent(
						new CooperateAction(), new DefectAction()), new Double(0.0));
		normativeBoard.setNorm(norm);
		
		NormSalienceAbstract normSalience = new NormSalienceController(1,
				normativeBoard);
		
		NormativeEventEntityAbstract eventEntity;
		
		System.out.println("A " + normativeBoard.getSalience(normId));
		
		// EMILIA receives COMPLIANCE Event
		eventEntity = new NormativeEvent(Calendar.getInstance().getTimeInMillis(),
				1, 1, 1, NormativeEventType.COMPLIANCE, normId);
		normSalience.receive(eventEntity, null);
		System.out.println("B " + normativeBoard.getSalience(normId));
		
		// EMILIA receives COMPLIANCE Event
		eventEntity = new NormativeEvent(Calendar.getInstance().getTimeInMillis(),
				2, 2, 1, NormativeEventType.COMPLIANCE_INFORMED, normId);
		normSalience.receive(eventEntity, null);
		System.out.println("C " + normativeBoard.getSalience(normId));
		
		// EMILIA receives VIOLATION Event
		eventEntity = new NormativeEvent(Calendar.getInstance().getTimeInMillis(),
				1, 1, 1, NormativeEventType.VIOLATION, normId);
		normSalience.receive(eventEntity, null);
		System.out.println("D " + normativeBoard.getSalience(normId));
		
		// EMILIA receives SANCTION Event
		eventEntity = new NormativeEvent(Calendar.getInstance().getTimeInMillis(),
				1, 2, 1, NormativeEventType.SANCTION, normId);
		normSalience.receive(eventEntity, null);
		System.out.println("E " + normativeBoard.getSalience(normId));
		
		// EMILIA receives VIOLATION INVOCATION Event
		eventEntity = new NormativeEvent(Calendar.getInstance().getTimeInMillis(),
				1, 2, 1, NormativeEventType.VIOLATION_INVOCATION, normId);
		normSalience.receive(eventEntity, null);
		System.out.println("F " + normativeBoard.getSalience(normId));
	}
}