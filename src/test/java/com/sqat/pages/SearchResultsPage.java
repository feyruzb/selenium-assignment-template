package com.sqat.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchResultsPage extends BasePage {

    private static final By HEADING = By.cssSelector("#content h1");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public SearchResultsPage openWithQuery(String query) {
        navigate("index.php?route=product/search&search=" + query);
        visible(HEADING);
        return this;
    }

    public String headingText() {
        return visible(HEADING).getText();
    }
}
