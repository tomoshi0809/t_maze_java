import java.util.Random;

public class Animat extends Phenotype {
	double[][] state;
	boolean plastic;

	Animat(Genotype g, Random r) {
		super(g, r);
		this.state = new double[this.numStdNeurons + this.numMdlNeurons][1];
		this.plastic = true;
	}

	double [][] behave (double [][] inputs){
		double [][] weights = Matrix.mask(this.weights, getStdMask(this.numInputs, this.numStdNeurons, this.numMdlNeurons));
		double [][] outputs = forward(weights, inputs);
		return outputs;
	}

	double [][] modulate (double [][] inputs) {
		double [][] weights = Matrix.mask(this.weights, getMdlMask(this.numInputs, this.numStdNeurons, this.numMdlNeurons));
		return forward(weights, inputs);
	}

	int [][] getStdMask(int numInputs, int numStdNeurons, int numMdlNeurons) {
		int [][] mask = new int[numStdNeurons + numMdlNeurons][numInputs + numStdNeurons + numMdlNeurons];
		for (int i = 0; i < numStdNeurons + numMdlNeurons; i ++) {
			for (int j = 0; j <  numInputs +numStdNeurons; j ++) {
				mask[i][j] = 1;
			}
			for (int j =  numInputs + numStdNeurons; j < numInputs + numStdNeurons + numMdlNeurons; j ++) {
				mask[i][j] = 0;
			}
		}
		return mask;
	}

	int [][] getMdlMask(int numInputs, int numStdNeurons, int numMdlNeurons) {
		int [][] mask = new int[numStdNeurons + numMdlNeurons][numInputs + numStdNeurons + numMdlNeurons];
		for (int i = 0; i < numStdNeurons + numMdlNeurons; i ++) {
			for (int j = 0; j <  numInputs +numStdNeurons; j ++) {
				mask[i][j] = 0;
			}
			for (int j =  numInputs + numStdNeurons; j < numInputs + numStdNeurons + numMdlNeurons; j ++) {
				mask[i][j] = 1;
			}
		}
		return mask;
	}

	double[][] forward (double [][] weights, double[][] inputs) {
		return Matrix.tanh(Matrix.scalar(0.5, Matrix.dot(weights, Matrix.concatenate(inputs, this.state, 1))));
	}

	double [][] learnStdNeurons(double [][] inputs, double [][] outputs, int numStdNeurons, int numMdlNeurons, double [][] modulations){
		double [][] deltaWeights = this.deltaWeight(inputs, outputs, modulations, this.state, this.numInputs, this.numStdNeurons, this.numMdlNeurons);
		deltaWeights = Matrix.concatenate(deltaWeights, Matrix.zeros(numStdNeurons + numMdlNeurons, numMdlNeurons), 0);
		double[][] updatedWeights = Matrix.add(this.weights, deltaWeights);
		return inspectWeight(updatedWeights);
	}

	double[][] learn(double[][] inputs, double[][] outputs, double[][] modulations) {
		double[][] updatedWeights = Matrix.add(this.weights, this.deltaWeight(inputs, outputs, modulations, this.state, this.numInputs, this.numStdNeurons, this.numMdlNeurons));
		return inspectWeight(updatedWeights);
	}

	double[][] deltaWeight(double[][] inputs, double[][] outputs, double [][] modulations, double [][] states, int numInputs, int numStdNeurons, int numMdlNeurons){
		double[][] u = new double[numInputs + numStdNeurons][1];
		for (int i = 0; i < numInputs; i ++) {
			u[i][0] = inputs[i][0];
		}
		for (int i = 0; i < numStdNeurons; i ++) {
			u[i + numInputs][0] = states [i][0];
		}
		double[][] v;

		if (outputs.length == 0) {
			v = behave(inputs);
		} else {
			v = outputs;
		}
		double[][] w = new double[v.length][u.length];
		for (int i = 0; i < v.length; i++) {
			for (int j = 0; j < u.length; j++) {
				w[i][j] = applyRule(u[j][0], v[i][0], modulations[i][0]);
			}
		}

		double[][] ret = new double[v.length][u.length];
		for (int i = 0; i < v.length; i++) {
			for (int j = 0; j < u.length; j++) {
				if (this.weights[i][j] != 0) {
					ret[i][j] = w[i][j];
				}
			}
		}
		return ret;
	}

	double applyRule(double x, double y, double m) {
		return Math.tanh((double)m/2) * this.rule[0] * (this.rule[1] * x * y + this.rule[2] * x + this.rule[3] * y + this.rule[4]);
	}

	double[][] inspectWeight(double [][] weights) {
		double [][] ret = new double[weights.length][weights[0].length];
		for (int i = 0; i < weights.length; i ++) {
			for (int j = 0; j < weights[0].length; j ++) {
				if (Math.abs(weights[i][j]) < 10.0) {
					ret[i][j] = weights[i][j];
				} else {
					double a = weights[i][j];
					ret[i][j] = 10.0 * (a / Math.abs(a));
				}
			}
		}
		return ret;
	}

	double[][] perform (double[][] inputs) {
		double [][] outputs = this.behave(inputs);
		double [][] modulations = this.modulate(inputs);
		if (this.plastic) {
			this.weights = this.learnStdNeurons(inputs,  outputs, this.numStdNeurons, this.numMdlNeurons, modulations);
		} else {
			this.weights = this.weights;
		}
		this.state = outputs;
		return outputs;
	}
}
