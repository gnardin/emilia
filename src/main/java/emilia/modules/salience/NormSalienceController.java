package emilia.modules.salience;

import emilia.board.normative.NormativeBoardInterface;
import emilia.entity.event.EventContentAbstract;
import emilia.modules.salience.NormSalienceAbstract;

public class NormSalienceController extends NormSalienceAbstract {

	private final double wc = 0.99;
	private final double wo = 0.33;
	private final double wnpv = -0.66;
	private final double wp = 0.33;
	private final double ws = 0.99;
	private final double we = 0.99;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Norm salience controller identification
	 * @param normativeBoard
	 *            Normative board
	 * @return none
	 */
	public NormSalienceController(int id, NormativeBoardInterface normativeBoard) {
		super(id, normativeBoard);
		this.repository = new NormInfoRepositoryMemory();
	}

	/**
	 * Update salience
	 * 
	 * @param event
	 *            Event content
	 * @return none
	 */
	@Override
	public void updateSalience(EventContentAbstract content) {
		int normId = content.getNormId();

		// Get the Salience from the Normative Board
		double salience = this.normativeBoard.getSalience(normId);

		// Calculate the new value based on the info received
		int compliance = this.repository.getNormInfo(normId,
				EventContentAbstract.Type.COMPLIANCE.ordinal());
		int violation = this.repository.getNormInfo(normId,
				EventContentAbstract.Type.VIOLATION.ordinal());
		int obsCompliance = this.repository.getNormInfo(normId,
				EventContentAbstract.Type.OBSERVED_COMPLIANCE.ordinal());
		int obsViolation = this.repository.getNormInfo(normId,
				EventContentAbstract.Type.OBSERVED_VIOLATION.ordinal());
		int punishment = this.repository.getNormInfo(normId,
				EventContentAbstract.Type.PUNISHMENT.ordinal());
		int sanction = this.repository.getNormInfo(normId,
				EventContentAbstract.Type.SANCTION.ordinal());
		int normInvocationCompliance = this.repository.getNormInfo(normId,
				EventContentAbstract.Type.NORM_INVOCATION_COMPLIANCE.ordinal());
		int normInvocationViolation = this.repository.getNormInfo(normId,
				EventContentAbstract.Type.NORM_INVOCATION_VIOLATION.ordinal());

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
					/ (double) (Math.max(punishment + sanction, obsViolation
							+ violation));
		}

		double s = 0;
		if ((Math.max(punishment + sanction, obsViolation + violation)) > 0) {
			s = (double) sanction
					/ (double) (Math.max(punishment + sanction, obsViolation
							+ violation));
		}

		double e = 0;
		if ((normInvocationCompliance + normInvocationViolation) > 0) {
			e = (double) (normInvocationCompliance - normInvocationViolation)
					/ (double) (normInvocationCompliance + normInvocationViolation);
		}

		// System.out.println(own + " " + obs + " " + npv + " " + p + " " + s
		// + " " + e);

		salience = (double) (2.97 + ((own * wc) + (obs * wo) + (npv * wnpv)
				+ (p * wp) + (s * ws) + (e * we)))
				/ (double) 6.60;

		// Set the Salience in the Normative Board
		this.normativeBoard.setSalience(normId, salience);
	}
}