package emilia.modules.enforcement;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SanctionInfoRepositoryMemory implements
		SanctionInfoRepositoryInterface {
	
	@SuppressWarnings("unused")
	private static final Logger																				logger	= LoggerFactory
																																								.getLogger(SanctionInfoRepositoryMemory.class);
	
	// Norm information repository <NormId, NormEvaluation>
	protected Map<Integer, NormInfoEntityInterface>										normInfoRep;
	
	// Sanction information repository <NormId, <SanctionId, SanctionEvaluation>>
	protected Map<Integer, Map<Integer, SanctionInfoEntityInterface>>	sanctionInfoRep;
	
	
	/**
	 * Create a sanction information repository into memory
	 * 
	 * @param none
	 * @return none
	 */
	public SanctionInfoRepositoryMemory() {
		this.normInfoRep = new HashMap<Integer, NormInfoEntityInterface>();
		this.sanctionInfoRep = new HashMap<Integer, Map<Integer, SanctionInfoEntityInterface>>();
	}
	
	
	@Override
	public boolean hasNormInfo(int normId) {
		return this.normInfoRep.containsKey(normId);
	}
	
	
	@Override
	public NormInfoEntityInterface getNormInfo(int normId) {
		NormInfoEntityInterface normInfo = null;
		
		if (this.normInfoRep.containsKey(normId)) {
			normInfo = this.normInfoRep.get(normId);
		}
		
		return normInfo;
	}
	
	
	@Override
	public Map<Integer, NormInfoEntityInterface> getNormsInfo() {
		return this.normInfoRep;
	}
	
	
	@Override
	public void setNormInfo(int normId, NormInfoEntityInterface evaluation) {
		this.normInfoRep.put(normId, evaluation);
	}
	
	
	@Override
	public SanctionInfoEntityInterface getSanctionInfo(int normId, int sanctionId) {
		
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
	public Map<Integer, SanctionInfoEntityInterface> getSanctionsInfo(int normId) {
		
		Map<Integer, SanctionInfoEntityInterface> sanctionInfo = null;
		if (this.sanctionInfoRep.containsKey(normId)) {
			sanctionInfo = this.sanctionInfoRep.get(normId);
		}
		
		return sanctionInfo;
	}
	
	
	@Override
	public void setSanctionInfo(int normId, int sanctionId,
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