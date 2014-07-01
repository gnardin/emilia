package emilia.test.entity.sanction;

import emilia.entity.sanction.SanctionCategory;
import emilia.entity.sanction.SanctionContentInterface;
import emilia.entity.sanction.SanctionEntityAbstract;

public class SanctionEntity extends SanctionEntityAbstract {
	
	public SanctionEntity(Integer id, SanctionCategory category,
			SanctionStatus status, SanctionContentInterface content) {
		this.setId(id);
		this.setCategory(category);
		this.setStatus(status);
		this.setContent(content);
	}
}