package emilia.modules.salience;

import emilia.board.NormativeBoardInterface;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.type.NormativeEvent;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.EventListener;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class NormSalienceAbstract implements EventListener {
	
	@SuppressWarnings("unused")
	private static final Logger						logger	= LoggerFactory
																										.getLogger(NormSalienceAbstract.class);
	
	// Agent identification
	protected Integer											agentId;
	
	// Normative information repository
	protected NormInfoRepositoryInterface	repository;
	
	// Normative Board
	protected NormativeBoardInterface			normativeBoard;
	
	
	/**
	 * Create a Norm Salience module
	 * 
	 * @param agentId
	 *          Agent identification
	 * @param normativeBoard
	 *          Normative board
	 * @return none
	 */
	public NormSalienceAbstract(Integer agentId,
			NormativeBoardInterface normativeBoard) {
		this.agentId = agentId;
		this.normativeBoard = normativeBoard;
	}
	
	
	/**
	 * Update salience
	 * 
	 * @param normId
	 *          Norm identification
	 * @param dataType
	 *          Data Type
	 * @return none
	 */
	public abstract void updateSalience(Integer normId);
	
	
	@Override
	public void receive(NormativeEventEntityAbstract event,
			Map<NormEntityAbstract, List<SanctionEntityAbstract>> normSanctions) {
		
		DataType dataType;
		switch(event.getType()) {
			case COMPLIANCE:
				dataType = DataType.COMPLIANCE;
				break;
			case COMPLIANCE_OBSERVED:
				dataType = DataType.COMPLIANCE_OBSERVED;
				break;
			case COMPLIANCE_INFORMED:
				dataType = DataType.COMPLIANCE_OBSERVED;
				break;
			case VIOLATION:
				dataType = DataType.VIOLATION;
				break;
			case VIOLATION_OBSERVED:
				dataType = DataType.VIOLATION_OBSERVED;
				break;
			case VIOLATION_INFORMED:
				dataType = DataType.VIOLATION_OBSERVED;
				break;
			case PUNISHMENT:
				dataType = DataType.PUNISHMENT;
				break;
			case PUNISHMENT_OBSERVED:
				dataType = DataType.PUNISHMENT;
				break;
			case PUNISHMENT_INFORMED:
				dataType = DataType.PUNISHMENT;
				break;
			case SANCTION:
				dataType = DataType.SANCTION;
				break;
			case SANCTION_OBSERVED:
				dataType = DataType.SANCTION;
				break;
			case SANCTION_INFORMED:
				dataType = DataType.SANCTION;
				break;
			case COMPLIANCE_INVOCATION:
				dataType = DataType.COMPLIANCE_INVOKED;
				break;
			case COMPLIANCE_INVOCATION_OBSERVED:
				dataType = DataType.COMPLIANCE_INVOKED;
				break;
			case COMPLIANCE_INVOCATION_INFORMED:
				dataType = DataType.COMPLIANCE_INVOKED;
				break;
			case VIOLATION_INVOCATION:
				dataType = DataType.VIOLATION_INVOKED;
				break;
			case VIOLATION_INVOCATION_OBSERVED:
				dataType = DataType.VIOLATION_INVOKED;
				break;
			case VIOLATION_INVOCATION_INFORMED:
				dataType = DataType.VIOLATION_INVOKED;
				break;
			default:
				dataType = null;
		}
		
		if ((dataType != null) && (event instanceof NormativeEvent)) {
			NormativeEvent normativeEvent = (NormativeEvent) event;
			this.repository.increment(normativeEvent.getNormId(), dataType);
			this.updateSalience(normativeEvent.getNormId());
		}
	}
}