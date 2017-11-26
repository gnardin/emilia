package examples.ijcai11;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import emilia.EmiliaController;
import emilia.board.NormativeBoardInterface;
import emilia.entity.event.NormativeEventType;
import emilia.modules.enforcement.NormEnforcementAbstract;
import examples.ijcai11.modules.recognition.NormRecognitionController;

public class EmiliaControllerPDG extends EmiliaController {
  
  @SuppressWarnings ( "unused" )
  private static final Logger logger = LoggerFactory
      .getLogger( EmiliaControllerPDG.class );
  
  private int                 tolerance;
  
  private double              delta_cost;
  
  
  public EmiliaControllerPDG( int agentId, String xmlFilename, String xsdFilename, int tolerance, double delta_cost ) {
    super( agentId, xmlFilename, xsdFilename );
    
    this.tolerance = tolerance;
    this.delta_cost = delta_cost;
    
    this.init();
    
    this.normEnforcement.registerCallback(
        new ArrayList<NormativeEventType>(
            Arrays.asList( NormativeEventType.COMPLIANCE,
                NormativeEventType.COMPLIANCE_OBSERVED,
                NormativeEventType.COMPLIANCE_INFORMED ) ),
        (NormRecognitionController) this.normRecognition );
  }
  
  
  @Override
  protected void setNormEnforcement( String normEnforcementClass ) {
    try {
      @SuppressWarnings ( "unchecked" )
      Class<NormEnforcementAbstract> neClass = (Class<NormEnforcementAbstract>) Class
          .forName( normEnforcementClass );
      
      Constructor<NormEnforcementAbstract> neConstructor = neClass
          .getDeclaredConstructor( Integer.class, Integer.class, Double.class,
              NormativeBoardInterface.class );
      
      this.normEnforcement = neConstructor.newInstance( this.agentId,
          this.tolerance, this.delta_cost, this.normativeBoard );
      
    } catch ( ClassNotFoundException e ) {
      e.printStackTrace();
    } catch ( NoSuchMethodException e ) {
      e.printStackTrace();
    } catch ( InvocationTargetException e ) {
      e.printStackTrace();
    } catch ( IllegalAccessException e ) {
      e.printStackTrace();
    } catch ( InstantiationException e ) {
      e.printStackTrace();
    }
  }
}