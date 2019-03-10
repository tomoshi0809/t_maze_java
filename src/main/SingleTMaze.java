
public class SingleTMaze extends Maze{
	final double MAX_REWARD =  1.0;
	final double MIN_REWARD =  0.1;
	final double PENALTY    = -0.5;
	final int NUM_TRIP = 100;
	final int CYCLE = 10;
	final int REGION =  3;
	static double noiseStd = 0.005;
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

	double trip(Animat animat, double target, boolean can_look) {
		if (!this.home(animat, can_look)) {
			punishment(animat, can_look);
			return this.penalty;
		}

		if (!this.corridor(animat, can_look)) {
			punishment(animat, can_look);
			return this.penalty;
		}

		double turn1 = this.junction(animat, can_look);
		if (!(0 <= turn1 && turn1 < (double)1/3)) {
			punishment(animat, can_look);
			return this.penalty;
		}

		if (!this.corridor(animat, can_look)) {
			punishment(animat, can_look);
			return this.penalty;
		}

		double reward;
		if (Math.abs(turn1)/turn1 == target) {
			reward = this.max_reward;
		} else {
			reward = this.min_reward;
		}
		this.maze_end(animat, reward, can_look);

		if (!this.corridor(animat, can_look)) {
			punishment(animat, can_look);
			return reward + this.penalty;
		}

		double turn2 = this.junction(animat, can_look);
		if (!(turn2 < 0 && turn2 >= (double)-1/3)) {
			punishment(animat, can_look);
			return reward + this.penalty;
		}

		if (!this.corridor(animat, can_look)) {
			punishment(animat, can_look);
			return reward + this.penalty;
		}
		this.home(animat, can_look);
		return reward;
	}

	EvalResult evaluate (Phenotype p) {
		int [] sw = this.switch_points(this.num_trip, this.cycle, this.region);
		double tmp =  Math.random() - 0.5;
		int target = (int)(Math.abs(tmp)/tmp);
		boolean can_look = true;
		int learn_duration = sw [(int)(sw.length / 2)];

		double reward_sum = 0.0;
		Data d = new Data();
		for (int i = 0; i < this.num_trip; i ++) {
			for (int j = 0; j < sw.length; j ++) {
				if (i == learn_duration) {
					can_look = false;
				}
				if (i == sw[j]) {
					target = target * -1;
					break;
				}
				target = target;
			}
			Animat a = (Animat)p;
			double reward = this.trip(a, target, can_look);
			d.pushData(target, reward);
			reward_sum += reward;
		}
		return new EvalResult(reward_sum, d);
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

	boolean isStraight (double output) {
		if (output <= (double)-1/3) {
			return true;
		}
		return false;
	}

	boolean isTurnRight (double output) {
		if (output >= (double)1/3) {
			return true;
		}
		return false;
	}

	boolean isTurnLeft (double output) {
		if (Math.abs(output) < (double)1/3) {
			return true;
		}
		return false;
	}
}

class EvalResult {
	double reward;
	Data data;

	EvalResult (double reward, Data data) {
		this.reward = reward;
		this.data = data;
	}
}
