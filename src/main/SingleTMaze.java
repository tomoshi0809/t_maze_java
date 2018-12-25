
public class SingleTMaze extends Maze{
	final double MAX_REWARD =  1.0;
	final double MIN_REWARD =  0.2;
	final double PENALTY    = -0.3;
	final int NUM_TRIP = 100;
	final int CYCLE = 50;
	final int REGION =  30;
	static double noiseStd = 0.001;
	static int numThink = 3;

	SingleTMaze(){
		super(SingleTMaze.noiseStd, SingleTMaze.numThink);
	}

	SingleTMaze(double noiseStd){
		super(noiseStd, SingleTMaze.numThink);
	}

	public int evaluate (Phenotype p) {
		return 0;
	}
}
