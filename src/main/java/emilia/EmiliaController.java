package emilia;

import emilia.board.NormativeBoardInterface;
import emilia.board.NormativeEventType;
import emilia.conf.EmiliaConf;
import emilia.conf.EmiliaConf.Param;
import emilia.conf.EmiliaConfParser;
import emilia.entity.event.EventEntityAbstract;
import emilia.entity.event.EventType;
import emilia.modules.adoption.NormAdoptionAbstract;
import emilia.modules.compliance.NormComplianceAbstract;
import emilia.modules.enforcement.NormEnforcementAbstract;
import emilia.modules.recognition.NormRecognitionAbstract;
import emilia.modules.salience.NormSalienceAbstract;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmiliaController extends EmiliaAbstract {
	
	private static final Logger			logger	= LoggerFactory
																							.getLogger(EmiliaController.class);
	
	// Agent identification
	private Integer									agentId;
	
	// Norm Recognition module
	private NormRecognitionAbstract	normRecognition;
	
	// Norm Adoption module
	private NormAdoptionAbstract		normAdoption;
	
	// Norm Salience module
	private NormSalienceAbstract		normSalience;
	
	// Norm Enforcement module
	private NormEnforcementAbstract	normEnforcement;
	
	// Norm Compliance module
	private NormComplianceAbstract	normCompliance;
	
	// Normative Board
	private NormativeBoardInterface	normativeBoard;
	
	
	/**
	 * Create the EMILIA controller
	 * 
	 * @param agentId
	 *          Agent identification
	 * @param xmlFilename
	 *          XML configuration filename
	 * @param xsdFilename
	 *          Schema configuration filename
	 * @return EMILIA controller
	 */
	public EmiliaController(Integer agentId, String xmlFilename,
			String xsdFilename) {
		
		this.agentId = agentId;
		
		EmiliaConf conf = EmiliaConfParser.getInstance().getConf(xmlFilename,
				xsdFilename);
		
		// Normative Board
		logger.debug("Initializing [NORMATIVE BOARD]");
		this.setNormativeBoard((String) conf
				.getStrValue(Param.NORMATIVE_BOARD_CLASS));
		logger.debug("Initialized [NORMATIVE BOARD]");
		
		// Norm Recognition
		logger.debug("Initializing [NORM RECOGNITION]");
		this.setNormRecognition((String) conf
				.getStrValue(Param.NORM_RECOGNITION_CLASS));
		logger.debug("Initialized [NORM RECOGNITION]");
		
		// Norm Adoption
		logger.debug("Initializing [NORM ADOPTION]");
		this.setNormAdoption((String) conf.getStrValue(Param.NORM_ADOPTION_CLASS));
		this.normativeBoard.registerCallback(
				new ArrayList<NormativeEventType>(Arrays.asList(
						NormativeEventType.INSERT_NORM, NormativeEventType.UPDATE_NORM,
						NormativeEventType.UPDATE_SALIENCE)), this.normAdoption);
		logger.debug("Initialized [NORM ADOPTION]");
		
		// Norm Salience
		logger.debug("Initializing [NORM SALIENCE]");
		this.setNormSalience((String) conf.getStrValue(Param.NORM_SALIENCE_CLASS));
		this.normRecognition.registerCallback(
				new ArrayList<Boolean>(Arrays.asList(true)),
				new ArrayList<EventType>(Arrays.asList(EventType.COMPLIANCE,
						EventType.COMPLIANCE_INFORMED, EventType.VIOLATION,
						EventType.VIOLATION_INFORMED, EventType.PUNISHMENT,
						EventType.PUNISHMENT_INFORMED, EventType.SANCTION,
						EventType.SANCTION_INFORMED, EventType.COMPLIANCE_INVOCATION,
						EventType.COMPLIANCE_INVOCATION_INFORMED,
						EventType.VIOLATION_INVOCATION,
						EventType.VIOLATION_INVOCATION_INFORMED)), this.normSalience);
		logger.debug("Initialized [NORM SALIENCE]");
		
		// Norm Enforcement
		logger.debug("Initializing [NORM ENFORCEMENT]");
		this.setNormEnforcement((String) conf
				.getStrValue(Param.NORM_ENFORCEMENT_CLASS));
		this.normRecognition.registerCallback(
				new ArrayList<Boolean>(Arrays.asList(true)),
				new ArrayList<EventType>(Arrays.asList(EventType.ACTION,
						EventType.ACTION_OBSERVED, EventType.ACTION_INFORMED,
						EventType.COMPLIANCE, EventType.COMPLIANCE_INFORMED,
						EventType.VIOLATION, EventType.VIOLATION_INFORMED,
						EventType.PUNISHMENT, EventType.PUNISHMENT_INFORMED,
						EventType.SANCTION, EventType.SANCTION_INFORMED,
						EventType.COMPLIANCE_INVOCATION,
						EventType.COMPLIANCE_INVOCATION_INFORMED,
						EventType.VIOLATION_INVOCATION,
						EventType.VIOLATION_INVOCATION_INFORMED)), this.normEnforcement);
		logger.debug("Initialized [NORM ENFORCEMENT]");
		
		// Norm Compliance
		logger.debug("Initializing [NORM COMPLIANCE]");
		this.setNormCompliance((String) conf
				.getStrValue(Param.NORM_COMPLIANCE_CLASS));
		logger.debug("Initialized [NORM COMPLIANCE]");
	}
	
	
	/**
	 * Set Normative Board
	 * 
	 * @param normativeBoardClass
	 *          Normative Board class name
	 * @return none
	 */
	private void setNormativeBoard(String normativeBoardClass) {
		try {
			@SuppressWarnings("unchecked")
			Class<NormativeBoardInterface> nbClass = (Class<NormativeBoardInterface>) Class
					.forName(normativeBoardClass);
			
			Constructor<NormativeBoardInterface> nbConstructor = nbClass
					.getDeclaredConstructor();
			
			this.normativeBoard = nbConstructor.newInstance();
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(NoSuchMethodException e) {
			e.printStackTrace();
		} catch(InvocationTargetException e) {
			e.printStackTrace();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		} catch(InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Set Norm Recognition
	 * 
	 * @param normRecognitionClass
	 *          Norm Recognition class name
	 * @return none
	 */
	private void setNormRecognition(String normRecognitionClass) {
		try {
			@SuppressWarnings("unchecked")
			Class<NormRecognitionAbstract> nrClass = (Class<NormRecognitionAbstract>) Class
					.forName(normRecognitionClass);
			
			Constructor<NormRecognitionAbstract> nrConstructor = nrClass
					.getDeclaredConstructor(Integer.class, NormativeBoardInterface.class);
			
			this.normRecognition = nrConstructor.newInstance(this.agentId,
					this.normativeBoard);
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(NoSuchMethodException e) {
			e.printStackTrace();
		} catch(InvocationTargetException e) {
			e.printStackTrace();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		} catch(InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Set Norm Adoption
	 * 
	 * @param normAdoptionClass
	 *          Norm Adoption class name
	 * @return none
	 */
	private void setNormAdoption(String normAdoptionClass) {
		try {
			@SuppressWarnings("unchecked")
			Class<NormAdoptionAbstract> naClass = (Class<NormAdoptionAbstract>) Class
					.forName(normAdoptionClass);
			
			Constructor<NormAdoptionAbstract> naConstructor = naClass
					.getDeclaredConstructor(NormativeBoardInterface.class);
			
			this.normAdoption = naConstructor.newInstance(this.normativeBoard);
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(NoSuchMethodException e) {
			e.printStackTrace();
		} catch(InvocationTargetException e) {
			e.printStackTrace();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		} catch(InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Set Norm Salience
	 * 
	 * @param normSalienceClass
	 *          Norm Salience class name
	 * @return none
	 */
	private void setNormSalience(String normSalienceClass) {
		try {
			@SuppressWarnings("unchecked")
			Class<NormSalienceAbstract> nsClass = (Class<NormSalienceAbstract>) Class
					.forName(normSalienceClass);
			
			Constructor<NormSalienceAbstract> nsConstructor = nsClass
					.getDeclaredConstructor(NormativeBoardInterface.class);
			
			this.normSalience = nsConstructor.newInstance(this.normativeBoard);
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(NoSuchMethodException e) {
			e.printStackTrace();
		} catch(InvocationTargetException e) {
			e.printStackTrace();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		} catch(InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Set Norm Enforcement
	 * 
	 * @param normEnforcementClass
	 *          Norm Enforcement class name
	 * @return none
	 */
	private void setNormEnforcement(String normEnforcementClass) {
		try {
			@SuppressWarnings("unchecked")
			Class<NormEnforcementAbstract> neClass = (Class<NormEnforcementAbstract>) Class
					.forName(normEnforcementClass);
			
			Constructor<NormEnforcementAbstract> neConstructor = neClass
					.getDeclaredConstructor(NormativeBoardInterface.class);
			
			this.normEnforcement = neConstructor.newInstance(this.normativeBoard);
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(NoSuchMethodException e) {
			e.printStackTrace();
		} catch(InvocationTargetException e) {
			e.printStackTrace();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		} catch(InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Set Norm Compliance
	 * 
	 * @param normComplianceClass
	 *          Norm Compliance class name
	 * @return none
	 */
	private void setNormCompliance(String normComplianceClass) {
		try {
			@SuppressWarnings("unchecked")
			Class<NormComplianceAbstract> ncClass = (Class<NormComplianceAbstract>) Class
					.forName(normComplianceClass);
			
			Constructor<NormComplianceAbstract> ncConstructor = ncClass
					.getDeclaredConstructor(NormativeBoardInterface.class);
			
			this.normCompliance = ncConstructor.newInstance(this.normativeBoard);
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(NoSuchMethodException e) {
			e.printStackTrace();
		} catch(InvocationTargetException e) {
			e.printStackTrace();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		} catch(InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Get Normative Board
	 * 
	 * @param none
	 * @return Normative Board
	 */
	public NormativeBoardInterface getNormativeBoard() {
		return this.normativeBoard;
	}
	
	
	@Override
	public void input(Object event) {
		if (event instanceof EventEntityAbstract) {
			EventEntityAbstract entity = (EventEntityAbstract) event;
			this.normRecognition.matchEvent(entity);
		}
	}
	
	
	@Override
	public Double getNormativeDrive(Integer normId) {
		return this.normCompliance.getNormativeDrive(normId);
	}
}