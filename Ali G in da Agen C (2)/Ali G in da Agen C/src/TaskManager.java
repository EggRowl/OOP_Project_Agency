
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import Interfaces.NotHomeless;

public class TaskManager implements NotHomeless,  Runnable{
	protected InformationSystem infos;
	protected static Vector<TaskManager> allTM = new Vector<>();
	private String name;
	protected Queue<Task> taskQ;
	
	protected Task t;
	protected Strategy s;

	public TaskManager(String name, Queue<Task> taskQ, InformationSystem infos) {
		this.name = name;
		this.taskQ = taskQ;
		this.infos = infos;
		
		allTM.add(this);
	}
	@Override
	public void run() {
		System.out.println("task manager " + this.name + " has entered the office");
		while(AgencyManager.canIGoHome() == false) {
			// wait for tasks to arrive
			// take the task
			this.t = this.taskQ.extract();
			if(this.t == null) {
				taskQ.insert(this.t);
				break;
			}
			// work on the task
			this.work();
			// create the strategy
			this.createStrategy();
			// insert the strategy
			this.insertTask();
			System.out.println("Strategy inserted " + this.s.getCodeName());
		}
		System.out.println("\nTask Manager: " + this.name + " has finished the day\n");
	}
	
	protected void work() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * creates the strategy
	 * @return a freshly made Strategy ready to enter the kwewe
	 */
	protected Strategy createStrategy() {
		String codeName = theAnagramatron(this.t.getSuspectName());
		int opLevel = convertServiceToLevel(this.t.getCustomerRequest());
		int ETC = estimateOpDuration(opLevel);
		this.s = new Strategy(t.getId(), codeName, opLevel, t.getCustomer(), ETC, t.getArrivalTime());
		return s;
	}
	/**
	 * takes a string converts it to a list
	 * shuffles the list and converts it back to a string
	 * @param toAnagram
	 * @return anagram of the given String
	 */
	protected String theAnagramatron(String toAnagram) {
			String theAnagram = toAnagram;
			char[] charray = toAnagram.toCharArray();
			List<Character> charrayList = new ArrayList<>();
		while(toAnagram == theAnagram) {
			for ( int i = 0; i < charray.length; i++) {
				charrayList.add(charray[i]);
			}
			Collections.shuffle(charrayList);
			for(int i = 0; i < charray.length; i++) {
				charray[i] = charrayList.get(i);
			}
			theAnagram = new String(charray);
		}
		return theAnagram;
	}
	/**
	 * converts the customers request to its int counterpart
	 * @param thisNoLevel the customer request String
	 * @return the customer int code
	 */
	protected int convertServiceToLevel(String custRequest) {
		int zeLevel = 0;
		if(custRequest.equals("inquiry")) {
			zeLevel = 1;
		}
		else if(custRequest.equals("Background check")) { 
			zeLevel = 2;
		}
		else if(custRequest.equals("surveillance")) {
			zeLevel = 3;
		}
		else if(custRequest.equals("fraud and illegal")) {
			zeLevel = 4;
		}
		else if(custRequest.equals("missing people")) {
			zeLevel = 5;
		}
		return zeLevel;
	}
	/**
	 * estimates how long the operation will take.
	 * @param level the level code of the operation
	 * @return
	 */
	protected int estimateOpDuration(int level) {
		int time = (level*t.getCustomer())*1000;
		return time;
	}
	protected synchronized void insertTask() {
		this.infos.insertStrategy(this.s);
	}
	public void goHome() {
		this.notifyAll();
	}
	public static void sendHome() {
		for(int i = 0; i < allTM.size(); i ++) {
			allTM.get(i).goHome();
		}
	}
	@Override
	public String getName() {
		return this.name;
	}
}
