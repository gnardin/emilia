package emilia.test.modules;

import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.modules.classifier.EventClassifierAbstract;

public class EventClassifier extends EventClassifierAbstract {
	
	@Override
	public NormativeEventEntityAbstract classify(Object event) {
		if (event instanceof NormativeEventEntityAbstract) {
			return (NormativeEventEntityAbstract) event;
		} else {
			return null;
		}
	}
}