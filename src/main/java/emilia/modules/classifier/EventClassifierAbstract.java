package emilia.modules.classifier;

import emilia.entity.event.EventEntity;
import emilia.entity.event.EventEntity.EventType;

public abstract class EventClassifierAbstract {

	/**
	 * Input event entity
	 * 
	 * @param event
	 *            Event entity
	 * @return none
	 */
	public abstract void input(EventEntity event);

	/**
	 * Register a callback method
	 * 
	 * @param type
	 *            Event type
	 * @param eventListener
	 *            Method to be called
	 * @return none
	 */
	public abstract void registerCallback(EventType type,
			EventClassifierListener eventListener);

	/**
	 * Unregister a callback method
	 * 
	 * @param type
	 *            Event type
	 * @param eventListener
	 *            Method to be called
	 * @return True if unregistered, False otherwise
	 */
	public abstract boolean unregisterCallback(EventType type,
			EventClassifierListener eventListener);
}