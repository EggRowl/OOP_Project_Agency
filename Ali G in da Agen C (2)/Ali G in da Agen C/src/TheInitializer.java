import java.util.Vector;

public class TheInitializer {
	
	protected InformationSystem infos = new InformationSystem();
	protected Vector<Thread> allSec = new Vector<>();
	protected Vector<Thread> allTM = new Vector<>();
	protected Vector<Thread> allOp = new Vector<>();
	protected Vector<Thread> allExec = new Vector<>();
	protected Vector<Vector<Thread>> allEmps =  new Vector<>();
	protected Vector<Operation> finishedOps = new Vector<>();
	protected Queue<Call> callQ = new Queue<>();
	protected Queue<Task> taskQ = new Queue<>();
	protected BoundedQueue<Operation> opQ = new BoundedQueue<>(15);
	protected CallSpammer callSpam = new CallSpammer("calls.txt.txt", callQ);
	protected String[] secNames = {"Karen", "Me julie" , "Bennito Mussolini" , "Shrekretary" , "Antiochus" };
	protected String[] tmNames = {"OGMapler", "xXxNoobSlayerxXx" , "Shipudnik" , "Kebabnik", "JPQ > Got track"};
	protected String[] opNames = {"Borat" , "Bruno" , "Azamat Bagatov" };
	protected String[] execNames = {"Lord FarQuaad", "Donkey", "Rasputin" , "Barack Obama" , "The Zohan", "Aniki" , "Van Darkholme" , "Chaimon Limon", "Madonna" , "Lady Gaga" , "Justin Timberlake" , "Britney Spears", "saba gepeto"}; 
	protected int execNum = 5;
	protected AgencyManager AliG = new AgencyManager("Ali-G", allEmps, taskQ, opQ, infos, finishedOps);	
	protected Thread manager = new Thread(AliG);
	public TheInitializer () {
		
	}
	public void startDay() {
		Thread spammer = new Thread(callSpam);
		manager.start();
		createWorkers();
		spammer.start();
	}
	private void createWorkers() {
		for(int i=0; i<5; i++) {
			Secretary sec = new Secretary(secNames[i], callQ, taskQ);
			Thread ts = new Thread(sec);
			allSec.add(ts);
			ts.start();
		}
		for(int i=0; i<3; i++) {
			TaskManager taskManager= new TaskManager (tmNames[i], taskQ , infos);
			Thread ttm = new Thread(taskManager);
			allTM.add(ttm);
			ttm.start();
		}
		for(int i=0; i<3; i++) {
			Operator op = new Operator(opNames[i], opQ, infos);
			Thread to = new Thread(op);
			allOp.add(to);
			to.start();
		}
		for(int i=0; i < this.execNum; i++) {
			Executive ex = new Executive(execNames[i], opQ, infos, finishedOps, AliG);
			Thread te = new Thread(ex);
			allExec.add(te);
			te.start();
		}
	}
	public void setMissionQuota(int missionCount) {
		AliG.setQuoata(missionCount);
	}
	public void setExecNumber(int number) {
		this.execNum = this.execNum + number;
	}
	public static void setOperatorWorktime(double opWorkT) {
		Operator.setWorkTime(opWorkT);
	}
}
