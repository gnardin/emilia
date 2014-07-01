package emilia.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmiliaConf {
	
	private static final Logger	logger	= LoggerFactory
																					.getLogger(EmiliaConf.class);
	
	// Configuration Parameters
	public enum Param {
		EVENT_CLASSIFIER_CLASS("eventClassifierClass"),
		NORM_RECOGNITION_CLASS("normRecognitionClass"),
		NORM_ADOPTION_CLASS("normAdoptionClass"),
		NORM_SALIENCE_CLASS("normSalienceClass"),
		NORM_ENFORCEMENT_CLASS("normEnforcementClass"),
		NORM_COMPLIANCE_CLASS("normComplianceClass"),
		NORMATIVE_BOARD_CLASS("normativeBoardClass");
		
		private String	name;
		
		
		/**
		 * Create a parameter
		 * 
		 * @param name
		 *          Name of the parameter
		 * @return none
		 */
		private Param(String name) {
			this.name = name;
		}
		
		
		/**
		 * Get name of the parameter
		 * 
		 * @param none
		 * @return Name of the parameter
		 */
		public String getName() {
			return this.name;
		}
	};
	
	// Parameter values
	private Object[]	paramValues;
	
	
	/**
	 * Create a configuration entity
	 * 
	 * @param none
	 * @return none
	 */
	public EmiliaConf() {
		this.paramValues = new Object[Param.values().length];
		for(Param param : Param.values()) {
			this.paramValues[param.ordinal()] = null;
		}
	}
	
	
	/**
	 * Get String parameter value
	 * 
	 * @param param
	 *          Parameter
	 * @return String parameter value
	 */
	public String getStrValue(Param param) {
		if (this.paramValues[param.ordinal()] instanceof String) {
			return (String) this.paramValues[param.ordinal()];
		}
		
		return null;
	}
	
	
	/**
	 * Get Integer parameter value
	 * 
	 * @param param
	 *          Parameter
	 * @return Integer parameter value
	 */
	public Integer getIntValue(Param param) {
		if (this.paramValues[param.ordinal()] instanceof Integer) {
			return (Integer) this.paramValues[param.ordinal()];
		}
		
		return null;
	}
	
	
	/**
	 * Get Double parameter value
	 * 
	 * @param param
	 *          Parameter
	 * @return Double parameter value
	 */
	public Double getDoubleValue(Param param) {
		if (this.paramValues[param.ordinal()] instanceof Double) {
			return (Double) this.paramValues[param.ordinal()];
		}
		
		return null;
	}
	
	
	/**
	 * Set the parameter value
	 * 
	 * @param param
	 *          Parameter
	 * @param value
	 *          Parameter value
	 * @return none
	 */
	public void setValue(Param param, Object value) {
		this.paramValues[param.ordinal()] = value;
		
		String strValue = "";
		if (value instanceof Integer) {
			strValue = ((Integer) value).toString();
		} else if (value instanceof Double) {
			strValue = ((Double) value).toString();
		} else if (value instanceof String) {
			strValue = (String) value;
		}
		logger.debug("[" + param.getName() + "] = [" + strValue + "]");
	}
	
	
	/**
	 * Convert the object to a String
	 * 
	 * @param none
	 * @return none
	 */
	@Override
	public String toString() {
		String str = new String();
		
		for(Param param : Param.values()) {
			str = "[" + param.getName() + "] = ["
					+ (String) this.paramValues[param.ordinal()] + "]\n";
		}
		
		return str;
	}
}