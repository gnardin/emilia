package emilia.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import emilia.entity.norm.NormEntityAbstract;
import emilia.entity.sanction.SanctionEntityAbstract;

public abstract class NormativeBoardAbstract implements NormativeBoardInterface {
	
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
	public NormEntityAbstract getNorm(Integer normId) {
		NormEntityAbstract norm = null;
		
		if (this.norms.containsKey(normId)) {
			norm = this.norms.get(normId);
		}
		
		return norm;
	}
	
	
	@Override
	public void setNorm(NormEntityAbstract norm) {
		Integer normId = norm.getId();
		
		NormEntityAbstract oldNorm;
		// Update a norm
		if (this.norms.containsKey(normId)) {
			oldNorm = this.norms.replace(normId, norm);
			
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
	public void removeNorm(Integer normId) {
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
	public Boolean hasNorm(Integer normId) {
		Boolean found = false;
		
		if (this.norms.containsKey(normId)) {
			found = true;
		}
		
		return found;
	}
	
	
	@Override
	public Double getSalience(Integer normId) {
		Double salience = 0.0;
		
		if (this.norms.containsKey(normId)) {
			salience = this.norms.get(normId).getSalience();
		}
		
		return salience;
	}
	
	
	@Override
	public void setSalience(Integer normId, Double salience) {
		if (this.norms.containsKey(normId)) {
			NormEntityAbstract newNorm = this.norms.get(normId);
			NormEntityAbstract oldNorm = newNorm.clone();
			newNorm.setSalience(salience);
			
			this.processNormativeEvent(NormativeBoardEventType.UPDATE_SALIENCE,
					oldNorm, newNorm);
		}
	}
	
	
	@Override
	public SanctionEntityAbstract getSanction(Integer sanctionId) {
		SanctionEntityAbstract sanction = null;
		
		if (this.sanctions.containsKey(sanctionId)) {
			sanction = this.sanctions.get(sanctionId);
		}
		
		return sanction;
	}
	
	
	@Override
	public void setSanction(SanctionEntityAbstract sanction) {
		Integer sanctionId = sanction.getId();
		
		// Update a sanction
		if (this.sanctions.containsKey(sanctionId)) {
			this.sanctions.replace(sanctionId, sanction);
			// Insert a sanction
		} else {
			this.sanctions.put(sanctionId, sanction);
		}
	}
	
	
	@Override
	public void removeSanction(Integer sanctionId) {
		if (this.sanctions.containsKey(sanctionId)) {
			this.sanctions.remove(sanctionId);
			
			List<Integer> sanctions;
			for(Integer normId : this.normSanctions.keySet()) {
				sanctions = this.normSanctions.get(normId);
				
				if (sanctions.contains(sanctionId)) {
					sanctions.remove(sanctionId);
					this.normSanctions.replace(normId, sanctions);
				}
			}
		}
	}
	
	
	@Override
	public Boolean hasSanction(Integer sanctionId) {
		Boolean found = false;
		
		if (this.sanctions.containsKey(sanctionId)) {
			found = true;
		}
		
		return found;
	}
	
	
	@Override
	public List<Integer> getNormSanctions(Integer normId) {
		List<Integer> sanctions = new ArrayList<Integer>();
		
		if (this.normSanctions.containsKey(normId)) {
			sanctions = this.normSanctions.get(normId);
		}
		
		return sanctions;
	}
	
	
	@Override
	public void setNormSanction(Integer normId, Integer sanctionId) {
		List<Integer> sanctions;
		
		if (this.normSanctions.containsKey(normId)) {
			sanctions = this.normSanctions.get(normId);
			if (!sanctions.contains(sanctionId)) {
				sanctions.add(sanctionId);
			}
			this.normSanctions.replace(normId, sanctions);
		} else {
			sanctions = new ArrayList<Integer>();
			sanctions.add(sanctionId);
			this.normSanctions.put(normId, sanctions);
		}
	}
	
	
	@Override
	public void removeNormSanction(Integer normId, Integer sanctionId) {
		
		if (this.normSanctions.containsKey(normId)) {
			List<Integer> sanctions = this.normSanctions.get(normId);
			if (sanctions.contains(sanctionId)) {
				sanctions.remove(sanctionId);
			}
			this.normSanctions.replace(normId, sanctions);
		}
	}
	
	
	@Override
	public Boolean hasNormSanction(Integer normId, Integer sanctionId) {
		Boolean found = false;
		
		if (this.normSanctions.containsKey(normId)) {
			List<Integer> sanctions = this.normSanctions.get(normId);
			if (sanctions.contains(sanctionId)) {
				found = true;
			}
		}
		
		return found;
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
		
		List<NormativeBoardListener> listener;
		for(NormativeBoardEventType type : types) {
			if (this.callbacks.containsKey(type)) {
				listener = this.callbacks.get(type);
				
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
	 * @param normId
	 */
	private void processNormativeEvent(NormativeBoardEventType type,
			NormEntityAbstract oldNorm, NormEntityAbstract newNorm) {
		for(NormativeBoardListener normListener : this.callbacks.get(type)) {
			normListener.receive(type, oldNorm, newNorm);
		}
	}
}