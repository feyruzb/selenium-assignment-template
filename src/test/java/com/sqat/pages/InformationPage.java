package com.sqat.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InformationPage extends BasePage {

    public static final int ABOUT_US = 1;
    public static final int TERMS = 2;
    public static final int PRIVACY = 3;
    public static final int DELIVERY = 4;

    private static final By HEADING = By.cssSelector("#content h1");
    private static final By BODY = By.xpath(
            "//div[@id='content']//*[self::p or self::div][normalize-space(.)!='']");

    public InformationPage(WebDriver driver) {
        super(driver);
    }

    public InformationPage open(int informationId) {
        navigate("index.php?route=information/information&information_id=" + informationId);
        visible(HEADING);
        return this;
    }

    public String headingText() {
        return visible(HEADING).getText();
    }

    public String bodyText() {
        return visible(BODY).getText();
    }
}
