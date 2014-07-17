package examples.topology.environment;

public class Environment {

	public enum Actions {
		COOPERATE, DEFECT
	};

	public enum AgentType {
		DUMMY, EMILIA
	};

	public enum NetworkType {
		MESH, LATTICE, SCALE_FREE, SMALL_NETWORK
	};

	public enum ObservanceType {
		NEIGHBOR, RANDOM, FOCAL_RANDOM
	}

	public enum Payoff {
		// Prisoner dilemma inequality requirement
		// T > R > P > S
		// Prevent altering cooperation
		// 2R > T + S
		TEMPTATION(5), REWARD(3), PUNISHMENT(1), SUCKER(0);

		private int payoff;

		Payoff(int payoff) {
			this.payoff = payoff;
		}

		public int getPayoff() {
			return this.payoff;
		}
	};
}