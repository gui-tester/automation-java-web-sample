package nz.co.sample;

import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class AssertionScreenshot {

    private AssertionScreenshot() {}

    public static void printAssertion(Scenario scenario) {
        Assertion.printAssertion(scenario);
        takeScreenshot(scenario);
    }

    public static void takeScreenshot(Scenario scenario) {
        byte[] screenshot = ((TakesScreenshot) TestDriver.getDriver()).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", "Test screenshot");
    }
}
