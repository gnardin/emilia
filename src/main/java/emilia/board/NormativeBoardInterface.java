package emilia.board;

import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.sanction.SanctionEntityAbstract;
import java.util.List;
import java.util.Map;

public interface NormativeBoardInterface {
  
  
  /**
   * Get norm
   * 
   * @param normId
   *          Norm identification
   * @return Norm entity
   */
  public NormEntityAbstract getNorm(int normId);
  
  
  /**
   * Get all norms
   * 
   * @param none
   * @return List of existing norms
   */
  public List<NormEntityAbstract> getNorms();
  
  
  /**
   * Add or set a norm
   * 
   * @param norm
   *          Norm entity
   * @return none
   */
  public void setNorm(NormEntityAbstract norm);
  
  
  /**
   * Remove a norm
   * 
   * @param normId
   *          Norm identification
   * @return none
   */
  public void removeNorm(int normId);
  
  
  /**
   * Exist the norm
   * 
   * @param normId
   *          Norm identification
   * @return True if norm exists, False otherwise
   */
  public boolean hasNorm(int normId);
  
  
  /**
   * Match norms
   * 
   * @param content
   *          Content to match with norms
   * @return List of norms that match the content
   */
  public abstract List<NormEntityAbstract> match(Object content);
  
  
  /**
   * Get norm salience
   * 
   * @param normId
   *          Norm identification
   * @return Norm salience
   */
  public double getSalience(int normId);
  
  
  /**
   * Set norm salience
   * 
   * @param normId
   *          Norm identification
   * @param salience
   *          Norm salience
   * @return none
   */
  public void setSalience(int normId, double salience);
  
  
  /**
   * Get sanction
   * 
   * @param sanctionId
   *          Sanction identification
   * @return Sanction entity
   */
  public SanctionEntityAbstract getSanction(int sanctionId);
  
  
  /**
   * Get all sanctions
   * 
   * @param none
   * @return List of existing sanctions
   */
  public List<SanctionEntityAbstract> getSanctions();
  
  
  /**
   * Add or set a sanction
   * 
   * @param sanction
   *          Sanction entity
   * @return none
   */
  public void setSanction(SanctionEntityAbstract sanction);
  
  
  /**
   * Remove a sanction
   * 
   * @param sanctionId
   *          Sanction identification
   * @return none
   */
  public void removeSanction(int sanctionId);
  
  
  /**
   * Exist the sanction
   * 
   * @param sanctionId
   *          Sanction identification
   * @return True if sanction exists, False otherwise
   */
  public boolean hasSanction(int sanctionId);
  
  
  /**
   * Get sanctions associated to the norm
   * 
   * @param normId
   *          Norm identification
   * @return List of sanctions entities associated to the norm
   */
  public List<SanctionEntityAbstract> getSanctions(int normId);
  
  
  /**
   * Add or set a sanction association to the norm
   * 
   * @param normId
   *          Norm identification
   * @param sanctionId
   *          Sanction identification
   * @return none
   */
  public void setNormSanction(int normId, int sanctionId);
  
  
  /**
   * Remove a sanction association to the norm
   * 
   * @param normId
   *          Norm identification
   * @param sanctionId
   *          Sanction identification
   * @return none
   */
  public void removeNormSanction(int normId, int sanctionId);
  
  
  /**
   * Exist the sanction association to the norm
   * 
   * @param normId
   *          Norm identification
   * @param sanctionId
   *          Sanction identification
   * @return True if association exists, False otherwise
   */
  public boolean hasNormSanction(int normId, int sanctionId);
  
  
  /**
   * Get sanctions identification associated to the norm
   * 
   * @param normId
   *          Norm identification
   * @return List of sanctions associated to the norm
   */
  public List<Integer> getNormSanctions(int normId);
  
  
  /**
   * Add norms and associated sanctions
   * 
   * @param normsSanctions
   *          Norms and associated sanctions
   * @return none
   */
  public abstract void addNormsSanctions(
      Map<NormEntityAbstract, List<SanctionEntityAbstract>> normsSanctions);
  
  
  /**
   * Get norms and associated sanctions
   * 
   * @param none
   * @return Norms and associated sanctions
   */
  public abstract Map<NormEntityAbstract, List<SanctionEntityAbstract>>
      getNormsSanctions();
  
  
  /**
   * Update the norms and associated sanctions
   * 
   * @param normsSanctions
   *          Norms and associated sanctions
   * @return none
   */
  public abstract void updateNormsSanctions(
      Map<NormEntityAbstract, List<SanctionEntityAbstract>> normsSanctions);
  
  
  /**
   * Register a callback method
   * 
   * @param types
   *          List of normative event type
   * @param normListener
   *          Method to be called
   * @return none
   */
  public void registerCallback(List<NormativeBoardEventType> types,
      NormativeBoardListener normListener);
  
  
  /**
   * Unregister a callback method
   * 
   * @param types
   *          List of normative event type
   * @param normListener
   *          Method to be called
   * @return none
   */
  public void unregisterCallback(List<NormativeBoardEventType> types,
      NormativeBoardListener normListener);
}