package kr.com.inspect.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Utterance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.com.inspect.dao.PostgreDao;
import kr.com.inspect.dto.Sound;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PostgreController {
	@Autowired
	private PostgreDao postgreDao;
	
	//엘라스틱서치
	private String index = "audiolist";
	
	private String s = File.separator;
	
	/* PostgreSQL 페이지 이동 */
	@GetMapping("/postgrePage")
	public String moveToElasticPage() {
		return "postgreSQL/postgrePage";
	}
	
	/* 엘라스틱서치 특정 인덱스를 PostgreSQL 특정 테이블에 넣기 */
	@GetMapping("/insertElasticIndexIntoPostgre")
	public String insertElasticIndexIntoPostgre() {
		postgreDao.insertElasticIndex(index);
		return "postgreSQL/insertElasticIndex";
	}
	
	/* JSON 파일을 PostgreSQL 특정 테이블에 넣기
	   (metadata, speaker, utterance, eojeolList) */
	@GetMapping("/insertJSONIntoPostgre")
	public String insertJSONObject(HttpServletRequest request) {
		String root = request.getSession().getServletContext().getRealPath("/");
		String path = root + "input" + s + "json" + s;
		boolean flag = postgreDao.insertJSONObject(path);
		if(flag == true) {
			return "postgreSQL/insertJSON";
		}
		return "postgreSQL/insertJSONFail";
	}
	
	/* xlsx 파일을 PostgreSQL 특정 테이블(program)에 넣기 */
	@GetMapping("/insertXlsxIntoPostgre")
	public String insertXlsxTable(HttpServletRequest request) {
		String root = request.getSession().getServletContext().getRealPath("/");
		String path = root + "input" + s + "xlsx" + s;
		boolean flag = postgreDao.insertXlsxTable(path);
		if(flag == true) {
			return "postgreSQL/insertXlsx";
		}
		return "postgreSQL/insertXlsxFail";
	}
	
	/* PostgreSQL 특정 테이블 가져오기 */
	@GetMapping("/getPostgreTable")
	public String getPostgreTable(Model model) {
		List<Metadata> metadata = postgreDao.getTable();
		model.addAttribute("result", metadata);
		return "postgreSQL/getTable";
	}
	/* Utterance 테이블 가져오기 */
	@GetMapping("/getUtteranceTable/{format}")
	public String getUtteranceTable(Model model,@PathVariable Integer format){
		List<Utterance> utterances = postgreDao.getUtteranceTableUsingMetadataId(format);
//		List<>;
		model.addAttribute("result",utterances);
		return "postgreSQL/getUtterance";
	}
}
