package nz.co.sample;

import nz.co.sample.Report;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "html:target/cucumber/report.html",
        },
        tags = "@report"
)

public class TestRunnerREPORT {
    private TestRunnerREPORT() {
        //Hide constructor
    }

    @AfterClass
    public static void generateReports() throws IOException {
        Report.generateReports("Sample","Google Search", "Dog Breeds");
    }
}
