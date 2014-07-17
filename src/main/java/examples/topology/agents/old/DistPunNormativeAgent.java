package examples.topology.agents.old;

import java.util.Vector;

import cern.jet.random.Uniform;

public class DistPunNormativeAgent extends DPagent {
	private int ID_NUM;

	private double honestyProb;
	private double punishmentProb;

	private double individualWeight;
	private double socialWeight;
	private double normativeWeight;

	private double deonticListened;
	private double normativeActionsObserved;

	private double normativeActionsObserved_CooperationWeight = .33;
	private double normativeActionsObserved_2_NormativePunishment = .99;
	private double normativeActionsObserved_2_StrategicPunishment = .33;
	private double normativeActionsReceived_2_NormativePunishment = .99;
	private double normativeActionsReceived_2_StrategicPunishment = .33;
	private double normativeActionsObserved_3_NormativePunishment = .99;
	private double normativeActionsObserved_3_StrategicPunishment = .66;
	private double normativeActionsReceived_3_NormativePunishment = .99;
	private double normativeActionsReceived_3_StrategicPunishment = .66;

	private double DEONTIC_LISTENED_THRESHOLD = 2; // EXPERIMENT PARAMETER
	private double NORMATIVE_ACTIONS_OBSERVED_THRESHOLD = 0; // EXPERIMENT
																// PARAMETER

	private double NORM_SALIENCE_INCREMENT_WHEN_COOPERATION = .66;
	private double NORM_SALIENCE_INCREMENT_WHEN_STRATEGIC_PUNISHMENT = 0;
	// private double NORM_SALIENCE_INCREMENT_WHEN_NORMATIVE_PUNISHMENT = .99;
	private double NORM_SALIENCE_INCREMENT_WHEN_NORMATIVE_PUNISHMENT = 0.99;
	private double NORM_SALIENCE_INCREMENT_WHEN_EDUCATIVE_PUNISHMENT = .99;
	private double NORM_SALIENCE_INCREMENT_FOR_EACH_NORM_COOPERATOR = .66;
	private double NORM_SALIENCE_INCREMENT_FOR_EACH_NON_PUNISHED_DEFECTOR = .66;
	private double NORM_SALIENCE_TIME_LOST = .001;
	private double NORM_SALIENCE_INCREMENT_FOR_EACH_2_STRATEGIC_PUNISHMENT_RECEIVED = .33;
	private double NORM_SALIENCE_INCREMENT_FOR_EACH_2_STRATEGIC_PUNISHMENT_OBSERVED = .33;
	private double NORM_SALIENCE_INCREMENT_FOR_EACH_2_NORMATIVE_PUNISHMENT_RECEIVED = .99;
	private double NORM_SALIENCE_INCREMENT_FOR_EACH_2_NORMATIVE_PUNISHMENT_OBSERVED = .99;
	// private double
	// NORM_SALIENCE_INCREMENT_FOR_EACH_2_NORMATIVE_PUNISHMENT_RECEIVED = 1.55;
	// private double
	// NORM_SALIENCE_INCREMENT_FOR_EACH_2_NORMATIVE_PUNISHMENT_OBSERVED = 1.55;
	private double NORM_SALIENCE_INCREMENT_FOR_EACH_2_EDUCATIVE_PUNISHMENT_OBSERVED = .99;
	private double NORM_SALIENCE_INCREMENT_FOR_EACH_2_EDUCATIVE_PUNISHMENT_RECEIVED = .99;

	private double NORM_SALIENCE_INCREMENT_FOR_EACH_3_EDUCATIVE_PUNISHMENT_OBSERVED = .99;
	private double NORM_SALIENCE_INCREMENT_FOR_EACH_3_EDUCATIVE_PUNISHMENT_RECEIVED = .99;
	private double NORM_SALIENCE_INCREMENT_FOR_EACH_3_STRATEGIC_PUNISHMENT_OBSERVED = .66;
	private double NORM_SALIENCE_INCREMENT_FOR_EACH_3_STRATEGIC_PUNISHMENT_RECEIVED = .66;
	private double NORM_SALIENCE_INCREMENT_FOR_EACH_3_NORMATIVE_PUNISHMENT_OBSERVED = .99;
	private double NORM_SALIENCE_INCREMENT_FOR_EACH_3_NORMATIVE_PUNISHMENT_RECEIVED = .99;

	private boolean NORM_EMERGED;

	private double NORMATIVE_MESSAGES_HEARD_WEIGHT = .99; // TO DOUBLE CHECK
	private double PUNISHMENTS_OBSERVED_WEIGHT = .66; // TO DOUBLE CHECK
	private double TOO_MANY_PUNISHMENTS_WEIGHT = 0;
	private double PUNISHMENTS_OBSERVED_TOLERATED = 100000;

	private double STRATEGIC_PUNISHMENTS_RECEIVED_TOLERATED = 100000;
	private double NORMATIVE_PUNISHMENTS_RECEIVED_TOLERATED = 100000;
	private double STRATEGIC_PUNISHMENTS_OBSERVED_TOLERATED = 100000;
	private double NORMATIVE_PUNISHMENTS_OBSERVED_TOLERATED = 100000;

	private double INITIAL_NORM_IMPORTANCE = 0.0;
	private double NORM_ACTIVATION_VALUE = 0.5;
	private double NORM_SALIENCE_NORMATIVE_THRESHOLD = 0.65;

	private double NORM_EMERGENCE_RATIO = .99;
	private double TOLERANCE_THRESHOLD = 0.03;
	private double PUNISHMENT_GRADIENT = 3;

	private double INDIVIDUAL_DRIVE_MULTIPLIER = 0.05; // original 0.4
	// private double SOCIAL_DRIVE_MULTIPLIER = .18; //Cooperative Behavior
	// Cascades in Human Social Networks; Fowler & Christakis PNAS 2010
	private double SOCIAL_DRIVE_MULTIPLIER = .1;
	private double NORMATIVE_DRIVE_MULTIPLIER = 0.9; // original 0.4
	private double STRATEGIC_PUNISHMENT_DRIVE_MULTIPLIER = .1;
	private double NORMATIVE_PUNISHMENT_DRIVE_MULTIPLIER = .1;

	private double PUNISHMENT_COST;
	private double AMPLIFICATION_FACTOR;

	private double INERTIAL_INDIVIDUAL_DRIVE = 0.5; // original 0.05

	private double normativeDrive;
	private double individualDrive;
	private double socialDrive;
	private double normativePunDrive;
	private double strategicPunDrive;

	private int observedLastDishonest;
	private boolean punishedInThisRound;
	private int PUNISHMENT_DISTRIBUTION; // typeOfPunishment == 0 --> Second
											// Party Private Punishment
	// typeOfPunishment == 1 --> Second Party Public Punishment
	// typeOfPunishment == 2 --> Third Party Punishment

	private int SOCIAL_DRIVE_STRATEGY; // 0 --> Most Frequent
										// 1 --> Most Succesful amongst
										// Neighbours
	private double normSalience; // normSalience < 0.5 --> No Norm
	// normSalience == 0.5 --> Medium Salient Norm
	// 0.5 < normSalience < 1 --> High Salient Norm
	private double normalizedActionsObservedIncrement;

	private int punishmentRestrictions; // punishmentRestrictions == 0 --> No
										// Restrictions.
	// punishmentRestrictions == 1 --> Only Honest can Punish.
	// punishmentRestrictions == 2 --> Honest Not Punisher CANNOT be punished.
	// All can punish.

	private int lastPlayer;
	private int lastFirstStageAction; // 0 --> means DEFECT ; 1 --> means
										// COOPERATE
	private int lastSecondStageAction; // 0 --> means NO PUNISHMENT;
										// 1 --> means STRATEGIC PUNISHMENT;
										// 2 --> means NORMATIVE PUNISHMENT
										// 3 --> means EDUCATIVE
	private Vector<Integer> lastPunishmentReceived; // 0 --> means NO
													// PUNISHMENT; 1 --> means
													// STRATEGIC PUNISHMENT; 2
													// --> means NORMATIVE
													// PUNISHMENT
	private Vector<Vector<Integer>> thirdPartyPunishmentsReceived;
	private int strategicSecondPartyPunishments;
	private int normativeSecondPartyPunishments;
	private int educationalSecondPartyPunishments;
	private int strategicThirdPartyPunishments;
	private int normativeThirdPartyPunishments;
	private int educationalThirdPartyPunishments;

	private int meaningfulStrategicThirdPartyPunishments;
	private int meaningfulNormativeThirdPartyPunishments;
	private int meaningfulEducationalThirdPartyPunishments;

