package emilia.entity.sanction;

public interface SanctionContentInterface {
  
  /**
   * Instantiates a sanction based on the input provided
   * 
   * @param input
   *          Set of parameters to instantiate a sanction
   * @return none
   */
  public void execute( Object input );
  
  
  /**
   * Get the instantiated sanction
   * 
   * @param none
   * @return Instantiated sanction
   */
  public Object getSanction();
}