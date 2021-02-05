package kr.com.inspect.paging;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import kr.com.inspect.dto.ResponseData;

/**
 * 페이징 처리와 관련하여 응답 객체에 관련 변수들을 설정하고 가져오는, 모듈화된 객체
 * @author Yeonhee Kim
 * @version 1.0
 */
@Component
public class PagingResponse {

	/**
	 * 화면으로 응답할 객체에 페이징 처리와 관련한 변수를 담고 반환함
	 * @param list 화면으로 응답할 데이터 리스트
	 * @param totalCount 데이터의 총 수
	 * @param pagination 페이징 처리 관련 HTML 태그 문자열
	 * @return 페이징 처리와 관련한 변수가 담긴 응답 객체
	 */
	public ResponseData getResponseData(Object list, Object totalCount, Object pagination) {
		ResponseData responseData = new ResponseData();
		Map<String, Object> items = new HashMap<String, Object>();	
		items.put("list", list);
		items.put("totalCount", totalCount);
		items.put("pagination", pagination);
		responseData.setItem(items);
		return responseData;
	}

	/**
	 * 응답 객체에서 화면으로 응답할 데이터 리스트, 데이터의 총 수, 페이징 처리 관련 HTML 태그 문자열<br>
	 * 이 담긴 Map을 가져와서 반환함
	 * @param responseData 화면으로 응답할 객체
	 * @return 화면으로 응답할 데이터 리스트, 데이터의 총 수, 페이징 처리 관련 HTML 태그 문자열이 담긴 Map
	 */
	public Map<String, Object> getItems(ResponseData responseData){
		return (Map<String, Object>) responseData.getItem();
	}

	/**
	 * 응답 객체에서 화면으로 응답할 데이터 리스트를 가져와서 반환함
	 * @param responseData 화면으로 응답할 객체
	 * @return 화면으로 응답할 데이터 리스트
	 */
	public Object getList(ResponseData responseData) {
		Map<String, Object> items = getItems(responseData);
		return items.get("list");
	}

	/**
	 * 응답 객체에서 데이터의 총 수를 가져와서 반환함
	 * @param responseData 화면으로 응답할 객체
	 * @return 데이터의 총 수
	 */
	public int getTotalCount(ResponseData responseData) {
		Map<String, Object> items = getItems(responseData);
		return (int) items.get("totalCount");
	}

	/**
	 * 응답 객체에서 페이징 처리 관련 HTML 태그 문자열을 가져와서 반환함
	 * @param responseData 응답 객체
	 * @return 페이징 처리 관련 HTML 태그 문자열
	 */
	public String getPagination(ResponseData responseData) {
		Map<String, Object> items = getItems(responseData);
		return (String) items.get("pagination");
	}
}
