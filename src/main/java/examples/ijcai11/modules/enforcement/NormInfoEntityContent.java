package examples.ijcai11.modules.enforcement;

import emilia.modules.enforcement.NormInfoEntityInterface;

public class NormInfoEntityContent implements NormInfoEntityInterface {
  
  
  private int numCompliants;
  
  private int numDefectors;
  
  
  /**
   * Create the norm information entity content
   * 
   * @param none
   * @return none
   */
  public NormInfoEntityContent() {
    this.numCompliants = 0;
    this.numDefectors = 0;
  }
  
  
  /**
   * Get the number of compliants
   * 
   * @param none
   * @return Number of compliants
   */
  public int getCompliants() {
    return this.numCompliants;
  }
  
  
  /**
   * Set the number of compliants
   * 
   * @param numCompliants
   *          Number of compliants
   * @return none
   */
  public void setCompliants(int numCompliants) {
    this.numCompliants = numCompliants;
  }
  
  
  /**
   * Get the number of defectors
   * 
   * @param none
   * @return Number of defectors
   */
  public int getDefectors() {
    return this.numDefectors;
  }
  
  
  /**
   * Set the number of defectors
   * 
   * @param numDefectors
   *          Number of defectors
   * @return none
   */
  public void setDefectors(int numDefectors) {
    this.numDefectors = numDefectors;
  }
  
  
  /**
   * Reset the content
   * 
   * @param none
   * @return none
   */
  public void reset() {
    this.numCompliants = 0;
    this.numDefectors = 0;
  }
}