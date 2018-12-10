
public class Genotype {
	int numInputs;
	int numNeurons;
	double sgRate; //sgRate: Synaptogenesis rate
	double [][]weights;
	double []rule;
	double fitness;

	Genotype(int numInputs, int numNeurons){
		this.numInputs = numInputs;
		this.numNeurons = numNeurons;
		this.sgRate = 1.0;
		int r = this.numNeurons;
		int c = this.numInputs + this.numNeurons;
		this.weights = initializeWeights(r, c, this.sgRate);
		this.rule = new double[5];
		for (int i = 0; i < 5; i ++) {
			this.rule[i] = Math.random() * 3.0 - 1.5;
		}
		this.fitness = 0.0;
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
}
