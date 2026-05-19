package com.sqat.pages;

import com.sqat.util.Credentials;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {

    private static final String PATH = "index.php?route=account/register";

    private static final By FIRSTNAME = By.id("input-firstname");
    private static final By LASTNAME = By.id("input-lastname");
    private static final By EMAIL = By.id("input-email");
    private static final By PASSWORD = By.id("input-password");
    private static final By NEWSLETTER = By.id("input-newsletter");
    private static final By AGREE = By.cssSelector("input[type='checkbox'][name='agree']");
    private static final By SUBMIT =
            By.xpath("//form[@id='form-register']//button[@type='submit']");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public RegisterPage open() {
        navigate(PATH);
        visible(FIRSTNAME);
        return this;
    }

    public void register(Credentials creds) {
        type(FIRSTNAME, creds.firstName());
        type(LASTNAME, creds.lastName());
        type(EMAIL, creds.email());
        type(PASSWORD, creds.password());
        check(NEWSLETTER, true);
        check(AGREE, true);
        scrollIntoView(visible(SUBMIT));
        click(SUBMIT);
        waitForUrlContains("account/success");
    }
}
