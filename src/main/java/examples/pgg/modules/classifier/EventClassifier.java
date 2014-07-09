package examples.pgg.modules.classifier;

import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.modules.classifier.EventClassifierAbstract;

public class EventClassifier extends EventClassifierAbstract {
	
	public EventClassifier(Integer agentId) {
		super(agentId);
	}
	
	
	@Override
	public NormativeEventEntityAbstract classify(Object event) {
		if (event instanceof NormativeEventEntityAbstract) {
			return (NormativeEventEntityAbstract) event;
		} else {
			return null;
		}
	}
}