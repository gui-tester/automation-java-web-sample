package nz.co.sample;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.time.LocalDateTime;

public class Element {
    private static final Logger log = LogManager.getLogger(Element.class.getName());
    private By locator = null;
    private WebElement webElement = null;

    public Element(By locator) {
        this.locator = locator;
    }

    public Element(WebElement element) {
        this.webElement = element;
    }

    public WebElement getWebElement() {
        return this.webElement;
    }

    public boolean exists() {
        if (this.locator == null) {
            return this.webElement.isDisplayed();
        } else {
            return TestDriver.getDriver().findElements(this.locator).size() > 0;
        }
    }

    public void focus() {
        (new Actions(TestDriver.getDriver())).moveToElement(this.findElement()).perform();
    }

    public WebElement findElement() {
        return this.findElement(10);
    }

    public WebElement findElement(int timeOut) {
        if (this.locator == null) {
            return this.webElement;
        } else {
            Wait<WebDriver> wait = new FluentWait<>(TestDriver.getDriver())
                    // Specify the timeout of the wait
                    .withTimeout(Duration.ofSeconds(timeOut))
                    // Specify polling time
                    .pollingEvery(Duration.ofMillis(250))
                    // Specify what exceptions to ignore
                    .ignoring(NoSuchElementException.class);

            //This is how we specify the condition to wait on.
            wait.until(ExpectedConditions.visibilityOfElementLocated(this.locator));
            return TestDriver.getDriver().findElement(this.locator);
        }
    }

    public void click() { this.findElement().click(); }

    //TODO: Replace thread.sleep with a smarter await for element logic
    public void waitForElementToLoad() {
        if (this.locator != null) {
            Wait<WebDriver> wait = new FluentWait<>(TestDriver.getDriver())
                    // Specify the timeout of the wait
                    .withTimeout(Duration.ofSeconds(60))
                    // Specify polling time
                    .pollingEvery(Duration.ofMillis(250))
                    // Specify what exceptions to ignore
                    .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);

            //This is how we specify the condition to wait on.
            wait.until(ExpectedConditions.visibilityOfElementLocated(this.locator));
        }
    }

    public void waitForElementRefreshed() {
        if (this.locator != null) {
            LocalDateTime currentDateTime = LocalDateTime.now();
            try {
                // Get current date time
                Wait<WebDriver> wait = new FluentWait<>(TestDriver.getDriver())
                        // Specify the timeout of the wait
                        .withTimeout(Duration.ofSeconds(5))
                        // Specify polling time
                        .pollingEvery(Duration.ofMillis(250))
                        // Specify what exceptions to ignore
                        .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);

                //This is how we specify the condition to wait on.
                wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(this.findElement())));
                currentDateTime = LocalDateTime.now();
