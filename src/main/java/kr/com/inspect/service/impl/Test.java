package kr.com.inspect.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {
	private static final Logger logger = LoggerFactory.getLogger(PostgreServiceImpl.class);
	private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();
	
	public static void main(String[] args) {
		Test test = new Test();
		test.run();
		System.out.println(Test.threadLocal.get());
		Test.threadLocal.remove();
	}
	
	public void run() {
		int threadCnt = 3;
		ExecutorService executor = Executors.newFixedThreadPool(threadCnt);
		List<Future<?>> futures = new ArrayList<>();
		
		for(int i=0; i<10; i++){
			futures.add(executor.submit(() -> {
				Test.threadLocal.set(Test.threadLocal.get()+1);
			}));
		}
		closeThread(executor, futures);
	}
	
	public void closeThread(ExecutorService executor, List<Future<?>> futures) {
		for (Future<?> future : futures) {
			try {
				future.get(); //스레드 작업이 종료될 때까지 기다림
			} catch (InterruptedException e) {
				//e.printStackTrace();
			} catch (ExecutionException e) {
				//e.printStackTrace();
			}
        }
		executor.shutdownNow(); //Task 종료
	}
}
