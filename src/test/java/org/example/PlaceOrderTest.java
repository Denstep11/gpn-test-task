package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaceOrderTest {
    WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }

    @ParameterizedTest
    @ValueSource(strings = {"standard_user",
            "locked_out_user",
            "problem_user",
            "performance_glitch_user",
            "error_user",
            "visual_user"})
    public void CorrectOrder(String user) {
        driver.findElement(By.xpath("//*[@id=\"user-name\"]"))
                .sendKeys(user, Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"password\"]"))
                .sendKeys("secret_sauce", Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();

        List<WebElement> products = driver.findElements(By.xpath("/html/body/div/div/div/div[2]/div/div/div/div"));
        for (WebElement element : products) {
            WebElement button = element.findElement(By.tagName("button"));
            button.click(); // add
        }

        driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"checkout\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"first-name\"]")).sendKeys("first_name");
        driver.findElement(By.xpath("//*[@id=\"last-name\"]")).sendKeys("last_name");
        driver.findElement(By.xpath("//*[@id=\"postal-code\"]")).sendKeys("123");
        driver.findElement(By.xpath("//*[@id=\"continue\"]")).click();
        assertEquals("https://www.saucedemo.com/checkout-step-two.html", driver.getCurrentUrl());
    }

    @ParameterizedTest
    @ValueSource(strings = {"standard_user",
            "locked_out_user",
            "problem_user",
            "performance_glitch_user",
            "error_user",
            "visual_user"})
    public void OrderWithoutFirstName(String user) {
        driver.findElement(By.xpath("//*[@id=\"user-name\"]"))
                .sendKeys(user, Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"password\"]"))
                .sendKeys("secret_sauce", Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();

        List<WebElement> products = driver.findElements(By.xpath("/html/body/div/div/div/div[2]/div/div/div/div"));
        for (WebElement element : products) {
            WebElement button = element.findElement(By.tagName("button"));
            button.click(); // add
        }

        driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"checkout\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"last-name\"]")).sendKeys("last_name");
        driver.findElement(By.xpath("//*[@id=\"postal-code\"]")).sendKeys("123");
        driver.findElement(By.xpath("//*[@id=\"continue\"]")).click();
        assertEquals("Error: First Name is required",
                driver.findElement(By.xpath("//*[@id=\"checkout_info_container\"]/div/form/div[1]/div[4]/h3")).getText());
    }

    @ParameterizedTest
    @ValueSource(strings = {"standard_user",
            "locked_out_user",
            "problem_user",
            "performance_glitch_user",
            "error_user",
            "visual_user"})
    public void OrderWithoutLastName(String user) {
        driver.findElement(By.xpath("//*[@id=\"user-name\"]"))
                .sendKeys(user, Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"password\"]"))
                .sendKeys("secret_sauce", Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();

        List<WebElement> products = driver.findElements(By.xpath("/html/body/div/div/div/div[2]/div/div/div/div"));
        for (WebElement element : products) {
            WebElement button = element.findElement(By.tagName("button"));
            button.click(); // add
        }

        driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"checkout\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"first-name\"]")).sendKeys("first_name");
        driver.findElement(By.xpath("//*[@id=\"postal-code\"]")).sendKeys("123");
        driver.findElement(By.xpath("//*[@id=\"continue\"]")).click();
        assertEquals("Error: Last Name is required",
                driver.findElement(By.xpath("//*[@id=\"checkout_info_container\"]/div/form/div[1]/div[4]/h3")).getText());
    }

    @ParameterizedTest
    @ValueSource(strings = {"standard_user",
            "locked_out_user",
            "problem_user",
            "performance_glitch_user",
            "error_user",
            "visual_user"})
    public void OrderWithoutPostalCode(String user) {
        driver.findElement(By.xpath("//*[@id=\"user-name\"]"))
                .sendKeys(user, Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"password\"]"))
                .sendKeys("secret_sauce", Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();

        List<WebElement> products = driver.findElements(By.xpath("/html/body/div/div/div/div[2]/div/div/div/div"));
        for (WebElement element : products) {
            WebElement button = element.findElement(By.tagName("button"));
            button.click(); // add
        }

        driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"checkout\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"first-name\"]")).sendKeys("first_name");
        driver.findElement(By.xpath("//*[@id=\"last-name\"]")).sendKeys("last_name");
        driver.findElement(By.xpath("//*[@id=\"continue\"]")).click();
        assertEquals("Error: Postal Code is required",
                driver.findElement(By.xpath("//*[@id=\"checkout_info_container\"]/div/form/div[1]/div[4]/h3")).getText());
    }
}
