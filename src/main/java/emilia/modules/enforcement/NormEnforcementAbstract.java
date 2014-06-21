package emilia.modules.enforcement;

import emilia.modules.recognition.EventListener;

public abstract class NormEnforcementAbstract implements
		EventListener {
	
	public abstract void monitor();
	
	
	public abstract void detect();
	
	
	public abstract void evaluate();
	
	
	public abstract void enforce();
	
	
	public abstract void adapt();
}