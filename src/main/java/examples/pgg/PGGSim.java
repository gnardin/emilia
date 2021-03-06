package examples.pgg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import emilia.defaultImpl.entity.norm.NormContent;
import emilia.defaultImpl.entity.norm.NormEntity;
import emilia.defaultImpl.entity.sanction.SanctionContent;
import emilia.defaultImpl.entity.sanction.SanctionContent.Sanction;
import emilia.defaultImpl.entity.sanction.SanctionEntity;
import emilia.entity.action.ActionAbstract;
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
import examples.pgg.actions.CooperateAction;
import examples.pgg.actions.DefectAction;

public class PGGSim {
  
  @SuppressWarnings ( "unused" )
  private static final Logger   logger         = LoggerFactory
      .getLogger( PGGSim.class );
  
  // Constants
  public final static int       numAgents      = 10;
  
  public final static int       numIteractions = 100000;
  
  public final static int       contribution   = 20;
  
  public final static double    costPunisher   = 3.0;
  
  public final static double    costSanctioner = 4.0;
  
  public final static double    costPunish     = 1.0;
  
  public final static double    costSanction   = 1.5;
  
  public final static double    multiplier     = 1.3;
  
  public final static double    initSalience   = 0.5;
  
  public final static String    xmlFile        = "src/main/resources/conf/pgg/emilia.xml";
  
  public final static String    xsdFile        = "src/main/resources/conf/emilia.xsd";
  
  // Variables
  public Map<Integer, PGGAgent> agents;
  
  public Uniform                rnd;
  
  
  /**
   * Create a simulation
   * 
   * @param none
   * @return none
   */
  public PGGSim() {
    this.agents = new HashMap<Integer, PGGAgent>();
    this.rnd = new Uniform( 0.0, 1.0, new MersenneTwister() );
  }
  
  
  /**
   * Initialize the agents
   * 
   * @param none
   * @return none
   */
  public void init() {
    
    // Initialize agents
    for ( Integer i = 0; i < numAgents; i++ ) {
      // COOPERATE norm
      
      NormContent normContent = new NormContent( new CooperateAction(),
          new DefectAction() );
      NormEntityAbstract norm = new NormEntity( 1, NormType.SOCIAL,
          NormSource.DISTRIBUTED, NormStatus.GOAL, normContent, initSalience );
      
      List<SanctionEntityAbstract> sanctions = new ArrayList<SanctionEntityAbstract>();
      
      // PUNISHMENT sanction
      SanctionContent sanctionContent = new SanctionContent(
          Sanction.PUNISHMENT, costPunish, costPunisher );
      SanctionCategory sanctionCategory = new SanctionCategory( Issuer.INFORMAL,
          Locus.OTHER_DIRECTED, Mode.DIRECT, Polarity.NEGATIVE,
          Discernability.UNOBSTRUSIVE );
      SanctionEntityAbstract sanction = new SanctionEntity( 1, sanctionCategory,
          SanctionStatus.ACTIVE, sanctionContent );
      sanctions.add( sanction );
      
      // SANCTION sanction
      sanctionContent = new SanctionContent( Sanction.SANCTION, costSanction,
          costSanctioner );
      sanctionCategory = new SanctionCategory( Issuer.FORMAL,
          Locus.OTHER_DIRECTED, Mode.DIRECT, Polarity.NEGATIVE,
          Discernability.UNOBSTRUSIVE );
      sanction = new SanctionEntity( 2, sanctionCategory, SanctionStatus.ACTIVE,
          sanctionContent );
      sanctions.add( sanction );
      
      // Norms X Sanctions
      Map<NormEntityAbstract, List<SanctionEntityAbstract>> normsSanctions = new HashMap<NormEntityAbstract, List<SanctionEntityAbstract>>();
      normsSanctions.put( norm, sanctions );
      
      PGGAgent agent = new PGGAgent( i, xmlFile, xsdFile, normsSanctions,
          this.rnd, costPunish );
      this.agents.put( i, agent );
    }
  }
  
  
  /**
   * Run the simulation
   * 
   * @param none
   * @return none
   */
  public void run() {
    
    for ( Integer r = 0; r < numIteractions; r++ ) {
      Map<Integer, ActionAbstract> actions = new HashMap<Integer, ActionAbstract>();
      Map<Integer, Map<Integer, SanctionEntityAbstract>> punishments = new HashMap<Integer, Map<Integer, SanctionEntityAbstract>>();
      
      System.out.println();
      System.out.println( "------ ITERATION " + (r + 1) + " ------" );
      System.out.println();
      
      // Play PD
      double payoff = 0.0;
      for ( Integer i = 0; i < numAgents; i++ ) {
        PGGAgent agent = this.agents.get( i );
        agent.init();
        ActionAbstract action = agent.decideAction();
        if ( action instanceof CooperateAction ) {
          payoff += contribution;
        }
        actions.put( i, action );
        
        System.out.println(
            "AgentId [" + i + "] ACTION [" + action.getDescription() + "]" );
      }
      System.out.println();
      
      // Set payoff
      payoff *= multiplier;
      payoff /= numAgents;
      for ( Integer i = 0; i < numAgents; i++ ) {
        PGGAgent agent = this.agents.get( i );
        agent.setPayoff( payoff );
        
        // Update agents about everyone's action
        agent.updateActions( actions );
      }
      
      // Get punishments
      for ( Integer punisher = 0; punisher < numAgents; punisher++ ) {
        PGGAgent agent = this.agents.get( punisher );
        Map<Integer, SanctionEntityAbstract> punishment = agent.decidePunish();
        
        for ( Integer punished : punishment.keySet() ) {
          Map<Integer, SanctionEntityAbstract> punish;
          if ( punishments.containsKey( punished ) ) {
            punish = punishments.get( punished );
          } else {
            punish = new HashMap<Integer, SanctionEntityAbstract>();
          }
          
          punish.put( punisher, punishment.get( punished ) );
          punishments.put( punished, punish );
        }
      }
      
      // Punish
      for ( Integer i = 0; i < numAgents; i++ ) {
        PGGAgent agent = this.agents.get( i );
        agent.updatePunishment( punishments );
      }
      
      // Payoff
      System.out.println();
      for ( Integer i = 0; i < numAgents; i++ ) {
        PGGAgent agent = this.agents.get( i );
        System.out.println(
            "AgentId [" + i + "] SALIENCE [" + agent.getSalience() + "]" );
      }
    }
  }
  
  
  /**
   * Main
   * 
   * @param none
   * @return none
   */
  public static void main( String[] args ) {
    PGGSim sim = new PGGSim();
    sim.init();
    sim.run();
  }
}