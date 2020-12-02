package kr.com.inspect.report;

public class TestRuleCompiler {
    public int Test(int[] list){
        int result = 0;
        for(int i : list)
            result = result + i;
        return result;
    }
}
