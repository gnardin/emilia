package examples.pgg.board;

import emilia.board.NormativeBoardAbstract;
import emilia.entity.norm.NormEntityAbstract;
import java.util.ArrayList;
import java.util.List;

public class NormativeBoard extends NormativeBoardAbstract {
	
	@Override
	public List<NormEntityAbstract> match(Object info) {
		
		List<NormEntityAbstract> result = new ArrayList<NormEntityAbstract>();
		NormEntityAbstract norm;
		
		// Norm identification
		if (info instanceof Integer) {
			norm = this.norms.get((Integer) info);
			result.add(norm);
			
			// Norm content
		} else if (info instanceof String) {
			for(Integer normId : this.norms.keySet()) {
				norm = this.norms.get(normId);
				if (norm.getContent().match((String) info)) {
					result.add(norm);
				}
			}
		}
		
		return result;
	}
}