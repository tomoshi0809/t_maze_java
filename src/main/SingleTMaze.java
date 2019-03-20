
public class SingleTMaze extends Maze{
	final int NUM_TRIP = 100;
	final int CYCLE = 10;
	final int REGION =  3;
	final double MIN_REWARD = -1.0;
	final double MAX_REWARD = 1.0;
	static double noiseStd = 0.001;
	static int numThink = 3;
	static double rewardSlope;
	int numTrip;
	int cycle;
	int region;

	SingleTMaze(){
		super(SingleTMaze.noiseStd, SingleTMaze.numThink);
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

	double getReward(double walkcntr) {
		double reward = this.MIN_REWARD + walkcntr * this.rewardSlope;
		return reward;
	}

	double trip(Animat animat, double target, int numVerCor, int numHorCor, boolean canLook) {
		this.rewardSlope = (double)(this.MAX_REWARD - this.MIN_REWARD) / (2 * (2 + numVerCor + numHorCor));
		int walkcntr = 0;

		if (!this.home(animat, canLook)) {
			punishment(animat, canLook);
			return getReward(walkcntr);
		}
		walkcntr ++;

		for (int i = 0; i < numVerCor; i ++) {
			if (!this.corridor(animat, canLook)) {
				punishment(animat, canLook);
				return getReward(walkcntr);
			}
			walkcntr ++;
		}

		double turn1 = this.junction(animat, canLook);
		if (!(turn1 >= (double)1/3)) {
			punishment(animat, canLook);
			return getReward(walkcntr);
		}
		walkcntr ++;

		for (int i = 0; i < numHorCor; i ++) {
			if (!this.corridor(animat, canLook)) {
				punishment(animat, canLook);
				return getReward(walkcntr);
			}
			walkcntr ++;
		}

		this.mazeEnd(animat, 1, canLook);
		walkcntr ++;

		double reward = this.MAX_REWARD;
		for (int i = 0; i < numHorCor; i ++) {
			if (!this.corridor(animat, canLook)) {
				punishment(animat, canLook);
				return getReward(walkcntr);
			}
			walkcntr ++;
		}

		double turn2 = this.junction(animat, canLook);
		if (!(turn2 < (double)-1/3)) {
			punishment(animat, canLook);
			return getReward(walkcntr);
		}
		walkcntr ++;

		for (int i = 0; i < numVerCor; i ++) {
			if (!this.corridor(animat, canLook)) {
				punishment(animat, canLook);
				return getReward(walkcntr);
			}
			walkcntr ++;
		}
		this.home(animat, canLook);
		return getReward(walkcntr);
	}

	EvalResult evaluate (Phenotype p, int numVerCor, int numHorCor) {
		boolean canLook = true;
		int learnDuration = this.NUM_TRIP / 2;

		double rewardSum = 0.0;
		Data d = new Data();
		Animat a = (Animat)p;
		for (int i = 0; i < this.numTrip; i ++) {
			if (i == learnDuration) {
				canLook = false;
			}
			double reward = this.trip(a, 1, numVerCor, numHorCor, canLook);
			d.pushData(1, reward);
			rewardSum += reward;
		}
		return new EvalResult(rewardSum, d);
	}

	int [] switchPoints(int numTrip, int cycle, int region) {
		int length = numTrip / cycle -1;
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
