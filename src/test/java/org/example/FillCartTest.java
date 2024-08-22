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
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FillCartTest {
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
    public void EachProduct(String user) {
        driver.findElement(By.xpath("//*[@id=\"user-name\"]"))
                .sendKeys(user, Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"password\"]"))
                .sendKeys("secret_sauce", Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
        List<WebElement> products = driver.findElements(By.xpath("/html/body/div/div/div/div[2]/div/div/div/div"));
        if(products.isEmpty()){
            throw new NoSuchElementException();
        }

        HashMap<String, String> productsMap = new HashMap<>();
        for (WebElement element : products) {
            productsMap.put(element.findElement(By.className("inventory_item_name")).getText(),
                    element.findElement(By.tagName("button")).getAttribute("id"));
        }

        for(String productName : productsMap.keySet()){
            WebElement button = driver.findElement(By.id(productsMap.get(productName)));
            button.click(); // add
            driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();
            WebElement product = driver.findElement(By.className("inventory_item_name"));
            WebElement cart = driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a/span"));
            assertAll(
                    () -> assertEquals(productName, product.getText()),
                    () -> assertEquals(1, Integer.valueOf(cart.getText()))
            );
            driver.findElement(By.className("cart_item")).findElement(By.tagName("button")).click(); // remove
            driver.navigate().back();
        }
    }


    @ParameterizedTest
    @ValueSource(strings = {"standard_user",
            "locked_out_user",
            "problem_user",
            "performance_glitch_user",
            "error_user",
            "visual_user"})
    public void AllProducts(String user) {
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
        WebElement cart = driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a/span"));
        assertAll(
                () -> assertEquals(6, Integer.valueOf(cart.getText()))
        );
    }


}
