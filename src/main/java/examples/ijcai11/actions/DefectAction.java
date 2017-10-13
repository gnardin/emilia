package examples.ijcai11.actions;

import emilia.entity.action.ActionAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefectAction extends ActionAbstract {
  
  
  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory
      .getLogger(DefectAction.class);
  
  
  public DefectAction() {
    super(1, "DEFECT");
  }
}