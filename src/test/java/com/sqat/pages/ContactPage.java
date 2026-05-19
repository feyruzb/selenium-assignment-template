package com.sqat.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ContactPage extends BasePage {

    private static final String PATH = "index.php?route=information/contact";

    private static final By NAME = By.id("input-name");
    private static final By EMAIL = By.id("input-email");
    private static final By ENQUIRY = By.id("input-enquiry");
    private static final By HEADING = By.cssSelector("#content h1");
    private static final By LOCATION_HEADING = By.xpath(
            "//div[@id='content']//h3[normalize-space()='Our Locations']");
    private static final By LOCATION_BLOCK = By.xpath(
            "//div[@id='content']//h3[normalize-space()='Our Locations']"
                    + "/following-sibling::div[contains(@class,'card')][1]");

    public ContactPage(WebDriver driver) {
        super(driver);
    }

    public ContactPage open() {
        navigate(PATH);
        visible(ENQUIRY);
        return this;
    }

    public ContactPage setName(String value) {
        type(NAME, value);
        return this;
    }

    public ContactPage setEmail(String value) {
        type(EMAIL, value);
        return this;
    }

    public ContactPage setEnquiry(String value) {
        type(ENQUIRY, value);
        return this;
    }

    public String enquiryValue() {
        return visible(ENQUIRY).getAttribute("value");
    }

    public String headingText() {
        return visible(HEADING).getText();
    }

    public String locationText() {
        return visible(LOCATION_BLOCK).getText();
    }

    public String locationHeading() {
        return visible(LOCATION_HEADING).getText();
    }
}
