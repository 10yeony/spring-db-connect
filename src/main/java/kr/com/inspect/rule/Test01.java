package kr.com.inspect.rule;

import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Program;
import kr.com.inspect.dto.Speaker;
import kr.com.inspect.dto.Utterance;
import kr.com.inspect.dto.EojeolList;

import java.util.*;

public class Test01 {
	public static void main(String[] args) throws Exception {
		List<Map<String, Object>> mapList01 = new ArrayList<>();
		List<Map<String, Object>> mapList02 = new ArrayList<>();
		List<Map<String, Object>> mapList03 = new ArrayList<>();
		Map<String, Object> map = null;
		Data data = new Data();
		List<Metadata> metadata = data.getMetadataAndProgram(0);
		List<Utterance> utterance = data.getUtterance(0);

		int i = 0;

		for (Metadata meta : metadata) {
			map = new HashMap<>();
			map.put("번호", meta.getId());
			map.put("파일명", meta.getTitle());
			//map.put("강의명", meta.getProgram().getTitle());
			//map.put("부제", meta.getProgram().getSubtitle());
			map.put("문장수", meta.getSentence_count());
			map.put("어절수", meta.getEojeol_total());
			map.put("발화자수", meta.getSpeaker_count());
			map.put("작성자", meta.getCreator());
			mapList01.add(map);
			i++;
			if (i == 1)
				break;
		}
		//System.out.println(mapList01);

		for (Utterance ut : utterance) {
			map = new HashMap<>();
			//map.put("번호", ut.getId());
			//map.put("형태", ut.getForm());
			//map.put("순한글", ut.getStandard_form());
			map.put("외래어", ut.getDialect_form());
			//map.put("발화자 번호", ut.getSpeaker_no());
			//map.put("메타 번호", ut.getMetadata_id());
			map.put("구분","\n");
			mapList02.add(map);
			i++;
			if (i == 1)
				break;
		}
		System.out.println(mapList02);
	}
}