package emilia.modules.salience;

import emilia.entity.event.EventContentAbstract;

public interface NormInfoRepositoryInterface {

	public void increment(EventContentAbstract content);
	
	public int getNormInfo(int normId, int dataType);
}