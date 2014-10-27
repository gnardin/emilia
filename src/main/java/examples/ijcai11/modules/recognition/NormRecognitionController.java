package examples.ijcai11.modules.recognition;

import emilia.board.NormativeBoardInterface;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.NormativeEventType;
import emilia.entity.event.type.ActionEvent;
import emilia.entity.event.type.NormativeEvent;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.NormStatus;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.EventListener;
import emilia.modules.recognition.NormRecognitionAbstract;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NormRecognitionController extends NormRecognitionAbstract
		implements EventListener {
	
	private static final Logger		logger								= LoggerFactory
																													.getLogger(NormRecognitionController.class);
	
	public final static int				thresholdMessages			= 2;
	
	public final static int				thresholdCompliances	= 10;
	
	public Map<Integer, Integer>	numMessages;
	
	public Map<Integer, Integer>	numCompliances;
	
	
	public NormRecognitionController(Integer agentId,
			NormativeBoardInterface normativeBoard) {
		super(agentId, normativeBoard);
		this.numMessages = new HashMap<Integer, Integer>();
		this.numCompliances = new HashMap<Integer, Integer>();
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
			int normId = ((NormativeEvent) event).getNormId();
			norms = this.normativeBoard.match(normId);
		}
		if (norms != null) {
			Map<NormEntityAbstract, List<SanctionEntityAbstract>> normSanctions = new HashMap<NormEntityAbstract, List<SanctionEntityAbstract>>();
			for(NormEntityAbstract norm : norms) {
				List<SanctionEntityAbstract> sanctions = this.normativeBoard.getSanctions(norm.getId());
				normSanctions.put(norm, sanctions);
			}
			this.processEvent(event, normSanctions);
		}
	}
	
	
	@Override
	public void recognizeNorm(NormativeEventEntityAbstract event) {
		int normId = -1;
		int numMsg = 0;
		int numCompliance = 0;
		boolean updated = false;
		if ((event.getType().equals(NormativeEventType.COMPLIANCE))
				|| (event.getType().equals(NormativeEventType.COMPLIANCE_INFORMED))
				|| (event.getType().equals(NormativeEventType.COMPLIANCE_OBSERVED))
				|| (event.getType().equals(NormativeEventType.PUNISHMENT))
				|| (event.getType().equals(NormativeEventType.PUNISHMENT_INFORMED))
				|| (event.getType().equals(NormativeEventType.PUNISHMENT_OBSERVED))
				|| (event.getType().equals(NormativeEventType.SANCTION))
				|| (event.getType().equals(NormativeEventType.SANCTION_INFORMED))
				|| (event.getType().equals(NormativeEventType.SANCTION_OBSERVED))) {
			NormativeEvent ne = (NormativeEvent) event;
			normId = ne.getNormId();
			if (this.numCompliances.containsKey(normId)) {
				numCompliance = this.numCompliances.get(normId) + 1;
			} else {
				numCompliance = 1;
			}
			this.numCompliances.put(normId, numCompliance);
			updated = true;
		} else if ((event.getType()
				.equals(NormativeEventType.COMPLIANCE_INVOCATION))
				|| (event.getType()
						.equals(NormativeEventType.COMPLIANCE_INVOCATION_INFORMED))
				|| (event.getType()
						.equals(NormativeEventType.COMPLIANCE_INVOCATION_OBSERVED))) {
			NormativeEvent ne = (NormativeEvent) event;
			normId = ne.getNormId();
			if (this.numMessages.containsKey(normId)) {
				numMsg = this.numMessages.get(normId) + 1;
			} else {
				numMsg = 1;
			}
			this.numMessages.put(normId, numMsg);
			updated = true;
		}
		if ((updated)
				&& (this.normativeBoard.getNorm(normId).getStatus()
						.equals(NormStatus.INACTIVE))) {
			if (this.numMessages.containsKey(normId)) {
				numMsg = this.numMessages.get(normId);
			}
			if (this.numCompliances.containsKey(normId)) {
				numCompliance = this.numCompliances.get(normId);
			}
			// Check whether the norm should become a BELIEF
			if ((numMsg >= thresholdMessages)
					&& (numCompliance >= thresholdCompliances)) {
				NormEntityAbstract norm = this.normativeBoard.getNorm(normId);
				norm.setStatus(NormStatus.BELIEF);
				this.normativeBoard.setNorm(norm);
			}
		}
		logger.debug(this.agentId + " " + event.getType() + " MSG " + numMsg
				+ " COMPLIANCE " + numCompliance);
	}
	
	
	@Override
	public void recognizeSanction(NormativeEventEntityAbstract event) {
		// Intended to do nothing in this implementation
	}
	
	
	@Override
	public void receive(NormativeEventEntityAbstract event,
			Map<NormEntityAbstract, List<SanctionEntityAbstract>> normSanctions) {
		this.recognizeNorm(event);
	}
}