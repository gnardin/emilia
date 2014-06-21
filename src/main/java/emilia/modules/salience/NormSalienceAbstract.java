package emilia.modules.salience;

import emilia.board.NormativeBoardInterface;
import emilia.entity.event.EventEntityAbstract;
import emilia.entity.event.type.NormativeEvent;
import emilia.modules.recognition.EventListener;

public abstract class NormSalienceAbstract implements EventListener {
	
	protected NormInfoRepositoryInterface	repository;
	
	protected NormativeBoardInterface			normativeBoard;
	
	
	/**
	 * Create a Norm Salience module
	 * 
	 * @param normativeBoard
	 *          Normative board
	 * @return none
	 */
	public NormSalienceAbstract(NormativeBoardInterface normativeBoard) {
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
	
	
	/**
	 * Receive a message from the Event Classifier
	 * 
	 * @param event
	 *          Event content
	 * @return none
	 */
	@Override
	public void receive(EventEntityAbstract event) {
		
		DataType dataType;
		switch(event.getType()) {
			case COMPLIANCE:
				dataType = DataType.COMPLIANCE;
				break;
			case COMPLIANCE_OBSERVED:
				dataType = DataType.COMPLIANCE;
				break;
			case COMPLIANCE_INFORMED:
				dataType = DataType.COMPLIANCE;
				break;
			case VIOLATION:
				dataType = DataType.VIOLATION;
				break;
			case VIOLATION_OBSERVED:
				dataType = DataType.VIOLATION;
				break;
			case VIOLATION_INFORMED:
				dataType = DataType.VIOLATION;
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
			NormativeEvent nEvent = (NormativeEvent) event;
			this.repository.increment(nEvent.getNormId(), dataType);
			this.updateSalience(nEvent.getNormId());
		}
	}
}