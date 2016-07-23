package P3_M6_MQMB_HDMPROXY_PROJECT;
import java.util.Random;
public class MiscFuncs {

	public static boolean bDebug = true;

	private static Random rand = new Random();

	public static final int getRandom(int range) {

		// Random integers that range from from 0 to range
		return rand.nextInt(range + 1);
	}

	/**
	 * Is a string empty?
	 * 
	 * @param str
	 * @return true if the string is empty. false o/w
	 */
	public static final boolean isStringEmpty(String str) {
		if ((null == str) || (0 == str.length())) {
			return true;
		}
		return false;
	}

	public static final String removeChar(String s, char c) {
		String r = "";
		if (!isStringEmpty(s)) {

			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) != c)
					r += s.charAt(i);
			}
		}
		return r;
	}

	/**
	 * String to int.
	 * 
	 * @param str -
	 *            Input string
	 * @return int value of string.
	 *  
	 */
	public static final int strToInt(String str) {
		int ret = -1;
		try {
			ret = Integer.parseInt(str);
		} catch (NumberFormatException e) {
		}
		return ret;
	}


}
