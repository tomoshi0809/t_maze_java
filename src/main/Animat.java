
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

		return null;
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

	double[] inspect_weight(double weight) {
		return null;
	}

	double[] perform(double[] input) {
		return null;
	}
}
