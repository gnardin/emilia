package emilia.recognition;

import emilia.entity.norm.NormEntityAbstract;

public abstract class Recognition {

	protected NormEntityAbstract[] candidateNormativeBelief;
	protected String[] behaviourBase;

	// Event is the most primitive element in the environment
	public void input(String content) {
		if (content.equals("Norm")) {
			this.updateCandidateNormativeBelief(content);
		} else if (content.equals("Behaviour")) {
			this.updateBehaviour(content);
		} else if (content.equals("Request")) {
			this.updateRequest(content);
		} else if (content.equals("Assertion")) {
			this.updateAssertion(content);
		}
	}

	public abstract void updateBehaviour(String behaviour);

	public abstract void updateRequest(String request);

	public abstract void updateAssertion(String assertion);

	public abstract void updateCandidateNormativeBelief(String norm);
}