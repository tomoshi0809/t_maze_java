
public class SingleTMaze extends Maze{
	final double MAX_REWARD =  1.0;
	final double MIN_REWARD =  0.2;
	final double PENALTY    = -0.3;
	final int NUM_TRIP = 100;
	final int CYCLE = 50;
	final int REGION =  30;
	static double noiseStd = 0.001;
	static int numThink = 3;
	double max_reward;
	double min_reward;
	double penalty;
	int num_trip;
	int cycle;
	int region;

	SingleTMaze(){
		super(SingleTMaze.noiseStd, SingleTMaze.numThink);
		this.max_reward = MAX_REWARD;
		this.min_reward = MIN_REWARD;
		this.penalty = PENALTY;
		this.num_trip = NUM_TRIP;
		this.cycle = CYCLE;
		this.region = REGION;

	}

	SingleTMaze(double noiseStd){
		super(noiseStd, SingleTMaze.numThink);
		this.num_trip = NUM_TRIP;
		this.cycle = CYCLE;
		this.region = REGION;
	}

	double trip(Animat animat, double target) {
		if (!this.home(animat)) {
			return this.penalty;
		}

		if (!this.corridor(animat)) {
			return this.penalty;
		}

		double turn1 = this.junction(animat);
		if (Math.abs(turn1) < (double)1/3) {
			return this.penalty;
		}

		if (!this.corridor(animat)) {
			return this.penalty;
		}

		double reward;
		if (Math.abs(turn1)/turn1 == target) {
			reward = this.max_reward;
		} else {
			reward = this.min_reward;
		}
		this.maze_end(animat, reward);

		if (!this.corridor(animat)) {
			return reward + this.penalty;
		}

		double turn2 = this.junction(animat);
		if (Math.abs(turn2) < (double)1/3 || (Math.abs(turn2)/turn2) == (Math.abs(turn1)/turn1)) {
			return reward + this.penalty;
		}

		if (!this.corridor(animat)) {
			return reward + this.penalty;
		}
		this.home(animat);
		return reward;
	}

	double evaluate (Phenotype p) {
		int [] sw = this.switch_points(this.num_trip, this.cycle, this.region);
		double tmp =  Math.random() - 0.5;
		int target = (int)(Math.abs(tmp)/tmp);

		double reward_sum = 0.0;
		for (int i = 0; i < this.num_trip; i ++) {
			for (int j = 0; j < sw.length; j ++) {
				if (i == sw[j]) {
					target = target * -1;
					break;
				}
				target = target;
			}
			Animat a = (Animat)p;
			reward_sum += this.trip(a, target);
		}
		return reward_sum;
	}

	int [] switch_points(int num_trip, int cycle, int region) {
		int length = num_trip / cycle -1;
		int [] ret = new int[length];
		for (int i = 0; i < length; i ++ ) {
			int tmp = cycle * (i + 1);
			ret[i] = tmp + (int)(Math.random() * region - region / 2);
		}
		return ret;
	}
}
