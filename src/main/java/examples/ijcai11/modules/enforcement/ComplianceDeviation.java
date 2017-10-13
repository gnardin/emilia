package examples.ijcai11.modules.enforcement;

import emilia.modules.enforcement.DeviationAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComplianceDeviation extends DeviationAbstract {
  
  
  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory
      .getLogger(ComplianceDeviation.class);
  
  
  /**
   * Create a compliance deviation
   * 
   * @param none
   * @return none
   */
  public ComplianceDeviation() {
    super(Type.COMPLIANCE);
  }
}