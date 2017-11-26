package emilia.entity.sanction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SanctionCategory {
  
  @SuppressWarnings ( "unused" )
  private static final Logger logger = LoggerFactory
      .getLogger( SanctionCategory.class );
  
  // Source types
  public enum Issuer {
    FORMAL,
    INFORMAL;
  }
  
  // Locus types
  public enum Locus {
    SELF_DIRECTED,
    OTHER_DIRECTED;
  }
  
  // Mode types
  public enum Mode {
    DIRECT,
    INDIRECT;
  }
  
  // Polarity types
  public enum Polarity {
    POSITIVE,
    NEGATIVE;
  }
  
  // Discernability types
  public enum Discernability {
    OBSTRUSIVE,
    UNOBSTRUSIVE;
  }
  
  // Source type
  private Issuer         source;
  
  // Locus type
  private Locus          locus;
  
  // Mode type
  private Mode           mode;
  
  // Polarity type
  private Polarity       polarity;
  
  // Discernibility type
  private Discernability discernibility;
  
  
  /**
   * Create a sanction classification
   * 
   * @param source
   *          Source type
   * @param locus
   *          Locus type
   * @param mode
   *          Mode type
   * @param polarity
   *          Polarity type
   * @param discernibility
   *          Discernibility type
   * @return none
   */
  public SanctionCategory( Issuer source, Locus locus, Mode mode, Polarity polarity, Discernability discernibility ) {
    this.source = source;
    this.locus = locus;
    this.mode = mode;
    this.polarity = polarity;
    this.discernibility = discernibility;
  }
  
  
  /**
   * Get source
   * 
   * @param none
   * @return Source
   */
  public Issuer getSource() {
    return this.source;
  }
  
  
  /**
   * Get locus
   * 
   * @param none
   * @return Locus
   */
  public Locus getLocus() {
    return this.locus;
  }
  
  
  /**
   * Get mode
   * 
   * @param none
   * @return Mode
   */
  public Mode getMode() {
    return this.mode;
  }
  
  
  /**
   * Get polarity
   * 
   * @param none
   * @return Polarity
   */
  public Polarity getPolarity() {
    return this.polarity;
  }
  
  
  /**
   * Get discernibility
   * 
   * @param none
   * @return Discernibility
   */
  public Discernability getDiscernibility() {
    return this.discernibility;
  }
  
  
  /**
   * Get sanction category
   * 
   * @param none
   * @return Numeric category
   */
  public int getCategory() {
    int category = source.ordinal() - 1;
    
    if ( (locus.ordinal() - 1) > 0 ) {
      category += (int) Math.pow( 2, 1 );
    }
    
    if ( (mode.ordinal() - 1) > 0 ) {
      category += (int) Math.pow( 2, 2 );
    }
    
    if ( (polarity.ordinal() - 1) > 0 ) {
      category += (int) Math.pow( 2, 3 );
    }
    
    if ( (discernibility.ordinal() - 1) > 0 ) {
      category += (int) Math.pow( 2, 4 );
    }
    
    return category;
  }
  
  
  @Override
  public boolean equals( Object obj ) {
    boolean result = false;
    
    if ( this == obj )
      return true;
    
    if ( (obj != null) && (this.getClass() == obj.getClass()) ) {
      SanctionCategory other = (SanctionCategory) obj;
      
      result = ((this.source.equals( other.source ))
          && (this.locus.equals( other.locus ))
          && (this.mode.equals( other.mode ))
          && (this.polarity.equals( other.polarity ))
          && (this.discernibility.equals( other.discernibility )));
    }
    
    return result;
  }
  
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    
    result = prime * result + (this.source.hashCode());
    result = prime * result + (this.locus.hashCode());
    result = prime * result + (this.mode.hashCode());
    result = prime * result + (this.polarity.hashCode());
    result = prime * result + (this.discernibility.hashCode());
    
    return result;
  }
  
  
  @Override
  public SanctionCategory clone() {
    try {
      return (SanctionCategory) super.clone();
    } catch ( CloneNotSupportedException e ) {
      throw new IllegalStateException( e );
    }
  }
}