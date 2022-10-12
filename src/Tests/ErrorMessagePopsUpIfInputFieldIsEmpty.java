package Tests;

import Pages.LoadTheWebsite;
import Pages.LogInForm;
import Pages.SalesForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class ErrorMessagePopsUpIfInputFieldIsEmpty {
    WebDriver driver;
    String currentURL = "";
    Duration timeout = Duration.ofSeconds(3);

    @BeforeTest
    public void OpenTheWebsite(){
        driver = new ChromeDriver();
        new LoadTheWebsite().LoadTheWebsite(driver);
    }

    @Test(priority = 1)
    public void checkIfTheWebsiteIsCorrect(){
        currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, "https://robotsparebinindustries.com/#/");
    }

    @Test(priority = 2)
    public void logInToTheWebsite(){
        LogInForm logInForm = new LogInForm(driver);
        logInForm.enterCredentialsToLogInAndClickLogInButton();
        new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("sales-form")));
        Boolean isVisible;
        try{
            isVisible = driver.findElement(By.id("sales-form")).isDisplayed();
        }
        catch(Exception e){
            isVisible = false;
        }
        Assert.assertEquals(isVisible, true);
    }

    @Test(priority = 3)
    public void checkIfErrorMessagesPopsUpIfInputFieldIsEmpty(){
        SalesForm salesForm = new SalesForm(driver);
        salesForm.doASaleWithAnEmptyInputField();
        Boolean isErrorMessageDisplayed;
        try{
            isErrorMessageDisplayed = driver.findElement(By.xpath("//*[text()='Please fill out this field.']")).isDisplayed();
        }
        catch (Exception e){
            isErrorMessageDisplayed = false;
        }
        Assert.assertEquals(isErrorMessageDisplayed, true);
    }

    @AfterTest
    public void closeTheWebsite() {driver.quit();}
}