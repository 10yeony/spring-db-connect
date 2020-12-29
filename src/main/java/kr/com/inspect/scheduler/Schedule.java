package kr.com.inspect.scheduler;

import kr.com.inspect.dto.Member;
import kr.com.inspect.service.MemberService;
import kr.com.inspect.service.PostgreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 스케쥴러 클래스
 * 
 * @author Woo Young
 * @version 1.0
 *
 */
@Component
@PropertySource(value = "classpath:properties/directory.properties")
public class Schedule {

	/**
	 * 로그 출력을 위한 logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(Schedule.class);

	/**
	 * 감시할 디렉토리 경로<br>
	 * 이곳에서 uploadJson 디렉토리로 복사된다.
	 */
	@Value("${input.json.directory}")
	private String pathFrom;

	/**
	 * json 디렉토리의 파일이 저장되는 디렉토리 경로<br>
	 * 파일이 디비에 저장되는 디렉토리
	 */
	@Value("${input.uploadJson.directory}")
	public String pathTo;

	/**
	 * DB에 저장된 모든 json 파일을 저장할 경로
	 */
	@Value("${input.jsonStorage.directory}")
	public String pathStorage;

	/**
	 * PostgreSQL 서비스 필드 선언
	 */
	@Autowired
	private PostgreService postgreService;

	/**
	 * Member 서비스 필드 선언
	 */
	@Autowired
	private MemberService memberService;

	/**
	 * 10분마다 디렉토리를 감시하며 json 파일을 DB에 파싱
	 * @throws Exception 예외처리
	 */
	@Scheduled(fixedDelay = 600000)
	public void watchDir() throws Exception {
		File dirFrom = new File(pathFrom);
		File dirTo = new File(pathTo);
		File dirStor = new File(pathStorage);
		File[] fileList = dirFrom.listFiles();

		/* json 디렉토리에서 uploadJson 디렉토리와 jsonStorage 디렉토리로 파일 복사 */
		for (File file : fileList) {
			File temp = new File(dirTo.getAbsolutePath() + File.separator + file.getName());
			File tempS = new File(dirStor.getAbsolutePath() + File.separator + file.getName());

			FileInputStream fis = null;
			FileOutputStream fos = null;
			FileOutputStream fosS = null;

			try {
				fis = new FileInputStream(file);
				fos = new FileOutputStream(temp);
				fosS = new FileOutputStream(tempS);
				byte[] b = new byte[4096];
				int cnt = 0;
				while ((cnt = fis.read(b)) != -1) {
					fos.write(b, 0, cnt);
					fosS.write(b, 0, cnt);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fis.close();
					fos.close();
					fosS.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			file.delete();
		}

		/* uploadJson 디렉토리안의 파일을 파싱하고 삭제 */
		postgreService.insertJSONDir(pathTo);
	}

	/**
	 * 매일 오전 9시에 회원을 검사
	 * @throws Exception 예외처리
	 */
//	@Scheduled(cron = "0 0 9 * * *")
	@Scheduled(fixedDelay = 60000)
	public void checkUserLoginTime() throws Exception {
		List<Member> memberList = memberService.getAllMemberList();
		String time, deleteTime, accountExpiredTime;

		TimeZone zone = TimeZone.getTimeZone("Asia/Seoul");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setTimeZone(zone);
		String currentTime = format.format(System.currentTimeMillis());

		// 각 사용자를 검사
		for(Member member : memberList){
			time = member.getLogin_time();

			// 마지막으로 로그인 한 날짜에 6개월 더해주기
			deleteTime = plusMonth(time, 6);

			// 마지막으로 로그인 한 날짜에 3개월 더해주기
			accountExpiredTime = plusMonth(time, 3);

			// 만약 6개월 더한 날짜가 현재 날짜보다 더 예전이라면 계정 삭제
			if(CompareDate(deleteTime, currentTime)){
//				memberService.deleteMemberByScheduler(member.getMember_id());
			}
			// 3개월 지난 날짜가 현재 날짜보다 더 예전이라면 계정 만료
			else if(CompareDate(accountExpiredTime, currentTime)){
				memberService.accountExpired(member.getMember_id());
			}
		}
	}

	/**
	 * 첫번째 인자의 날짜와 두번째 인자의 날짜를 비교하여
	 * 첫번째 날짜가 더 최근이라면 false 반환
	 * @param first 비교할 첫번째 날짜
	 * @param second 비교할 두번째 날짜
	 * @return first가 더 예전날짜라면 true ,
	 * 			second가 더 예전 날짜라면 false
	 */
	public boolean CompareDate(String first, String second){
		// 비교할 날짜들의 년, 월, 일
		int firstYear, firstMonth, firstDate;
		int secondYear, secondMonth, secondDate;

		firstYear = Integer.parseInt(first.substring(0,4));
		firstMonth = Integer.parseInt(first.substring(8,10));
		firstDate = Integer.parseInt(first.substring(5,7));

		secondYear = Integer.parseInt(second.substring(0,4));
		secondMonth = Integer.parseInt(second.substring(8,10));
		secondDate = Integer.parseInt(second.substring(5,7));

		// 첫번째 연도가 두번째 연도보다 크다면 return false
		if(firstYear > secondYear){
			return false;
		}
		// 첫번째 연도가 두번째 연도보다 적다면 return true
		else if(firstYear<secondYear){
			return true;
		}
		// 연도가 같고 월이 적다면 return true
		else if(firstMonth<secondMonth){
			return true;
		}
		// 연도와 월이 같고 일이 적다면 return true
		else if(firstMonth == secondMonth && firstDate < secondDate){
			return true;
		}

		return false;
	}

	/**
	 * 문자열 형식의 날짜와 숫자를 받아 날짜에 숫자 개월만큼 더해서 문자열로 반환
	 * @param date 문자열 형식의 날짜
	 * @param num 더해줄 월 수
	 * @return date에 num 개월 만큼 더한 날짜
	 */
	public String plusMonth(String date, int num){
		int year, month;

		year = Integer.parseInt(date.substring(0, 4));
		month = Integer.parseInt(date.substring(5, 7));

		if(month+num >12){
			month = (month+num)%12;
			if(month<10)
				date = Integer.toString(year+1) + date.substring(4,5) + "0" + Integer.toString(month) + date.substring(7);
			else
				date = Integer.toString(year+1) + date.substring(4,5) + Integer.toString(month) + date.substring(7);
		}else{
			month += num;
			if(month<10)
				date = date.substring(0,5) + "0" + Integer.toString(month) + date.substring(7);
			else
				date = date.substring(0,5) + Integer.toString(month) + date.substring(7);
		}

		return date;
	}
}
