package emilia.modules.salience;

import java.util.HashMap;
import java.util.Map;

import emilia.entity.event.EventContentAbstract;
import emilia.modules.salience.NormInfoEntity;
import emilia.modules.salience.NormInfoRepositoryInterface;

public class NormInfoRepositoryMemory implements NormInfoRepositoryInterface {

	private Map<Integer, NormInfoEntity> normativeInfoRep;

	/**
	 * Constructor
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
	 *            Norm identification
	 * @param dataType
	 *            Information type
	 * @return none
	 */
	@Override
	public int getNormInfo(int normId, int dataType) {
		int result = 0;

		if (this.normativeInfoRep.containsKey(normId)) {
			NormInfoEntity normInfoEntity = this.normativeInfoRep.get(normId);

			if (normInfoEntity.has(dataType)) {
				result = normInfoEntity.getNumber(dataType);
			}
		}

		return result;
	}

	/**
	 * Increment the counter of a data type for a norm depending on the event
	 * content
	 * 
	 * @param content
	 *            Event content
	 * @return none
	 */
	@Override
	public void increment(EventContentAbstract content) {

		NormInfoEntity normInfoEntity;
		if (this.normativeInfoRep.containsKey(content.getNormId())) {
			normInfoEntity = this.normativeInfoRep.get(content.getNormId());
		} else {
			normInfoEntity = new NormInfoEntity();
		}

		normInfoEntity.increment(content.getType().ordinal());
		this.normativeInfoRep.put(content.getNormId(), normInfoEntity);
	}
}
