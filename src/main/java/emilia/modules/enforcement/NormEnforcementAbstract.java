package emilia.modules.enforcement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.NormativeEventType;
import emilia.entity.event.type.NormativeEvent;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.EventListener;

public abstract class NormEnforcementAbstract implements EventListener {
  
  @SuppressWarnings ( "unused" )
  private static final Logger                            logger = LoggerFactory
      .getLogger( NormEnforcementAbstract.class );
  
  // Agent identification
  protected int                                          agentId;
  
  // Enforcement memory
  protected SanctionInfoRepositoryMemory                 memory;
  
  // Norm Salience callbacks
  protected Map<NormativeEventType, List<EventListener>> callbacks;
  
  // Norm Enforcement callback
  protected NormEnforcementListener                      callback;
  
  
  /**
   * Create a norm enforcement
   * 
   * @param none
   * @return none
   */
  public NormEnforcementAbstract( Integer agentId ) {
    this.agentId = agentId;
    this.memory = new SanctionInfoRepositoryMemory();
    this.callbacks = new HashMap<NormativeEventType, List<EventListener>>();
    this.callback = null;
  }
  
  
  /**
   * Register a callback method
   * 
   * @param types
   *          List of normative event type
   * @param eventListener
   *          Method to be called
   * @return none
   */
  public void registerCallback( List<NormativeEventType> types,
      EventListener eventListener ) {
    
    List<EventListener> eventListeners;
    for ( NormativeEventType type : types ) {
      
      if ( this.callbacks.containsKey( type ) ) {
        eventListeners = this.callbacks.get( type );
      } else {
        eventListeners = new ArrayList<EventListener>();
      }
      
      eventListeners.add( eventListener );
      this.callbacks.put( type, eventListeners );
    }
  }
  
  
  /**
   * Unregister a callback method
   * 
   * @param types
   *          List of normative event type
   * @param eventListener
   *          Method to be called
   * @return none
   */
  public void unregisterCallback( List<NormativeEventType> types,
      EventListener eventListener ) {
    
    for ( NormativeEventType type : types ) {
      if ( this.callbacks.containsKey( type ) ) {
        List<EventListener> eventListeners = this.callbacks.get( type );
        
        if ( eventListeners.contains( eventListener ) ) {
          eventListeners.remove( eventListener );
          this.callbacks.put( type, eventListeners );
        }
      }
    }
  }
  
  
  /**
   * Register to receive sanction information
   * 
   * @param NormEnforcementListener
   *          Method to be called
   * @return none
   */
  public void registerNormEnforcement( NormEnforcementListener listener ) {
    this.callback = listener;
  }
  
  
  /**
   * Unregister to receive sanction information
   * 
   * @param none
   * @return none
   */
  public void unregisterNormEnforcement() {
    this.callback = null;
  }
  
  
  @Override
  public void receive( NormativeEventEntityAbstract event,
      Map<NormEntityAbstract, List<SanctionEntityAbstract>> normSanctions ) {
    this.normEnforcementController( event, normSanctions );
  }
  
  
  /**
   * Control the sequence of events inside the Norm Enforcement module
   * 
   * @param event
   *          Normative event
   * @param normSanctions
   *          Norms and associated sanctions related to the event
   * @return none
   */
  private synchronized void normEnforcementController(
      NormativeEventEntityAbstract event,
      Map<NormEntityAbstract, List<SanctionEntityAbstract>> normSanctions ) {
    
    // Detect the status of each norm
    Map<NormEntityAbstract, DeviationAbstract> normDeviations = this
        .detect( event, normSanctions );
    
    // Send messages to callbacks
    
    for ( NormEntityAbstract norm : normDeviations.keySet() ) {
      DeviationAbstract deviation = normDeviations.get( norm );
      NormativeEvent normativeEvent = null;
      
      NormativeEventType type;
      if ( deviation.getType().equals( DeviationAbstract.Type.COMPLIANCE ) ) {
        
        if ( event.getSource() == this.agentId ) {
          type = NormativeEventType.COMPLIANCE;
        } else {
          if ( event.getInformer() == this.agentId ) {
            type = NormativeEventType.COMPLIANCE_OBSERVED;
          } else {
            type = NormativeEventType.COMPLIANCE_INFORMED;
          }
        }
        
        normativeEvent = new NormativeEvent( event.getTime(), event.getSource(),
            event.getTarget(), event.getInformer(), type, norm.getId() );
        
      } else if ( deviation.getType()
          .equals( DeviationAbstract.Type.VIOLATION ) ) {
        
        if ( event.getSource() == this.agentId ) {
          type = NormativeEventType.VIOLATION;
        } else {
          if ( event.getInformer() == this.agentId ) {
            type = NormativeEventType.VIOLATION_OBSERVED;
          } else {
            type = NormativeEventType.VIOLATION_INFORMED;
          }
        }
        
        normativeEvent = new NormativeEvent( event.getTime(), event.getSource(),
            event.getTarget(), event.getInformer(), type, norm.getId() );
      }
      
      if ( (normativeEvent != null)
          && (this.callbacks.containsKey( normativeEvent.getType() )) ) {
        List<EventListener> listeners = this.callbacks
            .get( normativeEvent.getType() );
        
        for ( EventListener listener : listeners ) {
          listener.receive( normativeEvent, normSanctions );
        }
      }
    }
    
    // Avoid sanction its own actions
    if ( event.getSource() != event.getTarget() ) {
      for ( NormEntityAbstract norm : normDeviations.keySet() ) {
        // Sanction Effectiveness Updater
        this.adapt( event, norm, normDeviations.get( norm ) );
        
        List<SanctionEntityAbstract> possibleSanctions = normSanctions
            .get( norm );
        if ( !possibleSanctions.isEmpty() ) {
          // Sanction Identified
          List<SanctionEntityAbstract> sanctions = this.evaluate( event, norm,
              possibleSanctions, normDeviations.get( norm ) );
          
          // Sanction Issuer
          this.enforce( event, norm, sanctions );
          
          for ( SanctionEntityAbstract sanction : sanctions ) {
            this.callback.receive( event, norm, sanction );
          }
        }
      }
    }
  }
  
  
  /**
   * 
   * @param event
   *          Normative event
   * @param normSanctions
   *          Norms and associated sanctions
   * @return List of norms and respective event deviation
   */
  public abstract Map<NormEntityAbstract, DeviationAbstract> detect(
      NormativeEventEntityAbstract event,
      Map<NormEntityAbstract, List<SanctionEntityAbstract>> normSanctions );
  
  
  /**
   * Adjust the Sanction effectiveness
   * 
   * @param event
   *          Normative event evaluated
   * @param norm
   *          Norm evaluated
   * @param evaluation
   *          Deviation evaluation
   * @return none
   */
  public abstract void adapt( NormativeEventEntityAbstract event,
      NormEntityAbstract norm, DeviationAbstract evaluation );
  
  
  /**
   * Evaluate the most adequate sanction to apply
   * 
   * @param event
   *          Normative event
   * @param norm
   *          Norm evaluated
   * @param sanctions
   *          List of possible sanctions
   * @param evaluation
   *          Deviation evaluation
   * @return List of sanctions selected for application
   */
  public abstract List<SanctionEntityAbstract> evaluate(
      NormativeEventEntityAbstract event, NormEntityAbstract norm,
      List<SanctionEntityAbstract> sanctions, DeviationAbstract evaluation );
  
  
  /**
   * Enforce the sanction
   * 
   * @param event
   *          Normative event
   * @param norm
   *          Norm evaluated
   * @param sanctions
   *          List of sanctions to apply because of norm compliance or violation
   * @return none
   */
  public abstract void enforce( NormativeEventEntityAbstract event,
      NormEntityAbstract norm, List<SanctionEntityAbstract> sanctions );
  
  
  /**
   * Update the norm enforcement module
   * 
   * @param none
   * @return none
   */
  public abstract void update();
}