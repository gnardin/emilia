package topology;

import examples.topology.network.LatticeGenerator;
import java.util.List;
import org.jgrapht.VertexFactory;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.Test;

public class LatticeGeneratorTest {

	/**
	 * Create a new vertex in a sequence
	 */
	private VertexFactory<Object> vertexFactory = new VertexFactory<Object>() {
		private int id = -1;

		public Object createVertex() {
			return new Integer(++this.id);
		}
	};

	private SimpleGraph<Object, DefaultEdge> mGraph;

	@Test
	public void latticeGeneratorTest() {
		this.mGraph = new SimpleGraph<Object, DefaultEdge>(DefaultEdge.class);
		
		LatticeGenerator<Object, DefaultEdge> lattice = new LatticeGenerator<Object, DefaultEdge>(
				9, 4);
		lattice.generateGraph(this.mGraph, this.vertexFactory, null);

		NeighborIndex<Object, DefaultEdge> neighborIndex = new NeighborIndex<Object, DefaultEdge>(
				this.mGraph);
		List<Object> neighbors;
		Integer agent;
		for (Object vertex : this.mGraph.vertexSet()) {
			agent = (Integer) vertex;

			neighbors = neighborIndex.neighborListOf(vertex);
			System.out.println();
			System.out.print(agent + " -> ");
			for (Object neighbor : neighbors) {
				System.out.print((Integer) neighbor + " ");
			}
		}
	}
}