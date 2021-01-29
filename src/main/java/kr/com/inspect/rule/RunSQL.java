package kr.com.inspect.rule;

import kr.com.inspect.dao.RuleDao;
import kr.com.inspect.dto.ResponseData;
import kr.com.inspect.dto.Rule;
import kr.com.inspect.report.PrevRuleResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * SQL 실행
 * @author Wooyoung
 * @author Yeonhee Kim
 * @version 1.0
 */
@Component
public class RunSQL {

    /**
     * 전사규칙에 관한 DAO 인터페이스
     */
    @Autowired
    private RuleDao ruleDao;

    /**
     * query를 받아 실행하는 메서드
     * @param query 실행할 쿼리문
     */
    public ResponseData run(ResponseData responseData, String query) {
        if(query.length() < 6){
            responseData.setItem("잘못된 쿼리입니다. 다시 입력해주세요." + "\n\n쿼리문이 너무 짧습니다.");
            return responseData;
        }
        String type = query.substring(0, 6);
        if(query.substring(0,4).toLowerCase().equals("with")){
            type = query.substring(0,4);
        }
        else if(query.substring(0,7).toLowerCase().equals("explain")){
            type = query.substring(0,7);
        }

        List<Object> list = new ArrayList<>();
        List<List<Object>> listList = new ArrayList<>();

        Data data = new Data();
        Connection con = null;
        ResultSet resultSet = null;
        int result, columnCount;
        PreparedStatement preparedStatement = null;
        try{
            con = data.getConnect();
            Statement statement = con.createStatement();

            switch (type){
                case "insert" :
                case "INSERT" :
                    result = statement.executeUpdate(query);
                    responseData.setCode("insert");
                    responseData.setItem(result + "개의 데이터가 삽입되었습니다.");
                    break;
                case "update" :
                case "UPDATE" :
                    result = statement.executeUpdate(query);
                    responseData.setCode("update");
                    responseData.setItem(result + "개의 데이터가 업데이트되었습니다.");
                    break;
                case "select" :
                case "SELECT" :
                case "WITH" :
                case "with" :
                case "explain" :
                case "EXPLAIN" :
                    resultSet = statement.executeQuery(query);
                    responseData.setCode("select");
                    columnCount = resultSet.getMetaData().getColumnCount();
                    for(int i=1; i<columnCount+1; i++){
                        list.add(resultSet.getMetaData().getColumnName(i));
                    }
                    listList.add(list);

                    while (resultSet.next()){
                        list = new ArrayList<>();
                        for(int i=1; i<columnCount+1; i++){
                            list.add(resultSet.getString(i));
                        }
                        listList.add(list);
                    }
                    responseData.setItem(listList);
                    break;
                case "delete" :
                case "DELETE" :
                    result = statement.executeUpdate(query);
                    responseData.setCode("delete");
                    responseData.setItem(result + "개의 데이터가 삭제되었습니다.");
                    break;
                case "create" :
                case "CREATE" :
                    statement.executeUpdate(query);
                    responseData.setCode("create");
                    responseData.setItem("성공적으로 생성하였습니다.");
                    break;
                default:
                    responseData.setCode("error");
                    responseData.setItem("잘못된 쿼리입니다. 다시 입력해주세요." + "\n\nselect, update, insert, delete, create, with, explain으로 시작하는 쿼리문을 입력해주세요.");
            }
            data.closeAll(resultSet, preparedStatement, con);
        }
        catch (Exception e){
            responseData.setCode("error");
            responseData.setItem("잘못된 쿼리입니다. 다시 입력해주세요." + "\n\n" + e.getMessage());
            return responseData;
        }
        return responseData;
    }

