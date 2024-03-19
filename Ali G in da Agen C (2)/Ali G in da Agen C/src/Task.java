
public class Task implements Runnable{
	
	protected static int IDCounter = 0;
	protected int ID;
	protected String suspectName;
	protected String request;
	protected int customer;
	protected int callTimeArrival;
	
	public Task(String suspectName, String request, int customer, int callTimeArrival) {
		this.suspectName = suspectName;
		this.request = request;
		this.customer = customer;
		this.callTimeArrival = callTimeArrival;
		this.setId();
	}

	@Override
	public void run() {
		 
		
	}
	/**
	 * sets the ID for the task.
	 */
	protected synchronized void setId() {
		IDCounter++;
		this.ID = IDCounter;
	}
	public int getCustomer() {
		return customer;
	}
	public String getSuspectName() {
		return this.suspectName;
	}
	public String getCustomerRequest() {
		return this.request;
	}
	public int getId() {
		return this.ID;
	}

	public int getArrivalTime() {
		return this.callTimeArrival;
	}
	
	
	
	
}
