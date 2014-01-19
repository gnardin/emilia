package emilia.entity.event;

public abstract class EventContentAbstract {

	public enum Type {
		ACTION, COMPLIANCE, VIOLATION, OBSERVED_COMPLIANCE, OBSERVED_VIOLATION, PUNISHMENT, SANCTION, NORM_INVOCATION_COMPLIANCE, NORM_INVOCATION_VIOLATION
	};

	protected Type type;
	protected int normId;

	public EventContentAbstract(Type type, int normId) {
		this.type = type;
		this.normId = normId;
	}

	/**
	 * Get type of the event content
	 * 
	 * @param none
	 * @return Event content type
	 */
	public Type getType() {
		return this.type;
	}

	/**
	 * Get norm identification
	 * 
	 * @param none
	 * @return Norm identification
	 */
	public int getNormId() {
		return this.normId;
	}
}