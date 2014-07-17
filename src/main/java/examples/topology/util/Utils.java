package examples.topology.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Vector;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;

public class Utils {

	public static int FindMaxInVector(Vector<Object> v,
			RandomEngine randGenerator) {
		Uniform rand = new Uniform(0.0, v.size(), randGenerator);

		int maxPointer = rand.nextIntFromTo(0, v.size() - 1);
		double max = (Double) v.elementAt(maxPointer);

		int pointer;
		double aux;
		for (int i = 0; i < v.size(); i++) {
			pointer = (i + maxPointer) % v.size();
			aux = (Double) v.elementAt(pointer);
			if (aux > max) {
				maxPointer = pointer;
				max = aux;
			}
		}

		return maxPointer;
	}

	static int FindMinInVector(Vector<Object> v) {
		if (v.size() > 1) {
			RandomEngine randGenerator = new MersenneTwister(
					new java.util.Date());
			Uniform rand = new Uniform(0.0, 100, randGenerator);

			int minPointer = rand.nextIntFromTo(0, v.size() - 1);
			int min = new Integer((Integer) v.elementAt(minPointer));

			for (int i = 0; i < v.size(); i++) {
				int aux = new Integer((Integer) v.elementAt((i + minPointer)
						% v.size()));
				if (aux < min) {
					minPointer = (i + minPointer) % v.size();
					min = new Integer((Integer) v.elementAt((i + minPointer)
							% v.size()));
				}
			}
			return minPointer;
		} else {
			return 0;
		}

	}

	public static double Mean(Vector<Double> Data) {
		double mean_value = new Double(0.0);
		double acumulative = new Double(0.0);

		for (int i = 0; i < Data.size(); i++) {
			acumulative += Data.elementAt(i);
		}
		if (acumulative != 0) {
			mean_value = acumulative / Data.size();
		}

		return mean_value;
	}

	static double MeanNoZeros(Vector<Double> Data) {
		double mean_value = new Double(0.0);
		double acumulative = new Double(0.0);
		double elements = 0;

		for (int i = 0; i < Data.size(); i++) {
			if (Data.elementAt(i) > 0) {
				acumulative += Data.elementAt(i);
				elements++;
			}
		}
		if (acumulative != 0) {
			mean_value = acumulative / elements;
		}

		return mean_value;
	}

	public static double STD(Vector<Double> Data) {
		double counter;
		if (Data.size() > 0) {
			double mean = Mean(Data);

			counter = (float) Math.pow(new Double(Data.elementAt(0)) - mean,
					2.0);

			int amount = 1;

			for (int i = 1; i < Data.size(); i++) {
				counter += (float) Math.pow(new Double(Data.elementAt(i))
						- mean, 2.0);
				amount++;
			}
			return (double) Math.sqrt((counter / amount));
		}
		return 0;
	}

	public static int sample(double[] p, Uniform rand) {
		double rn = rand.nextDoubleFromTo(0, 1);

		Vector<Double> prob = new Vector();
		for (int i = 0; i < p.length; i++) {
			prob.add(p[i]);
		}

		Collections.sort(prob);
		Collections.reverse(prob);

		double sum = prob.elementAt(0);
		int a = 0;
		double pointer = new Double(prob.elementAt(0));
		while (rn > sum && a < prob.size() - 1) {
			a++;
			sum += prob.elementAt(a);
			pointer = new Double(prob.elementAt(a));
		}
		boolean found = false;
		int result = 0;
		for (int i = 0; i < p.length && !found; i++) {
			if (p[i] == pointer) {
				result = new Integer(i);
				found = false;
			}
		}

		return result;

	}

