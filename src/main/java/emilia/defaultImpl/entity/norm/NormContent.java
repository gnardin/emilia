package emilia.defaultImpl.entity.norm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import emilia.entity.action.ActionAbstract;
import emilia.entity.norm.NormContentInterface;

public class NormContent implements NormContentInterface {
  
  @SuppressWarnings ( "unused" )
  private static final Logger logger = LoggerFactory
      .getLogger( NormContent.class );
  
  // Action
  private ActionAbstract      action;
  
  // Negated action
  private ActionAbstract      notAction;
  
  
  /**
   * Create a norm content
   * 
   * @param action
   *          Action
   * @param notAction
   *          Negated action
   * @return none
   */
  public NormContent( ActionAbstract action, ActionAbstract notAction ) {
    this.action = action;
    this.notAction = notAction;
  }
  
  
  /**
   * Get action
   * 
   * @param none
   * @return Action
   */
  public ActionAbstract getAction() {
    return this.action;
  }
  
  
  /**
   * Get negated action
   * 
   * @param none
   * @return Negated action
   */
  public ActionAbstract getNotAction() {
    return this.notAction;
  }
  
  
  @Override
  public boolean match( Object value ) {
    
    if ( value instanceof String ) {
      if ( (this.action.getDescription().equalsIgnoreCase( (String) value ))
          || (this.notAction.getDescription()
              .equalsIgnoreCase( (String) value )) ) {
        return true;
      }
    }
    
    return false;
  }
  
  
  @Override
  public boolean comply( Object value ) {
    
    if ( value instanceof String ) {
      if ( this.action.getDescription().equalsIgnoreCase( (String) value ) ) {
        return true;
      }
    }
    
    return false;
  }
}