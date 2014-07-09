package emilia.modules.enforcement;

import java.util.Map;

public interface SanctionInfoRepositoryInterface {
	
	/**
	 * Get sanction evaluation information
	 * 
	 * @param normId
	 *          Norm identification
	 * @param sanctionId
	 *          Sanction identification
	 * @return Norm x sanction evaluation information
	 */
	public SanctionInfoEntityInterface getSanctionInfo(Integer normId,
			Integer sanctionId);
	
	
	/**
	 * Get sanctions evaluation information
	 * 
	 * @param normId
	 *          Norm identification
	 * @return Sanctions evaluation information
	 */
	public Map<Integer, SanctionInfoEntityInterface> getSanctionsInfo(Integer normId);
	
	
	/**
	 * Set norm x sanction evaluation information
	 * 
	 * @param normId
	 *          Norm identification
	 * @param sanctionId
	 *          Sanction identification
	 * @param evaluation
	 *          Norm x sanction evaluation information
	 * @return none
	 */
	public void setSanctionInfo(Integer normId, Integer sanctionId,
			SanctionInfoEntityInterface evaluation);
}