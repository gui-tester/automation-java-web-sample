package nz.co.sample;

/***
 * Compare two values and return a boolean.
 * Some of the comparisons perform pre-processing before performing the compare.
 *
 */

public class Compare {
    private Compare() { //Hiding the constructor
    }
    /***
     * Does a string match a regex
     * @param regex       The regex that is used for pattern matching, simple matching is performed "/PATTERN/" only
     * @param actualValue The value that the regex will be applied to
     * @return            Boolean indicating if the regex matched or not
     */
    public static Boolean isRegex(String regex, String actualValue ) {
        String strippedRegex = regex.replaceFirst("^/","").replaceFirst("/$", "");
        return actualValue.matches( strippedRegex );
    }

    /***
     * Is is numeric string less than another
     * @param anchor      The string representing the compare, i.e. "&lt;50"
     * @param actualValue The value to be compared
     * @return            Boolean indicating fo the acutal value is less that the anchor value
     */
    public static Boolean isLessThan(String anchor, String actualValue) {
        if("".equals(actualValue))
            return false;
        String testValue = anchor.replaceFirst("^<", "");
        Double actualNumber =  Double.parseDouble(actualValue);
        Double testNumber = Double.parseDouble(testValue);
        return actualNumber < testNumber;
    }

    /***
     * Is is numeric string greater than another
     * @param anchor      The string representing the compare, i.e. "&gt;50"
     * @param actualValue The value to be compared
     * @return            Boolean indicating fo the acutal value is greater that the anchor value
     */
    public static Boolean isGreaterThan(String anchor, String actualValue) {
        if("".equals(actualValue))
            return false;
        String testValue = anchor.replaceFirst("^>","");
        Double actualNumber =  Double.parseDouble(actualValue);
        Double testNumber = Double.parseDouble(testValue);
        return actualNumber > testNumber;
    }

    /***
     * Simple string compare
     * @param actualValue    The value that will compare
     * @param expectedValue  The value that will be compared with
     * @return               Boolean indicating if the two strings are equal
     */
    public static Boolean isEquals(String expectedValue, String actualValue) {
        Boolean result = false;
        if (actualValue != null )  {
            result = actualValue.equalsIgnoreCase(expectedValue);
        }
        return result;
    }

    /***
     * Compare a ISO formatted date with a chronic date string.
     * @param chronicDate  The date in human readable form wrapped in {}, i.e {today}, {30 days from today}.
     * @param actualValue  The date string to be compared against
     * @return             Boolean indicating if the calculated date, in ISO format, matches the actual value
     */
    public static Boolean isIsoDate(String chronicDate, String actualValue ) {
        if("".equals(actualValue))
            return false;
        String strippedChronicDate = chronicDate.replaceFirst("^\\{", "").replaceFirst("}$","");
        String expectedValue = nz.co.sample.DateAndTime.chronicDateAsIso(strippedChronicDate);
        return isEquals(expectedValue,actualValue);
    }

    /***
     * Compare a NZ formatted date with a chronic date string.
     * @param chronicDate  The date in human readable form wrapped in (), i.e (today), (30 days from today).
     * @param actualValue  The date string to be compared against
     * @return             Boolean indicating if the calculated date, in NZ format, matches the actual value
     */
    public static Boolean isNzDate(String chronicDate, String actualValue ) {
        if("".equals(actualValue))
            return false;
        String strippedChronicDate = chronicDate.replaceFirst("^\\(", "").replaceFirst("\\)$","");
        String expectedValue = nz.co.sample.DateAndTime.chronicDateAsNz(strippedChronicDate);
        return isEquals(expectedValue,actualValue);
    }

    public static Boolean isNull( String nullValue ) {
        return nullValue == null;
    }
}
