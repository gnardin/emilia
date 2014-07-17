package examples.topology.agents.dummy;

import cern.jet.random.Uniform;
import examples.topology.agents.GeneralAgent;
import examples.topology.environment.Environment.Actions;

public class CooperativeAgent extends GeneralAgent {
	
	public CooperativeAgent(Integer agentId, Uniform rand) {
		super(agentId, rand);
	}
	
	
	@Override
	public Actions decideAction() {
		return Actions.COOPERATE;
	}
}