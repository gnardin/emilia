package examples.ijcai11;

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
import emilia.entity.sanction.SanctionCategory.Locus;
import emilia.entity.sanction.SanctionCategory.Mode;
import emilia.entity.sanction.SanctionCategory.Polarity;
import emilia.entity.sanction.SanctionCategory.Issuer;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.entity.sanction.SanctionEntityAbstract.SanctionStatus;
import examples.ijcai11.actions.CooperateAction;
import examples.ijcai11.actions.DefectAction;
import examples.ijcai11.network.LatticeGraphGenerator;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
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
	public final static int										timeSteps				= 10;
	
	public final static int										xDim						= 10;
	
	public final static int										yDim						= 10;
	
	public final static int										numAgents				= xDim * yDim;
	
	public final static Network								network					= Network.SCALE_FREE;
	
	public final static long									seed						= new Long(1234);
	
	public final static int										numNeighbors		= 4;
	
	public final static int										pdMatrix[][]		= {{3, 0}, {5, 1}};
	
	public final static double								costPunish			= 3.0;
	
	public final static double								costSanction		= 1.5;
	
	public final static double								costPunisher		= 1.0;
	
	public final static double								costSanctioner	= 1.0;
	
	public final static double								initSalience		= 0.1;
	
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
		this.rnd = new Uniform(0.0, 1.0, new MersenneTwister((int) seed));
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
		
		// Initialize agents
		PDGAgent agent;
		for(Integer i = 0; i < numAgents; i++) {
			
			// TODO
			// Which agent to activate
			NormStatus normStatus;
			if(this.rnd.nextDouble() < 0.5) {
				normStatus = NormStatus.BELIEF;
			} else {
				normStatus = NormStatus.GOAL;
			}
			logger.debug(normStatus.name());
			
			// COOPERATE norm
			NormContent normContent = new NormContent(new CooperateAction(),
					new DefectAction());
			NormEntityAbstract norm = new NormEntity(1, NormType.SOCIAL,
					NormSource.DISTRIBUTED, normStatus, normContent, initSalience);
			
			List<SanctionEntityAbstract> sanctions = new ArrayList<SanctionEntityAbstract>();
			
			// PUNISHMENT sanction
			SanctionContent sanctionContent = new SanctionContent(
					Sanction.PUNISHMENT, costPunish, costPunisher);
			SanctionCategory sanctionCategory = new SanctionCategory(Issuer.INFORMAL,
					Locus.OTHER_DIRECTED, Mode.DIRECT, Polarity.NEGATIVE,
					Discernability.UNOBSTRUSIVE);
			SanctionEntityAbstract sanction = new SanctionEntity(1, sanctionCategory,
					SanctionStatus.ACTIVE, sanctionContent);
			sanctions.add(sanction);
			
			// SANCTION sanction
			sanctionContent = new SanctionContent(Sanction.SANCTION, costSanction,
					costSanctioner);
			sanctionCategory = new SanctionCategory(Issuer.FORMAL,
					Locus.OTHER_DIRECTED, Mode.DIRECT, Polarity.NEGATIVE,
					Discernability.UNOBSTRUSIVE);
			sanction = new SanctionEntity(2, sanctionCategory, SanctionStatus.ACTIVE,
					sanctionContent);
			sanctions.add(sanction);
			
			// MESSAGE sanction
			sanctionContent = new SanctionContent(Sanction.MESSAGE, 0.0, 0.0);
			sanctionCategory = new SanctionCategory(Issuer.FORMAL,
					Locus.OTHER_DIRECTED, Mode.DIRECT, Polarity.NEGATIVE,
					Discernability.UNOBSTRUSIVE);
			sanction = new SanctionEntity(3, sanctionCategory, SanctionStatus.ACTIVE,
					sanctionContent);
			sanctions.add(sanction);
			
			// Norms X Sanctions
			Map<NormEntityAbstract, List<SanctionEntityAbstract>> normsSanctions = new HashMap<NormEntityAbstract, List<SanctionEntityAbstract>>();
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
		List<Integer> neighbor;
		for(Object v : this.mGraph.vertexSet()) {
			int source = (Integer) v;
			for(DefaultEdge e : this.mGraph.edgesOf(v)) {
				int target = (Integer) this.mGraph.getEdgeSource(e);
				if(target == source) {
					target = (Integer) this.mGraph.getEdgeTarget(e);
				}
				
				if(this.neighbors.containsKey(source)) {
					neighbor = this.neighbors.get(source);
				} else {
					neighbor = new ArrayList<Integer>();
				}
				
				if(!neighbor.contains(target)) {
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
		
		// Run the simulation by a certain number of timesteps
		for(Integer r = 0; r < timeSteps; r++) {
			Map<Integer, ActionAbstract> actions = new HashMap<Integer, ActionAbstract>();
			Map<Integer, Map<Integer, SanctionEntityAbstract>> punishments = new HashMap<Integer, Map<Integer, SanctionEntityAbstract>>();
			
			logger.debug("");
			logger.debug("------ ITERATION " + (r + 1) + " ------");
			logger.debug("");
			
			// Paired Selection
			Map<Integer, Integer> paired = new HashMap<Integer, Integer>();
			
			NeighborIndex<Object, DefaultEdge> ni = new NeighborIndex<Object, DefaultEdge>(
					this.mGraph);
			for(Object v : this.mGraph.vertexSet()) {
				int source = (Integer) v;
				
				if((!paired.containsKey(source)) && (!paired.containsValue(source))) {
					boolean found = false;
					List<Object> neighbors = ni.neighborListOf(v);
					List<Integer> neighbor = new ArrayList<Integer>();
					while((!found) && (neighbor.size() < neighbors.size())) {
						int index = this.rnd.nextIntFromTo(0, neighbors.size() - 1);
						int target = (Integer) neighbors.get(index);
						
						if((!paired.containsKey(target)) && (!paired.containsValue(target))) {
							paired.put(source, target);
							found = true;
						}
						
						if(!neighbor.contains(target)) {
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
				ActionAbstract action = agent.decideAction();
				actions.put(i, action);
				
				logger.debug("AgentId [" + i + "] ACTION [" + action.getDescription()
						+ "]");
			}
			logger.debug("");
			
			// Neighbors actions
			Map<Integer, Map<Integer, ActionAbstract>> neighborActions = new HashMap<Integer, Map<Integer, ActionAbstract>>();
			for(Integer i = 0; i < numAgents; i++) {
				List<Object> neighbors = ni.neighborListOf(i);
				Map<Integer, ActionAbstract> nActions = new HashMap<Integer, ActionAbstract>();
				for(Object n : neighbors) {
					if(actions.containsKey((Integer) n)) {
						nActions.put((Integer) n, actions.get((Integer) n));
					}
				}
				neighborActions.put(i, nActions);
			}
			
			// Set payoff
			for(Integer s : paired.keySet()) {
				int source = s;
				ActionAbstract actionS = actions.get(source);
				int target = paired.get(source);
				ActionAbstract actionT = actions.get(target);
				
				// Source and Target actions
				Map<Integer, ActionAbstract> playersActions = new HashMap<Integer, ActionAbstract>();
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
				Map<Integer, SanctionEntityAbstract> punishment = agent.decidePunish();
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