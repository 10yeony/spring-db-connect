package kr.com.inspect.service.impl;

import java.io.File;
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
import org.springframework.stereotype.Service;

import kr.com.inspect.dao.RuleDao;
import kr.com.inspect.dto.ResponseData;
import kr.com.inspect.dto.Rule;
import kr.com.inspect.rule.RuleCompiler;
import kr.com.inspect.service.RuleService;

/**
 * 전사규칙에 관한 Service 구현 클래스
 * @author Yeonhee Kim
 * @version 1.0
 */
@Service
public class RuleServiceImpl implements RuleService {

	/**
	 * 전사규칙에 관한 DAO 인터페이스
	 */
	@Autowired
	private RuleDao ruleDao;
	
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
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @param bottom_level_id 전사규칙 소분류 아이디
	 * @return 전사규칙 대분류, 중분류, 소분류 카테고리 리스트
	 */
	@Override
	public List<Rule> getRuleCategory(String top_level_id, 
											String middle_level_id) {
		
		List<Rule> list = new ArrayList<>();
		
		/* 대분류 목록 불러오기 */
		if(top_level_id == "" && middle_level_id == "") {
			list = ruleDao.getAllRuleTopLevel();
		}
		
		/* 중분류 목록 불러오기 */
		else if(middle_level_id == "") {
			list = ruleDao.getAllRuleMiddleLevel(Integer.parseInt(top_level_id));
		}
		
		/* 소분류 카테고리 목록 불러오기 */
		else {
			list = ruleDao.getAllRuleBottomLevel(Integer.parseInt(top_level_id), 
														Integer.parseInt(middle_level_id));
		}
		return list;
	}

	/**
	 * 선택된 카테고리에 해당되는 전사규칙 리스트를 조인해서 가져옴
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @param bottom_level_id 전사규칙 소분류 아이디
	 * @return 선택된 카테고리에 해당되는 전사규칙 리스트
	 */
	@Override
	public List<Rule> getRuleListUsingJoin(String top_level_id, 
													String middle_level_id, 
													String bottom_level_id) {
		
		List<Rule> list = new ArrayList<>();
		
		/* 전사규칙 리스트를 조인 */
		if(top_level_id == "" && middle_level_id == "" && bottom_level_id == "") {
			list = ruleDao.getRuleList();
		}
		
		/* 대분류 아이디를 통해 전사규칙 리스트를 조인 */
		else if(middle_level_id == "" && bottom_level_id == "") {
			list = ruleDao.getRuleListByTopId(Integer.parseInt(top_level_id));
		}
		
		/* 대분류, 중분류 아이디를 통해 전사규칙 리스트를 조인 */
		else if(bottom_level_id == "") {
			list = ruleDao.getRuleListByTopMiddleId(Integer.parseInt(top_level_id), 
															Integer.parseInt(middle_level_id));
		}
		
		/* 대분류, 중분류, 소분류 아이디를 통해 전사규칙 리스트를 조인 */
		else {
			list = ruleDao.getRuleListByTopMiddleBottomId(Integer.parseInt(top_level_id), 
																	Integer.parseInt(middle_level_id), 
																	Integer.parseInt(bottom_level_id));
		}
		return list;
	}

	/**
	 * 대분류/중분류/소분류를 DB에 등록함
	 * @param level 해당되는 분류(대분류/중분류/소분류)
	 * @param rule DB 등록을 위한 Rule 객체
	 * @return DB에 등록한 row의 수
	 */
	public int registerRule(String level, Rule rule) {
		int id = 0; //아이디
		int result = 0; //등록 후 DB에 추가된 row의 수
		
		switch(level) {
			case "top" :
				id = ruleDao.isExistTopLevel(rule);
				if(id == 0) { //존재하지 않는 경우에만 등록
					result = ruleDao.registerTopLevel(rule);
				}
				break;
			case "middle" :
				id = ruleDao.isExistMiddleLevel(rule);
				if(id == 0) { //존재하지 않는 경우에만 등록
					result = ruleDao.registerMiddleLevel(rule);
				}
				break;
			case "bottom" :
				id = ruleDao.isExistBottomLevel(rule); //등록 전 아이디(중복 확인)
				if(id == 0) { //존재하지 않는 경우에만 등록
					result = ruleDao.registerBottomLevel(rule);
					id = ruleDao.isExistBottomLevel(rule); //등록 후 아이디(auto increment된 아이디)
					
					/* 파일명 DB 등록(파일명이 중복되지 않도록 auto increment된 아이디 사용) */
					String fileName = "Rule"+ id ;
					rule.setBottom_level_id(id);
					rule.setFile_name(fileName);
					result += ruleDao.updateBottomLevelFileName(rule);
				}
				break;
		}
		return result;
	}

