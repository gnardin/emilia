package emilia.impl.entity.norm;

import emilia.entity.norm.NormContentInterface;

public class NormContent implements NormContentInterface {
	
	private String	content;
	
	
	public NormContent(String content) {
		this.content = content;
	}
	
	
	@Override
	public Boolean match(Object value) {
		Boolean result = false;
		
		if (value instanceof String) {
			if (this.content.equalsIgnoreCase((String) value)) {
				return true;
			}
		}
		
		return result;
	}
	
	
	@Override
	public String toString() {
		return this.content;
	}
}