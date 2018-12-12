
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
}
