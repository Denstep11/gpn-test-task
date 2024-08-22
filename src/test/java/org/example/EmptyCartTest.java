package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmptyCartTest {
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

    //@Timeout(12)
    @ParameterizedTest
    @ValueSource(strings = {"standard_user",
            "locked_out_user",
            "problem_user",
            "performance_glitch_user",
            "error_user",
            "visual_user"})
    public void RemoveFromCart(String user) {
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
        List<WebElement> productsInCart = driver.findElements(By.className("cart_item"));
        for (WebElement element : productsInCart) {
            WebElement button = element.findElement(By.tagName("button"));
            button.click(); // remove
            assertFalse(element.isDisplayed());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"standard_user",
            "locked_out_user",
            "problem_user",
            "performance_glitch_user",
            "error_user",
            "visual_user"})
    public void RemoveFromMain(String user) {
        driver.findElement(By.xpath("//*[@id=\"user-name\"]"))
                .sendKeys(user, Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"password\"]"))
                .sendKeys("secret_sauce", Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();

        List<WebElement> products = driver.findElements(By.xpath("/html/body/div/div/div/div[2]/div/div/div/div"));
        for (WebElement element : products) {
            WebElement button = element.findElement(By.tagName("button"));
            button.click(); // add
            button = element.findElement(By.tagName("button"));
            button.click(); // remove
        }

        driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();
        assertThrows(NoSuchElementException.class, () ->  driver.findElement(By.className("cart_item")));
    }
}