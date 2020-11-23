package kr.com.inspect.controller;

import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.com.inspect.service.ElasticService;

/**
 * 엘라스틱 서치의 컨트롤러
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

@Controller
public class ElasticController {
	
	/**
	 * 엘라스틱서치 서비스 필드 선언
	 */
	@Autowired
	private ElasticService elasticService;
	
	/**
	 * 엘라스틱 서치에서 가져온 정보
	 */
	private String index = "audiolist";

	/**
	 * 엘라스틱서치에서 해당되는 인덱스에 있는 데이터 모두 가져오기
	 * @param model 속성부여
	 * @return elasticsearch/getIndex 호출로 해당 페이지에 정보 리턴
	 */
	@GetMapping("/getElasticIndex")
	public String getElasticIndex(Model model) {
		SearchHit[] searchHits = elasticService.getIndex(index);
		model.addAttribute("result", searchHits); 
		return "elasticsearch/getIndex";
	}
}
