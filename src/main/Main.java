
public class Main {
	static int NUM_GENERATION = 20000;
	static int NUM_POPULATION = 300;
	static int NUM_STD_NEURONS = 1;
	static int NUM_MDL_NEURONS = 1;
	static int NUM_GROUP_MEMBER = 5;

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		EvolutionarySearch es = new EvolutionarySearch(SingleTMaze.class, Phenotype.class, NUM_STD_NEURONS, NUM_MDL_NEURONS, NUM_POPULATION, NUM_GROUP_MEMBER);
		es.run(NUM_GENERATION, true, true);
	}
}
