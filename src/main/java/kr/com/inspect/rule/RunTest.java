package kr.com.inspect.rule;

import kr.com.inspect.dto.Rule;

public class RunTest {
	public static void main(String[] args) throws Exception {
		Rule rule1 = new Rule();
		rule1.setFile_name("Rule123456780");
		String content1 = "System.out.println(\"테스트\"); " + "return \"success\";";
		rule1.setContents(content1);
		RuleCompiler ruleCompiler = new RuleCompiler();
		ruleCompiler.create(rule1);
		String object = (String) ruleCompiler.runObject(rule1);
		System.out.println(object);
	}
}
