package emilia.impl.entity.norm;

import emilia.entity.norm.NormContentInterface;
import emilia.entity.norm.NormEntityAbstract;

public class NormEntity extends NormEntityAbstract {
	
	public NormEntity(Integer id, Type type, Source source, Status status,
			String context, NormContentInterface content, Double salience) {
		this.setId(id);
		this.setType(type);
		this.setSource(source);
		this.setStatus(status);
		this.setContext(context);
		this.setContent(content);
		this.setSalience(salience);
	}
}