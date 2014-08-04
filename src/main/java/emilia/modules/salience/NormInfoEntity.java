package emilia.modules.salience;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NormInfoEntity {
	
	@SuppressWarnings("unused")
	private static final Logger			logger	= LoggerFactory
																							.getLogger(NormInfoEntity.class);
	
	// <Type, Quantity>
	private Map<DataType, Integer>	normInfo;
	
	
	/**
	 * Create a Norm Information Entity
	 * 
	 * @param none
	 * @return none
	 */
	public NormInfoEntity() {
		this.normInfo = new HashMap<DataType, Integer>();
		
		for(DataType dataType : DataType.values()) {
			this.normInfo.put(dataType, 0);
		}
	}
	
	
	/**
	 * Increment the data type of one unit
	 * 
	 * @param dataType
	 *          Data type
	 * @param increment
	 *          Increment the instances of data type
	 * @return none
	 */
	public void increment(DataType dataType, Integer increment) {
		if(this.normInfo.containsKey(dataType)) {
			this.normInfo.put(dataType, this.normInfo.get(dataType) + increment);
		} else {
			this.normInfo.put(dataType, 1);
		}
	}
	
	
	/**
	 * Get number of data type
	 * 
	 * @param dataType
	 *          Data type
	 * @return Number of data type
	 */
	public Integer getNumber(DataType dataType) {
		Integer result = 0;
		
		if(this.normInfo.containsKey(dataType)) {
			result = this.normInfo.get(dataType);
		}
		
		return result;
	}
	
	
	/**
	 * Set the number of data type
	 * 
	 * @param dataType
	 *          Data type
	 * @param number
	 *          Number of instances of data type
	 * @return none
	 */
	public void setNumber(DataType dataType, Integer number) {
		if(number < 0) {
			number = 0;
		}
		this.normInfo.put(dataType, number);
	}
}