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
	public Integer getNormInfo(Integer normId, DataType dataType);
	
	
	/**
	 * Increment the counter of a data type for a norm depending on the event
	 * content
	 * 
	 * @param content
	 *          Event content
	 * @return none
	 */
	public void increment(Integer normId, DataType dataType);
}