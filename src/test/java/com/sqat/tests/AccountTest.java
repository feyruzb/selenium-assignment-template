package com.sqat.tests;

import com.sqat.config.TestConfig;
import com.sqat.pages.AccountPage;
import com.sqat.pages.EditAccountPage;
import com.sqat.pages.LoginPage;
import com.sqat.pages.RegisterPage;
import com.sqat.util.Credentials;
import com.sqat.util.RandomData;
import org.junit.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AccountTest extends BaseTest {

    @Test
    public void shouldRegisterNewUserSuccessfully() {
        Credentials creds = Credentials.random();
        RegisterPage register = new RegisterPage(driver).open();
        register.register(creds);
        assertTrue("expected to land on /account/success after registration",
                driver.getCurrentUrl().contains("account/success"));
        assertTrue("page title should mention account creation",
                register.pageTitle().toLowerCase().contains("account"));
    }

    @Test
    public void shouldLogInAndManageSessionCookies() {
        Credentials creds = Credentials.random();
        new RegisterPage(driver).open().register(creds);
        AccountPage account = new LoginPage(driver).open()
                .login(creds.email(), creds.password());
        assertTrue("account page should be loaded after login", account.isLoaded());

        Set<Cookie> sessionCookies = driver.manage().getCookies();
        assertFalse("expected at least one cookie after login", sessionCookies.isEmpty());

        Cookie marker = new Cookie("sqat_marker", "abc123");
        driver.manage().addCookie(marker);
        assertEquals("abc123",
                driver.manage().getCookieNamed("sqat_marker").getValue());

        driver.manage().deleteCookieNamed("sqat_marker");
        assertNull("custom cookie should be gone after delete",
                driver.manage().getCookieNamed("sqat_marker"));
    }

    @Test
    public void shouldUpdateAccountFirstNameForLoggedInUser() {
        registerAndLogInNewUser();
        EditAccountPage edit = new AccountPage(driver).open().openEditAccount();
        String newName = "Renamed" + RandomData.firstName();
        edit.setFirstName(newName).save();

        String persisted = new EditAccountPage(driver).open().currentFirstName();
        assertEquals(newName, persisted);
    }

    @Test
    public void shouldNavigateBackAndForwardBetweenAccountPages() {
        registerAndLogInNewUser();
        new AccountPage(driver).open();
        String accountUrl = driver.getCurrentUrl();

        new AccountPage(driver).openEditAccount();
        String editUrl = driver.getCurrentUrl();
        assertNotEquals(accountUrl, editUrl);

        driver.navigate().back();
        waitUrl("route=account/account");

        driver.navigate().forward();
        waitUrl("route=account/edit");
    }

    @Test
    public void shouldLogOutAndClearSession() {
        registerAndLogInNewUser();
        AccountPage account = new AccountPage(driver).open();
        assertTrue("precondition: account page loaded", account.isLoaded());

        account.logout();
        assertTrue("expected to land on logout route",
                driver.getCurrentUrl().contains("route=account/logout"));

        driver.get(TestConfig.baseUrl() + "index.php?route=account/account");
        waitUrl("route=account/login");
    }

    private void waitUrl(String fragment) {
        new WebDriverWait(driver, TestConfig.explicitWait())
                .until(ExpectedConditions.urlContains(fragment));
    }
}
