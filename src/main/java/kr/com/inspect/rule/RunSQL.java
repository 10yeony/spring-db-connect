package kr.com.inspect.rule;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
public class RunSQL {
    public void run(String query) {
        System.out.println("RunSQL class");
        Data data = new Data();
        Connection con = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try{
        con = data.getConnect();
        Statement statement = con.createStatement();

//        String query = "update audio.member set isaccountnonexpired = false where member_id='wooyoung01';";

//        preparedStatement = con.prepareStatement(query);
//        System.out.println("prepareStatement");
//
//        resultSet = preparedStatement.executeQuery();
//        resultSet = statement.executeQuery(query);
        statement.executeUpdate(query);
//        System.out.println("ResultSet : " + resultSet);
        data.closeAll(resultSet, preparedStatement, con);
        System.out.println("close");}
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
