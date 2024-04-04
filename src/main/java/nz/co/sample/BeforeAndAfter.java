package nz.co.sample;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Properties;

public class BeforeAndAfter {
    @Before(order = 0)
    public void before() throws IOException, IllegalAccessException {
        TestDriver.setDriver();

        if (StringUtils.isEmpty(System.getenv("APP"))){
            // Will navigate to Evolution url by default. All other url will be managed in Feature folders
            TestDriver.getDriver().navigate().to(constructUrl());
        }

    }

    public static String constructUrl() throws IOException {
        Properties envProperties = WorkingEnvironment.getProperties();
        String url = envProperties.getProperty("url");
        return url;
    }

    @After
    public static void after(Scenario scenario) {
        nz.co.sample.AssertionScreenshot.takeScreenshot(scenario);
        TestDriver.stopDriver();
    }

}
