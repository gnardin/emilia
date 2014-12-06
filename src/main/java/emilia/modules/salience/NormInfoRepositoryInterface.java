package emilia.modules.salience;

public interface NormInfoRepositoryInterface {
	
	/**
	 * Get normative information about a data type from a norm
	 * 
	 * @param normId
	 *          Norm identification
	 * @param dataType
	 *          Data type
	 * @return none
	 */
	public int getNormInfo(int normId, DataType dataType);
	
	
	/**
	 * Set normative information about a data type from a norm
	 * 
	 * @param normId
	 *          Norm identification
	 * @param dataType
	 *          Data type
	 * @param value
	 *          Value to set
	 * @return none
	 */
	public void setNormInfo(int normId, DataType dataType, int value);
	
	
	/**
	 * Increment the counter of a data type for a norm depending on the event
	 * content
	 * 
	 * @param normId
	 *          Norm identification
	 * @param dataType
	 *          Data type
	 * @return none
	 */
	public void increment(int normId, DataType dataType);
	
	
	/**
	 * Increment the counter of a data type for a norm depending on the event
	 * content by a specified amount
	 * 
	 * @param normId
	 *          Norm identification
	 * @param dataType
	 *          Data type
	 * @param value
	 *          Value to increment
	 * @return none
	 */
	public void increment(int normId, DataType dataType, int value);
}