	private double lastFirstStageReward;
	private double lastCooperatorNonPunisherReward;
	private double lastSecondStageReward;
	private double lastSecondStageRewardWithoutPunishmentSent;
	private double lastStepReward;
	private double payoff;

	private double lastStepNormativeMessagesHeard;

	private double oldestReward;
	private int oldestFirstStageAction;

	private double pastDefectors;

	// private boolean EDUCATIONAL_PUNISHER;

	protected Uniform rand;

	private int PUNISHMENT_TYPE;
	private double EXPLORATION_RATE;
	private boolean DECREASING_PUN_COST;
	private boolean interactedThisRound;

	private double INDIVIDUAL_DRIVE_NORMALIZER_XMIN;
	private double INDIVIDUAL_DRIVE_NORMALIZER_XMAX;
	// NORM SALIENCE VALUES : [-1.65 4.29]

	private double lastOrientation;

	private Integer lastInertialPlayer;

	// private Double lastInertialMarginalReward;

	private Integer lastInertialAction;
	private double lastStepProportionalPunishmentsSeen;
	private double lastStepProportionalEducationalMessagesSeen;

	private double lastStepRewardWithoutPunishmentSent;
	private double oldestRewardWithoutPunishmentSent;

	private double lastStepFirstStageDefection;

	private Vector<Integer> lastSecondStageActionDP;

	private Vector<Double> damagesReceived;

	private boolean DECREASEDPUNCOST;

	public DistPunNormativeAgent(int id, double hProb, double pProb, int pun,
			double initialSalience, Uniform rand2, double normativeD,
			double individualD, double socialD, double individualW,
			double socialW, double normativeW, int degree, int punishmentType,
			int allowed_sent_3party_pun, int allowed_received_3party_pun,
			double punishmentDamage, double amplificationFactor,
			boolean decreasingPunCost, double explorationRate) {
		this.ID_NUM = new Integer(id);
		honestyProb = new Double(hProb);
		punishmentProb = new Double(pProb);
		this.PUNISHMENT_COST = new Double(punishmentDamage);
		this.AMPLIFICATION_FACTOR = new Double(amplificationFactor);
		this.DECREASING_PUN_COST = decreasingPunCost;

		observedLastDishonest = 0;
		punishedInThisRound = false;

		oldestReward = 0;
		lastFirstStageAction = 0;
		lastSecondStageAction = 0;
		lastFirstStageReward = 0;
		lastSecondStageReward = 0;
		lastSecondStageRewardWithoutPunishmentSent = 0;
		lastStepRewardWithoutPunishmentSent = 0;
		oldestRewardWithoutPunishmentSent = 0;

		this.PUNISHMENT_TYPE = new Integer(punishmentType);
		this.EXPLORATION_RATE = new Double(explorationRate);

		this.lastPunishmentReceived = new Vector();
		this.thirdPartyPunishmentsReceived = new Vector();

		this.deonticListened = 0;
		this.normativeActionsObserved = 0;

		this.normativeDrive = new Double(normativeD);
		this.individualDrive = new Double(individualD);
		this.socialDrive = new Double(socialD);

		this.individualWeight = new Double(individualW);
		this.socialWeight = new Double(socialW);
		this.normativeWeight = new Double(normativeW);

		this.PUNISHMENT_DISTRIBUTION = new Integer(pun);
		this.NORM_EMERGED = false;
		normSalience = new Double(initialSalience);
		rand = rand2;
		lastPlayer = -1;
		this.pastDefectors = new Integer(0);
		payoff = new Double(0);

		interactedThisRound = false;

		DECREASEDPUNCOST = false;

		// this.INDIVIDUAL_DRIVE_NORMALIZER_XMIN =new Double( (1 -
		// (allowed_sent_3party_pun * this.PUNISHMENT_COST *
		// this.AMPLIFICATION_FACTOR) -
		// (allowed_received_3party_pun*this.PUNISHMENT_COST)));
		// this.INDIVIDUAL_DRIVE_NORMALIZER_XMIN =new Double( (1 -
		// (allowed_received_3party_pun*this.PUNISHMENT_COST)));
		// this.INDIVIDUAL_DRIVE_NORMALIZER_XMIN =new Double( (1 -
		// (allowed_received_3party_pun*this.PUNISHMENT_COST)));
		// this.INDIVIDUAL_DRIVE_NORMALIZER_XMIN = new Double(-30.0);
		this.INDIVIDUAL_DRIVE_NORMALIZER_XMIN = new Double(0);
		this.INDIVIDUAL_DRIVE_NORMALIZER_XMAX = new Double(25.0);

		this.lastStepNormativeMessagesHeard = 0;
		this.lastStepProportionalPunishmentsSeen = 0;
		this.lastStepProportionalEducationalMessagesSeen = 0;
		lastOrientation = 1;

		this.lastStepFirstStageDefection = 0;
		lastSecondStageActionDP = new Vector();
		damagesReceived = new Vector();
	}

	public int getID() {
		return this.ID_NUM;
	}

	public int chooseFirstStageAction(int index) { // COOPERATE OR DEFECT
		oldestFirstStageAction = new Integer(this.lastFirstStageAction);
		this.damagesReceived.clear();
		meaningfulStrategicThirdPartyPunishments = 0;
		meaningfulNormativeThirdPartyPunishments = 0;
		meaningfulEducationalThirdPartyPunishments = 0;
		strategicThirdPartyPunishments = 0;
		normativeThirdPartyPunishments = 0;
		educationalThirdPartyPunishments = 0;

		lastPunishmentReceived.clear();
		double coin = new Double(rand.nextDoubleFromTo(0, 1));

		if (coin <= this.honestyProb) {
			lastFirstStageAction = 1; // COOPERATE
		} else {
			lastFirstStageAction = 0; // DEFECT
		}

		coin = new Double(rand.nextDoubleFromTo(0, 1));
		if (coin <= this.EXPLORATION_RATE) {
			lastFirstStageAction = rand.nextIntFromTo(0, 1);
		}

		// System.out.println("lastFirstStageAction:"+ lastFirstStageAction);
		// lastFirstStageAction = 0;
		return this.lastFirstStageAction;
	}

	public int getLastFirstStageAction() {
		return this.lastFirstStageAction;
	}

	public void receivePayOffFromFirstStage(double r) {
		// System.out.println("PRE oldestReward:"+oldestReward +
		// " lastFirstStageReward: " + lastFirstStageReward);
		// this.oldestReward = new Double(this.lastFirstStageReward);
		this.lastFirstStageReward = new Double(r);
		// System.out.println("A"+ this.getID()+ " did " +
		// this.lastFirstStageAction + " and received " +
		// this.lastFirstStageReward);
		// System.out.println("POST oldestReward:"+oldestReward +
		// " lastFirstStageReward: " + lastFirstStageReward);
	}

	public double getLastPayOffFromFirstStage() {
		return this.lastFirstStageReward;
	}

	/*
	 * public int chooseSecondStageActionCG(int defectors,double
	 * proportionalFirstStageDefection){ // PUNISH OR NOT TO PUNISH double coin
	 * = new Double(rand.nextDoubleFromTo(0, 1)); this.lastSecondStageAction =
	 * 0; //NOT PUNISH this.lastSecondStageReward = 0;
	 * this.lastSecondStageRewardWithoutPunishmentSent = 0; boolean punished =
	 * false; // System.out.println("A"+this.ID_NUM+" ("+ this.normSalience
	 * +") this.punishmentProb:"+this.punishmentProb + " coin:"+coin +
	 * " OponentAction:"+OponentAction); if(defectors>0){ if(coin <=
	 * this.punishmentProb){ //if the probability tells to punish and the other
	 * has been a defector if(this.PUNISHMENT_TYPE==0){ if(this.normSalience>
	 * this.NORM_ACTIVATION_VALUE){ this.lastSecondStageAction = 2; //PUNISH
	 * NORMATIVELY punished = true; } else{ this.lastSecondStageAction = 1;
	 * //PUNISH STRATEGICALLY punished = true; } } if(this.PUNISHMENT_TYPE==1 ||
	 * this.PUNISHMENT_TYPE==5){ this.lastSecondStageAction = 1; //PUNISH
	 * STRATEGICALLY punished = true; } if((this.PUNISHMENT_TYPE==2 ||
	 * this.PUNISHMENT_TYPE==4) && this.normSalience> this.NORM_ACTIVATION_VALUE
	 * ){ this.lastSecondStageAction = 2; //PUNISH NORMATIVELY punished = true;
	 * } } if(!punished){
	 * 
	 * if(this.shouldEducate(proportionalFirstStageDefection) &&
	 * this.PUNISHMENT_TYPE!=1 && this.PUNISHMENT_TYPE!=2 ){
	 * this.lastSecondStageAction = 3; //PUNISH EDUCATIVE } }
	 * 
	 * if(this.lastSecondStageAction == 1 || this.lastSecondStageAction == 2 ){
	 * if(this.DECREASING_PUN_COST){
	 * this.reducePunishmentCost(proportionalFirstStageDefection
	 * ,lastSecondStageAction); } // if(this.PUNISHMENT_COST==0 &&
	 * this.lastSecondStageAction == 2){ // this.lastSecondStageAction = 3; // }
	 * this.lastSecondStageReward -= new
	 * Double(PUNISHMENT_COST*AMPLIFICATION_FACTOR*defectors); //
	 * System.out.println("A"+this.ID_NUM+" ("+ this.normSalience
	 * +") APPLIED punishment. lastSecondStageReward:"+lastSecondStageReward); }
	 * } else{ this.lastSecondStageAction=0; } //
	 * System.out.println("lastSecondStageAction: "+lastSecondStageAction);
	 * return this.lastSecondStageAction; }
	 */

