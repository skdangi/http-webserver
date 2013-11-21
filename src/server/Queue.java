package server;

public interface Queue<T> {
	public void put(T value);
	public T take();
}
