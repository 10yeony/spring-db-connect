package kr.com.inspect.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.com.inspect.dao.RuleDao;
import kr.com.inspect.dto.CustomLibrary;
import kr.com.inspect.dto.ResponseData;
import kr.com.inspect.dto.Rule;
import kr.com.inspect.dto.RuleLog;
import kr.com.inspect.dto.UsingLog;
import kr.com.inspect.paging.CommonDto;
import kr.com.inspect.paging.CommonForm;
import kr.com.inspect.paging.PagingResponse;
import kr.com.inspect.paging.PagingUtil;
import kr.com.inspect.rule.RuleCompiler;
import kr.com.inspect.service.RuleService;
import kr.com.inspect.util.ClientInfo;
import kr.com.inspect.util.UsingLogUtil;

/**
 * 전사규칙에 관한 Service 구현 클래스
 * @author Yeonhee Kim
 * @version 1.0
 */
@Service
@PropertySource(value = "classpath:properties/directory.properties")
public class RuleServiceImpl implements RuleService {

	@Value("${input.custom.directory}")
	private String customPath;
	
	@Autowired
	private ClientInfo clientInfo;
	
	/**
	 * 전사규칙에 관한 DAO 인터페이스
	 */
	@Autowired
	private RuleDao ruleDao;
	
	/**
	 * 사용자의 사용 로그 기록을 위한 UsingLogUtil 객체
	 */
	@Autowired
	private UsingLogUtil usingLogUtil;
	
	@Autowired
	private PagingResponse pagingResponse;

	/**
	 * Rule 객체
	 */
	private Rule rule;

	/**
	 * 전사규칙 자바 컴파일러 객체
	 */
	private RuleCompiler ruleCompiler = new RuleCompiler();


	/**
	 * 전사규칙 대분류, 중분류, 소분류 카테고리 리스트를 반환함
	 * @param top_level_id    전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @return 전사규칙 대분류, 중분류, 소분류 카테고리 리스트
	 */
	@Override
	public List<Rule> getRuleCategory(String top_level_id, String middle_level_id) {

		List<Rule> list = new ArrayList<>();

		/* 대분류 목록 불러오기 */
		if (top_level_id == "" && middle_level_id == "") {
			list = ruleDao.getAllRuleTopLevel();
		}

		/* 중분류 목록 불러오기 */
		else if (middle_level_id == "") {
			list = ruleDao.getAllRuleMiddleLevel(Integer.parseInt(top_level_id));
		}

		/* 소분류 카테고리 목록 불러오기 */
		else {
			list = ruleDao.getAllRuleBottomLevel(Integer.parseInt(top_level_id), Integer.parseInt(middle_level_id));
		}
		return list;
	}

	/**
	 * 선택된 카테고리에 해당되는 전사규칙 리스트를 조인해서 가져옴
	 * @param top_level_id    전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @param bottom_level_id 전사규칙 소분류 아이디
	 * @return 선택된 카테고리에 해당되는 전사규칙 리스트
	 */
	@Override
	public List<Rule> getRuleListUsingJoin(String top_level_id, String middle_level_id, String bottom_level_id) {

		List<Rule> list = new ArrayList<>();

		/* 전사규칙 리스트를 조인 */
		if (top_level_id == "" && middle_level_id == "" && bottom_level_id == "") {
			list = ruleDao.getRuleList();
		}

		/* 대분류 아이디를 통해 전사규칙 리스트를 조인 */
		else if (middle_level_id == "" && bottom_level_id == "") {
			list = ruleDao.getRuleListByTopId(Integer.parseInt(top_level_id));
		}

		/* 대분류, 중분류 아이디를 통해 전사규칙 리스트를 조인 */
		else if (bottom_level_id == "") {
			list = ruleDao.getRuleListByTopMiddleId(Integer.parseInt(top_level_id), Integer.parseInt(middle_level_id));
		}

		/* 대분류, 중분류, 소분류 아이디를 통해 전사규칙 리스트를 조인 */
		else {
			list = ruleDao.getRuleListByTopMiddleBottomId(Integer.parseInt(top_level_id),
					Integer.parseInt(middle_level_id), Integer.parseInt(bottom_level_id));
		}
		return list;
	}
	
