package kr.com.inspect.parser;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kr.com.inspect.dto.EojeolList;
import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Speaker;
import kr.com.inspect.dto.Utterance;

/**
 *
 * @author Yeonhee Kim
 * @version 1.0
 */

public class JsonWriter {

	/**
	 *
	 * @param metadata
	 * @param speakerList
	 * @param utteranceList
	 * @param eojeolListList
	 * @return
	 */
	public String writeMetadataJson(Metadata metadata, 
			List<Speaker> speakerList, List<Utterance> utteranceList, List<EojeolList> eojeolListList) {
		JSONObject obj = new JSONObject();
		
		/* metadata */
		JSONObject obj_metadata = new JSONObject();
		obj_metadata.put("title", metadata.getTitle());
		obj_metadata.put("creator", metadata.getCreator());
		obj_metadata.put("distributor", metadata.getDistributor());
		obj_metadata.put("year", metadata.getYear());
		obj_metadata.put("category", metadata.getCategory());
		JSONArray arr_annotation_level = new JSONArray();
		String temp = "[]";
		String[] tempArr = temp.substring(1, temp.length()-1).split(",");
		for(int i=0; i<tempArr.length; i++) {
			if(tempArr.length == 1 && tempArr[i].equals("")) {
				break;
			}
			arr_annotation_level.add(tempArr[i]);
		}
		obj_metadata.put("annotation_level", arr_annotation_level);
		obj_metadata.put("sampling", metadata.getSampling());
		obj.put("metadata", obj_metadata);
		
		/* speaker */
		JSONArray obj_speaker_arr = new JSONArray();
		for(int i=0; i<speakerList.size(); i++) {
			JSONObject obj_speaker = new JSONObject();
			Speaker speaker = speakerList.get(i);
			if(speaker.getShortcut() == 0) {
				speaker.setShortcut(null);
			}
			obj_speaker.put("no", speaker.getNo());
			obj_speaker.put("shortcut", speaker.getShortcut());
			obj_speaker.put("occupation", speaker.getOccupation());
			obj_speaker.put("sex", speaker.getSex());
			obj_speaker.put("name", speaker.getName());
			obj_speaker.put("age", speaker.getAge());
			obj_speaker.put("birthplace", speaker.getBirthplace());
			obj_speaker.put("current_residence", speaker.getCurrent_residence());
			obj_speaker.put("principal_residence", speaker.getPricipal_residence());
			obj_speaker.put("education", speaker.getEducation());
			obj_speaker_arr.add(obj_speaker);
		}
		obj.put("speaker", obj_speaker_arr);
		
		/* setting */
		JSONObject obj_setting = new JSONObject();
		obj_setting.put("relation", metadata.getRelation());
		obj.put("setting", obj_setting);
		
		/* utterance */
		JSONArray obj_utterance_arr = new JSONArray();
		int eojeolListIdx = 0; 
		for(int i=0; i<utteranceList.size(); i++) {
			JSONObject obj_utterance = new JSONObject();
			Utterance utterance = utteranceList.get(i);
			obj_utterance.put("id", utterance.getId());
			obj_utterance.put("start", utterance.getStart());
			obj_utterance.put("end", utterance.getFinish());
			obj_utterance.put("speaker_id", utterance.getSpeaker_no());
			obj_utterance.put("form", utterance.getForm());
			obj_utterance.put("standard_form", utterance.getStandard_form());
			obj_utterance.put("dialect_form", utterance.getDialect_form());
			obj_utterance.put("note", utterance.getNote());
			
			/* eojeolList */
			JSONArray obj_eojeolList_arr = new JSONArray();
			for(int j=eojeolListIdx; j<eojeolListList.size(); j++) {
				JSONObject obj_eojeolList = new JSONObject();
				EojeolList eojeolList = eojeolListList.get(j);
				
				if(eojeolList.getUtterance_id().equals(utterance.getId())) {
					obj_eojeolList.put("eojeol", eojeolList.getEojeol());
					obj_eojeolList.put("standard", eojeolList.getStandard());
					obj_eojeolList.put("begin", eojeolList.getBegin());
					obj_eojeolList.put("end", eojeolList.getFinish());
					obj_eojeolList.put("isDialect", eojeolList.isDialect());
					obj_eojeolList_arr.add(obj_eojeolList);
				}else {
					continue;
				}
			}
			obj_utterance.put("eojeolList", obj_eojeolList_arr);
			obj_utterance_arr.add(obj_utterance);
		}
		obj.put("utterance", obj_utterance_arr);
		
		ObjectWriter prettyPrinter = new ObjectMapper().writerWithDefaultPrettyPrinter();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String prettyJson = gson.toJson(obj);
		return prettyJson;
	}
}
