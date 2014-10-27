package emilia.board;

import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.sanction.SanctionEntityAbstract;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class NormativeBoardAbstract implements NormativeBoardInterface {
	
	@SuppressWarnings("unused")
	private static final Logger																						logger	= LoggerFactory
																																										.getLogger(NormativeBoardAbstract.class);
	
	// Norm entities <NormId, NormEntity>
	protected Map<Integer, NormEntityAbstract>														norms;
	
	// Sanction entities <SanctionId, SanctionEntity>
	protected Map<Integer, SanctionEntityAbstract>												sanctions;
	
	// Norm x Sanction <NormId, List<SanctionId>>
	protected Map<Integer, List<Integer>>																	normSanctions;
	
	// Callbacks
	protected Map<NormativeBoardEventType, List<NormativeBoardListener>>	callbacks;
	
	
	/**
	 * Create a normative board
	 * 
	 * @param none
	 * @return none
	 */
	public NormativeBoardAbstract() {
		this.callbacks = new HashMap<NormativeBoardEventType, List<NormativeBoardListener>>();
		for(NormativeBoardEventType type : NormativeBoardEventType.values()) {
			this.callbacks.put(type, new ArrayList<NormativeBoardListener>());
		}
		
		this.norms = new HashMap<Integer, NormEntityAbstract>();
		this.sanctions = new HashMap<Integer, SanctionEntityAbstract>();
		this.normSanctions = new HashMap<Integer, List<Integer>>();
	}
	
	
	@Override
	public NormEntityAbstract getNorm(int normId) {
		NormEntityAbstract norm = null;
		
		if (this.norms.containsKey(normId)) {
			norm = this.norms.get(normId);
		}
		
		return norm;
	}
	
	
	@Override
	public List<NormEntityAbstract> getNorms() {
		List<NormEntityAbstract> normList = new ArrayList<NormEntityAbstract>();
		
		if (!this.norms.isEmpty()) {
			normList.addAll(this.norms.values());
		}
		
		return normList;
	}
	
	
	@Override
	public void setNorm(NormEntityAbstract norm) {
		int normId = norm.getId();
		
		NormEntityAbstract oldNorm;
		// Update a norm
		if (this.norms.containsKey(normId)) {
			oldNorm = this.norms.put(normId, norm);
			
			this.processNormativeEvent(NormativeBoardEventType.UPDATE_NORM, oldNorm,
					norm);
			
			// Insert a norm
		} else {
			this.norms.put(normId, norm);
			this.normSanctions.put(normId, new ArrayList<Integer>());
			
			this.processNormativeEvent(NormativeBoardEventType.INSERT_NORM, null,
					norm);
		}
	}
	
	
	@Override
	public void removeNorm(int normId) {
		if (this.norms.containsKey(normId)) {
			NormEntityAbstract oldNorm = this.norms.remove(normId);
			if (this.normSanctions.containsKey(normId)) {
				this.normSanctions.remove(normId);
			}
			
			this.processNormativeEvent(NormativeBoardEventType.REMOVE_NORM, oldNorm,
					null);
		}
	}
	
	
	@Override
	public boolean hasNorm(int normId) {
		
		if (this.norms.containsKey(normId)) {
			return true;
		}
		
		return false;
	}
	
	
	@Override
	public double getSalience(int normId) {
		double salience = 0.0;
		
		if (this.norms.containsKey(normId)) {
			salience = this.norms.get(normId).getSalience();
		}
		
		return salience;
	}
	
	
	@Override
	public void setSalience(int normId, double salience) {
		if (this.norms.containsKey(normId)) {
			NormEntityAbstract newNorm = this.norms.get(normId);
			NormEntityAbstract oldNorm = newNorm.clone();
			newNorm.setSalience(salience);
			
			this.processNormativeEvent(NormativeBoardEventType.UPDATE_SALIENCE,
					oldNorm, newNorm);
		}
	}
	
	
	@Override
	public SanctionEntityAbstract getSanction(int sanctionId) {
		SanctionEntityAbstract sanction = null;
		
		if (this.sanctions.containsKey(sanctionId)) {
			sanction = this.sanctions.get(sanctionId);
		}
		
		return sanction;
	}
	
	
	@Override
	public List<SanctionEntityAbstract> getSanctions() {
		List<SanctionEntityAbstract> sanctionList = new ArrayList<SanctionEntityAbstract>();
		
		if (!this.sanctions.isEmpty()) {
			sanctionList.addAll(this.sanctions.values());
		}
		
		return sanctionList;
	}
	
	
	@Override
	public void setSanction(SanctionEntityAbstract sanction) {
		this.sanctions.put(sanction.getId(), sanction);
	}
	
	
	@Override
	public void removeSanction(int sanctionId) {
		if (this.sanctions.containsKey(sanctionId)) {
			this.sanctions.remove(sanctionId);
			
			for(Integer normId : this.normSanctions.keySet()) {
				List<Integer> sanctions = this.normSanctions.get(normId);
				
				if (sanctions.contains(sanctionId)) {
					sanctions.remove(sanctionId);
					this.normSanctions.put(normId, sanctions);
				}
			}
		}
	}
	
	
	@Override
	public boolean hasSanction(int sanctionId) {
		if (this.sanctions.containsKey(sanctionId)) {
			return true;
		}
		
		return false;
	}
	
	
	@Override
	public List<SanctionEntityAbstract> getSanctions(int normId) {
		List<SanctionEntityAbstract> sanctionEntities = new ArrayList<SanctionEntityAbstract>();
		
		if (this.normSanctions.containsKey(normId)) {
			List<Integer> sanctions = this.normSanctions.get(normId);
			
			for(Integer sanctionId : sanctions) {
				if (this.sanctions.containsKey(sanctionId)) {
					sanctionEntities.add(this.sanctions.get(sanctionId));
				}
			}
		}
		
		return sanctionEntities;
	}
	
	
	@Override
	public void setNormSanction(int normId, int sanctionId) {
		
		List<Integer> sanctions;
		if (this.normSanctions.containsKey(normId)) {
			sanctions = this.normSanctions.get(normId);
			if (!sanctions.contains(sanctionId)) {
				sanctions.add(sanctionId);
			}
			this.normSanctions.put(normId, sanctions);
		} else {
			sanctions = new ArrayList<Integer>();
			sanctions.add(sanctionId);
			this.normSanctions.put(normId, sanctions);
		}
	}
	
	
	@Override
	public void removeNormSanction(int normId, int sanctionId) {
		
		if (this.normSanctions.containsKey(normId)) {
			List<Integer> sanctions = this.normSanctions.get(normId);
			if (sanctions.contains(sanctionId)) {
				sanctions.remove(sanctionId);
			}
			this.normSanctions.put(normId, sanctions);
		}
	}
	
	
	@Override
	public boolean hasNormSanction(int normId, int sanctionId) {
		
		if (this.normSanctions.containsKey(normId)) {
			List<Integer> sanctions = this.normSanctions.get(normId);
			if (sanctions.contains(sanctionId)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	@Override
	public List<Integer> getNormSanctions(int normId) {
		
		List<Integer> sanctions = new ArrayList<Integer>();
		
		if (this.normSanctions.containsKey(normId)) {
			sanctions = this.normSanctions.get(normId);
		}
		
		return sanctions;
	}
	
	
	@Override
	public void addNormsSanctions(
			Map<NormEntityAbstract, List<SanctionEntityAbstract>> normsSanctions) {
		
		for(NormEntityAbstract norm : normsSanctions.keySet()) {
			int normId = norm.getId();
			if (!this.hasNorm(normId)) {
				List<SanctionEntityAbstract> sanctions = normsSanctions.get(norm);
				this.setNorm(norm);
				for(SanctionEntityAbstract sanction : sanctions) {
					int sanctionId = sanction.getId();
					if (!this.hasSanction(sanctionId)) {
						this.setSanction(sanction);
					}
					this.setNormSanction(normId, sanctionId);
				}
			}
		}
	}
	
	
	@Override
	public Map<NormEntityAbstract, List<SanctionEntityAbstract>> getNormsSanctions() {
		Map<NormEntityAbstract, List<SanctionEntityAbstract>> normsSanctions = new HashMap<NormEntityAbstract, List<SanctionEntityAbstract>>();
		
		for(NormEntityAbstract norm : this.getNorms()) {
			List<SanctionEntityAbstract> sanctions = new ArrayList<SanctionEntityAbstract>();
			for(Integer sanctionId : this.getNormSanctions(norm.getId())) {
				sanctions.add(this.getSanction(sanctionId));
			}
			
			normsSanctions.put(norm, sanctions);
		}
		
		return normsSanctions;
	}
	
	
	@Override
	public void updateNormsSanctions(
			Map<NormEntityAbstract, List<SanctionEntityAbstract>> normsSanctions) {
		
		for(NormEntityAbstract norm : normsSanctions.keySet()) {
			this.setNorm(norm);
			int normId = norm.getId();
			
			for(SanctionEntityAbstract sanction : normsSanctions.get(norm)) {
				this.setSanction(sanction);
				int sanctionId = sanction.getId();
				
				if (!this.getNormSanctions(normId).contains(sanctionId)) {
					this.setNormSanction(normId, sanctionId);
				}
			}
		}
	}
	
	
	@Override
	public void registerCallback(List<NormativeBoardEventType> types,
			NormativeBoardListener normListener) {
		
		List<NormativeBoardListener> listener;
		for(NormativeBoardEventType type : types) {
			if (this.callbacks.containsKey(type)) {
				listener = this.callbacks.get(type);
			} else {
				listener = new ArrayList<NormativeBoardListener>();
			}
			
			listener.add(normListener);
			this.callbacks.put(type, listener);
		}
	}
	
	
	@Override
	public void unregisterCallback(List<NormativeBoardEventType> types,
			NormativeBoardListener normListener) {
		
		for(NormativeBoardEventType type : types) {
			if (this.callbacks.containsKey(type)) {
				List<NormativeBoardListener> listener = this.callbacks.get(type);
				
				if (listener.contains(normListener)) {
					listener.remove(normListener);
					this.callbacks.put(type, listener);
				}
			}
		}
	}
	
	
	/**
	 * Send event to all callbacks
	 * 
	 * @param type
	 *          Normative board event type
	 * @param oldNorm
	 *          Old norm
	 * @param newNorm
	 *          New norm
	 * @return none
	 */
	private void processNormativeEvent(NormativeBoardEventType type,
			NormEntityAbstract oldNorm, NormEntityAbstract newNorm) {
		
		for(NormativeBoardListener normListener : this.callbacks.get(type)) {
			normListener.receive(type, oldNorm, newNorm);
		}
	}
}