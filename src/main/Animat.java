
public class Animat extends Phenotype {
	double [][] state;
	boolean plastic;

	Animat (Genotype g){
		super(g);
		this.state = new double[this.numNeurons][1];
		this.plastic = true;
	}

	double [][] behave(double [][] inputs) {
		return Matrix.tanh(Matrix.dot(this.weights, Matrix.concatenate(inputs, this.state, 1)));
	}

	double [][] learn(double []inputs, double [] output){
		return null;
	}

	int apply_rule(int x, int y) {
		return 0;
	}

	double [] inspect_weight(double weight) {
		return null;
	}

	double [] perform(double [] input) {
		return null;
	}
}
