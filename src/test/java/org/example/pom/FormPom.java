package org.example.pom;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.utils.Utils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.ByteArrayInputStream;


public class FormPom {

    private static final Logger logger = LogManager.getLogger(FormPom.class);
    private final WebDriver driver;
    private final JavascriptExecutor js;

    private void takeScreenshot(String stepName) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(stepName, "image/png", new ByteArrayInputStream(screenshot), ".png");
        } catch (Exception e) {
            Allure.addAttachment("Screenshot Error", e.toString());
        }
    }

    @FindBy(xpath = "//*[text()='Forms']")
    private WebElement form;

    @FindBy(xpath = "//*[text()='Practice Form']")
    private WebElement practiceForm;

    @FindBy(id = "firstName")
    private WebElement firstName;

    @FindBy(id = "lastName")
    private WebElement lastName;

    @FindBy(id = "userEmail")
    private WebElement userEmail;

    @FindBy(id = "userNumber")
    private WebElement userPhone;

    @FindBy(id = "dateOfBirthInput")
    private WebElement DOB;

    @FindBy(id = "subjectsInput")
    private WebElement subject;

    @FindBy(id = "state")
    private WebElement state;

    @FindBy(id = "city")
    private WebElement city;

    @FindBy(id = "submit")
    private WebElement submit;

    public FormPom(WebDriver driverParam) {
        this.driver = driverParam;
        this.js = (JavascriptExecutor) driverParam;
        PageFactory.initElements(driverParam, this);
    }

    // ── Navigare ──────────────────────────────────────────────────────────────

    public void clickForms() {
        takeScreenshot("Before Click-Form");
        closeAdverts();
        scrollToElement(form);
        Utils.explicitWait(driver, ExpectedConditions.elementToBeClickable(form), 10);
        js.executeScript("arguments[0].click();", form);
        logger.info("Click pe 'Forms'");
        takeScreenshot("After Click-Form");
    }

    public void clickPracticeForm() {
        takeScreenshot("Before Click-Practice");
        Utils.explicitWait(driver, ExpectedConditions.elementToBeClickable(practiceForm), 10);
        practiceForm.click();
        Utils.explicitWait(driver, ExpectedConditions.visibilityOf(firstName), 10);
        logger.info("Pagina Practice Form incarcata");
        takeScreenshot("After Click-Practice");
    }

    // ── Campuri formular ──────────────────────────────────────────────────────

    public void setFirstName(String value) {
        takeScreenshot("Before Set FN");
        Utils.explicitWait(driver, ExpectedConditions.visibilityOf(firstName), 10);
        firstName.clear();
        firstName.sendKeys(value);
        takeScreenshot("After FN");
    }

    public void setLastName(String value) {
        takeScreenshot("Before Set LN");
        lastName.clear();
        lastName.sendKeys(value);
        takeScreenshot("After Set LN");
    }

    public void setUserEmail(String value) {
        takeScreenshot("Before Set Email");
        userEmail.clear();
        userEmail.sendKeys(value);
        takeScreenshot("After Set Email");
    }

    public void setPhone(String value) {
        takeScreenshot("Before Set Phone");
        Utils.explicitWait(driver, ExpectedConditions.visibilityOf(userPhone), 10);
        userPhone.clear();
        userPhone.sendKeys(value);
        takeScreenshot("After Set Phone");
    }

    public void setGender(String value) {
        takeScreenshot("Before Set Gender");
        WebElement genderLabel = driver.findElement(
                By.xpath("//*[@id='genterWrapper']//label[text()='" + value + "']"));
        js.executeScript("arguments[0].click();", genderLabel);
        logger.info("Gender setat: {}", value);
        takeScreenshot("After Set Gender");
    }

    public void setDOB(String value) {
        takeScreenshot("Before Set DOB");
        Utils.explicitWait(driver, ExpectedConditions.elementToBeClickable(DOB), 10);
        DOB.click();
        DOB.sendKeys(Keys.CONTROL + "a");
        DOB.sendKeys(value);
        DOB.sendKeys(Keys.ENTER);
        logger.info("DOB setat: {}", value);
        takeScreenshot("After Set DOB");
    }

    public void setSubject(String value) {
        takeScreenshot("Before Set Subject");
        Utils.explicitWait(driver, ExpectedConditions.elementToBeClickable(subject), 10);
        subject.clear();
        subject.sendKeys(value);
        Utils.explicitWait(driver,
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(@class,'subjects-auto-complete__option')]")), 5);
        subject.sendKeys(Keys.ENTER);
        logger.info("Subject setat: {}", value);
        takeScreenshot("After Set Subject");
    }

    public void setHobby(String value) {
        takeScreenshot("Before Set Hobby");
        WebElement hobbyLabel = driver.findElement(
                By.xpath("//*[@id='hobbiesWrapper']//label[text()='" + value + "']"));
        hobbyLabel.click();
        logger.info("Hobby setat: {}", value);
        takeScreenshot("After Set Hobby");
    }

    public void setState(String value) {
        takeScreenshot("Before Set State");
        scrollToElement(state);
        Utils.explicitWait(driver, ExpectedConditions.elementToBeClickable(state), 10);
        state.click();
        WebElement option = Utils.fluentWait(driver,
                By.xpath("//div[contains(@class,'option') and text()='" + value + "']"));
        option.click();
        logger.info("State setat: {}", value);
        takeScreenshot("After Set State");
    }

    public void setCity(String value) {
        takeScreenshot("Before Set City");
        Utils.explicitWait(driver, ExpectedConditions.elementToBeClickable(city), 10);
        city.click();
        WebElement option = Utils.fluentWait(driver,
                By.xpath("//div[contains(@class,'option') and text()='" + value + "']"));
        option.click();
        logger.info("City setat: {}", value);
        takeScreenshot("After Set City");
    }

    public void clickSubmit() {
        takeScreenshot("Before Click Submit");
        scrollToElement(submit);
        Utils.explicitWait(driver, ExpectedConditions.elementToBeClickable(submit), 10);
        js.executeScript("arguments[0].click();", submit);
        logger.info("Submit apasat");
        takeScreenshot("After Click Submit");
    }

    public String getFinalData(String label) {
        String xpath = "//table//*[text()='" + label + "']/../*[2]";
        Utils.explicitWait(driver,
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)), 15);
        return driver.findElement(By.xpath(xpath)).getText();
    }

    // ── Utilitare ─────────────────────────────────────────────────────────────

    public void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }


    public void closeAdverts() {
        String[] selectors = {
                "#adplus-anchor",
                "footer",
                "#ad_iframe",
                ".adsbygoogle"
        };
        for (String selector : selectors) {
            try {
                js.executeScript(
                        "var el = document.querySelector('" + selector + "');" +
                                "if(el) el.parentNode.removeChild(el);");
            } catch (Exception ignored) {}
        }
    }
    @Deprecated
    public void pause(int ms) throws InterruptedException {
        Thread.sleep(ms);
    }
}
