package examples.topology.environment.event;

public class EventEntity {
	
	protected long		time;
	
	protected int			sender;
	
	protected int			receiver;
	
	protected Object	content;
	
	
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
	 *          Event identification
	 * @return none
	 */
	public EventEntity(long time, int sender, int receiver, Object content) {
		this.time = time;
		this.sender = sender;
		this.receiver = receiver;
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
	 *          Event global numeric time
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
	 *          Event sender
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
	 *          Event receiver
	 * @return none
	 */
	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}
	
	
	/**
	 * Get the event content
	 * 
	 * @param none
	 * @return Event content
	 */
	public Object getContent() {
		return this.content;
	}
	
	
	/**
	 * Set the event content
	 * 
	 * @param type
	 *          Event content
	 * @return none
	 */
	public void setContent(Object content) {
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
		return this.time + " " + this.sender + " " + this.receiver + " "
				+ this.content;
	}
}