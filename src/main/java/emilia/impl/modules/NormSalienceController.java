package emilia.impl.modules;

import emilia.board.NormativeBoardInterface;
import emilia.modules.salience.DataType;
import emilia.modules.salience.NormInfoRepositoryMemory;
import emilia.modules.salience.NormSalienceAbstract;

public class NormSalienceController extends NormSalienceAbstract {
	
	// Weight values
	public enum Weight {
		WC(0.99),
		WO(0.33),
		WNPV(-0.66),
		WP(0.33),
		WS(0.99),
		WE(0.99);
		
		private final Double	value;
		
		
		private Weight(Double value) {
			this.value = value;
		}
		
		
		public double getValue() {
			return this.value;
		}
	};
	
	
	/**
	 * Constructor
	 * 
	 * @param moduleId
	 *          Norm salience controller identification
	 * @param normativeBoard
	 *          Normative board
	 * @return none
	 */
	public NormSalienceController(NormativeBoardInterface normativeBoard) {
		super(normativeBoard);
		this.repository = new NormInfoRepositoryMemory();
	}
	
	
	/**
	 * Update salience
	 * 
	 * @param normId
	 *          Norm identification
	 * @param dataType
	 *          Data Type
	 * @return none
	 */
	@Override
	public void updateSalience(Integer normId) {
		// Get the Salience from the Normative Board
		double salience = this.normativeBoard.getSalience(normId);
		
		// Calculate the new value based on the info received
		int compliance = this.repository.getNormInfo(normId, DataType.COMPLIANCE);
		int violation = this.repository.getNormInfo(normId, DataType.VIOLATION);
		int obsCompliance = this.repository.getNormInfo(normId,
				DataType.COMPLIANCE_OBSERVED);
		int obsViolation = this.repository.getNormInfo(normId,
				DataType.VIOLATION_OBSERVED);
		int punishment = this.repository.getNormInfo(normId, DataType.PUNISHMENT);
		int sanction = this.repository.getNormInfo(normId, DataType.SANCTION);
		int normInvocationCompliance = this.repository.getNormInfo(normId,
				DataType.COMPLIANCE_INVOKED);
		int normInvocationViolation = this.repository.getNormInfo(normId,
				DataType.VIOLATION_INVOKED);
		
		double own = 0;
		if ((compliance + violation) > 0) {
			own = (double) (compliance - violation)
					/ (double) (compliance + violation);
		}
		
		double obs = 0;
		if ((obsCompliance + obsViolation) > 0) {
			obs = (double) (obsCompliance - obsViolation)
					/ (double) (obsCompliance + obsViolation);
		}
		
		double npv = 0;
		if ((obsViolation + violation) > 0) {
			npv = (double) Math.max(0, (obsViolation + violation) - punishment
					- sanction)
					/ (double) (obsViolation + violation);
		}
		
		double p = 0;
		if ((Math.max(punishment + sanction, obsViolation + violation)) > 0) {
			p = (double) punishment
					/ (double) (Math.max(punishment + sanction, obsViolation + violation));
		}
		
		double s = 0;
		if ((Math.max(punishment + sanction, obsViolation + violation)) > 0) {
			s = (double) sanction
					/ (double) (Math.max(punishment + sanction, obsViolation + violation));
		}
		
		double e = 0;
		if ((normInvocationCompliance + normInvocationViolation) > 0) {
			e = (double) (normInvocationCompliance - normInvocationViolation)
					/ (double) (normInvocationCompliance + normInvocationViolation);
		}
		
		salience = (double) (2.97 + ((own * Weight.WC.getValue())
				+ (obs * Weight.WO.getValue()) + (npv * Weight.WNPV.getValue())
				+ (p * Weight.WP.getValue()) + (s * Weight.WS.getValue()) + (e * Weight.WE
				.getValue()))) / (double) 6.27;
		
		// Set the Salience in the Normative Board
		this.normativeBoard.setSalience(normId, salience);
	}
}