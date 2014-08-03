package emilia.modules.enforcement;

import java.util.Map;

public interface SanctionInfoRepositoryInterface {
	
	/**
	 * Check the norm information existence
	 * 
	 * @param normId
	 *          Norm identification
	 * @return True Norm information exist, False otherwise
	 */
	public Boolean hasNormInfo(Integer normId);
	
	
	/**
	 * Get norm evaluation information
	 * 
	 * @param normId
	 *          Norm identification
	 * @return Norm evaluation information
	 */
	public NormInfoEntityInterface getNormInfo(Integer normId);
	
	
	/**
	 * Get norms evaluation information
	 * 
	 * @param none
	 * @return Norms evaluation information
	 */
	public Map<Integer, NormInfoEntityInterface> getNormsInfo();
	
	
	/**
	 * Set norm evaluation information
	 * 
	 * @param normId
	 *          Norm identification
	 * @param evaluation
	 *          Norm evaluation information
	 * @return none
	 */
	public void setNormInfo(Integer normId, NormInfoEntityInterface evaluation);
	
	
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
	public Map<Integer, SanctionInfoEntityInterface> getSanctionsInfo(
			Integer normId);
	
	
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