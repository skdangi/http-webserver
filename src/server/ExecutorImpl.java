package server;

public class ExecutorImpl implements Executor {
	private BlockingQueue<Runnable> queue ;
	Thread[] workers;
	
	public ExecutorImpl(int poolSize, int queueSize){
		queue = new BlockingQueue<Runnable>(queueSize);
		workers = new Thread[poolSize];
		for(int i=1;i<=poolSize;i++){
			workers[i-1] = new Worker("thread"+i);
			workers[i-1].start();
		}
	}

	public void submit(Runnable job) {
		queue.put(job);
	}

	private class Worker extends Thread {
		public Worker(String name) {
			super(name);
		}

		public void run() {
			while (true) {
				Runnable r = queue.take();
				r.run();
			}
		}
	}
}