	public int chooseSecondStageActionDP(
			double proportionalFirstStageDefection, int oponent, int index) { // PUNISH
																				// OR
																				// NOT
																				// TO
																				// PUNISH
		double coin = new Double(rand.nextDoubleFromTo(0, 1));
		this.lastSecondStageAction = 0; // NOT PUNISH
		this.lastSecondStageReward = 0;
		this.lastSecondStageRewardWithoutPunishmentSent = 0;
		boolean punished = false;
		// System.out.println("A"+this.ID_NUM+" ("+ this.normSalience
		// +") this.punishmentProb:"+this.punishmentProb + " coin:"+coin +
		// " OponentAction:"+OponentAction);
		if (oponent == 0) {
			if (coin <= this.punishmentProb) { // if the probability tells to
												// punish and the other has been
												// a defector
				if (this.PUNISHMENT_TYPE == 0) {
					if (this.normSalience > this.NORM_ACTIVATION_VALUE) {
						this.lastSecondStageAction = 2; // PUNISH NORMATIVELY
						punished = true;
					} else {
						this.lastSecondStageAction = 1; // PUNISH STRATEGICALLY
						punished = true;
					}
				}
				if (this.PUNISHMENT_TYPE == 1 || this.PUNISHMENT_TYPE == 5) {
					this.lastSecondStageAction = 1; // PUNISH STRATEGICALLY
					punished = true;
				}
				if ((this.PUNISHMENT_TYPE == 2 || this.PUNISHMENT_TYPE == 4)
						&& this.normSalience > this.NORM_ACTIVATION_VALUE) {
					this.lastSecondStageAction = 2; // PUNISH NORMATIVELY
					punished = true;
				}
			}
			if (!punished) {
				if (this.shouldEducate(proportionalFirstStageDefection)
						&& this.PUNISHMENT_TYPE != 1
						&& this.PUNISHMENT_TYPE != 2) {
					this.lastSecondStageAction = 3; // PUNISH EDUCATIVE
				}
			}

			if (this.lastSecondStageAction == 1
					|| this.lastSecondStageAction == 2) {
				this.reducePunishmentCost(proportionalFirstStageDefection,
						lastSecondStageAction);
				this.lastSecondStageReward -= new Double(PUNISHMENT_COST
						* AMPLIFICATION_FACTOR);
			}
		} else {
			this.lastSecondStageAction = 0;
		}
		// if (index > 10) {
		// this.lastSecondStageAction = 0;
		// }
		// System.out.println("lastSecondStageAction: "+lastSecondStageAction);
		lastSecondStageActionDP.add(new Integer(lastSecondStageAction));

		return this.lastSecondStageAction;
	}

	/*
	 * public int chooseSecondStageAction(int OponentAction, double
	 * proportionalFirstStageDefection){ // PUNISH OR NOT TO PUNISH double coin
	 * = new Double(rand.nextDoubleFromTo(0, 1)); this.lastSecondStageAction =
	 * 0; //NOT PUNISH this.lastSecondStageReward = 0;
	 * this.lastSecondStageRewardWithoutPunishmentSent = 0; boolean punished =
	 * false; // System.out.println("A"+this.ID_NUM+" ("+ this.normSalience
	 * +") this.punishmentProb:"+this.punishmentProb + " coin:"+coin +
	 * " OponentAction:"+OponentAction); if(this.PUNISHMENT_DISTRIBUTION ==2){
	 * if(coin <= this.punishmentProb && OponentAction==0){ //if the probability
	 * tells to punish and the other has been a defector
	 * if(this.PUNISHMENT_TYPE==0){ if(this.normSalience>
	 * this.NORM_ACTIVATION_VALUE){ this.lastSecondStageAction = 2; //PUNISH
	 * NORMATIVELY punished = true; } else{ this.lastSecondStageAction = 1;
	 * //PUNISH STRATEGICALLY punished = true; } } if(this.PUNISHMENT_TYPE==1 ||
	 * this.PUNISHMENT_TYPE==5){ this.lastSecondStageAction = 1; //PUNISH
	 * STRATEGICALLY punished = true; } if((this.PUNISHMENT_TYPE==2 ||
	 * this.PUNISHMENT_TYPE==4) && this.normSalience> this.NORM_ACTIVATION_VALUE
	 * ){ this.lastSecondStageAction = 2; //PUNISH NORMATIVELY punished = true;
	 * } } if(!punished){ if(this.shouldEducate(proportionalFirstStageDefection)
	 * && this.PUNISHMENT_TYPE!=1 && this.PUNISHMENT_TYPE!=2 ){
	 * this.lastSecondStageAction = 3; //PUNISH EDUCATIVE } }
	 * 
	 * if(this.lastSecondStageAction == 1 || this.lastSecondStageAction == 2 ){
	 * if(this.DECREASING_PUN_COST){
	 * this.reducePunishmentCost(proportionalFirstStageDefection); }
	 * this.lastSecondStageReward -= new
	 * Double(PUNISHMENT_COST*AMPLIFICATION_FACTOR); //
	 * System.out.println("A"+this.ID_NUM+" ("+ this.normSalience
	 * +") APPLIED punishment. lastSecondStageReward:"+lastSecondStageReward); }
	 * } else{ this.lastSecondStageAction=0; } //
	 * System.out.println("lastSecondStageAction: "+lastSecondStageAction);
	 * return this.lastSecondStageAction; }
	 */
	public void setSecondStageAction(int a) {
		this.lastSecondStageAction = new Integer(a);
	}

	public void receivePunishments(Vector<Integer> players,
			Vector<Integer> actions, Vector<Double> sanctions) {

		this.strategicSecondPartyPunishments = 0;
		this.normativeSecondPartyPunishments = 0;
		this.educationalSecondPartyPunishments = 0;

		for (int i = 0; i < actions.size(); i++) {
			// System.out.println("A"+this.ID_NUM+" received punishment. lastSecondStageReward:"+lastSecondStageReward);
			if (players.elementAt(i) != this.getID()) {
				double punishmentReceived = sanctions.elementAt(i);
				if (actions.elementAt(i) == 1) {
					strategicSecondPartyPunishments++;
					lastSecondStageReward -= new Double(punishmentReceived);
					lastSecondStageRewardWithoutPunishmentSent -= new Double(
							punishmentReceived);
				}
				if (actions.elementAt(i) == 2) {
					normativeSecondPartyPunishments++;
					lastSecondStageReward -= new Double(punishmentReceived);
					lastSecondStageRewardWithoutPunishmentSent -= new Double(
							punishmentReceived);
				}
				if (actions.elementAt(i) == 3) {
					educationalSecondPartyPunishments++;
				}
				this.lastPunishmentReceived.add(new Integer(actions
						.elementAt(i)));
			}

		}
	}

	public void receivePunishmentsCG(Vector<Integer> actions,
			Vector<Double> sanctions) {

		this.strategicSecondPartyPunishments = 0;
		this.normativeSecondPartyPunishments = 0;
		this.educationalSecondPartyPunishments = 0;

		if ((actions.contains(1) || actions.contains(2))) {
			for (int i = 0; i < actions.size(); i++) {
				double punishmentReceived = sanctions.elementAt(i);
				if (this.lastFirstStageAction == 0) {
					lastSecondStageReward -= new Double(punishmentReceived);
					lastSecondStageRewardWithoutPunishmentSent -= new Double(
							punishmentReceived);
					this.lastPunishmentReceived.add(new Integer(actions
							.elementAt(i)));
					// System.out.println("A"+this.ID_NUM+" received punishment. lastSecondStageReward:"+lastSecondStageReward);
					if (actions.elementAt(i) == 1) {
						strategicSecondPartyPunishments++;
					}
					if (actions.elementAt(i) == 2) {
						normativeSecondPartyPunishments++;
					}
					if (actions.elementAt(i) == 3) {
						educationalSecondPartyPunishments++;
					}
				}
			}
		}

	}

