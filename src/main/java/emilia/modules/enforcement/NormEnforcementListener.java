package emilia.modules.enforcement;

import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.sanction.SanctionEntityAbstract;

public interface NormEnforcementListener {
  
  
  /**
   * Submit a sanction to the listener
   * 
   * @param event
   *          Normative event entity
   * @param norm
   *          Norm entity
   * @param sanction
   *          Sanction entity
   * @return none
   */
  public void receive(NormativeEventEntityAbstract entity,
      NormEntityAbstract norm, SanctionEntityAbstract sanction);
}