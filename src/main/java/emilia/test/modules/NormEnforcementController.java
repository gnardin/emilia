package emilia.test.modules;

import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import emilia.entity.event.NormativeEventEntityAbstract;
import emilia.entity.event.type.ActionEvent;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.sanction.SanctionCategory.Polarity;
import emilia.entity.sanction.SanctionEntityAbstract;
import emilia.modules.enforcement.DeviationAbstract;
import emilia.modules.enforcement.DeviationAbstract.Type;
import emilia.modules.enforcement.NormEnforcementAbstract;
import emilia.test.entity.norm.NormContent;
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
	 * @param none
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
		
		SanctionEntityAbstract sanction;
		if (newSanctions.size() > 0) {
			sanction = newSanctions.get(this.ufDist.nextIntFromTo(0,
					(newSanctions.size() - 1)));
			enforceSanctions.add(sanction);
		}
		
		return enforceSanctions;
	}
	
	
	@Override
	public void enforce(NormEntityAbstract norm,
			List<SanctionEntityAbstract> sanctions) {
	}
	
	
	@Override
	public void adapt(NormativeEventEntityAbstract event,
			NormEntityAbstract norm, DeviationAbstract evaluation) {
	}
}


/**
 * Compliance deviation class
 */
class ComplianceDeviation extends DeviationAbstract {
	
	/**
	 * Create a compliance deviation
	 * 
	 * @param none
	 * @return none
	 */
	public ComplianceDeviation() {
		super(Type.COMPLIANCE);
	}
}


/**
 * Violation deviation class
 */
class ViolationDeviation extends DeviationAbstract {
	
	/**
	 * Create a violation deviation
	 * 
	 * @param none
	 * @return none
	 */
	public ViolationDeviation() {
		super(Type.VIOLATION);
	}
}