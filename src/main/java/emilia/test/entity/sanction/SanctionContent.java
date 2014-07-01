package emilia.impl.entity.sanction;

import emilia.entity.sanction.SanctionContentInterface;

public class SanctionContent implements SanctionContentInterface {
	
	private String	content;
	
	
	public SanctionContent(String content) {
		this.content = content;
	}
	
	
	@Override
	public String toString() {
		return this.content;
	}
}