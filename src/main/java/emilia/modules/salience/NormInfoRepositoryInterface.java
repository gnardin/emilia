package emilia.modules.salience;

public interface NormInfoRepositoryInterface {
	
	public void increment(Integer normId, DataType dataType);
	
	
	public Integer getNormInfo(Integer normId, DataType dataType);
}