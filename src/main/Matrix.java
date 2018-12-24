
public class Matrix {
	public static double [][] dot (double [][] m1, double [][] m2){
		if (m1[0].length != m2.length) {
			System.err.println("dot() - Matrix size is not appropriate");
			System.exit(1);
		}

		int nrow = m1.length;
		int ncol = m2[0].length;

		double [][] ret = new double [nrow][ncol];
		for (int i = 0; i < nrow; i ++) {
			for (int j = 0; j < ncol; j ++) {
				double sum = 0;
				for (int k = 0; k < m2.length; k ++) {
					sum += m1[i][k] * m2[k][j];
				}
				ret[i][j] = sum;
			}
		}
		return ret;
	}

	public static double [][] scalar (double s, double [][] m1){
		int nrow = m1.length;
		int ncol = m1[0].length;
		double [][] ret = new double [nrow][ncol];
		for (int i = 0; i < nrow; i ++) {
			for (int j = 0; j < ncol; j ++) {
				ret[i][j] = s * m1[i][j];
			}
		}
		return ret;
	}

	public static double [][] add(double [][] m1, double [][] m2){
		if ((m1.length != m2.length) || (m1[0].length != m2[0].length)) {
			System.err.println("add() - Matrix size is not appropriate");
			System.exit(1);
		}
		int nrow = m1.length;
		int ncol = m1[0].length;
		double [][] ret = new double [nrow][ncol];
		for (int i = 0; i < nrow; i ++) {
			for (int j = 0; j < ncol; j ++) {
				ret[i][j] = m1[i][j] + m2[i][j];
			}
		}
		return ret;
	}

	public static double [][] tanh(double [][]m1){
		int nrow = m1.length;
		int ncol = m1[0].length;
		double [][] ret = new double [nrow][ncol];
		for (int i = 0; i < nrow; i ++) {
			for (int j = 0; j < ncol; j ++) {
				ret[i][j] = Math.tanh(m1[i][j]);
			}
		}
		return ret;
	}

	public static double [][] concatenate(double [][] m1, double [][] m2, int axis) {
		if (axis == 0) {
			int ncol = m1[0].length + m2[0].length;
			int nrow = m1.length;
			if (m1.length != m2.length) {
				System.err.println("Matrix - m1.length != m2.length in concanate");
			}
			double [][] ret = new double[nrow][ncol];
			for (int i = 0; i < nrow; i++) {
				for (int j = 0; j < m1[0].length; j ++) {
					ret[i][j] = m1[i][j];
				}
				for (int j = m1[0].length; j < ncol; j ++) {
					ret[i][j] = m2[i][j - m1[0].length];
				}
			}
			return ret;
		} else if (axis == 1) {
			int ncol = m1[0].length;
			int nrow = m1.length + m2.length;
			if (m1[0].length != m2[0].length) {
				System.err.println("Matrix - m1[0].length != m2[0].length in concanate");
			}
			double [][] ret = new double[nrow][ncol];
			for (int i = 0; i < ncol; i++) {
				for (int j = 0; j < m1.length; j ++) {
					ret[j][i] = m1[j][i];
				}
				for (int j = m1.length; j < nrow; j ++) {
					ret[j][i] = m2[j - m1.length][i];
				}
			}
			return ret;
		} else {
			System.err.println("Matrix - concatenate does not suppot the axis:" + axis);
		}
		return null;
	}

	public static double [] slice (double [] array, int start, int end) {
		double [] ret = new double [Math.min(end, array.length) - start];
		for (int i = start; i < Math.min(end, array.length); i ++) {
			ret[i - start] = array[i];
		}
		return ret;
	}
}
