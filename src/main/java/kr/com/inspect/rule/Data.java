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

    private String metadataNS = "MetadataMapper.";
    private String utteranceNS = "UtteranceMapper.";
    private String eojeolListNS = "EojeolListMapper.";
    private String jsonLogNS = "JsonLogMapper.";

//    public Connection getConnect(){
//        try {
//            Class.forName("org.postgresql.Driver");
//            Connection con = DriverManager.getConnection("jdbc:postgresql://45.32.55.180:5432/postgres", "postgres", "postgres");
//
//            return con;
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public void closeAll(PreparedStatement ps, Connection con) throws SQLException{
//        if(ps!=null) ps.close();
//        if(con != null) con.close();
//    }
//
//    public void closeAll(ResultSet rs, PreparedStatement ps, Connection con) throws SQLException{
//        if(rs != null) rs.close();
//        closeAll(ps, con);
//    }
//
//    public void metadata() throws Exception{
//        System.out.println("metadata() called");
//
//        List<Metadata> metadata = new ArrayList<>();
//        Connection con = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        String query = "SELECT * FROM audio.metadata;";
//
//        try {
//            con = getConnect();
//            ps = con.prepareStatement(query);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                Metadata m = new Metadata();
//                m.setTitle(rs.getString("title"));
//                metadata.add(m);
//            }
//        }finally {
//            closeAll(rs, ps, con);
//        }
//
//        System.out.println(metadata.get(0).getTitle());
////        System.out.println(postgreService);
////        List<Metadata> metadata= postgreService.getMetadata();
////        System.out.println(metadata.get(1));
//    }

    public SqlSession sqlSession() throws Exception{
        Class.forName("org.postgresql.Driver");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://45.32.55.180:5432/postgres");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*.xml"));
        sqlSessionFactoryBean.setDataSource((DataSource) dataSource);

        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
        return sqlSessionTemplate;
    }

    public List<Metadata> metadata()throws Exception{
        return sqlSession().selectList(metadataNS+"getMetadataAndProgram");
    }

    public List<Utterance> utterance(Integer metadataId)throws Exception{
        return sqlSession().selectList(utteranceNS+"getUtteranceByMetadataId", metadataId);
    }

    public List<EojeolList> eojeolList(Integer utteranceId)throws Exception{
        return sqlSession().selectList(eojeolListNS+"getEojeolListUsingUtteranceId", utteranceId);
    }
}
