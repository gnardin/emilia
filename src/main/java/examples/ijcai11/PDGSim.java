package examples.ijcai11;

import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import emilia.entity.action.ActionAbstract;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.NormSource;
import emilia.entity.norm.NormEntityAbstract.NormStatus;
import emilia.entity.norm.NormEntityAbstract.NormType;
import emilia.entity.sanction.SanctionCategory;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.entity.sanction.SanctionCategory.Discernibility;
import emilia.entity.sanction.SanctionCategory.Locus;
import emilia.entity.sanction.SanctionCategory.Mode;
import emilia.entity.sanction.SanctionCategory.Polarity;
import emilia.entity.sanction.SanctionCategory.Source;
import emilia.entity.sanction.SanctionEntityAbstract.SanctionStatus;
import examples.ijcai11.entity.action.CooperateAction;
import examples.ijcai11.entity.action.DefectAction;
import examples.ijcai11.entity.norm.NormContent;
import examples.ijcai11.entity.norm.NormEntity;
import examples.ijcai11.entity.sanction.SanctionContent;
import examples.ijcai11.entity.sanction.SanctionEntity;
import examples.ijcai11.entity.sanction.SanctionContent.Sanction;
import examples.ijcai11.network.LatticeGraphGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jgrapht.VertexFactory;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.generate.ScaleFreeGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PDGSim {
	
	private static final Logger	logger	= LoggerFactory.getLogger(PDGSim.class);
	
	public enum Network {
		COMPLETE,
		LATTICE,
		SCALE_FREE
	};
	
	// Constants
	public final static Integer								timeSteps				= 10;
	
	public final static Integer								xDim						= 10;
	
	public final static Integer								yDim						= 10;
	
	public final static Integer								numAgents				= xDim * yDim;
	
	public final static Network								network					= Network.SCALE_FREE;
	
	public final static Long									seed						= new Long(1234);
	
	public final static Integer								numNeighbors		= 4;
	
	public final static Integer								pdMatrix[][]		= {{3, 0}, {5, 1}};
	
	public final static Double								costPunish			= 3.0;
	
	public final static Double								costSanction		= 1.5;
	
	public final static Double								costPunisher		= 1.0;
	
	public final static Double								costSanctioner	= 1.0;
	
	public final static Double								initSalience		= 0.1;
	
	public final static String								xmlFile					= "src/main/resources/conf/ijcai11/emilia.xml";
	
	public final static String								xsdFile					= "src/main/resources/conf/emilia.xsd";
	
	// Variables
	public Map<Integer, PDGAgent>							agents;
	
	public Map<Integer, List<Integer>>				neighbors;
	
	private SimpleGraph<Object, DefaultEdge>	mGraph;
	
	public Uniform														rnd;
	
	
	/**
	 * Create a simulation
	 * 
	 * @param none
	 * @return none
	 */
	public PDGSim() {
		this.agents = new HashMap<Integer, PDGAgent>();
		this.neighbors = new HashMap<Integer, List<Integer>>();
		this.mGraph = new SimpleGraph<Object, DefaultEdge>(DefaultEdge.class);
		this.rnd = new Uniform(0.0, 1.0, new MersenneTwister(seed.intValue()));
	}
	
	/**
	 * Create a new vertex in a sequence
	 */
	private VertexFactory<Object>	vertexFactory	= new VertexFactory<Object>() {
																								
																								private int	id	= -1;
																								
																								
																								public Object createVertex() {
																									return new Integer(++this.id);
																								}
																							};
	
	
	/**
	 * Initialize the agents
	 * 
	 * @param none
	 * @return none
	 */
	public void init() {
		NormContent normContent;
		NormEntityAbstract norm;
		
		SanctionContent sanctionContent;
		SanctionCategory sanctionCategory;
		SanctionEntityAbstract sanction;
		Map<NormEntityAbstract, List<SanctionEntityAbstract>> normsSanctions;
		List<SanctionEntityAbstract> sanctions;
		
		// Initialize agents
		PDGAgent agent;
		NormStatus normStatus;
		for(Integer i = 0; i < numAgents; i++) {
			
			// TODO
			// Which agent to activate
			if (this.rnd.nextDouble() < 0.5) {
				normStatus = NormStatus.BELIEF;
			} else {
				normStatus = NormStatus.GOAL;
			}
			logger.debug(normStatus.name());
			
			// COOPERATE norm
			normContent = new NormContent(new CooperateAction(), new DefectAction());
			norm = new NormEntity(1, NormType.SOCIAL, NormSource.DISTRIBUTED,
					normStatus, normContent, initSalience);
			
			sanctions = new ArrayList<SanctionEntityAbstract>();
			
			// PUNISHMENT sanction
			sanctionContent = new SanctionContent(Sanction.PUNISHMENT, costPunish,
					costPunisher);
			sanctionCategory = new SanctionCategory(Source.INFORMAL,
					Locus.OTHER_DIRECTED, Mode.DIRECT, Polarity.NEGATIVE,
					Discernibility.UNOBSTRUSIVE);
			sanction = new SanctionEntity(1, sanctionCategory, SanctionStatus.ACTIVE,
					sanctionContent);
			sanctions.add(sanction);
			
			// SANCTION sanction
			sanctionContent = new SanctionContent(Sanction.SANCTION, costSanction,
					costSanctioner);
			sanctionCategory = new SanctionCategory(Source.FORMAL,
					Locus.OTHER_DIRECTED, Mode.DIRECT, Polarity.NEGATIVE,
					Discernibility.UNOBSTRUSIVE);
			sanction = new SanctionEntity(2, sanctionCategory, SanctionStatus.ACTIVE,
					sanctionContent);
			sanctions.add(sanction);
			
			// MESSAGE sanction
			sanctionContent = new SanctionContent(Sanction.MESSAGE, 0.0, 0.0);
			sanctionCategory = new SanctionCategory(Source.FORMAL,
					Locus.OTHER_DIRECTED, Mode.DIRECT, Polarity.NEGATIVE,
					Discernibility.UNOBSTRUSIVE);
			sanction = new SanctionEntity(3, sanctionCategory, SanctionStatus.ACTIVE,
					sanctionContent);
			sanctions.add(sanction);
			
			// Norms X Sanctions
			normsSanctions = new HashMap<NormEntityAbstract, List<SanctionEntityAbstract>>();
			normsSanctions.put(norm, sanctions);
			
			agent = new PDGAgent(i, xmlFile, xsdFile, normsSanctions, this.rnd,
					costPunish);
			this.agents.put(i, agent);
		}
		
		// Create the networks
		switch(network) {
			case COMPLETE:
				CompleteGraphGenerator<Object, DefaultEdge> complete = new CompleteGraphGenerator<Object, DefaultEdge>(
						numAgents);
				complete.generateGraph(this.mGraph, this.vertexFactory, null);
				break;
			case LATTICE:
				LatticeGraphGenerator<Object, DefaultEdge> lattice = new LatticeGraphGenerator<Object, DefaultEdge>(
						numAgents, numNeighbors);
				
				lattice.generateGraph(this.mGraph, this.vertexFactory, null);
				break;
			case SCALE_FREE:
				ScaleFreeGraphGenerator<Object, DefaultEdge> scaleFree = new ScaleFreeGraphGenerator<Object, DefaultEdge>(
						numAgents, seed);
				scaleFree.generateGraph(this.mGraph, this.vertexFactory, null);
				break;
		}
		
		// Identify the neighbors
		Integer source;
		Integer target;
		List<Integer> neighbor;
		for(Object v : this.mGraph.vertexSet()) {
			source = (Integer) v;
			for(DefaultEdge e : this.mGraph.edgesOf(v)) {
				target = (Integer) this.mGraph.getEdgeSource(e);
				if (target == source) {
					target = (Integer) this.mGraph.getEdgeTarget(e);
				}
				
				if (this.neighbors.containsKey(source)) {
					neighbor = this.neighbors.get(source);
				} else {
					neighbor = new ArrayList<Integer>();
				}
				
				if (!neighbor.contains(target)) {
					neighbor.add(target);
				}
				
				this.neighbors.put(source, neighbor);
			}
		}
	}
	
	
	/**
	 * Run the simulation
	 * 
	 * @param none
	 * @return none
	 */
	public void run() {
		PDGAgent agent;
		
		Map<Integer, ActionAbstract> actions;
		ActionAbstract action;
		
		Map<Integer, Map<Integer, SanctionEntityAbstract>> punishments;
		Map<Integer, SanctionEntityAbstract> punishment;
		
		Integer source;
		Integer target;
		Integer index;
		Boolean found;
		NeighborIndex<Object, DefaultEdge> ni;
		List<Object> neighbors;
		List<Integer> neighbor;
		Map<Integer, Integer> paired;
		
		Map<Integer, ActionAbstract> nActions;
		Map<Integer, Map<Integer, ActionAbstract>> neighborActions;
		
		ActionAbstract actionS;
		ActionAbstract actionT;
		Map<Integer, ActionAbstract> playersActions;
		
		// Run the simulation by a certain number of timesteps
		for(Integer r = 0; r < timeSteps; r++) {
			actions = new HashMap<Integer, ActionAbstract>();
			punishments = new HashMap<Integer, Map<Integer, SanctionEntityAbstract>>();
			
			logger.debug("");
			logger.debug("------ ITERATION " + (r + 1) + " ------");
			logger.debug("");
			
			// Paired Selection
			paired = new HashMap<Integer, Integer>();
			
			ni = new NeighborIndex<Object, DefaultEdge>(this.mGraph);
			for(Object v : this.mGraph.vertexSet()) {
				source = (Integer) v;
				
				if ((!paired.containsKey(source)) && (!paired.containsValue(source))) {
					found = false;
					neighbors = ni.neighborListOf(v);
					neighbor = new ArrayList<Integer>();
					while((!found) && (neighbor.size() < neighbors.size())) {
						index = this.rnd.nextIntFromTo(0, neighbors.size() - 1);
						target = (Integer) neighbors.get(index);
						
						if ((!paired.containsKey(target))
								&& (!paired.containsValue(target))) {
							paired.put(source, target);
							found = true;
						}
						
						if (!neighbor.contains(target)) {
							neighbor.add(target);
						}
					}
				}
			}
			
			for(Integer i : paired.keySet()) {
				logger.debug(i + " " + paired.get(i));
			}
			
			// First Stage - Play PDG
			for(Integer i = 0; i < numAgents; i++) {
				agent = this.agents.get(i);
				agent.init();
				action = agent.decideAction();
				actions.put(i, action);
				
				logger.debug("AgentId [" + i + "] ACTION [" + action.getDescription()
						+ "]");
			}
			logger.debug("");
			
			// Neighbors actions
			neighborActions = new HashMap<Integer, Map<Integer, ActionAbstract>>();
			for(Integer i = 0; i < numAgents; i++) {
				neighbors = ni.neighborListOf(i);
				nActions = new HashMap<Integer, ActionAbstract>();
				for(Object n : neighbors) {
					if (actions.containsKey((Integer) n)) {
						nActions.put((Integer) n, actions.get((Integer) n));
					}
				}
				neighborActions.put(i, nActions);
			}
			
			// Set payoff
			for(Integer s : paired.keySet()) {
				source = s;
				actionS = actions.get(source);
				target = paired.get(source);
				actionT = actions.get(target);
				
				// Source and Target actions
				playersActions = new HashMap<Integer, ActionAbstract>();
				playersActions.put(target, actionT);
				playersActions.put(source, actionS);
				
				// Source agent
				agent = this.agents.get(source);
				agent.setPayoff(new Double(pdMatrix[actionS.getId()][actionT.getId()]));
				agent.updateActions(target, neighborActions.get(source));
				
				// Target agent
				agent = this.agents.get(target);
				agent.setPayoff(new Double(pdMatrix[actionT.getId()][actionS.getId()]));
				agent.updateActions(source, neighborActions.get(target));
				
				logger.debug(source + " " + target + " " + actionS.getId() + " "
						+ actionT.getId() + " "
						+ pdMatrix[actionS.getId()][actionT.getId()] + " "
						+ pdMatrix[actionT.getId()][actionS.getId()]);
			}
			
			// Second Stage
			for(Integer agentId = 0; agentId < numAgents; agentId++) {
				agent = this.agents.get(agentId);
				punishment = agent.decidePunish();
				punishments.put(agentId, punishment);
			}
			
			// Punish
			for(Integer agentId = 0; agentId < numAgents; agentId++) {
				agent = this.agents.get(agentId);
				agent.updatePunishment(punishments, ni.neighborListOf(agentId));
			}
			
			// Update Strategy
			for(Integer agentId = 0; agentId < numAgents; agentId++) {
				agent = this.agents.get(agentId);
				agent.strategyUpdate();
			}
			
			// Payoff
			logger.debug("");
			for(Integer i = 0; i < numAgents; i++) {
				agent = this.agents.get(i);
				logger.debug("AgentId [" + i + "] SALIENCE [" + agent.getSalience()
						+ "]");
			}
		}
	}
	
	
	/**
	 * Main
	 * 
	 * @param none
	 * @return none
	 */
	public static void main(String[] args) {
		PDGSim sim = new PDGSim();
		sim.init();
		sim.run();
	}
}