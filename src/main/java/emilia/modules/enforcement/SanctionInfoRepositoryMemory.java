package emilia.modules.enforcement;

import java.util.HashMap;
import java.util.Map;

public class SanctionInfoRepositoryMemory implements
		SanctionInfoRepositoryInterface {
	
	// Sanction information repository <NormId, <SanctionId, Evaluation>>
	private Map<Integer, Map<Integer, SanctionInfoEntityInterface>>	sanctionInfoRep;
	
	
	/**
	 * Create a sanction information repository into memory
	 * 
	 * @param none
	 * @return none
	 */
	public SanctionInfoRepositoryMemory() {
		this.sanctionInfoRep = new HashMap<Integer, Map<Integer, SanctionInfoEntityInterface>>();
	}
	
	
	@Override
	public SanctionInfoEntityInterface getSanctionInfo(Integer normId,
			Integer sanctionId) {
		
		SanctionInfoEntityInterface sanctionEval = null;
		if (this.sanctionInfoRep.containsKey(normId)) {
			Map<Integer, SanctionInfoEntityInterface> sanctionInfo = this.sanctionInfoRep
					.get(normId);
			
			if (sanctionInfo.containsKey(sanctionId)) {
				sanctionEval = sanctionInfo.get(sanctionId);
			}
		}
		
		return sanctionEval;
	}
	
	
	@Override
	public Map<Integer, SanctionInfoEntityInterface> getSanctionsInfo(Integer normId) {
		
		Map<Integer, SanctionInfoEntityInterface> sanctionInfo = null;
		if (this.sanctionInfoRep.containsKey(normId)) {
			sanctionInfo = this.sanctionInfoRep.get(normId);
		}
		
		return sanctionInfo;
	}
	
	
	@Override
	public void setSanctionInfo(Integer normId, Integer sanctionId,
			SanctionInfoEntityInterface evaluation) {
		
		Map<Integer, SanctionInfoEntityInterface> sanctionInfo;
		if (this.sanctionInfoRep.containsKey(normId)) {
			sanctionInfo = this.sanctionInfoRep.get(normId);
		} else {
			sanctionInfo = new HashMap<Integer, SanctionInfoEntityInterface>();
		}
		
		sanctionInfo.put(sanctionId, evaluation);
		this.sanctionInfoRep.put(normId, sanctionInfo);
	}
}