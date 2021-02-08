package kr.com.inspect.service.impl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import kr.com.inspect.dao.ChartDao;
import kr.com.inspect.dao.MemberDao;
import kr.com.inspect.dao.PostgreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.com.inspect.service.ChartService;
/**
 * Chart Service
 * @author Yeonhee Kim
 * @author Wooyoung Lee
 * @version 1.0
 *
 */
@Service
public class ChartServiceImpl implements ChartService {
	/**
	 * chartDao dao 필드 선언
	 */
	@Autowired
	private ChartDao chartDao;
	
	/**
	 * PostgreSQL dao 필드 선언
	 */
	@Autowired
	private PostgreDao postgreDao;

	/**
	 * MemberDao 필드 선언
	 */
	@Autowired
	private MemberDao memberDao;

	/**
	 * 차트에 활용할 JsonLog 개수 목록을 가져옴
	 * @return 차트에 활용할 JsonLog 개수 목록
	 */
	@Override
	public List<Integer> getCountListOnJsonLog() {
		List<Integer> list = new ArrayList<>();
		Map<Integer, Integer> map = new HashMap<>();
		
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				map.put(0, chartDao.getCountLessThanOnJsonLog(LocalTime.of(00,01,00)));
			}
		});
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				map.put(1, chartDao.getCountLessAndMoreThanOnJsonLog(LocalTime.of(00,01,00), LocalTime.of(00,02,00)));
			}
		});
		Thread thread3 = new Thread(new Runnable() {
			@Override
			public void run() {
				map.put(2, chartDao.getCountLessAndMoreThanOnJsonLog(LocalTime.of(00,02,00), LocalTime.of(00,03,00)));
			}
		});
		Thread thread4 = new Thread(new Runnable() {
			@Override
			public void run() {
				map.put(3, chartDao.getCountLessAndMoreThanOnJsonLog(LocalTime.of(00,03,00), LocalTime.of(00,04,00)));
			}
		});
		Thread thread5 = new Thread(new Runnable() {
			@Override
			public void run() {
				map.put(4, chartDao.getCountLessAndMoreThanOnJsonLog(LocalTime.of(00,04,00), LocalTime.of(00,05,00)));
			}
		});
		Thread thread6 = new Thread(new Runnable() {
			@Override
			public void run() {
				map.put(5, chartDao.getCountMoreThanOnJsonLog(LocalTime.of(00,05,00)));
			}
		});
		
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
		thread6.start();
		
		try {
			thread1.join();
			thread2.join();
			thread3.join();
			thread4.join();
			thread5.join();
			thread6.join();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		
		for(int i=0; i<map.size(); i++) {
			list.add(map.get(i));
		}
		return list;
	}

	/**
	 * Metadata에서 각 데이터 타입의 비율 목록을 가져옴
	 * @return Metadata에서 각 데이터 타입의 비율 목록
	 */
	@Override
	public Map<String, Double> getRatioOnMetadataByType() {
		Map<String, Double> map = new HashMap<>();
		String[] keyArr = {"all", "korean_lecture", "meeting_audio", "customer_reception", "counsel_audio"};

		ExecutorService executor = Executors.newFixedThreadPool(keyArr.length);
		List<Future<?>> futures = new ArrayList<>();
		for (String key : keyArr) {
			futures.add(executor.submit(() -> {
				map.put(key, Double.parseDouble(Integer.toString(postgreDao.getMetadataCnt(key, ""))));
			}));
		}

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

		double all = map.get("all");

		/* 비율로 세팅 */
		for (int i = 0; i < map.size(); i++) {
			double value = map.get(keyArr[i]);
			double result = value / all * 100;
			map.put(keyArr[i], Math.round(result * 100) / 100.0);
		}

		return map;
	}

	/**
	 * 대쉬보드에 사용할 데이터 개수 가져옴
	 * @return 총 음성데이터, 문장, 어절, 회원 수 개수
	 */
	@Override
	public Map<String, Object> getCountData(){
		Map<String, Object> items = postgreDao.getDashboardCount();

		return items;
	}
}
