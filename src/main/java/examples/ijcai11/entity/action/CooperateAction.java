package examples.ijcai11.entity.action;

import emilia.entity.action.ActionAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CooperateAction extends ActionAbstract {
	
	@SuppressWarnings("unused")
	private static final Logger	logger	= LoggerFactory
																					.getLogger(CooperateAction.class);
	
	
	public CooperateAction() {
		super(0, "COOPERATE");
	}
}