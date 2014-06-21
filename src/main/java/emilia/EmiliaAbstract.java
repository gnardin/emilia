package emilia;

public abstract class EmiliaAbstract {
	
	/**
	 * Input event message
	 * 
	 * @param event
	 *          Event message
	 * @return none
	 */
	public abstract void input(Object event);
	
	
	/**
	 * Get the normative drive
	 * 
	 * @param normId
	 *          Norm identification
	 * @return Normative drive
	 */
	public abstract Double getNormativeDrive(Integer normId);
	
	/**
	 * MISSING SANCTION METHOD
	 */
}