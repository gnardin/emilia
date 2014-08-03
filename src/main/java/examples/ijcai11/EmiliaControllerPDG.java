package examples.ijcai11;

import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import emilia.EmiliaController;
import emilia.entity.event.NormativeEventType;
import emilia.modules.EventListener;

public class EmiliaControllerPDG extends EmiliaController {
	
	@SuppressWarnings("unused")
	private static final Logger	logger	= LoggerFactory
																					.getLogger(EmiliaControllerPDG.class);
	
	
	public EmiliaControllerPDG(Integer agentId, String xmlFilename,
			String xsdFilename) {
		super(agentId, xmlFilename, xsdFilename);
		
		this.normEnforcement.registerCallback(
				new ArrayList<NormativeEventType>(Arrays.asList(
						NormativeEventType.COMPLIANCE,
						NormativeEventType.COMPLIANCE_OBSERVED,
						NormativeEventType.COMPLIANCE_INFORMED)),
				(EventListener) this.normRecognition);
	}
}