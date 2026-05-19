package com.sqat.pages;

import com.sqat.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    private static final By SEARCH_INPUT = By.cssSelector("#search input[name='search']");
    private static final By SEARCH_BUTTON = By.cssSelector("#search button");
    private static final By CONTACT_LINK =
            By.xpath("//footer//a[contains(@href, 'route=information/contact')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(TestConfig.baseUrl());
        return this;
    }

    public SearchResultsPage searchFor(String query) {
        type(SEARCH_INPUT, query);
        click(SEARCH_BUTTON);
        return new SearchResultsPage(driver);
    }

    public InformationPage openFooterInformation(int informationId) {
        By link = By.xpath(
                "//footer//a[contains(@href, 'information_id=" + informationId + "')]");
        WebElement el = visible(link);
        scrollIntoView(el);
        jsClick(el);
        return new InformationPage(driver);
    }

    public ContactPage openContact() {
        WebElement el = visible(CONTACT_LINK);
        scrollIntoView(el);
        jsClick(el);
        return new ContactPage(driver);
    }
}
