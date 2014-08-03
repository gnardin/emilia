package examples.ijcai11.modules.enforcement;

import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import emilia.board.NormativeBoardInterface;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.type.ActionEvent;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.norm.NormEntityAbstract.NormStatus;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.enforcement.DeviationAbstract;
import emilia.modules.enforcement.DeviationAbstract.Type;
import emilia.modules.enforcement.NormEnforcementAbstract;
import emilia.modules.enforcement.NormInfoEntityInterface;
import examples.ijcai11.entity.norm.NormContent;
import examples.ijcai11.entity.sanction.SanctionContent;
import examples.ijcai11.entity.sanction.SanctionContent.Sanction;
import examples.ijcai11.entity.sanction.SanctionEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NormEnforcementController extends NormEnforcementAbstract {
	
	@SuppressWarnings("unused")
	private static final Logger			logger		= LoggerFactory
																								.getLogger(NormEnforcementController.class);
	
	private final static String			EVALUATE	= "evaluate";
	
	private Uniform									rnd;
	
	private Integer									defectors;
	
	private Integer									lastDefectors;
	
	private Integer									tolerance;
	
	private Double									delta_cost;
	
	private NormativeBoardInterface	normativeBoard;
	
	
	/**
	 * Create Norm Enforcement controller
	 * 
	 * @param agentId
	 *          Agent identification
	 * @return none
	 */
	public NormEnforcementController(Integer agentId, Integer tolerance,
			Double delta_cost, NormativeBoardInterface normativeBoard) {
		super(agentId);
		this.rnd = new Uniform(0.0, 1.0, new MersenneTwister());
		this.defectors = 0;
		this.lastDefectors = 0;
		this.tolerance = tolerance;
		this.delta_cost = delta_cost;
		this.normativeBoard = normativeBoard;
	}
	
	
	@Override
	public Map<NormEntityAbstract, DeviationAbstract> detect(
			NormativeEventEntityAbstract event,
			Map<NormEntityAbstract, List<SanctionEntityAbstract>> normSanctions) {
		
		Map<NormEntityAbstract, DeviationAbstract> deviations = new HashMap<NormEntityAbstract, DeviationAbstract>();
		
		NormContent normContent;
		ActionEvent actionEvent;
		for(NormEntityAbstract norm : normSanctions.keySet()) {
			if (norm.getContent() instanceof NormContent) {
				normContent = (NormContent) norm.getContent();
				
				if (event instanceof ActionEvent) {
					actionEvent = (ActionEvent) event;
					
					if (actionEvent.getAction().getDescription()
							.equalsIgnoreCase(normContent.getAction().getDescription())) {
						deviations.put(norm, new ComplianceDeviation());
					} else if (actionEvent.getAction().getDescription()
							.equalsIgnoreCase(normContent.getNotAction().getDescription())) {
						deviations.put(norm, new ViolationDeviation());
					}
				}
			}
		}
		
		return deviations;
	}
	
	
	@Override
	public void adapt(NormativeEventEntityAbstract event,
			NormEntityAbstract norm, DeviationAbstract evaluation) {
		
		Integer normId = norm.getId();
		
		NormInfoEntityContent normInfo;
		if (this.memory.hasNormInfo(normId)) {
			normInfo = (NormInfoEntityContent) this.memory.getNormInfo(normId);
		} else {
			normInfo = new NormInfoEntityContent();
		}
		
		if (evaluation.getType().equals(DeviationAbstract.Type.COMPLIANCE)) {
			normInfo.setCompliants(normInfo.getCompliants() + 1);
		} else if (evaluation.getType().equals(DeviationAbstract.Type.VIOLATION)) {
			normInfo.setDefectors(normInfo.getDefectors() + 1);
		}
		
		this.memory.setNormInfo(normId, normInfo);
		
		if (event.hasContextAttr(EVALUATE)) {
			normInfo = (NormInfoEntityContent) this.memory.getNormInfo(normId);
			
			this.defectors = normInfo.getDefectors();
			Double delta = 0.0;
			if ((this.lastDefectors <= this.defectors)
					&& (this.defectors > this.tolerance)) {
				delta = this.delta_cost;
			} else if ((this.lastDefectors > this.defectors)
					|| (this.defectors < this.tolerance)) {
				delta = (-1) * this.delta_cost;
			}
			
			SanctionEntity newSanction;
			SanctionContent sanctionContent;
			SanctionContent newSanctionContent;
			Double newSanctionAmount;
			List<SanctionEntityAbstract> newSanctions;
			Map<NormEntityAbstract, List<SanctionEntityAbstract>> update = new HashMap<NormEntityAbstract, List<SanctionEntityAbstract>>();
			Map<NormEntityAbstract, List<SanctionEntityAbstract>> normsSanctions = this.normativeBoard
					.getNormsSanctions();
			for(NormEntityAbstract normEntity : normsSanctions.keySet()) {
				
				newSanctions = new ArrayList<SanctionEntityAbstract>();
				for(SanctionEntityAbstract sanction : normsSanctions.get(normEntity)) {
					sanctionContent = (SanctionContent) sanction.getContent();
					
					// Update sanction information, except Message sanction
					if (!sanctionContent.getAction().equals(Sanction.MESSAGE)) {
						newSanctionAmount = sanctionContent.getAmount() + delta;
						if (newSanctionAmount < 0) {
							newSanctionAmount = 0.0;
						}
						
						newSanctionContent = new SanctionContent(
								sanctionContent.getAction(), newSanctionAmount,
								sanctionContent.getCost());
						
						newSanction = new SanctionEntity(sanction.getId(),
								sanction.getCategory(), sanction.getStatus(),
								newSanctionContent);
						newSanctions.add(newSanction);
					}
				}
				
				update.put(normEntity, newSanctions);
			}
			this.normativeBoard.updateNormsSanctions(update);
		}
	}
	
	
	@Override
	public List<SanctionEntityAbstract> evaluate(
			NormativeEventEntityAbstract event, NormEntityAbstract norm,
			List<SanctionEntityAbstract> sanctions, DeviationAbstract evaluation) {
		
		List<SanctionEntityAbstract> enforceSanctions = new ArrayList<SanctionEntityAbstract>();
		
		if (event.hasContextAttr(EVALUATE)) {
			Integer normId = norm.getId();
			NormInfoEntityContent normContent = (NormInfoEntityContent) this.memory
					.getNormInfo(normId);
			
			if ((evaluation.getType().equals(Type.VIOLATION))
					&& (!sanctions.isEmpty())) {
				
				if (norm.getStatus().equals(NormStatus.GOAL)) {
					
					Sanction type;
					Double normSalience = this.normativeBoard.getSalience(normId);
					// SEND A SANCTION
					if (this.rnd.nextDouble() < normSalience) {
						type = Sanction.SANCTION;
						
						// SEND A MESSAGE
					} else {
						type = Sanction.MESSAGE;
					}
					
					SanctionContent content;
					for(SanctionEntityAbstract sanction : sanctions) {
						content = (SanctionContent) sanction.getContent();
						if (content.getAction().equals(type)) {
							enforceSanctions.add(sanction);
						}
					}
				} else {
					
					Double probPunishment = (double) normContent.getDefectors()
							/ (double) (normContent.getCompliants() + normContent
									.getDefectors());
					
					// SELECT A PUNISHMENT
					if (this.rnd.nextDouble() < probPunishment) {
						SanctionContent content;
						for(SanctionEntityAbstract sanction : sanctions) {
							content = (SanctionContent) sanction.getContent();
							if (content.getAction().equals(Sanction.PUNISHMENT)) {
								enforceSanctions.add(sanction);
							}
						}
					}
				}
			}
		}
		
		return enforceSanctions;
	}
	
	
	@Override
	public void enforce(NormativeEventEntityAbstract event,
			NormEntityAbstract norm, List<SanctionEntityAbstract> sanctions) {
	}
	
	
	@Override
	public void update() {
		Map<Integer, NormInfoEntityInterface> normInfo = this.memory.getNormsInfo();
		NormInfoEntityContent normContent;
		for(Integer normId : normInfo.keySet()) {
			normContent = (NormInfoEntityContent) normInfo.get(normId);
			normContent.reset();
			this.memory.setNormInfo(normId, normContent);
		}
		
		this.lastDefectors = this.defectors;
	}
}