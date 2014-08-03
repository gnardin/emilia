package examples.ijcai11.entity.sanction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import emilia.entity.sanction.SanctionContentInterface;

public class SanctionContent implements SanctionContentInterface {
	
	@SuppressWarnings("unused")
	private static final Logger	logger	= LoggerFactory
																					.getLogger(SanctionContent.class);
	
	public enum Sanction {
		MESSAGE,
		PUNISHMENT,
		SANCTION;
	}
	
	// Sanction action
	private Sanction	action;
	
	// Sanction cost
	private Double		cost;
	
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
	public SanctionContent(Sanction action, Double cost, Double amount) {
		this.action = action;
		this.cost = cost;
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
	 * Get sanction cost
	 * 
	 * @param none
	 * @return Sanction cost
	 */
	public Double getCost() {
		return this.cost;
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