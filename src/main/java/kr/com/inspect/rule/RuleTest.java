package kr.com.inspect.rule;

public class RuleTest {
	public static void main(String[] args) throws Exception {
		RuleCompiler compiler = new RuleCompiler();
		
		String body = "public void test() throws Exception {\r\n" + 
				"		Data d = new Data();\r\n" + 
				"		System.out.println(d.getMetadataAndProgram(0));\r\n" + 
				"	}";
		compiler.create(body);
		compiler.runObject();
	}
}
