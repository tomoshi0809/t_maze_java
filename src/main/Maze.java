import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

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

	double thinking(Animat animat, RealMatrix input) {
		input = input.add(noise(animat));
		return 0;
	}

	RealMatrix noise(Animat animat) {
		double [] noise = new double[this.numInputs];
		for (int i = 0; i < noise.length; i ++){
			noise[i] = Math.random() * this.noiseStd;
		}
		return MatrixUtils.createRowRealMatrix(noise);
	}
}
