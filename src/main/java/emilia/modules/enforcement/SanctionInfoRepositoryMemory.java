package emilia.modules.enforcement;

import java.util.HashMap;
import java.util.Map;

public class SanctionInfoRepositoryMemory implements
		SanctionInfoRepositoryInterface {
	
	// Sanction information repository <NormId, <SanctionId, Evaluation>>
	private Map<Integer, Map<Integer, SanctionEvaluationInterface>>	sanctionInfoRep;
	
	
	/**
	 * Create a sanction information repository into memory
	 * 
	 * @param none
	 * @return none
	 */
	public SanctionInfoRepositoryMemory() {
		this.sanctionInfoRep = new HashMap<Integer, Map<Integer, SanctionEvaluationInterface>>();
	}
	
	
	@Override
	public SanctionEvaluationInterface getSanction(Integer normId,
			Integer sanctionId) {
		
		SanctionEvaluationInterface sanctionEval = null;
		if (this.sanctionInfoRep.containsKey(normId)) {
			Map<Integer, SanctionEvaluationInterface> sanctionInfo = this.sanctionInfoRep
					.get(normId);
			
			if (sanctionInfo.containsKey(sanctionId)) {
				sanctionEval = sanctionInfo.get(sanctionId);
			}
		}
		
		return sanctionEval;
	}
	
	
	@Override
	public Map<Integer, SanctionEvaluationInterface> getSanctions(Integer normId) {
		
		Map<Integer, SanctionEvaluationInterface> sanctionInfo = null;
		if (this.sanctionInfoRep.containsKey(normId)) {
			sanctionInfo = this.sanctionInfoRep.get(normId);
		}
		
		return sanctionInfo;
	}
	
	
	@Override
	public void setSanction(Integer normId, Integer sanctionId,
			SanctionEvaluationInterface evaluation) {
		
		Map<Integer, SanctionEvaluationInterface> sanctionInfo;
		if (this.sanctionInfoRep.containsKey(normId)) {
			sanctionInfo = this.sanctionInfoRep.get(normId);
		} else {
			sanctionInfo = new HashMap<Integer, SanctionEvaluationInterface>();
		}
		
		sanctionInfo.put(sanctionId, evaluation);
		this.sanctionInfoRep.put(normId, sanctionInfo);
	}
}