import java.util.Random;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

public class EvolutionarySearch {
	final double CROSS_RATE = 0.1;
	final double MUTATE_RATE = 0.1;
	final double SIGMA = 0.2;
	final int NUM_THREAD = 2;
	Environment env;
	Phenotype p;
	int numInputs;
	int numNeurons;
	int numPops;
	int numGroup;
	Genotype[] pop;
	int numEval;
	Class envCls;
	Class pheCls;
	Random rand;
	FileReadWriter frw;

	EvolutionarySearch(Class envCls, Class pheCls, int numNeurons, int numPops, int numGroup) {
		try {
			Class envClass = Class.forName(envCls.getName());
			this.env = (Environment) envClass.newInstance();
		} catch (Exception e) {
			System.err.println(e);
			System.exit(0);
		}
		this.numInputs = env.numInputs;
		this.numNeurons = numNeurons;
		this.numPops = numPops;
		this.numGroup = numGroup;
		this.pop = new Genotype[numPops];
		for (int i = 0; i < numPops; i++) {
			this.pop[i] = new Genotype(numInputs, numNeurons);
		}
		this.numEval = 5;
		this.envCls = envCls;
		this.pheCls = pheCls;
		this.rand = new Random();
		this.frw = new FileReadWriter();
	}

	public void run(int numGenerations, boolean isPrintStats, boolean isWriteFile) {
		int [][] numCors = createNumCors(numGenerations);
		for (int generation = 0; generation < numGenerations; generation++) {
			int numVerCor = numCors[generation][0];
			int numHorCor = numCors[generation][1];
			evaluate(this.pop, this.numEval, numVerCor, numHorCor);
			if (isPrintStats) {
				double[] stats = fitStats(this.pop);
				System.out.print(generation + ", ");
				for (int j = 0; j < stats.length; j++) {
					System.out.print(stats[j] + ", ");
				}
				System.out.println();
			}

			if (isWriteFile) {
				this.frw.putGenoType(generation, this.pop);
			}
			if (generation == numGenerations - 1) {
				break;
			}
			Genotype [] selected = select(this.pop, this.numPops, this.numGroup);
			Genotype [] crossovered = crossover(selected, CROSS_RATE);
			this.pop = cleanFitness(mutate(crossovered, MUTATE_RATE, SIGMA));
		}
	}

	int [][] createNumCors(int numGeneration){
		int [][] ret = new int[numGeneration][2];
		for (int i = 0; i < numGeneration; i ++) {
			ret[i][0] = 1 + (int)(Math.random() * 2);
			ret[i][1] = 1 + (int)(Math.random() * 2);
		}
		return ret;
	}

	void evaluate(Genotype[] pop, int numEval,  int numVerCor, int numHorCor) {
		int npop = pop.length;
		int nperthread = npop / NUM_THREAD;
		int thidx = 0;

		Thread [] th = new Thread[NUM_THREAD];

		while (thidx < NUM_THREAD) {
			int start = nperthread * thidx;
			int end = nperthread * (thidx + 1);
			if (thidx == NUM_THREAD - 1) {
				end = npop;
			}
			Genotype [] unit = new Genotype[end - start];
			for (int i = 0; i < (end - start); i ++) {
				unit[i] = pop[start + i];
			}
			th [thidx] = new EvaluateThread(this.env, unit, this.numEval, this.rand, numVerCor, numHorCor);
			th[thidx].start();
			thidx ++;
		}

		for (thidx = 0; thidx < NUM_THREAD; thidx ++) {
			try {
				th[thidx].join();
			} catch (InterruptedException e) {
				System.err.println(e);
				System.exit(1);
			}
		}
	}

	double[] fitStats(Genotype[] pop) {
		double[] fits = new double[pop.length];
		double [][] rules = new double [5][pop.length];
		double[] rewRight = new double[pop.length];
		double[] rewLeft = new double[pop.length];
		for (int i = 0; i < pop.length; i++) {
			fits[i] = pop[i].fitness;
			rewRight[i] = pop[i].data.getAveRewRight();
			rewLeft[i] = pop[i].data.getAveRewLeft();
			for (int index = 0; index < 5; index ++) {
				rules[index][i] = pop[i].rule[index];
			}
		}

		StandardDeviation std = new StandardDeviation(false);
		double[] ret = { StatUtils.mean(fits), StatUtils.percentile(fits, 50), std.evaluate(fits),
				StatUtils.max(fits), StatUtils.min(fits), StatUtils.mean(rewRight), StatUtils.mean(rewLeft),
				StatUtils.mean(rules[0]), StatUtils.mean(rules[1]), StatUtils.mean(rules[2]),StatUtils.mean(rules[3]),StatUtils.mean(rules[4])};
		return ret;
	}

	Genotype[] select(Genotype[] pop, int numPop, int numGroup) {
		if (pop.length == 0) {
			System.err.println("EvolutionarySearch - select () receives an empty pop");
		}
		Genotype[] ret = new Genotype[numPop];
		int offset = (int) (Math.random() * numPop);
		for (int count = 0; count < (numPop / numGroup); count ++) {
			int head = (offset + numGroup * count) % numPop;
			Genotype [] group = new Genotype[numGroup];
			for (int index = head; index < head + numGroup; index ++) {
				group[index - head] = pop[index % numPop];
			}
			Genotype groupBest = getBest(group);
			ret[head] = pop[head];
			for (int index = head + 1; index < head + numGroup; index ++) {
				ret[index % numPop] = Genotype.copy(groupBest);
			}
		}
		return ret;
	}

	Genotype[] crossover(Genotype[] pop, double cRate) {
		Genotype [] ret = new Genotype[pop.length];
		Genotype best = getBest(pop);
		for (int index = 0; index < pop.length; index ++) {
			if (pop[index] != best && Math.random() < cRate) {
				Genotype partner = pop[(int)(Math.random() * pop.length)];
				Genotype child = Genotype.crossGenotype(pop[index], partner);
				ret[index] = child;
			} else {
				ret[index] = pop[index];
			}
		}
		return ret;
	}

	Genotype[] mutate(Genotype[] pop, double mRate, double sigma) {
		Genotype best = getBest(pop);
		for (int index = 0; index < pop.length; index ++) {
			pop[index] = Genotype.mutateGenotype(pop[index], mRate, sigma, this.rand);
		}
		return pop;
	}

	Genotype[] cleanFitness(Genotype[] pop) {
		for (int index = 0; index < pop.length; index ++) {
			pop[index].fitness = 0;
		}
		return pop;
	}

	Genotype getBest(Genotype [] group) {
		if (group.length == 0) {
			System.err.println("EvolutionarySearch - getBest() receives an empty group");
		}
		double max = 0;
		int index = 0;
		for (int i = 0; i < group.length; i ++) {
			if (group[i].fitness >= max) {
				max = group[i].fitness;
				index = i;
			}
		}
		return group[index];
	}
}
