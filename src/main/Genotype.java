import java.util.Random;

public class Genotype {
	int numInputs;
	int numStdNeurons;
	int numMdlNeurons;
	double sgRate; //sgRate: Synaptogenesis rate
	double [][]weights;
	double []rule;
	double fitness;
	double MIN_FITNESS = -9999;
	Data data;
	GeneTag genetag;

	Genotype(){}

	Genotype(int numInputs, int numStdNeurons, int numMdlNeurons){
		this.numInputs = numInputs;
		this.numStdNeurons = numStdNeurons;
		this.numMdlNeurons = numMdlNeurons;
		this.sgRate = 1.0;
		int r = this.numStdNeurons + this.numMdlNeurons;
		int c = this.numInputs + this.numStdNeurons + this.numMdlNeurons;
		this.weights = initializeWeights(r, c, this.sgRate);
		this.rule = new double[5];
		for (int i = 0; i < 5; i ++) {
			this.rule[i] = Math.random() * 3.0 - 1.5;
		}
		this.fitness = MIN_FITNESS;
		this.genetag = new GeneTag();
	}

	static Genotype crossGenotype(Genotype parent1, Genotype parent2) {
		Genotype child = copy(parent1);
		child.weights = crossWeights(child.weights, parent2.weights);
		child.rule = crossRule(child.rule, parent2.rule);
		return child;
	}

	static Genotype mutateGenotype(Genotype g, double mRate, double sigma, Random r) {
		int nrow = g.weights.length;
		int ncol = g.weights[0].length;
		for (int i = 0; i < nrow; i ++) {
			for (int j = 0; j < ncol; j ++) {
				if (Math.random() < mRate) {
					g.weights[i][j] += r.nextGaussian() * sigma;
				}
			}
		}

		for (int i = 0; i < g.rule.length; i ++) {
			if (Math.random() < mRate) {
				g.rule[i] += r.nextGaussian() * sigma;
			}
		}
		return g;
	}

	static double [][] crossWeights(double [][] weights1, double [][] weights2){
		int nrow = weights1.length;
		int ncol = weights1[0].length;
		int p1 = (int)(Math.random() * nrow);
		int p2 = (int)(Math.random() * ncol);

		for (int i = 0; i < nrow; i ++) {
			for (int j = 0; j < ncol; j ++) {
				if (i < p1 && j < p2) {
					weights1[i][j] = weights2[i][j];
				}
				if (i >= p1 && j >= p2) {
					weights1[i][j] = weights2[i][j];
				}
			}
		}
		return weights1;
	}

	static double [] crossRule(double [] rule1, double []rule2) {
		for (int i = 0; i < rule1.length; i ++) {
			if (Math.random() < 0.5) {
				rule1[i] = rule1[i];
			} else {
				rule1[i] = rule2[i];
			}
		}
		return rule1;
	}

	static Genotype copy(Genotype g) {
		if (g == null) {
			System.err.println("Genotype - copy () receives null Genotype");
		}
		Genotype ret = new Genotype();
		ret.weights = new double[g.numStdNeurons + g.numMdlNeurons][g.numStdNeurons + g.numInputs + g.numMdlNeurons];
		ret.rule = new double[5];
		ret.numInputs = g.numInputs;
		ret.numStdNeurons = g.numStdNeurons;
		ret.numMdlNeurons = g.numMdlNeurons;
		ret.sgRate = g.sgRate;
		for (int i = 0; i < g.weights.length; i++) {
			for (int j = 0; j < g.weights[0].length; j++) {
				ret.weights[i][j] = g.weights[i][j];
			}
		}
		for (int i = 0; i < 5; i++) {
			ret.rule[i] = g.rule[i];
		}
		ret.fitness = g.fitness;
		ret.genetag = new GeneTag();
		return ret;
	}

	double [][] initializeWeights(int row, int col, double sgRate) {
		double [][] ret = new double[row][col];
		for (int r = 0; r < row; r ++ ) {
			for (int c = 0; c < col; c ++) {
				if (Math.random() < sgRate) {
					ret[r][c] = Math.random() * 2.0 - 1.0;
				}else {
					ret[r][c] = 0.0;
				}
			}
		}
		return ret;
	}

	int randNNeurons(int ulim) {
		if (ulim == 0) {
			ulim = 20;
		}
		return (int)(Math.random() * (ulim - 1)) + 1;
	}
}