	public Vector<Integer> getPunishmentReceived() {
		return this.lastPunishmentReceived;
	}

	public int getPunishmentReceived(int type) {
		int counter = 0;
		for (int i = 0; i < this.lastPunishmentReceived.size(); i++) {
			if (this.lastPunishmentReceived.elementAt(i) == type) {
				counter++;
			}
		}
		return counter;
	}

	public int getPunishmentApplied() {
		return this.lastSecondStageAction;
	}

	public double getHonestyProb() {
		return this.honestyProb;
	}

	public double getPunishmentProb() {
		return this.punishmentProb;
	}

	public void updateMixedStrategies() {

		updateIndividualDrive();
		updateNormativeDrive();
		// if(this.honestyProb<0.5){
		// System.out.println("\t\t PRE this.honestyProb:"+ this.honestyProb +
		// " A"+this.ID_NUM);
		// }

		if (this.normSalience > this.NORM_ACTIVATION_VALUE) {
			this.honestyProb += (this.individualDrive * this.individualWeight)
					+ (this.normativeDrive * this.normativeWeight);
		} else {
			this.honestyProb += (this.individualDrive * this.individualWeight);
		}

		// if(this.honestyProb<0.5){
		// System.out.println("\t\t POST this.honestyProb:"+ this.honestyProb +
		// " A"+this.ID_NUM);
		// }

		if (this.honestyProb < 0) {
			this.honestyProb = 0;
		}
		if (this.honestyProb > NORM_EMERGENCE_RATIO) {
			// if(this.honestyProb>0.95){
			this.honestyProb = NORM_EMERGENCE_RATIO;
		}
		// System.out.println("this.honestyProb:"+this.honestyProb +
		// "\t individualDrive:"+individualDrive+ "\t normativeDrive:"+
		// normativeDrive);

	}

	public void updatePunishmentStrategy() {
		// this.punishmentProb += new Double(this.strategicPunDrive *
		// this.individualDrive + this.normativePunDrive * this.normativeDrive);
		double aux = 0;

		if (this.normativeWeight == 0
				|| this.normSalience <= this.NORM_ACTIVATION_VALUE) {
			// if(this.individualWeight>0 &&
			// this.normSalience<=this.NORM_ACTIVATION_VALUE){
			aux += new Double(this.strategicPunDrive);
			// System.out.println("\t \t A"+ this.getID()
			// +" strategicPunDrive");
		}
		if (this.normativeWeight > 0
				&& this.normSalience > this.NORM_ACTIVATION_VALUE) {
			// if(this.normativeWeight>0){
			aux += new Double(this.normativePunDrive);
			// System.out.println("\t \t A"+ this.getID()
			// +" normativePunDrive");
		}

		this.punishmentProb = new Double(aux);

		if (this.punishmentProb > 1) {
			this.punishmentProb = 1;
		}
		if (this.punishmentProb < 0) {
			this.punishmentProb = 0;
		}
	}

	private void updateNormativeDrive() {
		if (this.normSalience >= NORM_ACTIVATION_VALUE) {
			double marginalSalience = (this.normSalience - NORM_ACTIVATION_VALUE)
					+ INITIAL_NORM_IMPORTANCE;
			this.normativeDrive = new Double(marginalSalience
					* this.NORMATIVE_DRIVE_MULTIPLIER);
		}
	}

	private void updateIndividualDrive() {
		// double marginalReward = (this.lastStepReward - this.oldestReward);
		double marginalReward = (this.lastStepRewardWithoutPunishmentSent - this.oldestRewardWithoutPunishmentSent);
		if (this.lastSecondStageAction > 0) {
			// System.out.println("\t A" + this.getID() +
			// " lastStepRewardWithoutPunishmentSent: " +
			// lastStepRewardWithoutPunishmentSent +
			// " oldestRewardWithoutPunishmentSent: " +
			// oldestRewardWithoutPunishmentSent);
			// System.out.println("\t A" + this.getID() +
			// " lastStepReward: "+this.lastStepReward + " oldestReward: " +
			// this.oldestReward);
			// System.out.println("");
		}
		double normalizedMarginalReward = new Double(
				(marginalReward - ((this.INDIVIDUAL_DRIVE_NORMALIZER_XMAX + this.INDIVIDUAL_DRIVE_NORMALIZER_XMIN) / 2))
						/ ((this.INDIVIDUAL_DRIVE_NORMALIZER_XMAX - this.INDIVIDUAL_DRIVE_NORMALIZER_XMIN) / 2));

		double orientation = new Double(1);
		// if(this.lastFirstStageAction==0 || this.lastStepReward==0){
		if (this.lastFirstStageAction == 0) {
			orientation = new Double(-1);
		}

		if (marginalReward == 0
				&& this.lastFirstStageAction == this.oldestFirstStageAction) {
			this.individualDrive = new Double(lastOrientation
					* this.INERTIAL_INDIVIDUAL_DRIVE
					* this.INDIVIDUAL_DRIVE_MULTIPLIER);

			if (this.honestyProb > 0.5 && this.lastFirstStageAction == 0) {
				// System.out.println("\t INERTIA A:"+this.ID_NUM+
				// " individualDrive:"+individualDrive + " lastInertialPlayer:"
				// + this.lastInertialPlayer + "  lastOrientation:"+
				// lastOrientation + "  lastInertialAction:"+lastInertialAction
				// + " normalizedMarginalReward:"+normalizedMarginalReward);

			}
		} else {

			// this.individualDrive = new Double(orientation *
			// normalizedMarginalReward * this.INDIVIDUAL_DRIVE_MULTIPLIER);
			double aux = new Double(orientation * marginalReward);
			this.individualDrive = new Double(
					((aux - ((this.INDIVIDUAL_DRIVE_NORMALIZER_XMAX + this.INDIVIDUAL_DRIVE_NORMALIZER_XMIN) / (double) 2)) / ((this.INDIVIDUAL_DRIVE_NORMALIZER_XMAX - this.INDIVIDUAL_DRIVE_NORMALIZER_XMIN) / (double) 2))
							* this.INDIVIDUAL_DRIVE_MULTIPLIER);
			if (this.honestyProb > 0.2 && this.lastFirstStageAction == 0) {
				// System.out.println("\t A:"+this.ID_NUM+
				// " individualDrive:"+individualDrive+
				// " normalizedMarginalReward:"+normalizedMarginalReward);
				// System.out.println("\t marginalReward: "+ marginalReward +
				// " lastFirstStageAction:" +lastFirstStageAction +
				// " oldestFirstStageAction:"+ oldestFirstStageAction +
				// " normalizedMarginalReward: "+ normalizedMarginalReward);

			}
			this.lastOrientation = new Double(orientation);
			if (this.lastOrientation > 0 && marginalReward < 0) {
				this.lastOrientation = this.lastOrientation * -1;
			}
			this.lastInertialPlayer = new Integer(this.lastPlayer);
			// this.lastInertialMarginalReward = new
			// Double(normalizedMarginalReward);
			this.lastInertialAction = new Integer(this.lastFirstStageAction);

		}
		// System.out.println("");
	}

