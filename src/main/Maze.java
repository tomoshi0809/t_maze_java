abstract class Environment {
	int numInputs;
	abstract public int evaluate(Phenotype p);
}

abstract public class Maze extends Environment {
	double noiseStd;
	int numThink;
	boolean debug;

	Maze (double noiseStd, int numThink) {
		this.numInputs = 5;
		this.numThink = numThink;
		this.noiseStd = noiseStd;
		this.debug = false;
	}

	boolean home(Animat animat) {
		return true;
	}

	boolean corridor(Animat animat) {
		return true;
	}

	boolean junction(Animat animat) {
		return true;
	}

	double maze_end(Animat animat) {
		return 0;
	}

	double thinking(Animat animat, double [][] inputs) {
		double [][] output;
		for (int i = 0; i < this.numThink; i ++ ) {
			double [][] a = Matrix.add(inputs, noise());
			output = animat.perform(a);
		}
		return output;
	}

	double [][] noise() {
		double [][] noise = new double[this.numInputs][1];
		for (int i = 0; i < noise.length; i ++){
			noise[i][0] = Math.random() * this.noiseStd;
		}
		return noise;
	}
}
