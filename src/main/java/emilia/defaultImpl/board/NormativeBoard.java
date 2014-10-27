package emilia.defaultImpl.board;

import emilia.board.NormativeBoardAbstract;
import emilia.entity.norm.NormEntityAbstract;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NormativeBoard extends NormativeBoardAbstract {
	
	@SuppressWarnings("unused")
	private static final Logger	logger	= LoggerFactory
																					.getLogger(NormativeBoard.class);
	
	
	@Override
	public List<NormEntityAbstract> match(Object info) {
		
		List<NormEntityAbstract> result = new ArrayList<NormEntityAbstract>();
		
		// Norm identification
		if(info instanceof Integer) {
			NormEntityAbstract norm = this.norms.get((Integer) info);
			result.add(norm);
			
			// Norm content
		} else if(info instanceof String) {
			for(Integer normId : this.norms.keySet()) {
				NormEntityAbstract norm = this.norms.get(normId);
				if(norm.getContent().match((String) info)) {
					result.add(norm);
				}
			}
		}
		
		return result;
	}
}