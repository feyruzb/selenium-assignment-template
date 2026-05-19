package com.sqat.pages;

import com.sqat.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TestConfig.explicitWait());
    }

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        clickable(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement el = visible(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected void check(By locator, boolean shouldBeChecked) {
        WebElement el = clickable(locator);
        if (el.isSelected() != shouldBeChecked) {
            el.click();
        }
    }

    protected void scrollIntoView(WebElement el) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", el);
    }

    protected void jsClick(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    protected void waitForUrlContains(String fragment) {
        wait.until(ExpectedConditions.urlContains(fragment));
    }

    public String pageTitle() {
        return driver.getTitle();
    }

    public void navigate(String relativePath) {
        String base = TestConfig.baseUrl();
        if (base.endsWith("/") && relativePath.startsWith("/")) {
            relativePath = relativePath.substring(1);
        }
        driver.get(base + relativePath);
    }

    public void navigateAuthenticated(String relativePath) {
        String token = extractCustomerToken(driver.getCurrentUrl());
        String base = TestConfig.baseUrl();
        if (base.endsWith("/") && relativePath.startsWith("/")) {
            relativePath = relativePath.substring(1);
        }
        String url = base + relativePath;
        if (token != null) {
            url += (relativePath.contains("?") ? "&" : "?") + "customer_token=" + token;
        }
        driver.get(url);
    }

    private static String extractCustomerToken(String url) {
        int idx = url.indexOf("customer_token=");
        if (idx < 0) return null;
        int start = idx + "customer_token=".length();
        int end = url.indexOf('&', start);
        return end > 0 ? url.substring(start, end) : url.substring(start);
    }
}
