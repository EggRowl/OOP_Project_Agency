import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import Exceptions.TooMuchManagerException;
import Interfaces.NotHomeless;


public class TestMain {
	protected Vector<Secretary> allSec = new Vector<>();
	protected Vector<TaskManager> allTM = new Vector<>();
	protected Vector<Operator> allOp = new Vector<>();
	protected Vector<Executive> allExec = new Vector<>();
	
	protected Vector<Vector<Object>> allEmps = new Vector<>();
	
	public static void writeToFile(String s, String fileName) {
		BufferedWriter writer = null;
		
		try {
			FileWriter file1 = new FileWriter(fileName +".txt");
			writer = new BufferedWriter(file1);
			writer.write(s);
		} catch(IOException e) {
			System.out.println("the file " + fileName + " could not be created");
		} finally {
			try {
				if(writer != null)
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) throws TooMuchManagerException {
	
		Vector<Secretary> allSec = new Vector<>();
		Vector<TaskManager> allTM = new Vector<>();
		Vector<Operator> allOp = new Vector<>();
		Vector<Executive> allExec = new Vector<>();
		Vector<Vector<? extends NotHomeless>> allEmps = new Vector<>();
		allEmps.add(allSec);
		allEmps.add(allTM);
		allEmps.add(allOp);
		allEmps.add(allExec);
		AgencyManager AliG = new AgencyManager("Ali-G", allEmps);
		Queue<Call> callQ = new Queue<>();
		Queue<Task> taskQ = new Queue<>();
		Queue<Strategy> stratQ = new Queue<>();
		CallSpammer spam = new CallSpammer("calls.txt.txt", callQ);
		Thread cs = new Thread(spam);
		Secretary shrek = new Secretary("Shrekretary", callQ, taskQ);
		Thread s1 = new Thread(shrek);
		TaskManager borat = new TaskManager("Borat", taskQ, stratQ);
		
		
		cs.start();
	}

}
