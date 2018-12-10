import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

public class EvolutionarySearch {
	Environment env;
	Phenotype p;
	int numInputs;
	int numNeurons;
	int numPop;
	int numGroups;
	Genotype[] pop;
	int numEval;
	Class envCls;
	Class pheCls;

	EvolutionarySearch(Class envCls, Class pheCls, int numNeurons, int numPops,int numGroups){
		try {
			Class envClass = Class.forName(envCls.getName());
			this.env = (Environment)envClass.newInstance();
		}catch (Exception e){
			System.err.println(e);
			System.exit(0);
		}
		this.p = p;
		this.numInputs = env.numInputs;
		this.numNeurons = numNeurons;
		this.numPop = numPop;
		this.numGroups = numGroups;
		this.pop = new Genotype[numPop];
		for (int i = 0; i < numPop; i ++) {
			this.pop[i] = new Genotype(numInputs, numNeurons);
		}
		this.numEval = 2;
		this.envCls = envCls;
		this.pheCls = pheCls;
	}

	private void run (int numGenerations, boolean printStats) {
		for (int i = 0; i < numGenerations; i ++ ){
			evaluate(this.pop, this.numEval);
			if (printStats) {
				double [] stats = fit_stats(this.pop);
				for (int j = 0; j < stats.length; j ++ ) {
					System.out.print(stats[j] + ", ");
				}
				System.out.println();
			}
			if (i == numGenerations - 1) {
				break;
			}
			this.pop = mutate(crossover(select(this.pop, this.numPop, this.numGroups)));
		}
	}

	void evaluate(Genotype [] pop, int numEval) {
		for (Genotype g: pop) {
			g.fitness = Math.random();	//TODO: You should fix
		}
	}

	double [] fit_stats(Genotype [] pop) {
		double [] fits = new double[pop.length];
		for (int i = 0; i < pop.length; i ++) {
			fits[i] = pop[i].fitness;
		}
		StandardDeviation std = new StandardDeviation(false);
		double [] ret = {StatUtils.mean(fits), StatUtils.percentile(fits, 50), std.evaluate(fits),
						StatUtils.max(fits), StatUtils.min(fits)};
		return ret;
	}

	Genotype[] select(Genotype [] pop, int numPop, int numGropus) {
		return pop;
	}

	Genotype[] crossover(Genotype [] pop) {
		return pop;
	}

	Genotype[] mutate(Genotype [] pop) {
		return pop;
	}
}
