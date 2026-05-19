package com.sqat.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private static final String PATH = "index.php?route=account/login";

    private static final By EMAIL = By.id("input-email");
    private static final By PASSWORD = By.id("input-password");
    private static final By SUBMIT =
            By.xpath("//form[@id='form-login']//button[@type='submit']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        navigate(PATH);
        visible(EMAIL);
        return this;
    }

    public AccountPage login(String email, String password) {
        type(EMAIL, email);
        type(PASSWORD, password);
        click(SUBMIT);
        waitForUrlContains("account/account");
        return new AccountPage(driver);
    }
}
