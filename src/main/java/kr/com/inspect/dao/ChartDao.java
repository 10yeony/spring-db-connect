package kr.com.inspect.dao;

import java.time.LocalTime;

public interface ChartDao {
	/**
	 * 특정 시간보다 빠른 jsonlog 개수 가져오기
	 * @param time 기준시간 
	 * @return 특정 시간보다 빠른 jsonlog 개수
	 */
	public int getCountLessThanOnJsonLog(LocalTime time);
	
	/**
	 * 특정 시간보다 느린 jsonlog 개수 가져오기
	 * @param time 기준시간
	 * @return 특정 시간보다 느린 jsonlog 개수
	 */
	public int getCountMoreThanOnJsonLog(LocalTime time);
	
	/**
	 * 특정 시간보다 빠르거나 느린 jsonlog 개수 가져오기
	 * @param time1 기준시간1
	 * @param time2 기준시간2
	 * @return 특정 시간보다 빠르거나 느린 jsonlog 개수
	 */
	public int getCountLessAndMoreThanOnJsonLog(LocalTime time1, LocalTime time2);
}
