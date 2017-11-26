package emilia.defaultImpl.entity.sanction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import emilia.entity.sanction.SanctionContentInterface;

public class SanctionContent implements SanctionContentInterface {
  
  @SuppressWarnings ( "unused" )
  private static final Logger logger = LoggerFactory
      .getLogger( SanctionContent.class );
  
  public enum Sanction {
    PUNISHMENT,
    SANCTION,
    MESSAGE;
  }
  
  // Sanction action
  private Sanction action;
  
  // Sanction cost
  private double   cost;
  
  // Sanction punishment amount
  private double   amount;
  
  
  /**
   * Create a sanction content
   * 
   * @param action
   *          Sanction action
   * @param cost
   *          Sanction application cost
   * @param amount
   *          Sanction amount
   */
  public SanctionContent( Sanction action, double cost, double amount ) {
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
  public double getCost() {
    return this.cost;
  }
  
  
  /**
   * Get sanction amount
   * 
   * @param none
   * @return Sanction amount
   */
  public double getAmount() {
    return this.amount;
  }
  
  
  @Override
  public void execute( Object input ) {
  }
  
  
  @Override
  public Object getSanction() {
    return null;
  }
  
  
  @Override
  public String toString() {
    return this.action.toString();
  }
}