package kr.com.inspect.service;

import java.util.List;
import java.util.Map;

public interface ChartService {
	/**
	 * 차트에 활용할 JsonLog 개수 목록을 가져옴
	 * @return 차트에 활용할 JsonLog 개수 목록
	 */
	public List<Integer> getCountListOnJsonLog();

	/**
	 * Metadata에서 각 데이터 타입의 비율 목록을 가져옴
	 * @return Metadata에서 각 데이터 타입의 비율 목록
	 */
	public Map<String, Double> getRatioOnMetadataByType();

	/**
	 * 대쉬보드에 사용할 데이터 개수 가져옴
	 * @return 총 음성데이터, 문장, 어절, 회원 수 개수
	 */
	public Map<String, Object> getCountData();
}
