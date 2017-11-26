package emilia.entity.action;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ActionAbstract {
  
  @SuppressWarnings ( "unused" )
  private static final Logger   logger = LoggerFactory
      .getLogger( ActionAbstract.class );
  
  // Action identification
  protected int                 id;
  
  // Action description
  protected String              description;
  
  // Action parameters
  protected Map<Object, Object> params;
  
  
  /**
   * Create an action without parameters
   * 
   * @param id
   *          Action identification
   * @param description
   *          Action description
   * @return none
   */
  public ActionAbstract( int id, String description ) {
    this.id = id;
    this.description = description;
    this.params = null;
  }
  
  
  /**
   * Create an action with parameters
   * 
   * @param id
   *          Action identification
   * @param description
   *          Action description
   * @param params
   *          Action parameters
   * @return none
   */
  public ActionAbstract( int id, String description, Map<Object, Object> params ) {
    this.id = id;
    this.description = description;
    this.params = params;
  }
  
  
  /**
   * Get the action identification
   * 
   * @param none
   * @return Action identification
   */
  public int getId() {
    return this.id;
  }
  
  
  /**
   * Get the action description
   * 
   * @param none
   * @return Action description
   */
  public String getDescription() {
    return this.description;
  }
  
  
  /**
   * Get action parameters
   * 
   * @param none
   * @return Action parameters
   */
  public Map<Object, Object> getParams() {
    return this.params;
  }
  
  
  /**
   * Get a specific action parameter value
   * 
   * @param index
   *          Parameter index. It can be a String or an Integer.
   * @return Parameter value
   */
  public Object getParam( Object index ) {
    Object param = null;
    
    if ( (this.params != null) && (index != null) ) {
      param = this.params.get( index );
    }
    
    return param;
  }
  
  
  /**
   * Set a specific action parameter value
   * 
   * @param index
   *          Parameter index. It can be a String or an Integer.
   * @param param
   *          Parameter value
   * @return none
   */
  public void setParam( Object index, Object param ) {
    if ( (this.params != null) && (index != null) ) {
      this.params.put( index, param );
    }
  }
  
  
  @Override
  public boolean equals( Object obj ) {
    boolean result = false;
    
    if ( this == obj ) {
      result = true;
    } else if ( (obj != null) && (obj.getClass() == this.getClass()) ) {
      ActionAbstract action = (ActionAbstract) obj;
      if ( this.id == action.getId() ) {
        result = true;
      }
    }
    
    return result;
  }
  
  
  @Override
  public int hashCode() {
    int hash = 217 + this.id;
    return hash;
  }
  
  
  @Override
  public String toString() {
    String str = new String();
    
    str += this.id + " " + this.description + " ";
    
    if ( this.params != null ) {
      for ( Object param : this.params.keySet() ) {
        Object value = this.params.get( param ).toString();
        
        str += value.toString() + " ";
      }
      
      str = str.substring( 0, (str.length() - 1) );
    }
    
    return str;
  }
}