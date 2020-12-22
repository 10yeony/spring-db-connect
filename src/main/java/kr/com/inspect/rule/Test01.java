package kr.com.inspect.rule;

import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Program;
import kr.com.inspect.dto.Speaker;
import kr.com.inspect.dto.Utterance;
import kr.com.inspect.dto.EojeolList;

import java.util.*;

public class Test01 {
	public static void main(String[] args) throws Exception {

		Data data = new Data();
		List<Metadata> result = data.getMetadataAndProgram(0);
		String sol="";
		
		for(Metadata r : result) {
			sol += ("파일명:"+r.getTitle());
			sol += "\t";
			sol += ("작성자:"+r.getCreator());
			sol += "\t";
			sol += ("문장수:"+r.getSentence_count());
			sol += "\t";
			sol += ("어절수:"+r.getEojeol_total());
			sol += "\t";
			sol += ("발화자수:"+r.getSpeaker_count());
			sol += "\n";
			System.out.println(sol);
		}
	}
}
