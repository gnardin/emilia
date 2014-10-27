package emilia.entity.norm;

public interface NormContentInterface {
	
	/**
	 * Match
	 * 
	 * @param value
	 *          Value to check whether it matches the norm content
	 * @return True if it matches, False otherwise
	 */
	public boolean match(Object value);
}