package examples.pgg.entity.norm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import emilia.entity.norm.NormContentInterface;
import emilia.entity.norm.NormEntityAbstract;

public class NormEntity extends NormEntityAbstract {
	
	@SuppressWarnings("unused")
	private static final Logger	logger	= LoggerFactory
																					.getLogger(NormEntity.class);
	
	
	public NormEntity(Integer id, NormType type, NormSource source,
			NormStatus status, NormContentInterface content, Double salience) {
		this.setId(id);
		this.setType(type);
		this.setSource(source);
		this.setStatus(status);
		this.setContent(content);
		this.setSalience(salience);
	}
}