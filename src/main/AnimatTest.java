import org.junit.jupiter.api.Test;

class AnimatTest {
	@Test
	void testBehave() {
		Genotype g = new Genotype(5, 2);
		Animat a = new Animat(g);
		
		a.state = zeros(a.numNeurons);
	}
	
	double [] ones (int len) {
		double [] ret = new double[len];
		for (int i = 0; i < len; i ++) {
			ret[i] = 1;
		}
		return ret;
	}
	
	double [] zeros (int len) {
		double [] ret = new double[len];
		for (int i = 0; i < len; i ++) {
			ret[i] = 0;
		}
		return ret;
	}
}
