package nz.co.sample;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

/***
 *
 * @author jbentillo
 * This class was created so we could easily control any element selections and implement explicit wait to dynamically wait the element before performing any actions.
 * This class can also have all browser related high level navigations and control. All test and input should be covered under POM and/or step implementation
 */
public abstract class SeleniumWrapper {

    public WebDriver driver;
    private long timeout = 15;
    private Logger logger;

    public SeleniumWrapper(WebDriver driver) {
        this.__init__(driver, this.timeout);
    }

    public SeleniumWrapper(WebDriver driver, long timeout) {
        this.__init__(driver, timeout);
    }

    private void __init__(WebDriver driver, long timeout) {
        this.driver = driver;
        this.timeout = timeout;
        this.logger = LoggerFactory.getLogger(SeleniumWrapper.class);
    }

    /**
     * This method was created to explicitly wait the element before any actions performed
     * @param by: By.Id, By.Name, ... (ex. By.Id('username'), By.Name('password'))
     * @return
     */
    public WebElement findElement(By by) {
        try {
            (new WebDriverWait(driver, Duration.ofSeconds(timeout))).until(ExpectedConditions.presenceOfElementLocated(by));
            WebElement element = driver.findElement(by);
            highlightElement(element);
            return element;
        }catch (NoSuchElementException ex) {
            logger.error("Element not found. " +  by.toString());
        }catch (Exception ex) {
            logger.error("An error encountered searching element. " +  by.toString());
        }

        return null;
    }

    public List<WebElement> findElements(By by) {
        try {
            (new WebDriverWait(driver, Duration.ofSeconds(timeout))).until(ExpectedConditions.presenceOfElementLocated(by));
            List<WebElement> elements = driver.findElements(by);

            for (WebElement element : elements){
                highlightElement(element);
            }

            return elements;
        }catch (NoSuchElementException ex) {
            logger.error("Element not found. " +  by.toString());
        }catch (Exception ex) {
            logger.error("An error encountered searching element. " +  by.toString());
        }

        return null;
    }

    /**
     * This method to highlight the web element for better visibility and revert back to original style.
     * @param element: WebElement
     * @return: WebElement to further re-use the element instance
     */
    private WebElement highlightElement(WebElement element) {
        String original_style = element.getAttribute("style"); // Get original attribute so we could revert back the style

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].setAttribute('style', 'border:2px solid red')", element);

        sleep(0.5);

        if (original_style.isEmpty()) {
            jsExecutor.executeScript("arguments[0].removeAttribute('style', '')", element);
        }else {
            jsExecutor.executeScript(String.format("arguments[0].setAttribute('style', '%s')", original_style), element);
        }
        return element;
    }

    /**
     * Method to explicitly wait the element before action click()
     * @param by: By.Id, By.Name, ... (ex. By.Id('username'), By.Name('password'))
     */
    public void elementClick(By by) throws Exception {
        elementClick(findElement(by));
    }

    /**
     * Method to explicitly wait the element before action click()
     * @param element an instance of the WebElement to click
     */
    public void elementClick(WebElement element) throws Exception {
        try {
            (new WebDriverWait(driver, Duration.ofSeconds(timeout))).until(ExpectedConditions.elementToBeClickable(element));
            Actions actions =  new Actions(driver);
            actions.moveToElement(element).click().perform();
        }catch (StaleElementReferenceException ex) {
            logger.error("StaleElementReferenceException: " + ex);
            throw new StaleElementReferenceException(ex.getMessage());
        }catch (Exception ex) {
            logger.error("An error occurred: " + ex);
            throw new Exception(ex);
        }
    }

    /**
     * Note: Ideally, we should not be using javascript to control element since Selenium already supported this.
     * Due to intermittent and trivial issues of moveToElement and scrollToElement selenium API, we can use this method to selected web elements only.
     * @param element Web Element
     * @throws Exception
     */
    public void elementClickJS(WebElement element) throws Exception {
        try {
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].scrollIntoView(true);", element);
            sleep(0.5);
            (new WebDriverWait(driver, Duration.ofSeconds(timeout))).until(ExpectedConditions.elementToBeClickable(element));
            executor.executeScript("arguments[0].click();", element);
        }catch (StaleElementReferenceException ex) {
            logger.error("StaleElementReferenceException: " + ex);
            throw new StaleElementReferenceException(ex.getMessage());
        }catch (Exception ex) {
            logger.error("An error occurred: " + ex);
            throw new Exception(ex);
        }
    }

    public void elementSendKeys(WebElement element, CharSequence... keysToSend){
        //element.clear(); // clear() method sometimes failed/delayed in numeric spinner input
        //element.sendKeys(keysToSend);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), keysToSend[0]);
    }

    public void scrollToElement(WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    public String getElementValue(WebElement element){
        return element.getAttribute("value");
    }

    public void sleep(double secs) {
        try {
            Thread.sleep((long)(secs * 1000));
        } catch (InterruptedException e) {
            logger.error("InterruptedException: " + e.getMessage());
        }
    }

}
