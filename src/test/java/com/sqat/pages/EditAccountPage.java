package com.sqat.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EditAccountPage extends BasePage {

    private static final String PATH = "index.php?route=account/edit";

    private static final By FIRSTNAME = By.id("input-firstname");
    private static final By SUBMIT = By.xpath(
            "//div[@id='content']//form//button[@type='submit']");

    public EditAccountPage(WebDriver driver) {
        super(driver);
    }

    public EditAccountPage open() {
        navigateAuthenticated(PATH);
        visible(FIRSTNAME);
        return this;
    }

    public EditAccountPage setFirstName(String value) {
        type(FIRSTNAME, value);
        return this;
    }

    public String currentFirstName() {
        return visible(FIRSTNAME).getAttribute("value");
    }

    public AccountPage save() {
        scrollIntoView(visible(SUBMIT));
        click(SUBMIT);
        waitForUrlContains("route=account/account");
        return new AccountPage(driver);
    }
}
