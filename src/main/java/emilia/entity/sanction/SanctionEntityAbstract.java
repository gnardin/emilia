package emilia.entity.sanction;

import emilia.entity.EntityAbstract;

public abstract class SanctionEntityAbstract extends EntityAbstract implements
		Cloneable {
	
	public enum Source {
		FORMAL,
		INFORMAL;
	}
	
	public enum Locus {
		SELF_DIRECTED,
		OTHER_DIRECTED;
	}
	
	public enum Mode {
		DIRECT,
		INDIRECT;
	}
	
	public enum Polarity {
		POSITIVE,
		NEGATIVE;
	}
	
	public enum Discernibility {
		OBSTRUSIVE,
		UNOBSTRUSIVE;
	}
	
	public enum SanctionStatus {
		ACTIVE,
		INACTIVE;
	}
	
	protected Integer										id;
	
	protected Integer										type;
	
	protected SanctionStatus						status;
	
	protected SanctionContentInterface	content;
	
	
	/**
	 * Get sanction identification
	 * 
	 * @param none
	 * @return Sanction identification
	 */
	public Integer getId() {
		return this.id;
	}
	
	
	/**
	 * Set the sanction identification
	 * 
	 * @param id
	 *          Sanction identification
	 * @return none
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	/**
	 * Get sanction type
	 * 
	 * @param none
	 * @return Sanction type
	 */
	public Integer getType() {
		return this.type;
	}
	
	
	/**
	 * Calculate the sanction type number
	 * 
	 * @param source
	 *          Source type
	 * @param locus
	 *          Locus type
	 * @param mode
	 *          Mode type
	 * @param polarity
	 *          Polarity type
	 * @param discernibility
	 *          Discernibility type
	 * @return Calculate type
	 */
	public Integer calcType(Source source, Locus locus, Mode mode,
			Polarity polarity, Discernibility discernibility) {
		
		Integer localType = source.ordinal() - 1;
		
		if ((locus.ordinal() - 1) > 0) {
			localType += (int) Math.pow(2, 1);
		}
		
		if ((mode.ordinal() - 1) > 0) {
			localType += (int) Math.pow(2, 2);
		}
		
		if ((polarity.ordinal() - 1) > 0) {
			localType += (int) Math.pow(2, 3);
		}
		
		if ((discernibility.ordinal() - 1) > 0) {
			localType += (int) Math.pow(2, 4);
		}
		
		return localType;
	}
	
	
	/**
	 * Set sanction type
	 * 
	 * @param source
	 *          Source type
	 * @param locus
	 *          Locus type
	 * @param mode
	 *          Mode type
	 * @param polarity
	 *          Polarity type
	 * @param discernibility
	 *          Discernibility type
	 * @return none
	 */
	public void setType(Source source, Locus locus, Mode mode, Polarity polarity,
			Discernibility discernibility) {
		this.type = calcType(source, locus, mode, polarity, discernibility);
	}
	
	
	/**
	 * Get sanction status
	 * 
	 * @param none
	 * @return Sanction status
	 */
	public SanctionStatus getStatus() {
		return this.status;
	}
	
	
	/**
	 * Set sanction status
	 * 
	 * @param status
	 *          Sanction status
	 * @return none
	 */
	public void setStatus(SanctionStatus status) {
		this.status = status;
	}
	
	
	/**
	 * Get sanction content
	 * 
	 * @param none
	 * @return Sanction content
	 */
	public SanctionContentInterface getContent() {
		return this.content;
	}
	
	
	/**
	 * Set sanction content
	 * 
	 * @param content
	 *          Norm content
	 * @return none
	 */
	public void setContent(SanctionContentInterface content) {
		this.content = content;
	}
	
	
	/**
	 * Clone
	 * 
	 * @param none
	 * @return Cloned sanction entity
	 */
	public SanctionEntityAbstract clone() {
		try {
			return (SanctionEntityAbstract) super.clone();
		} catch(CloneNotSupportedException e) {
			throw new IllegalStateException(e);
		}
	}
}