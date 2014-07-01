package emilia.test.entity.sanction;

import emilia.entity.sanction.SanctionContentInterface;

public class SanctionContent implements SanctionContentInterface {
	
	public enum Sanction {
		PUNISHMENT;
	}
	
	// Sanction action
	private Sanction	action;
	
	// Sanction punishment amount
	private Double		amount;
	
	
	/**
	 * Create a sanction content
	 * 
	 * @param action
	 *          Sanction action
	 * @param amount
	 *          Sanction amount
	 */
	public SanctionContent(Sanction action, Double amount) {
		this.action = action;
		this.amount = amount;
	}
	
	
	/**
	 * Get sanction action
	 * 
	 * @param none
	 * @return Sanction action
	 */
	public Sanction getAction() {
		return this.action;
	}
	
	
	/**
	 * Get sanction amount
	 * 
	 * @param none
	 * @return Sanction amount
	 */
	public Double getAmount() {
		return this.amount;
	}
	
	
	@Override
	public String toString() {
		return this.action.toString();
	}
}