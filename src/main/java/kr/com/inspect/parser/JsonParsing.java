package kr.com.inspect.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.ObjectUtils;
import kr.com.inspect.dto.EojeolList;
import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Speaker;
import kr.com.inspect.dto.Utterance;
import kr.com.inspect.util.Singleton;

/**
 * JSON Parsing Class
 * @author Yeonhee Kim
 * @version 1.0
 *
 */
public class JsonParsing {
	/**
	 * Thread Safe하게 자원을 공유하기 위한 싱글톤 객체
	 */
	private Singleton singletone = Singleton.getInstance();
	
	/**
	 * JSON 파일을 읽어 JSON객체로 파싱
	 * @param fullPath 파일 디렉토리
	 * @return 파싱값을 obj에 담아 리턴
	 */
	public JSONObject getJSONObject(String fullPath) {
		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			long beforeTimeCheck = System.currentTimeMillis();
			obj = parser.parse(new FileReader(fullPath));
			long afterTimeCheck = System.currentTimeMillis();
			singletone.setTimeRecorder("parseJsonFile", afterTimeCheck-beforeTimeCheck);
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		} catch (ParseException e) {
			//e.printStackTrace();
		}
	    return (JSONObject) obj;
	}
	
	/**
	 * Metadata 파싱
	 * @param obj JSON 객체
	 * @return map에 담은 metadata 값을 변수 metadata로 리턴
	 */
	public Metadata setMetadata(JSONObject obj) {
		long beforeTimeCheck = System.currentTimeMillis();
		Map map = new HashMap();
		Metadata metadata = new Metadata();
		map = (Map) obj.get("metadata");
		//primary key는 auto increment
		metadata.setCreator(map.get("creator").toString());
		metadata.setAnnotation_level(map.get("annotation_level").toString());
		metadata.setYear(map.get("year").toString());
		metadata.setSampling(map.get("sampling").toString());
		metadata.setTitle(map.get("title").toString());
		metadata.setCategory(map.get("category").toString());
		metadata.setAudio_type(map.get("title").toString().substring(0, 2));
		metadata.setDistributor(map.get("distributor").toString());
		map = (Map) obj.get("setting");
		metadata.setRelation(map.get("relation").toString());
		long afterTimeCheck = System.currentTimeMillis();
		singletone.setTimeRecorder("parseMetadata", afterTimeCheck-beforeTimeCheck);
		return metadata;
	}
	
	/**
	 * Speaker 파싱
	 * @param obj JSON 객체
	 * @param metadata_id Speaker 테이블의 metadata_id 값(FK)
	 * @return Speaker 테이블의 값을 리스트에 담아 변수 speakerList로 리턴
	 */
	public List<Speaker> setSpeaker(JSONObject obj, int metadata_id){
		long beforeTimeCheck = System.currentTimeMillis();
		List<Speaker> speakerList = new ArrayList<>();
		JSONArray arr = (JSONArray) obj.get("speaker");
		for(int i=0; i<arr.size(); i++) {
			Map element = (Map)arr.get(i);
			Speaker speaker = new Speaker();
		    //primary key는 auto increment
			if(!ObjectUtils.isEmpty(element.get("no"))) {
				speaker.setNo(Integer.parseInt(element.get("no").toString()));
			}
			if(!ObjectUtils.isEmpty(element.get("shortcut"))) {
				speaker.setShortcut(Integer.parseInt(element.get("shortcut").toString()));
			}
			if(!ObjectUtils.isEmpty(element.get("occupation"))) {
				speaker.setOccupation(element.get("occupation").toString());
			}
			if(!ObjectUtils.isEmpty(element.get("sex"))) {
				speaker.setSex(element.get("sex").toString());
			}
			if(!ObjectUtils.isEmpty(element.get("name"))) {
				speaker.setName(element.get("name").toString());
			}
			if(!ObjectUtils.isEmpty(element.get("age"))) {
				speaker.setAge(element.get("age").toString());
			}
			if(!ObjectUtils.isEmpty(element.get("birthplace"))) {
				speaker.setBirthplace(element.get("birthplace").toString());
			}
			if(!ObjectUtils.isEmpty(element.get("current_residence"))) {
				speaker.setCurrent_residence(element.get("current_residence").toString());
			}
			if(!ObjectUtils.isEmpty(element.get("pricipal_residence"))) {
				speaker.setPricipal_residence(element.get("pricipal_residence").toString());
			}
			if(!ObjectUtils.isEmpty(element.get("education"))) {
				speaker.setEducation(element.get("education").toString());
			}
			speaker.setMetadata_id(metadata_id); //foreign key
			speakerList.add(speaker);
		}
		long afterTimeCheck = System.currentTimeMillis();
		singletone.setTimeRecorder("parseSpeaker", afterTimeCheck-beforeTimeCheck);
		return speakerList;
	}
	
	/**
	 * Utterance 파싱
	 * @param obj JSON 객체
	 * @param metadata_id utterance 테이블의 metadata_id 값(FK)
	 * @return
	 */
	public List<Utterance> setUtterance(JSONObject obj, int metadata_id){
		long beforeTimeCheck = System.currentTimeMillis();
		List<Utterance> utteranceList = new ArrayList<>();
		JSONArray arr = (JSONArray) obj.get("utterance");
		for(int i=0; i<arr.size(); i++) {
			Map element = (Map)arr.get(i);
		    Utterance utterance = new Utterance();
		    String utterance_id = null;
		    if(!ObjectUtils.isEmpty(element.get("id"))) {
		    	utterance_id = element.get("id").toString(); //primary key
		    	utterance.setId(utterance_id); 
		    }
		    if(!ObjectUtils.isEmpty(element.get("note"))) {
		    	utterance.setNote(element.get("note").toString());
		    }
		    if(!ObjectUtils.isEmpty(element.get("form"))) {
		    	utterance.setForm(element.get("form").toString());
		    }
		    if(!ObjectUtils.isEmpty(element.get("standard_form"))) {
		    	utterance.setStandard_form(element.get("standard_form").toString());
		    }
		    if(!ObjectUtils.isEmpty(element.get("dialect_form"))) {
		    	utterance.setDialect_form(element.get("dialect_form").toString());
		    }
		    if(!ObjectUtils.isEmpty(element.get("speaker_id"))) {
		    	utterance.setSpeaker_no(element.get("speaker_id").toString());
		    }
		    if(!ObjectUtils.isEmpty(element.get("start"))) {
		    	utterance.setStart(Double.parseDouble(element.get("start").toString()));
		    }
		    if(!ObjectUtils.isEmpty(element.get("end"))) {
		    	utterance.setFinish(Double.parseDouble(element.get("end").toString()));
		    }
		    utterance.setMetadata_id(metadata_id); //foreign key
		    
		    JSONArray eojoelArr = (JSONArray)element.get("eojeolList");
		    utterance.setEojoelList(setEojeolList(eojoelArr, utterance_id, metadata_id)); //EojeolList 목록 추가
		    utteranceList.add(utterance);
		}
		long afterTimeCheck = System.currentTimeMillis();
		singletone.setTimeRecorder("parseUtterance", afterTimeCheck-beforeTimeCheck);
		return utteranceList;
	}
	
	/**
	 * EojeolList 파싱
	 * @param arr JSON 배열 
	 * @param utterance_id EojeolList 테이블의 utterance_id 값(FK)
	 * @param metadata_id EojeolList 테이블의 metadata_id 값(FK)
	 * @return 파싱값을 리스트에 담아 eojeolListList로 리턴
	 */
	public List<EojeolList> setEojeolList(JSONArray arr, String utterance_id, int metadata_id){
		long beforeTimeCheck = System.currentTimeMillis();
		List<EojeolList> eojeolListList = new ArrayList<>();
		if(arr != null) {
			for(int i=0; i<arr.size(); i++) {
				Map element = (Map)arr.get(i);
			    EojeolList eojeolList = new EojeolList();
			    if(!ObjectUtils.isEmpty(element.get("id"))) {
			    	eojeolList.setId(element.get("id").toString()); //primary key
			    }
			    if(!ObjectUtils.isEmpty(element.get("standard"))) {
			    	eojeolList.setStandard(element.get("standard").toString());
			    }
			    if(!ObjectUtils.isEmpty(element.get("eojeol"))) {
			    	eojeolList.setEojeol(element.get("eojeol").toString());
			    }
			    if(!ObjectUtils.isEmpty(element.get("end"))) {
			    	eojeolList.setFinish(Integer.parseInt(element.get("end").toString()));
			    }
			    if(!ObjectUtils.isEmpty(element.get("isDialect"))) {
			    	eojeolList.setDialect(Boolean.parseBoolean(element.get("isDialect").toString()));
			    }
			    if(!ObjectUtils.isEmpty(element.get("begin"))) {
			    	eojeolList.setBegin(Integer.parseInt(element.get("begin").toString()));
			    }
			    eojeolList.setUtterance_id(utterance_id); //foreign key
			    eojeolList.setMetadata_id(metadata_id); //foreign key
			    eojeolListList.add(eojeolList);
			}
		}
		long afterTimeCheck = System.currentTimeMillis();
		singletone.setTimeRecorder("parseEojeolList", afterTimeCheck-beforeTimeCheck);
		return eojeolListList;
	}
}
