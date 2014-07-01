package emilia.modules.enforcement;

public abstract class DeviationAbstract {
	
	public enum Type {
		COMPLIANCE,
		VIOLATION;
	}
	
	protected Type	type;
	
	
	/**
	 * Create a deviation
	 * 
	 * @param type
	 *          Type of deviation
	 * @return none
	 */
	public DeviationAbstract(Type type) {
		this.type = type;
	}
	
	
	/**
	 * Get deviation type
	 * 
	 * @param none
	 * @return Deviation type
	 */
	public Type getType() {
		return this.type;
	}
}