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
 * 
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

@Controller
@PropertySource(value = "classpath:properties/directory.properties")
public class PostgreController {
	
	/**
	 * 
	 */
	@Autowired
	private PostgreService postgreService;
	
	/**
	 * 엘라스틱서치
	 */
	private String index = "audiolist";
	
	/**
	 * 
	 */
	@Value("${input.uploadJson.directory}")
	private String uploadJsonPath;
	
	/**
	 * 
	 */
	@Value("${input.uploadXlsx.directory}")
	private String uploadXlsxPath;
	
	/**
	 * 
	 */
	@Value("${input.json.directory}")
	private String jsonPath;
	
	/**
	 * 
	 */
	@Value("${input.xlsx.directory}")
	private String xlsxPath;
	
	/**
	 * 엘라스틱서치 특정 인덱스를 PostgreSQL 특정 테이블에 넣기
	 * @return
	 */
	@GetMapping("/insertElasticIndexIntoPostgre")
	public String insertElasticIndexIntoPostgre() {
		postgreService.insertElasticIndex(index);
		return "postgreSQL/insertElasticIndex";
	}
	
	/**
	 * 데이터 입력 페이지로 이동
	 * @return
	 */
	@GetMapping("/insertIntoPostgre")
	public String insertPostgres() {
		return "postgreSQL/insertPostgres";
	}

	/**
	 * json 파일 postgresql 에 업로드
	 * @param multipartFile
	 * @return
	 * @throws Exception
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
	 * @param multipartFile
	 * @return
	 * @throws Exception
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
	 * json파일 서버 디렉토리에서 업로드
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/jsonDir", method = RequestMethod.POST)
	@ResponseBody
	public String jsonDir () throws Exception{
		return postgreService.insertJSONDir(jsonPath);
	}

	/**
	 * xlsx파일 서버 디렉토리에서 업로드
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/xlsxDir")
	@ResponseBody
	public String xlsxDir () throws Exception{
		return postgreService.insertXlsxDir(xlsxPath);
	}

	/**
	 * Utterance 테이블 가져오기
	 * @param model
	 * @param format
	 * @return
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
	 * @param model
	 * @param format
	 * @return
	 */
	@GetMapping("/getEojeolList/{format}")
	public String getEojeolList(Model model, @PathVariable String format){
		List<EojeolList> eojeolLists = postgreService.getEojeolListUsingUtteranceId(format);
		model.addAttribute("eojeollist",eojeolLists);
		model.addAttribute("metadata",postgreService.getMetadataAndProgramUsingId(eojeolLists.get(0).getMetadata_id()));
		model.addAttribute("utterance",postgreService.getUtteranceUsingId(eojeolLists.get(0).getUtterance_id()));
		return "postgreSQL/getEojeolList";
	}
	
	/*  */
	/**
	 * Metadata & Program 조인해서 가져오기
	 * @param model
	 * @return
	 */
	@GetMapping("/getMetadataAndProgram")
	public String getMetadataAndProgram(Model model) {
		List<Metadata> metadata = postgreService.getMetadataAndProgram();
		model.addAttribute("result", metadata);
		return "postgreSQL/getTable";
	}

	/**
	 * JsonLog 테이블 가져오기
	 * @param model
	 * @return
	 */
	@GetMapping("/getJsonLog")
	public String getJsonLog(Model model){
		List<JsonLog> jsonLogs = postgreService.getJsonLog();
		model.addAttribute("jsonLog",jsonLogs);

		return "postgreSQL/getJsonLog";
	}
}
