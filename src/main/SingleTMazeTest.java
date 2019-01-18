import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;


class Mock extends Animat {
	int count;
	int num_think;
	double out;
	double [][] cmp;
	boolean flag;
	double [] action;

	Mock (Genotype g, Random r){
		super(g, r);
		this.count = 0;
		this.out = 0.0;
		this.cmp = zeros(5, 1);
		this.flag = false;
		this.num_think = 3;
		this.action = new double[9];
		this.action[0] = 0.0;
		this.action[1] = 0.0;
		this.action[2] = 1.0;
		this.action[3] = 0.0;
		this.action[4] = 0.0;
		this.action[5] = 0.0;
		this.action[6] = -1.0;
		this.action[7] = 0.0;
		this.action[8] = 0.0;
	}

	/*
	double [][] perform (double [][] inputs){
		this.count += 1;
		this.flag = assertEual2DArray(inputs, this.cmp);
		double [][] tmp = {{this.out}};
		return Matrix.concatenate(tmp, inputs, 1);
	}
	*/

	double [][] perform (double [][] inputs){
		double [][] ret = new double[1][1];
		ret[0][0] = this.action[(int)this.count/this.num_think];
		this.count += 1;
		this.count = this.count % (this.action.length * this.num_think);
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

class SingleTMazeTest {
	/*
	@Test
	void testHome() {
		SingleTMaze s = new SingleTMaze(0.0);
		Genotype g = new Genotype(5, 2);
		Mock animat = new Mock(g);
		animat.count = 0;
		animat.out = 0.0;
		double [][] a = {{1.0}, {0.0}, {0.0}, {0.0}, {1.0}};
		animat.cmp = a;
		boolean f = s.home(animat);
		assertTrue(f);
		animat.out = 1.0;
		f = s.home(animat);
		assertFalse(f);
		animat.out = -1.0;
		f = s.home(animat);
		assertFalse(f);
		f = (animat.count == 9);
		assertTrue(f);
		assertTrue(animat.flag);
	}

	@Test
	void testCorridor() {
		SingleTMaze s = new SingleTMaze(0.0);
		Genotype g = new Genotype(5, 2);
		Mock animat = new Mock(g);
		animat.count = 0;
		animat.out = 0.0;
		double [][] a = {{0.0}, {0.0}, {0.0}, {0.0}, {1.0}};
		animat.cmp = a;
		boolean f = s.corridor(animat);
		assertTrue(f);
		animat.out = 1.0;
		f = s.corridor(animat);
		assertFalse(f);
		animat.out = -1.0;
		f = s.corridor(animat);
		assertFalse(f);
		f = (animat.count == 9);
		assertTrue(f);
		assertTrue(animat.flag);
	}

	@Test
	void testJunction() {
		SingleTMaze s = new SingleTMaze(0.0);
		Genotype g = new Genotype(5, 2);
		Mock animat = new Mock(g);
		animat.count = 0;
		animat.out = 0.0;
		double [][] a = {{0.0}, {1.0}, {0.0}, {0.0}, {1.0}};
		animat.cmp = a;
		boolean f = (s.junction(animat) == 0.0);
		assertTrue(f);
		animat.out = 1.0;
		f = (s.junction(animat) == 1.0);
		assertTrue(f);
		animat.out = -1.0;
		f = (s.junction(animat) == -1.0);
		assertTrue(f);
		f = (animat.count == 9);
		assertTrue(f);
		assertTrue(animat.flag);
	}

	@Test
	void testMaze_End() {
		SingleTMaze s = new SingleTMaze(0.0);
		Genotype g = new Genotype(5, 2);
		Mock animat = new Mock(g);
		animat.count = 0;
		animat.out = 0.0;
		double [][] a = {{0.0}, {0.0}, {1.0}, {0.5}, {1.0}};
		animat.cmp = a;
		boolean f = (s.maze_end(animat, 0.5) == 0.0);
		assertTrue(f);
		f = (animat.count == 3);
		assertTrue(f);
		assertTrue(animat.flag);
		double [][] b = {{0.0}, {1.0}, {0.0}, {0.5}, {1.0}};
		animat.cmp = b;
		f = (s.maze_end(animat, 0.0) == 0.0);
		assertTrue(f);
		assertFalse(animat.flag);
	}
	*/
	/*
	@Test
	void testTrip() {
		SingleTMaze m = new SingleTMaze(0.0);
		Genotype g = new Genotype(5, 2);
		Mock animat = new Mock(g, new Random());

		assertTrue(m.trip(animat, 1.0) == m.max_reward);
		assertTrue(m.trip(animat, -1.0) == m.min_reward);

		double min_region = (double)(m.num_trip - m.region) / 2;
		double max_region = (double)(m.num_trip + m.region) / 2;
		double min_reward_sum = min_region * m.max_reward + max_region * m.min_reward;
		double max_reward_sum = min_region * m.min_reward + max_region * m.max_reward;

		double [] array = new double[100];
		for (int i = 0; i < 100; i ++) {
			array[i] = m.evaluate(animat);
		}
		assertTrue(min(array) >= min_reward_sum);
		assertTrue(max(array) <= max_reward_sum);

		int num_trip = 150;
		int cycle = 50;
		int region = 20;

		int [][]array2 = new int[100][2];
		for (int i = 0; i < 100; i ++) {
			array2[i] = m.switch_points(num_trip, cycle, region);
		}
	}
	*/

	double min(double [] array) {
		double min = array[0];
		for ( int i = 1; i < array.length; i ++) {
			if (array[i] < min) {
				min = array[i];
			}
		}
		return min;
	}

	double max(double [] array) {
		double max = array[0];
		for ( int i = 1; i < array.length; i ++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		return max;
	}
}
