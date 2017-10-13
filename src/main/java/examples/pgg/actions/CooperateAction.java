package examples.pgg.actions;

import emilia.entity.action.ActionAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CooperateAction extends ActionAbstract {
  
  
  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory
      .getLogger(CooperateAction.class);
  
  
  public CooperateAction() {
    super(1, "COOPERATE");
  }
}