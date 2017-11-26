package emilia.entity.norm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import emilia.entity.EntityAbstract;

public abstract class NormEntityAbstract extends EntityAbstract
    implements Cloneable {
  
  @SuppressWarnings ( "unused" )
  private static final Logger logger = LoggerFactory
      .getLogger( NormEntityAbstract.class );
  
  public enum NormType {
    LEGAL,
    SOCIAL;
  }
  
  public enum NormSource {
    AUTHORITY,
    DISTRIBUTED;
  }
  
  public enum NormStatus {
    INACTIVE,
    BELIEF,
    GOAL;
  }
  
  protected int                  id;
  
  protected NormType             type;
  
  protected NormSource           source;
  
  protected NormStatus           status;
  
  protected NormContentInterface content;
  
  protected double               salience;
  
  
  /**
   * Get norm identification
   * 
   * @param none
   * @return Norm identification
   */
  public int getId() {
    return this.id;
  }
  
  
  /**
   * Set the norm identification
   * 
   * @param id
   *          Norm identification
   * @return none
   */
  public void setId( int id ) {
    this.id = id;
  }
  
  
  /**
   * Get norm type
   * 
   * @param none
   * @return Norm type
   */
  public NormType getType() {
    return this.type;
  }
  
  
  /**
   * Set norm type
   * 
   * @param type
   *          Norm type
   * @return none
   */
  public void setType( NormType type ) {
    this.type = type;
  }
  
  
  /**
   * Get source type
   * 
   * @param none
   * @return Source type
   */
  public NormSource getSource() {
    return this.source;
  }
  
  
  /**
   * Set source type
   * 
   * @param source
   *          Source type
   * @return none
   */
  public void setSource( NormSource source ) {
    this.source = source;
  }
  
  
  /**
   * Get norm status
   * 
   * @param none
   * @return Norm status
   */
  public NormStatus getStatus() {
    return this.status;
  }
  
  
  /**
   * Set norm status
   * 
   * @param status
   *          Norm status
   * @return none
   */
  public void setStatus( NormStatus status ) {
    this.status = status;
  }
  
  
  /**
   * Get norm content
   * 
   * @param none
   * @return Norm content
   */
  public NormContentInterface getContent() {
    return this.content;
  }
  
  
  /**
   * Set norm content
   * 
   * @param content
   *          Norm content
   * @return none
   */
  public void setContent( NormContentInterface content ) {
    this.content = content;
  }
  
  
  /**
   * Get the norm salience
   * 
   * @param none
   * @return Norm salience
   */
  public double getSalience() {
    return this.salience;
  }
  
  
  /**
   * Set the norm salience
   * 
   * @param salience
   *          Norm salience
   * @return none
   */
  public void setSalience( double salience ) {
    this.salience = salience;
  }
  
  
  @Override
  public boolean equals( Object obj ) {
    boolean result = false;
    
    if ( this == obj ) {
      result = true;
    } else if ( (obj != null) && (obj.getClass() == this.getClass()) ) {
      NormEntityAbstract norm = (NormEntityAbstract) obj;
      if ( this.getId() == norm.getId() ) {
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
  public NormEntityAbstract clone() {
    try {
      return (NormEntityAbstract) super.clone();
    } catch ( CloneNotSupportedException e ) {
      throw new IllegalStateException( e );
    }
  }
}