	public void updateSocialDrive(int neighbours_frequent_strategy,
			double neighboursWithMostFrequentStrategy,
			int mostSuccesfulNeighbourStrategy,
			double neighboursWithMostSuccesfulStrategy) {

		// why people punish defectors? Joseph Henrich and Robert Boyd.
		// Payoff-biased transmission: a tendency to copy the most successful
		// individual,
		// Conformist transmission: a tendency to copy the most frequent
		// behavior in the population.
		double orientation = new Double(1);
		double weight = new Double(0);
		SOCIAL_DRIVE_STRATEGY = 0;

		if (neighbours_frequent_strategy == 0 && SOCIAL_DRIVE_STRATEGY == 0) {
			orientation = new Double(-1);
			weight = new Double(neighboursWithMostFrequentStrategy);
		}
		if (neighbours_frequent_strategy == 1 && SOCIAL_DRIVE_STRATEGY == 0) {
			orientation = new Double(1);
			weight = new Double(neighboursWithMostFrequentStrategy);
		}
		// if(mostSuccesfulNeighbourStrategy==0 && SOCIAL_DRIVE_STRATEGY==1){
		// orientation = new Double(-1);
		// weight = new Double(neighboursWithMostSuccesfulStrategy);
		// }
		// if(mostSuccesfulNeighbourStrategy==1 && SOCIAL_DRIVE_STRATEGY==1){
		// orientation = new Double(1);
		// weight = new Double(neighboursWithMostSuccesfulStrategy);
		// }

		this.socialDrive = new Double(orientation * weight
				* this.SOCIAL_DRIVE_MULTIPLIER);

		/*
		 * PRE BOWLES if(neighboursWithMostFrequentStrategy==1 &&
		 * this.rand.nextDoubleFromTo(0, 1)<0.318){ //Opinions and Social
		 * Pressure, Asch 1955 this.socialDrive = new Double(orientation*
		 * this.SOCIAL_DRIVE_MULTIPLIER); } else{ this.socialDrive = 0; }
		 */

		// System.out.println("A"+this.ID_NUM+" this.socialDrive:"+this.socialDrive
		// + " neighbours_frequent_strategy:"+ neighbours_frequent_strategy +
		// " neighboursWithMostFrequentStrategy:"+neighboursWithMostFrequentStrategy);
		// double marginalPayoff = neighbour_payoff - this.lastStepReward;
		// if(neighbour_strategy ==0){ //DEFECTOR
		//
		// }
	}

	public void updateNormSalience(double firstStageCooperators,
			double firstStageDefectors, double normativePunishmentsSeen,
			double normativePunishmentsReceived,
			double strategicPunishmentsSeen,
			double strategicPunishmentsReceived,
			double educationalMessagesSeen,
			double educativePunishmentsReceived, double nonPunishedDefectors,
			double meaningfulStrategicPunishmentsSeen,
			double meaningfulNormativePunishmentsSeen,
			double meaningfulEducationalMessagesSeen,
			double meaningfulStrategicPunishmentsReceived,
			double meaningfulNormativePunishmentsReceived,
			double meaningfulEducativePunishmentsReceived) {

		if (meaningfulStrategicPunishmentsSeen >= 3) {
			meaningfulStrategicPunishmentsSeen = 0;
			meaningfulNormativePunishmentsSeen = 1;
		}

		// TODO: when dealing with multicontext situations, we will have to take
		// into account if the norm that is being applied is related to that
		// context.
		// the context will affect the function that activates the norm, and the
		// norm's salience.

		// ******************************************************************************************
		// *********** norm salience
		// ***************************************************
		// ******************************************************************************************
		double actionsObservedIncrement = new Double(0);

		if (this.normSalience >= NORM_ACTIVATION_VALUE) {
			// ****** SELF OBSERVATIONS
			if (this.lastFirstStageAction == 0) { // IF THE AGENT HAS BEEN A
													// DEFECTOR
				actionsObservedIncrement -= NORM_SALIENCE_INCREMENT_WHEN_COOPERATION;
			} else { // IF THE AGENT HAS BEEN A COOPERATOR
				actionsObservedIncrement += NORM_SALIENCE_INCREMENT_WHEN_COOPERATION;
			}
			if (this.lastSecondStageAction == 1) { // IF THE AGENT HAS BEEN AN
													// STRATEGIC PUNISHER
				actionsObservedIncrement += NORM_SALIENCE_INCREMENT_WHEN_STRATEGIC_PUNISHMENT;
			}
			if (this.lastSecondStageAction == 2) { // IF THE AGENT HAS BEEN AN
													// NORMATIVE PUNISHER
				actionsObservedIncrement += NORM_SALIENCE_INCREMENT_WHEN_NORMATIVE_PUNISHMENT;
			}
			if (this.lastSecondStageAction == 3) { // IF THE AGENT HAS BEEN AN
													// EDUCATIVE PUNISHER
				actionsObservedIncrement += NORM_SALIENCE_INCREMENT_WHEN_EDUCATIVE_PUNISHMENT;
			}

			// ****** SURROUND OBSERVATIONS
			actionsObservedIncrement += (NORM_SALIENCE_INCREMENT_FOR_EACH_NORM_COOPERATOR * firstStageCooperators); // increments

			if (firstStageCooperators > 0) {
				actionsObservedIncrement -= (NORM_SALIENCE_INCREMENT_FOR_EACH_NON_PUNISHED_DEFECTOR
						* nonPunishedDefectors * firstStageCooperators); // detriments
			} else {
				actionsObservedIncrement -= (NORM_SALIENCE_INCREMENT_FOR_EACH_NON_PUNISHED_DEFECTOR * nonPunishedDefectors); // detriments
			}

			if (normativePunishmentsSeen < NORMATIVE_PUNISHMENTS_OBSERVED_TOLERATED) {
				if (this.PUNISHMENT_DISTRIBUTION == 2) {
					actionsObservedIncrement += (NORM_SALIENCE_INCREMENT_FOR_EACH_2_NORMATIVE_PUNISHMENT_OBSERVED * meaningfulNormativePunishmentsSeen);
				}
				if (this.PUNISHMENT_DISTRIBUTION == 3) {
					actionsObservedIncrement += (NORM_SALIENCE_INCREMENT_FOR_EACH_3_NORMATIVE_PUNISHMENT_OBSERVED * meaningfulNormativePunishmentsSeen);
				}
			} else {
				if (this.PUNISHMENT_DISTRIBUTION == 2) {
					actionsObservedIncrement -= (NORM_SALIENCE_INCREMENT_FOR_EACH_2_NORMATIVE_PUNISHMENT_OBSERVED * meaningfulNormativePunishmentsSeen);
				}
				if (this.PUNISHMENT_DISTRIBUTION == 3) {
					actionsObservedIncrement -= (NORM_SALIENCE_INCREMENT_FOR_EACH_3_NORMATIVE_PUNISHMENT_OBSERVED * meaningfulNormativePunishmentsSeen);
				}

			}

			if (strategicPunishmentsSeen < STRATEGIC_PUNISHMENTS_OBSERVED_TOLERATED) {
				if (this.PUNISHMENT_DISTRIBUTION == 2) {
					actionsObservedIncrement += (NORM_SALIENCE_INCREMENT_FOR_EACH_2_STRATEGIC_PUNISHMENT_OBSERVED * meaningfulStrategicPunishmentsSeen); // still
																																							// missing
																																							// the
																																							// communications
				}
				if (this.PUNISHMENT_DISTRIBUTION == 3) {
					actionsObservedIncrement += (NORM_SALIENCE_INCREMENT_FOR_EACH_3_STRATEGIC_PUNISHMENT_OBSERVED * meaningfulStrategicPunishmentsSeen); // still
																																							// missing
																																							// the
																																							// communications
				}
			} else {
				if (this.PUNISHMENT_DISTRIBUTION == 2) {
					actionsObservedIncrement -= (NORM_SALIENCE_INCREMENT_FOR_EACH_2_STRATEGIC_PUNISHMENT_OBSERVED * meaningfulStrategicPunishmentsSeen);
				}
				if (this.PUNISHMENT_DISTRIBUTION == 3) {
					actionsObservedIncrement -= (NORM_SALIENCE_INCREMENT_FOR_EACH_3_STRATEGIC_PUNISHMENT_OBSERVED * meaningfulStrategicPunishmentsSeen);
				}

			}

			if (normativePunishmentsReceived < NORMATIVE_PUNISHMENTS_RECEIVED_TOLERATED) {
				if (this.PUNISHMENT_DISTRIBUTION == 2) {
					actionsObservedIncrement += (NORM_SALIENCE_INCREMENT_FOR_EACH_2_NORMATIVE_PUNISHMENT_RECEIVED * meaningfulNormativePunishmentsReceived);
				}
				if (this.PUNISHMENT_DISTRIBUTION == 3) {
					actionsObservedIncrement += (NORM_SALIENCE_INCREMENT_FOR_EACH_3_NORMATIVE_PUNISHMENT_RECEIVED * meaningfulNormativePunishmentsReceived);
				}
			} else {
				if (this.PUNISHMENT_DISTRIBUTION == 2) {
					actionsObservedIncrement -= (NORM_SALIENCE_INCREMENT_FOR_EACH_2_NORMATIVE_PUNISHMENT_RECEIVED * meaningfulNormativePunishmentsReceived);
				}
				if (this.PUNISHMENT_DISTRIBUTION == 3) {
					actionsObservedIncrement -= (NORM_SALIENCE_INCREMENT_FOR_EACH_3_NORMATIVE_PUNISHMENT_RECEIVED * meaningfulNormativePunishmentsReceived);
				}
			}

			if (strategicPunishmentsReceived < STRATEGIC_PUNISHMENTS_RECEIVED_TOLERATED) {
				if (this.PUNISHMENT_DISTRIBUTION == 2) {
					actionsObservedIncrement += (NORM_SALIENCE_INCREMENT_FOR_EACH_2_STRATEGIC_PUNISHMENT_RECEIVED * meaningfulStrategicPunishmentsReceived); // still
																																								// missing
																																								// the
																																								// communications
				}
				if (this.PUNISHMENT_DISTRIBUTION == 3) {
					actionsObservedIncrement += (NORM_SALIENCE_INCREMENT_FOR_EACH_3_STRATEGIC_PUNISHMENT_RECEIVED * meaningfulStrategicPunishmentsReceived); // still
																																								// missing
																																								// the
																																								// communications
				}

			} else {
				if (this.PUNISHMENT_DISTRIBUTION == 2) {
					actionsObservedIncrement -= (NORM_SALIENCE_INCREMENT_FOR_EACH_2_STRATEGIC_PUNISHMENT_RECEIVED * meaningfulStrategicPunishmentsReceived);
				}
				if (this.PUNISHMENT_DISTRIBUTION == 3) {
					actionsObservedIncrement -= (NORM_SALIENCE_INCREMENT_FOR_EACH_3_STRATEGIC_PUNISHMENT_RECEIVED * meaningfulStrategicPunishmentsReceived);
				}
			}

			if (this.PUNISHMENT_DISTRIBUTION == 2) {
				actionsObservedIncrement += NORM_SALIENCE_INCREMENT_FOR_EACH_2_EDUCATIVE_PUNISHMENT_OBSERVED
						* meaningfulEducationalMessagesSeen;
				actionsObservedIncrement += NORM_SALIENCE_INCREMENT_FOR_EACH_2_EDUCATIVE_PUNISHMENT_RECEIVED
						* meaningfulEducativePunishmentsReceived;
			}
			if (this.PUNISHMENT_DISTRIBUTION == 3) {
				actionsObservedIncrement += NORM_SALIENCE_INCREMENT_FOR_EACH_3_EDUCATIVE_PUNISHMENT_OBSERVED
						* meaningfulEducationalMessagesSeen;
				actionsObservedIncrement += NORM_SALIENCE_INCREMENT_FOR_EACH_3_EDUCATIVE_PUNISHMENT_RECEIVED
						* meaningfulEducativePunishmentsReceived;
			}

			// double normalizedActionsObservedIncrement = new
			// Double((actionsObservedIncrement - 2.475)/4.125); //NORMALIZED!!
			// this.normalizedActionsObservedIncrement = new
			// Double((actionsObservedIncrement - 34.32)/100.32); //NORMALIZED!!
			if (actionsObservedIncrement < 0) {
				// this.normalizedActionsObservedIncrement = new
				// Double(actionsObservedIncrement/65.34);
				// this.normalizedActionsObservedIncrement = new Double(
				// actionsObservedIncrement / 3.3);

				// -Wc + 2 * 2 * Wnpd
				this.normalizedActionsObservedIncrement = new Double(
						actionsObservedIncrement / 3.63);
			}
			if (actionsObservedIncrement > 0) {
				// this.normalizedActionsObservedIncrement = new
				// Double(actionsObservedIncrement/165.99);
				// this.normalizedActionsObservedIncrement = new Double(
				// actionsObservedIncrement / 5.94);

				// +Wc + 4 * Wo + 4 * Ws
				this.normalizedActionsObservedIncrement = new Double(
						actionsObservedIncrement / 6.27);
			}

			// System.out.println("normalizedActionsObservedIncrement:"+normalizedActionsObservedIncrement);
			// this.normSalience += new
			// Double(normalizedActionsObservedIncrement -
			// this.NORM_SALIENCE_TIME_LOST);
			this.normSalience += new Double(normalizedActionsObservedIncrement);

			if (this.normSalience > 1) {
				this.normSalience = 1;
			}
			if (this.normSalience < NORM_ACTIVATION_VALUE) {
				this.normSalience = 0.0;
				deonticListened = 0;
				normativeActionsObserved = 0;
			}

			if (firstStageCooperators >= ((firstStageCooperators + firstStageDefectors) * NORM_EMERGENCE_RATIO)
					&& !this.NORM_EMERGED) {
				this.NORM_EMERGED = true;
				// System.out.println("NORM_EMERGED!! for A" + this.ID_NUM +
				// " firstStageCooperators:"+firstStageCooperators);
			}

		}

		// ******************************************************************************************
		// *********** norm recognition/activation
		// ***********************************************
		// ******************************************************************************************

		deonticListened += meaningfulNormativePunishmentsSeen
				+ meaningfulNormativePunishmentsReceived
				+ meaningfulEducationalMessagesSeen
				+ meaningfulEducativePunishmentsReceived;

		// System.out.println("\t \t AAAA" +
		// this.ID_NUM+"  meaningfulNormativePunishmentsSeen:"+meaningfulNormativePunishmentsSeen
		// +"  meaningfulNormativePunishmentsReceived:"+meaningfulNormativePunishmentsReceived
		// +"  meaningfulEducationalMessagesSeen:"+meaningfulEducationalMessagesSeen
		// +"  meaningfulEducativePunishmentsReceived:"+meaningfulEducativePunishmentsReceived
		// );

		if (this.PUNISHMENT_DISTRIBUTION == 2) {
			normativeActionsObserved += firstStageCooperators
					+ meaningfulStrategicPunishmentsSeen
					+ meaningfulNormativePunishmentsSeen
					+ meaningfulStrategicPunishmentsReceived
					+ meaningfulNormativePunishmentsReceived;

			// normativeActionsObserved += new Double(
			// (firstStageCooperators *
			// this.normativeActionsObserved_CooperationWeight)
			// + (meaningfulStrategicPunishmentsSeen *
			// this.normativeActionsObserved_2_StrategicPunishment)
			// + (meaningfulNormativePunishmentsSeen *
			// this.normativeActionsObserved_2_NormativePunishment)
			// + (meaningfulStrategicPunishmentsReceived *
			// this.normativeActionsReceived_2_StrategicPunishment)
			// + (meaningfulNormativePunishmentsReceived *
			// this.normativeActionsReceived_2_NormativePunishment));
		}
		if (this.PUNISHMENT_DISTRIBUTION == 3) {
			normativeActionsObserved += new Double(
					(firstStageCooperators * this.normativeActionsObserved_CooperationWeight)
							+ (meaningfulStrategicPunishmentsSeen * this.normativeActionsObserved_3_StrategicPunishment)
							+ (meaningfulNormativePunishmentsSeen * this.normativeActionsObserved_3_NormativePunishment)
							+ (meaningfulStrategicPunishmentsReceived * this.normativeActionsReceived_3_StrategicPunishment)
							+ (meaningfulNormativePunishmentsReceived * this.normativeActionsReceived_3_NormativePunishment));
		}

		if (this.normSalience < NORM_ACTIVATION_VALUE
				&& deonticListened >= DEONTIC_LISTENED_THRESHOLD
				&& normativeActionsObserved >= NORMATIVE_ACTIONS_OBSERVED_THRESHOLD) {
			this.normSalience = new Double(0.51);
			// System.out.println("\t A"+this.ID_NUM+" ACTIVATED ITS NORM SALIENCE");
		}

	}

