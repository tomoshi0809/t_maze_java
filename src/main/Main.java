
public class Main {
	static int NUM_GENERATION = 100;
	static int NUM_POPULATION = 300;
	static int NUM_NEURONS = 1;
	static double NOISE_STD = 0.003;
	static int NUM_THINK = 3;
	static int NUM_GROUPS = 5;

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		EvolutionarySearch es = new EvolutionarySearch(SingleTMaze.class, Phenotype.class, NUM_NEURONS, NUM_POPULATION, NUM_GROUPS);
		es.run(1000, true, true);
	}
}
