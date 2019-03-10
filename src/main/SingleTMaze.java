
public class SingleTMaze extends Maze{
	final double MAX_REWARD =  1.0;
	final double MIN_REWARD =  0.1;
	final double PENALTY    = -0.5;
	final int NUM_TRIP = 100;
	final int CYCLE = 10;
	final int REGION =  3;
	static double noiseStd = 0.005;
	static int numThink = 3;
	double maxReward;
	double minReward;
	double penalty;
	int numTrip;
	int cycle;
	int region;

	SingleTMaze(){
		super(SingleTMaze.noiseStd, SingleTMaze.numThink);
		this.maxReward = MAX_REWARD;
		this.minReward = MIN_REWARD;
		this.penalty = PENALTY;
		this.numTrip = NUM_TRIP;
		this.cycle = CYCLE;
		this.region = REGION;

	}

	SingleTMaze(double noiseStd){
		super(noiseStd, SingleTMaze.numThink);
		this.numTrip = NUM_TRIP;
		this.cycle = CYCLE;
		this.region = REGION;
	}

	double trip(Animat animat, double target, int num_ver_cor, int num_hor_cor, boolean can_look) {
		if (!this.home(animat, can_look)) {
			punishment(animat, can_look);
			return this.penalty;
		}

		for (int i = 0; i < num_ver_cor; i ++) {
			if (!this.corridor(animat, can_look)) {
				punishment(animat, can_look);
				return this.penalty;
			}
		}

		double turn1 = this.junction(animat, can_look);
		if (!(turn1 >= (double)1/3)) {
			punishment(animat, can_look);
			return this.penalty;
		}

		for (int i = 0; i < num_hor_cor; i ++) {
			if (!this.corridor(animat, can_look)) {
				punishment(animat, can_look);
				return this.penalty;
			}
		}
		this.mazeEnd(animat, 1, can_look);

		double reward = this.MAX_REWARD;
		for (int i = 0; i < num_hor_cor; i ++) {
			if (!this.corridor(animat, can_look)) {
				punishment(animat, can_look);
				return reward + this.penalty;
			}
		}

		double turn2 = this.junction(animat, can_look);
		if (!(turn2 < (double)-1/3)) {
			punishment(animat, can_look);
			return reward + this.penalty;
		}

		for (int i = 0; i < num_ver_cor; i ++) {
			if (!this.corridor(animat, can_look)) {
				punishment(animat, can_look);
				return reward + this.penalty;
			}
		}
		this.home(animat, can_look);
		return reward;
	}

	EvalResult evaluate (Phenotype p, int num_ver_cor, int num_hor_cor) {
		boolean can_look = true;
		int learn_duration = this.NUM_TRIP / 2;

		double reward_sum = 0.0;
		Data d = new Data();
		Animat a = (Animat)p;
		for (int i = 0; i < this.numTrip; i ++) {
			if (i == learn_duration) {
				can_look = true;
			}
			double reward = this.trip(a, 1, num_ver_cor, num_hor_cor, can_look);
			d.pushData(1, reward);
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
