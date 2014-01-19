package emilia.modules.classifier;

import emilia.entity.event.EventEntity;
import emilia.entity.event.EventEntity.EventType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventClassifier extends EventClassifierAbstract {

	protected Map<Integer, List<EventClassifierListener>> callbacks;

	/**
	 * Constructor
	 * 
	 * @param none
	 * @return none
	 */
	public EventClassifier() {
		this.callbacks = new HashMap<Integer, List<EventClassifierListener>>();
	}

	/**
	 * Input event entity
	 * 
	 * @param event
	 *            Event entity
	 * @return none
	 */
	public void input(EventEntity event) {
		if (this.callbacks.containsKey(event.getType().ordinal())) {
			List<EventClassifierListener> listener = this.callbacks.get(event
					.getType().ordinal());

			for (EventClassifierListener eventListener : listener) {
				eventListener.receive(event);
			}
		}
	}

	/**
	 * Register a callback method
	 * 
	 * @param type
	 *            Event type
	 * @param eventListener
	 *            Method to be called
	 * @return none
	 */
	public void registerCallback(EventType type,
			EventClassifierListener eventListener) {
		List<EventClassifierListener> listener;

		if (this.callbacks.containsKey(type.ordinal())) {
			listener = this.callbacks.get(type.ordinal());
		} else {
			listener = new ArrayList<EventClassifierListener>();
		}

		listener.add(eventListener);
		this.callbacks.put(type.ordinal(), listener);
	}

	/**
	 * Unregister a callback method
	 * 
	 * @param type
	 *            Event type
	 * @param eventListener
	 *            Method to be called
	 * @return True if unregistered, False otherwise
	 */
	public boolean unregisterCallback(EventType type,
			EventClassifierListener eventListener) {
		boolean result = false;

		if (this.callbacks.containsKey(type.ordinal())) {
			List<EventClassifierListener> listener = this.callbacks.get(type
					.ordinal());

			if (listener.contains(eventListener)) {
				listener.remove(eventListener);
				result = true;
			}
		}

		return result;
	}
}