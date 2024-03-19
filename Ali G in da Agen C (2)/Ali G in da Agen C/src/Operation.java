
public class Operation {
	
	public int getOpDet() {
		return opDet;
	}
	public int getOpInv() {
		return opInv;
	}
	public int getOpMotor() {
		return opMotor;
	}
	public int getOpCar() {
		return opCar;
	}

	protected int ID;
	protected String codeName;
	protected int opLevel;
	protected int ETC;
	protected int arrivalTime;
	protected int numOfAgents;
	protected int numOfVehicles;
	protected int opDet;
	protected int opInv;
	protected int opMotor;
	protected int opCar;
	
	
	public Operation(int ID, String codeName, int opLevel, int ETC, int arrivalTime, int opDet, int opInv, int opMotor, int opCar) {
		this.ID = ID;
		this.codeName = codeName;
		this.opLevel = opLevel;
		this.ETC = ETC;
		this.arrivalTime = arrivalTime;
		this.opDet = opDet;
		this.opInv = opInv;
		this.opMotor = opMotor;
		this.opCar = opCar;
		
		this.numOfAgents = opDet + opInv;
		this.numOfVehicles = opMotor + opCar;
	}
	
	public String getCodeName() {
		return this.codeName;
	}
	public int getOpLevel() {
		return this.opLevel;
	}
	public int getEtc() {
		return this.ETC;
	}
	public int getNumOfAg() {
		return this.numOfAgents;
	}
	public int getNumOfVe() {
		return this.numOfVehicles;
	}
	public String toString() {
		return "Operation code name: " + this.codeName + "\n"
				+ "estimated time of completion: " + this.ETC;
		
	}
}
