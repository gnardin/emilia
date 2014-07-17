package examples.topology.agents.dummy;

import cern.jet.random.Uniform;
import examples.topology.agents.GeneralAgent;
import examples.topology.environment.Environment.Actions;

public class DefectiveAgent extends GeneralAgent {
	
	public DefectiveAgent(Integer agentId, Uniform rand) {
		super(agentId, rand);
	}
	
	
	@Override
	public Actions decideAction() {
		return Actions.DEFECT;
	}
}