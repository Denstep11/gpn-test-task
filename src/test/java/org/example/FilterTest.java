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

import java.nio.file.WatchEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FilterTest {
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
    public void NameAZ(String user) {
        driver.findElement(By.xpath("//*[@id=\"user-name\"]"))
                .sendKeys(user, Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"password\"]"))
                .sendKeys("secret_sauce", Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select")).click();
        driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select/option[1]")).click();
        List<WebElement> products = driver.findElements(By.className("inventory_item_description"));
        String[] productName = new String[products.size()];
        for (int i = 0; i < products.size(); i++) {
            productName[i] = products.get(i).getText();
        }
        Arrays.sort(productName);
        for (int i = 0; i < products.size(); i++) {
            assertEquals(productName[i], products.get(i).getText());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"standard_user",
            "locked_out_user",
            "problem_user",
            "performance_glitch_user",
            "error_user",
            "visual_user"})
    public void NameZA(String user) {
        driver.findElement(By.xpath("//*[@id=\"user-name\"]"))
                .sendKeys(user, Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"password\"]"))
                .sendKeys("secret_sauce", Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select")).click();
        driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select/option[2]")).click();
        List<WebElement> products = driver.findElements(By.className("inventory_item_label"));
        String[] productName = new String[products.size()];
        for (int i = 0; i < products.size(); i++) {
            productName[i] = products.get(i).getText();
        }
        Arrays.sort(productName, Collections.reverseOrder());
        for (int i = 0; i < products.size(); i++) {
            assertEquals(productName[i], products.get(i).getText());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"standard_user",
            "locked_out_user",
            "problem_user",
            "performance_glitch_user",
            "error_user",
            "visual_user"})
    public void PriceLowToHigh(String user) {
        driver.findElement(By.xpath("//*[@id=\"user-name\"]"))
                .sendKeys(user, Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"password\"]"))
                .sendKeys("secret_sauce", Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select")).click();
        driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select/option[3]")).click();
        List<WebElement> products = driver.findElements(By.className("inventory_item_price"));
        Double[] productPrice = new Double[products.size()];
        for (int i = 0; i < products.size(); i++) {
            productPrice[i] = Double.valueOf(products.get(i).getText().replace("$",""));
        }
        Arrays.sort(productPrice);
        for (int i = 0; i < products.size(); i++) {
            assertEquals(productPrice[i].toString(), products.get(i).getText().replace("$",""));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"standard_user",
            "locked_out_user",
            "problem_user",
            "performance_glitch_user",
            "error_user",
            "visual_user"})
    public void PriceHighToLow(String user) {
        driver.findElement(By.xpath("//*[@id=\"user-name\"]"))
                .sendKeys(user, Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"password\"]"))
                .sendKeys("secret_sauce", Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select")).click();
        driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select/option[4]")).click();
        List<WebElement> products = driver.findElements(By.className("inventory_item_price"));
        Double[] productPrice = new Double[products.size()];
        for (int i = 0; i < products.size(); i++) {
            productPrice[i] = Double.valueOf(products.get(i).getText().replace("$",""));
        }
        Arrays.sort(productPrice, Collections.reverseOrder());
        for (int i = 0; i < products.size(); i++) {
            assertEquals(productPrice[i].toString(), products.get(i).getText().replace("$",""));
        }
    }
}
