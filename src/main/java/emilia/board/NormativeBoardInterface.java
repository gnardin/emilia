package emilia.board;

import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.sanction.SanctionEntityAbstract;
import java.util.List;

public interface NormativeBoardInterface {
	
	/**
	 * Get norm
	 * 
	 * @param normId
	 *          Norm identification
	 * @return Norm entity
	 */
	public NormEntityAbstract getNorm(Integer normId);
	
	
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
	public void removeNorm(Integer normId);
	
	
	/**
	 * Exist the norm
	 * 
	 * @param normId
	 *          Norm identification
	 * @return True if norm exists, False otherwise
	 */
	public Boolean hasNorm(Integer normId);
	
	
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
	public Double getSalience(Integer normId);
	
	
	/**
	 * Set norm salience
	 * 
	 * @param normId
	 *          Norm identification
	 * @param salience
	 *          Norm salience
	 * @return none
	 */
	public void setSalience(Integer normId, Double salience);
	
	
	/**
	 * Get sanction
	 * 
	 * @param sanctionId
	 *          Sanction identification
	 * @return Sanction entity
	 */
	public SanctionEntityAbstract getSanction(Integer sanctionId);
	
	
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
	public void removeSanction(Integer sanctionId);
	
	
	/**
	 * Exist the sanction
	 * 
	 * @param sanctionId
	 *          Sanction identification
	 * @return True if sanction exists, False otherwise
	 */
	public Boolean hasSanction(Integer sanctionId);
	
	
	/**
	 * Get sanctions associated to the norm
	 * 
	 * @param normId
	 *          Norm identification
	 * @return List of sanctions entities associated to the norm
	 */
	public List<SanctionEntityAbstract> getSanctions(Integer normId);
	
	
	/**
	 * Get sanctions identification associated to the norm
	 * 
	 * @param normId
	 *          Norm identification
	 * @return List of sanctions associated to the norm
	 */
	public List<Integer> getNormSanctions(Integer normId);
	
	
	/**
	 * Add or set a sanction association to the norm
	 * 
	 * @param normId
	 *          Norm identification
	 * @param sanctionId
	 *          Sanction identification
	 * @return none
	 */
	public void setNormSanction(Integer normId, Integer sanctionId);
	
	
	/**
	 * Remove a sanction association to the norm
	 * 
	 * @param normId
	 *          Norm identification
	 * @param sanctionId
	 *          Sanction identification
	 * @return none
	 */
	public void removeNormSanction(Integer normId, Integer sanctionId);
	
	
	/**
	 * Exist the sanction association to the norm
	 * 
	 * @param normId
	 *          Norm identification
	 * @param sanctionId
	 *          Sanction identification
	 * @return True if association exists, False otherwise
	 */
	public Boolean hasNormSanction(Integer normId, Integer sanctionId);
	
	
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