	public void updateStrategicPunishmentDrive(double presentCooperators) {
		// //double marginalDefectors = new Double(this.pastDefectors -
		// presentDefectors); // Survey Evidence on Conditional Norm Enforcement
		// double marginalDefectors = new Double(presentDefectors -
		// this.pastDefectors); // DANI's
		// this.pastDefectors = new Double(presentDefectors);
		// double socialIncrement = 0;
		// //socialIncrement = new
		// Double((STRATEGIC_PUNISHMENT_DRIVE_MULTIPLIER*marginalDefectors)/(this.PUNISHMENT_COST*this.AMPLIFICATION_FACTOR));
		// socialIncrement = new
		// Double((marginalDefectors)/(this.PUNISHMENT_COST*this.AMPLIFICATION_FACTOR));
		// //socialIncrement = new Double((0.5*marginalDefectors));
		//
		// double individualIncrement = 0;
		// double marginalBenefit = this.lastCooperatorNonPunisherReward -
		// this.lastStepReward; //REMINDER: NOBEL, and, to increase punishment
		// prob if I had decided to stop punishing and it is needed again.
		// double normalizedMarginalBenefit = new Double((marginalBenefit -
		// ((5+this.INDIVIDUAL_DRIVE_NORMALIZER_XMIN)/2))/((5-this.INDIVIDUAL_DRIVE_NORMALIZER_XMIN)/2));
		// //double normalizedMarginalBenefit =new Double(marginalBenefit -
		// (((5+this.INDIVIDUAL_DRIVE_NORMALIZER_XMIN)/2)/((5-this.INDIVIDUAL_DRIVE_NORMALIZER_XMIN)/2)));
		// individualIncrement = new
		// Double(STRATEGIC_PUNISHMENT_DRIVE_MULTIPLIER*normalizedMarginalBenefit);
		//
		// this.strategicPunDrive = new Double(presentDefectors); PRE BOWLES
		// double presentCooperators = new Double((1.0-presentDefectors)*100);
		this.strategicPunDrive = presentCooperators;
		// this.strategicPunDrive = new
		// Double(Math.log(presentCooperators+1)/Math.log(100));
		// this.strategicPunDrive = new
		// Double(Math.exp(presentCooperators/100)/Math.exp(1));

		// System.out.println("\t A" +this.ID_NUM + "\t marginalDefectors: " +
		// marginalDefectors + "\t socialIncrement:"+socialIncrement +
		// " \t this.socialWeight:"+ this.socialWeight);
		// System.out.println("\t A" +this.ID_NUM +
		// "\t individualIncrement:"+individualIncrement +
		// " \t this.individualWeight:"+this.individualWeight);
		// System.out.println("\t A" +this.ID_NUM +
		// "\t this.strategicPunDrive:"+this.strategicPunDrive);

		// if(this.strategicPunDrive>1){
		// this.strategicPunDrive = 1;
		// }
		// if(this.strategicPunDrive<0){
		// this.strategicPunDrive = 0;
		// }
	}