//                log.info("waitForElementRefreshed - StaleElement");
            }
            catch (Exception TimeoutException ) {
                currentDateTime = LocalDateTime.now();
                log.info("waitForElementRefreshed - no StaleElement");
            }
        }
    }


    public void waitForTextToBe(String textValue) {
        if (this.locator != null) {
            Wait<WebDriver> wait = new FluentWait<>(TestDriver.getDriver())
                    // Specify the timeout of the wait
                    .withTimeout(Duration.ofSeconds(60))
                    // Specify polling time
                    .pollingEvery(Duration.ofMillis(250))
                    // Specify what exceptions to ignore
                    .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);

            //This is how we specify the condition to wait on.
            wait.until(ExpectedConditions.textToBe(this.locator, textValue));
        }
    }

    public void waitForAttributeToBe(String attributeName, String attributeValue) {
        if (this.locator != null) {
            Wait<WebDriver> wait = new FluentWait<>(TestDriver.getDriver())
                    // Specify the timeout of the wait
                    .withTimeout(Duration.ofSeconds(120))
                    // Specify polling time
                    .pollingEvery(Duration.ofMillis(250))
                    // Specify what exceptions to ignore
                    .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);

            //This is how we specify the condition to wait on.
            wait.until(ExpectedConditions.attributeToBe(this.locator, attributeName, attributeValue));
        }
    }

    public void waitForAttributeToBeNotEmpty(String attributeName) {
        if (this.locator != null) {
            Wait<WebDriver> wait = new FluentWait<>(TestDriver.getDriver())
                    // Specify the timeout of the wait
                    .withTimeout(Duration.ofSeconds(30))
                    // Specify polling time
                    .pollingEvery(Duration.ofMillis(250))
                    // Specify what exceptions to ignore
                    .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);

            //This is how we specify the condition to wait on.
            wait.until(ExpectedConditions.attributeToBeNotEmpty(this.findElement(), attributeName));
        }
    }

    public void waitForInvisibilityOfElementLocated() {
        if (this.locator != null) {
            Wait<WebDriver> wait = new FluentWait<>(TestDriver.getDriver())
                    // Specify the timeout of the wait
                    .withTimeout(Duration.ofSeconds(180))
                    // Specify polling time
                    .pollingEvery(Duration.ofMillis(2))
                    // Specify what exceptions to ignore
                    .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);

            //This is how we specify the condition to wait on.
            wait.until(ExpectedConditions.invisibilityOfElementLocated(this.locator));
        }
    }

    public void waitForElementToBeClickable() {
        if (this.locator != null) {
            Wait<WebDriver> wait = new FluentWait<>(TestDriver.getDriver())
                    // Specify the timeout of the wait
                    .withTimeout(Duration.ofSeconds(60))
                    // Specify polling time
                    .pollingEvery(Duration.ofMillis(250))
                    // Specify what exceptions to ignore
                    .ignoring(NoSuchElementException.class);

            //This is how we specify the condition to wait on.
            wait.until(ExpectedConditions.elementToBeClickable(this.locator));
        }
    }

    public String getText() {
        return this.findElement().getText();
    }

    public String getLabel() {
        return this.findElement().findElement(By.xpath("../label")).getText();
    }

    public Boolean isBorderRed() {
        try {
            return this.findElement().getAttribute("style").contains("red");
        } catch (Exception labelException) {
            log.debug(labelException);
            return false;
        }
    }

    public Boolean isLabelRed() {
        try {
            return this.findElement().findElement(By.xpath("../label")).getAttribute("style").contains("red");
        } catch (Exception labelException) {
            log.debug(labelException);
        }
        try {
            return this.findElement().findElement(By.xpath("../../label")).getAttribute("style").contains("red");
        } catch (Exception labelException) {
            log.debug(labelException);
            return false;
        }
    }

    public String getErrorMessage() {
        try {
            return this.findElement().findElement(By.xpath("../div[@id='idpop']/div[@class='popover-inner']/div[@id='idpopcon']")).getText();
        } catch (Exception errorMessageException) {
            log.debug(errorMessageException);
            return "";
        }
    }

    public Boolean isErrorMessageRed() {
        try {
            return this.findElement().findElement(By.xpath("../div[@id='idpop']/div[@class='popover-inner']/div[@id='idpopcon']")).getAttribute("style").contains("red");
        } catch (Exception errorMessageException) {
            log.debug(errorMessageException);
            return false;
        }
    }
    public String getDropdownSelectedValue() {
        Select dropdown = new Select(this.findElement());
        return dropdown.getFirstSelectedOption().getText();
    }

    public void verifyValidationMessage(String fieldName, Boolean isFieldMandatory) {
        Boolean results = false;

        //Error Message
        try { results = this.findElement().findElement(By.xpath("../div[@id='idpop']/div[@class='popover-inner']/div[@id='idpopcon']")).getAttribute("style").contains("red");
        } catch (Exception errorMessageException) { results = false; }
        nz.co.sample.Assertion.assertResponseValues(fieldName + " error message is red", isFieldMandatory, results);

        //Field border
        try { results = this.findElement().getAttribute("style").contains("red");
        } catch (Exception borderException) { results = false; }
        nz.co.sample.Assertion.assertResponseValues(fieldName + " border is red", isFieldMandatory, results);

        //Field label
        try { results = this.findElement().findElement(By.xpath("../label")).getAttribute("style").contains("red");
        } catch (Exception labelException) { results = false; }
        if (results == false) {
            try { results = this.findElement().findElement(By.xpath("../../label")).getAttribute("style").contains("red");
            } catch (Exception labelException) { results = false; }
        }
        nz.co.sample.Assertion.assertResponseValues(fieldName + " label is red", isFieldMandatory, results);
    }

    public void verifyMandatoryField(String fieldName) {
        Boolean results = false;

        //Field background : background-color: #EDDADA
        try { results = this.findElement().getCssValue("background-color").contentEquals("rgba(237, 218, 218, 1)");
        } catch (Exception backgroundColorException) { results = false;}
        nz.co.sample.Assertion.assertResponseValues(fieldName + " background color is pale red", true, results);

        if (this.findElement().getText().isEmpty()) {
            this.verifyValidationMessage(fieldName, true);
        }

        nz.co.sample.Assertion.assertResponseValues(fieldName + " is editable",true, this.findElement().isEnabled());
    }

    public void verifyNonMandatoryField(String fieldName) {
        Boolean results = false;

        //Field background : background-color: #FFFFFF
        try { results = this.findElement().getCssValue("background-color").contentEquals("rgba(255, 255, 255, 1)");
        } catch (Exception backgroundColorException) { results = false;}
        nz.co.sample.Assertion.assertResponseValues(fieldName + " background color is white", true, results);

        nz.co.sample.Assertion.assertResponseValues(fieldName + " is editable",true, this.findElement().isEnabled());
    }

    public void verifyCheckbox(String fieldName) {
        Boolean results = false;

        //Field background : background-color: rgba(0, 0, 0, 0) - transparent
        try { results = this.findElement().getCssValue("background-color").contentEquals("rgba(0, 0, 0, 0)");
        } catch (Exception backgroundColorException) { results = false;}
        nz.co.sample.Assertion.assertResponseValues(fieldName + " background color is transparent", true, results);
    }

    public void verifyReadOnlyField(String fieldName) {
        Boolean results = false;

        //Field background : background-color: #EEEEEE
        try { results = this.findElement().getCssValue("background-color").contentEquals("rgba(238, 238, 238, 1)");
        } catch (Exception backgroundColorException) { results = false; }
        nz.co.sample.Assertion.assertResponseValues(fieldName + " background color is grey", true, results);

        //Field attribute disabled="disabled"
        nz.co.sample.Assertion.assertResponseValues(fieldName + " is read only",true, !this.findElement().isEnabled());
    }

    public void verifyMandatoryReadOnlyField(String fieldName) {
        Boolean results = false;

        //Field background : background-color: #EDDADA
        try { results = this.findElement().getCssValue("background-color").contentEquals("rgba(237, 218, 218, 1)");
        } catch (Exception backgroundColorException) { results = false; }
        nz.co.sample.Assertion.assertResponseValues(fieldName + " background color is pale red", true, results);

        //Field attribute disabled="disabled"
        nz.co.sample.Assertion.assertResponseValues(fieldName + " is read only",true, !this.findElement().isEnabled());
    }

    public void verifyReadOnlyFieldLookup(String fieldName) {
        Boolean results = false;

        //Field background : background-color: #FFFFFF
        try { results = this.findElement().getCssValue("background-color").contentEquals("rgba(255, 255, 255, 1)");
        } catch (Exception backgroundColorException) { results = false; }
        nz.co.sample.Assertion.assertResponseValues(fieldName + " background color is white", true, results);

        //Field attribute disabled="disabled"
        nz.co.sample.Assertion.assertResponseValues(fieldName + " is read only",true, !this.findElement().isEnabled());
    }

    public void sendKeys(String keys) {
        this.findElement().sendKeys(new CharSequence[]{keys});
    }

    public void selectOptionWithIndex(Integer index) { this.selectOptionWithIndex(index); }

    public void clearAndSendKeys(String keys) {
        if (this.locator != null) {
            Wait<WebDriver> wait = new FluentWait<>(TestDriver.getDriver())
                    // Specify the timeout of the wait
                    .withTimeout(Duration.ofSeconds(120))
                    // Specify polling time
                    .pollingEvery(Duration.ofMillis(250))
                    // Specify what exceptions to ignore
                    .ignoring(NoSuchElementException.class);

            //This is how we specify the condition to wait on.
            wait.until(ExpectedConditions.elementToBeClickable(this.locator));
            this.findElement().sendKeys(Keys.chord(Keys.CONTROL, "a") + keys);
        }
    }

    public Boolean isSelected() {
        try {
            return this.findElement().isSelected();
        } catch (Exception var2) {
            log.info(var2);
            return false;
        }
    }

    public Boolean isDisplayed() {
        try {
            return this.findElement().isDisplayed();
        } catch (Exception var2) {
            log.info(var2);
            return false;
        }
    }

    public Boolean isDisplayed(int timeOut) {
        try {
            Wait<WebDriver> wait = new FluentWait<>(TestDriver.getDriver())
                    // Specify the timeout of the wait
                    .withTimeout(Duration.ofSeconds(timeOut))
                    // Specify polling time
                    .pollingEvery(Duration.ofMillis(250))
                    // Specify what exceptions to ignore
                    .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);

            //This is how we specify the condition to wait on.
            wait.until(ExpectedConditions.visibilityOfElementLocated(this.locator));

            return this.findElement().isDisplayed();
        } catch (TimeoutException var2) {
            log.info(var2);
            return false;
        }
    }

    public Boolean isEnabled() {
        Boolean results = false;
        WebElement element = this.findElement();
        if (element != null) {
            results = this.findElement().isEnabled();
        }

        return results;
    }

    public void clickWhenEnabled() {
        if (this.locator != null) {
            Wait<WebDriver> wait = new FluentWait<>(TestDriver.getDriver())
                    // Specify the timeout of the wait
                    .withTimeout(Duration.ofSeconds(120))
                    // Specify polling time
                    .pollingEvery(Duration.ofMillis(250))
                    // Specify what exceptions to ignore
                    .ignoring(NoSuchElementException.class);

            //This is how we specify the condition to wait on.
            wait.until(ExpectedConditions.elementToBeClickable(this.locator));
            this.findElement().click();
        }
    }

    public void clear() {
        this.findElement().clear();
    }

    public String getAttribute(String attributeName) {
        return this.findElement().getAttribute(attributeName);
    }
    
    public void singleSelectByValue(String value) {
    	Select selectElement = new Select(this.findElement()); 
    	selectElement.selectByValue(value);
    }
}
