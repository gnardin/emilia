package emilia;

import static org.junit.Assert.assertEquals;
import emilia.board.NormativeBoardEventType;
import emilia.board.NormativeBoardInterface;
import emilia.entity.norm.NormContentInterface;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.NormSource;
import emilia.entity.norm.NormEntityAbstract.NormStatus;
import emilia.entity.norm.NormEntityAbstract.NormType;
import emilia.modules.adoption.NormAdoptionAbstract;
import examples.pgg.board.NormativeBoard;
import examples.pgg.modules.adoption.NormAdoptionController;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class EmiliaAdoptionTest {
	
	NormativeBoardInterface	normtiveBoard;
	
	NormAdoptionAbstract		normAdoption;
	
	
	@Before
	public void constructor() {
		this.normtiveBoard = new NormativeBoard();
		this.normAdoption = new NormAdoptionController(1, this.normtiveBoard);
		
		this.normtiveBoard.registerCallback(
				new ArrayList<NormativeBoardEventType>(Arrays.asList(
						NormativeBoardEventType.INSERT_NORM,
						NormativeBoardEventType.UPDATE_SALIENCE)), this.normAdoption);
	}
	
	
	@Test
	public void normAdoptionTest() {
		NormContent content = new NormContent("COOPERATE");
		NormEntity norm = new NormEntity(1, NormType.SOCIAL,
				NormSource.DISTRIBUTED, NormStatus.BELIEF, content);
		this.normtiveBoard.setNorm(norm);
		
		assertEquals(this.normtiveBoard.getNorm(1).getStatus(), NormStatus.GOAL);
	}
}


class NormEntity extends NormEntityAbstract {
	
	public NormEntity(Integer id, NormType type, NormSource source,
			NormStatus status, NormContentInterface content) {
		this.setId(id);
		this.setType(type);
		this.setSource(source);
		this.setStatus(status);
		this.setContent(content);
	}
}


class NormContent implements NormContentInterface {
	
	private String	content;
	
	
	public NormContent(String content) {
		this.content = content;
	}
	
	
	@Override
	public Boolean match(Object value) {
		Boolean result = false;
		
		if(value instanceof String) {
			if(this.content.equals((String) value)) {
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