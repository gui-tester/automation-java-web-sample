package nz.co.sample;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "html:target/cucumber/report.html",
                "json:target/cucumber/TEST-response.json",
                "junit:target/cucumber/TEST-response.xml",
                "rerun:target/rerun.txt"
        },
        tags = "@greenpeace"
)

public class TestRunner {
}
