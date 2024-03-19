import java.util.Vector;

import Interfaces.NotHomeless;

public class Executive implements NotHomeless, Runnable{
	protected AgencyManager manager;
	protected InformationSystem infos;
	protected static Vector<Executive> allExec = new Vector<>();
	protected String name;
	protected BoundedQueue<Operation> opQ;
	Operation op;
	Vector<Operation> finishedOps;
	
	public Executive(String name, BoundedQueue<Operation> opQ, InformationSystem infos, Vector<Operation> finishedOps, AgencyManager manager) {
		this.name = name;
		this.opQ = opQ;
		this.infos = infos;
		this.finishedOps = finishedOps;
		this.manager = manager;
	}
	
	
	@Override
	public void run() {
		System.out.println("executive "+ this.name +" has entered the office");
		while(AgencyManager.canIGoHome() == false) {
			//1) take operation
			this.op = this.opQ.extract();
		//2) proceed with operation
			if(op.getCodeName() != null)
				System.out.println(this.name + " started the operation" + op.getCodeName());
			this.beginOp();
		//3) end Op
			this.returnFromOp();
			System.out.println("Executive " + this.name + " returned from operation " + op.getCodeName());
		}
		System.out.println("Executive " + this.name + " has finished the day");
	}
	protected void beginOp() {
		double time = ((this.op.getNumOfAg() + this.op.getNumOfVe())*(2 + Math.random()*6))*1000;
		 try {
			Thread.sleep((int) time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void returnFromOp() {
		/////// return Assets
		this.infos.returnAssets(op.getOpDet(), op.getOpInv(), op.getOpCar(), op.getOpMotor());
		////// need to update manager
		manager.missionReport(op);
	}
	protected int calcOpTime() {
		int opTime = 0;
		
		return opTime;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}
	@Override
	public void goHome() {
		this.notifyAll();
	}
	public static void sendHome() {
		for(int i = 0; i < allExec.size(); i ++) {
			allExec.get(i).goHome();
		}
	}
}
