package examples.pgg.modules.classifier;

import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.modules.classifier.EventClassifierAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventClassifier extends EventClassifierAbstract {
	
	@SuppressWarnings("unused")
	private static final Logger	logger	= LoggerFactory
																					.getLogger(EventClassifier.class);
	
	
	public EventClassifier(Integer agentId) {
		super(agentId);
	}
	
	
	@Override
	public NormativeEventEntityAbstract classify(Object event) {
		if(event instanceof NormativeEventEntityAbstract) {
			return (NormativeEventEntityAbstract) event;
		} else {
			return null;
		}
	}
}