package kr.com.inspect.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.com.inspect.service.ChartService;

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
}
