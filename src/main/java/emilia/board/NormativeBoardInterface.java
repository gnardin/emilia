package emilia.board;

import emilia.entity.norm.NormEntityAbstract;
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
	 * @param info
	 *          Information to match
	 * @return List of norms that match the information
	 */
	public abstract List<NormEntityAbstract> match(Object info);
	
	
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
	 * Register a callback method
	 * 
	 * @param types
	 *          List of normative event type
	 * @param normListener
	 *          Method to be called
	 * @return none
	 */
	public void registerCallback(List<NormativeEventType> types,
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
	public void unregisterCallback(List<NormativeEventType> types,
			NormativeBoardListener normListener);
}