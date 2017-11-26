package emilia.modules.enforcement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DeviationAbstract {
  
  @SuppressWarnings ( "unused" )
  private static final Logger logger = LoggerFactory
      .getLogger( DeviationAbstract.class );
  
  public enum Type {
    COMPLIANCE,
    VIOLATION;
  }
  
  // Deviation type
  protected Type type;
  
  
  /**
   * Create a deviation
   * 
   * @param type
   *          Type of deviation
   * @return none
   */
  public DeviationAbstract( Type type ) {
    this.type = type;
  }
  
  
  /**
   * Get deviation type
   * 
   * @param none
   * @return Deviation type
   */
  public Type getType() {
    return this.type;
  }
}