	/**
	 * 룰 로그 테이블을 페이징 처리하여 가져옴
	 * @param data RuleLog 테이블의 외래키인 using_log_no
	 * @param function_name 페이지의 번호를 클릭했을 때 호출되는 자바스크립트 함수명 또는 게시글 조회를 요청하는 함수명을 저장할 변수
	 * @param current_page_no 현재 화면에 출력되고 있는 페이지 번호 또는 페이지의 번호를 클릭했을 때에 번호를 저장할 변수
	 * @param count_per_page 한 화면에 출력되는 페이지의 수를 저장할 변수
	 * @param count_per_list 한 화면에 출력되는 게시글의 수를 저장할 변수
	 * @param search_word 검색어
	 * @return 룰 로그 테이블
	 */
	public ResponseData getRuleLog(int using_log_no,
									String function_name, 
									int current_page_no,
									int count_per_page,
									int count_per_list,
									String search_word) {
		
		CommonDto commonDto = new CommonDto();
		int totalCount = 0;
		totalCount = ruleDao.getAllCountOfRuleLog(using_log_no, search_word); 
		
		if (totalCount > 0) {
			commonDto = commonDto.setCommonDto(function_name, current_page_no, count_per_page, count_per_list, totalCount);
		}
		int limit = commonDto.getLimit();
		int offset = commonDto.getOffset();
		List<RuleLog> list = ruleDao.getAllRuleLog(using_log_no, limit, offset, search_word);
		String pagination = commonDto.getPagination();
		
		ResponseData responseData = pagingResponse.getResponseData(list, totalCount, pagination);
		return responseData;
	}

	/**
	 * 대분류/중분류/소분류를 DB에 등록함
	 * @param level 해당되는 분류(대분류/중분류/소분류)
	 * @param rule  DB 등록을 위한 Rule 객체
	 * @return DB에 등록한 row의 수
	 */
	public int registerRule(String level, Rule rule) {
		int id = 0; // 아이디
		int result = 0; // 등록 후 DB에 추가된 row의 수
		String content = null;
		
		switch (level) {
		case "top":
			id = ruleDao.isExistTopLevel(rule);
			if (id == 0) { // 존재하지 않는 경우에만 등록
				result = ruleDao.registerTopLevel(rule);
				if(result > 0) {
					content = "Rule 대분류 등록";
				}
			}
			break;
		case "middle":
			id = ruleDao.isExistMiddleLevel(rule);
			if (id == 0) { // 존재하지 않는 경우에만 등록
				result = ruleDao.registerMiddleLevel(rule);
				if(result > 0) {
					content = "Rule 중분류 등록";
				}
			}
			break;
		case "bottom":
			id = ruleDao.isExistBottomLevel(rule); // 등록 전 아이디(중복 확인)
			if (id == 0) { // 존재하지 않는 경우에만 등록
				result = ruleDao.registerBottomLevel(rule);
				id = ruleDao.isExistBottomLevel(rule); // 등록 후 아이디(auto increment된 아이디)

				/* 파일명 DB 등록(파일명이 중복되지 않도록 auto increment된 아이디 사용) */
				String fileName = "Rule" + id;
				rule.setBottom_level_id(id);
				rule.setFile_name(fileName);
				result += ruleDao.updateBottomLevelFileName(rule);
				if(result > 0) {
					content = "Rule 소분류 등록";
				}
			}
			break;
		}
		
		RuleLog ruleLog = new RuleLog();
		ruleLog.setContent(content);
		ruleLog.setTop_level_name(rule.getTop_level_name());
		ruleLog.setMiddle_level_name(rule.getMiddle_level_name());
		ruleLog.setBottom_level_name(rule.getBottom_level_name());
		usingLogUtil.setUsingLog(ruleLog);
		return result;
	}

