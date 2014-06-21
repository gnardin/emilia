package emilia.entity.sanction;

import emilia.entity.EntityAbstract;

public abstract class SanctionEntityAbstract extends EntityAbstract {
	
	public enum SanctionType {
	}
	
	protected Integer										id;
	
	protected SanctionType							type;
	
	protected SanctionContentInterface	content;
	
	protected String										context;
	
	protected String										deontic;
}