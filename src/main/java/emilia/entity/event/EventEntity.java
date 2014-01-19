package emilia.entity.event;

import emilia.entity.EntityAbstract;

@SuppressWarnings("serial")
public class EventEntity extends EntityAbstract {

	public enum EventType {
		ACTION, INFORMATION
	};

	protected long time;
	protected int sender;
	protected int receiver;
	protected EventType type;
	protected EventContentAbstract content;

	/**
	 * Constructor
	 * 
	 * @param none
	 * @return none
	 */
	public EventEntity() {
	}

	/**
	 * Constructor
	 * 
	 * @param eventId
	 *            Event identification
	 * @return none
	 */
	public EventEntity(long time, int sender, int receiver, EventType type,
			EventContentAbstract content) {
		this.time = time;
		this.sender = sender;
		this.receiver = receiver;
		this.type = type;
		this.content = content;
	}

	/**
	 * Get the event global numeric time
	 * 
	 * @param none
	 * @return Global numeric time at which the event was generated
	 */
	public long getTime() {
		return this.time;
	}

	/**
	 * Set the event global numeric time
	 * 
	 * @param time
	 *            Event global numeric time
	 * @none
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * Get the event sender
	 * 
	 * @param none
	 * @return Event sender
	 */
	public int getSender() {
		return this.sender;
	}

	/**
	 * Set the event sender
	 * 
	 * @param sender
	 *            Event sender
	 * @return none
	 */
	public void setSender(int sender) {
		this.sender = sender;
	}

	/**
	 * Get the event receiver
	 * 
	 * @param none
	 * @return Event receiver
	 */
	public int getReceiver() {
		return this.receiver;
	}

	/**
	 * Set the event receiver
	 * 
	 * @param sender
	 *            Event receiver
	 * @return none
	 */
	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	/**
	 * Get the event type
	 * 
	 * @param none
	 * @return Event type
	 */
	public EventType getType() {
		return this.type;
	}

	/**
	 * Set the event type
	 * 
	 * @param type
	 *            Event type
	 * @return none
	 */
	public void setType(EventType type) {
		this.type = type;
	}

	/**
	 * Get the event content
	 * 
	 * @param none
	 * @return Event content
	 */
	public EventContentAbstract getContent() {
		return this.content;
	}

	/**
	 * Set the event content
	 * 
	 * @param type
	 *            Event content
	 * @return none
	 */
	public void setContent(EventContentAbstract content) {
		this.content = content;
	}

	/**
	 * Convert object to String
	 * 
	 * @param none
	 * @return String containing the event information
	 */
	@Override
	public String toString() {
		return this.entityId + " " + this.time + " " + this.sender + " "
				+ this.receiver + " " + this.content;
	}
}