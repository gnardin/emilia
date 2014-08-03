package emilia.entity.sanction;

import emilia.entity.EntityAbstract;

public abstract class SanctionEntityAbstract extends EntityAbstract implements
		Cloneable {
	
	// Sanction status
	public enum SanctionStatus {
		ACTIVE,
		INACTIVE;
	}
	
	// Sanction identification
	protected Integer										id;
	
	// Sanction category
	protected SanctionCategory					category;
	
	// Sanction status
	protected SanctionStatus						status;
	
	// Sanction content
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
	 * Get sanction category
	 * 
	 * @param none
	 * @return Sanction category
	 */
	public SanctionCategory getCategory() {
		return this.category;
	}
	
	
	/**
	 * Set sanction category
	 * 
	 * @param category
	 *          Sanction category
	 * @return none
	 */
	public void setCategory(SanctionCategory category) {
		this.category = category;
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
	
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		
		if (this == obj) {
			result = true;
		} else if ((obj != null) && (obj.getClass() == this.getClass())) {
			SanctionEntityAbstract sanction = (SanctionEntityAbstract) obj;
			if (this.getId().intValue() == sanction.getId().intValue()) {
				result = true;
			}
		}
		
		return result;
	}
	
	
	@Override
	public int hashCode() {
		int hash = 217 + this.id;
		return hash;
	}
	
	
	@Override
	public SanctionEntityAbstract clone() {
		try {
			return (SanctionEntityAbstract) super.clone();
		} catch(CloneNotSupportedException e) {
			throw new IllegalStateException(e);
		}
	}
}