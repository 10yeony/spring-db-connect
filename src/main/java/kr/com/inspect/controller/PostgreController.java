package kr.com.inspect.controller;

import java.util.List;
import kr.com.inspect.dto.EojeolList;
import kr.com.inspect.dto.JsonLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Utterance;
import kr.com.inspect.service.PostgreService;
import org.springframework.web.multipart.MultipartFile;

/**
 * PostgreSQL 컨트롤러
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

@Controller
@PropertySource(value = "classpath:properties/directory.properties")
public class PostgreController {
	
	/**
	 * PostgreSQL 서비스 필드 선언
	 */
	@Autowired
	private PostgreService postgreService;
	
	/**
	 * 엘라스틱 서치에서 가져온 정보
	 */
	private String index = "audiolist";
	
	/**
	 * 업로드될 JSON 파일이 담기는 경로<br>
	 * (곧바로 파싱 후 DB에 등록되고 삭제됨. 일반 사용자가 사용하는 경로.)
	 */
	@Value("${input.uploadJson.directory}")
	private String uploadJsonPath;
	
	/**
	 * 업로드될 xlsx 파일이 담기는 경로<br>
	 * (주기적으로 모니터링하여 일정한 시간에 파일이 있으면 파싱 후 DB에 등록하고 삭제함. <br>
	 * 관리자가 사용하는 경로.)
	 */
	@Value("${input.uploadXlsx.directory}")
	private String uploadXlsxPath;
	
	/**
	 * 업로드될 JSON 파일이 담기는 경로<br>
	 * (주기적으로 모니터링하여 일정한 시간에 파일이 있으면 파싱 후 DB에 등록하고 삭제함. <br>
	 * 관리자가 사용하는 경로.)
	 */
	@Value("${input.json.directory}")
	private String jsonPath;
	
	/**
	 * 업로드될 xlsx 파일이 담기는 경로(관리자가 사용하는 경로.)
	 */
	@Value("${input.xlsx.directory}")
	private String xlsxPath;
	
	/**
	 * 엘라스틱서치 특정 인덱스를 PostgreSQL 특정 테이블에 넣기
	 * @return 엘라스틱서치의 Index 값을 리턴
	 */
	@GetMapping("/insertElasticIndexIntoPostgre")
	public String insertElasticIndexIntoPostgre() {
		postgreService.insertElasticIndex(index);
		return "postgreSQL/insertElasticIndex";
	}
	
	/**
	 * 데이터 입력 페이지로 이동
	 * @return 페이지 값 리턴
	 */
	@GetMapping("/insertIntoPostgre")
	public String insertPostgres() {
		return "postgreSQL/insertPostgres";
	}

	/**
	 * json 파일 postgresql 에 업로드
	 * @param multipartFile 파일업로드 기능 구현
	 * @return json파일 여부(true/false)에 따라 값을 반환
	 * @throws Exception 에외처리
	 */
	@RequestMapping(value = "/jsonUpload", method = RequestMethod.POST)
	@ResponseBody
	public String jsonUpload (@RequestParam("jsonFile") List<MultipartFile> multipartFile) throws Exception{
		boolean flag = postgreService.insertJSONUpload(uploadJsonPath, multipartFile);

		if(flag == true)
			return "true";
		else
			return "false";
	}

	/**
	 * xlsx 파일 postgresql 에 업로드
	 * @param multipartFile 파일업로드 기능 구현
	 * @return xlsx파일 여부(true/false)에 따라 값을 반환
	 * @throws Exception 예외처리
	 */
	@RequestMapping(value = "/xlsxUpload", method = RequestMethod.POST)
	@ResponseBody
	public String xlsxUpload (@RequestParam("xlsxFile") List<MultipartFile> multipartFile) throws Exception{
		boolean flag = postgreService.insertXlsxUpload(uploadXlsxPath, multipartFile);

		if(flag == true)
			return "true";
		else
			return "false";
	}

	/**
	 * json파일을 서버 디렉토리에서 업로드
	 * @return 디렉토리 값 반환
	 * @throws Exception 예외 처리
	 */
	@RequestMapping(value = "/jsonDir", method = RequestMethod.POST)
	@ResponseBody
	public String jsonDir () throws Exception{
		return postgreService.insertJSONDir(jsonPath);
	}

	/**
	 * xlsx파일 서버 디렉토리에서 업로드
	 * @return 디렉토리 값 반환
	 * @throws Exception 예외 처리
	 */
	@PostMapping("/xlsxDir")
	@ResponseBody
	public String xlsxDir () throws Exception{
		return postgreService.insertXlsxDir(xlsxPath);
	}

	/**
	 * Utterance 테이블 가져오기
	 * @param model 속성부여
	 * @param format 테이블 조인값
	 * @return Utterance 값 리턴
	 */
	@GetMapping("/getUtteranceTable/{format}")
	public String getUtteranceTable(Model model, @PathVariable Integer format){
		List<Utterance> utterances = postgreService.getUtteranceUsingMetadataId(format);
 		Metadata metadata = postgreService.getMetadataAndProgramUsingId(format);
		model.addAttribute("utterances",utterances);
		model.addAttribute("metadata",metadata);
		return "postgreSQL/getUtterance";
	}

	/**
	 * EojeolList 테이블 가져오기 
	 * @param model 속성부여
	 * @param format 테이블 조인값
	 * @return EojeoList 값 리턴
	 */
	@GetMapping("/getEojeolList/{format}")
	public String getEojeolList(Model model, @PathVariable String format){
		List<EojeolList> eojeolLists = postgreService.getEojeolListUsingUtteranceId(format);
		model.addAttribute("eojeollist",eojeolLists);
		model.addAttribute("metadata",postgreService.getMetadataAndProgramUsingId(eojeolLists.get(0).getMetadata_id()));
		model.addAttribute("utterance",postgreService.getUtteranceUsingId(eojeolLists.get(0).getUtterance_id()));
		return "postgreSQL/getEojeolList";
	}
	
	/**
	 * Metadata & Program 조인해서 가져오기
	 * @param model 속성부여
	 * @return 해당 페이지로 값 리턴
	 */
	@GetMapping("/getMetadataAndProgram")
	public String getMetadataAndProgram(Model model) {
		List<Metadata> metadata = postgreService.getMetadataAndProgram();
		model.addAttribute("result", metadata);
		return "postgreSQL/getTable";
	}

	/**
	 * JsonLog 테이블 가져오기
	 * @param model 속성부여
	 * @return 해당 페이지로 값 리턴
	 */
	@GetMapping("/getJsonLog")
	public String getJsonLog(Model model){
		List<JsonLog> jsonLogs = postgreService.getJsonLog();
		model.addAttribute("jsonLog",jsonLogs);

		return "postgreSQL/getJsonLog";
	}
}
