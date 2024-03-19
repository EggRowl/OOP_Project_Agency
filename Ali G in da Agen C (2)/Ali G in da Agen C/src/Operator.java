import java.util.Vector;

import Interfaces.NotHomeless;

public class Operator implements NotHomeless, Runnable{
	InformationSystem infos;
	protected static final int maxOperators = 3; 
	protected static double workTime = 1; /// gui thingy
	protected static Vector<Operator> allOp = new Vector<>();
	protected String name;
	protected BoundedQueue<Operation> opQ;
	protected Strategy s;
	protected Operation op;
	
	
	protected int det;
	protected int inv;
	protected int cars;
	protected int motors;
	protected int opSeats;
	
	public Operator(String name, BoundedQueue<Operation> opQ, InformationSystem infos) {
		this.name = name;
		this.opQ = opQ;
		this.infos = infos;
	}
	
	
	
	@Override
	public void run() {
		System.out.println("operator " + this.name + " has entered the office");
		//0) wait for Strategy or end of day
		while(AgencyManager.canIGoHome() == false) {

		//1) grab strategy
			this.takeStrat();
		//2) work // (GUI input)
			this.work(); // still need to do stuff with it
		//3) convert to operation
			this.createOperation();
		//4) insert to operationQueue
			this.opQ.insert(this.op);
			if(this.op.getCodeName() != null) {
				System.out.println("operator " + this.name + "  inserted operation " + op.getCodeName() + " ID: " + this.op.getCodeName());
			}

		}
		System.out.println("Operator: " + this.name + " has finished the day");
	}
	
	protected synchronized Strategy takeStrat() {
		while((this.s = infos.retrieveStrategy()) == null){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
		return this.s;
	}
	protected void work() {
		try {
			Thread.sleep((long) workTime*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	protected void createOperation() {
		int dets = 0, invs = 0, motos = 0, cars = 0;
		this.calcManPower();
		int[] assets = {dets, invs, motos, cars};
		int[] retAssets = infos.getAssets(assets, this.opSeats);
		dets = retAssets[0];
		invs = retAssets[1];
		motos = retAssets[2];
		cars = retAssets[3];

		this.op = new Operation(s.getID(), s.getCodeName(), s.getOpLevel(), s.getETC(), s.getArrivalTime(), dets, invs, motos, cars);
	}
	/**
	 * calculates the amount of agents/vehicles needed
	 */
	protected void calcManPower() {
		int opLevel = s.getOpLevel();
		if(opLevel == 1) {
			this.opSeats = 2;
			this.inv = 2;
		}
		else if(opLevel == 2) {
			this.opSeats = 5;
			this.inv = 3;
			this.det = 2;
		}
		else if(opLevel == 3) {
			this.opSeats = 6;
			this.inv = 1;
			this.det = 5;
		}
		else if(opLevel == 4) {
			this.opSeats = 10;
			this.inv = 4;
			this.det = 6;
		}
		else if(opLevel == 5) {
			this.opSeats = 15;
			this.inv = 7;
			this.det = 8;
		}
	}
	public static void setWorkTime(double time) {
		workTime = time;
	}
	@Override
	public String getName() {
		return this.name;
	}
	@Override
	public void goHome() {
		this.notify();		
	}
	public static void sendHome() {
		for(int i = 0; i < allOp.size(); i ++) {
			allOp.get(i).goHome();
		}
	}
	public static Vector<Operator> allOperators(){
		return allOp;
	}
	
}
