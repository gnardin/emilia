package examples.pgg.entity.sanction;

import emilia.entity.sanction.SanctionCategory;
import emilia.entity.sanction.SanctionContentInterface;
import emilia.entity.sanction.SanctionEntityAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SanctionEntity extends SanctionEntityAbstract {
	
	@SuppressWarnings("unused")
	private static final Logger	logger	= LoggerFactory
																					.getLogger(SanctionEntity.class);
	
	
	public SanctionEntity(Integer id, SanctionCategory category,
			SanctionStatus status, SanctionContentInterface content) {
		this.setId(id);
		this.setCategory(category);
		this.setStatus(status);
		this.setContent(content);
	}
}