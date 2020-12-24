package kr.com.inspect.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.com.inspect.dao.MemberDao;
import kr.com.inspect.dao.RuleDao;
import kr.com.inspect.dto.RuleLog;
import kr.com.inspect.dto.UsingLog;

/**
 * 사용 로그에 ip, 접속 시간, 아이디, 로그 내용을 세팅함
 * @author Yeonhee Kim
 * @version 1.0
 */
@Component
public class UsingLogUtil {
	
	/**
	 * 클라이언트 정보 필드
	 */
	@Autowired
	private ClientInfo clientInfo;
	
	/**
	 * MemberDao Member DAO 필드 선언
	 */
	@Autowired
	private MemberDao memberDao;
	
	/**
	 * RuleDao Rule DAO 필드 선언
	 */
	@Autowired
	private RuleDao ruleDao;
	
	/**
	 * 사용 로그에 ip, 접속 시간, 아이디, 로그 내용을 세팅하고 DB에 등록함
	 * @param usingLog 사용 로그 객체
	 */
	public void setUsingLog(UsingLog usingLog){
		if(usingLog.getIp_addr() == null) {
			usingLog.setIp_addr(clientInfo.getIpAddr());
		}
		if(usingLog.getTime() == null) {
			usingLog.setTime(clientInfo.getTime());
		}
		if(usingLog.getMember_id() == null) {
			usingLog.setMember_id(clientInfo.getMemberId());
		}
		
		if(usingLog instanceof RuleLog) {
			RuleLog ruleLog = (RuleLog) usingLog;
			if(ruleLog.getUsing_log_no() == 0) { //아직 사용 로그를 등록하지 않은 경우
				memberDao.insertIntoUsingLog(usingLog);
				ruleLog.setUsing_log_no(getNoOfUsingLog(usingLog));
			}
			ruleDao.insertIntoRuleLog(ruleLog);
		}else {
			memberDao.insertIntoUsingLog(usingLog);
		}
	}
	
	/**
	 * UsingLog 테이블에 사용 로그를 등록하고, RuleLog에 외래키, 아이피, 아이디, 현재 시간, 내용을 세팅하여 리턴함
	 * @param usingLogContent UsingLog 테이블의 내용
	 * @param ruleLogContent RuleLog 테이블의 내용
	 * @return 외래키, 아이피, 아이디, 현재 시간, 내용을 세팅한 RuleLog
	 */
	public RuleLog insertUsingLogAndSetRuleLog(String usingLogContent, String ruleLogContent) {
		UsingLog usingLog = new UsingLog();
		usingLog.setContent(usingLogContent);
		setUsingLog(usingLog);
		
		RuleLog vo = new RuleLog();
		vo.setUsing_log_no(getNoOfUsingLog(usingLog));
		vo.setIp_addr(clientInfo.getIpAddr());
		vo.setMember_id(clientInfo.getMemberId());
		vo.setTime(clientInfo.getTime());
		vo.setContent(ruleLogContent);
		
		return vo;
	}
	
	/**
	 * 사용 로그의 Primary Key인 순번을 가져옴
	 * @param usingLog 사용 로그 객체
	 * @return 사용 로그의 Primary Key인 순번
	 */
	public int getNoOfUsingLog(UsingLog usingLog) {
		return memberDao.getUsingLog(usingLog).getNo();
	}
}
