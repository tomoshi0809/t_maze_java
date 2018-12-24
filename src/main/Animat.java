
public class Animat extends Phenotype {
	double[][] state;
	boolean plastic;

	Animat(Genotype g) {
		super(g);
		this.state = new double[this.numNeurons][1];
		this.plastic = true;
	}

	double[][] behave(double[][] inputs) {
		return Matrix.tanh(Matrix.dot(this.weights, Matrix.concatenate(inputs, this.state, 1)));
	}

	double[][] learn(double[][] inputs, double[][] outputs) {
		double[][] updated_weights = Matrix.add(this.weights, this.delta_weight(inputs, outputs));
		return inspect_weight(updated_weights);
	}

	double[][] delta_weight(double[][] inputs, double[][] outputs) {
		double[][] u = Matrix.concatenate(inputs, this.state, 1);
		double[][] v;
		if (outputs.length == 0) {
			v = behave(inputs);
		} else {
			v = outputs;
		}
		double[][] w = new double[v.length][u.length];
		for (int i = 0; i < v.length; i++) {
			for (int j = 0; j < u.length; j++) {
				w[i][j] = apply_rule(u[j][0], v[i][0]);
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

	double apply_rule(double x, double y) {
		return this.rule[0] * this.rule[1] * x * y + this.rule[2] * x + this.rule[3] * y + this.rule[4];
	}

	double[][] inspect_weight(double [][] weights) {
		double [][] ret = new double[weights.length][weights[0].length];
		for (int i = 0; i < weights.length; i ++) {
			for (int j = 0; j < weights[0].length; j ++) {
				if (weights[i][j] < 10.0) {
					ret[i][j] = weights[i][j];
				} else {
					double a = weights[i][j];
					ret[i][j] = 10.0 * (a / Math.abs(a));
				}
			}
		}
		return ret;
	}

	double[][] perform(double[][] inputs) {
		double [][] outputs = this.behave(inputs);
		if (this.plastic) {
			this.weights = this.learn(inputs,  outputs);
		} else {
			this.weights = this.weights;
		}
		this.state = outputs;
		return outputs;
	}
}
