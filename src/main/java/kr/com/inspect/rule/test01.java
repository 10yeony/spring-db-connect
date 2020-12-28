package kr.com.inspect.rule;

import java.util.List;

import kr.com.inspect.dto.Metadata;
import kr.com.inspect.rule.Data;
import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Program;
import kr.com.inspect.dto.Speaker;
import kr.com.inspect.dto.Utterance;
import kr.com.inspect.dto.EojeolList;
import java.util.*;

public class test01 {
	public static void main(String[] args) throws Exception {
		Data data = new Data();
		List<Metadata> metadata = data.getMetadataAndProgram(0);

		List<List<Object>> result = new ArrayList<>();
		List<Object> list = new ArrayList<>();

		list.add("Title");
		list.add("제목");
		list.add("부제");
		result.add(list);

		for (Metadata meta : metadata) {
			list = new ArrayList<>();
			list.add(replace(meta.getTitle()));
			list.add(replace(meta.getProgram().getTitle()));
			list.add(replace(meta.getProgram().getSubtitle()));
			result.add(list);
		}
	}

	public static Object replace(Object obj) {
		try {
			if (obj.toString().contains(",")) {
				obj = obj.toString().replaceAll(",", " ");
			}
		} catch (NullPointerException e) {
			//e.printStackTrace();
		}
		return obj;
	}
}
