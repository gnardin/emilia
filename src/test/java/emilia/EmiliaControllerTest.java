package emilia;

import emilia.board.normative.NormativeBoard;
import emilia.board.normative.NormativeBoardInterface;
import emilia.board.sanction.SanctionBoard;
import emilia.board.sanction.SanctionBoardInterface;
import emilia.entity.event.EventEntity;
import emilia.entity.event.EventEntity.EventType;
import emilia.entity.event.content.ComplianceEvent;
import emilia.entity.event.content.SanctionEvent;
import emilia.entity.event.content.ViolationEvent;
import emilia.modules.adoption.NormAdoptionAbstract;
import emilia.modules.adoption.NormAdoptionController;
import emilia.modules.classifier.EventClassifier;
import emilia.modules.compliance.NormComplianceController;
import emilia.modules.enforcement.NormEnforcementAbstract;
import emilia.modules.enforcement.NormEnforcementController;
import emilia.modules.recognition.NormRecognitionAbstract;
import emilia.modules.recognition.NormRecognitionController;
import emilia.modules.salience.NormSalienceAbstract;
import emilia.modules.salience.NormSalienceController;

import org.junit.Test;

public class EmiliaControllerTest {

	@Test
	public void emiliaControllerTest() {
		// Create the Normative Board
		NormativeBoardInterface normativeBoard = new NormativeBoard();

		// Create the Sanction Board
		SanctionBoardInterface sanctionBoard = new SanctionBoard();

		// Create the Norm Recognition
		NormRecognitionAbstract normRecognition = new NormRecognitionController(
				1, normativeBoard, sanctionBoard);

		// Create the Norm Adoption
		NormAdoptionAbstract normAdoption = new NormAdoptionController();

		// Create the Norm Salience
		NormSalienceAbstract normSalience = new NormSalienceController(2,
				normativeBoard);

		// Create the Norm Enforcement
		NormEnforcementAbstract normEnforcement = new NormEnforcementController(
				3, normativeBoard, sanctionBoard);

		// Create the Emilia agent
		EmiliaController emilia = new EmiliaController(new EventClassifier(),
				normRecognition, normAdoption, normSalience, normEnforcement,
				new NormComplianceController(normativeBoard));

		int normId = 1;
		EventEntity eventEntity;

		// EMILIA receives COMPLIANCE Event
		eventEntity = new EventEntity(120, 1, 2, EventType.INFORMATION,
				new ComplianceEvent(normId));
		emilia.input(eventEntity);
		System.out.println(emilia.getNormativeDrive(normId));

		// EMILIA receives COMPLIANCE Event
		eventEntity = new EventEntity(120, 1, 2, EventType.INFORMATION,
				new ComplianceEvent(normId));
		emilia.input(eventEntity);
		System.out.println(emilia.getNormativeDrive(normId));

		// EMILIA receives VIOLATION Event
		eventEntity = new EventEntity(120, 1, 2, EventType.INFORMATION,
				new ViolationEvent(normId));
		emilia.input(eventEntity);
		System.out.println(emilia.getNormativeDrive(normId));

		// EMILIA receives SANCTION Event
		eventEntity = new EventEntity(120, 1, 2, EventType.INFORMATION,
				new SanctionEvent(normId));
		emilia.input(eventEntity);
		System.out.println(emilia.getNormativeDrive(normId));

	}
}