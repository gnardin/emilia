package emilia.entity.sanction;

import emilia.entity.EntityAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SanctionEntityAbstract extends EntityAbstract implements
		Cloneable {
	
	@SuppressWarnings("unused")
	private static final Logger	logger	= LoggerFactory
																					.getLogger(SanctionEntityAbstract.class);
	
	// Sanction status
	public enum SanctionStatus {
		ACTIVE,
		INACTIVE;
	}
	
	// Sanction identification
	protected int												id;
	
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
	public int getId() {
		return this.id;
	}
	
	
	/**
	 * Set the sanction identification
	 * 
	 * @param id
	 *          Sanction identification
	 * @return none
	 */
	public void setId(int id) {
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
		
		if (this == obj) {
			return true;
		} else if ((obj != null) && (obj.getClass() == this.getClass())) {
			SanctionEntityAbstract sanction = (SanctionEntityAbstract) obj;
			if (this.getId() == sanction.getId()) {
				return true;
			}
		}
		
		return false;
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