package kr.com.inspect.util;

import java.util.HashMap;
import java.util.Map;

/* Thread-Safe하게 자원을 공유하기 위한 싱글톤 객체 */
public class Singleton {
	
	/**
	 * 싱글톤 기본 생성자 
	 */
	private Singleton() {}
	
	/**
	 * 싱글톤 객체를 얻어오는 메소드
	 * @return Singleton 객체
	 */
	public static Singleton getInstance() {
		return LazyHolder.INSTANCE;
	}
	
	/**
	 * 오직 한번만 싱글톤 객체를 생성할 수 있게 하는 LazyHolder 클래스
	 * @author Yeonhee Kim
	 */
	private static class LazyHolder {
		private static final Singleton INSTANCE = new Singleton();  
	}
	
	/**
	 * 아직 DB에 등록되지 않은 새로운 데이터의 개수(중복 방지)
	 */
	private int newData;
	
	/**
	 * 해당되는 DB에 입력되는 총 row의 수
	 */
	private Map<String, Integer> dbRowCount = new HashMap<String, Integer>();
	
	/**
	 * 시간 기록을 위한 timeRecorder
	 */
	private Map<String, Long> timeRecorder = new HashMap<String, Long>();
	
	/**
	 * 아직 DB에 등록되지 않은 새로운 데이터의 개수 getter
	 * @return 아직 DB에 등록되지 않은 새로운 데이터의 개수
	 */
	public int getNewData() {
		return newData;
	}

	/**
	 * 아직 DB에 등록되지 않은 새로운 데이터의 개수 setter
	 * @param newData 아직 DB에 등록되지 않은 새로운 데이터의 개수
	 */
	public void setNewData(int newData) {
		this.newData = newData;
	}
	
	/**
	 * 합산한 해당 DB들에 추가된 총 row의 수를 리셋함
	 */
	public void resetDbRowCount() {
		this.dbRowCount = new HashMap<String, Integer>();
	}
	
	/**
	 * 해당되는 DB에 추가된 총 row의 수를 더함
	 * @param dbName 해당되는 DB 이름
	 * @param count 해당되는 DB에 추가된 총 row의 수
	 */
	public void setDbRowCount(String dbName, int count) {
		if(dbRowCount.get(dbName) == null) {
			this.dbRowCount.put(dbName, 0);
		}
		this.dbRowCount.put(dbName, dbRowCount.get(dbName)+count);
	}
	
	/**
	 * 해당되는 DB에 추가된 총 row의 수를 반환함
	 * @param dbName 해당되는 DB 이름
	 * @return 해당되는 DB에 추가된 총 row의 수
	 */
	public Integer getDbRowCount(String dbName) {
		return dbRowCount.get(dbName);
	}
	
	/**
	 * 측정한 시간 기록들을 리셋함
	 */
	public void resetTimeRecorder() {
		this.timeRecorder = new HashMap<String, Long>();
	}

	/**
	 * 해당되는 키에 시간을 더함
	 * @param key 해당되는 키
	 * @param time 시간
	 */
	public void setTimeRecorder(String key, long time) {
		if(timeRecorder.get(key) == null) {
			this.timeRecorder.put(key, (long) 0);
		}
		this.timeRecorder.put(key, timeRecorder.get(key)+time);
	}
	
	/**
	 * 해당되는 키의 시간 기록합을 밀리초 단위로 반환함
	 * @param key 해당되는 키
	 * @return 해당되는 키의 시간 기록합(밀리초)
	 */
	public Long getTimeRecorder(String key){
		return timeRecorder.get(key);
	}
}
