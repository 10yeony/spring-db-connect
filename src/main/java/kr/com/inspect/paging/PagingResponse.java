package kr.com.inspect.paging;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import kr.com.inspect.dto.ResponseData;

/**
 *
 * @author Yeonhee Kim
 * @version 1.0
 */
@Component
public class PagingResponse {

	/**
	 *
	 * @param list
	 * @param totalCount
	 * @param pagination
	 * @return
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
	 *
	 * @param responseData
	 * @return
	 */
	public Map<String, Object> getItems(ResponseData responseData){
		return (Map<String, Object>) responseData.getItem();
	}

	/**
	 *
	 * @param responseData
	 * @return
	 */
	public Object getList(ResponseData responseData) {
		Map<String, Object> items = getItems(responseData);
		return items.get("list");
	}

	/**
	 *
	 * @param responseData
	 * @return
	 */
	public int getTotalCount(ResponseData responseData) {
		Map<String, Object> items = getItems(responseData);
		return (int) items.get("totalCount");
	}

	/**
	 *
	 * @param responseData
	 * @return
	 */
	public String getPagination(ResponseData responseData) {
		Map<String, Object> items = getItems(responseData);
		return (String) items.get("pagination");
	}
}
