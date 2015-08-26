package emilia.defaultImpl.modules.classifier;

import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.modules.classifier.EventClassifierAbstract;
import java.util.ArrayList;
import java.util.List;
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
	public List<NormativeEventEntityAbstract> classify(Object event) {
		List<NormativeEventEntityAbstract> normativeEvents = new ArrayList<NormativeEventEntityAbstract>();
		
		if(event instanceof NormativeEventEntityAbstract) {
			normativeEvents.add((NormativeEventEntityAbstract) event);
		}
		
		return normativeEvents;
	}
}