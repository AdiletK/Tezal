package kg.tezal.tezal_back.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Login {

    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "/home/adik/Desktop/Folders/TezAl/tezal/src/test/java/kg/tezal/tezal_back/driver/geckodriver");
        WebDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver,10);
        try {
            driver.get("https://tezal.herokuapp.com/login");
            driver.findElement(By.name("username")).sendKeys("admin");
            driver.findElement(By.name("password")).sendKeys("123456"+ Keys.ENTER);
//            WebElement firstResult = wait.until(presenceOfElementLocated(By.className("text-brand")));
//            System.out.println(firstResult.getText());
            driver.get("https://tezal.herokuapp.com/supplier/list");
            driver.findElement(By.className("btn")).sendKeys(Keys.ENTER);
            driver.get("https://tezal.herokuapp.com/supplier/form");
            driver.findElement(By.name("name")).sendKeys("Meizu");
            driver.findElement(By.className("btn")).sendKeys(Keys.ENTER);
            driver.get("https://tezal.herokuapp.com/supplier/list");

        } finally {
            driver.quit();
        }
    }
}