package com.sqat.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountPage extends BasePage {

    private static final String PATH = "index.php?route=account/account";

    private static final By EDIT_ACCOUNT_LINK = By.xpath(
            "//a[contains(@href,'route=account/edit') and not(ancestor::nav)]");
    private static final By LOGOUT_LINK = By.xpath(
            "//a[contains(@href,'route=account/logout') and not(ancestor::nav)]");

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public AccountPage open() {
        navigateAuthenticated(PATH);
        visible(EDIT_ACCOUNT_LINK);
        return this;
    }

    public boolean isLoaded() {
        return driver.getCurrentUrl().contains("route=account/account")
                && !driver.findElements(EDIT_ACCOUNT_LINK).isEmpty();
    }

    public EditAccountPage openEditAccount() {
        scrollIntoView(visible(EDIT_ACCOUNT_LINK));
        click(EDIT_ACCOUNT_LINK);
        return new EditAccountPage(driver);
    }

    public AddressPage openAddNewAddress() {
        navigateAuthenticated("index.php?route=account/address.form");
        return new AddressPage(driver);
    }

    public void logout() {
        scrollIntoView(visible(LOGOUT_LINK));
        click(LOGOUT_LINK);
        waitForUrlContains("route=account/logout");
    }
}
