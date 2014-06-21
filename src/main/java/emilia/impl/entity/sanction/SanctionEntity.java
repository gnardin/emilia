package emilia.impl.entity.sanction;

import emilia.entity.sanction.SanctionContentInterface;
import emilia.entity.sanction.SanctionEntityAbstract;

public class SanctionEntity extends SanctionEntityAbstract {
	
	private Integer										sanctionId;
	
	private SanctionContentInterface	content;
	
	
	public SanctionEntity(Integer sanctionId, SanctionContentInterface content) {
		this.sanctionId = sanctionId;
		this.content = content;
	}
}