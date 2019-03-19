import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

class AnimatTest {
	@Test
	void testBehave() {
		Genotype g = new Genotype(5, 1, 1);
		Animat a = new Animat(g, new Random());
		a.state = zeros(a.numStdNeurons, 1);
		a.weights = ones2D(2, 7);
		int numInputs = 5;
		int numStdNeurons = 1;
		int numMdlNeurons = 1;

		double [][] input = ones(a.numInputs,1);
		double [][] output = Matrix.tanh(Matrix.scalar(0.5 * numInputs, ones(numStdNeurons + numMdlNeurons, 1)));
		a.state = zeros(numStdNeurons + numMdlNeurons, 1);
		double [][] actual = a.behave(input);
		boolean f = assertEual2DArray(actual, output);
		assertTrue(f);

		output = Matrix.tanh(Matrix.scalar(0.5 * (numInputs + numStdNeurons), ones(numStdNeurons + numMdlNeurons, 1)));
		a.state = ones(numStdNeurons+ numMdlNeurons, 1);
		actual = a.behave(input);
		f = assertEual2DArray(actual, output);
		assertTrue(f);
	}

	@Test
	void testModulate () {
		Genotype g = new Genotype(5, 1, 1);
		Animat a = new Animat(g, new Random());
		a.state = zeros(a.numStdNeurons, 1);
		a.weights = ones2D(2, 7);
		int numInputs = 5;
		int numStdNeurons = 1;
		int numMdlNeurons = 1;

		double [][] input = ones(a.numInputs,1);
		double [][] output = Matrix.tanh(Matrix.scalar((double)numMdlNeurons/2, ones(numStdNeurons + numMdlNeurons, 1)));
		a.state = ones(numStdNeurons + numMdlNeurons, 1);
		double [][] actual = a.modulate(input);

		boolean f = assertEual2DArray(actual, output);
		assertTrue(f);
	}

	@Test
	void testDelta_weight() {
		Genotype g = new Genotype(5, 1, 1);
		Animat a = new Animat(g, new Random());
		a.state = zeros(a.numStdNeurons + a.numMdlNeurons, 1);
		a.weights = ones2D(2, 7);
		int numInputs = 5;
		int numStdNeurons = 1;
		int numMdlNeurons = 1;

		double [][] input = ones(a.numInputs, 1);
		double [][] output = ones(a.numStdNeurons + a.numMdlNeurons, 1);
		double [][] modulations = {{1}, {1}};
		a.state = ones(numStdNeurons + numMdlNeurons, 1);
		a.rule[0] = 1;
		a.rule[1] = 1;
		a.rule[2] = 1;
		a.rule[3] = 1;
		a.rule[4] = 1;

		double [][] expected_delta = Matrix.scalar(Math.tanh(0.5) * 4, ones2D(numStdNeurons + numMdlNeurons, numInputs + numStdNeurons));
		double [][] actual_delta = a.deltaWeight(input, output, modulations, a.state, a.numInputs, a.numStdNeurons, a.numMdlNeurons);
		boolean f = assertEual2DArray(expected_delta, actual_delta);
		assertTrue(f);
	}

	@Test
	void testLearnStdNeurons () {
		Genotype g = new Genotype(5, 1, 1);
		Animat a = new Animat(g, new Random());
		a.state = ones(a.numStdNeurons + a.numMdlNeurons, 1);
		a.weights = ones2D(2, 7);
		int numInputs = 5;
		int numStdNeurons = 1;
		int numMdlNeurons = 1;
		a.rule[0] = 1;
		a.rule[1] = 1;
		a.rule[2] = 1;
		a.rule[3] = 1;
		a.rule[4] = 1;

		double [][] modulations = {{1}, {1}};
		double [][] inputs = ones(a.numInputs, 1);
		double [][] outputs = ones(a.numStdNeurons + a.numMdlNeurons, 1);
		double [][] inspectedWeights = a.learnStdNeurons(inputs, outputs, numStdNeurons, numMdlNeurons, modulations);
		double o = 1;

		double delta_w = Math.tanh(o/2) * 4;
		assertTrue(1 + delta_w == inspectedWeights[0][0]);
	}

	@Test
	void testInspect_weight() {
		Genotype g = new Genotype(5, 1, 1);
		Animat a = new Animat(g, new Random());
		a.state = zeros(a.numStdNeurons + a.numMdlNeurons, 1);
		a.weights = ones2D(2, 7);
		int numInputs = 5;
		int numNeurons = 2;
		double [][] input = ones2D(numNeurons, numInputs + numNeurons);
		double [][] output = ones2D(numNeurons, numInputs + numNeurons);
		a.state = ones(numNeurons, 1);
		boolean f = assertEual2DArray(a.inspectWeight(input), output);
		assertTrue(f);

		double [][] input2 = {{-11}, {-11},{-11},{-11},{-11},{-11}};
		double [][] output2 = {{-10}, {-10},{-10},{-10},{-10},{-10}};
		boolean f2 = assertEual2DArray(a.inspectWeight(input), output);
		assertTrue(f2);
	}

	@Test
	void testPerform() {
		Genotype g = new Genotype(5, 1, 1);
		Animat a = new Animat(g, new Random());
		a.weights = ones2D(2, 7);
		int numInputs = 5;
		int numStdNeurons = 1;
		int numMdlNeurons = 1;
		a.state[0][0] = 0;
		a.state[1][0] = 0;
		double [][] inputs = ones2D(numInputs, 1);
		double [][] outputs = a.perform(inputs);
		double [][] expected = {{Math.tanh((double)5/2)}, {Math.tanh((double)5/2)}};
		assertTrue(assertEual2DArray(expected, outputs));
	}

	@Test
	void testApply_rule() {
		Genotype g = new Genotype(5, 1, 1);
		Animat a = new Animat(g, new Random());
		a.state = zeros(a.numStdNeurons + a.numMdlNeurons, 1);
		a.weights = ones2D(1, 7);
		int numInputs = 5;
		int numNeurons = 2;
		a.state = ones(numNeurons, 1);
		a.rule[0] = 1;
		a.rule[1] = 1;
		a.rule[2] = 1;
		a.rule[3] = 1;
		a.rule[4] = 1;
		assertTrue(a.applyRule(3, 4, 1) == Math.tanh((double)1/2) * 20);
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
