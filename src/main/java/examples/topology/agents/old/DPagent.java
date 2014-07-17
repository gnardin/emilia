package examples.topology.agents.old;

import java.util.Vector;

public abstract class DPagent {

	public abstract double getPunishmentCost();

	public abstract int chooseFirstStageAction(int index);

	public abstract int getLastFirstStageAction();

	public abstract void receivePayOffFromFirstStage(double rewardForDefector);

	public abstract boolean isStrategicPunisher();

	public abstract boolean isNormativePunisher();

	public abstract void updateStrategicPunishmentDrive(
			double proportionalFirstStageCooperation);

	public abstract void updateNormativePunishmentDrive(
			double proportionalFirstStageDefection);

	public abstract void updatePunishmentStrategy();

	public abstract void resetSecondStageActionDP();

	public abstract int chooseSecondStageActionDP(
			double proportionalFirstStageDefection, int oponent, int index);

	public abstract void receivePunishmentDP(int action);

	public abstract void receivePunishmentPunSan(int action, double damage);

	public abstract void updateMeaningfulStrategicThirdPartyPunishments(int i);

	public abstract void updateMeaningfulNormativeThirdPartyPunishments(int i);

	public abstract void updateMeaningfulEducationalThirdPartyPunishments(int i);

	public abstract void deducePunishmentCostDP(double c);

	public abstract void deducePunishmentCostPunSan();

	public abstract void deducePunishmentsReceived();

	public abstract void updatePayoffs();

	public abstract Vector<Integer> getSecondStageActionsDP();

	public abstract int getStrategicThirdPartyPunishments();

	public abstract int getNormativeThirdPartyPunishments();

	public abstract int getEducationalThirdPartyPunishments();

	public abstract int ObserveSelfPunishments(int i);

	public abstract int getMeaningfulStrategicPunishmentsReceived();

	public abstract int getMeaningfulNormativePunishmentsReceived();

	public abstract int getMeaningfulEducativePunishmentsReceived();

	public abstract void updateVariablesForNormativePunishmentDrive(
			double proportionalPunishmentsSeen, double d);

	public abstract void updateNormSalience(double firstStageCooperation,
			double firstStageDefection, double normativePunishmentsDone,
			double normativePunishmentsReceived,
			double strategicPunishmentsDone,
			double strategicPunishmentsReceived,
			double educationalMessagesSent,
			double educativePunishmentsReceived, double nonPunishedDefectors,
			double meaningfulStrategicPunishmentsSeen,
			double meaningfulNormativePunishmentsSeen,
			double meaningfulEducationalMessagesSeen,
			double meaningfulStrategicPunishmentsReceived,
			double meaningfulNormativePunishmentsReceived,
			double meaningfulEducativePunishmentsReceived);

	public abstract void updateMixedStrategies(double cooperators);

	public abstract void updateMixedStrategies();

	public abstract double getSalience();

	public abstract boolean activeSalience();

	public abstract double getIndividualDrive();

	public abstract double getNormativeDrive();

	public abstract double getHonestyProb();

	public abstract double getPunishmentProb();

	public abstract double getStrategicPunishmentDrive();

	public abstract double getNormativePunishmentDrive();

	public void updateSocialDrive(int mostFrequentStrategyAmongstNeighbours,
			double proportionalNeighboursWithMostFrequentStrategy,
			int mostSuccesfulNeighbourStrategy,
			double proportionalNeighboursWithMostSuccesfulStrategy) {
	}

	public abstract double getNormalizedActionsObservedIncrement();
}