	/**
	 * 대분류/중분류/소분류 아이디로 해당되는 항목을 삭제함
	 * @param level 해당되는 분류(대분류/중분류/소분류)
	 * @param id    대분류/중분류/소분류 아이디
	 * @return DB에서 삭제한 row의 수
	 */
	@Override
	public int deleteRule(String level, int id, String name) {
		int result = 0;
		String content = null;
		switch (level) {
		case "top":
			content = "Rule 대분류 삭제";
			result = ruleDao.deleteTopLevel(id);
			break;
		case "middle":
			content = "Rule 중분류 삭제";
			result = ruleDao.deleteMiddleLevel(id);
			break;
		case "bottom":
			content = "Rule 소분류 삭제";
			
			/* 자바 파일, 클래스 파일 삭제 */
			Rule rule = ruleDao.getRuleBottomLevel(id);
			name = rule.getBottom_level_name();
			String fileName = rule.getFile_name();
			deleteJavaClassFile(fileName);

			/* DB에서 소분류 삭제 */
			result = ruleDao.deleteBottomLevel(id);
			break;
		}
		if(result > 0) {
			UsingLog usingLog = new UsingLog();
			usingLog.setContent(content+name);
			usingLogUtil.setUsingLog(usingLog);
		}
		return result;
	}

	@Override
	public Rule getRuleBottomLevel(int bottom_level_id) {
		return ruleDao.getRuleBottomLevel(bottom_level_id);
	}

