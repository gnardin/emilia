package examples.ijcai11.modules.classifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.modules.classifier.EventClassifierAbstract;

public class EventClassifier extends EventClassifierAbstract {
	
	@SuppressWarnings("unused")
	private static final Logger	logger	= LoggerFactory
																					.getLogger(EventClassifier.class);
	
	
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