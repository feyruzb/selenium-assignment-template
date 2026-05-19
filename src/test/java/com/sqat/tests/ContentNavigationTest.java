package com.sqat.tests;

import com.sqat.pages.ContactPage;
import com.sqat.pages.HomePage;
import com.sqat.pages.InformationPage;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ContentNavigationTest extends BaseTest {

    @Test
    public void shouldDisplayAboutUsPageContent() {
        InformationPage about = new InformationPage(driver).open(InformationPage.ABOUT_US);
        assertEquals("About Us", about.headingText());
        assertFalse("about-us body should not be empty", about.bodyText().isEmpty());
        assertTrue("page title should mention About Us",
                about.pageTitle().toLowerCase().contains("about"));
    }

    @Test
    public void shouldLoopThroughAllFooterInformationPages() {
        InformationPage page = new InformationPage(driver);
        List<Integer> ids = List.of(
                InformationPage.ABOUT_US,
                InformationPage.TERMS,
                InformationPage.PRIVACY,
                InformationPage.DELIVERY);

        for (int id : ids) {
            page.open(id);
            assertFalse("heading should render for info_id=" + id,
                    page.headingText().isEmpty());
            assertFalse("body text should render for info_id=" + id,
                    page.bodyText().isEmpty());
            assertTrue("url should reflect info_id=" + id,
                    driver.getCurrentUrl().contains("information_id=" + id));
        }
    }

    @Test
    public void shouldNavigateToContactPageFromFooter() {
        ContactPage contact = new HomePage(driver).open().openContact();
        assertTrue("expected to land on /information/contact",
                driver.getCurrentUrl().contains("information/contact"));
        assertEquals("Contact Us", contact.headingText());
    }

    @Test
    public void shouldDisplayContactPageStoreLocationBlock() {
        ContactPage contact = new ContactPage(driver).open();
        assertTrue("location section should have an 'Our Locations' heading",
                contact.locationHeading().toLowerCase().contains("our location"));
        String details = contact.locationText();
        assertTrue("location block should contain a telephone label, got: " + details,
                details.toLowerCase().contains("telephone"));
        assertTrue("location block should expose non-trivial content",
                details.length() > 20);
    }

    @Test
    public void shouldScrollToFooterUsingJavaScript() {
        new HomePage(driver).open();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement footer = driver.findElement(By.tagName("footer"));
        long footerTopBefore = ((Number) js.executeScript(
                "return arguments[0].getBoundingClientRect().top;", footer)).longValue();
        assertTrue("footer should start below the fold, was at viewport-top=" + footerTopBefore,
                footerTopBefore > 200);
        js.executeScript(
                "arguments[0].scrollIntoView({block:'start', behavior:'instant'});", footer);
        new org.openqa.selenium.support.ui.WebDriverWait(driver,
                java.time.Duration.ofSeconds(5))
                .until(d -> {
                    long top = ((Number) ((JavascriptExecutor) d).executeScript(
                            "return arguments[0].getBoundingClientRect().top;", footer))
                            .longValue();
                    return top < footerTopBefore;
                });
        long footerTopAfter = ((Number) js.executeScript(
                "return arguments[0].getBoundingClientRect().top;", footer)).longValue();
        assertTrue("scrollIntoView should bring footer into view, before=" + footerTopBefore
                        + " after=" + footerTopAfter,
                footerTopAfter < footerTopBefore);
    }
}
