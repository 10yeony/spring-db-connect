package kr.com.inspect.dao.impl;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.com.inspect.dao.ChartDao;

@Repository
public class ChartDaoImpl implements ChartDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	private final String jsonLogNS = "JsonLogMapper.";

	/**
	 * 특정 시간보다 빠른 jsonlog 개수 가져오기
	 * @param time 기준시간 
	 * @return 특정 시간보다 빠른 jsonlog 개수
	 */
	@Override
	public int getCountLessThanOnJsonLog(LocalTime time) {
		return sqlSession.selectOne(jsonLogNS+"getCountLessThan", time);
	}

	/**
	 * 특정 시간보다 느린 jsonlog 개수 가져오기
	 * @param time 기준시간
	 * @return 특정 시간보다 느린 jsonlog 개수
	 */
	@Override
	public int getCountMoreThanOnJsonLog(LocalTime time) {
		return sqlSession.selectOne(jsonLogNS+"getCountMoreThan", time);
	}

	/**
	 * 특정 시간보다 빠르거나 느린 jsonlog 개수 가져오기
	 * @param time1 기준시간1
	 * @param time2 기준시간2
	 * @return 특정 시간보다 빠르거나 느린 jsonlog 개수
	 */
	@Override
	public int getCountLessAndMoreThanOnJsonLog(LocalTime time1, LocalTime time2) {
		Map<String, LocalTime> map = new HashMap<String, LocalTime>();
		map.put("time1", time1);
		map.put("time2", time2);
		return sqlSession.selectOne(jsonLogNS+"getCountLessAndMoreThan", map);
	}
	
}
