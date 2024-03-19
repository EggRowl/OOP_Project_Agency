
public class Call implements Runnable{
	
	protected String suspectName;
	protected String service;
	protected int customer;
	protected int callArrivalDelay;
	protected int callDuration;
	protected int arrivalTime;
	private Queue<Call> callQ;
	protected boolean inCall;
	
	public Call(String suspectName, String service, int customer, int callArrivalDelay, int callDUration, Queue<Call> callQ) {
		this.suspectName = suspectName;
		this.service = service;
		this.customer = customer;
		this.callArrivalDelay = callArrivalDelay;
		this.callDuration = callDuration*1000; // duration times 1000 milisec
		this.callQ = callQ;
	}
	
	@Override
	public void run() {
		this.dialing();
		this.inCall = true;
		this.forwardCall();
		this.waitForEnd();
	}
	/**
	 * inserts the call into the Call queue
	 * @return
	 */
	private synchronized void forwardCall() { 
		callQ.insert(this);
	}
	/**
	 * the time it takes for the call to connect.
	 */
	private void dialing() {
		try {
			Thread.sleep(callArrivalDelay*1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	public synchronized void waitForEnd() {
		while(this.inCall == true) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * wakes the call up in order to finish.
	 */
	public synchronized void endCall() {
		this.inCall = false;
		System.out.println("call " + this.suspectName + " has ended");
		this.notifyAll();
	}
	///////////////////////////getters/////////////////////////////
	public String getNameOfInterrogee() {
		return this.suspectName;
	}
	public String getCustomerRequest() {
		return this.service;
	}
	public int getCustomer() {
		return this.customer;
	}
	public int getCallDuration() {
		return this.callDuration;
	}
	public int getArrivalDelay() {
		return this.callArrivalDelay;
	}
	public int getArrivalTime() {
		return this.arrivalTime;
	}
	public String toString() {
		return "interogee name: " + this.suspectName + 
				"\nservice requested: " + this.service +
				"\nclient type: " + this.customer;
	}
}
