import java.util.Vector;

import Interfaces.NotHomeless;

public class Secretary implements NotHomeless, Runnable {
	
	protected static Vector<Secretary> allSec = new Vector<>();
	protected static boolean working;
	protected static final int MaxSecretary = 5;
	protected static int secCounter = 0;
	protected String name;
	protected Queue<Call> callQ;
	protected Queue<Task> taskQ;
	protected Call c; // current call being worked on
	protected Task t; // current task
	
	
	public Secretary(String name, Queue<Call> callQ, Queue<Task> taskQ) {
		
		this.name = name;
		this.callQ = callQ;
		this.taskQ = taskQ;
		allSec.add(this);
	}

	@Override
	public void run() {
		System.out.println("secretary " + this.name + " has entered the office");
		while(CallSpammer.isSpamming(0) || callQ.isEmpty() == false) {//
			//// 1) wait for call to arive
			//// 2) answer call
			this.c = callQ.extract();
			if(c == null) {
				callQ.insert(c);
				break;
			}
			//// 3) work on the call
			this.work();
			/// 4) create the task
			this.createTask();
			/// 5) forward the task to the task queue
			this.insertTask();
			System.out.println("secretary: " + this.name + " inserted task: " + t.getId());
			/// 6) end the call with c.endCall
			this.c.endCall();
			/// 7) go back to start (1)
		}
		System.out.println("Secretary: " + this.name + " has finished the day");
	}
	protected synchronized void waitForCall() {
		while(callQ.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	protected synchronized Call answerCall() {
		this.c = this.callQ.extract();
		return this.c;
	}
	protected void work() {
		try {	
			if(this.c.getCustomer() == 1)
				Thread.sleep((long) (this.c.getCallDuration() + 500 + Math.random()*500));
			else if(this.c.getCustomer() == 2)
					Thread.sleep((long) (this.c.getCallDuration() + 1000 + Math.random()*1000));
			else if(this.c.getCustomer() == 3){
					Thread.sleep((long) (this.c.getCallDuration() + 2000 + Math.random()*1000));
				}
		} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	protected void createTask() {
		t = new Task(this.c.getNameOfInterrogee(), this.c.getCustomerRequest(), this.c.getCustomer(), this.c.getArrivalTime());
		
		
	}
	protected synchronized void insertTask() {
		taskQ.insert(this.t);
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void goHome() {
		for(int i = 0; i < allSec.size(); i ++) {
			allSec.get(i).notifyAll();
		}
	}
}
