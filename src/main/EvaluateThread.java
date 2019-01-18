import java.util.Random;

public class EvaluateThread extends Thread {
	Genotype [] unit;
	Environment env;
	int numEval;
	Random rand;
	
	EvaluateThread(Environment env, Genotype [] g, int numEval, Random r){
		this.env = env;
		this.unit = g;
		this.numEval = numEval;
		this.rand = r;
	}
	
	public void run() {
		for (Genotype g : this.unit) {
			double sum = 0;
			for (int i = 0; i < numEval; i ++) {
				EvalResult er = this.env.evaluate(new Animat(g, this.rand));
				sum +=  er.reward;
				g.data = er.data;
			}
			g.fitness = sum / numEval;
		}
	}
}
