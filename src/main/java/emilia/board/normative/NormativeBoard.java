package emilia.board.normative;

import java.util.Hashtable;
import java.util.Map;

public class NormativeBoard implements NormativeBoardInterface {

	private Map<Integer, Double> normativeBoard;

	/**
	 * Constructor
	 * 
	 * @param none
	 * @return none
	 */
	public NormativeBoard() {
		this.normativeBoard = new Hashtable<Integer, Double>();
	}

	/**
	 * Get norm salience
	 * 
	 * @param normId
	 *            Norm identification
	 * @return Norm salience
	 */
	@Override
	public double getSalience(int normId) {
		double salience = 0.0;

		if (this.normativeBoard.containsKey(normId)) {
			salience = this.normativeBoard.get(normId);
		}

		return salience;
	}

	/**
	 * Set norm salience
	 * 
	 * @param normId
	 *            Norm identification
	 * @param salience
	 *            Norm salience
	 * @return none
	 */
	@Override
	public void setSalience(int normId, double salience) {
		this.normativeBoard.put(normId, salience);
	}
}