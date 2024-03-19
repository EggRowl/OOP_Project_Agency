import java.util.Vector;

import Functions.ObjectByNaturalComp;

public class InformationSystem {
	protected Vector<Strategy> stratList = new Vector<>();
	protected int Detectives = 60;
	protected int Investigators = 40;
	protected int Cars = 10;
	protected int Motorcycles = 50;
	protected ObjectByNaturalComp<Strategy> stratComp = new ObjectByNaturalComp<>();
	public InformationSystem() {
		
	}
	public synchronized void insertStrategy(Strategy s) {
		this.stratList.add(s);
		this.stratList.sort(stratComp);
		this.notifyAll();
		
	}
	public synchronized Strategy retrieveStrategy() {
		while(stratList.size() == 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return stratList.remove(0);
	}
	/**
	 * returns the assets to the operator
	 * @param assets {dets, invs, motos, cars}
	 * @param opSeats
	 * @return
	 */
	public synchronized int[] getAssets(int[] assets, int opSeats) { 
		while(enoughAgents(assets[0],assets[1]) && enoughVehicles(opSeats))
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		int dets = this.getDet(assets[0]);
		int invs = this.getInv(assets[1]);
		int motors = this.getMotor(opSeats);
		int cars = this.getCar(opSeats - motors);
		
		int[] retAssets = {dets, invs, motors,cars};
		return retAssets;
	}
	protected boolean enoughAgents(int det, int inv) {
		if(Investigators >= inv && Detectives >= det)
			return true;
		else
			return false;
	}
	protected boolean enoughVehicles(int opSeats) {
		if(opSeats >= getTotalSeats())
			return true;
		else
			return false;
	}
	protected int getTotalSeats() {
		return Motorcycles + Cars*5;
	}
	protected int getInv(int amount) {
		Investigators = Investigators - amount;
		return amount;
	}
	protected int getDet(int amount) {
		Detectives = Detectives - amount;
		return amount;
	}
	protected int getMotor(int opSeats) {
		if(Motorcycles >= opSeats) {
			Motorcycles = Motorcycles - opSeats;
			return opSeats;
		}
		else {
			opSeats = Motorcycles;
			Motorcycles = 0;
			return opSeats;
		}
	}
	/**
	 * if the remainder of the total seats needed/5 is 0 assign total seats neede/5 cars 
	 * else assign the total seats needed/5 + 1 ( to be able to seat the remaining agents)
	 * @param seats
	 * @return number of cars assigned to operation
	 */
	protected int getCar(int seats) {
		if(seats % 5 == 0) {
			Cars = Cars - seats/5;
			return seats/5;
		}
		else {
			Cars = Cars - (seats/5 + 1);
			return seats/5 + 1;
		}
	}
	//////////////////////////////// Executive stuff  ////////////////////////////
	public synchronized void returnAssets(int det, int inv, int car, int moto) {
		Detectives = Detectives + det;
		Investigators = Investigators + inv;
		Cars = Cars + car;
		Motorcycles = Motorcycles + moto;
		this.notifyAll();
		// need to notify
	}
	public synchronized void returnDetectives(int amount) {
		Detectives = Detectives + amount;
		this.notifyAll();
	}
	public synchronized void returnInvestigators(int amount) {
		Investigators = Investigators + amount;
		this.notifyAll();
	}
	public synchronized void returnCars(int amount) {
		Cars = Cars + amount;
		this.notifyAll();
	}
	public synchronized void returnMotorcycles(int amount) {
		Motorcycles = Motorcycles + amount;
		this.notifyAll();
	}
	///////////////////////////////////////////////////
	
}
