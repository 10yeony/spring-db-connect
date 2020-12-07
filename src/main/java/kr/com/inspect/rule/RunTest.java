package kr.com.inspect.rule;

import kr.com.inspect.dto.Rule;

public class RunTest {
	public static void main(String[] args) throws Exception {
		System.out.println("스레드 적용 전 ====================");
		Rule rule1 = new Rule();
		rule1.setFile_name("Rule123456780");
		String content1 = "System.out.println(\"테스트\"); " + "return \"success\";";
		rule1.setContents(content1);
		RuleCompiler ruleCompiler = new RuleCompiler();
		ruleCompiler.create(rule1);
		String object = (String) ruleCompiler.runObject(rule1);
		System.out.println(object);
		
		System.out.println("스레드 적용 ====================");
		Rule rule2 = new Rule();
		rule2.setFile_name("Rule123456789");
		String content2 = "System.out.println(\"테스트\"); " + "return \"success\";";
		rule2.setContents(content2);
		rule2.setFile_name("Rule123456789");
		RuleCompilerTest ruleCompilerTest = new RuleCompilerTest();
		ruleCompilerTest.create(rule2);
		ruleCompilerTest.runObject(rule2);
		System.out.println(object);
	}
}
