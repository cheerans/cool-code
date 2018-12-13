import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadsConcept{
	
	private int SLEEPTIMEOUT = 10;
	private int NOOFTASKS = 2;
	
	public class PrintThread extends Thread{
		
		private AtomicBoolean shutdown = null;
		private CountDownLatch latch = null;
		private BlockingQueue<Integer> wrkQueue;
		private long sleepTimeout = 10;

		public PrintThread(	AtomicBoolean shutdown, 
							CountDownLatch latch, 
							BlockingQueue<Integer> wrkQueue,
							long sleepTimeout) {
			super();
			this.shutdown = shutdown;
			this.latch = latch;
			this.wrkQueue = wrkQueue;
			this.sleepTimeout = sleepTimeout;
		}

		@Override
		public void run() {
			
			int number;
			while(shutdown.get() == false) {
				
		        try {	    		
					if(null != wrkQueue) {
						number = wrkQueue.take();
						System.out.println(number);
					}
					Thread.sleep(sleepTimeout);
		        } catch (InterruptedException e) {

		        }
			}
			latch.countDown();
		}
	}
	
	private ExecutorService taskExecutor = new ThreadPoolExecutor(	NOOFTASKS, 
																	NOOFTASKS,
														            0L, 
														            TimeUnit.MILLISECONDS,
														            new LinkedBlockingQueue<Runnable>() );
	
	private static AtomicBoolean shutdown = new AtomicBoolean(false);
	private CountDownLatch latch = null;
	    
	public ThreadsConcept() {
		
		super();
		createThreadPool();
	}

	public void createThreadPool() {			
		
		// We will rely on a countdown latch to create/shutdown the threads
		latch = new CountDownLatch(NOOFTASKS);
		BlockingQueue<Integer> queue = new LinkedBlockingDeque<Integer>();
		Thread wt = new PrintThread(shutdown, latch, queue, SLEEPTIMEOUT);
        taskExecutor.execute(wt);
	}
	
    public void shutdown() {
    	
    	shutdown.set(true);
        try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        taskExecutor.shutdown();	
    }
}
