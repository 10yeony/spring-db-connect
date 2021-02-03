package kr.com.inspect.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import kr.com.inspect.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.com.inspect.service.ChartService;

import javax.servlet.http.HttpServletResponse;

/**
 * 대쉬보드의 차트 데이터 관련 컨트롤러
 * @author Yeonhee Kim
 * @author Wooyoung Lee
 * @version 1.0
 */

@Controller
@RequestMapping("chart")
public class ChartController {

	/**
	 * ChartService 필드 선언
	 */
	@Autowired
	private ChartService chartService;

	/**
	 *
	 * @return
	 * @throws InterruptedException
	 */
	@GetMapping("getCountListOnJsonLog")
	@ResponseBody
	public String getCountListOnJsonLog() throws InterruptedException {
		List<Integer> list = chartService.getCountListOnJsonLog();
		return list.toString();
	}

	/**
	 *
	 * @return
	 */
	@GetMapping("getRatioOnMetadataByType")
	@ResponseBody
	public Map<String, Double> getRatioOnMetadataByType() {
		Map<String, Double> map = chartService.getRatioOnMetadataByType();
		return map;
	}

	/**
	 * 대쉬보드에 쓰일 데이터 개수 반환
	 * @param response 웹으로 보낼 응답
	 * @throws IOException 입출력 예외
	 */
	@GetMapping("getCountData")
	@ResponseBody
	public void getCountData(HttpServletResponse response) throws IOException {
		ResponseData responseData = new ResponseData();
		Map<String, Object> items = chartService.getCountData();
		responseData.responseJSON(response, items);
	}
}
