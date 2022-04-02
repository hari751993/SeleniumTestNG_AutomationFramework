package com.basic.actions;

import com.basic.constants.CommonVariables;
import com.basic.driver.DriverFactory;
import com.google.common.base.Strings;;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.sql.Time;
import java.time.Duration;
import java.util.InputMismatchException;
import java.util.concurrent.TimeUnit;

public class PreDefinedActions {
    private static final Logger LOGGER = LogManager.getLogger(PreDefinedActions.class);
    public static WebDriverWait wait = new WebDriverWait(DriverFactory.driver, Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
    public By getPageObject(String locator) {
        LOGGER.info("Starting method getPageObject");
        try {
            String locatorType = getLocatorType(locator);
            String locatorVal = getLocatorValue(locator);

            switch (locatorType.toUpperCase()) {
                // Find by id
                case "ID":
                    return By.id(locatorVal);
                // Find by xpath
                case "XPATH":
                    return By.xpath(locatorVal);
                // Find by Classname
                case "CLASSNAME":
                    return By.className(locatorVal);

                // Find by Name
                case "NAME":
                    return By.name(locatorVal);

                // Find by CSS
                case "CSS":
                    return By.cssSelector(locatorVal);

                // Find by Link
                case "LINK":
                    return By.linkText(locatorVal);

                // Find by PartialLink
                case "PARTIALLINK":
                    return By.partialLinkText(locatorVal);

                default:
                    throw new InputMismatchException("You can use : ID, XPATH, CLASSNAME, NAME, CSS, LINK, PARTIALLINK only ");
            }
        } catch (Exception e) {
            return null;
        }
    }

    private static String getLocatorType(String locator){
        String locatorType = null;
        if(!Strings.isNullOrEmpty(locator)) {
            locatorType = locator.split(":")[0].trim();
        }else{
            LOGGER.error("Locator is missing in properties file !");
        }
        return locatorType;
    }

    private static String getLocatorValue(String locator){

        String locatorValue = null;
        if(!Strings.isNullOrEmpty(locator)) {
            locatorValue = locator.split(":")[1].trim();
        }else{
            LOGGER.error("Locator is missing in properties file !");
        }
        return locatorValue;
    }

    public WebElement getPageElement(String locator){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(getPageObject(locator)));
        return element;
    }

    public void clickElement(String locator){
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(getPageObject(locator)));
            element.click();
        }catch (Exception e) {
            LOGGER.info("Element click failed ! " + e);
        }
    }
}