package kr.com.inspect.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.com.inspect.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import kr.com.inspect.service.PostgreService;

/**
 * PostgreSQL 컨트롤러
 * @author Yeonhee Kim
 * @author Wooyoung Lee
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
	 * 다운받을 json 파일을 저장할 경로
	 */
	@Value("${output.json.directory}")
	private String jsonOutputPath;
	
//	/**
//	 * 업로드될 JSON 파일이 담기는 경로<br>
//	 * (곧바로 파싱 후 DB에 등록되고 삭제됨. 일반 사용자가 사용하는 경로.)
//	 */
//	@Value("${input.uploadJson.directory}")
//	private String uploadJsonPath;

	/**
	 * 업로드될 xlsx 파일이 담기는 경로<br>
	 * (곧바로 파싱 후 DB에 등록되고 삭제됨. 일반 사용자가 사용하는 경로.)
	 */
	@Value("${input.uploadXlsx.directory}")
	private String uploadXlsxPath;

	/**
	 * 업로드될 JSON 파일이 담기는 경로<br>
	 * (주기적으로 모니터링하여 일정한 시간에 파일이 있으면 파싱 후 DB에 등록하고 삭제함. <br>
	 * 관리자와 사용자가 모두 사용하는 경로.)
	 */
	@Value("${input.json.directory}")
	private String jsonPath;

	/**
	 * 데이터 입력 페이지로 이동
	 * @return 페이지 값 리턴
	 */
	@GetMapping("/insertIntoPostgre")
	public String insertPostgres() {
		return "postgreSQL/insertPostgres";
	}
	
	
	/**
	 * metadata 아이디로 JSON 파일을 다운받음
	 * @param request 요청 객체
	 * @param response 응답 객체
	 * @param file 요청의 종류(다운로드/메일)
	 * @param metaId metadata id
	 */
	@GetMapping("/makeMetadataJSON")
	@ResponseBody
	public void makeMetadataJSON(HttpServletRequest request, HttpServletResponse response, String file, int metaId) {
		Member member = (Member)request.getSession().getAttribute("member");
		String email = member.getEmail();
		postgreService.makeMetadataJSON(response, file, email, metaId, jsonOutputPath);
	}

	/**
	 * json 파일 jsonPath 에 업로드
	 * @param multipartFile 파일업로드 기능 구현
	 * @return 업로드가 성공적으로 되면 true반환
	 * @throws Exception 에외처리
	 */
	@RequestMapping(value = "/jsonUpload", method = RequestMethod.POST)
	@ResponseBody
	public String jsonUpload (@RequestParam("jsonFile") List<MultipartFile> multipartFile) throws Exception{
		postgreService.insertJSONUpload(jsonPath, multipartFile);
		return "true";
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
	 * wav 파일 postgresql 에 업로드
	 * @param multipartFile 파일업로드 기능 구현
	 * @return 오류없이 업로드되면 "true" 반환
	 * @throws Exception 에외처리
	 */
	@RequestMapping(value = "/wavUpload", method = RequestMethod.POST)
	@ResponseBody
	public String wavUpload (@RequestParam("wavFile") List<MultipartFile> multipartFile) throws Exception{
		postgreService.uploadWav(multipartFile);

		return "true";
	}

	/**
	 * Utterance 테이블 가져오기
	 * @param model 속성부여
	 * @param format metadata id
	 * @param request 사용자로부터 들어온 요청
	 * @return Utterance 페이지 리턴
	 */
	@GetMapping("/getUtteranceTable/{format}")
	public String getUtteranceTable(Model model, @PathVariable Integer format, HttpServletRequest request){
		List<Utterance> utterances = postgreService.getUtteranceUsingMetadataId(format);
 		Metadata metadata = postgreService.getMetadataAndProgramUsingId(format);
		model.addAttribute("utterances",utterances);
		model.addAttribute("metadata",metadata);
		postgreService.wavFileCopy(metadata.getTitle(), request);
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
	 * utterance 수정
	 * @param form 수정할 텍스트
	 * @param id 수정할 utterance 의 id
	 * @return 수정 성공여부
	 */
	@RequestMapping(value = "/editUtterance", method = RequestMethod.POST)
	@ResponseBody
	public String editUtterance(@RequestParam("form") String form, @RequestParam("id") String id){
		postgreService.editUtterance(id, form);

		return "true";
	}

	/**
	 * 사용기록 페이지에서 전사데이터 페이지로 이동
	 * @param model 속성부여
	 * @param data metadata id
	 * @param request 사용자로부터 들어온 요청
	 * @return Utterance 페이지 리턴
	 */
	@GetMapping("goUtterance")
	public String goUtterance(Model model, int data, HttpServletRequest request){
		UtteranceLog utteranceLog = postgreService.getUtteranceLogByUsingNo(data);
		int format = utteranceLog.getMetadata_id();
		getUtteranceTable(model, utteranceLog.getMetadata_id(), request);
		List<Utterance> utterances = postgreService.getUtteranceUsingMetadataId(format);
		Metadata metadata = postgreService.getMetadataAndProgramUsingId(format);
		model.addAttribute("utterances",utterances);
		model.addAttribute("metadata",metadata);
		postgreService.wavFileCopy(metadata.getTitle(), request);
		return "postgreSQL/getUtterance";

	}

}
