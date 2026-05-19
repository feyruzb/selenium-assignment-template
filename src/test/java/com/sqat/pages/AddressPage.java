package com.sqat.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class AddressPage extends BasePage {

    private static final String PATH = "index.php?route=account/address.form";

    private static final By FIRSTNAME = By.id("input-firstname");
    private static final By LASTNAME = By.id("input-lastname");
    private static final By ADDRESS_1 = By.id("input-address-1");
    private static final By CITY = By.id("input-city");
    private static final By POSTCODE = By.id("input-postcode");
    private static final By COUNTRY = By.id("input-country");
    private static final By ZONE = By.id("input-zone");
    private static final By SUBMIT = By.xpath(
            "//div[@id='content']//form//button[@type='submit']");

    public AddressPage(WebDriver driver) {
        super(driver);
    }

    public AddressPage open() {
        navigateAuthenticated(PATH);
        visible(COUNTRY);
        return this;
    }

    public AddressPage setFirstName(String value) { type(FIRSTNAME, value); return this; }
    public AddressPage setLastName(String value)  { type(LASTNAME, value); return this; }
    public AddressPage setAddress1(String value)  { type(ADDRESS_1, value); return this; }
    public AddressPage setCity(String value)      { type(CITY, value); return this; }
    public AddressPage setPostcode(String value)  { type(POSTCODE, value); return this; }

    public AddressPage selectCountry(String visibleText) {
        wait.until(d -> {
            WebElement c = d.findElement(COUNTRY);
            return c.isDisplayed() && c.isEnabled();
        });
        List<WebElement> beforeZoneOpts = driver.findElements(
                By.cssSelector("#input-zone option"));
        int beforeCount = beforeZoneOpts.size();
        String beforeFirstReal = beforeCount > 1 ? beforeZoneOpts.get(1).getText() : "";

        new Select(visible(COUNTRY)).selectByVisibleText(visibleText);

        wait.until(d -> {
            WebElement zoneEl = d.findElement(ZONE);
            if (!zoneEl.isEnabled()) return false;
            List<WebElement> opts = d.findElements(By.cssSelector("#input-zone option"));
            if (opts.isEmpty()) return false;
            if (opts.size() != beforeCount) return true;
            return opts.size() > 1 && !opts.get(1).getText().equals(beforeFirstReal);
        });
        return this;
    }

    public List<String> countryOptions() {
        return new Select(visible(COUNTRY)).getOptions().stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();
    }

    public String selectedCountry() {
        return new Select(visible(COUNTRY)).getFirstSelectedOption().getText().trim();
    }

    public void save() {
        scrollIntoView(visible(SUBMIT));
        click(SUBMIT);
        wait.until(d -> {
            String u = d.getCurrentUrl();
            return u.contains("route=account/address")
                    && !u.contains("route=account/address.");
        });
    }

    public AddressPage selectFirstAvailableZone() {
        wait.until(d -> {
            WebElement z = d.findElement(ZONE);
            if (!z.isEnabled()) return false;
            for (WebElement o : z.findElements(By.tagName("option"))) {
                String t = o.getText().trim();
                if (!t.isEmpty() && !t.toLowerCase().contains("please select")) {
                    return true;
                }
            }
            return false;
        });
        Select zone = new Select(visible(ZONE));
        List<WebElement> options = zone.getOptions();
        for (int i = 0; i < options.size(); i++) {
            String text = options.get(i).getText().trim();
            if (!text.isEmpty() && !text.toLowerCase().contains("please select")) {
                zone.selectByIndex(i);
                return this;
            }
        }
        throw new IllegalStateException("No selectable zone option available");
    }
}
