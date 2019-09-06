package yuyi.family.common.util;

public class NUMUtil {
	
	public static String RandomNumber(int digit) {
		String result="";
		for(int i=0;i<digit;i++) {
			result+=((int)(Math.random()*(9-0+1)));
		}
		return result;
	}

}
