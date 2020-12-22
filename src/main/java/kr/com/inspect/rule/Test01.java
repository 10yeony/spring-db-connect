package kr.com.inspect.rule;

import kr.com.inspect.rule.Data;
import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Program;
import kr.com.inspect.dto.Speaker;
import kr.com.inspect.dto.Utterance;
import kr.com.inspect.dto.EojeolList;

import java.util.*;

public class Test01 {
	public Object run() throws Exception {
		Data data = new Data();
		Metadata metadata = new Metadata();
		List<Metadata> result = data.getMetadataAndProgram(0);
		
		System.out.println(result);
		return result;
	}
}
