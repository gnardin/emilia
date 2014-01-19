package emilia;

import emilia.entity.event.EventEntity;
import emilia.entity.event.EventEntity.EventType;
import emilia.modules.adoption.NormAdoptionAbstract;
import emilia.modules.classifier.EventClassifierAbstract;
import emilia.modules.compliance.NormComplianceAbstract;
import emilia.modules.enforcement.NormEnforcementAbstract;
import emilia.modules.recognition.NormRecognitionAbstract;
import emilia.modules.salience.NormSalienceAbstract;

public class EmiliaController {

	private EventClassifierAbstract eventClassifier;

	private NormRecognitionAbstract normRecognition;

	private NormAdoptionAbstract normAdoption;

	private NormSalienceAbstract normSalience;

	private NormEnforcementAbstract normEnforcement;

	private NormComplianceAbstract normCompliance;

	/**
	 * Constructor
	 * 
	 * @param none
	 * @return none
	 */
	public EmiliaController(EventClassifierAbstract eventClassifier,
			NormRecognitionAbstract normRecognition,
			NormAdoptionAbstract normAdoption,
			NormSalienceAbstract normSalience,
			NormEnforcementAbstract normEnforcement,
			NormComplianceAbstract normCompliance) {

		this.eventClassifier = eventClassifier;

		// Norm Recognition
		this.normRecognition = normRecognition;
		this.eventClassifier.registerCallback(EventType.ACTION,
				this.normRecognition);
		this.eventClassifier.registerCallback(EventType.INFORMATION,
				this.normRecognition);

		// Norm Adoption
		this.normAdoption = normAdoption;

		// Norm Salience
		this.normSalience = normSalience;
		this.eventClassifier.registerCallback(EventType.INFORMATION,
				this.normSalience);

		// Norm Enforcement
		this.normEnforcement = normEnforcement;
		this.eventClassifier.registerCallback(EventType.ACTION,
				this.normEnforcement);

		// Norm Compliance
		this.normCompliance = normCompliance;
	}

	/**
	 * Input event message
	 * 
	 * @param event
	 *            Event message
	 * @return none
	 */
	public void input(EventEntity event) {
		this.eventClassifier.input(event);
	}

	/**
	 * Get the normative drive
	 * 
	 * @param normId
	 *            Norm identification
	 * @return Normative drive
	 */
	public double getNormativeDrive(int normId) {
		return this.normCompliance.getNormativeDrive(normId);
	}
}