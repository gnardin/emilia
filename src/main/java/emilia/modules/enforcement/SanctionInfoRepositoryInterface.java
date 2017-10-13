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
  public boolean hasNormInfo(int normId);
  
  
  /**
   * Get norm evaluation information
   * 
   * @param normId
   *          Norm identification
   * @return Norm evaluation information
   */
  public NormInfoEntityInterface getNormInfo(int normId);
  
  
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
  public void setNormInfo(int normId, NormInfoEntityInterface evaluation);
  
  
  /**
   * Get sanction evaluation information
   * 
   * @param normId
   *          Norm identification
   * @param sanctionId
   *          Sanction identification
   * @return Norm x sanction evaluation information
   */
  public SanctionInfoEntityInterface getSanctionInfo(int normId,
      int sanctionId);
  
  
  /**
   * Get sanctions evaluation information
   * 
   * @param normId
   *          Norm identification
   * @return Sanctions evaluation information
   */
  public Map<Integer, SanctionInfoEntityInterface> getSanctionsInfo(int normId);
  
  
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
  public void setSanctionInfo(int normId, int sanctionId,
      SanctionInfoEntityInterface evaluation);
}