package examples.topology.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.generate.GraphGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatticeGenerator<V, E> implements GraphGenerator<V, E, V> {
	private static final Logger logger = LoggerFactory
			.getLogger(LatticeGenerator.class);

	private int numAgents;
	private int numNeighbors;

	/**
	 * Construct a new LatticeGenerator.
	 * 
	 * @param numAgents
	 *            Number of agents
	 * @param numNeighbors
	 *            Number of neighbors
	 * 
	 * @throws IllegalArgumentException
	 *             if the specified size is negative.
	 */
	public LatticeGenerator(int numAgents, int numNeighbors) {

		if (numAgents < 0) {
			throw new IllegalArgumentException(
					"Network must contain positive number of nodes.");
		}
		if ((numNeighbors % 2) != 0) {
			throw new IllegalArgumentException(
					"All nodes must have an even degree.");
		}

		this.numAgents = numAgents;
		this.numNeighbors = numNeighbors;

		logger.debug("Number of Agents: " + this.numAgents
				+ " Number of Neighbors: " + this.numNeighbors);
	}

	/**
	 * Get number of agents
	 * 
	 * @param none
	 * @return Number of agents
	 */
	public int numAgents() {
		return this.numAgents;
	}

	/**
	 * Get number of neighbors
	 * 
	 * @param none
	 * @return Number of neighbors
	 */
	public int numNeighbors() {
		return this.numNeighbors;
	}

	/**
	 * Generate a graph
	 * 
	 * @param target
	 * @param vertexFactory
	 * @param resultMap
	 * @return none
	 */
	public void generateGraph(Graph<V, E> target,
			VertexFactory<V> vertexFactory, Map<String, V> resultMap) {

		List<V> vertexList = new ArrayList<V>();
		for (int i = 0; i < this.numAgents; i++) {
			V newVertex = vertexFactory.createVertex();
			target.addVertex(newVertex);
			vertexList.add(newVertex);
		}

		int neighborhood = (int) ((double) this.numNeighbors / 2.0);
		logger.debug("Vertex list size " + vertexList.size() + " Neighborhood "
				+ neighborhood);

		// Lattice constructed
		for (int i = 0; i < vertexList.size(); i++) {
			for (int k = 1; k <= neighborhood; k++) {
				target.addEdge(vertexList.get(i),
						vertexList.get((i + k) % vertexList.size()));
			}
		}
	}
}