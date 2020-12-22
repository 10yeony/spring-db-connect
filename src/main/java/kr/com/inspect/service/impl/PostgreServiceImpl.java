package kr.com.inspect.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.session.SqlSession;
import org.elasticsearch.search.SearchHit;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.com.inspect.dao.ElasticDao;
import kr.com.inspect.dao.MemberDao;
import kr.com.inspect.dao.PostgreDao;
import kr.com.inspect.dto.EojeolList;
import kr.com.inspect.dto.JsonLog;
import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Program;
import kr.com.inspect.dto.ResponseData;
import kr.com.inspect.dto.Speaker;
import kr.com.inspect.dto.UsingLog;
import kr.com.inspect.dto.Utterance;
import kr.com.inspect.paging.CommonDto;
import kr.com.inspect.paging.CommonForm;
import kr.com.inspect.paging.PagingUtil;
import kr.com.inspect.parser.JsonParsing;
import kr.com.inspect.parser.XlsxParsing;
import kr.com.inspect.service.PostgreService;
import kr.com.inspect.util.UsingLogUtil;
/**
 * PostgreSQL Service
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

@Service
@PropertySource(value = "classpath:properties/directory.properties")
public class PostgreServiceImpl implements PostgreService{

	/**
	 * 로그 출력을 위한 logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(PostgreServiceImpl.class);

	/**
	 * 엘라스틱 dao 필드 선언
	 */
	@Autowired
	private ElasticDao elasticDao;
	
	/**
	 * PostgreSQL dao 필드 선언
	 */
	@Autowired
	private PostgreDao postgreDao;
	
	/**
	 * 사용자의 사용 로그 기록을 위한 UsingLogUtil 객체
	 */
	@Autowired
	private UsingLogUtil usingLogUtil;
	
	/**
	 * 세션 필드 선언
	 */
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * metadata mapper 필드 선언
	 */
	private final String metadataNS = "MetadataMapper.";
	/**
	 * ProgramMapper 필드 선언
	 */
	private final String programNS = "ProgramMapper.";
	/**
	 * SpeakerMapper 필드 선언
	 */
	private final String speakerNS = "SpeakerMapper.";
	/**
	 * UtteranceMapper 필드 선언
	 */
	private final String utteranceNS = "UtteranceMapper.";
	/**
	 * EojeolListMapper 필드 선언
	 */
	private final String eojeolListNS = "EojeolListMapper.";
	/**
	 * JsonLogMapper 필드 선언
	 */
	private final String jsonLogNS = "JsonLogMapper.";
	/**
	 * jsonParsing 필드 선언
	 */
	private JsonParsing jsonParsing = new JsonParsing();
	/**
	 * XlsxParsing 필드 선언
	 */
	private XlsxParsing xlsxParsing = new XlsxParsing();
	/**
	 * 음성파일 저장 경로
	 */
	@Value("${input.sound.directory}")
	private String soundPath;
	
	/**
	 * 중복된 데이터 체크(아직 DB에 등록되지 않은 데이터일 경우 true)
	 */
	private boolean check;
	
	/**
	 * 엘라스틱 서치에서 받아온 인덱스를 PostgreSQL에 넣음(테스트)
	 * @param index
	 */
	@Override
	public void insertElasticIndex(String index) {
		// 인덱스를 통해 엘라스틱서치에서 데이터를 받아옴
		SearchHit[] searchHits = elasticDao.getIndex(index);

		//테스트용 VO인 Sound를 엘라스틱서치에서 PostgreSQL로 넣음(사용시 수정 필요)
//		for(SearchHit hit: searchHits) {
//			Map<String, Object> map = hit.getSourceAsMap();
//			Sound sound = new Sound();
//			sound.setId(hit.getId());
//			sound.setCategory((String)map.get("category"));
//			sound.setTitle((String)map.get("title"));
//			sound.setCompany((String)map.get("company"));
//			sound.setContent((String)map.get("content"));
//			
//			postgreInsertMapper.insertTestValue(sound); //PostgreSQL INSERT 쿼리문 필요
//		}
	}
	
	/**
	 * Metadata 테이블을 모두 가지고 옴
	 * @return Metadata 테이블들의 값을 리스트로 담아 리턴
	 */
	public List<Metadata> getMetadata(){
		return postgreDao.getMetadata();
	}

	/**
	 * JsonLog 테이블을 모두 가져옴
	 * @param function_name 페이지의 번호를 클릭했을 때 호출되는 자바스크립트 함수명 또는 게시글 조회를 요청하는 함수명을 저장할 변수
	 * @param current_page_no 현재 화면에 출력되고 있는 페이지 번호 또는 페이지의 번호를 클릭했을 때에 번호를 저장할 변수
	 * @param count_per_page 한 화면에 출력되는 페이지의 수를 저장할 변수
	 * @param count_per_list 한 화면에 출력되는 게시글의 수를 저장할 변수
	 * @param search_word 검색어
	 * @return JsonLog 테이블들의 값을 리스트로 담아 리턴
	 */
	public ResponseData getJsonLog(String function_name, 
										int current_page_no,
										int count_per_page,
										int count_per_list,
										String search_word){ 
		
		CommonDto commonDto = new CommonDto();
		int totalCount = postgreDao.getJsonLogCnt(search_word); //총 JsonLog의 row 수
		if (totalCount != 0) {
			CommonForm commonForm = new CommonForm();
			commonForm.setFunction_name(function_name);
			commonForm.setCurrent_page_no(current_page_no);
			commonForm.setCount_per_page(count_per_page);
			commonForm.setCount_per_list(count_per_list);
			commonForm.setTatal_list_count(totalCount);
			commonDto = PagingUtil.setPageUtil(commonForm);
		}
		int limit = commonDto.getLimit();
		int offset = commonDto.getOffset();
		
		List<JsonLog> list = new ArrayList<>();
		list = postgreDao.getJsonLog(limit, offset, search_word);
		
		ResponseData responseData = new ResponseData();
    	Map<String, Object> items = new HashMap<String, Object>();	
    	items.put("list", list);
    	items.put("totalCount", totalCount);
    	items.put("pagination", commonDto.getPagination());
		responseData.setItem(items);
		return responseData;	
	}
	
	/**
	 * id로 해당되는 Metadata 테이블을 가져옴
	 * @param id Metadata 테이블의 id 값
	 * @return id 값에 해당하는 metadata 테이블들의 값을 리턴
	 */
	public Metadata getMetadataById(Integer id) {
		return postgreDao.getMetadataById(id);
	}
	
	/**
	 *  metadataId로 해당되는 Utterance 테이블을 가져옴
	 *  @param metadataId Utterance 테이블의 metadataId 값
	 *  @return metadataId 값에 해당하는 Utterance 테이블들의 값을 리스트에 담아 리턴
	 */
	public List<Utterance> getUtteranceUsingMetadataId(Integer metadataId){
		return postgreDao.getUtteranceUsingMetadataId(metadataId);
	}
	
	/**
	 * utterance_id 를 이용하여 eojeollist 데이터 가져오기
	 * @param id eojeollist 테이블의 utterance_id 값
	 * @return utterance_id 값에 해당하는 eojeollist 테이블들의 값을 리스트에 담아 리턴
	 */
	public List<EojeolList> getEojeolListUsingUtteranceId(String id){
		return postgreDao.getEojeolListUsingUtteranceId(id);
	}
	
	/**
	 * JSON 파일들을 업로드해서 PostgreSQL에 넣음
	 * @param path 파일 디렉토리
	 * @param jsonFile json 파일
	 * @return DB의 데이터 여부를 확인하고 값을 리턴함
	 */
	@Override
	public void insertJSONUpload(String path, List<MultipartFile> jsonFile) throws Exception {
		UsingLog usingLog = new UsingLog();
		usingLog.setContent("JSON 파일 업로드");
		usingLogUtil.setUsingLog(usingLog);
		
		int threadCnt = 5;
		ExecutorService executor = Executors.newFixedThreadPool(threadCnt);
		List<Future<?>> futures = new ArrayList<>();
		
		/* 서버 디렉토리에 파일 저장 */
		for(MultipartFile json : jsonFile){
			futures.add(executor.submit(() -> {
				String filename = json.getOriginalFilename();
				File f = new File(path+filename); 
				try {
					json.transferTo(f);
				} catch (IllegalStateException | IOException e) {
					//e.printStackTrace();
				}
			}));
		}
		closeThread(executor, futures);
	}

	/**
	 * 서버 디렉토리 안의 json 파일을 PostgreSQL에 넣음
	 * @param path 파일 디렉토리
	 * @exception Exception 예외 처리
	 * @return DB의 데이터 여부를 확인하고 값을 리턴함
	 */
	@Override
	public String insertJSONDir(String path) throws Exception {
		File dir = new File(path);
		File[] fileList = dir.listFiles();
		
		check = false;
		if(fileList.length == 0) {
			return "null";
		}
		
		TimeZone zone = TimeZone.getTimeZone("Asia/Seoul");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setTimeZone(zone);
		String format_time;
		
		long beforeTime = System.currentTimeMillis();
		format_time = format.format(beforeTime);
		logger.info(format_time + " JSON 입력 시작");
		
		int threadCnt = 3;
		ExecutorService executor = Executors.newFixedThreadPool(threadCnt);
		List<Future<?>> futures = new ArrayList<>();
		
		for(File file : fileList){
			futures.add(executor.submit(() -> {
				/* 확장자가 json인 파일을 읽는다 */
				if(file.isFile() && FilenameUtils.getExtension(file.getName()).equals("json")){
					logger.info("start file " + file.getName());

					String fullPath = path + file.getName();

					JsonLog jsonLog = new JsonLog();

					/* jsonLog 테이블 시작시간 측정 */
					jsonLog.setStart(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					long startTime = System.currentTimeMillis();

					/* json 파일을 읽어서 객체로 파싱 */
					JSONObject obj = jsonParsing.getJSONObject(fullPath);

					/* metadata 테이블 입력 */
					Metadata metadata  = jsonParsing.setMetadata(obj);

					/* metadata_id를 가져옴(creator, title) */
					Map map = new HashMap();
					map.put("creator", metadata.getCreator());
					map.put("title", metadata.getTitle());
					String isExistId = sqlSession.selectOne(metadataNS+"isExistMetadataId", map);

					if(isExistId == null) { //등록된 데이터가 아닐 경우
						check = true;

						/* metadata 테이블 입력 */
						sqlSession.insert(metadataNS+"insertIntoMetadata", metadata);

						/* auto increment로 등록된 id를 가져옴 */
						int metadata_id = sqlSession.selectOne(metadataNS+"getMetadataId", map);

						/* jsonLog 테이블에 파일명과 metadata_id 입력 */
						jsonLog.setTitle(metadata.getTitle());
						jsonLog.setMetadata_id(metadata_id);

						/* speaker 테이블 입력 */
						List<Speaker> speakerList = jsonParsing.setSpeaker(obj, metadata_id);
						for(Speaker speaker : speakerList) {
							sqlSession.insert(speakerNS+"insertIntoSpeaker", speaker);
						}

						/* utterance 테이블 입력 */
						List<Utterance> utteranceList = jsonParsing.setUtterance(obj, metadata_id);
						for(Utterance utterance : utteranceList) {
							sqlSession.insert(utteranceNS+"insertIntoUtterance", utterance); //utterance 입력
							
							List<EojeolList> eojeolListList = utterance.getEojoelList();
							for(EojeolList eojeolList : eojeolListList) {
								sqlSession.insert(eojeolListNS+"insertIntoEojeolList", eojeolList); //eojeolList 입력
							}
						}

						/* jsonLog 테이블 종료시간 측정 */
						jsonLog.setFinish(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						long endTime = System.currentTimeMillis();

						/* jsonLog 테이블 소요시간 입력 */
						int elapsed = (int)((endTime-startTime)/1000.0);
						int min = elapsed/60;
						int sec = elapsed - min*60;
						jsonLog.setElapsed(""+min+":"+sec);

						sqlSession.insert(jsonLogNS+"insertIntoJsonLog", jsonLog);
					}
					logger.info("finish file " + file.getName());
				}
				file.delete();
			}));
		}
		closeThread(executor, futures);
		
		long afterTime = System.currentTimeMillis();
		format_time = format.format(afterTime);
		logger.info(format_time + " JSON 입력 끝");
		
		long diffTime = (afterTime - beforeTime);
		logger.info("JSON 입력 소요 시간(ms) : " + diffTime + "밀리초");
		
		long secDiffTime = diffTime/1000;
		logger.info("JSON 입력 소요 시간(s) : " + secDiffTime + "초");

		if(check == true) { //아직 등록되지 않은 데이터가 하나라도 있을 경우
			return "true";
		}else { //모두 중복된 데이터일 경우
			return "false";
		}
	}

	/**
	 * xlsx 파일들을 업로드해서 PostgreSQL에 넣음
	 * @param path 파일 디렉토리
	 * @param xlsxFile 엑셀 파일
	 * @exception Exception 예외 처리
	 * @return DB의 데이터 여부를 확인하고 값을 리턴함
	 */
	@Override
	public boolean insertXlsxUpload(String path, List<MultipartFile> xlsxFile) throws Exception{		
		UsingLog usingLog = new UsingLog();
		usingLog.setContent("xlsx 파일 업로드");
		usingLogUtil.setUsingLog(usingLog);
		
		int threadCnt = 5;
		ExecutorService executor = Executors.newFixedThreadPool(threadCnt);
		List<Future<?>> futures = new ArrayList<>();

		/* 서버 디렉토리에 파일 저장 */
		for(MultipartFile xlsx : xlsxFile){
			futures.add(executor.submit(() -> {
				String filename = xlsx.getOriginalFilename();
				File f = new File(path+filename);
				try {
					xlsx.transferTo(f);
				} catch (IllegalStateException | IOException e) {
					//e.printStackTrace();
				}
			}));
		}
		closeThread(executor, futures);
		
		File dir = new File(path);
		File[] fileList = dir.listFiles();
		check = false;

		threadCnt = 5;
		executor = Executors.newFixedThreadPool(threadCnt);
		futures = new ArrayList<>();
		
		for(File file : fileList){
			futures.add(executor.submit(() -> {
				/* 확장자가 xlsx인 파일을 읽는다 */
			    if(file.isFile() && FilenameUtils.getExtension(file.getName()).equals("xlsx")){
			    	String fullPath = path + file.getName();
			    	List<Program> list = xlsxParsing.setProgramList(fullPath);

			    	for(Program p : list) {
			    		if(sqlSession.selectOne(programNS+"getProgramByFileNum", p.getFile_num()) == null) {
			    			check = true;
			    			sqlSession.insert(programNS+"insertIntoProgram", p);
			    		}
			    	}
			    }
			}));
		}
		closeThread(executor, futures);
		
		threadCnt = 5;
		executor = Executors.newFixedThreadPool(threadCnt);
		futures = new ArrayList<>();

		/* 서버 디렉토리에 파일 삭제 */
		try{
			while (dir.listFiles().length != 0){
				File[] folder_list = dir.listFiles();

				for (File file : folder_list){
					futures.add(executor.submit(() -> {
						file.delete();
					}));
				}
			}
		}catch (Exception e){
		}
		closeThread(executor, futures);

		if(check == true) { //아직 등록되지 않은 데이터가 하나라도 있을 경우
			return true;
		}else { //모두 중복된 데이터일 경우
			return false;
		}
	}

	/**
	 * 서버 디렉토리 안의 xlsx 파일을 PostgreSQL에 넣음
	 * @param path 파일 디렉토리
	 * @exception Exception 예외 처리
	 * @return DB의 데이터 여부를 확인하고 값을 리턴함
	 */
	@Override
	public String insertXlsxDir(String path) throws Exception{
		File dir = new File(path);
		File[] fileList = dir.listFiles();
		check = false;
		
		int threadCnt = 5;
		ExecutorService executor = Executors.newFixedThreadPool(threadCnt);
		List<Future<?>> futures = new ArrayList<>();

		if(fileList.length == 0)
			return "null";

		for(File file : fileList){
			futures.add(executor.submit(() -> {
				/* 확장자가 xlsx인 파일을 읽는다 */
				if(file.isFile() && FilenameUtils.getExtension(file.getName()).equals("xlsx")){
					String fullPath = path + file.getName();
					List<Program> list = xlsxParsing.setProgramList(fullPath);

					for(Program p : list) {
						if(sqlSession.selectOne(programNS+"getProgramByFileNum", p.getFile_num()) == null) {
							check = true;
							sqlSession.insert(programNS+"insertIntoProgram", p);
						}
					}
				}
			}));
		}
		closeThread(executor, futures);

		if(check == true) { //아직 등록되지 않은 데이터가 하나라도 있을 경우
			return "true";
		}else { //모두 중복된 데이터일 경우
			return "false";
		}
	}
	
	/**
	 * Metadata 테이블과 Program 테이블을 조인해서 전체 테이블을 가져옴
	 * @param data 데이터 타입 유형(전체/강의/회의/고객응대/상담)
	 * @return Metadata 테이블과 Program 테이블을 조인한 전체 테이블
	 */
	public List<Metadata> getMetadataAndProgram(String data){
		List<Metadata> list = new ArrayList<>();
		switch(data) {
			case "all": //전체
				list = postgreDao.getMetadataAndProgram();
				break;
			case "korean_lecture": //한국어 강의 데이터
				list = postgreDao.getMetadataAndProgramInLecture();
				break;
			case "meeting_audio": //회의 음성 데이터
				list = postgreDao.getMetadataAndProgramInMeeting();
				break;
			case "customer_reception": //고객 응대 데이터
				break;
			case "counsel_audio": //상담 음성 데이터
				break;
			default:
				break;
		}
		return list;
	}
	
	/**
	 * Metadata 테이블과 Program 테이블을 조인해서 페이징 처리하여 가져옴
	 * @param data 데이터 타입 유형(전체/강의/회의/고객응대/상담)
	 * @param function_name 페이지의 번호를 클릭했을 때 호출되는 자바스크립트 함수명 또는 게시글 조회를 요청하는 함수명을 저장할 변수
	 * @param current_page_no 현재 화면에 출력되고 있는 페이지 번호 또는 페이지의 번호를 클릭했을 때에 번호를 저장할 변수
	 * @param count_per_page 한 화면에 출력되는 페이지의 수를 저장할 변수
	 * @param count_per_list 한 화면에 출력되는 게시글의 수를 저장할 변수
	 * @param search_word 검색어
	 * @return Metadata 테이블과 Program 테이블을 조인하여 페이징 처리한 테이블
	 */
	public ResponseData getMetadataAndProgram(String data, 
											String function_name, 
											int current_page_no,
											int count_per_page,
											int count_per_list,
											String search_word){
    	
		CommonDto commonDto = new CommonDto();
		int totalCount = postgreDao.getMetadataCnt(data, search_word); //총 Metadata의 row 수
		if (totalCount != 0) {
			CommonForm commonForm = new CommonForm();
			commonForm.setFunction_name(function_name);
			commonForm.setCurrent_page_no(current_page_no);
			commonForm.setCount_per_page(count_per_page);
			commonForm.setCount_per_list(count_per_list);
			commonForm.setTatal_list_count(totalCount);
			commonDto = PagingUtil.setPageUtil(commonForm);
		}
		int limit = commonDto.getLimit();
		int offset = commonDto.getOffset();
		
		List<Metadata> list = new ArrayList<>();
		list = postgreDao.getMetadataAndProgram(data, limit, offset, search_word);
		
		ResponseData responseData = new ResponseData();
    	Map<String, Object> items = new HashMap<String, Object>();	
    	items.put("list", list);
    	items.put("totalCount", totalCount);
    	items.put("pagination", commonDto.getPagination());
		responseData.setItem(items);
		return responseData;
	}

	/**
	 * metadata id로 Metadata 테이블과 Program 테이블을 조인해서 가져옴
	 * @param metaId Metadata와 Program의 조인키
	 * @return 조인값을 리턴
	 */
	public Metadata getMetadataAndProgramUsingId(Integer metaId){
		return postgreDao.getMetadataAndProgramUsingId(metaId);
	}

	/**
	 * utterance id 로 해당되는 utterance 튜플을 가져옴
	 * @param id utterance 튜플에 해당하는 utterance id 값
	 * @return 튜플 리턴
	 */
	public Utterance getUtteranceUsingId(String id){
		return postgreDao.getUtteranceUsingId(id);
	}

	/**
	 * 음성데이터 저장 디렉토리에가서 프론트에서 음성파일에 접근할 수 있도록 webapp/resource/sound/로 음성파일 복사
	 * @param metaTitle 사용자가 클릭한 utterance의 파일명
	 * @param request 사용자로부터 들어온 요청
	 */
	public void wavFileCopy(String metaTitle, HttpServletRequest request) {
		// 음성파일이 저장되어 있는 디렉토리
		File dir = new File(soundPath);
		// utterance의 파일명이 포함된 파일을 찾는 필터
		String[] files = dir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.indexOf(metaTitle) != -1;
			}
		});

		// resource에 sound 디렉토리가 없을 경우 생성
		File fileDir = new File(request.getSession().getServletContext().getRealPath("/resource/sound/"));
		if(!fileDir.exists()){
			fileDir.mkdir();
		}

		// file이 존재한다면 webapp으로 음성파일 복사
		if(files.length!=0){
			File file = new File(soundPath + files[0]);
			File temp = new File(request.getSession().getServletContext().getRealPath("/resource/sound/"+metaTitle+".wav"));
			FileInputStream fis = null;
			FileOutputStream fos = null;

			try {
				fis = new FileInputStream(file);
				fos = new FileOutputStream(temp);
				byte[] b = new byte[4096];
				int cnt = 0;
				while ((cnt = fis.read(b)) != -1) {
					fos.write(b, 0, cnt);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fis.close();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * wav 파일들을 저장 경로에 업로드
	 * @param wavFile wav 파일
	 * @throws Exception 파일 업로드 예외처리
	 */
	public void uploadWav(List<MultipartFile> wavFile) throws Exception {
		UsingLog usingLog = new UsingLog();
		usingLog.setContent("wav 파일 업로드");
		usingLogUtil.setUsingLog(usingLog);
		
		int threadCnt = 5;
		ExecutorService executor = Executors.newFixedThreadPool(threadCnt);
		List<Future<?>> futures = new ArrayList<>();
		
		/* 서버의 음성파일 저장 디렉토리에 wav파일 저장 */
		for (MultipartFile wav : wavFile) {
			futures.add(executor.submit(() -> {
				String filename = wav.getOriginalFilename();
				File f = new File(soundPath + filename);
				try {
					wav.transferTo(f);
				} catch (IllegalStateException | IOException e) {
					//e.printStackTrace();
				}
			}));
		}
		closeThread(executor, futures);
	}
	
	public void closeThread(ExecutorService executor, List<Future<?>> futures) {
		for (Future<?> future : futures) {
			try {
				future.get(); //스레드 작업이 종료될 때까지 기다림
			} catch (InterruptedException e) {
				//e.printStackTrace();
			} catch (ExecutionException e) {
				//e.printStackTrace();
			}
        }
		executor.shutdownNow(); //Task 종료
	}
}
