package kr.com.inspect.parser;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import kr.com.inspect.dto.EojeolList;
import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Speaker;
import kr.com.inspect.dto.Utterance;

public class JsonMaker {
	public String writeMetadataJson(Metadata metadata, 
			List<Speaker> speakerList, List<Utterance> utteranceList, List<EojeolList> eojeolListList) {
		JSONObject obj = new JSONObject();
		JSONObject obj_metadata = new JSONObject();
		obj_metadata.put("creator", "크리에이터");
		JSONArray arr_annotation_level = new JSONArray();
		String temp = "[]";
		String[] tempArr = temp.substring(1, temp.length()-1).split(",");
		for(int i=0; i<tempArr.length; i++) {
			arr_annotation_level.add(tempArr[i]);
		}
		obj_metadata.put("annotation_level", arr_annotation_level);
		obj_metadata.put("year", "");
		obj_metadata.put("sampling", "");
		obj_metadata.put("title", "");
		obj_metadata.put("category", "");
		obj_metadata.put("distributor", "");
		obj.put("metadata", obj_metadata);
		/*
		"title": "LH1D02S0355",
	    "creator": "이주희(dusgkrwkd@naver.com)",
	    "distributor": "",
	    "year": "2020년",
	    "category": "",
	    "annotation_level": [],
	    "sampling": ""
	    */
		
		obj.put("speaker", "");
		obj.put("setting", "");
		obj.put("utterance", "");
		return obj.toJSONString();
	}
}
