package emilia.modules.salience;

import java.util.HashMap;
import java.util.Map;

public class NormInfoEntity {

	private Map<Integer, Integer> normInfo;

	/**
	 * Constructor
	 * 
	 * @param none
	 * @return none
	 */
	public NormInfoEntity() {
		this.normInfo = new HashMap<Integer, Integer>();
	}

	/**
	 * Has data type
	 * 
	 * @param dataType
	 *            Number of the data type
	 * @return True if it has data type, False otherwise
	 */
	public boolean has(int dataType) {
		return this.normInfo.containsKey(dataType);
	}

	/**
	 * Increment of 1 the data type
	 * 
	 * @param dataType
	 *            Number of the data type
	 * @return none
	 */
	public void increment(int dataType) {
		if (this.normInfo.containsKey(dataType)) {
			this.normInfo.put(dataType, this.normInfo.get(dataType) + 1);
		} else {
			this.normInfo.put(dataType, 1);
		}
	}

	/**
	 * Get number of data type
	 * 
	 * @param dataType
	 *            Number of the data type
	 * @return Number of data type
	 */
	public int getNumber(int dataType) {
		int result = 0;

		if (this.normInfo.containsKey(dataType)) {
			result = this.normInfo.get(dataType);
		}

		return result;
	}

	/**
	 * Set the number of data type
	 * 
	 * @param dataType
	 *            Data type
	 * @param number
	 *            Number of data type
	 * @return none
	 */
	public void setNumber(int dataType, int number) {
		this.normInfo.put(dataType, number);
	}
}