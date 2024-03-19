
import java.util.Vector;

import Exceptions.TooMuchManagerException;
import Interfaces.NotHomeless;

public class AgencyManager implements NotHomeless, Runnable{

	protected Vector<Vector<Thread>> allEmps = new Vector<>();	
	protected Queue<Task> taskQ;
	protected Queue<Operation> opQ;
	protected InformationSystem infos;
	protected static boolean dayOver = false;
	protected static int dailyQuota = 10;
	protected static int opFinishedToday;
	protected Vector<Operation> finishedOps;
	protected String name;
	protected static BoundedQueue<AgencyManager> manager = new BoundedQueue<>(1);
	
	public AgencyManager(String name, Vector<Vector<Thread>> allEmps, Queue<Task> taskQ, Queue<Operation> opQ, InformationSystem infos
			, Vector<Operation> finishedOps) throws TooMuchManagerException {
		if(manager.size() == 0) {
		this.name = name;
		this.taskQ = taskQ;
		this.opQ = opQ;
		this.infos = infos;
		this.finishedOps = finishedOps;
		manager.insert(this);
		}
		else {
			throw new TooMuchManagerException();
		}
	}
	public void run() {
		System.out.println("Hear me now. yous started da day and me expecting eclesence");
		// while daily quota not reached  wait.
		workdayHappening();
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\nday over");
		sendHome();
		killThreads();
		System.out.println("active threads after everybody but the secretaries went home: " + Thread.activeCount());

		
	}
	protected void workdayStart() {
		
	}
	protected synchronized void workdayHappening() {
		while(finishedOps.size() < dailyQuota) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		dayOver = true;
	}
	/**
	 * at the end of the day waits for all the employees to finish
	 */
	private void killThreads() {
		for(int i = 0; i < allEmps.size(); i++) {
			for(int j = 0; j < allEmps.elementAt(i).size(); j++) {
				try {
					allEmps.elementAt(i).elementAt(j).join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static boolean canIGoHome() {
		return dayOver;
	}
	/**
	 * sets the quota for the amount of missions to carry out before the day ends.
	 * @param todaysMissions
	 */
	public void setQuoata(int todaysMissions) {
		dailyQuota = todaysMissions;
	}

	/*
	 * when an executive reports back from a mission the its added to the completed list and notidies the manager
	 */
	public synchronized void missionReport(Operation op) {
		opFinishedToday++;
		this.finishedOps.add(op);
		System.out.println("\nnumber of operations finished: " + finishedOps.size()+ "\n");
		this.notifyAll();
	}
	protected void sendHome() {
		this.taskQ.insert(null);
		this.opQ.insert(null);
		this.infos.insertStrategy(null);
	}
	@Override
	public String getName() {
		return this.name;
	}
	@Override
	public void goHome() {
		
	}
}