	public void updateNormativePunishmentDrive(double presentDefectors) {
		// double marginalDefectors = new Double(this.pastDefectors-
		// presentDefectors);
		this.pastDefectors = new Double(presentDefectors);
		// double socialIncrement = 0;
		// socialIncrement = new
		// Double((NORMATIVE_PUNISHMENT_DRIVE_MULTIPLIER*marginalDefectors)/(this.PUNISHMENT_COST*this.AMPLIFICATION_FACTOR));
		// this.normativePunDrive = 0;
		// this.normativePunDrive += new
		// Double(socialIncrement*this.normativeWeight);
		//
		// if(this.lastStepProportionalPunishmentsSeen >
		// PUNISHMENTS_OBSERVED_TOLERATED){
		// this.normativePunDrive -= new
		// Double(this.lastStepProportionalPunishmentsSeen*
		// TOO_MANY_PUNISHMENTS_WEIGHT);
		// }
		// else{
		// this.normativePunDrive += new
		// Double(this.lastStepProportionalPunishmentsSeen*
		// PUNISHMENTS_OBSERVED_WEIGHT);
		// }
		//
		// if(this.lastStepProportionalEducationalMessagesSeen >
		// this.lastStepNormativeMessagesHeard ){
		// this.normativePunDrive += new
		// Double(this.lastStepProportionalEducationalMessagesSeen *
		// NORMATIVE_MESSAGES_HEARD_WEIGHT);
		// }
		// else{
		// this.normativePunDrive -= new
		// Double(this.lastStepProportionalEducationalMessagesSeen *
		// NORMATIVE_MESSAGES_HEARD_WEIGHT);
		// }
		//
		// this.lastStepNormativeMessagesHeard = new
		// Double(this.lastStepProportionalEducationalMessagesSeen);
		//
		double marginalSalience = new Double(
				(this.normSalience - NORM_ACTIVATION_VALUE)
						+ INITIAL_NORM_IMPORTANCE);
		this.normativePunDrive += new Double(marginalSalience
				* this.NORMATIVE_DRIVE_MULTIPLIER);

		// if(this.normativePunDrive>1){
		// this.normativePunDrive = 1;
		// }
		// if(this.normativePunDrive<0){
		// this.normativePunDrive = 0;
		// }
	}

	public int getLastOponent() {
		return this.lastPlayer;
	}

	public Vector<Vector<Integer>> getThirdPartyPunishments() {
		return this.thirdPartyPunishmentsReceived;
	}

	public void addThirdPartyPunishment(Vector<Integer> punishment) {
		// System.out.println("\t A"+ this.ID_NUM+" received "+
		// punishment.toString());
		this.thirdPartyPunishmentsReceived.add(new Vector(punishment));
	}

	public void receiveThirdPartyPunishments() {
		// System.out.println("\t A"+this.ID_NUM+
		// "  thirdPartyPunishmentsReceived:"+
		// this.thirdPartyPunishmentsReceived.toString() +
		// " lastSecondStageReward:"+lastSecondStageReward);

		for (int i = 0; i < this.thirdPartyPunishmentsReceived.size(); i++) {
			Vector<Integer> aux_pun = new Vector(
					this.thirdPartyPunishmentsReceived.elementAt(i));
			if (aux_pun.elementAt(1) == 1) {
				strategicThirdPartyPunishments++;
			}
			if (aux_pun.elementAt(1) == 2) {
				normativeThirdPartyPunishments++;
			}
			if (aux_pun.elementAt(1) == 3) {
				educationalThirdPartyPunishments++;
			}
		}
		this.lastSecondStageReward -= new Double(
				((strategicThirdPartyPunishments + normativeThirdPartyPunishments) * this.PUNISHMENT_COST));
		this.lastSecondStageRewardWithoutPunishmentSent -= new Double(
				((strategicThirdPartyPunishments + normativeThirdPartyPunishments) * this.PUNISHMENT_COST));
		this.thirdPartyPunishmentsReceived.clear();
	}

