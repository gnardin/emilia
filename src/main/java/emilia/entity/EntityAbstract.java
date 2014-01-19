package emilia.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class EntityAbstract implements Serializable {

	protected long entityId;

	/**
	 * Constructor
	 * 
	 * @param none
	 * @return none
	 */
	public EntityAbstract() {
		this.entityId = -1;
	}

	/**
	 * Constructor
	 * 
	 * @param entityId
	 *            Entity identification
	 * @return none
	 */
	public EntityAbstract(long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Get entity identification
	 * 
	 * @param entityId
	 *            Entity identification
	 * @return none
	 */
	public long getId() {
		return this.entityId;
	}
}