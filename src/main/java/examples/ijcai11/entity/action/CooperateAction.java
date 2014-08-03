package examples.ijcai11.entity.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import emilia.entity.action.ActionAbstract;

public class CooperateAction extends ActionAbstract {
	
	@SuppressWarnings("unused")
	private static final Logger	logger	= LoggerFactory
																					.getLogger(CooperateAction.class);
	
	
	public CooperateAction() {
		super(1, "COOPERATE");
	}
}