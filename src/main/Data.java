public class Data {
	private double aveRewRight;
	private double aveRewLeft;
	private int countTrialRight;
	private int countTrialLeft;
	private double rewRight;
	private double rewLeft;
	
	
	Data(){
		this.aveRewRight = 0;
		this.aveRewLeft = 0;
		this.countTrialRight = 0;
		this.countTrialLeft = 0;
		this.rewRight = 0;
		this.rewLeft = 0;
	}
	
	void pushData(int target, double reward) {
		if (target == 1) {	//TODO: this hard-coded part should be updated.
			this.countTrialRight ++;
			this.rewRight += reward;
		} else {
			this.countTrialLeft ++;
			this.rewLeft += reward;
		}
	}
	
	double getAveRewLeft () {
		this.aveRewLeft = (double) this.rewLeft / this.countTrialLeft;
		return this.aveRewLeft;
	}
	
	double getAveRewRight() {
		this.aveRewRight = (double) this.rewRight / this.countTrialRight;
		return this.aveRewRight;
	}
}
