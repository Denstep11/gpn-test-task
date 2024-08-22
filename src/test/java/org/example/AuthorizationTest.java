package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AuthorizationTest {
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

    @Timeout(5)
    @ParameterizedTest
    @ValueSource(strings = {"standard_user",
            "locked_out_user",
            "problem_user",
            "performance_glitch_user",
            "error_user",
            "visual_user"})
    public void Authorization(String user) {
        driver.findElement(By.xpath("//*[@id=\"user-name\"]"))
                .sendKeys(user, Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"password\"]"))
                .sendKeys("secret_sauce", Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
        assertEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());
    }

    @Test
    public void AuthorizationWithoutLogin() {
        driver.findElement(By.xpath("//*[@id=\"password\"]"))
                .sendKeys("secret_sauce", Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
        assertNotEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());
    }

    @ParameterizedTest
    @ValueSource(strings = {"standard_user",
            "locked_out_user",
            "problem_user",
            "performance_glitch_user",
            "error_user",
            "visual_user"})
    public void AuthorizationWithoutPassword(String user) {
        driver.findElement(By.xpath("//*[@id=\"user-name\"]"))
                .sendKeys(user, Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
        assertNotEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());
    }

    @Test
    public void AuthorizationWithoutLoginAndPassword() {
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
        assertNotEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());
    }

    @Test
    public void AuthorizationLockedOutUser() {
        driver.findElement(By.xpath("//*[@id=\"user-name\"]"))
                .sendKeys("locked_out_user", Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"password\"]"))
                .sendKeys("secret_sauce", Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
        assertEquals("Epic sadface: Sorry, this user has been locked out.",
                driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3")).getText());
    }
}