    /**
     * query를 받아서 실행하여 rule_bottom_level에 저장하는 메서드
     * @param responseData 응답
     * @param rule 입력할 rule
     * @return
     */
    public ResponseData run(ResponseData responseData, String presentVersion, Rule rule, String TIME) {
        int updateResult = ruleDao.updateRuleContents(rule);

        String query = rule.getContents().trim();
        if(query.length() < 6){
            responseData.setItem("잘못된 쿼리입니다. 다시 입력해주세요." + "\n\n쿼리문이 너무 짧습니다.");
            return responseData;
        }
        String type = query.substring(0, 6);
        if(query.substring(0,4).toLowerCase().equals("with")){
            type = query.substring(0,4);
        }
        else if(query.substring(0,7).toLowerCase().equals("explain")){
            type = query.substring(0,7);
        }
        List<Object> list = new ArrayList<>();
        List<List<Object>> listList = new ArrayList<>();

        Data data = new Data();
        Connection con = null;
        ResultSet resultSet = null;
        int result, columnCount;
        PreparedStatement preparedStatement = null;
        try{
            con = data.getConnect();
            Statement statement = con.createStatement();

            switch (type){
                case "insert" :
                case "INSERT" :
                    result = statement.executeUpdate(query);
                    responseData.setCode("insert");
                    responseData.setItem(result + "개의 데이터가 삽입되었습니다.");
                    break;
                case "update" :
                case "UPDATE" :
                    result = statement.executeUpdate(query);
                    responseData.setCode("update");
                    responseData.setItem(result + "개의 데이터가 업데이트되었습니다.");
                    break;
                case "select" :
                case "SELECT" :
                case "with" :
                case "WITH" :
                case "explain" :
                case "EXPLAIN" :
                    resultSet = statement.executeQuery(query);
                    responseData.setCode("select");
                    columnCount = resultSet.getMetaData().getColumnCount();
                    for(int i=1; i<columnCount+1; i++) {
                        if (resultSet.getMetaData().getColumnName(i) == null)
                            list.add(resultSet.getMetaData().getColumnName(i));
                        else {
                            list.add(resultSet.getMetaData().getColumnName(i).replace(",", ""));
                        }
                    }
                    listList.add(list);

                    while (resultSet.next()){
                        list = new ArrayList<>();
                        for(int i=1; i<columnCount+1; i++) {
                            if (resultSet.getString(i) == null)
                                list.add(resultSet.getString(i));
                            else {
                                list.add(resultSet.getString(i).replace(",", ""));
                            }
                        }

                        listList.add(list);
                    }
                    responseData.setItem(listList);
                    break;
                case "delete" :
                case "DELETE":
                    result = statement.executeUpdate(query);
                    responseData.setCode("delete");
                    responseData.setItem(result + "개의 데이터가 삭제되었습니다.");
                    break;
                case "create" :
                case "CREATE":
                    statement.executeUpdate(query);
                    responseData.setCode("create");
                    responseData.setItem("성공적으로 생성하였습니다.");
                    break;
                default:
                    responseData.setCode("error");
                    responseData.setItem("잘못된 쿼리입니다. 다시 입력해주세요." + "\n\nselect, update, insert, delete, create, with, explain으로 시작하는 쿼리문을 입력해주세요.");
            }
            data.closeAll(resultSet, preparedStatement, con);
        }
        catch (Exception e){
            responseData.setCode("error");
            responseData.setItem("잘못된 쿼리입니다. 다시 입력해주세요." + "\n\n" + e.getMessage());
        }
        if(responseData.getCode().equals("select")){
            rule.setResult(responseData.getItem().toString());
            rule.setImp_contents(listList.size()-1 +"개의 데이터를 조회하였습니다.");
        }
        else{
            rule.setImp_contents(responseData.getItem().toString());
            rule.setResult("");
        }
        ruleDao.updateRuleCompileResult(rule);
        Rule vo = ruleDao.getRuleBottomLevel(rule.getBottom_level_id());
        if(!(presentVersion.equals(vo.getVersion()) || presentVersion.equals("0"))) {
        	ruleDao.registerPrevBottomLevel(vo);
        }else {
        	ruleDao.updatePrevBottomLevel(vo);
        }
        
        if(TIME != null) {
        	PrevRuleResult prevRuleResult = new PrevRuleResult();
        	prevRuleResult.writeRuleResultTxtFile(vo, TIME);
        }
        
        return responseData;
    }
}
