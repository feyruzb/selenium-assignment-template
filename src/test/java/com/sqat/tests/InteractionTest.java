package com.sqat.tests;

import com.sqat.pages.AccountPage;
import com.sqat.pages.AddressPage;
import com.sqat.pages.ContactPage;
import com.sqat.pages.HomePage;
import com.sqat.pages.SearchResultsPage;
import com.sqat.util.RandomData;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InteractionTest extends BaseTest {

    @Test
    public void shouldSearchAndFindMatchingProducts() {
        SearchResultsPage results = new HomePage(driver).open().searchFor("mac");
        assertTrue("results heading should reflect query",
                results.headingText().toLowerCase().contains("mac"));
        assertTrue("url should land on product/search route",
                driver.getCurrentUrl().contains("route=product/search"));
    }

    @Test
    public void shouldRepeatSearchesForMultipleProductTerms() {
        HomePage home = new HomePage(driver);
        for (String term : List.of("mac", "phone", "ipod")) {
            SearchResultsPage results = home.open().searchFor(term);
            assertTrue("heading should contain '" + term + "'",
                    results.headingText().toLowerCase().contains(term));
        }
    }

    @Test
    public void shouldFillContactEnquiryTextareaAndReadItBack() {
        ContactPage contact = new ContactPage(driver).open();
        String message = RandomData.phrase() + ". " + RandomData.phrase() + ".";
        contact.setName(RandomData.firstName())
                .setEmail(RandomData.email())
                .setEnquiry(message);
        assertEquals("textarea should round-trip the typed message",
                message, contact.enquiryValue());
        assertTrue("page title should mention contact",
                contact.pageTitle().toLowerCase().contains("contact"));
    }

    @Test
    public void shouldPopulateCountryAndZoneDropdownsOnAddressForm() {
        registerAndLogInNewUser();
        AddressPage address = new AccountPage(driver).open().openAddNewAddress();

        List<String> countries = address.countryOptions();
        assertTrue("country dropdown should expose many options, got " + countries.size(),
                countries.size() > 50);
        assertTrue("United Kingdom should be available as a country option",
                countries.contains("United Kingdom"));

        address.selectCountry("United Kingdom");
        assertEquals("United Kingdom", address.selectedCountry());
    }

    @Test
    public void shouldSubmitAddressFormForLoggedInUser() {
        registerAndLogInNewUser();
        AddressPage address = new AccountPage(driver).open().openAddNewAddress();
        address.setFirstName(RandomData.firstName())
                .setLastName(RandomData.lastName())
                .setAddress1("221B " + RandomData.phrase())
                .setCity("London")
                .setPostcode("NW16XE")
                .selectCountry("United Kingdom")
                .selectFirstAvailableZone();
        address.save();
        String url = driver.getCurrentUrl();
        assertTrue("expected to land on address list after save, was: " + url,
                url.contains("route=account/address"));
        assertFalse("URL should no longer be on the .form page after submit, was: " + url,
                url.contains("route=account/address."));
    }
}
