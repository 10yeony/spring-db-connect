package kr.com.inspect.rule;

public class RuleWork extends Thread {
	@Override
	public void run(){
		try {
			Object result = work();
		}catch(Exception e) {
			
		}
	}
	
	public Object work() {
		
		return "";
	}
}