	public static void gzipFile(String from, String to) throws IOException {
		FileInputStream in = new FileInputStream(from);
		GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(to));
		byte[] buffer = new byte[4096];
		int bytesRead;
		while ((bytesRead = in.read(buffer)) != -1)
			out.write(buffer, 0, bytesRead);
		in.close();
		out.close();
	}

	public static String Unzip(String inFilePath) throws Exception {
		GZIPInputStream gzipInputStream = new GZIPInputStream(
				new FileInputStream(inFilePath));

		String outFilePath = inFilePath.replace(".gz", "");
		OutputStream out = new FileOutputStream(outFilePath);

		byte[] buf = new byte[1024];
		int len;
		while ((len = gzipInputStream.read(buf)) > 0)
			out.write(buf, 0, len);

		gzipInputStream.close();
		out.close();

		new File(inFilePath).delete();

		return outFilePath;
	}

	private static Vector<Double> LineToNumber(String line) {
		Vector<Double> result = new Vector();
		int lineLength = line.length();

		// System.out.println("line:" + line.length());

		String[] lineSplit = line.split("	");

		for (int i = 0; i < lineSplit.length; i++) {
			// System.out.println("i="+i+"  lineSplit[i]:"+lineSplit[i]);
			if (!lineSplit[i].isEmpty()) {
				double aux = new Double(Double.valueOf(lineSplit[i]));
				result.add(new Double(aux));
			}
		}

		return result;
	}

	public static void AnalyzeFiles(String filename, int NUMFILES,
			int amountAgents2, int neighbourSize2, int network_type,
			int punishmentDist, int punishmentType, double agentsHprob,
			double agentsPprob, int educatorsAmount, double educatorsPprob,
			double educatorsSalience, double punishmentDamage,
			double amplificationFactor, double individualW, double socialW,
			double normativeW, int punishDist, int pureStrategic, int nfr)
			throws Exception {
		Vector<FileReader> freaders = new Vector();
		Vector<BufferedReader> breaders = new Vector();
		Vector<Boolean> openFiles = new Vector();
		int numOpenFiles = 0;

		String outfilename = new String("");
		outfilename += filename + ".final";
		String outZipFilename = new String("Dyn-" + outfilename + ".gz");

		BufferedWriter secPrinter;
		secPrinter = new BufferedWriter(new FileWriter(outfilename));

		for (int i = 0; i < NUMFILES; i++) {
			String name1 = new String(filename + "-" + i);
			// openFiles.add(new String(name1));
			String zipName = name1 + ".gz";
			Unzip(zipName);
			openFiles.add(new Boolean(true));
			String aux_filename = new String(name1);
			// LECTURA
			FileReader istream = new FileReader(aux_filename);
			freaders.add(new FileReader(aux_filename));
			// BufferedReader in = new BufferedReader(istream);
			breaders.add(new BufferedReader(istream));
			numOpenFiles++;
		}
		int index = 0;
		while (numOpenFiles > 0) {
			Vector<Double> avgCooperators = new Vector();
			Vector<Double> avgDefectors = new Vector(); // 3
			Vector<Double> avgStrategicPunishers = new Vector(); // 4
			Vector<Double> avgNormativePunishers = new Vector(); // 5
			Vector<Double> avgIndividualDrive = new Vector(); // 6
			Vector<Double> avgSocialDrive = new Vector(); // 7
			Vector<Double> avgNormativeDrive = new Vector(); // 8
			Vector<Double> avgStrategicPDrive = new Vector(); // 9
			Vector<Double> avgNormativePDrive = new Vector(); // 10
			Vector<Double> avghProb = new Vector(); // 11
			Vector<Double> avgpProb = new Vector(); // 12
			Vector<Double> avgSalience = new Vector(); // 13
			Vector<Double> avgSalienceActive = new Vector(); // 13
			Vector<Double> strategicPunishmentsDone = new Vector(); // 14
			Vector<Double> normativePunishmentsDone = new Vector(); // 15
			Vector<Double> educationalMessagesSent = new Vector(); // 16
			Vector<Double> avgAgentsWithSalience = new Vector(); // 17
			Vector<Double> avgCooperatorsWithSalience = new Vector(); // 18
			Vector<Double> avgDefectorsWithSalience = new Vector(); // 19
			Vector<Double> avgPunishersWithSalience = new Vector(); // 20
			Vector<Double> majorityAction = new Vector(); // 21
			Vector<Double> punCost = new Vector();
			Vector<Double> hubPunCost = new Vector();
			Vector<Double> leafPunCost = new Vector();

			for (int i = 0; i < openFiles.size(); i++) {
				String line = new String();
				int pointer = -1;
				if (openFiles.elementAt(i)) {
					pointer = new Integer(i);
					if ((line = breaders.elementAt(pointer).readLine()) == null) {
						openFiles.remove(pointer);
						openFiles.add(pointer, new Boolean(false));
						breaders.elementAt(i).close();
						numOpenFiles = numOpenFiles - 1;
					} else {
						Vector<Double> numbersFromLine = new Vector(
								LineToNumber(line));

						// System.out.println("numbersFromLine.size():"+numbersFromLine.size());
						avgCooperators.add(new Double(numbersFromLine
								.elementAt(1)));
						avgDefectors.add(new Double(numbersFromLine
								.elementAt(2))); // 3
						avgStrategicPunishers.add(new Double(numbersFromLine
								.elementAt(3))); // 4
						avgNormativePunishers.add(new Double(numbersFromLine
								.elementAt(4))); // 5
						avgIndividualDrive.add(new Double(numbersFromLine
								.elementAt(5))); // 6
						avgSocialDrive.add(new Double(numbersFromLine
								.elementAt(6))); // 7
						avgNormativeDrive.add(new Double(numbersFromLine
								.elementAt(7))); // 8
						avgStrategicPDrive.add(new Double(numbersFromLine
								.elementAt(8))); // 9
						avgNormativePDrive.add(new Double(numbersFromLine
								.elementAt(9))); // 10
						avghProb.add(new Double(numbersFromLine.elementAt(10))); // 11
						avgpProb.add(new Double(numbersFromLine.elementAt(11))); // 12
						avgSalience.add(new Double(numbersFromLine
								.elementAt(12))); // 13
						avgSalienceActive.add(new Double(numbersFromLine
								.elementAt(7))); // 13
						strategicPunishmentsDone.add(new Double(numbersFromLine
								.elementAt(14))); // 14
						normativePunishmentsDone.add(new Double(numbersFromLine
								.elementAt(15))); // 15
						educationalMessagesSent.add(new Double(numbersFromLine
								.elementAt(16))); // 16
						avgAgentsWithSalience.add(new Double(numbersFromLine
								.elementAt(17))); // 17
						avgCooperatorsWithSalience.add(new Double(
								numbersFromLine.elementAt(18))); // 18
						avgDefectorsWithSalience.add(new Double(numbersFromLine
								.elementAt(19))); // 19
						avgPunishersWithSalience.add(new Double(numbersFromLine
								.elementAt(20))); // 20
						majorityAction.add(new Double(numbersFromLine
								.elementAt(21))); // 21
						punCost.add(new Double(numbersFromLine.elementAt(22))); // 21
						hubPunCost
								.add(new Double(numbersFromLine.elementAt(23))); // 21
						leafPunCost.add(new Double(numbersFromLine
								.elementAt(24))); // 21
					}
				}
			}
			if (numOpenFiles > 0) {
				float mean_avgCooperators = new Float(Mean(avgCooperators));
				float mean_avgDefectors = new Float(Mean(avgDefectors)); // 3
				float mean_avgStrategicPunishers = new Float(
						Mean(avgStrategicPunishers)); // 4
				float mean_avgNormativePunishers = new Float(
						Mean(avgNormativePunishers)); // 5
				float mean_avgIndividualDrive = new Float(
						Mean(avgIndividualDrive)); // 6
				float mean_avgSocialDrive = new Float(Mean(avgSocialDrive)); // 7
				float mean_avgNormativeDrive = new Float(
						Mean(avgNormativeDrive)); // 8
				float mean_avgStrategicPDrive = new Float(
						Mean(avgStrategicPDrive)); // 9
				float mean_avgNormativePDrive = new Float(
						Mean(avgNormativePDrive)); // 10
				float mean_avghProb = new Float(Mean(avghProb)); // 11
				float mean_avgpProb = new Float(Mean(avgpProb)); // 12
				float mean_avgSalience = new Float(Mean(avgSalience)); // 13
				float mean_strategicPunishmentsDone = new Float(
						Mean(strategicPunishmentsDone)); // 14
				float mean_normativePunishmentsDone = new Float(
						Mean(normativePunishmentsDone)); // 15
				float mean_educationalMessagesSent = new Float(
						Mean(educationalMessagesSent)); // 16
				float mean_avgAgentsWithSalience = new Float(
						Mean(avgAgentsWithSalience)); // 17
				float mean_avgSalienceActive = new Float(
						Mean(avgSalienceActive)); // 17
				float mean_avgCooperatorsWithSalience = new Float(
						Mean(avgCooperatorsWithSalience)); // 18
				float mean_avgDefectorsWithSalience = new Float(
						Mean(avgDefectorsWithSalience)); // 19
				float mean_avgPunishersWithSalience = new Float(
						Mean(avgPunishersWithSalience)); // 20
				float mean_majorityAction = new Float(Mean(majorityAction)); // 21
				float mean_punCost = new Float(Mean(punCost)); // 22
				float mean_hubPunCost = new Float(Mean(hubPunCost)); // 23
				float mean_leafPunCost = new Float(Mean(leafPunCost)); // 24
				float std_avgCooperators = new Float(STD(avgCooperators));
				float std_avgDefectors = new Float(STD(avgDefectors)); // 3
				float std_avgStrategicPunishers = new Float(
						STD(avgStrategicPunishers)); // 4
				float std_avgNormativePunishers = new Float(
						STD(avgNormativePunishers)); // 5
				float std_avgIndividualDrive = new Float(
						STD(avgIndividualDrive)); // 6
				float std_avgSocialDrive = new Float(STD(avgSocialDrive)); // 7
				float std_avgNormativeDrive = new Float(STD(avgNormativeDrive)); // 8
				float std_avgStrategicPDrive = new Float(
						STD(avgStrategicPDrive)); // 9
				float std_avgNormativePDrive = new Float(
						STD(avgNormativePDrive)); // 10
				float std_avghProb = new Float(STD(avghProb)); // 11
				float std_avgpProb = new Float(STD(avgpProb)); // 12
				float std_avgSalience = new Float(STD(avgSalience)); // 13
				float std_strategicPunishmentsDone = new Float(
						STD(strategicPunishmentsDone)); // 14
				float std_normativePunishmentsDone = new Float(
						STD(normativePunishmentsDone)); // 15
				float std_educationalMessagesSent = new Float(
						STD(educationalMessagesSent)); // 16
				float std_avgAgentsWithSalience = new Float(
						STD(avgAgentsWithSalience)); // 17
				float std_avgSalienceActive = new Float(STD(avgSalienceActive)); // 17
				float std_avgCooperatorsWithSalience = new Float(
						STD(avgCooperatorsWithSalience)); // 18
				float std_avgDefectorsWithSalience = new Float(
						STD(avgDefectorsWithSalience)); // 19
				float std_avgPunishersWithSalience = new Float(
						STD(avgPunishersWithSalience)); // 20
				float std_majorityAction = new Float(STD(majorityAction));
				float std_punCost = new Float(STD(punCost)); // 22

				secPrinter.write(index + "\t" + // 1
						mean_avgCooperators + "\t" + // 2
						mean_avgDefectors + "\t" + // 3
						mean_avgStrategicPunishers + "\t" + // 4
						mean_avgNormativePunishers + "\t" + // 5
						mean_avgIndividualDrive + "\t" + // 6
						mean_avgSocialDrive + "\t" + // 7
						mean_avgNormativeDrive + "\t" + // 8
						mean_avgStrategicPDrive + "\t" + // 9
						mean_avgNormativePDrive + "\t" + // 10
						mean_avghProb + "\t" + // 11
						mean_avgpProb + "\t" + // 12
						mean_avgSalience + "\t" + // 13
						mean_avgSalienceActive + "\t" + // 14
						mean_strategicPunishmentsDone + "\t" + // 15
						mean_normativePunishmentsDone + "\t" + // 16
						mean_educationalMessagesSent + "\t" + // 17
						mean_avgAgentsWithSalience + "\t" + // 18
						mean_avgCooperatorsWithSalience + "\t" + // 19
						mean_avgDefectorsWithSalience + "\t" + // 20
						mean_avgPunishersWithSalience + "\t" + // 21
						mean_majorityAction + "\t" + // 22
						mean_punCost + "\t" + // 23
						mean_hubPunCost + "\t" + // 24
						mean_leafPunCost + "\t" + // 25
						std_avgCooperators + "\t" + // 26
						std_avgDefectors + "\t" + // 27
						std_avgStrategicPunishers + "\t" + // 28
						std_avgNormativePunishers + "\t" + // 29
						std_avgIndividualDrive + "\t" + // 30
						std_avgSocialDrive + "\t" + // 31
						std_avgNormativeDrive + "\t" + // 32
						std_avgStrategicPDrive + "\t" + // 33
						std_avgNormativePDrive + "\t" + // 34
						std_avghProb + "\t" + // 35
						std_avgpProb + "\t" + // 36
						std_avgSalience + "\t" + // 37
						std_avgSalienceActive + "\t" + // 38
						std_strategicPunishmentsDone + "\t" + // 39
						std_normativePunishmentsDone + "\t" + // 40
						std_educationalMessagesSent + "\t" + // 41
						std_avgAgentsWithSalience + "\t" + // 42
						std_avgCooperatorsWithSalience + "\t" + // 43
						std_avgDefectorsWithSalience + "\t" + // 44
						std_avgPunishersWithSalience + "\t" + // 45
						std_majorityAction + "\t" + // 46
						amountAgents2 + "\t" + // 47
						neighbourSize2 + "\t" + // 48
						network_type + "\t" + // 49
						punishmentDist + "\t" + // 50
						punishmentType + "\t" + // 51
						agentsHprob + "\t" + // 52
						agentsPprob + "\t" + // 53
						educatorsAmount + "\t" + // 54
						educatorsPprob + "\t" + // 55
						educatorsSalience + "\t" + // 56
						punishmentDamage + "\t" + // 57
						amplificationFactor + "\t" + // 58
						individualW + "\t" + // 59
						socialW + "\t" + // 60
						normativeW + "\t" + // 61
						punishDist + "\t" + // 62
						pureStrategic + "\t" + // 63
						nfr);
				secPrinter.newLine();
				secPrinter.flush();
				index++;
			}
		}

		secPrinter.close();

		for (int i = 0; i < NUMFILES; i++) {
			String name1 = new String("./" + filename + "-" + i);
			String zipName = name1 + ".gz";
			Utils.gzipFile(name1, zipName);
			File f = new File(zipName);
			f.delete();
		}

		for (int i = 0; i < NUMFILES; i++) {
			String name1 = new String("./" + filename + "-" + i);
			File f = new File(name1);
			f.delete();
		}

		Utils.gzipFile(outfilename, outZipFilename);
		File f = new File(outfilename);
		f.delete();

	}

	public static void AnalyzeFilesDP(String filename, int NUMFILES,
			int amountAgents, int numPlayers, int punishmentType,
			double agentsHprob, double agentsPprob, double individualWeight,
			double normativeWeight, int punishers, double initialSalience,
			double explorationRate) throws Exception {
		Vector<FileReader> freaders = new Vector();
		Vector<BufferedReader> breaders = new Vector();
		Vector<Boolean> openFiles = new Vector();
		int numOpenFiles = 0;

		String outfilename = new String("");
		outfilename += filename + ".final";
		String outZipFilename = new String("Dyn-" + outfilename + ".gz");

		BufferedWriter secPrinter;
		secPrinter = new BufferedWriter(new FileWriter(outfilename));

		for (int i = 0; i < NUMFILES; i++) {
			String name1 = new String(filename + "-" + i);
			// openFiles.add(new String(name1));
			String zipName = name1 + ".gz";
			Unzip(zipName);
			openFiles.add(new Boolean(true));
			String aux_filename = new String(name1);
			// LECTURA
			FileReader istream = new FileReader(aux_filename);
			freaders.add(new FileReader(aux_filename));
			// BufferedReader in = new BufferedReader(istream);
			breaders.add(new BufferedReader(istream));
			numOpenFiles++;
		}
		int index = 0;
		while (numOpenFiles > 0) {
			Vector<Double> avgCooperators = new Vector(); // 2
			Vector<Double> avgDefectors = new Vector(); // 3
			Vector<Double> avgPunishers = new Vector(); // 4
			Vector<Double> avgStrategicPunishers = new Vector(); // 5
			Vector<Double> avgNormativePunishers = new Vector(); // 6
			Vector<Double> avgIndividualDrive = new Vector(); // 7
			Vector<Double> avgNormativeDrive = new Vector(); // 8
			Vector<Double> avgStrategicPDrive = new Vector(); // 9
			Vector<Double> avgNormativePDrive = new Vector(); // 10
			Vector<Double> avghProb = new Vector(); // 11
			Vector<Double> avgpProb = new Vector(); // 12
			Vector<Double> avgSalience = new Vector(); // 13
			Vector<Double> avgStrategicPunishments = new Vector(); // 14
			Vector<Double> avgNormativePunishments = new Vector(); // 15
			Vector<Double> avgPunishments = new Vector(); // 16

			for (int i = 0; i < openFiles.size(); i++) {
				String line = new String();
				int pointer = -1;
				if (openFiles.elementAt(i)) {
					pointer = new Integer(i);
					if ((line = breaders.elementAt(pointer).readLine()) == null) {
						openFiles.remove(pointer);
						openFiles.add(pointer, new Boolean(false));
						breaders.elementAt(i).close();
						numOpenFiles = numOpenFiles - 1;
					} else {
						Vector<Double> numbersFromLine = new Vector(
								LineToNumber(line));

						avgCooperators.add(new Double(numbersFromLine
								.elementAt(1)));
						avgDefectors.add(new Double(numbersFromLine
								.elementAt(2))); // 3
						avgPunishers.add(new Double(numbersFromLine
								.elementAt(3))); // 4
						avgStrategicPunishers.add(new Double(numbersFromLine
								.elementAt(4))); // 4
						avgNormativePunishers.add(new Double(numbersFromLine
								.elementAt(5))); // 5
						avgIndividualDrive.add(new Double(numbersFromLine
								.elementAt(6))); // 6
						avgNormativeDrive.add(new Double(numbersFromLine
								.elementAt(7))); // 8
						avgStrategicPDrive.add(new Double(numbersFromLine
								.elementAt(8))); // 9
						avgNormativePDrive.add(new Double(numbersFromLine
								.elementAt(9))); // 10
						avghProb.add(new Double(numbersFromLine.elementAt(10))); // 11
						avgpProb.add(new Double(numbersFromLine.elementAt(11))); // 12
						avgSalience.add(new Double(numbersFromLine
								.elementAt(12))); // 13
					}
				}
			}

			if (numOpenFiles > 0) {
				float mean_avgCooperators = new Float(Mean(avgCooperators));
				float mean_avgDefectors = new Float(Mean(avgDefectors)); // 3
				float mean_avgPunishers = new Float(Mean(avgPunishers)); // 4
				float mean_avgStrategicPunishers = new Float(
						Mean(avgStrategicPunishers)); // 5
				float mean_avgNormativePunishers = new Float(
						Mean(avgNormativePunishers)); // 6
				float mean_avgIndividualDrive = new Float(
						Mean(avgIndividualDrive)); // 7
				float mean_avgNormativeDrive = new Float(
						Mean(avgNormativeDrive)); // 8
				float mean_avgStrategicPDrive = new Float(
						Mean(avgStrategicPDrive)); // 9
				float mean_avgNormativePDrive = new Float(
						Mean(avgNormativePDrive)); // 10
				float mean_avghProb = new Float(Mean(avghProb)); // 11
				float mean_avgpProb = new Float(Mean(avgpProb)); // 12
				float mean_avgSalience = new Float(Mean(avgSalience)); // 13

				secPrinter.write(index + "\t" + // 1
						mean_avgCooperators + "\t" + // 2
						mean_avgDefectors + "\t" + // 3
						mean_avgPunishers + "\t" + // 4
						mean_avgStrategicPunishers + "\t" + // 5
						mean_avgNormativePunishers + "\t" + // 6
						mean_avgIndividualDrive + "\t" + // 7
						mean_avgNormativeDrive + "\t" + // 8
						mean_avgStrategicPDrive + "\t" + // 9
						mean_avgNormativePDrive + "\t" + // 10
						mean_avghProb + "\t" + // 11
						mean_avgpProb + "\t" + // 12
						mean_avgSalience + "\t" + // 13
						amountAgents + "\t" + // 14
						numPlayers + "\t" + // 15
						punishmentType + "\t" + // 16
						agentsHprob + "\t" + // 17
						agentsPprob + "\t" + // 18
						individualWeight + "\t" + // 19
						normativeWeight + "\t" + // 20
						punishers + "\t" + // 21
						initialSalience + "\t" + // 22
						explorationRate);
				secPrinter.newLine();
				secPrinter.flush();
				index++;
			}
		}

		secPrinter.close();

		for (int i = 0; i < NUMFILES; i++) {
			String name1 = new String("./" + filename + "-" + i);
			String zipName = name1 + ".gz";
			Utils.gzipFile(name1, zipName);
			File f = new File(zipName);
			f.delete();
		}

		for (int i = 0; i < NUMFILES; i++) {
			String name1 = new String("./" + filename + "-" + i);
			File f = new File(name1);
			f.delete();
		}
	}

	public static void AnalyzeFilesPunSan(String filename, int NUMFILES,
			int amountAgents, int numPlayers, int punishmentType,
			double agentsHprob, double agentsPprob, double individualWeight,
			double normativeWeight, int punishers, double initialSalience,
			double explorationRate) throws Exception {
		Vector<FileReader> freaders = new Vector();
		Vector<BufferedReader> breaders = new Vector();
		Vector<Boolean> openFiles = new Vector();
		int numOpenFiles = 0;

		String outfilename = new String("");
		outfilename += filename + ".final";
		String outZipFilename = new String("Dyn-" + outfilename + ".gz");

		BufferedWriter secPrinter;
		secPrinter = new BufferedWriter(new FileWriter(outfilename));

		for (int i = 0; i < NUMFILES; i++) {
			String name1 = new String(filename + "-" + i);
			// openFiles.add(new String(name1));
			String zipName = name1 + ".gz";
			Unzip(zipName);
			openFiles.add(new Boolean(true));
			String aux_filename = new String(name1);
			// LECTURA
			FileReader istream = new FileReader(aux_filename);
			freaders.add(new FileReader(aux_filename));
			// BufferedReader in = new BufferedReader(istream);
			breaders.add(new BufferedReader(istream));
			numOpenFiles++;
		}
		int index = 0;
		while (numOpenFiles > 0) {
			Vector<Double> avgCooperators = new Vector(); // 2
			Vector<Double> avgDefectors = new Vector(); // 3
			Vector<Double> avgPunishers = new Vector(); // 4
			Vector<Double> avgStrategicPunishers = new Vector(); // 5
			Vector<Double> avgNormativePunishers = new Vector(); // 6
			Vector<Double> avgIndividualDrive = new Vector(); // 7
			Vector<Double> avgNormativeDrive = new Vector(); // 8
			Vector<Double> avgStrategicPDrive = new Vector(); // 9
			Vector<Double> avgNormativePDrive = new Vector(); // 10
			Vector<Double> avghProb = new Vector(); // 11
			Vector<Double> avgpProb = new Vector(); // 12
			Vector<Double> avgSalience = new Vector(); // 13
			Vector<Double> avgStrategicPunishments = new Vector(); // 14
			Vector<Double> avgNormativePunishments = new Vector(); // 15
			Vector<Double> avgPunishments = new Vector(); // 16
			Vector<Double> avgNormalizedActionsObservedIncrement = new Vector(); // 17

			for (int i = 0; i < openFiles.size(); i++) {
				String line = new String();
				int pointer = -1;
				if (openFiles.elementAt(i)) {
					pointer = new Integer(i);
					if ((line = breaders.elementAt(pointer).readLine()) == null) {
						openFiles.remove(pointer);
						openFiles.add(pointer, new Boolean(false));
						breaders.elementAt(i).close();
						numOpenFiles = numOpenFiles - 1;
					} else {
						Vector<Double> numbersFromLine = new Vector(
								LineToNumber(line));

						avgCooperators.add(new Double(numbersFromLine
								.elementAt(1)));
						avgDefectors.add(new Double(numbersFromLine
								.elementAt(2))); // 3
						avgPunishers.add(new Double(numbersFromLine
								.elementAt(3))); // 4
						avgStrategicPunishers.add(new Double(numbersFromLine
								.elementAt(4))); // 4
						avgNormativePunishers.add(new Double(numbersFromLine
								.elementAt(5))); // 5
						avgIndividualDrive.add(new Double(numbersFromLine
								.elementAt(6))); // 6
						avgNormativeDrive.add(new Double(numbersFromLine
								.elementAt(7))); // 8
						avgStrategicPDrive.add(new Double(numbersFromLine
								.elementAt(8))); // 9
						avgNormativePDrive.add(new Double(numbersFromLine
								.elementAt(9))); // 10
						avghProb.add(new Double(numbersFromLine.elementAt(10))); // 11
						avgpProb.add(new Double(numbersFromLine.elementAt(11))); // 12
						avgSalience.add(new Double(numbersFromLine
								.elementAt(12))); // 13
						avgStrategicPunishments.add(new Double(numbersFromLine
								.elementAt(13))); // 14
						avgNormativePunishments.add(new Double(numbersFromLine
								.elementAt(14))); // 15
						avgPunishments.add(new Double(numbersFromLine
								.elementAt(15))); // 16
						avgNormalizedActionsObservedIncrement.add(new Double(
								numbersFromLine.elementAt(16))); // 17
					}
				}
			}
			if (numOpenFiles > 0) {
				float mean_avgCooperators = new Float(Mean(avgCooperators));
				float mean_avgDefectors = new Float(Mean(avgDefectors)); // 3
				float mean_avgPunishers = new Float(Mean(avgPunishers)); // 4
				float mean_avgStrategicPunishers = new Float(
						Mean(avgStrategicPunishers)); // 5
				float mean_avgNormativePunishers = new Float(
						Mean(avgNormativePunishers)); // 6
				float mean_avgIndividualDrive = new Float(
						Mean(avgIndividualDrive)); // 7
				float mean_avgNormativeDrive = new Float(
						Mean(avgNormativeDrive)); // 8
				float mean_avgStrategicPDrive = new Float(
						Mean(avgStrategicPDrive)); // 9
				float mean_avgNormativePDrive = new Float(
						Mean(avgNormativePDrive)); // 10
				float mean_avghProb = new Float(Mean(avghProb)); // 11
				float mean_avgpProb = new Float(Mean(avgpProb)); // 12
				float mean_avgSalience = new Float(Mean(avgSalience)); // 13
				float mean_avgStrategicPunishments = new Float(
						Mean(avgStrategicPunishments)); // 14 //14
				float mean_avgNormativePunishments = new Float(
						Mean(avgNormativePunishments)); // 15 //15
				float mean_avgPunishments = new Float(Mean(avgPunishments)); // 16
				float mean_avgNAOI = new Float(
						Mean(avgNormalizedActionsObservedIncrement));

				secPrinter.write(index + "\t" + // 1
						mean_avgCooperators + "\t" + // 2
						mean_avgDefectors + "\t" + // 3
						mean_avgPunishers + "\t" + // 4
						mean_avgStrategicPunishers + "\t" + // 5
						mean_avgNormativePunishers + "\t" + // 6
						mean_avgIndividualDrive + "\t" + // 7
						mean_avgNormativeDrive + "\t" + // 8
						mean_avgStrategicPDrive + "\t" + // 9
						mean_avgNormativePDrive + "\t" + // 10
						mean_avghProb + "\t" + // 11
						mean_avgpProb + "\t" + // 12
						mean_avgSalience + "\t" + // 13
						mean_avgStrategicPunishments + "\t" + // 14
						mean_avgNormativePunishments + "\t" + // 15
						mean_avgPunishments + "\t" + // 16
						mean_avgNAOI + // 17
						amountAgents + "\t" + // 18
						numPlayers + "\t" + // 19
						punishmentType + "\t" + // 20
						agentsHprob + "\t" + // 21
						agentsPprob + "\t" + // 22
						individualWeight + "\t" + // 23
						normativeWeight + "\t" + // 24
						punishers + "\t" + // 25
						initialSalience + "\t" + // 26
						explorationRate);
				secPrinter.newLine();
				secPrinter.flush();
				index++;
			}
		}

		secPrinter.close();

		for (int i = 0; i < NUMFILES; i++) {
			String name1 = new String("./" + filename + "-" + i);
			String zipName = name1 + ".gz";
			Utils.gzipFile(name1, zipName);
			File f = new File(zipName);
			f.delete();
		}

		for (int i = 0; i < NUMFILES; i++) {
			String name1 = new String("./" + filename + "-" + i);
			File f = new File(name1);
			f.delete();
		}
	}

	public static void AnalyzeInternalizationFiles(String filename,
			int NUMFILES, int network_type, int educatorsAmount,
			double educatorsSalience, double punishmentDamage,
			double amplificationFactor, int calculationTolerance,
			int amountInternalizer, double punishmentProb,
			double riskTolerance, int punishmentDistribution,
			int amountStrategic) throws Exception {
		Vector<FileReader> freaders = new Vector();
		Vector<BufferedReader> breaders = new Vector();
		Vector<Boolean> openFiles = new Vector();
		int numOpenFiles = 0;

		String outfilename = new String("");
		outfilename += filename + ".final";
		String outZipFilename = new String(filename + ".gz");

		BufferedWriter secPrinter;
		secPrinter = new BufferedWriter(new FileWriter(outfilename));

		for (int i = 0; i < NUMFILES; i++) {
			String name1 = new String(filename + "-" + i);
			// openFiles.add(new String(name1));
			String zipName = name1 + ".gz";
			Unzip(zipName);
			openFiles.add(new Boolean(true));
			String aux_filename = new String(name1);
			// LECTURA
			FileReader istream = new FileReader(aux_filename);
			freaders.add(new FileReader(aux_filename));
			// BufferedReader in = new BufferedReader(istream);
			breaders.add(new BufferedReader(istream));
			numOpenFiles++;
		}
		int index = 0;
		while (numOpenFiles > 0) {
			Vector<Double> avgCooperators = new Vector();
			Vector<Double> avgDefectors = new Vector(); // 3
			Vector<Double> avgInternalizer = new Vector(); // 4
			Vector<Double> avgSalience = new Vector(); // 5
			Vector<Double> avgBenefitCostCalculationRepeated = new Vector(); // 6
			Vector<Double> avgnormalizedActionsObservedIncrement = new Vector(); // 7
			Vector<Double> avgProbBeingPunished = new Vector(); // 8
			Vector<Double> avgStrategicPunishers = new Vector(); // 9
			Vector<Double> avgNormativePunishers = new Vector(); // 10
			Vector<Double> strategicPunishmentsDone = new Vector(); // 11
			Vector<Double> normativePunishmentsDone = new Vector(); // 12
			Vector<Double> educationalMessagesSent = new Vector(); // 13

			for (int i = 0; i < openFiles.size(); i++) {
				String line = new String();
				int pointer = -1;
				if (openFiles.elementAt(i)) {
					pointer = new Integer(i);
					if ((line = breaders.elementAt(pointer).readLine()) == null) {
						openFiles.remove(pointer);
						openFiles.add(pointer, new Boolean(false));
						breaders.elementAt(i).close();
						numOpenFiles = numOpenFiles - 1;
					} else {
						Vector<Double> numbersFromLine = new Vector(
								LineToNumber(line));
						// System.out.println("numbersFromLine.size():"+numbersFromLine.size());

						avgCooperators.add(new Double(numbersFromLine
								.elementAt(1)));
						avgDefectors.add(new Double(numbersFromLine
								.elementAt(2))); // 3
						avgInternalizer.add(new Double(numbersFromLine
								.elementAt(3))); // 4
						avgSalience
								.add(new Double(numbersFromLine.elementAt(4))); // 5
						avgBenefitCostCalculationRepeated.add(new Double(
								numbersFromLine.elementAt(5))); // 6
						avgnormalizedActionsObservedIncrement.add(new Double(
								numbersFromLine.elementAt(6))); // 7
						avgProbBeingPunished.add(new Double(numbersFromLine
								.elementAt(7))); // 8
						avgStrategicPunishers.add(new Double(numbersFromLine
								.elementAt(8))); // 9
						avgNormativePunishers.add(new Double(numbersFromLine
								.elementAt(9))); // 10
						strategicPunishmentsDone.add(new Double(numbersFromLine
								.elementAt(10))); // 11
						normativePunishmentsDone.add(new Double(numbersFromLine
								.elementAt(11))); // 12
						educationalMessagesSent.add(new Double(numbersFromLine
								.elementAt(12))); // 13
					}
				}
			}
			if (numOpenFiles > 0) {
				float mean_avgCooperators = new Float(Mean(avgCooperators));
				float mean_avgDefectors = new Float(Mean(avgDefectors)); // 3
				float mean_avgInternalizer = new Float(Mean(avgInternalizer)); // 4
				float mean_avgSalience = new Float(Mean(avgSalience)); // 5
				float mean_avgBenefitCostCalculationRepeated = new Float(
						Mean(avgBenefitCostCalculationRepeated)); // 6
				float mean_avgnormalizedActionsObservedIncrement = new Float(
						Mean(avgnormalizedActionsObservedIncrement)); // 7
				float mean_avgProbBeingPunished = new Float(
						Mean(avgProbBeingPunished)); // 8
				float mean_avgStrategicPunishers = new Float(
						Mean(avgStrategicPunishers)); // 9
				float mean_avgNormativePunishers = new Float(
						Mean(avgNormativePunishers)); // 10
				float mean_strategicPunishmentsDone = new Float(
						Mean(strategicPunishmentsDone)); // 11
				float mean_normativePunishmentsDone = new Float(
						Mean(normativePunishmentsDone)); // 12
				float mean_educationalMessagesSent = new Float(
						Mean(educationalMessagesSent)); // 13
				float std_avgCooperators = new Float(STD(avgCooperators));
				float std_avgDefectors = new Float(STD(avgDefectors)); // 3
				float std_avgInternalizer = new Float(STD(avgInternalizer)); // 4
				float std_avgSalience = new Float(STD(avgSalience)); // 5
				float std_avgBenefitCostCalculationRepeated = new Float(
						STD(avgBenefitCostCalculationRepeated)); // 6
				float std_avgnormalizedActionsObservedIncrement = new Float(
						STD(avgnormalizedActionsObservedIncrement)); // 7
				float std_avgProbBeingPunished = new Float(
						STD(avgProbBeingPunished)); // 8
				float std_avgStrategicPunishers = new Float(
						STD(avgStrategicPunishers)); // 9
				float std_avgNormativePunishers = new Float(
						STD(avgNormativePunishers)); // 10
				float std_strategicPunishmentsDone = new Float(
						STD(strategicPunishmentsDone)); // 11
				float std_normativePunishmentsDone = new Float(
						STD(normativePunishmentsDone)); // 12
				float std_educationalMessagesSent = new Float(
						STD(educationalMessagesSent)); // 13

				secPrinter.write(index + "\t" + // 1
						mean_avgCooperators + "\t" + // 2
						mean_avgDefectors + "\t" + // 3
						mean_avgInternalizer + "\t" + // 4
						mean_avgSalience + "\t" + // 5
						mean_avgBenefitCostCalculationRepeated + "\t" + // 6
						mean_avgnormalizedActionsObservedIncrement + "\t" + // 7
						mean_avgProbBeingPunished + "\t" + // 8
						mean_avgStrategicPunishers + "\t" + // 9
						mean_avgNormativePunishers + "\t" + // 10
						mean_strategicPunishmentsDone + "\t" + // 11
						mean_normativePunishmentsDone + "\t" + // 12
						mean_educationalMessagesSent + "\t" + // 13
						std_avgCooperators + "\t" + // 14
						std_avgDefectors + "\t" + // 15
						std_avgInternalizer + "\t" + // 16
						std_avgSalience + "\t" + // 17
						std_avgBenefitCostCalculationRepeated + "\t" + // 18
						std_avgnormalizedActionsObservedIncrement + "\t" + // 19
						std_avgProbBeingPunished + "\t" + // 20
						std_avgStrategicPunishers + "\t" + // 21
						std_avgNormativePunishers + "\t" + // 22
						std_strategicPunishmentsDone + "\t" + // 23
						std_normativePunishmentsDone + "\t" + // 24
						std_educationalMessagesSent + "\t" + // 25
						network_type + "\t" + // 26
						educatorsAmount + "\t" + // 27
						educatorsSalience + "\t" + // 28
						punishmentDamage + "\t" + // 29
						amplificationFactor + "\t" + // 30
						calculationTolerance + "\t" + // 31
						amountInternalizer + "\t" + // 32
						punishmentProb + "\t" + // 33
						riskTolerance + "\t" + // 34
						punishmentDistribution + "\t" + // 35
						amountStrategic // 36
				);
				secPrinter.newLine();
				secPrinter.flush();
				index++;
			}
		}

		secPrinter.close();

		for (int i = 0; i < NUMFILES; i++) {
			String name1 = new String("./" + filename + "-" + i);
			String zipName = name1 + ".gz";
			Utils.gzipFile(name1, zipName);
			File f = new File(zipName);
			f.delete();
		}

		for (int i = 0; i < NUMFILES; i++) {
			String name1 = new String("./" + filename + "-" + i);
			File f = new File(name1);
			f.delete();
		}
		Utils.gzipFile(outfilename, outZipFilename);
		File f = new File(outfilename);
		f.delete();
	}

	public static void AnalyzeInternalizationP2PFiles(String filename,
			int NUMFILES, int network_type, int educatorsAmount,
			double educatorsSalience, double punishmentDamage,
			int calculationTolerance, int amountInternalizer,
			double riskTolerance, int experiment, int topological_experiment)
			throws Exception {
		Vector<FileReader> freaders = new Vector();
		Vector<BufferedReader> breaders = new Vector();
		Vector<Boolean> openFiles = new Vector();
		int numOpenFiles = 0;

		String outfilename = new String("");
		outfilename += filename; // + ".final";
		String outZipFilename = new String(filename + ".gz");

		BufferedWriter secPrinter;
		secPrinter = new BufferedWriter(new FileWriter(outfilename));

		for (int i = 0; i < NUMFILES; i++) {
			String name1 = new String(filename + "-" + i);
			// openFiles.add(new String(name1));
			String zipName = name1 + ".gz";
			Unzip(zipName);
			openFiles.add(new Boolean(true));
			String aux_filename = new String(name1);
			// LECTURA
			FileReader istream = new FileReader(aux_filename);
			freaders.add(new FileReader(aux_filename));
			// BufferedReader in = new BufferedReader(istream);
			breaders.add(new BufferedReader(istream));
			numOpenFiles++;
		}
		int index = 0;
		while (numOpenFiles > 0) {
			Vector<Double> succesfullTransactions = new Vector();
			Vector<Double> unsuccesfullTransactions = new Vector(); // 3
			Vector<Double> noTransaction = new Vector(); // 4
			Vector<Double> unfinishedTransactions = new Vector(); // 5
			Vector<Double> services = new Vector(); // 6
			Vector<Double> avgAwaitingTime = new Vector(); // 7
			Vector<Double> avgSalienceN1 = new Vector(); // 8
			Vector<Double> avgSalienceN2 = new Vector(); // 9
			Vector<Double> amountN1Internalizers = new Vector(); // 10
			Vector<Double> amountN2Internalizers = new Vector(); // 11
			Vector<Double> benefitCostCalculationRepeated = new Vector();
			Vector<Double> serviceProviders = new Vector();
			Vector<Double> resp0 = new Vector();
			Vector<Double> resp1 = new Vector();
			Vector<Double> resp2 = new Vector();
			Vector<Double> resp3 = new Vector();
			Vector<Double> strategicPunishmentsDoneN1 = new Vector();
			Vector<Double> normativePunishmentsDoneN1 = new Vector();
			Vector<Double> educationalMessagesSentN1 = new Vector();
			Vector<Double> strategicPunishmentsDoneN2 = new Vector();
			Vector<Double> normativePunishmentsDoneN2 = new Vector();
			Vector<Double> educationalMessagesSentN2 = new Vector();
			Vector<Double> total_succesfullTransactions = new Vector();
			Vector<Double> total_unsuccesfullTransactions = new Vector(); // 3
			Vector<Double> total_noTransaction = new Vector(); // 4

			for (int i = 0; i < openFiles.size(); i++) {
				String line = new String();
				int pointer = -1;
				if (openFiles.elementAt(i)) {
					pointer = new Integer(i);
					if ((line = breaders.elementAt(pointer).readLine()) == null) {
						openFiles.remove(pointer);
						openFiles.add(pointer, new Boolean(false));
						breaders.elementAt(i).close();
						numOpenFiles = numOpenFiles - 1;
					} else {
						Vector<Double> numbersFromLine = new Vector(
								LineToNumber(line));
						// System.out.println("numbersFromLine.size():"+numbersFromLine.size());

						succesfullTransactions.add(new Double(numbersFromLine
								.elementAt(1)));
						unsuccesfullTransactions.add(new Double(numbersFromLine
								.elementAt(2)));
						noTransaction.add(new Double(numbersFromLine
								.elementAt(3)));
						unfinishedTransactions.add(new Double(numbersFromLine
								.elementAt(4)));
						services.add(new Double(numbersFromLine.elementAt(5)));
						avgAwaitingTime.add(new Double(numbersFromLine
								.elementAt(6)));
						avgSalienceN1.add(new Double(numbersFromLine
								.elementAt(7)));
						avgSalienceN2.add(new Double(numbersFromLine
								.elementAt(8)));
						amountN1Internalizers.add(new Double(numbersFromLine
								.elementAt(9)));
						amountN2Internalizers.add(new Double(numbersFromLine
								.elementAt(10)));
						benefitCostCalculationRepeated.add(new Double(
								numbersFromLine.elementAt(11)));
						serviceProviders.add(new Double(numbersFromLine
								.elementAt(12)));
						resp0.add(new Double(numbersFromLine.elementAt(7)));
						resp1.add(new Double(numbersFromLine.elementAt(14)));
						resp2.add(new Double(numbersFromLine.elementAt(15)));
						resp3.add(new Double(numbersFromLine.elementAt(16)));
						strategicPunishmentsDoneN1.add(new Double(
								numbersFromLine.elementAt(17)));
						normativePunishmentsDoneN1.add(new Double(
								numbersFromLine.elementAt(18)));
						educationalMessagesSentN1.add(new Double(
								numbersFromLine.elementAt(19)));
						strategicPunishmentsDoneN2.add(new Double(
								numbersFromLine.elementAt(20)));
						normativePunishmentsDoneN2.add(new Double(
								numbersFromLine.elementAt(21)));
						educationalMessagesSentN2.add(new Double(
								numbersFromLine.elementAt(22)));
						total_succesfullTransactions.add(new Double(
								numbersFromLine.elementAt(23)));
						total_unsuccesfullTransactions.add(new Double(
								numbersFromLine.elementAt(24)));
						total_noTransaction.add(new Double(numbersFromLine
								.elementAt(25)));
					}
				}
			}
			if (numOpenFiles > 0) {
				float mean_succesfullTransactions = new Float(
						Mean(succesfullTransactions));
				float mean_unsuccesfullTransactions = new Float(
						Mean(unsuccesfullTransactions)); // 3
				float mean_noTransaction = new Float(Mean(noTransaction)); // 4
				float mean_unfinishedTransactions = new Float(
						Mean(unfinishedTransactions)); // 5
				float mean_services = new Float(Mean(services)); // 6
				float mean_avgAwaitingTime = new Float(Mean(avgAwaitingTime)); // 7
				float mean_avgSalienceN1 = new Float(Mean(avgSalienceN1)); // 8
				float mean_avgSalienceN2 = new Float(Mean(avgSalienceN2)); // 9
				float mean_amountN1Internalizers = new Float(
						Mean(amountN1Internalizers)); // 10
				float mean_amountN2Internalizers = new Float(
						Mean(amountN2Internalizers)); // 11
				float mean_benefitCostCalculationRepeated = new Float(
						Mean(benefitCostCalculationRepeated));
				float mean_serviceProviders = new Float(Mean(serviceProviders));
				float mean_resp0 = new Float(Mean(resp0));
				float mean_resp1 = new Float(Mean(resp1));
				float mean_resp2 = new Float(Mean(resp2));
				float mean_resp3 = new Float(Mean(resp3));
				float mean_strategicPunishmentsDoneN1 = new Float(
						Mean(strategicPunishmentsDoneN1));
				float mean_normativePunishmentsDoneN1 = new Float(
						Mean(normativePunishmentsDoneN1));
				float mean_educationalMessagesSentN1 = new Float(
						Mean(educationalMessagesSentN1));
				float mean_strategicPunishmentsDoneN2 = new Float(
						Mean(strategicPunishmentsDoneN2));
				float mean_normativePunishmentsDoneN2 = new Float(
						Mean(normativePunishmentsDoneN2));
				float mean_educationalMessagesSentN2 = new Float(
						Mean(educationalMessagesSentN2));
				float mean_total_succesfullTransactions = new Float(
						Mean(total_succesfullTransactions));
				float mean_total_unsuccesfullTransactions = new Float(
						Mean(total_unsuccesfullTransactions));
				float mean_total_noTransaction = new Float(
						Mean(total_noTransaction));
				float std_succesfullTransactions = new Float(
						STD(succesfullTransactions));
				float std_unsuccesfullTransactions = new Float(
						STD(unsuccesfullTransactions)); // 3
				float std_noTransaction = new Float(STD(noTransaction)); // 4
				float std_unfinishedTransactions = new Float(
						STD(unfinishedTransactions)); // 5
				float std_services = new Float(STD(services)); // 6
				float std_avgAwaitingTime = new Float(STD(avgAwaitingTime)); // 7
				float std_avgSalienceN1 = new Float(STD(avgSalienceN1)); // 8
				float std_avgSalienceN2 = new Float(STD(avgSalienceN2)); // 9
				float std_amountN1Internalizers = new Float(
						STD(amountN1Internalizers)); // 10
				float std_amountN2Internalizers = new Float(
						STD(amountN2Internalizers)); // 11
				float std_benefitCostCalculationRepeated = new Float(
						STD(benefitCostCalculationRepeated));
				float std_serviceProviders = new Float(STD(serviceProviders));
				float std_resp0 = new Float(STD(resp0));
				float std_resp1 = new Float(STD(resp1));
				float std_resp2 = new Float(STD(resp2));
				float std_resp3 = new Float(STD(resp3));
				float std_strategicPunishmentsDoneN1 = new Float(
						STD(strategicPunishmentsDoneN1));
				float std_normativePunishmentsDoneN1 = new Float(
						STD(normativePunishmentsDoneN1));
				float std_educationalMessagesSentN1 = new Float(
						STD(educationalMessagesSentN1));
				float std_strategicPunishmentsDoneN2 = new Float(
						STD(strategicPunishmentsDoneN2));
				float std_normativePunishmentsDoneN2 = new Float(
						STD(normativePunishmentsDoneN2));
				float std_educationalMessagesSentN2 = new Float(
						STD(educationalMessagesSentN2));

				// System.out.println(index + "\t"+
				// auxMean + "\t"+
				// auxSTD + "\t"+
				// elements);
				secPrinter.write(index + "\t" + // 1
						mean_succesfullTransactions + "\t" + // 2
						mean_unsuccesfullTransactions + "\t" + // 3
						mean_noTransaction + "\t" + // 4
						mean_unfinishedTransactions + "\t" + // 5
						mean_services + "\t" + // 6
						mean_avgAwaitingTime + "\t" + // 7
						mean_avgSalienceN1 + "\t" + // 8
						mean_avgSalienceN2 + "\t" + // 9
						mean_amountN1Internalizers + "\t" + // 10
						mean_amountN2Internalizers + "\t" + // 11
						mean_benefitCostCalculationRepeated + "\t" + // 12
						mean_serviceProviders + "\t" + // 13
						mean_resp0 + "\t" + // 14
						mean_resp1 + "\t" + // 15
						mean_resp2 + "\t" + // 16
						mean_resp3 + "\t" + // 17
						mean_strategicPunishmentsDoneN1 + "\t" + // 18
						mean_normativePunishmentsDoneN1 + "\t" + // 19
						mean_educationalMessagesSentN1 + "\t" + // 20
						mean_strategicPunishmentsDoneN2 + "\t" + // 21
						mean_normativePunishmentsDoneN2 + "\t" + // 22
						mean_educationalMessagesSentN2 + "\t" + // 23
						std_succesfullTransactions + "\t" + // 24
						std_unsuccesfullTransactions + "\t" + // 25
						std_noTransaction + "\t" + // 26
						std_unfinishedTransactions + "\t" + // 27
						std_services + "\t" + // 28
						std_avgAwaitingTime + "\t" + // 29
						std_avgSalienceN1 + "\t" + // 30
						std_avgSalienceN2 + "\t" + // 31
						std_amountN1Internalizers + "\t" + // 32
						std_amountN2Internalizers + "\t" + // 33
						std_benefitCostCalculationRepeated + "\t" + // 34
						std_serviceProviders + "\t" + // 35
						std_resp0 + "\t" + // 36
						std_resp1 + "\t" + // 37
						std_resp2 + "\t" + // 38
						std_resp3 + "\t" + // 39
						std_strategicPunishmentsDoneN1 + "\t" + // 40
						std_normativePunishmentsDoneN1 + "\t" + // 41
						std_educationalMessagesSentN1 + "\t" + // 42
						std_strategicPunishmentsDoneN2 + "\t" + // 43
						std_normativePunishmentsDoneN2 + "\t" + // 44
						std_educationalMessagesSentN2 + "\t" + // 45
						network_type + "\t" + // 46
						educatorsAmount + "\t" + // 47
						educatorsSalience + "\t" + // 48
						punishmentDamage + "\t" + // 49
						calculationTolerance + "\t" + // 50
						amountInternalizer + "\t" + // 51
						riskTolerance + "\t" + // 52
						experiment + "\t" + // 53
						topological_experiment // 54
				);
				secPrinter.newLine();
				secPrinter.flush();
				index++;
			}
		}

		secPrinter.close();

		for (int i = 0; i < NUMFILES; i++) {
			String name1 = new String("./" + filename + "-" + i);
			File f = new File(name1);
			f.delete();
		}
		Utils.gzipFile(outfilename, outZipFilename);
		File f = new File(outfilename);
		f.delete();
	}

	public static void AnalyzeConventionEmergence(String name, int NUMFILES,
			int amountAgents2, int numActions, int historyWindow2,
			int neighbourSize2, int network_type, int numPlayers,
			int rewardRule, int agentType, int game) throws Exception {

		Vector<FileReader> freaders = new Vector();
		Vector<BufferedReader> breaders = new Vector();
		Vector<Boolean> openFiles = new Vector();
		int numOpenFiles = 0;

		String outfilename = new String("Dyn-");
		outfilename += name + ".final";
		String outZipFilename = new String("Dyn-" + name + ".gz");

		BufferedWriter secPrinter;
		secPrinter = new BufferedWriter(new FileWriter(outfilename));

		for (int i = 0; i < NUMFILES; i++) {
			String name1 = new String("Ev-" + name + "-" + i);
			String zipName = name1 + ".gz";
			Utils.Unzip(zipName);
			openFiles.add(new Boolean(true));
			String filename = new String(name1);

			// LECTURA
			FileReader istream = new FileReader(filename);
			freaders.add(new FileReader(filename));

			// BufferedReader in = new BufferedReader(istream);
			breaders.add(new BufferedReader(istream));
			numOpenFiles++;
		}
		int index = 0;
		while (numOpenFiles > 0) {
			Vector<Double> convergence = new Vector();
			Vector<Double> payoffAct00 = new Vector();
			Vector<Double> payoffAct01 = new Vector();
			Vector<Double> payoffAct10 = new Vector();
			Vector<Double> payoffAct11 = new Vector();
			Vector<Double> avgPayoff = new Vector();
			Vector<Double> agentsPreferringNR = new Vector();
			Vector<Double> agentsPreferringR = new Vector();
			Vector<Double> agentsPreferringNR2 = new Vector();
			Vector<Double> agentsPreferringR2 = new Vector();
			Vector<Double> qValue0 = new Vector();
			Vector<Double> qValue1 = new Vector();

			for (int i = 0; i < openFiles.size(); i++) {
				String line = new String();
				int pointer = -1;
				if (openFiles.elementAt(i)) {
					pointer = new Integer(i);
					if ((line = breaders.elementAt(pointer).readLine()) == null) {
						openFiles.remove(pointer);
						openFiles.add(pointer, new Boolean(false));
						breaders.elementAt(i).close();
						numOpenFiles = numOpenFiles - 1;
					} else {
						Vector<Double> numbersFromLine = new Vector(
								LineToNumber(line));
						convergence
								.add(new Double(numbersFromLine.elementAt(3)));
						payoffAct00
								.add(new Double(numbersFromLine.elementAt(4)));
						payoffAct01
								.add(new Double(numbersFromLine.elementAt(5)));
						payoffAct10
								.add(new Double(numbersFromLine.elementAt(6)));
						payoffAct11
								.add(new Double(numbersFromLine.elementAt(7)));
						avgPayoff.add(new Double(numbersFromLine.elementAt(8)));
						agentsPreferringNR.add(new Double(numbersFromLine
								.elementAt(9)));
						agentsPreferringR.add(new Double(numbersFromLine
								.elementAt(10)));
						agentsPreferringNR2.add(new Double(numbersFromLine
								.elementAt(11)));
						agentsPreferringR2.add(new Double(numbersFromLine
								.elementAt(12)));
						qValue0.add(new Double(numbersFromLine.elementAt(7)));
						qValue1.add(new Double(numbersFromLine.elementAt(14)));
					}
				}
				int aux = openFiles.size();
				while (aux <= NUMFILES) {
					convergence.add(1.0);
					aux++;
				}
			}
			if (numOpenFiles > 0) {
				float convergenceMean = new Float(Mean(convergence));
				float payoffAct00Mean = new Float(Mean(payoffAct00));
				float payoffAct00STD = new Float(STD(payoffAct00));
				float payoffAct01Mean = new Float(Mean(payoffAct01));
				float payoffAct01STD = new Float(STD(payoffAct01));
				float payoffAct10Mean = new Float(Mean(payoffAct10));
				float payoffAct10STD = new Float(STD(payoffAct10));
				float payoffAct11Mean = new Float(Mean(payoffAct11));
				float payoffAct11STD = new Float(STD(payoffAct11));
				float avgPayoffMean = new Float(Mean(avgPayoff));
				float avgPayoffSTD = new Float(STD(avgPayoff));
				float agentsPreferringNRMean = new Float(
						Mean(agentsPreferringNR));
				float agentsPreferringNRSTD = new Float(STD(agentsPreferringNR));
				float agentsPreferringRMean = new Float(Mean(agentsPreferringR));
				float agentsPreferringRSTD = new Float(STD(agentsPreferringR));
				float agentsPreferringNR2Mean = new Float(
						Mean(agentsPreferringNR2));
				float agentsPreferringNR2STD = new Float(
						STD(agentsPreferringNR2));
				float agentsPreferringR2Mean = new Float(
						Mean(agentsPreferringR2));
				float agentsPreferringR2STD = new Float(STD(agentsPreferringR2));
				float qValue0Mean = new Float(Mean(qValue0));
				float qValue1Mean = new Float(Mean(qValue1));

				// System.out.println(index + "\t"+
				// auxMean + "\t"+
				// auxSTD + "\t"+
				// elements);
				secPrinter.write(index + "\t" + // 1
						convergenceMean + "\t" + // 2
						payoffAct00Mean + "\t" + // 3
						payoffAct00STD + "\t" + // 4
						payoffAct01Mean + "\t" + // 5
						payoffAct01STD + "\t" + // 6
						payoffAct10Mean + "\t" + // 7
						payoffAct10STD + "\t" + // 8
						payoffAct11Mean + "\t" + // 9
						payoffAct11STD + "\t" + // 10
						avgPayoffMean + "\t" + // 11
						avgPayoffSTD + "\t" + // 12
						agentsPreferringNRMean + "\t" + // 13
						agentsPreferringNRSTD + "\t" + // 14
						agentsPreferringRMean + "\t" + // 15
						agentsPreferringRSTD + "\t" + // 16
						agentsPreferringNR2Mean + "\t" + // 17
						agentsPreferringNR2STD + "\t" + // 18
						agentsPreferringR2Mean + "\t" + // 19
						agentsPreferringR2STD + "\t" + // 20
						qValue0Mean + "\t" + // 21
						qValue1Mean + "\t" + // 22
						amountAgents2 + "\t" + // 23
						numActions + "\t" + // 24
						historyWindow2 + "\t" + // 25
						neighbourSize2 + "\t" + // 26
						network_type + "\t" + // 27
						numPlayers + "\t" + // 28
						rewardRule + "\t" + // 29
						agentType + "\t" + // 30
						game // 31
				);
				secPrinter.newLine();
				secPrinter.flush();
				index++;
			}
		}

		secPrinter.close();

		for (int i = 0; i < NUMFILES; i++) {
			String name1 = new String("./Ev-" + name + "-" + i);
			String zipName = name1 + ".gz";
			Utils.gzipFile(name1, zipName);
		}

		for (int i = 0; i < NUMFILES; i++) {
			String name1 = new String("./Ev-" + name + "-" + i);
			File f = new File(name1);
			f.delete();
		}

		Utils.gzipFile(outfilename, outZipFilename);
		File f = new File(outfilename);
		f.delete();

	}

	public static void AnalyzeConventionPunEmp(String name) throws Exception {
		String outfilename = new String("A-");
		outfilename += name + ".final";

		String outfilename2 = new String("3d-");
		outfilename2 += name + ".final";

		String outfilename3 = new String("3d-");
		outfilename3 += name + "-C.final";

		String outfilename4 = new String("3d-");
		outfilename4 += name + "-P.final";

		String outfilename5 = new String("3d-");
		outfilename5 += name + "-PunSent.final";

		BufferedWriter secPrinter, secPrinter2, secPrinter3, secPrinter4, secPrinter5;
		secPrinter = new BufferedWriter(new FileWriter(outfilename));
		secPrinter2 = new BufferedWriter(new FileWriter(outfilename2));
		secPrinter3 = new BufferedWriter(new FileWriter(outfilename3));
		secPrinter4 = new BufferedWriter(new FileWriter(outfilename4));
		secPrinter5 = new BufferedWriter(new FileWriter(outfilename5));

		String filename = new String(name + ".data");
		// LECTURA
		FileReader freader = new FileReader(filename);
		// BufferedReader in = new BufferedReader(istream);
		BufferedReader breader = new BufferedReader(freader);

		String line = new String();
		int index = 0;

		int fileAggregator = 0;
		if (name == "message") {
			fileAggregator = -5;
		}

		Vector<Integer> messagesRequests = new Vector();
		for (int i = 0; i < 21; i++) {
			messagesRequests.add(0);
		}

		Vector<Integer> contributionDistributions = new Vector();
		for (int i = 0; i < 21; i++) {
			contributionDistributions.add(0);
		}

		Vector<Vector<Double>> punishmentDistributions = new Vector();
		for (int i = 0; i < 21; i++) {
			Vector<Double> auxPunDist = new Vector();
			punishmentDistributions.add(auxPunDist);
		}

		Vector<Integer> punishmentSent = new Vector();
		for (int i = 0; i < 31; i++) {
			punishmentSent.add(0);
		}

		double totalMessages = 0;

		for (int block = 1; block <= 3; block++) {
			for (int rounds = 1; rounds <= 10; rounds++) {
				double acumContribution = 0;
				double acumProfit = 0;
				double punishers = 0;
				double sanctioners = 0;
				double punisher_and_sanctioners = 0;
				double coop_punishers = 0;
				double coop_sanctioners = 0;
				double coop_punisher_and_sanctioners = 0;

				double defectors = 0; // defector is an agent whose contribution
										// is below the group contribution
				double nonPunishedDefectors = 0;
				double punisherDefectors = 0;
				double acumDefPunReceived = 0;

				double cooperators = 0;
				double acumCoopContr = 0;

				double punishmentPointsSent = 0;
				double acumAdvices = 0;

				Vector<Double> advices = new Vector();

				for (int subjects = 1; subjects <= 48; subjects++) {
					line = breader.readLine();

					Vector<Double> numbersFromLine = new Vector(
							LineToNumber(line));
					double agentContribution = new Double(
							numbersFromLine.elementAt(7));
					acumContribution += new Double(numbersFromLine.elementAt(7));

					int auxC = (int) agentContribution;
					int oldCont = new Integer(
							contributionDistributions.elementAt(auxC));
					contributionDistributions.remove(auxC);
					contributionDistributions.add(auxC, (oldCont + 1));

					double groupContribution = new Double(
							numbersFromLine.elementAt(8));
					double averageGroupContribution = new Double(
							groupContribution / 4.0);

					if (agentContribution < averageGroupContribution) {
						defectors++;
					} else {
						cooperators++;
						acumCoopContr += agentContribution;
					}

					acumProfit += new Double(numbersFromLine.elementAt(5));

					double punReceived = 0;

					if (block == 2) {
						punReceived = new Double(numbersFromLine.elementAt(13));
						if (name == "sanc" || name == "pun") {
							punishmentDistributions.elementAt(auxC).add(
									punReceived);

							double pos = numbersFromLine.elementAt(12);
							int aux = (int) pos;
							int oldPunSent = new Integer(
									punishmentSent.elementAt(aux));
							punishmentSent.remove(aux);
							punishmentSent.add(aux, (oldPunSent + 1));
						}
					}

					// System.out.println(numbersFromLine.toString());
					if (block == 2) {
						if (numbersFromLine.elementAt(12) != 0
								&& (name == "sanc" || name == "pun")) {
							double indPunSen = 0;
							double indPunActs = 0;
							double avgPunSent = 0;
							if (numbersFromLine.elementAt(9) != 0) {
								indPunSen += new Double(
										numbersFromLine.elementAt(9));
								indPunActs++;
							}
							if (numbersFromLine.elementAt(10) != 0) {
								indPunSen += new Double(
										numbersFromLine.elementAt(10));
								indPunActs++;
							}
							if (numbersFromLine.elementAt(11) != 0) {
								indPunSen += new Double(
										numbersFromLine.elementAt(11));
								indPunActs++;
							}
							if (indPunActs != 0) {
								avgPunSent = new Double(indPunSen / indPunActs);
							}

							punishmentPointsSent += new Double(avgPunSent);
							punishers++;
							coop_punishers += numbersFromLine.elementAt(7);

							if (agentContribution < averageGroupContribution) {
								acumDefPunReceived += new Double(
										numbersFromLine.elementAt(13));
								if (numbersFromLine.elementAt(13) == 0) {
									nonPunishedDefectors++;
								}
								if (numbersFromLine.elementAt(12) > 0) {
									punisherDefectors++;
								}
							}

							//
						}
						if (name == "message" || name == "sanc") {

							if (name == "sanc"
									&& numbersFromLine.elementAt(12) > 0
									&& (numbersFromLine.elementAt(14) != -1
											|| numbersFromLine.elementAt(15) != -1
											|| numbersFromLine.elementAt(16) != -1 || numbersFromLine
											.elementAt(17) != -1)) {
								punisher_and_sanctioners++;
								coop_punisher_and_sanctioners += numbersFromLine
										.elementAt(7);
							}
							if (name == "sanc"
									&& numbersFromLine.elementAt(12) == 0
									&& (numbersFromLine.elementAt(14) != -1
											|| numbersFromLine.elementAt(15) != -1
											|| numbersFromLine.elementAt(16) != -1 || numbersFromLine
											.elementAt(17) != -1)

							) {
								sanctioners++;
								coop_sanctioners += numbersFromLine
										.elementAt(7);
							}

							if (name == "message"
									&& (numbersFromLine.elementAt(9) != -1
											|| numbersFromLine.elementAt(10) != -1
											|| numbersFromLine.elementAt(11) != -1 || numbersFromLine
											.elementAt(12) != -1)) {
								sanctioners++;
								coop_sanctioners += numbersFromLine
										.elementAt(7);
							}

							double noAdvicesSent = 0;
							double actualAdvicesSent = 0;
							// System.out.println(numbersFromLine.toString());
							if (numbersFromLine.elementAt(14 + fileAggregator) != -1) {
								double pos = numbersFromLine
										.elementAt(14 + fileAggregator);
								int aux = (int) pos;
								int oldRequests = new Integer(
										messagesRequests.elementAt(aux));
								messagesRequests.remove(aux);
								messagesRequests.add(aux, (oldRequests + 1));

								actualAdvicesSent += numbersFromLine
										.elementAt(14 + fileAggregator);
								advices.add(new Double(numbersFromLine
										.elementAt(14 + fileAggregator)));
								noAdvicesSent++;

							}
							if (numbersFromLine.elementAt(15 + fileAggregator) != -1) {
								double pos = numbersFromLine
										.elementAt(15 + fileAggregator);
								int aux = (int) pos;
								int oldRequests = new Integer(
										messagesRequests.elementAt(aux));
								messagesRequests.remove(aux);
								messagesRequests.add(aux, (oldRequests + 1));

								actualAdvicesSent += numbersFromLine
										.elementAt(15 + fileAggregator);
								advices.add(new Double(numbersFromLine
										.elementAt(15 + fileAggregator)));
								noAdvicesSent++;
							}
							if (numbersFromLine.elementAt(16 + fileAggregator) != -1) {
								double pos = numbersFromLine
										.elementAt(16 + fileAggregator);
								int aux = (int) pos;
								int oldRequests = new Integer(
										messagesRequests.elementAt(aux));
								messagesRequests.remove(aux);
								messagesRequests.add(aux, (oldRequests + 1));

								actualAdvicesSent += numbersFromLine
										.elementAt(16 + fileAggregator);
								advices.add(new Double(numbersFromLine
										.elementAt(16 + fileAggregator)));
								noAdvicesSent++;
							}
							if (numbersFromLine.elementAt(17 + fileAggregator) != -1) {
								double pos = numbersFromLine
										.elementAt(17 + fileAggregator);
								int aux = (int) pos;
								int oldRequests = new Integer(
										messagesRequests.elementAt(aux));
								messagesRequests.remove(aux);
								messagesRequests.add(aux, (oldRequests + 1));

								actualAdvicesSent += numbersFromLine
										.elementAt(17 + fileAggregator);
								advices.add(new Double(numbersFromLine
										.elementAt(17 + fileAggregator)));
								noAdvicesSent++;
							}
							double auxAcumAdvices = new Double(
									actualAdvicesSent / noAdvicesSent);
							totalMessages += noAdvicesSent;
							if (auxAcumAdvices > 0) {
								acumAdvices += new Double(auxAcumAdvices);
							}
						}
					}
				}
				int totalRound = (((block - 1) * 10) + rounds);
				float cooperationMean = new Float(acumContribution / 48.0);
				float cooperatorsAVGContribution = new Float(acumCoopContr
						/ cooperators);
				float profitMean = new Float(acumProfit / 48.0);
				float punishmentPointsSentMean = 0;
				if (block == 2) {
					punishmentPointsSentMean = new Float(punishmentPointsSent
							/ punishers);
				}

				secPrinter
						.write(totalRound
								+ "\t"
								+ // 1
								cooperationMean
								+ "\t"
								+ // 2
								punishmentPointsSentMean
								+ "\t"
								+ // 3
								punishers
								+ "\t"
								+ // 4
								sanctioners
								+ "\t"
								+ // 5
								punisher_and_sanctioners
								+ "\t"
								+ // 6
								new Double(
										acumAdvices
												/ (sanctioners + punisher_and_sanctioners))
								+ "\t"
								+ // 7
								new Double(coop_punishers / punishers)
								+ "\t"
								+ // 8
								new Double(coop_sanctioners / sanctioners)
								+ "\t"
								+ // 9
								new Double(coop_punisher_and_sanctioners
										/ punisher_and_sanctioners)
								+ "\t"
								+ // 10
								Mean(advices)
								+ "\t"
								+ // 11
								STD(advices)
								+ "\t"
								+ // 12
								profitMean
								+ "\t"
								+ // 13
								new Double(
										(coop_punishers + coop_sanctioners + coop_punisher_and_sanctioners)
												/ (punishers + sanctioners + punisher_and_sanctioners))
								+ "\t" + // 14
								defectors + "\t" + // 15
								(acumDefPunReceived / defectors) + "\t" + // 16
								nonPunishedDefectors + "\t" + // 17
								punisherDefectors + "\t" + // 18
								cooperatorsAVGContribution + "\t" + // 19
								advices.size() // 20

						);

				// System.out.println(messagesRequests.toString());

				secPrinter.newLine();
				secPrinter.flush();

				if (block == 2) {
					double acumMessages = 0;
					for (int i = 0; i < messagesRequests.size(); i++) {
						acumMessages += new Double(
								messagesRequests.elementAt(i));
					}
					for (int i = 0; i < messagesRequests.size(); i++) {
						int step = new Integer(((block - 1) * 10) + rounds);
						secPrinter2
								.write(step
										+ "\t"
										+ i
										+ "\t"
										+ (messagesRequests.elementAt(i) / acumMessages));
						secPrinter2.newLine();
						secPrinter2.flush();
					}
					secPrinter2.newLine();
					secPrinter2.flush();
					messagesRequests.clear();
					for (int i = 0; i < 21; i++) {
						messagesRequests.add(0);
					}
				}

				double acumCont = 0;
				for (int i = 0; i < contributionDistributions.size(); i++) {
					acumCont += new Double(
							contributionDistributions.elementAt(i));
				}
				for (int i = 0; i < contributionDistributions.size(); i++) {
					int step = new Integer(((block - 1) * 10) + rounds);
					secPrinter3
							.write(step
									+ "\t"
									+ i
									+ "\t"
									+ (contributionDistributions.elementAt(i) / acumCont));
					secPrinter3.newLine();
					secPrinter3.flush();
				}
				secPrinter3.newLine();
				secPrinter3.flush();
				contributionDistributions.clear();
				for (int i = 0; i < 21; i++) {
					contributionDistributions.add(0);
				}

				if (name == "sanc" || name == "pun") {
					for (int i = 0; i < punishmentDistributions.size(); i++) {
						int step = new Integer(((block - 1) * 10) + rounds);
						secPrinter4.write(step + "\t" + i + "\t"
								+ Mean(punishmentDistributions.elementAt(i)));
						secPrinter4.newLine();
						secPrinter4.flush();
						punishmentDistributions.elementAt(i).clear();
					}
					secPrinter4.newLine();
					secPrinter4.flush();

					for (int i = 0; i < punishmentSent.size(); i++) {
						int step = new Integer(((block - 1) * 10) + rounds);
						secPrinter5.write(step + "\t" + i + "\t"
								+ punishmentSent.elementAt(i));
						secPrinter5.newLine();
						secPrinter5.flush();
					}
					secPrinter5.newLine();
					secPrinter5.flush();
					punishmentSent.clear();
					for (int i = 0; i < 31; i++) {
						punishmentSent.add(0);
					}

				}

			}
		}
		breader.close();

		System.out.println("totalMessages:" + totalMessages);

		secPrinter.close();
		secPrinter2.close();
		secPrinter3.close();
		secPrinter4.close();
		secPrinter5.close();
	}

	public static void AnalyzePunEmpPerGroup(String name) throws Exception {
		String outfilename = new String("A-");
		outfilename += name + ".final";

		String outfilename2 = new String("ContImpPun-");
		outfilename2 += name + ".final";

		String outfilename3 = new String("3D-");
		outfilename3 += name + ".final";

		String outfilename4 = new String("ContImpMes-");
		outfilename4 += name + ".final";

		BufferedWriter secPrinter, secPrinter2, secPrinter3, secPrinter4;
		secPrinter = new BufferedWriter(new FileWriter(outfilename));
		secPrinter2 = new BufferedWriter(new FileWriter(outfilename2));
		secPrinter3 = new BufferedWriter(new FileWriter(outfilename3));
		secPrinter4 = new BufferedWriter(new FileWriter(outfilename4));

		String filename = new String(name + ".data");
		// LECTURA
		FileReader freader = new FileReader(filename);
		// BufferedReader in = new BufferedReader(istream);
		BufferedReader breader = new BufferedReader(freader);

		String line = new String();
		int index = 0;

		Vector<Double> avgPunGroupDev2014Neg = new Vector();
		Vector<Double> avgPunGroupDev148Neg = new Vector();
		Vector<Double> avgPunGroupDev82Neg = new Vector();
		Vector<Double> avgPunGroupDev22Neg = new Vector();
		Vector<Double> avgPunGroupDev28Pos = new Vector();
		Vector<Double> avgPunGroupDev814Pos = new Vector();
		Vector<Double> avgPunGroupDev1420Pos = new Vector();
		double groupDev2014Neg = 0;
		double groupDev148Neg = 0;
		double groupDev82Neg = 0;
		double groupDev22Neg = 0;
		double groupDev28Pos = 0;
		double groupDev814Pos = 0;
		double groupDev1420Pos = 0;

		Vector<Double> avgPunPunishersDev2014Neg = new Vector();
		Vector<Double> avgPunPunishersDev148Neg = new Vector();
		Vector<Double> avgPunPunishersDev82Neg = new Vector();
		Vector<Double> avgPunPunishersDev22Neg = new Vector();
		Vector<Double> avgPunPunishersDev28Pos = new Vector();
		Vector<Double> avgPunPunishersDev814Pos = new Vector();
		Vector<Double> avgPunPunishersDev1420Pos = new Vector();
		double punisherDev2014Neg = 0;
		double punisherDev148Neg = 0;
		double punisherDev82Neg = 0;
		double punisherDev22Neg = 0;
		double punisherDev28Pos = 0;
		double punisherDev814Pos = 0;
		double punisherDev1420Pos = 0;

		Vector<Double> contImprovements02 = new Vector();
		Vector<Double> contImprovements24 = new Vector();
		Vector<Double> contImprovements46 = new Vector();
		Vector<Double> contImprovements68 = new Vector();
		Vector<Double> contImprovements810 = new Vector();

		Vector<Double> contImprovementsMes05 = new Vector();
		Vector<Double> contImprovementsMes510 = new Vector();
		Vector<Double> contImprovementsMes1015 = new Vector();
		Vector<Double> contImprovementsMes1520 = new Vector();

		for (int group = 1; group <= 1; group++) {
			for (int block = 1; block <= 3; block++) {
				Vector<Double> oldContributions = new Vector();
				Vector<Double> oldPunishments = new Vector();
				Vector<Double> oldMessages = new Vector();

				for (int rounds = 1; rounds <= 10; rounds++) {

					Vector<Double> t_avgPunGroupDev2014Neg = new Vector();
					Vector<Double> t_avgPunGroupDev148Neg = new Vector();
					Vector<Double> t_avgPunGroupDev82Neg = new Vector();
					Vector<Double> t_avgPunGroupDev22Neg = new Vector();
					Vector<Double> t_avgPunGroupDev28Pos = new Vector();
					Vector<Double> t_avgPunGroupDev814Pos = new Vector();
					Vector<Double> t_avgPunGroupDev1420Pos = new Vector();
					double t_groupDev2014Neg = 0;
					double t_groupDev148Neg = 0;
					double t_groupDev82Neg = 0;
					double t_groupDev22Neg = 0;
					double t_groupDev28Pos = 0;
					double t_groupDev814Pos = 0;
					double t_groupDev1420Pos = 0;

					Vector<Double> t_avgPunPunishersDev2014Neg = new Vector();
					Vector<Double> t_avgPunPunishersDev148Neg = new Vector();
					Vector<Double> t_avgPunPunishersDev82Neg = new Vector();
					Vector<Double> t_avgPunPunishersDev22Neg = new Vector();
					Vector<Double> t_avgPunPunishersDev28Pos = new Vector();
					Vector<Double> t_avgPunPunishersDev814Pos = new Vector();
					Vector<Double> t_avgPunPunishersDev1420Pos = new Vector();
					double t_punisherDev2014Neg = 0;
					double t_punisherDev148Neg = 0;
					double t_punisherDev82Neg = 0;
					double t_punisherDev22Neg = 0;
					double t_punisherDev28Pos = 0;
					double t_punisherDev814Pos = 0;
					double t_punisherDev1420Pos = 0;

					double groupCont = 0;
					Vector<Vector<Double>> punishmentDistributions = new Vector();
					Vector<Vector<Double>> messageDistributions = new Vector();
					Vector<Double> distanceFromGroupCont = new Vector();
					Vector<Double> playersContributions = new Vector();

					for (int i = 0; i < 4; i++) {
						Vector<Double> auxPunDist = new Vector();
						punishmentDistributions.add(auxPunDist);
						messageDistributions.add(new Vector(auxPunDist));
					}

					for (int player = 1; player <= 4; player++) {
						line = breader.readLine();
						Vector<Double> numbersFromLine = new Vector(
								LineToNumber(line));

						groupCont = new Double(
								(numbersFromLine.elementAt(8) * 1.0) / 4.0);

						double indCont = new Double(
								numbersFromLine.elementAt(7));
						playersContributions.add(indCont);
						double dif = new Double(indCont - groupCont);

						distanceFromGroupCont.add(new Double(dif));

						if (block == 2) {
							if (player == 1) {
								punishmentDistributions.elementAt(1)
										.add(new Double(numbersFromLine
												.elementAt(9)));
								punishmentDistributions.elementAt(2).add(
										new Double(numbersFromLine
												.elementAt(10)));
								punishmentDistributions.elementAt(3).add(
										new Double(numbersFromLine
												.elementAt(11)));

								if (name == "g-Sanc") {
									// System.out.println(numbersFromLine.toString());
									messageDistributions.elementAt(1).add(
											new Double(numbersFromLine
													.elementAt(15))); // 14 15
																		// 16 17
									messageDistributions.elementAt(2).add(
											new Double(numbersFromLine
													.elementAt(16)));
									messageDistributions.elementAt(3).add(
											new Double(numbersFromLine
													.elementAt(17)));
								}
							}
							if (player == 2) {
								punishmentDistributions.elementAt(0)
										.add(new Double(numbersFromLine
												.elementAt(9)));
								punishmentDistributions.elementAt(2).add(
										new Double(numbersFromLine
												.elementAt(10)));
								punishmentDistributions.elementAt(3).add(
										new Double(numbersFromLine
												.elementAt(11)));

								if (name == "g-Sanc") {
									messageDistributions.elementAt(0).add(
											new Double(numbersFromLine
													.elementAt(14))); // 14 15
																		// 16 17
									messageDistributions.elementAt(2).add(
											new Double(numbersFromLine
													.elementAt(16)));
									messageDistributions.elementAt(3).add(
											new Double(numbersFromLine
													.elementAt(17)));
								}
							}
							if (player == 3) {
								punishmentDistributions.elementAt(0)
										.add(new Double(numbersFromLine
												.elementAt(9)));
								punishmentDistributions.elementAt(1).add(
										new Double(numbersFromLine
												.elementAt(10)));
								punishmentDistributions.elementAt(3).add(
										new Double(numbersFromLine
												.elementAt(11)));
								if (name == "g-Sanc") {
									messageDistributions.elementAt(0).add(
											new Double(numbersFromLine
													.elementAt(14))); // 14 15
																		// 16 17
									messageDistributions.elementAt(1).add(
											new Double(numbersFromLine
													.elementAt(15)));
									messageDistributions.elementAt(3).add(
											new Double(numbersFromLine
													.elementAt(17)));
								}
							}
							if (player == 4) {
								punishmentDistributions.elementAt(0)
										.add(new Double(numbersFromLine
												.elementAt(9)));
								punishmentDistributions.elementAt(1).add(
										new Double(numbersFromLine
												.elementAt(10)));
								punishmentDistributions.elementAt(2).add(
										new Double(numbersFromLine
												.elementAt(11)));
								if (name == "g-Sanc") {
									messageDistributions.elementAt(0).add(
											new Double(numbersFromLine
													.elementAt(14))); // 14 15
																		// 16 17
									messageDistributions.elementAt(1).add(
											new Double(numbersFromLine
													.elementAt(15)));
									messageDistributions.elementAt(2).add(
											new Double(numbersFromLine
													.elementAt(16)));
								}
							}
						}
					}
					if (block == 2) {
						System.out.println("Players Contributions: "
								+ playersContributions.toString());
						System.out.println("Player 1 got punished: "
								+ punishmentDistributions.elementAt(0)
										.toString());
						System.out.println("Player 2 got punished: "
								+ punishmentDistributions.elementAt(1)
										.toString());
						System.out.println("Player 3 got punished: "
								+ punishmentDistributions.elementAt(2)
										.toString());
						System.out.println("Player 4 got punished: "
								+ punishmentDistributions.elementAt(3)
										.toString());
						System.out
								.println("--------------------------------------------------------------------");

						if (rounds > 1) {
							for (int player = 0; player < 4; player++) {
								// System.out.println("block:"+ block +
								// "\t rounds:"+ rounds
								// + "\t playersContribution:"
								// +playersContributions.elementAt(player)
								// + "\t oldContribution:"
								// +oldContributions.elementAt(player)
								// );
								// System.out.println("oldPunishments:"+oldPunishments);
								double aux = new Double(
										playersContributions.elementAt(player)
												- oldContributions
														.elementAt(player));
								System.out.println("Player " + player
										+ " improvement:" + aux);
								if (oldPunishments.elementAt(player) >= 0
										&& oldPunishments.elementAt(player) < 2) {
									contImprovements02.add(new Double(aux));
								}
								if (oldPunishments.elementAt(player) >= 2
										&& oldPunishments.elementAt(player) < 4) {
									contImprovements24.add(new Double(aux));
								}
								if (oldPunishments.elementAt(player) >= 4
										&& oldPunishments.elementAt(player) < 6) {
									contImprovements46.add(new Double(aux));
								}
								if (oldPunishments.elementAt(player) >= 6
										&& oldPunishments.elementAt(player) < 8) {
									contImprovements68.add(new Double(aux));
								}
								if (oldPunishments.elementAt(player) >= 8) {
									contImprovements810.add(new Double(aux));
								}

								if (name == "g-Sanc") {
									if (oldMessages.elementAt(player) >= 0
											&& oldMessages.elementAt(player) < 5) {
										contImprovementsMes05.add(new Double(
												aux));
									}
									if (oldMessages.elementAt(player) >= 5
											&& oldMessages.elementAt(player) < 10) {
										contImprovementsMes510.add(new Double(
												aux));
									}
									if (oldMessages.elementAt(player) >= 10
											&& oldMessages.elementAt(player) < 15) {
										contImprovementsMes1015.add(new Double(
												aux));
									}
									if (oldMessages.elementAt(player) >= 15) {
										contImprovementsMes1520.add(new Double(
												aux));
									}
								}

							}
							oldPunishments.clear();
							oldMessages.clear();
						}

						for (int player = 0; player < 4; player++) {

							oldPunishments.add(new Double(
									MeanNoZeros(punishmentDistributions
											.elementAt(player))));
							if (name == "g-Sanc") {
								oldMessages.add(new Double(
										MeanNoZeros(messageDistributions
												.elementAt(player))));
							}

							for (int op = 0; op < 4; op++) {
								if (player != op) {
									double dif = new Double(
											playersContributions
													.elementAt(player)
													- playersContributions
															.elementAt(op));
									// System.out.println("playersContributions:"+playersContributions.toString());
									// System.out.println("punishmentDistributions:"+
									// punishmentDistributions.toString());
									// System.out.println("player:"+player+"\t op:"+op);
									int opPointer = op;
									if (op > player) {
										opPointer = new Integer(op - 1);
									}
									if (dif >= -20 && dif < -14) {
										punisherDev2014Neg++;
										t_punisherDev2014Neg++;
										double aux = new Double(
												punishmentDistributions
														.elementAt(player)
														.elementAt(opPointer));
										if (aux != 0) {
											avgPunPunishersDev2014Neg.add(aux);
											t_avgPunPunishersDev2014Neg
													.add(aux);
										}
									}
									if (dif >= -14 && dif < -8) {
										punisherDev148Neg++;
										t_punisherDev148Neg++;
										double aux = new Double(
												punishmentDistributions
														.elementAt(player)
														.elementAt(opPointer));
										if (aux != 0) {
											avgPunPunishersDev148Neg.add(aux);
											t_avgPunPunishersDev148Neg.add(aux);
										}
									}
									if (dif >= -8 && dif < -2) {
										punisherDev82Neg++;
										t_punisherDev82Neg++;
										double aux = new Double(
												punishmentDistributions
														.elementAt(player)
														.elementAt(opPointer));
										if (aux != 0) {
											avgPunPunishersDev82Neg.add(aux);
											t_avgPunPunishersDev82Neg.add(aux);
										}
									}
									if (dif >= -2 && dif <= 2) {
										punisherDev22Neg++;
										t_punisherDev22Neg++;
										double aux = new Double(
												punishmentDistributions
														.elementAt(player)
														.elementAt(opPointer));
										if (aux != 0) {
											avgPunPunishersDev22Neg.add(aux);
											t_avgPunPunishersDev22Neg.add(aux);
										}
									}
									if (dif > 2 && dif <= 8) {
										punisherDev28Pos++;
										t_punisherDev28Pos++;
										double aux = new Double(
												punishmentDistributions
														.elementAt(player)
														.elementAt(opPointer));
										if (aux != 0) {
											avgPunPunishersDev28Pos.add(aux);
											t_avgPunPunishersDev28Pos.add(aux);
										}
									}
									if (dif > 8 && dif <= 14) {
										punisherDev814Pos++;
										t_punisherDev814Pos++;
										double aux = new Double(
												punishmentDistributions
														.elementAt(player)
														.elementAt(opPointer));
										if (aux != 0) {
											avgPunPunishersDev814Pos.add(aux);
											t_avgPunPunishersDev814Pos.add(aux);
										}
									}
									if (dif > 14 && dif <= 20) {
										punisherDev1420Pos++;
										t_punisherDev1420Pos++;
										double aux = new Double(
												punishmentDistributions
														.elementAt(player)
														.elementAt(opPointer));
										if (aux != 0) {
											avgPunPunishersDev1420Pos.add(aux);
											t_avgPunPunishersDev1420Pos
													.add(aux);
										}
									}
								}
							}
						}
					}

					for (int i = 0; i < distanceFromGroupCont.size(); i++) {
						double dif = new Double(
								distanceFromGroupCont.elementAt(i));

						if (dif >= -20 && dif < -14) {
							groupDev2014Neg++;
							t_groupDev2014Neg++;
							for (int aux = 0; aux < punishmentDistributions
									.elementAt(i).size(); aux++) {
								double toAdd = new Double(
										punishmentDistributions.elementAt(i)
												.elementAt(aux));
								if (toAdd != 0) {
									avgPunGroupDev2014Neg.add(toAdd);
									t_avgPunGroupDev2014Neg.add(toAdd);
								}
							}
						}
						if (dif >= -14 && dif < -8) {
							groupDev148Neg++;
							t_groupDev148Neg++;
							for (int aux = 0; aux < punishmentDistributions
									.elementAt(i).size(); aux++) {
								double toAdd = new Double(
										punishmentDistributions.elementAt(i)
												.elementAt(aux));
								if (toAdd != 0) {
									avgPunGroupDev148Neg.add(toAdd);
									t_avgPunGroupDev148Neg.add(toAdd);
								}
							}
						}
						if (dif >= -8 && dif < -2) {
							groupDev82Neg++;
							t_groupDev82Neg++;
							for (int aux = 0; aux < punishmentDistributions
									.elementAt(i).size(); aux++) {
								double toAdd = new Double(
										punishmentDistributions.elementAt(i)
												.elementAt(aux));
								if (toAdd != 0) {
									avgPunGroupDev82Neg.add(toAdd);
									t_avgPunGroupDev82Neg.add(toAdd);
								}
							}
						}
						if (dif >= -2 && dif <= 2) {
							groupDev22Neg++;
							t_groupDev22Neg++;
							for (int aux = 0; aux < punishmentDistributions
									.elementAt(i).size(); aux++) {
								double toAdd = new Double(
										punishmentDistributions.elementAt(i)
												.elementAt(aux));
								if (toAdd != 0) {
									avgPunGroupDev22Neg.add(toAdd);
									t_avgPunGroupDev22Neg.add(toAdd);
								}
							}
						}
						if (dif > 2 && dif <= 8) {
							groupDev28Pos++;
							t_groupDev28Pos++;
							for (int aux = 0; aux < punishmentDistributions
									.elementAt(i).size(); aux++) {
								double toAdd = new Double(
										punishmentDistributions.elementAt(i)
												.elementAt(aux));
								if (toAdd != 0) {
									avgPunGroupDev28Pos.add(toAdd);
									t_avgPunGroupDev28Pos.add(toAdd);
								}
							}
						}
						if (dif > 8 && dif <= 14) {
							groupDev814Pos++;
							t_groupDev814Pos++;
							for (int aux = 0; aux < punishmentDistributions
									.elementAt(i).size(); aux++) {
								double toAdd = new Double(
										punishmentDistributions.elementAt(i)
												.elementAt(aux));
								if (toAdd != 0) {
									avgPunGroupDev814Pos.add(toAdd);
									t_avgPunGroupDev814Pos.add(toAdd);
								}
							}
						}
						if (dif > 14 && dif <= 20) {
							groupDev1420Pos++;
							t_groupDev1420Pos++;
							for (int aux = 0; aux < punishmentDistributions
									.elementAt(i).size(); aux++) {
								double toAdd = new Double(
										punishmentDistributions.elementAt(i)
												.elementAt(aux));
								if (toAdd != 0) {
									avgPunGroupDev1420Pos.add(toAdd);
									t_avgPunGroupDev1420Pos.add(toAdd);
								}
							}
						}
					}
					oldContributions = new Vector(playersContributions);
					if (block == 2) {
						secPrinter3.write(rounds + "\t 1" + "\t" + "[-20,-14)"
								+ "\t" + Mean(t_avgPunGroupDev2014Neg) + "\t"
								+ (t_avgPunGroupDev2014Neg.size()) + "\t"
								+ t_groupDev2014Neg + "\t"
								+ Mean(t_avgPunPunishersDev2014Neg) + "\t"
								+ t_avgPunPunishersDev2014Neg.size() + "\t"
								+ t_punisherDev2014Neg);
						secPrinter3.newLine();
						secPrinter3.flush();

						secPrinter3.write(rounds + "\t 2" + "\t" + "[-14,-8)"
								+ "\t" + Mean(t_avgPunGroupDev148Neg) + "\t"
								+ (t_avgPunGroupDev148Neg.size()) + "\t"
								+ t_groupDev148Neg + "\t"
								+ Mean(t_avgPunPunishersDev148Neg) + "\t"
								+ t_avgPunPunishersDev148Neg.size() + "\t"
								+ t_punisherDev148Neg);
						secPrinter3.newLine();
						secPrinter3.flush();

						secPrinter3.write(rounds + "\t 3" + "\t" + "[-8,-2)"
								+ "\t" + Mean(t_avgPunGroupDev82Neg) + "\t"
								+ (t_avgPunGroupDev82Neg.size()) + "\t"
								+ t_groupDev82Neg + "\t"
								+ Mean(t_avgPunPunishersDev82Neg) + "\t"
								+ t_avgPunPunishersDev82Neg.size() + "\t"
								+ t_punisherDev82Neg);
						secPrinter3.newLine();
						secPrinter3.flush();

						secPrinter3.write(rounds + "\t 4" + "\t" + "[-2,2)"
								+ "\t" + Mean(t_avgPunGroupDev22Neg) + "\t"
								+ (t_avgPunGroupDev22Neg.size()) + "\t"
								+ t_groupDev22Neg + "\t"
								+ Mean(t_avgPunPunishersDev22Neg) + "\t"
								+ t_avgPunPunishersDev22Neg.size() + "\t"
								+ t_punisherDev22Neg);
						secPrinter3.newLine();
						secPrinter3.flush();

						secPrinter3.write(rounds + "\t 5" + "\t" + "[2,8)"
								+ "\t" + Mean(t_avgPunGroupDev28Pos) + "\t"
								+ (t_avgPunGroupDev28Pos.size()) + "\t"
								+ t_groupDev28Pos + "\t"
								+ Mean(t_avgPunPunishersDev28Pos) + "\t"
								+ t_avgPunPunishersDev28Pos.size() + "\t"
								+ t_punisherDev28Pos);
						secPrinter3.newLine();
						secPrinter3.flush();

						secPrinter3.write(rounds + "\t 6" + "\t" + "[8,14)"
								+ "\t" + Mean(t_avgPunGroupDev814Pos) + "\t"
								+ (t_avgPunGroupDev814Pos.size()) + "\t"
								+ t_groupDev814Pos + "\t"
								+ Mean(t_avgPunPunishersDev814Pos) + "\t"
								+ t_avgPunPunishersDev814Pos.size() + "\t"
								+ t_punisherDev814Pos);
						secPrinter3.newLine();
						secPrinter3.flush();

						secPrinter3.write(rounds + "\t 7" + "\t" + "[14,20)"
								+ "\t" + Mean(t_avgPunGroupDev1420Pos) + "\t"
								+ (t_avgPunGroupDev1420Pos.size()) + "\t"
								+ t_groupDev1420Pos + "\t"
								+ Mean(t_avgPunPunishersDev1420Pos) + "\t"
								+ t_avgPunPunishersDev1420Pos.size() + "\t"
								+ t_punisherDev1420Pos);
						secPrinter3.newLine();
						secPrinter3.flush();

						secPrinter3.newLine();

					}
				}
			}
		}

		secPrinter.write("1" + "\t" + "[-20,-14)" + "\t"
				+ Mean(avgPunGroupDev2014Neg) + "\t"
				+ (avgPunGroupDev2014Neg.size()) + "\t" + groupDev2014Neg
				+ "\t" + Mean(avgPunPunishersDev2014Neg) + "\t"
				+ avgPunPunishersDev2014Neg.size() + "\t" + punisherDev2014Neg);
		secPrinter.newLine();
		secPrinter.flush();

		secPrinter.write("2" + "\t" + "[-14,-8)" + "\t"
				+ Mean(avgPunGroupDev148Neg) + "\t"
				+ (avgPunGroupDev148Neg.size()) + "\t" + groupDev148Neg + "\t"
				+ Mean(avgPunPunishersDev148Neg) + "\t"
				+ avgPunPunishersDev148Neg.size() + "\t" + punisherDev148Neg);
		secPrinter.newLine();
		secPrinter.flush();

		secPrinter.write("3" + "\t" + "[-8,-2)" + "\t"
				+ Mean(avgPunGroupDev82Neg) + "\t"
				+ (avgPunGroupDev82Neg.size()) + "\t" + groupDev82Neg + "\t"
				+ Mean(avgPunPunishersDev82Neg) + "\t"
				+ avgPunPunishersDev82Neg.size() + "\t" + punisherDev82Neg);
		secPrinter.newLine();
		secPrinter.flush();

		secPrinter.write("4" + "\t" + "[-2,2)" + "\t"
				+ Mean(avgPunGroupDev22Neg) + "\t"
				+ (avgPunGroupDev22Neg.size()) + "\t" + groupDev22Neg + "\t"
				+ Mean(avgPunPunishersDev22Neg) + "\t"
				+ avgPunPunishersDev22Neg.size() + "\t" + punisherDev22Neg);
		secPrinter.newLine();
		secPrinter.flush();

		secPrinter.write("5" + "\t" + "[2,8)" + "\t"
				+ Mean(avgPunGroupDev28Pos) + "\t"
				+ (avgPunGroupDev28Pos.size()) + "\t" + groupDev28Pos + "\t"
				+ Mean(avgPunPunishersDev28Pos) + "\t"
				+ avgPunPunishersDev28Pos.size() + "\t" + punisherDev28Pos);
		secPrinter.newLine();
		secPrinter.flush();

		secPrinter.write("6" + "\t" + "[8,14)" + "\t"
				+ Mean(avgPunGroupDev814Pos) + "\t"
				+ (avgPunGroupDev814Pos.size()) + "\t" + groupDev814Pos + "\t"
				+ Mean(avgPunPunishersDev814Pos) + "\t"
				+ avgPunPunishersDev814Pos.size() + "\t" + punisherDev814Pos);
		secPrinter.newLine();
		secPrinter.flush();

		secPrinter.write("7" + "\t" + "[14,20)" + "\t"
				+ Mean(avgPunGroupDev1420Pos) + "\t"
				+ (avgPunGroupDev1420Pos.size()) + "\t" + groupDev1420Pos
				+ "\t" + Mean(avgPunPunishersDev1420Pos) + "\t"
				+ avgPunPunishersDev1420Pos.size() + "\t" + punisherDev1420Pos);
		secPrinter.newLine();
		secPrinter.flush();

		secPrinter2.write("1" + "\t" + "[0,2)" + "\t"
				+ Mean(contImprovements02) + "\t" + contImprovements02.size());
		secPrinter2.newLine();
		secPrinter2.flush();
		secPrinter2.write("2" + "\t" + "[2,4)" + "\t"
				+ Mean(contImprovements24) + "\t" + contImprovements24.size());
		secPrinter2.newLine();
		secPrinter2.flush();
		secPrinter2.write("3" + "\t" + "[4,6)" + "\t"
				+ Mean(contImprovements46) + "\t" + contImprovements46.size());
		secPrinter2.newLine();
		secPrinter2.flush();
		secPrinter2.write("4" + "\t" + "[6,8)" + "\t"
				+ Mean(contImprovements68) + "\t" + contImprovements68.size());
		secPrinter2.newLine();
		secPrinter2.flush();
		secPrinter2
				.write("5" + "\t" + "[8,10)" + "\t" + Mean(contImprovements810)
						+ "\t" + contImprovements810.size());
		secPrinter2.newLine();
		secPrinter2.flush();

		secPrinter4.write("1" + "\t" + "[0,5)" + "\t"
				+ Mean(contImprovementsMes05) + "\t"
				+ contImprovementsMes05.size());
		secPrinter4.newLine();
		secPrinter4.flush();
		secPrinter4.write("2" + "\t" + "[5,10)" + "\t"
				+ Mean(contImprovementsMes510) + "\t"
				+ contImprovementsMes510.size());
		secPrinter4.newLine();
		secPrinter4.flush();
		secPrinter4.write("3" + "\t" + "[10,15)" + "\t"
				+ Mean(contImprovementsMes1015) + "\t"
				+ contImprovementsMes1015.size());
		secPrinter4.newLine();
		secPrinter4.flush();
		secPrinter4.write("4" + "\t" + "[15,20]" + "\t"
				+ Mean(contImprovementsMes1520) + "\t"
				+ contImprovementsMes1520.size());
		secPrinter4.newLine();
		secPrinter4.flush();

		breader.close();

		secPrinter.close();
		secPrinter2.close();
		secPrinter3.close();
		secPrinter4.close();
	}
}