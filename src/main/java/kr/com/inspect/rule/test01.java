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

		List<String> list = new ArrayList<>();
		String str = "";

		str += "Title" + "," + "제목" + "," + "부제";
		list.add(str);

		for (Metadata meta : metadata) {
			str = "";
			str += meta.getTitle() + "";
			str += meta.getProgram().getTitle() + "";
			str += meta.getProgram().getSubtitle();
			str.replace(",","");
			list.add(str);
		}
		System.out.println(list);
	}
}
