public class BoundedQueue<T> extends Queue<T> {
	protected int qCap;

	public BoundedQueue(int cap) {
		super();
		qCap = cap;
	}
	
	@Override
	public synchronized void insert(T t) {
		while(q.size() >= qCap) {
			try {
				this.wait();
			} catch (InterruptedException e) { 
				e.printStackTrace();
			}	
		}
		q.add(t);
		this.notifyAll();
		
	}
	@Override
	public synchronized T extract(){
		while (q.isEmpty()) {
			try{
				this .wait();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		T item = q.get(0);
		q.remove(item);
		this.notifyAll();
		return item;
	}
}