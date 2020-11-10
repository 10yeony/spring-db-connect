package kr.com.inspect.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kr.com.inspect.dto.EojeolList;
import kr.com.inspect.dto.JsonLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Utterance;
import kr.com.inspect.service.PostgreService;

@Controller
@PropertySource(value = "classpath:properties/directory.properties")
public class PostgreController {
	@Autowired
	private PostgreService postgreService;
	
	//엘라스틱서치
	private String index = "audiolist";
	
	@Value("${input.json.directory}")
	private String jsonPath;
	
	@Value("${input.xlsx.directory}")
	private String xlsxPath;
	
	/* 엘라스틱서치 특정 인덱스를 PostgreSQL 특정 테이블에 넣기 */
	@GetMapping("/insertElasticIndexIntoPostgre")
	public String insertElasticIndexIntoPostgre() {
		postgreService.insertElasticIndex(index);
		return "postgreSQL/insertElasticIndex";
	}
	
	/* JSON 파일을 PostgreSQL 특정 테이블에 넣기
	   (metadata, speaker, utterance, eojeolList) */
	@GetMapping("/insertJSONIntoPostgre")
	public String insertJSONObject(HttpServletRequest request) {
		boolean flag = postgreService.insertJSONObject(jsonPath);
		if(flag == true) {
			return "postgreSQL/insertJSON";
		}
		return "postgreSQL/insertJSONFail";
	}
	
	/* xlsx 파일을 PostgreSQL 특정 테이블(program)에 넣기 */
	@GetMapping("/insertXlsxIntoPostgre")
	public String insertXlsxTable(HttpServletRequest request) {
		boolean flag = postgreService.insertXlsxTable(xlsxPath);
		if(flag == true) {
			return "postgreSQL/insertXlsx";
		}
		return "postgreSQL/insertXlsxFail";
	}

	/* 데이터 입력 페이지로 이동 */
	@GetMapping("/insertIntoPostgre")
	public String insertPostgres() {
		return "postgreSQL/insertPostgres";
	}

	/* 회원정보 가져와서 회원 목록 페이지로 이동 */
	@GetMapping("/memberList")
	public String getMember() {
		return "member/getMemberList";
	}
	
	/* Utterance 테이블 가져오기 */
	@GetMapping("/getUtteranceTable/{format}")
	public String getUtteranceTable(Model model, @PathVariable Integer format){
		List<Utterance> utterances = postgreService.getUtteranceUsingMetadataId(format);
 		Metadata metadata = postgreService.getMetadataAndProgramUsingId(format);
		model.addAttribute("utterances",utterances);
		model.addAttribute("metadata",metadata);
		return "postgreSQL/getUtterance";
	}

	/* EojeolList 테이블 가져오기 */
	@GetMapping("/getEojeolList/{format}")
	public String getEojeolList(Model model, @PathVariable String format){
		List<EojeolList> eojeolLists = postgreService.getEojeolListUsingUtteranceId(format);
		model.addAttribute("eojeollist",eojeolLists);
		return "postgreSQL/getEojeolList";
	}
	
	/* Metadata & Program 조인해서 가져오기 */
	@GetMapping("/getMetadataAndProgram")
	public String getMetadataAndProgram(Model model) {
		List<Metadata> metadata = postgreService.getMetadataAndProgram();
		model.addAttribute("result", metadata);
		return "postgreSQL/getTable";
	}

	/* JsonLog 테이블 가져오기 */
	@GetMapping("/getJsonLog")
	public String getJsonLog(Model model){
		List<JsonLog> jsonLogs = postgreService.getJsonLog();
		model.addAttribute("jsonLog",jsonLogs);

		return "postgreSQL/getJsonLog";
	}
}