	public boolean isStrategicPunisher() {
		if ((this.normSalience < this.NORM_ACTIVATION_VALUE || this.normativeWeight == 0)
				&& (this.PUNISHMENT_TYPE == 1 || this.PUNISHMENT_TYPE == 0 || this.PUNISHMENT_TYPE == 5)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isNormativePunisher() {
		if (this.normSalience <= this.NORM_ACTIVATION_VALUE
				|| this.normativeWeight == 0 || this.PUNISHMENT_TYPE == 1
				|| this.PUNISHMENT_TYPE == 3 || this.PUNISHMENT_TYPE == 5) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * public boolean isEducationalPunisher() {
	 * if(this.normSalience>=this.NORM_ACTIVATION_VALUE &&
	 * this.EDUCATIONAL_PUNISHER){ // System.out.println("A"+ this.ID_NUM+
	 * " is an EDUCATOR"); return true; } else{ // System.out.println("A"+
	 * this.ID_NUM+ " is NOT an EDUCATOR"); return false; } }
	 */

	public boolean shouldEducate(double proportionalFirstStageDefection) {
		double coin = this.rand.nextDouble();
		if (this.activeSalience()) {
			if (coin < (proportionalFirstStageDefection / this.normSalience)) {
				return true;
			}
		}

		return false;
	}

	public boolean activeSalience() {
		if (this.normSalience > 0.4) {
			return true;
		} else {
			return false;
		}

	}

	public int ObserveSelfPunishments(int t) {
		int i = 0;
		if (t == 1) {
			if (this.PUNISHMENT_DISTRIBUTION == 2) {
				i = new Integer(strategicSecondPartyPunishments);
			}
			if (this.PUNISHMENT_DISTRIBUTION == 3) {
				i = new Integer(strategicThirdPartyPunishments);
			}
		}
		if (t == 2) {
			if (this.PUNISHMENT_DISTRIBUTION == 2) {
				i = new Integer(normativeSecondPartyPunishments);
			}
			if (this.PUNISHMENT_DISTRIBUTION == 3) {
				i = new Integer(normativeThirdPartyPunishments);
			}
		}
		if (t == 3) {
			if (this.PUNISHMENT_DISTRIBUTION == 2) {
				i = new Integer(educationalSecondPartyPunishments);
			}
			if (this.PUNISHMENT_DISTRIBUTION == 3) {
				i = new Integer(educationalThirdPartyPunishments);
			}
		}
		return i;
	}

	public void updatePayoffs() {
		this.oldestReward = new Double(this.lastStepReward);
		this.oldestRewardWithoutPunishmentSent = new Double(
				this.lastStepRewardWithoutPunishmentSent);
		this.lastStepReward = new Double(this.lastFirstStageReward
				+ this.lastSecondStageReward);
		this.lastStepRewardWithoutPunishmentSent = new Double(
				this.lastFirstStageReward
						+ this.lastSecondStageRewardWithoutPunishmentSent);

		if (this.lastFirstStageAction == 1 && this.lastSecondStageAction == 0) { // meaning,
																					// COOPERATOR
																					// NON
																					// PUNISHER
			this.lastCooperatorNonPunisherReward = new Double(
					this.lastStepReward);
		}
	}

	public double getPayoff() {
		return this.lastStepReward;
	}

	public boolean shouldIPunish() {
		double coin = new Double(rand.nextDoubleFromTo(0, 1));
		if (coin <= this.punishmentProb) {
			return true;
		} else {
			return false;
		}
	}

	public boolean wasStrategicPunisher() {
		if (this.lastSecondStageAction == 1) {
			return true;
		} else
			return false;
	}

	public boolean wasNormativePunisher() {
		if (this.lastSecondStageAction == 2) {
			return true;
		} else
			return false;
	}

	public void setPunishmentAction(int i) {
		this.lastSecondStageAction = new Integer(i);
		this.lastSecondStageReward = 0;
		this.lastSecondStageRewardWithoutPunishmentSent = 0;
	}

	public void updateMeaningfulStrategicThirdPartyPunishments(int n) {
		this.meaningfulStrategicThirdPartyPunishments += new Integer(n);
	}

	public void updateMeaningfulNormativeThirdPartyPunishments(int n) {
		this.meaningfulNormativeThirdPartyPunishments += new Integer(n);
	}

	public void updateMeaningfulEducationalThirdPartyPunishments(int n) {
		this.meaningfulEducationalThirdPartyPunishments += new Integer(n);
		// System.out.println("\t A" +
		// this.ID_NUM+" updated meaningfulEducationalThirdPartyPunishments: "+meaningfulEducationalThirdPartyPunishments);
	}

	public int getMeaningfulStrategicPunishmentsReceived() {
		return this.meaningfulStrategicThirdPartyPunishments;
	}

	public int getMeaningfulNormativePunishmentsReceived() {
		return this.meaningfulNormativeThirdPartyPunishments;
	}

	public int getMeaningfulEducativePunishmentsReceived() {
		return this.meaningfulEducationalThirdPartyPunishments;
	}

	public double getSalience() {
		return this.normSalience;
	}

	public double getIndividualDrive() {
		return this.individualDrive;
	}

	public double getNormativeDrive() {
		return this.normativeDrive;
	}

	public double getSocialDrive() {
		return this.socialDrive;
	}

	/*
	 * public void setInteractedThisRound(boolean b){ this.interactedThisRound =
	 * new Boolean(b); }
	 * 
	 * public boolean interactedThisRound(){ return this.interactedThisRound; }
	 */
	public void deducePunishmentCost() {
		this.lastSecondStageReward -= PUNISHMENT_COST * AMPLIFICATION_FACTOR;
	}

	public double getStrategicPunishmentDrive() {
		return this.strategicPunDrive;
	}

	public double getNormativePunishmentDrive() {
		return this.normativePunDrive;
	}

	public void updateVariablesForNormativePunishmentDrive(
			double proportionalPunishmentsSeen,
			double proportionalEducationalMessagesSeen) {
		this.lastStepProportionalPunishmentsSeen = new Double(
				proportionalPunishmentsSeen);
		this.lastStepProportionalEducationalMessagesSeen = new Double(
				proportionalEducationalMessagesSeen);
		// System.out.println("lastStepProportionalPunishmentsSeen: "+lastStepProportionalPunishmentsSeen
		// +
		// " lastStepProportionalEducationalMessagesSeen:"+lastStepProportionalEducationalMessagesSeen);
	}

	public double getNormalizedActionsObservedIncrement() {
		return normalizedActionsObservedIncrement;
	}

	public double getLastFirstStageReward() {
		return this.lastFirstStageReward;
	}

	public void reducePunishmentCost(double proportionalFirstStageDefection,
			int punAction) {

		if (!DECREASEDPUNCOST) {
			if ((this.lastStepFirstStageDefection <= proportionalFirstStageDefection && proportionalFirstStageDefection > TOLERANCE_THRESHOLD)
			// && NORM_EMERGED
			) {
				// System.out.println("\t A"+this.getID()+" INCREASED PUN COST: "
				// + this.PUNISHMENT_COST + " lastStepFirstStageDefection: " +
				// lastStepFirstStageDefection +
				// " proportionalFirstStageDefection: "+
				// proportionalFirstStageDefection );
				this.PUNISHMENT_COST += PUNISHMENT_GRADIENT;
				if (this.PUNISHMENT_COST > 10) {
					this.PUNISHMENT_COST = 10;
				}
			}
			if (((this.lastStepFirstStageDefection > proportionalFirstStageDefection) || proportionalFirstStageDefection <= TOLERANCE_THRESHOLD)
			// && NORM_EMERGED
			) {
				// System.out.println("\t A"+this.getID()+" DECREASED PUN COST: "
				// + this.PUNISHMENT_COST + " lastStepFirstStageDefection: " +
				// lastStepFirstStageDefection +
				// " proportionalFirstStageDefection: "+
				// proportionalFirstStageDefection );
				this.PUNISHMENT_COST -= PUNISHMENT_GRADIENT;
				if (this.PUNISHMENT_COST < 0 && punAction == 2) {
					this.PUNISHMENT_COST = 0;
				}
				if (this.PUNISHMENT_COST < 1 && punAction == 1) {
					this.PUNISHMENT_COST = 1;
				}
			}
			DECREASEDPUNCOST = true;
		}

		// this.PUNISHMENT_COST=1.0;
		// if(this.PUNISHMENT_COST> this.INDIVIDUAL_DRIVE_NORMALIZER_XMAX){
		// this.INDIVIDUAL_DRIVE_NORMALIZER_XMAX = this.PUNISHMENT_COST;
		// }
		// this.INDIVIDUAL_DRIVE_NORMALIZER_XMIN =new Double( (1 -
		// (this.PUNISHMENT_COST)));
		this.lastStepFirstStageDefection = new Double(
				proportionalFirstStageDefection);

	}

	public double getPunishmentCost() {
		return this.PUNISHMENT_COST;
	}

	public void receivePunishment(int type, double cost) {
		if (type == 1) {
			strategicThirdPartyPunishments++;
			this.lastSecondStageReward -= new Double(cost);
			this.lastSecondStageRewardWithoutPunishmentSent -= new Double(cost);
		}
		if (type == 2) {
			normativeThirdPartyPunishments++;
			this.lastSecondStageReward -= new Double(cost);
			this.lastSecondStageRewardWithoutPunishmentSent -= new Double(cost);
		}
		if (type == 3) {
			educationalThirdPartyPunishments++;
		}
	}

	public void receivePunishmentDP(int type, double damage) {
		if (type == 1) {
			strategicThirdPartyPunishments++;
			this.damagesReceived.add(damage);
		}
		if (type == 2) {
			normativeThirdPartyPunishments++;
			this.damagesReceived.add(damage);
		}
		if (type == 3) {
			educationalThirdPartyPunishments++;
		}

	}

	public void deducePunishmentCostDP(double cost) {
		this.lastSecondStageReward -= new Double(cost);
		// this.lastSecondStageRewardWithoutPunishmentSent -= new Double(cost);
	}

	public void deducePunishmentCostPunSan() {
		Double cost = new Double(this.lastSecondStageActionDP.size() * 1.0);
		this.lastSecondStageReward -= new Double(cost);
	}

	public void resetSecondStageActionDP() {
		this.DECREASEDPUNCOST = false;
		this.lastSecondStageActionDP.clear();
	}

	public Vector<Integer> getSecondStageActionsDP() {
		return this.lastSecondStageActionDP;
	}

	public int getStrategicThirdPartyPunishments() {
		return strategicThirdPartyPunishments;
	}

	public int getNormativeThirdPartyPunishments() {
		return normativeThirdPartyPunishments;
	}

	public int getEducationalThirdPartyPunishments() {
		return educationalThirdPartyPunishments;
	}

	public void deducePunishmentsReceived() {
		double totaldamage = 0;
		for (int i = 0; i < this.damagesReceived.size(); i++) {
			totaldamage += new Double(damagesReceived.elementAt(i));
		}
		this.lastSecondStageReward -= new Double(totaldamage);
		this.lastSecondStageRewardWithoutPunishmentSent -= new Double(
				totaldamage);
	}

	@Override
	public void receivePunishmentDP(int type) {
		if (type == 1) {
			strategicThirdPartyPunishments++;
		}
		if (type == 2) {
			normativeThirdPartyPunishments++;
		}
		if (type == 3) {
			educationalThirdPartyPunishments++;
		}
	}

	@Override
	public void receivePunishmentPunSan(int action, double damage) {
	}

	@Override
	public void updateMixedStrategies(double cooperators) {
	}
}