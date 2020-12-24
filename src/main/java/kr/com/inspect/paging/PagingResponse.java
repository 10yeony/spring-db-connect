package kr.com.inspect.paging;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import kr.com.inspect.dto.ResponseData;

@Component
public class PagingResponse {
	public ResponseData getResponseData(Object list, Object totalCount, Object pagination) {
		ResponseData responseData = new ResponseData();
		Map<String, Object> items = new HashMap<String, Object>();	
		items.put("list", list);
		items.put("totalCount", totalCount);
    	items.put("pagination", pagination);
		responseData.setItem(items);
		return responseData;
	}
	
	public Map<String, Object> getItems(ResponseData responseData){
		return (Map<String, Object>) responseData.getItem();
	}
	
	public Object getList(ResponseData responseData) {
		Map<String, Object> items = getItems(responseData);
		return items.get("list");
	}
	
	public int getTotalCount(ResponseData responseData) {
		Map<String, Object> items = getItems(responseData);
		return (int) items.get("totalCount");
	}
	
	public String getPagination(ResponseData responseData) {
		Map<String, Object> items = getItems(responseData);
		return (String) items.get("pagination");
	}
}
