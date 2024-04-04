package nz.co.sample;

import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.assertTrue;

public class Assertion {

    private static final Logger log = LogManager.getLogger(Assertion.class.getName());

    private static StringBuilder resultMessages = new StringBuilder();
    private static Boolean allPassed = true;

    private Assertion() {
    }

    public static boolean isAllPassed() {
        return allPassed;
    }

    public static StringBuilder getResultMessages() {
        return resultMessages;
    }

    public static void assertResponseValues(String key, Boolean expectedValue, Boolean actualValue) {
        Boolean result = expectedValue == actualValue;
        assertResponseValues(result, key, expectedValue.toString(), actualValue.toString());
    }

    public static void assertResponseValues(String key, String expectedValue, String actualValue) {
        Boolean result = expectedValue.equals(actualValue);
        assertResponseValues(result, key, expectedValue, actualValue);
    }

    public static void assertResponseValues(Boolean result, String key, String expectedValue, String actualValue) {
        resultMessages.append(result ? "PASSED (AS EXPECTED):\t" : "FAILED:\t");
        resultMessages.append(key);
        resultMessages.append("\tEXPECTED value: ");
        resultMessages.append(expectedValue);
        resultMessages.append(" and ACTUAL value: ");
        resultMessages.append(actualValue);
        resultMessages.append(";\n");
        if (!result) {
            allPassed = false;
        }
    }


    public static void write(String message) {
        resultMessages.append("\n"+message);
    }

    public static void printAssertion(Scenario scenario) {
        String messages = Assertion.getResultMessages().toString();
        log.info("\n"+messages);
        scenario.log(messages);
        resultMessages = new StringBuilder();
        boolean tempAllPassed = Assertion.isAllPassed();
        allPassed = true;
        assertTrue(tempAllPassed);
    }

    public static void assertValue(int expectedValue, int actualValue, String message){
        assertValue(Integer.toString(expectedValue), Integer.toString(actualValue), message);
    }

    public static void assertValue(double expectedValue, double actualValue, String message){
        //assertValue(Double.toString(expectedValue), Double.toString(actualValue), message);
        Boolean result = Math.abs(expectedValue - actualValue) <= 0.001;
        assertResponseValues(result, message, Double.toString(expectedValue), Double.toString(actualValue));
    }

    public static void assertTextContains(String textSource, String expectedValue, String message){
        String actualValue = Boolean.toString(textSource.contains(expectedValue));
        assertValue("true", actualValue, message);
    }

    public static void assertValueTrue(boolean actualValue, String message){
        assertValue("true", Boolean.toString(actualValue), message);
    }

    public static void assertValue(String expectedValue, String actualValue, String message) {
        Boolean result;
        Character processKey;

        if (expectedValue == null) {
            throw new NullPointerException("expectedValue must not be null");
        }

        processKey = expectedValue.isEmpty() ? 'E' : expectedValue.charAt(0);   // setting to 'E' as we want empty expected values to be handled by the default case

        switch (processKey) {
            case '[':
                result = Compare.isNzDate(expectedValue, actualValue);
                break;
            case '{':
                result = Compare.isIsoDate(expectedValue, actualValue);
                break;
            case '>':
                result = Compare.isGreaterThan(expectedValue, actualValue);
                break;
            case '<':
                result = Compare.isLessThan(expectedValue, actualValue);
                break;
            case '/':
                result = Compare.isRegex(expectedValue, actualValue);
                break;
            default:
                result = Compare.isEquals(expectedValue, actualValue);
        }

        assertResponseValues(result, message, expectedValue, actualValue);
    }
}
