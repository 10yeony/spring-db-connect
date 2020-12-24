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

		str += "Title" + "/" + "제목" + "/" + "부제" + "/" + "\n";
		list.add(str);

		for (Metadata meta : metadata) {
			str = "";
			str += meta.getTitle()+"/" + "\t";
			str += meta.getProgram().getTitle()+ "/" + "\t";

			str += meta.getProgram().getSubtitle()+ "/" + "\t" + "\n";
			if (str.contains(",")) {
				str = str.replaceAll(",", " ");
			}
			list.add(str);
		}
		System.out.println(list);
	}

	public void getreplace() {
		String str = "";

		if (str.contains(",")) {
			str = str.replaceAll("//", "");
			System.out.println(str);
		}
	}
}
