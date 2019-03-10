import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

class AnimatTest {
	@Test
	void testBehave() {
		Genotype g = new Genotype(5, 2);
		Animat a = new Animat(g, new Random());
		a.state = zeros(a.numNeurons, 1);
		a.weights = ones2D(2, 7);
		int num_inputs = 5;
		int num_neurons = 2;

		double [][] input = ones(a.numInputs,1);
		double [][] output = Matrix.tanh(Matrix.scalar(num_inputs, ones(num_neurons, 1)));
		a.state = zeros(num_neurons, 1);
		boolean f = assertEual2DArray(a.behave(input), output);
		assertTrue(f);

		output = Matrix.tanh(Matrix.scalar(num_inputs + num_neurons, ones(num_neurons, 1)));
		a.state = ones(num_neurons, 1);
		f = assertEual2DArray(a.behave(input), output);
		assertTrue(f);
	}

	@Test
	void testDelta_weight() {
		Genotype g = new Genotype(5, 2);
		Animat a = new Animat(g, new Random());
		a.state = zeros(a.numNeurons, 1);
		a.weights = ones2D(2, 7);
		int num_inputs = 5;
		int num_neurons = 2;

		double [][] input = ones(a.numInputs, 1);
		double [][] output = ones(a.numNeurons, 1);
		a.state = ones(num_neurons, 1);
		a.rule[0] = 1;
		a.rule[1] = 1;
		a.rule[2] = 1;
		a.rule[3] = 1;
		a.rule[4] = 1;

		double [][] expected_delta = Matrix.scalar(4, ones2D(num_neurons, num_inputs + num_neurons));
		double [][] actual_delta = a.deltaWeight(input, output);
		boolean f = assertEual2DArray(expected_delta, actual_delta);
		assertTrue(f);
	}

	@Test
	void testInspect_weight() {
		Genotype g = new Genotype(5, 2);
		Animat a = new Animat(g, new Random());
		a.state = zeros(a.numNeurons, 1);
		a.weights = ones2D(2, 7);
		int num_inputs = 5;
		int num_neurons = 2;
		double [][] input = ones2D(num_neurons, num_inputs + num_neurons);
		double [][] output = ones2D(num_neurons, num_inputs + num_neurons);
		a.state = ones(num_neurons, 1);
		boolean f = assertEual2DArray(a.inspectWeight(input), output);
	}

	@Test
	void testPerform() {

	}

	@Test
	void testApply_rule() {
		Genotype g = new Genotype(5, 2);
		Animat a = new Animat(g, new Random());
		a.state = zeros(a.numNeurons, 1);
		a.weights = ones2D(2, 7);
		int num_inputs = 5;
		int num_neurons = 2;
		a.state = ones(num_neurons, 1);
		a.rule[0] = 1;
		a.rule[1] = 1;
		a.rule[2] = 1;
		a.rule[3] = 1;
		a.rule[4] = 1;
		assertTrue(a.applyRule(3, 4) == 20);
	}

	double [][] ones (int len, int axis) {
		if (axis == 0) {
			double [][] ret = new double[1][len];
			for (int i = 0; i < len; i ++) {
				ret[0][i] = 1;
			}
			return ret;
		} else if (axis == 1) {
			double [][] ret = new double[len][1];
			for (int i = 0; i < len; i ++) {
				ret[i][0] = 1;
			}
			return ret;
		} else {
			System.err.println("ones - not suppor this axis" + axis);
			return null;
		}
	}

	double [][] ones2D (int nrow, int ncol) {
		double [][] ret = new double[nrow][ncol];
		for (int i = 0; i < nrow; i ++) {
			for (int j = 0; j < ncol; j ++) {
				ret[i][j] = 1;
			}
		}
		return ret;
	}

	double [][] zeros (int len, int axis) {
		if (axis == 0) {
			double [][] ret = new double[1][len];
			for (int i = 0; i < len; i ++) {
				ret[0][i] = 0;
			}
			return ret;
		} else if (axis == 1) {
			double [][] ret = new double[len][1];
			for (int i = 0; i < len; i ++) {
				ret[i][0] = 0;
			}
			return ret;
		} else {
			System.err.println("zeros - not suppor this axis" + axis);
			return null;
		}
	}

	boolean assertEual2DArray(double [][] m1, double [][] m2) {
		int nrow = m1.length;
		int ncol = m1[0].length;
		for (int i = 0; i < nrow; i ++) {
			for (int j = 0; j < ncol; j ++) {
				if (m1[i][j] != m2[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
}
