package com.andersenlab;
import com.andersenlab.utils.Waiters;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class AppTest {
    WebDriver driver;
    //Locators
    protected static final By MAIN_PAGE_USER_EMAIL_INPUT = By.xpath("//input[@id=\"user_email\"]");
    protected static final By MAIN_PAGE_SIGN_UP_BUTTON = By.xpath("//button[contains(text(), \"Sign up for GitHub\")]");
    protected static final By LOGIN_PAGE_USER_EMAIL_INPUT = By.xpath("//input[@id=\"email\"]");
    protected static final By CONTINUE_TO_PASSWORD_BUTTON = By.xpath("//button[@data-continue-to =\"password-container\"]");
    protected static final By EMAIL_ERROR_LOCATOR = By.xpath("//p[@id=\"email-err\"]/p");

    //Constant values
    protected static final String USER_EMAIL = "zaretskaya.nady@gmail.com";
    protected static final String USER_ERROR_TEXT = "Email is invalid or already taken";

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testSignUpWithAlreadyTakenEmail() {
        driver.get("https://github.com/");
        enterText(MAIN_PAGE_USER_EMAIL_INPUT, USER_EMAIL);
        clickButton(MAIN_PAGE_SIGN_UP_BUTTON);
        waitForElementVisible(LOGIN_PAGE_USER_EMAIL_INPUT);
        assertEquals(USER_EMAIL, getAttributeValue( LOGIN_PAGE_USER_EMAIL_INPUT, "value"));
        assertEquals(USER_ERROR_TEXT, getText(EMAIL_ERROR_LOCATOR));
        assertFalse(isButtonEnabled(CONTINUE_TO_PASSWORD_BUTTON));
    }

    //Helpers (если использовать pageObject эти методы будут вынесены в BasePage класс)
    protected void clickButton(By by) {
        waitForElementClickable(by);
        driver.findElement(by).click();
    }

    protected void enterText(By by, String text) {
        findElement(by).sendKeys(text);
    }

    protected WebElement findElement(By by) {
        return driver.findElement(by);
    }

    protected void waitForElementVisible(By by) {
        Waiters.waitForElementVisible(driver, by);
    }

    protected void waitForElementClickable(By by) {
        Waiters.waitForElementClickable(driver, by);
    }

    protected String getText(By by) {
        waitForElementVisible(by);
        return findElement(by).getText();
    }

    protected String getAttributeValue(By by, String attribute) {
        return findElement(by).getAttribute(attribute);
    }

    protected boolean isButtonEnabled(By by) {
       return driver.findElement(by).isEnabled();
    }

}






