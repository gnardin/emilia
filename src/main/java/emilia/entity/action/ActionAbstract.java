package emilia.entity.action;

import java.util.List;

public abstract class ActionAbstract {
	
	// Action identification
	protected Integer				id;
	
	// Action parameters
	protected List<Object>	params;
	
	
	/**
	 * Create an action without parameters
	 * 
	 * @param id
	 *          Action identification
	 * @return none
	 */
	public ActionAbstract(Integer id) {
		this.id = id;
		this.params = null;
	}
	
	
	/**
	 * Create an action with parameters
	 * 
	 * @param id
	 *          Action identification
	 * @param params
	 *          Action parameters
	 * @return none
	 */
	public ActionAbstract(Integer id, List<Object> params) {
		this.id = id;
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
}