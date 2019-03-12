
public class Main {
	static int NUM_GENERATION = 10000;
	static int NUM_POPULATION = 300;
	static int NUM_NEURONS = 2;
	static int NUM_GROUPS = 10;

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		EvolutionarySearch es = new EvolutionarySearch(SingleTMaze.class, Phenotype.class, NUM_NEURONS, NUM_POPULATION, NUM_GROUPS);
		es.run(NUM_GENERATION, true, true);
	}
}
