package kr.com.inspect.util;

/**
 * 랜덤 키값 생성 (UUID를 생성하는 방법도 있으나 이 방법이 더 다양한 문자열 생성 가능)
 * @author Yeonhee Kim
 * @version 1.0
 */
public class RandomKey {
	
	/**
	 * 길이를 받고 랜덤한 문자열을 리턴
	 * @param len 랜덤 문자열의 길이
	 * @return 랜덤 문자열
	 */
	public String getRamdomString(int len) { 
		char[] charSet = new char[] { 
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
				'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 
				'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
				'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
				'!', '@', '#', '$', '%', '^', '&', '*', '(', ')'
				};

		int idx = 0; 
		StringBuffer sb = new StringBuffer(); 
		//System.out.println("charSet.length :::: "+charSet.length); 
		
		for (int i = 0; i < len; i++) { 
			idx = (int) (Math.random() * charSet.length); // 생성된 난수를 정수로 추출 (소숫점제거) 
			//System.out.println("idx :::: "+idx); 
			sb.append(charSet[idx]); 
		} 
		return sb.toString(); 
	}
}
