package emilia;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import emilia.board.NormativeBoardEventType;
import emilia.board.NormativeBoardInterface;
import emilia.defaultImpl.board.NormativeBoard;
import emilia.defaultImpl.modules.adoption.NormAdoptionController;
import emilia.entity.norm.NormContentInterface;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.NormSource;
import emilia.entity.norm.NormEntityAbstract.NormStatus;
import emilia.entity.norm.NormEntityAbstract.NormType;
import emilia.modules.adoption.NormAdoptionAbstract;

public class EmiliaAdoptionTest {
  
  NormativeBoardInterface normtiveBoard;
  
  NormAdoptionAbstract    normAdoption;
  
  
  @Before
  public void constructor() {
    this.normtiveBoard = new NormativeBoard();
    this.normAdoption = new NormAdoptionController( 1, this.normtiveBoard );
    
    this.normtiveBoard.registerCallback(
        new ArrayList<NormativeBoardEventType>(
            Arrays.asList( NormativeBoardEventType.INSERT_NORM,
                NormativeBoardEventType.UPDATE_SALIENCE ) ),
        this.normAdoption );
  }
  
  
  @Test
  public void normAdoptionTest() {
    NormContent content = new NormContent( "COOPERATE" );
    NormEntity norm = new NormEntity( 1, NormType.SOCIAL,
        NormSource.DISTRIBUTED, NormStatus.BELIEF, content );
    this.normtiveBoard.setNorm( norm );
    
    assertEquals( this.normtiveBoard.getNorm( 1 ).getStatus(),
        NormStatus.GOAL );
  }
}



class NormEntity extends NormEntityAbstract {
  
  public NormEntity( Integer id, NormType type, NormSource source, NormStatus status, NormContentInterface content ) {
    this.setId( id );
    this.setType( type );
    this.setSource( source );
    this.setStatus( status );
    this.setContent( content );
  }
}



class NormContent implements NormContentInterface {
  
  private String content;
  
  
  public NormContent( String content ) {
    this.content = content;
  }
  
  
  @Override
  public boolean match( Object content ) {
    
    if ( content instanceof String ) {
      if ( this.content.equals( (String) content ) ) {
        return true;
      }
    }
    
    return false;
  }
  
  
  @Override
  public boolean comply( Object content ) {
    boolean comply = false;
    
    if ( content instanceof String ) {
      if ( this.content.equalsIgnoreCase( (String) content ) ) {
        comply = true;
      }
    }
    
    return comply;
  }
  
  
  @Override
  public String toString() {
    return this.content;
  }
}