package emilia.modules.salience;

import java.util.HashMap;
import java.util.Map;
import emilia.modules.salience.NormInfoEntity;
import emilia.modules.salience.NormInfoRepositoryInterface;

public class NormInfoRepositoryMemory implements NormInfoRepositoryInterface {
	
	// <NormId, Normative Information>
	private Map<Integer, NormInfoEntity>	normativeInfoRep;
	
	
	/**
	 * Create a normative information repository into memory
	 * 
	 * @param none
	 * @return none
	 */
	public NormInfoRepositoryMemory() {
		this.normativeInfoRep = new HashMap<Integer, NormInfoEntity>();
	}
	
	
	/**
	 * Get normative information about a data type from a norm
	 * 
	 * @param normId
	 *          Norm identification
	 * @param dataType
	 *          Data type
	 * @return none
	 */
	@Override
	public Integer getNormInfo(Integer normId, DataType dataType) {
		Integer result = 0;
		
		if(this.normativeInfoRep.containsKey(normId)) {
			NormInfoEntity normInfoEntity = this.normativeInfoRep.get(normId);
			result = normInfoEntity.getNumber(dataType);
		}
		
		return result;
	}
	
	
	/**
	 * Increment the counter of a data type for a norm depending on the event
	 * content
	 * 
	 * @param content
	 *          Event content
	 * @return none
	 */
	@Override
	public void increment(Integer normId, DataType dataType) {
		
		NormInfoEntity normInfoEntity;
		if(this.normativeInfoRep.containsKey(normId)) {
			normInfoEntity = this.normativeInfoRep.get(normId);
		}else {
			normInfoEntity = new NormInfoEntity();
		}
		
		normInfoEntity.increment(dataType, 1);
		this.normativeInfoRep.put(normId, normInfoEntity);
	}
}
