package emilia.modules.salience;

import emilia.board.normative.NormativeBoardInterface;
import emilia.entity.event.EventContentAbstract;
import emilia.entity.event.EventEntity;
import emilia.modules.classifier.EventClassifierListener;

public abstract class NormSalienceAbstract extends EventClassifierListener {

	protected NormInfoRepositoryInterface repository;

	protected NormativeBoardInterface normativeBoard;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Component identification
	 * @param normativeBoard
	 *            Normative board
	 * @return none
	 */
	public NormSalienceAbstract(int id, NormativeBoardInterface normativeBoard) {
		super(id);
		this.normativeBoard = normativeBoard;
	}

	/**
	 * Update salience
	 * 
	 * @param event
	 *            Event content
	 * @return none
	 */
	public abstract void updateSalience(EventContentAbstract content);

	/**
	 * Receive a message from the Event Classifier
	 * 
	 * @param event
	 *            Event entity
	 * @return none
	 */
	@Override
	public void receive(EventEntity event) {
		this.repository.increment(event.getContent());
		this.updateSalience(event.getContent());
	}
}