	/**
	 * 대분류/중분류/소분류 아이디로 해당되는 항목을 삭제함
	 * @param level 해당되는 분류(대분류/중분류/소분류)
	 * @param id 대분류/중분류/소분류 아이디
	 * @return DB에서 삭제한 row의 수
	 */
	@Override
	public int deleteRule(String level, int id) {
		int result = 0;
		switch(level) {
			case "top":
				result = ruleDao.deleteTopLevel(id);
				break;
			case "middle":
				result = ruleDao.deleteMiddleLevel(id);
				break;
			case "bottom":
				/* 자바 파일, 클래스 파일 삭제 */
				String fileName = ruleDao.getRuleBottomLevel(id).getFile_name();
				deleteJavaClassFile(fileName);
				
				/* DB에서 소분류 삭제 */
				result = ruleDao.deleteBottomLevel(id);
				break;
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
		
		//System.out.println("업데이트");
		//System.out.println(rule);
		
		Rule vo =  ruleDao.getRuleBottomLevel(rule.getBottom_level_id());
		
		//System.out.println("db 불러오기");
		//System.out.println(rule);
		
		Object obj = null;
		try {
			
			//System.out.println("생성");
			
			ruleCompiler.create(vo);
			
			//System.out.println(vo);
			
			obj = ruleCompiler.runObject(vo); //실행 결과값
			compile = true;
		}catch (Exception e) {
			obj = getStringOfException(e); //예외 문자열
		}
		
		/* 자바 파일 및 클래스 파일 삭제 */
		deleteJavaClassFile(vo.getFile_name());
		
		/* 컴파일 결과값 DB에 등록 */
		rule.setResult(obj.toString());
		int updateResult = ruleDao.updateRuleCompileResult(rule);
		System.out.println(updateResult);
		
		/* 리턴값 세팅 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("compile", compile); //컴파일 결과
		map.put("updateResult", updateResult); //업데이트된 DB row의 수
		map.put("object", obj); //실행 결과값 또는 예외 메세지
		return map;
	}

	/**
	 * Rule 클래스 파일을 실행시킴
	 * @param list Rule 목록
	 * @throws Exception 예외
	 */
	@Override
	public void runRuleCompiler(List<Rule> list) throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(list.size());
		List<Future<?>> futures = new ArrayList<>();
		
		for(Rule rule : list) {
			futures.add(executor.submit(() -> {
				//String threadName = Thread.currentThread().getName();
				Object obj = null;
				try { 
					ruleCompiler.create(rule);
					obj = ruleCompiler.runObject(rule); //실행 결과값
				} catch (Exception e) { 
					obj = getStringOfException(e); //예외 문자열
				} 
					
				/* 자바 파일 및 클래스 파일 삭제 */
				//deleteJavaClassFile(rule.getFile_name());
					
				/* 컴파일 결과값 DB에 등록 */
				rule.setResult(obj.toString());
				int updateResult = ruleDao.updateRuleCompileResult(rule);
				
	        }));
		}
		
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
		String packagePath = "kr"+s+"com"+s+"inspect"+s+"rule"+s;
		String javaPath = ruleCompiler.getPath() + fileName + ".java";
		String classPath = ruleCompiler.getClassPath() + packagePath + fileName + ".class";
		//System.out.println("javaPath : " + javaPath);
		//System.out.println("classPath : " + classPath);
		
		File[] fileArr = new File[2];
		fileArr[0] = new File(javaPath);
		fileArr[1] = new File(classPath);
		
		for(File file : fileArr) {
			if(file.exists()){
				if(file.delete()){
					//System.out.println("파일 삭제 성공");
				}else {
					//System.out.println("파일 삭제 실패");
				}
			}else {
				//System.out.println("파일이 존재하지 않습니다.");
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
			//e.printStackTrace();
		}
		return map;
	}
}
