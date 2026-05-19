package com.sqat.tests;

import com.sqat.config.TestConfig;
import com.sqat.pages.LoginPage;
import com.sqat.pages.RegisterPage;
import com.sqat.util.Credentials;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public abstract class BaseTest {

    protected WebDriver driver;

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--remote-allow-origins=*");
        if (TestConfig.headless()) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(TestConfig.pageLoadTimeout());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected Credentials registerAndLogInNewUser() {
        Credentials creds = Credentials.random();
        new RegisterPage(driver).open().register(creds);
        new LoginPage(driver).open().login(creds.email(), creds.password());
        return creds;
    }
}
