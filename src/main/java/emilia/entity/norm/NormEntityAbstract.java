package emilia.entity.norm;

import emilia.entity.EntityAbstract;

@SuppressWarnings("serial")
public abstract class NormEntityAbstract extends EntityAbstract {

	public enum Type {
		LEGAL, SOCIAL;
	}

	public enum SourceType {
		AUTHORITY, SET_AGENTS, DISTRIBUTED, IMPERSONAL;
	}

	protected Type type;
	protected SourceType source;
	protected NormContentInterface content;
	protected String context;
	protected String deontic;

	public NormEntityAbstract(long normId) {
		super(normId);
	}
}