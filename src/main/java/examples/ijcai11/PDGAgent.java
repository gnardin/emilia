package examples.ijcai11;

import cern.jet.random.Uniform;
import emilia.entity.action.ActionAbstract;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.NormativeEventType;
import emilia.entity.event.type.ActionEvent;
import emilia.entity.event.type.NormativeEvent;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.NormStatus;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.enforcement.NormEnforcementListener;
import examples.ijcai11.entity.action.CooperateAction;
import examples.ijcai11.entity.action.DefectAction;
import examples.ijcai11.entity.norm.NormEntity;
import examples.ijcai11.entity.sanction.SanctionContent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PDGAgent implements NormEnforcementListener {
	
	private static final Logger										logger			= LoggerFactory
																																.getLogger(PDGAgent.class);
	
	// Constants
	private final static ActionAbstract						COOPERATE		= new CooperateAction();
	
	private final static ActionAbstract						DEFECT			= new DefectAction();
	
	private final static String										EVALUATE		= "evaluate";
	
	private final static Integer									TOLERANCE		= 1;
	
	private final static Double										DELTA_COST	= 0.1;
	
	private final static Double										INERTIA			= 0.05;
	
	private final static Double										MAX_REWARD	= 5.0;
	
	private final static Double										MIN_REWARD	= 1.0;
	
	private final static Double										NOISE				= 0.0;
	
	private final static Integer									NORMID			= 1;
	
	// Variables
	private Integer																agentId;
	
	private EmiliaControllerPDG										normative;
	
	private ActionAbstract												action;
	
	private ActionAbstract												lastAction;
	
	private Double																payoff;
	
	private Double																lastPayoff;
	
	private Uniform																rnd;
	
	private Map<Integer, SanctionEntityAbstract>	punishments;
	
	private Map<Integer, ActionAbstract>					neighborsAction;
	
	private Double																selfInterestedDrive;
	
	private Double																normativeDrive;
	
	
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
	public PDGAgent(Integer agentId, String xmlFile, String xsdFile,
			Map<NormEntityAbstract, List<SanctionEntityAbstract>> normsSanctions,
			Uniform rnd, Double costPunish) {
		this.agentId = agentId;
		this.normative = new EmiliaControllerPDG(agentId, xmlFile, xsdFile,
				TOLERANCE, DELTA_COST);
		this.normative.registerNormEnforcement(this);
		this.normative.addNormsSanctions(normsSanctions);
		this.rnd = rnd;
		
		this.punishments = new HashMap<Integer, SanctionEntityAbstract>();
		this.neighborsAction = new HashMap<Integer, ActionAbstract>();
		
		if (this.rnd.nextIntFromTo(0, 1) == 0) {
			this.action = COOPERATE;
		} else {
			this.action = DEFECT;
		}
		
		this.payoff = 0.0;
		
		this.selfInterestedDrive = 0.5;
		this.normativeDrive = 0.0;
		
		this.init();
	}
	
	
	/**
	 * Get agent identification
	 * 
	 * @param none
	 * @return Agent identification
	 */
	public Integer getId() {
		return this.agentId;
	}
	
	
	/**
	 * Initialization
	 * 
	 * @param none
	 * @return none
	 */
	public void init() {
		this.lastAction = this.action;
		this.lastPayoff = this.payoff;
		this.payoff = 0.0;
		this.punishments.clear();
		this.neighborsAction.clear();
		this.normative.update();
	}
	
	
	/**
	 * Get Salience
	 * 
	 * @param none
	 * @return Salience
	 */
	public Double getSalience() {
		return this.normative.getNormSalience(NORMID);
	}
	
	
	/**
	 * Get payoff
	 * 
	 * @param none
	 * @return Payoff
	 */
	public Double getPayoff() {
		return this.payoff;
	}
	
	
	/**
	 * Set payoff
	 * 
	 * @param payoff
	 *          Round payoff
	 * @return none
	 */
	public void setPayoff(Double payoff) {
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
		if (this.rnd.nextDouble() > NOISE) {
			Double probCooperate = this.selfInterestedDrive;
			
			// Include NID
			NormEntity norm = (NormEntity) this.normative.getNorm(NORMID);
			if ((norm != null) && (norm.getStatus().equals(NormStatus.GOAL))) {
				probCooperate += this.normativeDrive;
			}
			
			probCooperate = Math.max(0.0, Math.min(probCooperate, 1.0));
			
			// Decide action
			if (this.rnd.nextDouble() < probCooperate) {
				this.action = COOPERATE;
			} else {
				this.action = DEFECT;
			}
		} else {
			// Random choice
			if (this.rnd.nextIntFromTo(0, 1) == 0) {
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
	 * @param playersActions
	 *          List of the two players actions
	 * @param neighborsActions
	 *          List of neighbors actions
	 * @return none
	 */
	public void updateActions(Integer player,
			Map<Integer, ActionAbstract> neighborsAction) {
		
		List<NormativeEventEntityAbstract> eventList = new ArrayList<NormativeEventEntityAbstract>();
		
		this.neighborsAction = neighborsAction;
		
		ActionAbstract act;
		ActionEvent actEvent;
		for(Integer neighborId : this.neighborsAction.keySet()) {
			act = this.neighborsAction.get(neighborId);
			
			if (!player.equals(neighborId)) {
				if (act.equals(COOPERATE)) {
					actEvent = new ActionEvent(Calendar.getInstance().getTimeInMillis(),
							neighborId, this.agentId, this.agentId, COOPERATE);
				} else {
					actEvent = new ActionEvent(Calendar.getInstance().getTimeInMillis(),
							neighborId, this.agentId, this.agentId, DEFECT);
				}
				
				eventList.add(actEvent);
			}
		}
		
		if (this.neighborsAction.containsKey(player)) {
			act = this.neighborsAction.get(player);
			
			if (act.equals(COOPERATE)) {
				actEvent = new ActionEvent(Calendar.getInstance().getTimeInMillis(),
						player, this.agentId, this.agentId, COOPERATE);
			} else {
				actEvent = new ActionEvent(Calendar.getInstance().getTimeInMillis(),
						player, this.agentId, this.agentId, DEFECT);
			}
			
			actEvent.setContextAttribute(EVALUATE, new Boolean(true));
			eventList.add(actEvent);
		}
		
		// Process all the events
		for(NormativeEventEntityAbstract event : eventList) {
			this.normative.input(event);
		}
	}
	
	
	/**
	 * Decide to punish
	 * 
	 * @param none
	 * @return List of punishments
	 */
	public Map<Integer, SanctionEntityAbstract> decidePunish() {
		
		if (this.action.equals(COOPERATE)) {
			SanctionEntityAbstract sanction;
			SanctionContent sanctionContent;
			for(Integer neighborId : this.punishments.keySet()) {
				sanction = this.punishments.get(neighborId);
				sanctionContent = (SanctionContent) sanction.getContent();
				this.payoff -= sanctionContent.getCost();
			}
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
			Map<Integer, Map<Integer, SanctionEntityAbstract>> punishments,
			List<Object> neighbors) {
		NormativeEventEntityAbstract normativeEvent;
		Map<Integer, SanctionEntityAbstract> punish;
		SanctionEntityAbstract sanction;
		SanctionContent sanctionContent;
		
		List<Integer> agentList = new ArrayList<Integer>();
		agentList.add(this.agentId);
		for(Object agentId : neighbors) {
			agentList.add((Integer) agentId);
		}
		
		logger.debug(this.agentId + " " + agentList);
		
		for(Integer punisher : agentList) {
			if (punishments.containsKey(punisher)) {
				punish = punishments.get(punisher);
				
				for(Integer punished : punish.keySet()) {
					normativeEvent = null;
					sanction = punish.get(punished);
					sanctionContent = (SanctionContent) sanction.getContent();
					
					// PUNISHER
					if (punisher == this.agentId) {
						switch(sanctionContent.getAction()) {
							case PUNISHMENT:
								normativeEvent = new NormativeEvent(Calendar.getInstance()
										.getTimeInMillis(), punisher, punished, punisher,
										NormativeEventType.PUNISHMENT, NORMID);
								break;
							case SANCTION:
								normativeEvent = new NormativeEvent(Calendar.getInstance()
										.getTimeInMillis(), punisher, punished, punisher,
										NormativeEventType.SANCTION, NORMID);
								break;
							case MESSAGE:
								normativeEvent = new NormativeEvent(Calendar.getInstance()
										.getTimeInMillis(), punisher, punished, punisher,
										NormativeEventType.COMPLIANCE_INVOCATION, NORMID);
								break;
						}
						
					} else {
						// PUNISHED
						if (punished == this.agentId) {
							
							switch(sanctionContent.getAction()) {
								case PUNISHMENT:
									normativeEvent = new NormativeEvent(Calendar.getInstance()
											.getTimeInMillis(), punished, punisher, punisher,
											NormativeEventType.PUNISHMENT, NORMID);
									break;
								case SANCTION:
									normativeEvent = new NormativeEvent(Calendar.getInstance()
											.getTimeInMillis(), punished, punisher, punisher,
											NormativeEventType.SANCTION, NORMID);
									break;
								case MESSAGE:
									normativeEvent = new NormativeEvent(Calendar.getInstance()
											.getTimeInMillis(), punished, punisher, punisher,
											NormativeEventType.COMPLIANCE_INVOCATION, NORMID);
									break;
							}
							
							this.payoff -= sanctionContent.getAmount();
							
							// OBSERVED
						} else {
							
							switch(sanctionContent.getAction()) {
								case PUNISHMENT:
									normativeEvent = new NormativeEvent(Calendar.getInstance()
											.getTimeInMillis(), punished, punished, punished,
											NormativeEventType.PUNISHMENT_OBSERVED, NORMID);
									break;
								case SANCTION:
									normativeEvent = new NormativeEvent(Calendar.getInstance()
											.getTimeInMillis(), punished, punished, punished,
											NormativeEventType.SANCTION_OBSERVED, NORMID);
									break;
								case MESSAGE:
									normativeEvent = new NormativeEvent(Calendar.getInstance()
											.getTimeInMillis(), punished, punished, punished,
											NormativeEventType.COMPLIANCE_INVOCATION_OBSERVED, NORMID);
									break;
							}
						}
					}
					
					if (normativeEvent != null) {
						this.normative.input(normativeEvent);
					}
				}
			}
		}
	}
	
	
	/**
	 * Update the drivers that guides the agent's decision-making
	 * 
	 * @param none
	 * @return none
	 */
	public void strategyUpdate() {
		
		Integer act;
		if (this.action.equals(COOPERATE)) {
			act = 1;
		} else {
			act = -1;
		}
		
		if ((this.payoff - this.lastPayoff) == 0) {
			this.selfInterestedDrive += act
					* ((this.payoff - this.lastPayoff) / (MAX_REWARD - MIN_REWARD));
		} else {
			if (this.lastAction.equals(COOPERATE)) {
				act = 1;
			} else {
				act = -1;
			}
			
			this.selfInterestedDrive += act * INERTIA;
		}
		
		this.selfInterestedDrive = Math.min(1,
				Math.max(0, this.selfInterestedDrive));
		
		this.normativeDrive = this.normative.getNormSalience(NORMID);
	}
	
	
	@Override
	public void receive(NormativeEventEntityAbstract event,
			NormEntityAbstract norm, SanctionEntityAbstract sanction) {
		
		if (this.action == COOPERATE) {
			logger.debug("PUNISHER [" + event.getTarget() + "] PUNISHED ["
					+ event.getSource() + "] ENFORCEMENT ["
					+ sanction.getContent().toString() + "]");
			this.punishments.put(event.getSource(), sanction);
		}
	}
}