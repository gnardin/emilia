package emilia;

import emilia.defaultImpl.entity.norm.NormContent;
import emilia.defaultImpl.entity.norm.NormEntity;
import emilia.defaultImpl.entity.sanction.SanctionContent;
import emilia.defaultImpl.entity.sanction.SanctionContent.Sanction;
import emilia.defaultImpl.entity.sanction.SanctionEntity;
import emilia.defaultImpl.modules.enforcement.NormEnforcementController;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.NormativeEventType;
import emilia.entity.event.type.ActionEvent;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.NormSource;
import emilia.entity.norm.NormEntityAbstract.NormStatus;
import emilia.entity.norm.NormEntityAbstract.NormType;
import emilia.entity.sanction.SanctionCategory;
import emilia.entity.sanction.SanctionCategory.Discernability;
import emilia.entity.sanction.SanctionCategory.Issuer;
import emilia.entity.sanction.SanctionCategory.Locus;
import emilia.entity.sanction.SanctionCategory.Mode;
import emilia.entity.sanction.SanctionCategory.Polarity;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.entity.sanction.SanctionEntityAbstract.SanctionStatus;
import emilia.modules.EventListener;
import emilia.modules.enforcement.NormEnforcementListener;
import examples.pgg.actions.CooperateAction;
import examples.pgg.actions.DefectAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class EmiliaEnforcementTest {
  
  
  private NormEntityAbstract                                    norm;
  
  private SanctionEntityAbstract                                sanction;
  
  private List<SanctionEntityAbstract>                          sanctions;
  
  private Map<NormEntityAbstract, List<SanctionEntityAbstract>> normSanctions;
  
  
  @Before
  public void constructor() {
    // COOPERATE norm
    Integer normId = 1;
    NormContent normContent = new NormContent(new CooperateAction(),
        new DefectAction());
    this.norm = new NormEntity(normId, NormType.SOCIAL, NormSource.DISTRIBUTED,
        NormStatus.GOAL, normContent, 0.0);
    
    // PUNISHMENT sanction
    Integer sanctionId = 1;
    SanctionContent sanctionContent = new SanctionContent(Sanction.PUNISHMENT,
        new Double(1.5), new Double(3.0));
    SanctionCategory sanctionCategory = new SanctionCategory(Issuer.INFORMAL,
        Locus.OTHER_DIRECTED, Mode.DIRECT, Polarity.NEGATIVE,
        Discernability.UNOBSTRUSIVE);
    this.sanction = new SanctionEntity(sanctionId, sanctionCategory,
        SanctionStatus.ACTIVE, sanctionContent);
    
    // Norm X Sanction
    this.sanctions = new ArrayList<SanctionEntityAbstract>();
    this.sanctions.add(this.sanction);
    
    this.normSanctions = new HashMap<NormEntityAbstract, List<SanctionEntityAbstract>>();
    this.normSanctions.put(this.norm, this.sanctions);
  }
  
  
  @Test
  public void normEnforcementTest() {
    
    NormEnforcementController controller = new NormEnforcementController(1);
    
    EventHandler eventHandler = new EventHandler();
    controller.registerNormEnforcement(eventHandler);
    controller.registerCallback(new ArrayList<NormativeEventType>(Arrays.asList(
        NormativeEventType.COMPLIANCE, NormativeEventType.COMPLIANCE_OBSERVED,
        NormativeEventType.COMPLIANCE_INFORMED, NormativeEventType.VIOLATION,
        NormativeEventType.VIOLATION_OBSERVED,
        NormativeEventType.VIOLATION_INFORMED)), eventHandler);
    
    ActionEvent cooperateEvent = new ActionEvent(
        Calendar.getInstance().getTimeInMillis(), 1, 2, 1,
        new CooperateAction());
    controller.receive(cooperateEvent, this.normSanctions);
    
    ActionEvent defectEvent = new ActionEvent(
        Calendar.getInstance().getTimeInMillis(), 1, 2, 1, new DefectAction());
    controller.receive(defectEvent, this.normSanctions);
  }
}



class EventHandler implements EventListener, NormEnforcementListener {
  
  
  @Override
  public void receive(NormativeEventEntityAbstract event,
      Map<NormEntityAbstract, List<SanctionEntityAbstract>> normSanctions) {
    
    System.out.println("Norm Salience Receive " + event.getType());
    
  }
  
  
  @Override
  public void receive(NormativeEventEntityAbstract entity,
      NormEntityAbstract norm, SanctionEntityAbstract sanction) {
    
    if(sanction.getContent() instanceof SanctionContent) {
      SanctionContent sanctionContent = (SanctionContent) sanction.getContent();
      
      System.out.println("EMILIA Controller receive "
          + sanctionContent.getAction() + " " + sanctionContent.getAmount());
    }
    
  }
}