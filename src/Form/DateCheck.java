package Form;



import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class DateCheck {
 
	private static Pattern pattern;
	private static Matcher matcher;
 
	private static final String DATE_PATTERN = 
		"^[2][0-2][0-9][0-9]?-?([0][1-9]?|[1][0-2])?-?([0-2][0-9]?|[3][0-1])?$";
 
	public DateCheck() {
		pattern = Pattern.compile(DATE_PATTERN);
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