package emilia.entity.norm;

import emilia.entity.EntityInterface;

public abstract class NormEntityAbstract implements EntityInterface {

	public enum Type {
		LEGAL, SOCIAL;
	}

	public enum SourceType {
		AUTHORITY, SET_AGENTS, DISTRIBUTED, IMPERSONAL;
	}

	protected int id;
	protected Type type;
	protected SourceType source;
	protected NormContentInterface content;
	protected String context;
	protected String deontic;
}