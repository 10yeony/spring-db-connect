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
		List<Metadata> result = data.getMetadataAndProgram(0);
		List<String> list = new ArrayList<>();
		String sol = "";

		for (Metadata r : result) {
			sol += String.format("%-20s", "파일명" + r.getTitle());
			sol += String.format("%-40s", "강의명" + r.getProgram().getTitle() + "\t");
			sol += String.format("%-30s", "부제:" + r.getProgram().getSubtitle());
//			if (sol.contains(",")) {
//				sol = sol.replaceAll(",", "/");
//			}
			sol += "\n";
			list.add(sol);
			System.out.println(list);
		}

//		Data data = new Data();
//		List<Metadata> metadata = data.getMetadataAndProgram(0);
//
//		List<String> list = new ArrayList<>();
//		String str = "";
//
//		str += "Title" + "," + "제목" + "," + "부제" + "\n";
//		list.add(str);
//
//		for (Metadata meta : metadata) {
//			str = "";
//			str += meta.getTitle() + "\t";
//			str += meta.getProgram().getTitle() + "\t";
//			str += meta.getProgram().getSubtitle() + "\t" + "\n";
//			str += str.replaceAll("\\r\\n|\\r|\\n", " ");
//			if(str.contains(",")) {
//				str= str.replaceAll("//","");
//			}
//			list.add(str);
//			//Collections.replaceAll(list, "(\\r\\n)+|[\\r\\n]+", " ");
//		}
//		System.out.println(list);
	}

	public void getreplace() {
		String str = "";

		if (str.contains(",")) {
			str = str.replaceAll("//", "");
			System.out.println(str);
		}
	}
}
