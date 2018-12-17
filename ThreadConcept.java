package main.java;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadsConcept{
	
	
	private static int NOOFTASKS = 2;
	
	private static ExecutorService taskExecutor = new ThreadPoolExecutor(	NOOFTASKS, 
																			NOOFTASKS,
																            0L, 
																            TimeUnit.MILLISECONDS,
																            new LinkedBlockingQueue<Runnable>() );
	

	static final String signal = new String("Signal");
	
    public static void main(String[] args){
    	
    	createThreadPool();
    	taskExecutor.shutdown();
    	try {
			taskExecutor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			
		}
    	System.out.println("Shutdown Threadpool done");
    }
    
	public static void createThreadPool() {
		
		BlockingQueue<Integer> queue1 = new LinkedBlockingDeque<Integer>();
		BlockingQueue<Integer> queue2 = new LinkedBlockingDeque<Integer>();
		
		for(int iCur : IntStream.range(1, 101).toArray()){
			if(iCur %2 == 0){
				queue2.add(iCur);				
			}else{
				queue1.add(iCur);
			}
		}		
        taskExecutor.execute(new PrintThread(signal, queue1));
        try {
        	synchronized(signal){
        		signal.wait();
        	}
		} catch (InterruptedException e) {
		}
        taskExecutor.execute(new PrintThread(signal, queue2));
	}   
	
	public static class PrintThread implements Runnable{
		
		private String signal;
		private BlockingQueue<Integer> wrkQueue;

		public PrintThread(	String signal, 
							BlockingQueue<Integer> wrkQueue) {
			super();
			this.signal = signal;
			this.wrkQueue = wrkQueue;
		}

		@Override
		public void run() {
			
			final String THREADLABEL = "Thread ";
			final String NUMBERLABEL = ": The number is '";
			final String QUOTES = "'";
			final String ENDTHREADMSG = "Ending thread ";
				
			while(wrkQueue.size() > 0) {
				
		        try {	
		        	
					if(null != wrkQueue) {
						System.out.println(new StringBuilder().append(THREADLABEL).append(Thread.currentThread().getId()).append(NUMBERLABEL).append(wrkQueue.take()).append(QUOTES));
					}	
					synchronized(signal){
						signal.notifyAll();
						signal.wait();
					}
				} catch (InterruptedException e) {
				}
			}
			synchronized(signal){				
				signal.notifyAll();
			}	
			System.out.println(ENDTHREADMSG + Thread.currentThread().getId());
		}
	}
}
