package kr.com.inspect.rule;

import kr.com.inspect.dto.EojeolList;
import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Utterance;
import kr.com.inspect.service.PostgreService;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.persistence.criteria.CriteriaBuilder;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Data {
    public Connection getConnect() throws Exception{
        Class.forName("org.postgresql.Driver");

        Connection conn = DriverManager.getConnection("jdbc:postgresql://45.32.55.180:5432/postgres", "postgres", "postgres");
        return conn;
    }

    public void closeAll(PreparedStatement ps, Connection conn)throws SQLException {
        if(ps!=null) ps.close(); // 호출한 순서와 반대로 종료시킴
        if(conn!=null) conn.close();
    }

    public void closeAll(ResultSet rs,PreparedStatement ps, Connection conn)throws SQLException {
        if(rs!=null) rs.close();
        closeAll(ps, conn);
    }

    public List<Metadata> metadata() throws Exception{
        List<Metadata> metadata = new ArrayList<>();
        Metadata meta = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM audio.metadata;";

        conn = getConnect();
        ps = conn.prepareStatement(query);

        rs = ps.executeQuery();

        while(rs.next()){
            meta = new Metadata();
            meta.setId(rs.getInt("id"));
            meta.setTitle(rs.getString("title"));
            metadata.add(meta);
        }

        closeAll(rs, ps, conn);
        return metadata;
    }
}
