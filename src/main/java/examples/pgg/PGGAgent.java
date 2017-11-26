package examples.pgg;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cern.jet.random.Uniform;
import emilia.EmiliaController;
import emilia.defaultImpl.entity.sanction.SanctionContent;
import emilia.entity.action.ActionAbstract;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.NormativeEventType;
import emilia.entity.event.type.ActionEvent;
import emilia.entity.event.type.NormativeEvent;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.enforcement.NormEnforcementListener;
import examples.pgg.actions.CooperateAction;
import examples.pgg.actions.DefectAction;

public class PGGAgent implements NormEnforcementListener {
  
  @SuppressWarnings ( "unused" )
  private static final Logger                  logger    = LoggerFactory
      .getLogger( PGGAgent.class );
  
  // Constants
  private final static ActionAbstract          COOPERATE = new CooperateAction();
  
  private final static ActionAbstract          DEFECT    = new DefectAction();
  
  private final static double                  NOISE     = 0.05;
  
  private final static int                     normId    = 1;
  
  // Variables
  private int                                  agentId;
  
  private EmiliaController                     normative;
  
  private ActionAbstract                       action;
  
  private double                               payoff;
  
  private Uniform                              rnd;
  
  private Map<Integer, SanctionEntityAbstract> punishments;
  
  private Map<Integer, ActionAbstract>         neighborsActions;
  
  
  /**
   * Create an agent
   * 
   * @param agentId
   *          Agent identification
   * @param xmlFile
   *          XML file
   * @param xsdFile
   *          XSD file
   * @param normsSanctions
   *          Norms and associated sanctions
   * @param rnd
   *          Random generator
   * @return none
   */
  public PGGAgent( int agentId, String xmlFile, String xsdFile, Map<NormEntityAbstract, List<SanctionEntityAbstract>> normsSanctions, Uniform rnd, double costPunish ) {
    this.agentId = agentId;
    this.normative = new EmiliaController( agentId, xmlFile, xsdFile );
    this.normative.init();
    this.normative.registerNormEnforcement( this );
    this.normative.addNormsSanctions( normsSanctions );
    this.rnd = rnd;
    
    this.punishments = new HashMap<Integer, SanctionEntityAbstract>();
    this.neighborsActions = new HashMap<Integer, ActionAbstract>();
    
    this.init();
  }
  
  
  /**
   * Get agent identification
   * 
   * @param none
   * @return Agent identification
   */
  public int getId() {
    return this.agentId;
  }
  
  
  /**
   * Initialization
   * 
   * @param none
   * @return none
   */
  public void init() {
    this.payoff = 0.0;
    this.punishments.clear();
    this.neighborsActions.clear();
    this.normative.update();
  }
  
  
  /**
   * Get Salience
   * 
   * @param none
   * @return Salience
   */
  public double getSalience() {
    return this.normative.getNormSalience( normId );
  }
  
  
  /**
   * Get payoff
   * 
   * @param none
   * @return Payoff
   */
  public double getPayoff() {
    return this.payoff;
  }
  
  
  /**
   * Set payoff
   * 
   * @param payoff
   *          Round payoff
   * @return none
   */
  public void setPayoff( double payoff ) {
    this.payoff = payoff;
  }
  
  
  /**
   * Decide to cooperate or defect
   * 
   * @param none
   * @return Cooperation or Defect action
   */
  public ActionAbstract decideAction() {
    
    // Cooperation probability
    if ( this.rnd.nextDouble() > NOISE ) {
      if ( this.rnd.nextDouble() < this.normative.getNormSalience( normId ) ) {
        this.action = COOPERATE;
      } else {
        this.action = DEFECT;
      }
    } else {
      if ( this.rnd.nextIntFromTo( 0, 1 ) == 0 ) {
        this.action = COOPERATE;
      } else {
        this.action = DEFECT;
      }
    }
    
    return this.action;
  }
  
  
  /**
   * Update the agent with neighbors actions
   * 
   * @param neighborsActions
   *          List of neighbors actions
   * @return none
   */
  public void updateActions( Map<Integer, ActionAbstract> neighborsActions ) {
    this.neighborsActions = neighborsActions;
    
    for ( Integer neighborId : neighborsActions.keySet() ) {
      ActionAbstract act = neighborsActions.get( neighborId );
      
      NormativeEventEntityAbstract eventEntity;
      if ( act.equals( COOPERATE ) ) {
        eventEntity = new ActionEvent( Calendar.getInstance().getTimeInMillis(),
            neighborId, this.agentId, this.agentId, COOPERATE );
      } else {
        eventEntity = new ActionEvent( Calendar.getInstance().getTimeInMillis(),
            neighborId, this.agentId, this.agentId, DEFECT );
      }
      
      this.normative.input( eventEntity );
    }
  }
  
  
  /**
   * Decide to punish
   * 
   * @param none
   * @return List of punishments
   */
  public Map<Integer, SanctionEntityAbstract> decidePunish() {
    
    if ( this.action.equals( COOPERATE ) ) {
      for ( Integer neighborId : this.punishments.keySet() ) {
        SanctionEntityAbstract sanction = this.punishments.get( neighborId );
        SanctionContent sanctionContent = (SanctionContent) sanction
            .getContent();
        this.payoff -= sanctionContent.getCost();
      }
    } else {
      this.punishments = new HashMap<Integer, SanctionEntityAbstract>();
    }
    
    return this.punishments;
  }
  
  
  /**
   * Update payoff based on others punishments
   * 
   * @param punishments
   *          Received and observed punishments <Punished, <Punisher,
   *          Punishment>>
   * @return none
   */
  public void updatePunishment(
      Map<Integer, Map<Integer, SanctionEntityAbstract>> punishments ) {
    
    for ( Integer punished : punishments.keySet() ) {
      Map<Integer, SanctionEntityAbstract> punish = punishments.get( punished );
      
      for ( Integer punisher : punish.keySet() ) {
        NormativeEventEntityAbstract normativeEvent = null;
        SanctionEntityAbstract sanction = punish.get( punisher );
        SanctionContent sanctionContent = (SanctionContent) sanction
            .getContent();
        
        // Affected
        if ( punished == this.agentId ) {
          
          switch ( sanctionContent.getAction() ) {
            case PUNISHMENT:
              normativeEvent = new NormativeEvent(
                  Calendar.getInstance().getTimeInMillis(), punisher, punished,
                  punished, NormativeEventType.PUNISHMENT, normId );
              break;
            case SANCTION:
              normativeEvent = new NormativeEvent(
                  Calendar.getInstance().getTimeInMillis(), punisher, punished,
                  punished, NormativeEventType.SANCTION, normId );
              break;
            case MESSAGE:
              break;
          }
          
          this.payoff -= sanctionContent.getAmount();
          
        } else {
          // Observed
          if ( punisher == this.agentId ) {
            
            switch ( sanctionContent.getAction() ) {
              case PUNISHMENT:
                normativeEvent = new NormativeEvent(
                    Calendar.getInstance().getTimeInMillis(), punisher,
                    punished, punished, NormativeEventType.PUNISHMENT_OBSERVED,
                    normId );
                break;
              case SANCTION:
                normativeEvent = new NormativeEvent(
                    Calendar.getInstance().getTimeInMillis(), punisher,
                    punished, punished, NormativeEventType.SANCTION_OBSERVED,
                    normId );
                break;
              case MESSAGE:
                break;
            }
            
            // Informed
          } else {
            
            switch ( sanctionContent.getAction() ) {
              case PUNISHMENT:
                normativeEvent = new NormativeEvent(
                    Calendar.getInstance().getTimeInMillis(), punisher,
                    punished, punisher, NormativeEventType.PUNISHMENT_INFORMED,
                    normId );
                break;
              case SANCTION:
                normativeEvent = new NormativeEvent(
                    Calendar.getInstance().getTimeInMillis(), punisher,
                    punished, punisher, NormativeEventType.SANCTION_INFORMED,
                    normId );
                break;
              case MESSAGE:
                break;
            }
          }
        }
        
        this.normative.input( normativeEvent );
      }
    }
  }
  
  
  @Override
  public void receive( NormativeEventEntityAbstract event,
      NormEntityAbstract norm, SanctionEntityAbstract sanction ) {
    
    if ( this.action == COOPERATE ) {
      System.out.println(
          "PUNISHER [" + event.getTarget() + "] PUNISHED [" + event.getSource()
              + "] ENFORCEMENT [" + sanction.getContent().toString() + "]" );
      this.punishments.put( event.getSource(), sanction );
    }
  }
}