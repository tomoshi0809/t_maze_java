
public class Util {
	public void show2DArray(double [][] m1){
		for (int i = 0; i < m1.length; i ++) {
			for (int j = 0; j < m1[0].length; j ++) {
				System.out.print(m1[i][j] + " ");
			}
			System.out.println();
		}
	}
}
