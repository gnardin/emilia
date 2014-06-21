package emilia.entity.norm;

import emilia.entity.EntityAbstract;

public abstract class NormEntityAbstract extends EntityAbstract implements
		Cloneable {
	
	public enum Type {
		LEGAL,
		SOCIAL;
	}
	
	public enum Source {
		AUTHORITY,
		SET_AGENTS,
		DISTRIBUTED,
		IMPERSONAL;
	}
	
	public enum Status {
		INACTIVE,
		BELIEF,
		GOAL;
	}
	
	protected Integer								id;
	
	protected Type									type;
	
	protected Source								source;
	
	protected Status								status;
	
	protected NormContentInterface	content;
	
	protected String								context;
	
	protected Double								salience;
	
	
	/**
	 * Get norm identification
	 * 
	 * @param none
	 * @return Norm identification
	 */
	public Integer getId() {
		return this.id;
	}
	
	
	/**
	 * Set the norm identification
	 * 
	 * @param id
	 *          Norm identification
	 * @return none
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	/**
	 * Get norm type
	 * 
	 * @param none
	 * @return Norm type
	 */
	public Type getType() {
		return this.type;
	}
	
	
	/**
	 * Set norm type
	 * 
	 * @param type
	 *          Norm type
	 * @return none
	 */
	public void setType(Type type) {
		this.type = type;
	}
	
	
	/**
	 * Get source type
	 * 
	 * @param none
	 * @return Source type
	 */
	public Source getSource() {
		return this.source;
	}
	
	
	/**
	 * Set source type
	 * 
	 * @param source
	 *          Source type
	 * @return none
	 */
	public void setSource(Source source) {
		this.source = source;
	}
	
	
	/**
	 * Get norm status
	 * 
	 * @param none
	 * @return Norm status
	 */
	public Status getStatus() {
		return this.status;
	}
	
	
	/**
	 * Set norm status
	 * 
	 * @param status
	 *          Norm status
	 * @return none
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	/**
	 * Get norm content
	 * 
	 * @param none
	 * @return Norm content
	 */
	public NormContentInterface getContent() {
		return this.content;
	}
	
	
	/**
	 * Set norm content
	 * 
	 * @param content
	 *          Norm content
	 * @return none
	 */
	public void setContent(NormContentInterface content) {
		this.content = content;
	}
	
	
	/**
	 * Get norm context in which the norm is valid
	 * 
	 * @param none
	 * @return Norm context
	 */
	public String getContext() {
		return this.context;
	}
	
	
	/**
	 * Set norm context
	 * 
	 * @param context
	 *          Norm context
	 * @return none
	 */
	public void setContext(String context) {
		this.context = context;
	}
	
	
	/**
	 * Get the norm salience
	 * 
	 * @param none
	 * @return Norm salience
	 */
	public Double getSalience() {
		return this.salience;
	}
	
	
	/**
	 * Set the norm salience
	 * 
	 * @param salience
	 *          Norm salience
	 * @return none
	 */
	public void setSalience(Double salience) {
		this.salience = salience;
	}
	
	
	/**
	 * Clone
	 * 
	 * @param none
	 * @return Cloned norm entity
	 */
	public NormEntityAbstract clone() {
		try {
			return (NormEntityAbstract) super.clone();
		} catch(CloneNotSupportedException e) {
			throw new IllegalStateException(e);
		}
	}
}