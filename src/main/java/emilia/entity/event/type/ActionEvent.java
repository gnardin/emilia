package emilia.entity.event.type;

import emilia.entity.action.ActionAbstract;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.NormativeEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionEvent extends NormativeEventEntityAbstract {
  
  
  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory
      .getLogger(ActionEvent.class);
  
  // Action performed
  protected ActionAbstract    action;
  
  
  /**
   * Create an Action event entity
   * 
   * @param time
   *          Event time
   * @param sourceId
   *          Agent source of the reported action
   * @param targetId
   *          Agent target of the reported action
   * @param informerId
   *          Agent informing the event
   * @param action
   *          Performed action
   * @return none
   */
  public ActionEvent(double time, int sourceId, int targetId, int informerId,
      ActionAbstract action) {
    super(time, sourceId, targetId, informerId, NormativeEventType.ACTION);
    
    this.action = action;
  }
  
  
  /**
   * Obtain the action performed
   * 
   * @param none
   * @return Performed action
   */
  public ActionAbstract getAction() {
    return this.action;
  }
}