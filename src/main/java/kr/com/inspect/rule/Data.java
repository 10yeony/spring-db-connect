package kr.com.inspect.rule;

import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import kr.com.inspect.dto.EojeolList;
import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Program;
import kr.com.inspect.dto.Speaker;
import kr.com.inspect.dto.Utterance;
import org.apache.ibatis.io.Resources;

/**
 * 사용자가 가져다 쓸 DB 접속 객체
 * @author Yeonhee Kim
 * @author Wooyoung Lee
 *
 */
public class Data {
	
	/**
	 * DB Driver 클래스명
	 */
	private String driver;
	
	/**
	 * DB url
	 */
	private String url;
	
	/**
	 * DB 사용자 아이디
	 */
	private String user;
	
	/**
	 * DB 비밀번호
	 */
	private String pass;
	
	/**
	 * Data 객체의 기본 생성자(JDBC 정보 세팅)
	 */
	public Data() {
		String resource = "properties/db.properties";
		Properties properties = new Properties();

		try{
			Reader reader = Resources.getResourceAsReader(resource);
			properties.load(reader);

			this.driver = properties.getProperty("jdbc.driverClassName");
			this.url = properties.getProperty("jdbc.url");
			this.user = properties.getProperty("jdbc.username");
			this.pass = properties.getProperty("jdbc.password");
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * DB 설정 정보를 넣고 DB 연결 객체를 반환함
	 * @return Connection 객체
	 * @throws Exception
	 */
	public Connection getConnect() throws Exception{
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, pass);
		return conn;
    }
	
	/**
	 * DB와 관련된 자원을 닫음
	 * @param rs ResultSet
	 * @param ps PreparedStatement
	 * @param conn Connection
	 * @throws SQLException SQL 예외
	 */
	public void closeAll(ResultSet rs,PreparedStatement ps, Connection conn) throws SQLException {
		if(rs!=null) rs.close();
		if(ps!=null) ps.close(); 
		if(conn!=null) conn.close();
	}

	/**
	 * 해당되는 Metadata 테이블을 가져옴
	 * @param metadata_id Metadata 아이디(0일 경우 전체 Metadata)
	 * @return 해당되는 Metadata 테이블
	 * @throws Exception 예외
	 */
	public List<Metadata> getMetadata(int metadata_id) throws Exception{
		List<Metadata> list = new ArrayList<>();
		Metadata vo = new Metadata();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		conn = getConnect();
		
		String query = "SELECT * FROM audio.metadata";
		if(metadata_id == 0) { //전체
			ps = conn.prepareStatement(query);
		} else { //특정 아이디
			query += " WHERE id=?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, metadata_id);
		}

		rs = ps.executeQuery();
		
		while(rs.next()){
			vo = new Metadata();
			vo.setId(rs.getInt("id"));
			vo.setCreator(rs.getString("creator"));
			vo.setAnnotation_level(rs.getString("annotation_level"));
			vo.setYear(rs.getString("year"));
			vo.setSampling(rs.getString("sampling"));
			vo.setTitle(rs.getString("title"));
			vo.setCategory(rs.getString("category"));
			vo.setAudio_type(rs.getString("audio_type"));
			vo.setDistributor(rs.getString("distributor"));
			vo.setRelation(rs.getString("relation"));
			list.add(vo);
		}
		closeAll(rs, ps, conn);
		return list;
	}
	
	/**
	 * 해당되는 Metadata 테이블과 Program 테이블을 조인하여 가져옴
	 * @param metadata_id Metadata 아이디(0일 경우 전체 Metadata)
	 * @return Metadata 테이블과 Program 테이블을 조인한 해당 테이블
	 * @throws Exception 예외
	 */
	public List<Metadata> getMetadataAndProgram(int metadata_id) throws Exception{
		List<Metadata> list = new ArrayList<>();
		List<Speaker> speakerList = null;
		List<Utterance> utteranceList = null;
		Metadata vo = null;
		Program program = null;
		Speaker speaker = null;
		Utterance utterance = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs, rsSpeaker, rsUtterance = null;
		
		String query = "SELECT "
									+ "m.id metadata_id, "
									+ "m.creator creator, "
									+ "m.annotation_level annotation_level, "
									+ "m.year metadata_year, "
									+ "m.sampling sampling, "
									+ "m.title metadata_title, "
									+ "m.category category, "
									+ "m.audio_type audio_type, "
									+ "m.distributor distributor, "
									+ "m.relation relation, " 
									+ "p.id program_id, "
									+ "p.file_num file_num, "
									+ "p.title program_title, " 
									+ "p.subtitle subtitle, "
									+ "p.running_time running_time, "
									+ "ut.sentence_count sentence_count, "
									+ "eo.eojeol_total eojeol_total, " 
									+ "sp.speaker_count speaker_count "
							+ "FROM " 
									+ "audio.metadata m " 
							+ "LEFT JOIN "
									+ "audio.program p "
							+ "ON " 
									+ "m.title = p.file_num " 
							+ "LEFT JOIN " 
									+ "(SELECT "
											+ "metadata_id, "
											+ "COUNT(form) sentence_count " 
									+ "FROM "
											+ "audio.utterance " 
									+ "GROUP BY "
											+ "metadata_id) ut " 
							+ "ON " 
									+ "m.id = ut.metadata_id " 
							+ "LEFT JOIN " 
									+ "(SELECT "
											+ "metadata_id, "
											+ "COUNT(standard) eojeol_total " 
										+ "FROM "
												+ "audio.eojeolList " 
										+ "GROUP BY "
												+ "metadata_id) eo " 
							+ "ON " 
									+ "m.id = eo.metadata_id "
							+ "LEFT JOIN "
									+ "(SELECT "
											+ "metadata_id, "
											+ "COUNT(id) speaker_count "
									+ "FROM "
											+ "audio.speaker "
									+ "GROUP BY " 
											+ "metadata_id) sp "
							+ "ON "
									+ "m.id = sp.metadata_id";
		
		conn = getConnect();

		if(metadata_id == 0) { //전체
			ps = conn.prepareStatement(query);
		}else { //특정 아이디
			query += " WHERE m.id=?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, metadata_id);
		}
		
		rs = ps.executeQuery();
		String querySpeaker = "SELECT * FROM audio.speaker WHERE metadata_id=? and no = ?";
		String queryUtterance = "SELECT * FROM audio.utterance WHERE metadata_id=? ORDER BY start";

		while(rs.next()){
			vo = new Metadata();
			program = new Program();
			speakerList = new ArrayList<>();
			utteranceList = new ArrayList<>();
			vo.setId(rs.getInt("metadata_id"));

			vo.setCreator(rs.getString("creator"));
			vo.setAnnotation_level(rs.getString("annotation_level"));
			vo.setYear(rs.getString("metadata_year"));
			vo.setSampling(rs.getString("sampling"));
			vo.setTitle(rs.getString("metadata_title"));
			vo.setCategory(rs.getString("category"));
			vo.setAudio_type(rs.getString("audio_type"));
			vo.setDistributor(rs.getString("distributor"));
			vo.setRelation(rs.getString("relation"));
			program.setId(rs.getInt("program_id"));
			program.setFile_num(rs.getString("file_num"));
			program.setTitle(rs.getString("program_title"));
			program.setSubtitle(rs.getString("subtitle"));
			program.setRunning_time(rs.getString("running_time"));
			vo.setProgram(program);
			vo.setSentence_count(rs.getInt("sentence_count"));
			vo.setEojeol_total(rs.getInt("eojeol_total"));
			vo.setSpeaker_count(rs.getInt("speaker_count"));
			list.add(vo);
		}
		
		closeAll(rs, ps, conn);
		return list;
	}
	
	/**
	 * 해당되는 Speaker 테이블을 가져옴
	 * @param metadata_id Metadata 아이디(0일 경우 전체 Metadata)
	 * @return 해당되는 Speaker 테이블
	 * @throws Exception 예외
	 */
	public List<Speaker> getSpeaker(int metadata_id) throws Exception{
		List<Speaker> list = new ArrayList<>();
		Speaker vo = new Speaker();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		conn = getConnect();
		
		String query = "SELECT * FROM audio.speaker";
		if(metadata_id == 0) { //전체
			ps = conn.prepareStatement(query);
		} else { //특정 아이디
			query += " WHERE metadata_id=?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, metadata_id);
		}
		
		rs = ps.executeQuery();
		
		while(rs.next()){
			vo = new Speaker();
			vo.setId(rs.getInt("id"));
			vo.setNo(rs.getInt("no"));
			vo.setShortcut(rs.getInt("shortcut"));
			vo.setOccupation(rs.getString("occupation"));
			vo.setSex(rs.getString("sex"));
			vo.setName(rs.getString("name"));
			vo.setAge(rs.getString("age"));
			vo.setBirthplace(rs.getString("birthplace"));
			vo.setCurrent_residence(rs.getString("current_residence"));
			vo.setPricipal_residence(rs.getString("pricipal_residence"));
			vo.setEducation(rs.getString("education"));
			vo.setMetadata_id(rs.getInt("metadata_id"));
			list.add(vo);
		}
		closeAll(rs, ps, conn);
		return list;
	}
	
	/**
	 * 해당되는 Utterance 테이블을 가져옴
	 * @param metadata_id Metadata 아이디(0일 경우 전체 Metadata)
	 * @return 해당되는 Utterance 테이블
	 * @throws Exception 예외
	 */
	public List<Utterance> getUtterance(int metadata_id) throws Exception{
		List<Utterance> list = new ArrayList<>();
		List<Speaker> speakerList = null;
		Utterance vo = new Utterance();
		Speaker speaker = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs, rsSpeaker = null;
		
		conn = getConnect();
		
		String query = "SELECT * FROM audio.utterance";
		if(metadata_id == 0) { //전체
			ps = conn.prepareStatement(query);
		} else { //특정 아이디
			query += " WHERE metadata_id=?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, metadata_id);
		}
		
		rs = ps.executeQuery();
		String querySpeaker = "SELECT * FROM audio.speaker WHERE metadata_id=? and no = ?";
		
		while(rs.next()){
			vo = new Utterance();
			speakerList = new ArrayList<>();
			vo.setId(rs.getString("id"));
			vo.setNote(rs.getString("note"));
			vo.setForm(rs.getString("form"));
			vo.setStandard_form(rs.getString("standard_form"));
			vo.setDialect_form(rs.getString("dialect_form"));
			vo.setSpeaker_no(rs.getInt("speaker_no"));
			vo.setStart(rs.getDouble("start"));
			vo.setFinish(rs.getDouble("finish"));
			vo.setMetadata_id(rs.getInt("metadata_id"));
			if(vo.getSpeaker_no() == 0){
				vo.setSpeaker(null);
			}
			else{
				ps = conn.prepareStatement(querySpeaker);
				ps.setInt(1, vo.getMetadata_id());
				ps.setInt(2, Integer.valueOf(vo.getSpeaker_no()));
				rsSpeaker = ps.executeQuery();
				while(rsSpeaker.next()){
					speaker = new Speaker();
					speaker.setId(rsSpeaker.getInt("id"));
					speaker.setNo(rsSpeaker.getInt("no"));
					speaker.setShortcut(rsSpeaker.getInt("shortcut"));
					speaker.setOccupation(rsSpeaker.getString("occupation"));
					speaker.setSex(rsSpeaker.getString("sex"));
					speaker.setName(rsSpeaker.getString("name"));
					speaker.setAge(rsSpeaker.getString("age"));
					speaker.setBirthplace(rsSpeaker.getString("birthplace"));
					speaker.setCurrent_residence(rsSpeaker.getString("current_residence"));
					speaker.setPricipal_residence(rsSpeaker.getString("pricipal_residence"));
					speaker.setEducation(rsSpeaker.getString("education"));
					speaker.setMetadata_id(rsSpeaker.getInt("metadata_id"));
				}
				vo.setSpeaker(speaker);
			}
			list.add(vo);
		}
		closeAll(rs, ps, conn);
		return list;
	}
	
	/**
	 * 해당되는 EojeolList 테이블을 가져옴
	 * @param metadata_id Metadata 아이디(0일 경우 전체 Metadata)
	 * @return 해당되는 EojeolList 테이블
	 * @throws Exception 예외
	 */
	public List<EojeolList> getEojeolList(int metadata_id) throws Exception{
		List<EojeolList> list = new ArrayList<>();
		EojeolList vo = new EojeolList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		conn = getConnect();
		
		String query = "SELECT * FROM audio.eojeolList";
		if(metadata_id == 0) { //전체
			ps = conn.prepareStatement(query);
		} else { //특정 아이디
			query += " WHERE metadata_id=?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, metadata_id);
		}
		
		rs = ps.executeQuery();
		
		while(rs.next()){
			vo = new EojeolList();
			vo.setId(rs.getString("id"));
			vo.setStandard(rs.getString("standard"));
			vo.setEojeol(rs.getString("eojeol"));
			vo.setFinish(rs.getInt("finish"));
			vo.setDialect(rs.getBoolean("isdialect"));
			vo.setBegin(rs.getInt("begin"));
			vo.setUtterance_id(rs.getString("utterance_id"));
			vo.setMetadata_id(rs.getInt("metadata_id"));
			list.add(vo);
		}
		closeAll(rs, ps, conn);
		return list;
    }
}
