package org.example.pom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.utils.Utils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object Model pentru demoqa.com/automation-practice-form
 */
public class FormPom {

    private static final Logger logger = LogManager.getLogger(FormPom.class);
    private final WebDriver driver;
    private final JavascriptExecutor js;

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
        closeAdverts();
        scrollToElement(form);
        Utils.explicitWait(driver, ExpectedConditions.elementToBeClickable(form), 10);
        js.executeScript("arguments[0].click();", form);
        logger.info("Click pe 'Forms'");
    }

    public void clickPracticeForm() {
        Utils.explicitWait(driver, ExpectedConditions.elementToBeClickable(practiceForm), 10);
        practiceForm.click();
        Utils.explicitWait(driver, ExpectedConditions.visibilityOf(firstName), 10);
        logger.info("Pagina Practice Form incarcata");
    }

    // ── Campuri formular ──────────────────────────────────────────────────────

    public void setFirstName(String value) {
        Utils.explicitWait(driver, ExpectedConditions.visibilityOf(firstName), 10);
        firstName.clear();
        firstName.sendKeys(value);
    }

    public void setLastName(String value) {
        lastName.clear();
        lastName.sendKeys(value);
    }

    public void setUserEmail(String value) {
        userEmail.clear();
        userEmail.sendKeys(value);
    }

    public void setPhone(String value) {
        Utils.explicitWait(driver, ExpectedConditions.visibilityOf(userPhone), 10);
        userPhone.clear();
        userPhone.sendKeys(value);
    }

    public void setGender(String value) {
        WebElement genderLabel = driver.findElement(
                By.xpath("//*[@id='genterWrapper']//label[text()='" + value + "']"));
        js.executeScript("arguments[0].click();", genderLabel);
        logger.info("Gender setat: {}", value);
    }

    public void setDOB(String value) {
        Utils.explicitWait(driver, ExpectedConditions.elementToBeClickable(DOB), 10);
        DOB.click();
        DOB.sendKeys(Keys.CONTROL + "a");
        DOB.sendKeys(value);
        DOB.sendKeys(Keys.ENTER);
        logger.info("DOB setat: {}", value);
    }

    public void setSubject(String value) {
        Utils.explicitWait(driver, ExpectedConditions.elementToBeClickable(subject), 10);
        subject.clear();
        subject.sendKeys(value);
        Utils.explicitWait(driver,
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(@class,'subjects-auto-complete__option')]")), 5);
        subject.sendKeys(Keys.ENTER);
        logger.info("Subject setat: {}", value);
    }

    public void setHobby(String value) {
        WebElement hobbyLabel = driver.findElement(
                By.xpath("//*[@id='hobbiesWrapper']//label[text()='" + value + "']"));
        hobbyLabel.click();
        logger.info("Hobby setat: {}", value);
    }

    public void setState(String value) {
        scrollToElement(state);
        Utils.explicitWait(driver, ExpectedConditions.elementToBeClickable(state), 10);
        state.click();
        WebElement option = Utils.fluentWait(driver,
                By.xpath("//div[contains(@class,'option') and text()='" + value + "']"));
        option.click();
        logger.info("State setat: {}", value);
    }

    public void setCity(String value) {
        Utils.explicitWait(driver, ExpectedConditions.elementToBeClickable(city), 10);
        city.click();
        WebElement option = Utils.fluentWait(driver,
                By.xpath("//div[contains(@class,'option') and text()='" + value + "']"));
        option.click();
        logger.info("City setat: {}", value);
    }

    public void clickSubmit() {
        scrollToElement(submit);
        Utils.explicitWait(driver, ExpectedConditions.elementToBeClickable(submit), 10);
        js.executeScript("arguments[0].click();", submit);
        logger.info("Submit apasat");
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

    /**
     * Elimina reclame / popup-uri care blocheaza interactiunile.
     */
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

    /** @deprecated Foloseste explicit/fluent wait in locul Thread.sleep */
    @Deprecated
    public void pause(int ms) throws InterruptedException {
        Thread.sleep(ms);
    }
}
