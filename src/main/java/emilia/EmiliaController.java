package emilia;

import emilia.board.NormativeBoardEventType;
import emilia.board.NormativeBoardInterface;
import emilia.conf.EmiliaConf;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.NormativeEventType;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.adoption.NormAdoptionAbstract;
import emilia.modules.classifier.EventClassifierAbstract;
import emilia.modules.compliance.NormComplianceAbstract;
import emilia.modules.enforcement.NormEnforcementAbstract;
import emilia.modules.enforcement.NormEnforcementListener;
import emilia.modules.recognition.NormRecognitionAbstract;
import emilia.modules.salience.NormSalienceAbstract;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmiliaController extends EmiliaAbstract implements
		NormEnforcementListener {
	
	private static final Logger				logger	= LoggerFactory
																								.getLogger(EmiliaController.class);
	
	private String										xmlFilename;
	
	private String										xsdFilename;
	
	// Event Classifier module
	protected EventClassifierAbstract	eventClassifier;
	
	// Norm Recognition module
	protected NormRecognitionAbstract	normRecognition;
	
	// Norm Adoption module
	protected NormAdoptionAbstract		normAdoption;
	
	// Norm Salience module
	protected NormSalienceAbstract		normSalience;
	
	// Norm Enforcement module
	protected NormEnforcementAbstract	normEnforcement;
	
	// Norm Compliance module
	protected NormComplianceAbstract	normCompliance;
	
	// Normative Board
	protected NormativeBoardInterface	normativeBoard;
	
	protected EmiliaConf							conf;
	
	
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
	public EmiliaController(int agentId, String xmlFilename, String xsdFilename) {
		super(agentId);
		
		this.xmlFilename = xmlFilename;
		this.xsdFilename = xsdFilename;
	}
	
	
	/**
	 * Initialize the EMILIA controller
	 * 
	 * @param none
	 * @return True if initialized successfully, False otherwise
	 */
	public boolean init() {
		boolean initialize = false;
		
		if(EmiliaConf.isValid(xmlFilename, xsdFilename)) {
			this.conf = EmiliaConf.getConf(xmlFilename, xsdFilename);
			// Event Classifier
			logger.debug("Initializing [EVENT CLASSIFIER]");
			this.setEventClassifier(this.conf.getEventClassifierClass());
			logger.debug("Initialized [EVENT CLASSIFIER]");
			
			// Normative Board
			logger.debug("Initializing [NORMATIVE BOARD]");
			this.setNormativeBoard(this.conf.getNormativeBoardClass());
			logger.debug("Initialized [NORMATIVE BOARD]");
			
			// Norm Recognition
			logger.debug("Initializing [NORM RECOGNITION]");
			this.setNormRecognition(this.conf.getNormRecognitionClass());
			logger.debug("Initialized [NORM RECOGNITION]");
			
			// Norm Adoption
			logger.debug("Initializing [NORM ADOPTION]");
			this.setNormAdoption(this.conf.getNormAdoptionClass());
			this.normativeBoard.registerCallback(
					new ArrayList<NormativeBoardEventType>(Arrays.asList(
							NormativeBoardEventType.INSERT_NORM,
							NormativeBoardEventType.UPDATE_NORM,
							NormativeBoardEventType.UPDATE_SALIENCE)), this.normAdoption);
			logger.debug("Initialized [NORM ADOPTION]");
			
			// Norm Salience
			logger.debug("Initializing [NORM SALIENCE]");
			this.setNormSalience(this.conf.getNormSalienceClass());
			this.normRecognition.registerCallback(
					new ArrayList<Boolean>(Arrays.asList(true)),
					new ArrayList<NormativeEventType>(Arrays.asList(
							NormativeEventType.COMPLIANCE,
							NormativeEventType.COMPLIANCE_OBSERVED,
							NormativeEventType.COMPLIANCE_INFORMED,
							NormativeEventType.VIOLATION,
							NormativeEventType.VIOLATION_OBSERVED,
							NormativeEventType.VIOLATION_INFORMED,
							NormativeEventType.PUNISHMENT,
							NormativeEventType.PUNISHMENT_OBSERVED,
							NormativeEventType.PUNISHMENT_INFORMED,
							NormativeEventType.SANCTION,
							NormativeEventType.SANCTION_OBSERVED,
							NormativeEventType.SANCTION_INFORMED,
							NormativeEventType.COMPLIANCE_INVOCATION,
							NormativeEventType.COMPLIANCE_INVOCATION_OBSERVED,
							NormativeEventType.COMPLIANCE_INVOCATION_INFORMED,
							NormativeEventType.VIOLATION_INVOCATION,
							NormativeEventType.VIOLATION_INVOCATION_OBSERVED,
							NormativeEventType.VIOLATION_INVOCATION_INFORMED)),
					this.normSalience);
			logger.debug("Initialized [NORM SALIENCE]");
			
			// Norm Enforcement
			logger.debug("Initializing [NORM ENFORCEMENT]");
			this.setNormEnforcement(this.conf.getNormEnforcementClass());
			this.normRecognition.registerCallback(
					new ArrayList<Boolean>(Arrays.asList(true)),
					new ArrayList<NormativeEventType>(Arrays.asList(
							NormativeEventType.ACTION, NormativeEventType.ACTION_OBSERVED,
							NormativeEventType.ACTION_INFORMED,
							NormativeEventType.COMPLIANCE,
							NormativeEventType.COMPLIANCE_OBSERVED,
							NormativeEventType.COMPLIANCE_INFORMED,
							NormativeEventType.VIOLATION,
							NormativeEventType.VIOLATION_OBSERVED,
							NormativeEventType.VIOLATION_INFORMED,
							NormativeEventType.PUNISHMENT,
							NormativeEventType.PUNISHMENT_OBSERVED,
							NormativeEventType.PUNISHMENT_INFORMED,
							NormativeEventType.SANCTION,
							NormativeEventType.SANCTION_OBSERVED,
							NormativeEventType.SANCTION_INFORMED,
							NormativeEventType.COMPLIANCE_INVOCATION,
							NormativeEventType.COMPLIANCE_INVOCATION_OBSERVED,
							NormativeEventType.COMPLIANCE_INVOCATION_INFORMED,
							NormativeEventType.VIOLATION_INVOCATION,
							NormativeEventType.VIOLATION_INVOCATION_OBSERVED,
							NormativeEventType.VIOLATION_INVOCATION_INFORMED)),
					this.normEnforcement);
			
			this.normEnforcement.registerCallback(
					new ArrayList<NormativeEventType>(Arrays.asList(
							NormativeEventType.COMPLIANCE,
							NormativeEventType.COMPLIANCE_OBSERVED,
							NormativeEventType.COMPLIANCE_INFORMED,
							NormativeEventType.VIOLATION,
							NormativeEventType.VIOLATION_OBSERVED,
							NormativeEventType.VIOLATION_INFORMED)), this.normSalience);
			
			this.normEnforcement.registerNormEnforcement(this);
			logger.debug("Initialized [NORM ENFORCEMENT]");
			
			// Norm Compliance
			logger.debug("Initializing [NORM COMPLIANCE]");
			this.setNormCompliance(this.conf.getNormComplianceClass());
			logger.debug("Initialized [NORM COMPLIANCE]");
			
			initialize = true;
		}
		
		return initialize;
	}
	
	
	/**
	 * Set Event Classifier
	 * 
	 * @param eventClassifierClass
	 *          Event Classifier class name
	 * @return none
	 */
	protected void setEventClassifier(String eventClassifierClass) {
		try {
			@SuppressWarnings("unchecked")
			Class<EventClassifierAbstract> nbClass = (Class<EventClassifierAbstract>) Class
					.forName(eventClassifierClass);
			
			Constructor<EventClassifierAbstract> nbConstructor = nbClass
					.getDeclaredConstructor(Integer.class);
			
			this.eventClassifier = nbConstructor.newInstance(this.agentId);
			
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
	 * Set Normative Board
	 * 
	 * @param normativeBoardClass
	 *          Normative Board class name
	 * @return none
	 */
	protected void setNormativeBoard(String normativeBoardClass) {
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
	protected void setNormRecognition(String normRecognitionClass) {
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
	protected void setNormAdoption(String normAdoptionClass) {
		try {
			@SuppressWarnings("unchecked")
			Class<NormAdoptionAbstract> naClass = (Class<NormAdoptionAbstract>) Class
					.forName(normAdoptionClass);
			
			Constructor<NormAdoptionAbstract> naConstructor = naClass
					.getDeclaredConstructor(Integer.class, NormativeBoardInterface.class);
			
			this.normAdoption = naConstructor.newInstance(this.agentId,
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
	 * Set Norm Salience
	 * 
	 * @param normSalienceClass
	 *          Norm Salience class name
	 * @return none
	 */
	protected void setNormSalience(String normSalienceClass) {
		try {
			@SuppressWarnings("unchecked")
			Class<NormSalienceAbstract> nsClass = (Class<NormSalienceAbstract>) Class
					.forName(normSalienceClass);
			
			Constructor<NormSalienceAbstract> nsConstructor = nsClass
					.getDeclaredConstructor(Integer.class, NormativeBoardInterface.class);
			
			this.normSalience = nsConstructor.newInstance(this.agentId,
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
	 * Set Norm Enforcement
	 * 
	 * @param normEnforcementClass
	 *          Norm Enforcement class name
	 * @return none
	 */
	protected void setNormEnforcement(String normEnforcementClass) {
		try {
			@SuppressWarnings("unchecked")
			Class<NormEnforcementAbstract> neClass = (Class<NormEnforcementAbstract>) Class
					.forName(normEnforcementClass);
			
			Constructor<NormEnforcementAbstract> neConstructor = neClass
					.getDeclaredConstructor(Integer.class);
			
			this.normEnforcement = neConstructor.newInstance(this.agentId);
			
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
	protected void setNormCompliance(String normComplianceClass) {
		try {
			@SuppressWarnings("unchecked")
			Class<NormComplianceAbstract> ncClass = (Class<NormComplianceAbstract>) Class
					.forName(normComplianceClass);
			
			Constructor<NormComplianceAbstract> ncConstructor = ncClass
					.getDeclaredConstructor(Integer.class, NormativeBoardInterface.class);
			
			this.normCompliance = ncConstructor.newInstance(this.agentId,
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
	
	
	@Override
	public void input(Object event) {
		NormativeEventEntityAbstract normativeEvent = this.eventClassifier
				.classify(event);
		if(normativeEvent != null) {
			this.normRecognition.matchEvent(normativeEvent);
		}
	}
	
	
	@Override
	public void setInitialValues(int normId, Object initialValues) {
		this.normSalience.setInitialValue(normId, initialValues);
	}
	
	
	@Override
	public void update() {
		for(NormEntityAbstract norm : this.normativeBoard.getNorms()) {
			this.normSalience.updateSalience(norm.getId());
		}
		this.normEnforcement.update();
	}
	
	
	@Override
	public double getNormSalience(int normId) {
		return this.normCompliance.getNormativeDrive(normId);
	}
	
	
	@Override
	public NormEntityAbstract getNorm(int normId) {
		NormEntityAbstract norm = null;
		
		if(this.normativeBoard.hasNorm(normId)) {
			norm = this.normativeBoard.getNorm(normId);
		}
		
		return norm;
	}
	
	
	@Override
	public void addNormsSanctions(
			Map<NormEntityAbstract, List<SanctionEntityAbstract>> normsSanctions) {
		this.normativeBoard.addNormsSanctions(normsSanctions);
	}
	
	
	@Override
	public Map<NormEntityAbstract, List<SanctionEntityAbstract>> getNormsSanctions() {
		
		Map<NormEntityAbstract, List<SanctionEntityAbstract>> normsSanctions = this.normativeBoard
				.getNormsSanctions();
		
		return normsSanctions;
	}
	
	
	@Override
	public void updateNormsSanctions(
			Map<NormEntityAbstract, List<SanctionEntityAbstract>> normsSanctions) {
		this.updateNormsSanctions(normsSanctions);
	}
	
	
	@Override
	public void receive(NormativeEventEntityAbstract event,
			NormEntityAbstract norm, SanctionEntityAbstract sanction) {
		this.sendSanction(event, norm, sanction);
	}
}