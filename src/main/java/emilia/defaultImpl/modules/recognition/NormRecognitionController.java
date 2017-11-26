package emilia.defaultImpl.modules.recognition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import emilia.board.NormativeBoardInterface;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.type.ActionEvent;
import emilia.entity.event.type.NormativeEvent;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.recognition.NormRecognitionAbstract;

public class NormRecognitionController extends NormRecognitionAbstract {
  
  @SuppressWarnings ( "unused" )
  private static final Logger logger = LoggerFactory
      .getLogger( NormRecognitionController.class );
  
  
  public NormRecognitionController( Integer agentId, NormativeBoardInterface normativeBoard ) {
    super( agentId, normativeBoard );
  }
  
  
  @Override
  public void matchEvent( NormativeEventEntityAbstract event ) {
    List<NormEntityAbstract> norms = null;
    
    this.recognizeNorm( event );
    this.recognizeSanction( event );
    
    if ( event instanceof ActionEvent ) {
      String action = ((ActionEvent) event).getAction().getDescription();
      norms = this.normativeBoard.match( action );
    } else if ( event instanceof NormativeEvent ) {
      int normId = ((NormativeEvent) event).getNormId();
      norms = this.normativeBoard.match( normId );
    }
    
    if ( norms != null ) {
      Map<NormEntityAbstract, List<SanctionEntityAbstract>> normSanctions = new HashMap<NormEntityAbstract, List<SanctionEntityAbstract>>();
      
      for ( NormEntityAbstract norm : norms ) {
        List<SanctionEntityAbstract> sanctions = this.normativeBoard
            .getSanctions( norm.getId() );
        normSanctions.put( norm, sanctions );
      }
      if ( normSanctions.size() > 0 ) {
        this.processEvent( event, normSanctions );
      }
    }
  }
  
  
  @Override
  public void recognizeNorm( NormativeEventEntityAbstract event ) {
    // Intended to do nothing in this implementation
  }
  
  
  @Override
  public void recognizeSanction( NormativeEventEntityAbstract event ) {
    // Intended to do nothing in this implementation
  }
}