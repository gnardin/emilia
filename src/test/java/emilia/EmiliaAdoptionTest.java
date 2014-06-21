package emilia;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import emilia.board.NormativeBoardInterface;
import emilia.board.NormativeEventType;
import emilia.entity.norm.NormContentInterface;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.Source;
import emilia.entity.norm.NormEntityAbstract.Status;
import emilia.entity.norm.NormEntityAbstract.Type;
import emilia.impl.board.NormativeBoard;
import emilia.impl.modules.NormAdoptionController;
import emilia.modules.adoption.NormAdoptionAbstract;

public class EmiliaAdoptionTest {
	
	NormativeBoardInterface	normtiveBoard;
	
	NormAdoptionAbstract		normAdoption;
	
	
	@Before
	public void constructor() {
		this.normtiveBoard = new NormativeBoard();
		this.normAdoption = new NormAdoptionController(this.normtiveBoard);
		
		this.normtiveBoard
				.registerCallback(
						new ArrayList<NormativeEventType>(Arrays.asList(
								NormativeEventType.INSERT_NORM,
								NormativeEventType.UPDATE_SALIENCE)), this.normAdoption);
	}
	
	
	@Test
	public void test() {
		NormContent content = new NormContent("COOPERATE");
		NormEntity norm = new NormEntity(1, Type.SOCIAL, Source.DISTRIBUTED,
				Status.BELIEF, "ALL", content);
		this.normtiveBoard.setNorm(norm);
		
		assertEquals(this.normtiveBoard.getNorm(1).getStatus(), Status.GOAL);
	}
}


class NormEntity extends NormEntityAbstract {
	
	public NormEntity(Integer id, Type type, Source source, Status status,
			String context, NormContentInterface content) {
		this.setId(id);
		this.setType(type);
		this.setSource(source);
		this.setStatus(status);
		this.setContext(context);
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
		
		if (value instanceof String) {
			if (this.content.equals((String) value)) {
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