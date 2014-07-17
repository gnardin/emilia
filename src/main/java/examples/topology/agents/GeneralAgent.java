package examples.topology.agents;

import cern.jet.random.Uniform;
import examples.topology.environment.Environment.Actions;
import java.util.HashMap;
import java.util.Map;

public abstract class GeneralAgent {
	
	protected int										agentId;
	
	protected Uniform								rand;
	
	protected Map<Integer, Integer>	memory;
	
	protected int										numInteractions;
	
	
	public GeneralAgent(int agentId, Uniform rand) {
		this.agentId = agentId;
		this.rand = rand;
		this.memory = new HashMap<Integer, Integer>();
		this.numInteractions = 0;
	}
	
	
	/**
	 * Return the action to play against its opponents
	 * 
	 * @param opponentId
	 *          Opponent agent identification
	 * @return Action
	 */
	public abstract Actions decideAction();
	
	
	/**
	 * Get the agent identification
	 * 
	 * @param none
	 * @return Agent identification
	 */
	public int getId() {
		return this.agentId;
	}
	
	
	/**
	 * Get the number of interactions
	 * 
	 * @param none
	 * @return Number of interactions
	 */
	public int getNumInteractions() {
		return this.numInteractions;
	}
	
	
	/**
	 * Add an interaction with a specific agent
	 * 
	 * @param opponentId
	 *          Opponent agent identification
	 * @return none
	 */
	public void addInteraction(int opponentId) {
		if (this.memory.containsKey(opponentId)) {
			this.memory.put(opponentId, this.memory.get(opponentId) + 1);
		} else {
			this.memory.put(opponentId, 1);
		}
		
		this.numInteractions++;
	}
	
	
	/**
	 * Get number of interactions with other agents
	 * 
	 * @param opponentId
	 *          Opponent agent identification
	 * @return Number of interactions
	 */
	public int getInteractions(int opponentId) {
		int result = 0;
		
		if (this.memory.containsKey(opponentId)) {
			result = this.memory.get(opponentId);
		}
		
		return result;
	}
	
	
	/**
	 * Remove the number of interactions
	 * 
	 * @param opponentId
	 *          Opponent agent identification
	 * @return True Removed, False Agent not found
	 */
	public boolean removeInteractions(int opponentId) {
		if (this.memory.containsKey(opponentId)) {
			this.numInteractions -= this.memory.remove(opponentId);
			return true;
		}
		
		return false;
	}
}