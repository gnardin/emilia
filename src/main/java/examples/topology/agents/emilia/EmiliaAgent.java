package examples.topology.agents.emilia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cern.jet.random.Uniform;
import emilia.EmiliaController;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.NormStatus;
import emilia.entity.norm.NormEntityAbstract.NormType;
import emilia.entity.norm.NormEntityAbstract.NormSource;
import emilia.entity.sanction.SanctionEntityAbstract;
import examples.pgg.entity.action.CooperateAction;
import examples.pgg.entity.action.DefectAction;
import examples.pgg.entity.norm.NormContent;
import examples.pgg.entity.norm.NormEntity;
import examples.topology.agents.GeneralAgent;
import examples.topology.environment.Environment.Actions;

public class EmiliaAgent extends GeneralAgent {
	
	public EmiliaAgent(Integer agentId, Uniform rand) {
		super(agentId, rand);
		
		EmiliaController emilia = new EmiliaController(agentId,
				"src/main/resources/conf/emilia.xml",
				"src/main/resources/conf/emilia.xsd");
		
		// COOPERATE norm
		NormContent content = new NormContent(new CooperateAction(),
				new DefectAction());
		NormEntityAbstract norm = new NormEntity(1, NormType.SOCIAL,
				NormSource.DISTRIBUTED, NormStatus.GOAL, content, 0.0);
		
		Map<NormEntityAbstract, List<SanctionEntityAbstract>> normsSanctions;
		normsSanctions = new HashMap<NormEntityAbstract, List<SanctionEntityAbstract>>();
		normsSanctions.put(norm, new ArrayList<SanctionEntityAbstract>());
		
		emilia.addNormsSanctions(normsSanctions);
	}
	
	
	@Override
	public Actions decideAction() {
		return Actions.COOPERATE;
	}
}