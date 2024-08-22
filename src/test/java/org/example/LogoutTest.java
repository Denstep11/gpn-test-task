package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogoutTest {
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
    public void Logout(String user) {
        driver.findElement(By.xpath("//*[@id=\"user-name\"]"))
                .sendKeys(user, Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"password\"]"))
                .sendKeys("secret_sauce", Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"react-burger-menu-btn\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"logout_sidebar_link\"]")).click();
        assertEquals("https://www.saucedemo.com/", driver.getCurrentUrl());
    }
}
