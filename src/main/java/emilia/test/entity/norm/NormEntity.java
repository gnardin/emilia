package emilia.test.entity.norm;

import emilia.entity.norm.NormContentInterface;
import emilia.entity.norm.NormEntityAbstract;

public class NormEntity extends NormEntityAbstract {
	
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