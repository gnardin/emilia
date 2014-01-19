package emilia.modules.classifier;

import emilia.entity.event.EventEntity;
import java.util.Objects;

public abstract class EventClassifierListener {

	protected long id;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Listener identification
	 * @return none
	 */
	public EventClassifierListener(int id) {
		this.id = id;
	}

	/**
	 * Get event listener identification
	 * 
	 * @param none
	 * @return Event listener identification
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * Submit an event entity to the listener
	 * 
	 * @param event
	 *            Event entity
	 * @return none
	 */
	public abstract void receive(EventEntity event);

	/**
	 * Equals method
	 * 
	 * @param obj
	 *            Object to compare
	 * @return True if equals, False otherwise
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (this.getClass() != obj.getClass()) {
			return false;
		}

		final EventClassifierListener other = (EventClassifierListener) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}

		return true;
	}

	/**
	 * Hash code
	 * 
	 * @param none
	 * @return hash code identification
	 */
	public int hashCode() {
		int hash = 5;
		hash = 97 * hash + Objects.hashCode(this.id);

		return hash;
	}
}