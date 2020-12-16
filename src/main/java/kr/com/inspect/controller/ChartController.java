package kr.com.inspect.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.com.inspect.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.com.inspect.service.ChartService;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("chart")
public class ChartController {
	
	@Autowired
	private ChartService chartService;
	
	@GetMapping("getCountListOnJsonLog")
	@ResponseBody
	public String getCountListOnJsonLog() throws InterruptedException {
		List<Integer> list = chartService.getCountListOnJsonLog();
		return list.toString();
	}

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
		ObjectMapper mapper = new ObjectMapper(); // JSON 변경용

		Map<String, Object> items = chartService.getCountData();

		/* 응답시 한글 인코딩 처리 */
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().print(mapper.writeValueAsString(items));
		response.getWriter().flush();
	}
}
