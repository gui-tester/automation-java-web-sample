package nz.co.sample.googleSearch;

import nz.co.sample.Assertion;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import nz.co.sample.googleScreen.GoogleLandingScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.SQLException;
import javax.inject.Inject;

@ScenarioScoped
public class GoogleSearchSteps {

    private static final Logger log = LogManager.getLogger(GoogleSearchSteps.class.getName());
    private final GoogleLandingScreen googleLandingScreen;
    private Scenario scenario;

    @Inject
    public GoogleSearchSteps() {
        googleLandingScreen = new GoogleLandingScreen();
    }

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
        log.info("FEATURE " + scenario.getUri().toString().substring(scenario.getUri().toString().lastIndexOf("/") + 1) + ": " + scenario.getName() + " " + formatScenarioTags());
    }

    private String formatScenarioTags() {
        String[] tags = scenario.getSourceTagNames().toString().split(",\\s*");
        return String.join(" and ", tags);
    }

    @Given("^Google Search has loaded$")
    public void googleSearchHasLoaded() {
        Assertion.assertResponseValues("Google Search Engine loads successfully",true,googleLandingScreen.getSearchString().isDisplayed());
    }

    @When("^I search for ([^\"]*)")
    public void iSearchForDogBreed(String dogBreed) {
        googleLandingScreen.getSearchString().sendKeys(dogBreed);
        googleLandingScreen.getSearchButton().click();
    }

    @Then("^I see results that match the word ([^\"]*)")
    public void iSeeResultsThatMatchTheWordSearched(String dogBreed) throws SQLException, ClassNotFoundException {
        Assertion.assertResponseValues("The search has returned the expected results",dogBreed,googleLandingScreen.getResultString().toString());
    }

}
