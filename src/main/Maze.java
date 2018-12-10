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
}
