import java.math.BigInteger;
import java.util.Random;

public class Phenotype {
	int numInputs;
	int numNeurons;
	double [][] weights;
	double [] rule;
	Random rand;

	Phenotype(Genotype g, Random r){
		this.numInputs = g.numInputs;
		this.numNeurons = g.numNeurons;
		this.weights = transformWeights(g.weights);
		this.rule = g.rule;
		this.rand = r;
	}

	double [][] transformWeights(double [][] weights) {
		double [][] ret = new double[weights.length][weights[0].length];
		double ulim = 10.0;
		double llim = 0.01;
		for (int i = 0; i < weights.length; i ++) {
			for (int j = 0; j < weights[0].length; j ++) {
				ret[i][j] = transform(weights[i][j], ulim, llim);
			}
		}
		return ret;
	}

	double [] transformRule(double [] rule){
		double [] ret = new double[rule.length];
		double ulim = 30.0;
		double llim = 0.1;
		for (int i = 0; i < rule.length; i ++) {
			ret[i] = transform(rule[i], ulim, llim);
		}
		if (rule[0] == 0) {
			ret[0] = (this.rand.nextDouble() + 0.5) * sign(this.rand.nextGaussian());
		}
		if (isAll(ret, 0, 1, 5)) {
			for (int i = 1; i < 5; i ++) {
				ret[i] = (this.rand.nextDouble() + 0.5) * sign(this.rand.nextGaussian());
			}

		}
		return ret;
	}

	boolean isAll(double [] a, double v, int start, int end) {
		if (start >= a.length || end > a.length) {
			System.err.println("[ERROR] start and end should be in the range !");
			System.exit(1);
		}
		for (int i = start; i < end; i ++) {
			if (a[i] != v) {
				return false;
			}
		}
		return true;
	}

	double transform(double gx, double ulim, double llim) {
		double x = Math.pow(gx, 3);
		if (Math.abs(x) > ulim) {
			return sign(x) * ulim;
		}else {
			if (Math.abs(x) > llim) {
				return x;
			}else {
				return 0;
			}
		}
	}

	int sign(double a) {
		return BigInteger.valueOf((int)a).signum();
	}
}