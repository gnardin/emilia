package examples.ijcai11.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import emilia.entity.action.ActionAbstract;

public class DefectAction extends ActionAbstract {
  
  @SuppressWarnings ( "unused" )
  private static final Logger logger = LoggerFactory
      .getLogger( DefectAction.class );
  
  
  public DefectAction() {
    super( 1, "DEFECT" );
  }
}