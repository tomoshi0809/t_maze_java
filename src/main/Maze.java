abstract class Environment {
	int numInputs;
	abstract double evaluate(Phenotype p);
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
		double [][] input = {{1.0}, {0.0}, {0.0}, {0.0}, {1.0}};
		double [][] out = this.thinking(animat, input);
		if (this.debug) {
			System.out.println("MS: " + out[0][0]);
		}
		if (Math.abs(out[0][0]) <= (double)1/3) {
			return true;
		}
		return false;
	}

	boolean corridor(Animat animat) {
		double [][] input = {{0.0}, {0.0}, {0.0}, {0.0}, {1.0}};
		double [][] out = this.thinking(animat, input);
		if (this.debug) {
			System.out.println("CO: " + out[0][0]);
		}
		if (Math.abs(out[0][0]) <= (double)1/3) {
			return true;
		}
		return false;
	}

	double junction(Animat animat) {
		double [][] input = {{0.0}, {1.0}, {0.0}, {0.0}, {1.0}};
		double [][] out = this.thinking(animat, input);
		if (this.debug) {
			System.out.println("JN: " + out[0][0]);
		}
		return out[0][0];
	}

	double maze_end(Animat animat, double reward) {
		double [][] input = {{0.0}, {0.0}, {1.0}, {reward}, {1.0}};
		double [][] out = this.thinking(animat, input);
		if (this.debug) {
			System.out.println("ME: " + out[0][0]);
		}
		return out[0][0];
	}

	double [][] thinking(Animat animat, double [][] inputs) {
		double [][] output = null;
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
