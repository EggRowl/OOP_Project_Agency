import java.util.List;
import java.util.Vector;

public class Queue<T> {
	protected List<T> q;

	public Queue() {
		this.q = new Vector<T>();
	}
	/**
	 * 
	 * @param t
	 * @return
	 */
	public synchronized void insert(T t) {
		q.add(t);		
		this.notifyAll();

	}
	public synchronized T extract() {
		while(q.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		T t = q.get(0);
		q.remove(t);
		return t;
	}
	
	public synchronized T peek() {
		return this.q.get(q.size()-1);
	}
	public synchronized boolean contains(T t) {
		return q.contains(t);
	}

	public synchronized int size() {
		return q.size();
	}
	public synchronized boolean isEmpty() {
		return q.isEmpty();
	}
}
