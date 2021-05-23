package steps;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestGoogleSearch {
    String url;
    WebDriver driver;
    String product = "Ovos de Pascoa";

    @Before
    public void initialize(){
        url = "https://www.google.com/";

        System.setProperty("webdriver.chrome.driver", "drivers/chrome/chromedriver90.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(60000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void searchForOvoDePascoa() throws InterruptedException {
        driver.get(url);
        driver.findElement(By.name("q")).sendKeys(product, Keys.ENTER);

        WebDriverWait wait = new WebDriverWait(driver, 4);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a > h3")));

        // Get a list of results from Google search
        findExpectdPage();

        validateExpectedPage();
    }

    private void findExpectdPage() {
        List<WebElement> results = driver.findElements(By.cssSelector("a > h3"));
        for (int i=0; i<results.size(); i++)
        {
            if (results.get(i).getText().contains("My Sweet Canada"))
            {
                results.get(i).click();
                break;
            }
        }
    }

    private void validateExpectedPage(){
        driver.getTitle().contains("Onde encontrar ovos de páscoa em Toronto");
        assertEquals(driver.findElement(By.cssSelector("h1.post-title.entry-title")).getText(), "Onde encontrar ovos de páscoa em Toronto");
        assertTrue(driver.findElement(By.cssSelector("div.entry-content.entry.clearfix > h1:first-of-type")).getText().contains("ovos de chocolate"));
        assertTrue(driver.findElement(By.cssSelector(".the-subtitle")).getText().equals("About me"));
        assertTrue(driver.findElement(By.cssSelector(".aboutme-widget-content")).getText().contains("Carina"));
        assertTrue(driver.findElement(By.cssSelector(".aboutme-widget-content")).getText().contains("My Sweet Canada"));
    }

    @After
    public void quit(){ driver. quit(); }
}
