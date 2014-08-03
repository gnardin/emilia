package examples.ijcai11.modules.enforcement;

import emilia.modules.enforcement.NormInfoEntityInterface;

public class NormInfoEntityContent implements NormInfoEntityInterface {
	
	private Integer	numDefectors;
	
	
	/**
	 * Create the norm information entity content
	 * 
	 * @param none
	 * @return none
	 */
	public NormInfoEntityContent() {
		this.numDefectors = 0;
	}
	
	
	/**
	 * Get the number of defectors
	 * 
	 * @param none
	 * @return Number of defectors
	 */
	public Integer getDefectors() {
		return this.numDefectors;
	}
	
	
	/**
	 * Set the number of defectors
	 * 
	 * @param numDefectors
	 *          Number of defectors
	 * @return none
	 */
	public void setDefectors(Integer numDefectors) {
		this.numDefectors = numDefectors;
	}
}