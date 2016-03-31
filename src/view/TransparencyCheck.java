package view;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class TransparencyCheck {
 
	private static Pattern pattern;
	private static Matcher matcher;
 
	private static final String TRANS_PATTERN = 
		"^[0-9][0-9]?$|^100$";
 
	public TransparencyCheck() {
		pattern = Pattern.compile(TRANS_PATTERN);
	}
 
	/**
	 * Validate hex with regular expression
	 * 
	 * @param hex
	 *            hex for validation
	 * @return true valid hex, false invalid hex
	 */
	public static boolean validate(final String hex) {
 
		matcher = pattern.matcher(hex);
		return matcher.matches();
 
	}
}