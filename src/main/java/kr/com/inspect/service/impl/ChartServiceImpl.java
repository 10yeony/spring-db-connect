package kr.com.inspect.service.impl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.com.inspect.dao.ChartDao;
import kr.com.inspect.service.ChartService;

@Service
public class ChartServiceImpl implements ChartService {
	
	@Autowired
	private ChartDao chartDao;

	/**
	 * 차트에 활용할 JsonLog 개수 목록을 가져옴
	 * @return 차트에 활용할 JsonLog 개수 목록
	 * @throws InterruptedException 
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
}