	/**
	 * 사용자가 입력한 Rule 코드를 DB에 업데이트함
	 * @param rule 코드 업데이트를 위한 Rule 객체
	 * @return DB에 업데이트된 row의 수
	 * @throws Exception 예외
	 */
	@Override
	public Map<String, Object> updateContents(Rule rule) {
		boolean compile = false;
		int result = ruleDao.updateContents(rule);
		// System.out.println(result);
		Rule vo = ruleDao.getRuleBottomLevel(rule.getBottom_level_id());
		// System.out.println(vo);
		Object obj = null;
		try {
			ruleCompiler.create(vo);
			obj = ruleCompiler.runObject(vo); // 실행 결과값
			if (obj == null) {
				obj = "null";
			}
			compile = true;
		} catch (Exception e) {
			obj = getStringOfException(e); // 예외 문자열
		}

		/* 자바 파일 및 클래스 파일 삭제 */
		deleteJavaClassFile(vo.getFile_name());

		/* 컴파일 결과값 DB에 등록 */
		rule.setResult(obj.toString());
		int updateResult = ruleDao.updateRuleCompileResult(rule);
		if(updateResult > 0) {
			RuleLog ruleLog = new RuleLog();
			ruleLog.setTop_level_name(vo.getTop_level_name());
			ruleLog.setMiddle_level_name(vo.getMiddle_level_name());
			ruleLog.setBottom_level_name(vo.getBottom_level_name());
			ruleLog.setContent("Rule 작성");
			usingLogUtil.setUsingLog(ruleLog);
		}

		/* 리턴값 세팅 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("compile", compile); // 컴파일 결과
		map.put("updateResult", updateResult); // 업데이트된 DB row의 수
		map.put("object", obj); // 실행 결과값 또는 예외 메세지
		return map;
	}

	/**
	 * Rule 클래스 파일을 실행시킴
	 * @param list Rule 목록
	 * @throws Exception 예외
	 */
	@Override
	public void runRuleCompiler(List<Rule> list) throws Exception {
		if(list.size() == 0) {
			return;
		}
		String usingLogContent = "Rule 실행 - 총 "+list.size()+"개";
		final int no = usingLogUtil.insertUsingLog(usingLogContent);
		final String ip_addr = clientInfo.getIpAddr();
		final String member_id = clientInfo.getMemberId();
		final String time = clientInfo.getTime();
		final String ruleLogContent = "Rule 실행";
		
		int threadCnt = 5; // 스레드 개수 설정
		ExecutorService executor = Executors.newFixedThreadPool(threadCnt);
		List<Future<?>> futures = new ArrayList<>();

		for (Rule rule : list) {
			futures.add(executor.submit(() -> {
				// String threadName = Thread.currentThread().getName();
				Object obj = null;
				try {
					ruleCompiler.create(rule);
					obj = ruleCompiler.runObject(rule); // 실행 결과값
				} catch (Exception e) {
					obj = getStringOfException(e); // 예외 문자열
				}

				/* 자바 파일 및 클래스 파일 삭제 */
				deleteJavaClassFile(rule.getFile_name());

				/* 컴파일 결과값 DB에 등록 */
				rule.setResult(obj.toString());
				int updateResult = ruleDao.updateRuleCompileResult(rule);
				
				if(updateResult > 0) {
					RuleLog ruleLog = new RuleLog();
					ruleLog.setUsing_log_no(no);
					ruleLog.setIp_addr(ip_addr);
					ruleLog.setMember_id(member_id);
					ruleLog.setTime(time);
					ruleLog.setContent(ruleLogContent);
					ruleLog.setTop_level_id(rule.getTop_level_id());
					ruleLog.setTop_level_name(rule.getTop_level_name());
					ruleLog.setMiddle_level_id(rule.getMiddle_level_id());
					ruleLog.setMiddle_level_name(rule.getMiddle_level_name());
					ruleLog.setBottom_level_id(rule.getBottom_level_id());
					ruleLog.setBottom_level_name(rule.getBottom_level_name());
					usingLogUtil.setUsingLog(ruleLog);
				}
			}));
		}

		for (Future<?> future : futures) {
			try {
				future.get(); // 스레드 작업이 종료될 때까지 기다림
			} catch (InterruptedException e) {
				// e.printStackTrace();
			} catch (ExecutionException e) {
				// e.printStackTrace();
			}
		}
		executor.shutdownNow(); // Task 종료
	}

	/**
	 * 예외 발생시 예외를 문자열로 바꿔서 리턴함
	 * @param e 예외
	 * @return 문자열로 변환된 예외
	 */
	public String getStringOfException(Exception e) {
		/* 에러메세지를 문자열로 바꿈 */
		StringWriter error = new StringWriter();
		e.printStackTrace(new PrintWriter(error));
		return error.toString();
	}

	/**
	 * 컴파일 오류시 자바 파일과 클래스 파일을 삭제함
	 * @param fileName 자바 및 클래스 파일명
	 */
	public void deleteJavaClassFile(String fileName) {
		String s = File.separator;
		String packagePath = "kr" + s + "com" + s + "inspect" + s + "rule" + s;
		String javaPath = ruleCompiler.getPath() + fileName + ".java";
		String classPath = ruleCompiler.getClassPath() + packagePath + fileName + ".class";
		// System.out.println("javaPath : " + javaPath);
		// System.out.println("classPath : " + classPath);

		File[] fileArr = new File[2];
		fileArr[0] = new File(javaPath);
		fileArr[1] = new File(classPath);

		for (File file : fileArr) {
			if (file.exists()) {
				if (file.delete()) {
					// System.out.println("파일 삭제 성공");
				} else {
					// System.out.println("파일 삭제 실패");
				}
			} else {
				// System.out.println("파일이 존재하지 않습니다.");
			}
		}
	}

	/**
	 * 클래스 아이디로 클래스 정보, 필드 정보, 생성자 정보, 메소드 정보를 가져옴
	 * @param class_id DB 상의 클래스 아이디
	 * @return 클래스에 관한 전반적인 정보를 담은 응답 객체
	 */
	public Map<String, Object> getApiDesc(int class_id) {
		Map<String, Object> map = new HashMap<>();

		Thread classThread = new Thread(new Runnable() {
			@Override
			public void run() {
				map.put("class", ruleDao.getApiClass(class_id));
			}
		});
		Thread fieldThread = new Thread(new Runnable() {
			@Override
			public void run() {
				map.put("field", ruleDao.getApiClassField(class_id));
			}
		});
		Thread constructorThread = new Thread(new Runnable() {
			@Override
			public void run() {
				map.put("constructor", ruleDao.getApiClassConstructor(class_id));
			}
		});
		Thread methodThread = new Thread(new Runnable() {
			@Override
			public void run() {
				map.put("method", ruleDao.getApiClassMethod(class_id));
			}
		});

		classThread.start();
		fieldThread.start();
		constructorThread.start();
		methodThread.start();

		try {
			classThread.join();
			fieldThread.join();
			constructorThread.join();
			methodThread.join();
		} catch (InterruptedException e) {
			// e.printStackTrace();
		}
		return map;
	}

	/**
	 * 사용자가 import하고자 하는 커스텀 라이브러리 파일을 업로드함 
	 * @param customFile 사용자가 업로드한 커스텀 라이브러리 파일
	 * @param customLibrary 커스텀 라이브러리 객체
	 * @throws Exception 예외
	 */
	public void uploadCustomLibrary(List<MultipartFile> customFile, CustomLibrary customLibrary) throws Exception {
		if(customFile.size() == 0) {
			return;
		}
		
		File fileDir = new File(customPath + customLibrary.getCreator() + File.separator); //Original Directory
		if(!fileDir.exists()){
			fileDir.mkdir();
		}
		
		UsingLog usingLog = new UsingLog();
		usingLog.setContent("Rule 관련 라이브러리 등록 - 총 "+customFile.size()+"개");
		usingLogUtil.setUsingLog(usingLog);
		final int no = usingLogUtil.getNoOfUsingLog(usingLog);
			
		int threadCnt = 5;
		ExecutorService executor = Executors.newFixedThreadPool(threadCnt);
		List<Future<?>> futures = new ArrayList<>();
		
		/* 사용자가 업로드한 커스텀 라이브러리 파일을 서버 스토리지에 생성 */
		for (MultipartFile cus : customFile) {
			futures.add(executor.submit(() -> {
				
				/* 업로드 파일 생성 */
				String filename = cus.getOriginalFilename();
				String fileFormat = filename.substring(filename.lastIndexOf(".")+1, filename.length());
				
				File f = null;
				if(fileFormat.equals("class")) { //class 파일일 때
					String classPackage = customLibrary.getClass_package();
					String[] classPackageArr = classPackage.split("[.]");
					String packagePath = fileDir + File.separator;
					for(int i=0; i<classPackageArr.length-1; i++) {
						packagePath += classPackageArr[i] + File.separator;
						File folder = new File(packagePath);
						if(!folder.exists()) {
							folder.mkdir();
						}
					}
					f = new File(packagePath + File.separator + filename);
				}else if(fileFormat.equals("jar")) { //jar 파일일 때
					customLibrary.setClass_package("");
					f = new File(fileDir + File.separator + filename);
				}
				try {
					cus.transferTo(f);
				} catch (IllegalStateException | IOException e) {
					//e.printStackTrace();
				}
				
				/* DB 저장 */
				customLibrary.setFile_name(filename);
				int result = registerCustomLibrary(customLibrary);
				
				if(result > 0) {
					RuleLog ruleLog = new RuleLog();
					ruleLog.setLibrary_file_name(filename);
					ruleLog.setContent("Rule 관련 라이브러리 등록");
					ruleLog.setUsing_log_no(no);
					usingLogUtil.setUsingLog(ruleLog);
				}
			}));
		}
		closeThread(executor, futures);
	}

	/**
	 * 사용자가 import하고자 하는 커스텀 라이브러리를 등록함
	 * @param customLibrary
	 * @return DB에 추가된 row의 수
	 */
	@Override
	public int registerCustomLibrary(CustomLibrary customLibrary) {
		int result = 0; // 등록 후 DB에 추가된 row의 수
		
		/* 이미 DB에 등록한 파일명인지 중복검사 */
		boolean flag = false;
		List<CustomLibrary> list = ruleDao.getAllCustomLibraryByCreator(customLibrary.getCreator());
		for(CustomLibrary library : list) {
			if(customLibrary.getFile_name().equals(library.getFile_name())) {
				flag = true;
			}
		}
		if(flag == false) {
			result = ruleDao.registerCustomLibrary(customLibrary);
			result = 1;
		}
		
		// class 파일이라면 package 업데이트
		else if((flag == true)&&(customLibrary.getFile_name().substring(customLibrary.getFile_name().lastIndexOf(".")+1).equals("class"))){
			ruleDao.updateCustomLibraryPackage(customLibrary);
		}
		
		return result;
	}
	
	/**
	 * 사용자 아이디로 사용자가 추가한 커스텀 라이브러리 목록을 가져옴
	 * @param creator 사용자 아이디
	 * @return 사용자가 추가한 커스텀 라이브러리 목록
	 */
	@Override
	public List<CustomLibrary> getAllCustomLibraryByCreator(String creator){
		return ruleDao.getAllCustomLibraryByCreator(creator);
	}
	
	/**
	 * 해당되는 커스텀 라이브러리 파일을 삭제하고 DB에서도 삭제함
	 * @param customLibrary 삭제할 CustomLibrary 객체
	 * @return DB에서 삭제된 row의 수
	 */
	@Override
	public int deleteCustomLibrary(CustomLibrary customLibrary) {
		int result = 0;
		customLibrary = ruleDao.getCustomLibraryById(customLibrary.getId());
		
		File file = null;
		String fileName = customLibrary.getFile_name();
		
		/* class 파일 */
		if(fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()).equals("class")) {
			String classPackage = "";
			String[] classPackageArr = customLibrary.getClass_package().split("[.]");
			for(int i=0; i<classPackageArr.length-1; i++) {
				classPackage += classPackageArr[i] + File.separator;
			}
			file = new File(customPath 
					+ customLibrary.getCreator() 
					+ File.separator 
					+ classPackage
					+ customLibrary.getFile_name());
		}
		
		/* jar 파일 */
		else if(fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()).equals("jar")) {
			file = new File(customPath 
					+ customLibrary.getCreator() 
					+ File.separator 
					+ customLibrary.getFile_name());
		}
		if (file.exists()) {
			if (file.delete()) {
				// System.out.println("파일 삭제 성공");
			} else {
				// System.out.println("파일 삭제 실패");
			}
		} else {
			// System.out.println("파일이 존재하지 않습니다.");
		}
		
		result = ruleDao.deleteCustomLibrary(customLibrary.getId());
		if(result > 0) {
			RuleLog ruleLog = new RuleLog();
			ruleLog.setLibrary_file_name(customLibrary.getFile_name());
			ruleLog.setContent("Rule 관련 라이브러리 삭제");
			usingLogUtil.setUsingLog(ruleLog);
		}
		return result;
	}
	
	/**
	 * 스레드 작업이 종료될 때까지 기다리고 Task를 종료함
	 * @param executor ExecutorService
	 * @param futures List<Future<?>>
	 */
	public void closeThread(ExecutorService executor, List<Future<?>> futures) {
		for (Future<?> future : futures) {
			try {
				future.get(); // 스레드 작업이 종료될 때까지 기다림
			} catch (InterruptedException e) {
				// e.printStackTrace();
			} catch (ExecutionException e) {
				// e.printStackTrace();
			}
		}
		executor.shutdownNow(); // Task 종료
	}
}
