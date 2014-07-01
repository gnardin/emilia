package emilia.modules.classifier;

import emilia.entity.event.NormativeEventEntityAbstract;

public abstract class EventClassifierAbstract {
	
	/**
	 * Classify events
	 * 
	 * @param event
	 *          Event to classify
	 * @return Classified event
	 */
	public abstract NormativeEventEntityAbstract classify(Object event);
}