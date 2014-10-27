package emilia.defaultImpl.modules.enforcement;

import emilia.defaultImpl.entity.norm.NormContent;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.type.ActionEvent;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.sanction.SanctionCategory.Polarity;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.enforcement.DeviationAbstract;
import emilia.modules.enforcement.DeviationAbstract.Type;
import emilia.modules.enforcement.NormEnforcementAbstract;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NormEnforcementController extends NormEnforcementAbstract {
	
	@SuppressWarnings("unused")
	private static final Logger	logger	= LoggerFactory
																					.getLogger(NormEnforcementController.class);
	
	private MersenneTwister			gen;
	
	private Uniform							ufDist;
	
	
	/**
	 * Create Norm Enforcement controller
	 * 
	 * @param agentId
	 *          Agent identification
	 * @return none
	 */
	public NormEnforcementController(Integer agentId) {
		super(agentId);
		this.gen = new MersenneTwister();
		this.ufDist = new Uniform(this.gen);
	}
	
	
	@Override
	public Map<NormEntityAbstract, DeviationAbstract> detect(
			NormativeEventEntityAbstract event,
			Map<NormEntityAbstract, List<SanctionEntityAbstract>> normSanctions) {
		
		Map<NormEntityAbstract, DeviationAbstract> deviations = new HashMap<NormEntityAbstract, DeviationAbstract>();
		
		for(NormEntityAbstract norm : normSanctions.keySet()) {
			if (norm.getContent() instanceof NormContent) {
				NormContent normContent = (NormContent) norm.getContent();
				
				if (event instanceof ActionEvent) {
					ActionEvent actionEvent = (ActionEvent) event;
					
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
	public List<SanctionEntityAbstract> evaluate(
			NormativeEventEntityAbstract event, NormEntityAbstract norm,
			List<SanctionEntityAbstract> sanctions, DeviationAbstract evaluation) {
		
		List<SanctionEntityAbstract> enforceSanctions = new ArrayList<SanctionEntityAbstract>();
		
		Polarity polarity;
		if (evaluation.getType().equals(Type.VIOLATION)) {
			polarity = Polarity.NEGATIVE;
		} else {
			polarity = Polarity.POSITIVE;
		}
		
		List<SanctionEntityAbstract> newSanctions = new ArrayList<SanctionEntityAbstract>();
		for(SanctionEntityAbstract sanction : sanctions) {
			if (sanction.getCategory().getPolarity().equals(polarity)) {
				newSanctions.add(sanction);
			}
		}
		
		if (newSanctions.size() > 0) {
			SanctionEntityAbstract sanction = newSanctions.get(this.ufDist
					.nextIntFromTo(0, (newSanctions.size() - 1)));
			enforceSanctions.add(sanction);
		}
		
		return enforceSanctions;
	}
	
	
	@Override
	public void enforce(NormativeEventEntityAbstract event,
			NormEntityAbstract norm, List<SanctionEntityAbstract> sanctions) {
	}
	
	
	@Override
	public void adapt(NormativeEventEntityAbstract event,
			NormEntityAbstract norm, DeviationAbstract evaluation) {
	}
	
	
	@Override
	public void update() {
	}
}