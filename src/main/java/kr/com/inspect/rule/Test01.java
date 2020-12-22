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
			sol += String.format("%-25s", "파일명:"+r.getTitle()+"\t");
			sol += String.format("%.15s", "문장수:"+ r.getSentence_count()+"\t");
			sol += String.format("%.15s", "어절수:"+r.getEojeol_total()+"\t");
			sol += String.format("%.20s", "발화자수:"+r.getSpeaker_count()+"\t");
          sol += String.format("%-35s", "작성자:"+r.getCreator());
			sol += "\n";
			System.out.println(sol);
		}
	}
}
