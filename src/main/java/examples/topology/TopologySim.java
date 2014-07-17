package examples.topology;

import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;
import examples.topology.agents.GeneralAgent;
import examples.topology.agents.dummy.CooperativeAgent;
import examples.topology.agents.emilia.EmiliaAgent;
import examples.topology.environment.Environment.Actions;
import examples.topology.environment.Environment.AgentType;
import examples.topology.environment.Environment.NetworkType;
import examples.topology.environment.Environment.ObservanceType;
import examples.topology.network.LatticeGenerator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jgrapht.VertexFactory;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopologySim {
	
	private static final Logger logger = LoggerFactory.getLogger(TopologySim.class);

	private int trial;
	private int timeLimit;
	private NetworkType networkType;
	private AgentType agentType;
	private int numPlayers;
	private int numNeighbors;
	private ObservanceType observanceType;
	private int observanceLimit;
	private boolean detailedFiles;

	// Global Variables
	private RandomEngine randGenerator;
	private Uniform rand;
	private SimpleGraph<Object, DefaultEdge> mGraph;
	private Map<Integer, GeneralAgent> agents;

	/**
	 * Create a new vertex in a sequence
	 */
	private VertexFactory<Object> vertexFactory = new VertexFactory<Object>() {
		private int id = -1;

		public Object createVertex() {
			return new Integer(++this.id);
		}
	};

	/**
	 * Constructor
	 * 
	 * @param trial
	 * @param seed
	 * @param timeLimit
	 * @param networkType
	 * @param agentType
	 * @param numAgents
	 * @param numPlayers
	 * @param numNeighbors
	 * @param observanceType
	 * @param observanceLimit
	 * @param detailedFiles
	 * 
	 * @param bothAgentsReward
	 * @param visualization
	 * @param weightedReward
	 * @param observanceProb
	 * @param rewiring
	 * @param rewiringTolerance
	 * @param rewiringType
	 * @param observanceLimit
	 * @param observanceType
	 * @param rewardRule
	 * @param combinedWeapons
	 * @param topologicalExperimentType
	 * @param thresholdLimitPercentage
	 * @param selfReinforcingStructures
	 */
	public TopologySim(int trial, long seed, int timeLimit, NetworkType networkType,
			AgentType agentType, int numAgents, int numPlayers,
			int numNeighbors, ObservanceType observanceType,
			int observanceLimit, boolean detailedFiles) {
		this.randGenerator = new MersenneTwister(new Date(seed));
		this.rand = new Uniform(0.0, numAgents, this.randGenerator);

		this.trial = trial;
		this.timeLimit = timeLimit;
		this.networkType = networkType;
		this.agentType = agentType;
		this.numPlayers = numPlayers;
		this.numNeighbors = numNeighbors;
		this.observanceType = observanceType;
		this.observanceLimit = observanceLimit;
		this.detailedFiles = detailedFiles;

		this.mGraph = new SimpleGraph<Object, DefaultEdge>(DefaultEdge.class);

		switch (this.networkType) {
		case LATTICE:
			LatticeGenerator<Object, DefaultEdge> lattice = new LatticeGenerator<Object, DefaultEdge>(
					numAgents, this.numNeighbors);

			lattice.generateGraph(this.mGraph, this.vertexFactory, null);
			break;
		default:
			throw new IllegalArgumentException(
					"Network structure does not implemented.");
		}

		// Create the agents
		this.agents = new HashMap<Integer, GeneralAgent>();
		GeneralAgent agent;
		Integer agentId;
		for (Object vertex : this.mGraph.vertexSet()) {
			agentId = (Integer) vertex;

			switch (this.agentType) {
			case DUMMY:
				agent = new CooperativeAgent(agentId, this.rand);
				break;
			case EMILIA:
				agent = new EmiliaAgent(agentId, this.rand);
				break;
			default:
				agent = new CooperativeAgent(agentId, this.rand);
			}
			this.agents.put(agentId, agent);

			logger.debug("Agent " + agentId + " is connected to "
					+ this.mGraph.edgesOf(agentId).size());
		}
	}

	/**
	 * Select the opponent players of a specific agent
	 * 
	 * @param agentId
	 *            Agent identification
	 * @param observanceType
	 *            Type of allowed observance (NEIGHBOR, RANDOM, FOCAL_RANDOM)
	 * @return Observed players
	 */
	private ArrayList<Integer> getOpponentPlayers(int agentId,
			ObservanceType observanceType) {
		ArrayList<Integer> players = new ArrayList<Integer>();
		int player;
		Set<Object> vertices;
		NeighborIndex<Object, DefaultEdge> neighborIndex;
		List<Object> neighbors;

		switch (observanceType) {
		case NEIGHBOR:
			neighborIndex = new NeighborIndex<Object, DefaultEdge>(this.mGraph);
			neighbors = neighborIndex.neighborListOf(agentId);

			while (((this.observanceLimit == 0) || ((players.size() < this.observanceLimit))
					&& ((this.numPlayers == 0) || (players.size() < this.numPlayers)))
					&& (neighbors.size() > 0)) {
				player = (Integer) neighbors.remove(0);
				players.add(player);
			}
			break;
		case RANDOM:
			vertices = this.mGraph.vertexSet();
			int limit = Math.max(this.observanceLimit, this.numPlayers);
			for (int i = 0; i < limit; i++) {
				do {
					player = this.rand.nextIntFromTo(0, vertices.size() - 1);
				} while ((player == agentId) || (players.contains(player)));
				players.add(player);
			}
			break;
		case FOCAL_RANDOM:
			vertices = this.mGraph.vertexSet();
			do {
				player = this.rand.nextIntFromTo(0, vertices.size() - 1);
			} while ((player == agentId) || (players.contains(player)));
			players.add(player);

			neighborIndex = new NeighborIndex<Object, DefaultEdge>(this.mGraph);
			neighbors = neighborIndex.neighborListOf(player);

			while (((this.observanceLimit == 0) || ((players.size() < this.observanceLimit))
					&& ((this.numPlayers == 0) || (players.size() < this.numPlayers)))
					&& (neighbors.size() > 0)) {
				player = (Integer) neighbors.remove(0);
				players.add(player);
			}
		}

		players.add(agentId);

		return players;
	}

	/**
	 * Run the simulation
	 * 
	 * @param none
	 * @return none
	 */
	public void run() {
		logger.debug("Trial " + this.trial);

		// Create a shuffled list of agents
		ArrayList<Integer> order = new ArrayList<Integer>();
		for (Integer agentId : this.agents.keySet()) {
			order.add(agentId);
		}

		int round = 0;
		boolean endTrial = false;
		GeneralAgent agent;
		Actions action;
		ArrayList<Integer> players;
		do {
			round++;

			// Shuffle the agents
			Collections.shuffle(order);

			// Execute the simulation
			players = new ArrayList<Integer>();
			for (Integer agentId : order) {
				agent = this.agents.get(agentId);

				players = getOpponentPlayers(agentId, this.observanceType);

				for (Integer opponentId : players) {
					action = agent.decideAction();
				}
			}

			if (round > this.timeLimit) {
				endTrial = true;
			}
		} while (!endTrial);
	}

	/**
	 * 
	 */
	public static void main(String[] args) throws Exception {

		int numTrials = 1;
		long seed = (new Date()).getTime();
		int timeLimit = 500000;
		NetworkType networkType = NetworkType.LATTICE;
		AgentType agentType = AgentType.EMILIA;
		int numAgents = 100;
		int numPlayers = 0;
		int numNeighbors = 4;
		ObservanceType observanceType = ObservanceType.NEIGHBOR;
		int observanceLimit = 0;
		boolean detailedFile = true;

		TopologySim sim;
		for (int trial = 0; trial < numTrials; trial++) {
			sim = new TopologySim(trial, seed, timeLimit, networkType, agentType,
					numAgents, numPlayers, numNeighbors, observanceType,
					observanceLimit, detailedFile);
			sim.run();
		}
	}
}