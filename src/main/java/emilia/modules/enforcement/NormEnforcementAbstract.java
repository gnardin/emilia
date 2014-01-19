package emilia.modules.enforcement;

import emilia.modules.classifier.EventClassifierListener;

public abstract class NormEnforcementAbstract extends EventClassifierListener {

	public NormEnforcementAbstract(int id) {
		super(id);
	}

	public abstract void monitor();

	public abstract void detect();

	public abstract void evaluate();

	public abstract void enforce();

	public abstract void adapt();
}