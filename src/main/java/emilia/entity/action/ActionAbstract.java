package emilia.entity.action;

import java.util.List;

public abstract class ActionAbstract {
	
	// Action identification
	protected Integer				id;
	
	// Action description
	protected String				description;
	
	// Action parameters
	protected List<Object>	params;
	
	
	/**
	 * Create an action without parameters
	 * 
	 * @param id
	 *          Action identification
	 * @param description
	 *          Action description
	 * @return none
	 */
	public ActionAbstract(Integer id, String description) {
		this.id = id;
		this.description = description;
		this.params = null;
	}
	
	
	/**
	 * Create an action with parameters
	 * 
	 * @param id
	 *          Action identification
	 * @param description
	 *          Action description
	 * @param params
	 *          Action parameters
	 * @return none
	 */
	public ActionAbstract(Integer id, String description, List<Object> params) {
		this.id = id;
		this.description = description;
		this.params = params;
	}
	
	
	/**
	 * Get the action identification
	 * 
	 * @param none
	 * @return Action identification
	 */
	public Integer getId() {
		return this.id;
	}
	
	
	/**
	 * Get the action description
	 * 
	 * @param none
	 * @return Action description
	 */
	public String getDescription() {
		return this.description;
	}
	
	
	/**
	 * Get action parameters
	 * 
	 * @param none
	 * @return Action parameters
	 */
	public List<Object> getParams() {
		return this.params;
	}
	
	
	/**
	 * Get a specific action parameter value
	 * 
	 * @param index
	 *          Parameter index
	 * @return Parameter value
	 */
	public Object getParam(Integer index) {
		Object param = null;
		
		if ((this.params != null) && (index < this.params.size())) {
			param = this.params.get(index);
		}
		
		return param;
	}
	
	
	/**
	 * Set a specific action parameter value
	 * 
	 * @param index
	 *          Parameter index
	 * @param param
	 *          Parameter value
	 * @return none
	 */
	public void setParam(Integer index, Object param) {
		if ((this.params != null) && (index < this.params.size())) {
			this.params.set(index, param);
		}
	}
	
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		
		if (this == obj) {
			result = true;
		} else if ((obj != null) && (obj.getClass() == this.getClass())) {
			ActionAbstract action = (ActionAbstract) obj;
			if (this.getId().intValue() == action.getId().intValue()) {
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
}