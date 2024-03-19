import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class CallSpammer implements Runnable{
	
	protected static Vector<CallSpammer> allSpammers = new Vector<>();
	protected String fileName;
	protected boolean spamming = true;
	private int maxCalls = 0;
	private static int spammedCalls = 0;
	private Vector<Thread> callThreads = new Vector<>();
	private Queue<Call> callQ;
	
	public CallSpammer(String fileName, Queue<Call> callQ) {
		this.fileName = fileName;
		this.callQ = callQ;
		this.setMaxCalls();
		allSpammers.add(this);
	}
	@Override
	public void run() {
		
		//set the maximum amount of incoming calls
		
		// start spamming calls
		this.startSpamming();
		System.out.println("\n\nnumber of call Threads in total: " + callThreads.size());
		
	}
	
	/**
	 * counts all the incoming calls
	 * @return
	 */
	private boolean setMaxCalls() {
		int counter = 0;
		BufferedReader reader2 = null;
		try {
			reader2 = new BufferedReader(new FileReader(this.fileName));
			String line = reader2.readLine();
			while((line = reader2.readLine()) != null) {
				counter++;
			}// while
		} catch(IOException e) {
			System.out.printf("ERROR - A %s occurred:\n\t", e.getClass().toString(), e.getMessage());
			return false;
		}
		try {
			reader2.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
			maxCalls = counter;
		return true;
	}
	
	/**
	 * generates the calls from the file
	 */
	public void startSpamming() {
		this.spamming = true;
		BufferedReader reader = null;
		System.out.println("Ring Ring Ring Ring Ring Ring Banana phone tududududu");
		try {
			reader = new BufferedReader(new FileReader(this.fileName));
			String line = reader.readLine();
			while((line = reader.readLine()) != null) {
				String[] variables = line.split("	");
				String name = variables[0];
				String service = variables[1];
				int clientType = parseClient(variables[2]);
				int arrivalDelay = Integer.parseInt(variables[3]);
				int callDuration = Integer.parseInt(variables[4]);
				Call curCall = new Call(name, service, clientType, arrivalDelay, callDuration, callQ);
				@SuppressWarnings("unused")
				Thread caller;
				callThreads.add(caller = new Thread(curCall));
				callThreads.get(callThreads.size() -1).start();
				
			}// while
		} catch(IOException e) {
			System.out.printf("ERROR - A %s occurred:\n\t", e.getClass().toString(), e.getMessage());
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * parses the service to his number code (1-5)
	 * @param serv
	 * @return the number code of said service
	 */
	/**
	 * parses the String clientType into its clientCode counterpart
	 * @param clientType
	 * @return clientCode
	 */
	private int parseClient(String clientType) {
		int clientCode = 0;
		if(clientType.equals("private")) {
			clientCode = 1;
		}
		else if(clientType.equals("business")) {
			clientCode = 2;
		}
		else if(clientType.equals("government")){
			clientCode = 3;
		}
		return clientCode;
	}
	
	public int getTotalCallsSpammed() {
		return this.spammedCalls;
	}
	public int getMaxCalls() {
		return this.maxCalls;
	}
	public boolean getSpamming() {
		return this.spamming;
	}
	public static boolean isSpamming(int index) {
		return allSpammers.get(index).getSpamming();
	}
}
