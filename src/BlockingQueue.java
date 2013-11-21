

import java.util.LinkedList;


public class BlockingQueue<T> implements Queue<T> {
	private int length;
	private int currentSize=0;
	private LinkedList<T> list = new LinkedList<T>();
	public BlockingQueue(int size){
		//size validation if less  than zero
		this.length = size;
	}
	public void  put(T value) {
		synchronized (this) {
			while(currentSize==length){
				try {
					this.wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			if(currentSize==0){
				this.notify();
			}
		    list.addLast(value);
		    currentSize++;
		}
	}

	public T take() {
		synchronized (this) {
			while(currentSize==0){
				try {
					this.wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			if(currentSize==length){
				this.notify();
			}
		    currentSize--;
		    return list.removeFirst();
		}
	}
}
