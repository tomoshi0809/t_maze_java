
public class Animat extends Phenotype {
	double [] state;
	boolean plastic;

	Animat (Genotype g){
		super(g);
		this.state = new double[this.numNeurons];
		this.plastic = true;
	}

	double behave(double [] inputs) {
		return 0;
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
