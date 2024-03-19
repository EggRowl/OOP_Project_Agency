public class Strategy implements Comparable<Strategy>{
	
	protected int ID;
	protected String codeName;
	protected int opLevel;
	protected int customer;
	protected int ETC; // Estimated Time of Completion
	protected int arrivalTime;
	
	public Strategy(int ID, String codeName, int opLevel, int customer, int ETC, int arrivalTime) {
		this.ID = ID;
		this.codeName = codeName;
		this.opLevel = opLevel;
		this.customer = customer;
		this.ETC = ETC;
		this.arrivalTime = arrivalTime;
		
	}
	public int getID(){
		return this.ID;
	}
	public int getETC() {
		return this.ETC;
	}
	public int getOpLevel() {
		return this.opLevel;
	}
	public String getCodeName() {
		return this.codeName;
	}
	public int getArrivalTime() {
		return this.arrivalTime;
	}
	@Override
	public int compareTo(Strategy s) {
		if(this.ETC > s.getETC())
			return 1;
		else if(this.ETC == s.getETC())
			return 0;
		else
			return -1;
	}
}
