package emilia.modules.salience;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NormInfoRepositoryMemory implements NormInfoRepositoryInterface {
  
  @SuppressWarnings ( "unused" )
  private static final Logger            logger = LoggerFactory
      .getLogger( NormInfoRepositoryMemory.class );
  
  // <NormId, Normative Information>
  protected Map<Integer, NormInfoEntity> normativeInfoRep;
  
  
  /**
   * Create a normative information repository into memory
   * 
   * @param none
   * @return none
   */
  public NormInfoRepositoryMemory() {
    this.normativeInfoRep = new HashMap<Integer, NormInfoEntity>();
  }
  
  
  @Override
  public int getNormInfo( int normId, DataType dataType ) {
    Integer result = 0;
    
    if ( this.normativeInfoRep.containsKey( normId ) ) {
      NormInfoEntity normInfoEntity = this.normativeInfoRep.get( normId );
      result = normInfoEntity.getValue( dataType );
    }
    
    return result;
  }
  
  
  @Override
  public void setNormInfo( int normId, DataType dataType, int value ) {
    
    NormInfoEntity normInfoEntity;
    if ( this.normativeInfoRep.containsKey( normId ) ) {
      normInfoEntity = this.normativeInfoRep.get( normId );
    } else {
      normInfoEntity = new NormInfoEntity();
    }
    
    normInfoEntity.setValue( dataType, value );
    this.normativeInfoRep.put( normId, normInfoEntity );
  }
  
  
  @Override
  public void increment( int normId, DataType dataType ) {
    
    NormInfoEntity normInfoEntity;
    if ( this.normativeInfoRep.containsKey( normId ) ) {
      normInfoEntity = this.normativeInfoRep.get( normId );
    } else {
      normInfoEntity = new NormInfoEntity();
    }
    
    normInfoEntity.increment( dataType, 1 );
    this.normativeInfoRep.put( normId, normInfoEntity );
  }
  
  
  @Override
  public void increment( int normId, DataType dataType, int value ) {
    
    NormInfoEntity normInfoEntity;
    if ( this.normativeInfoRep.containsKey( normId ) ) {
      normInfoEntity = this.normativeInfoRep.get( normId );
    } else {
      normInfoEntity = new NormInfoEntity();
    }
    
    normInfoEntity.increment( dataType, value );
    this.normativeInfoRep.put( normId, normInfoEntity );
  }
}