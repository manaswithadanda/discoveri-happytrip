package testHappyTrip;

import static org.testng.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestLogin {
	
	WebDriver driver;
	String chromeDriverPath = "./src/test/resources/drivers/chromedriver.exe";
	String URL = "http://172.30.15.99:8085/HappyTrip";
	WebDriverWait wait;
	
	@Test
	public void testLogin() {
		
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(URL);

		WebElement ele = driver.findElement(By.id("button_flight_search"));
		ele.click();
		
		assertTrue(driver.findElement(By.xpath("//*[@id='errorContainer']/li[2]")).getText().contains("Loca tion is emp ty"));
		driver.quit();
	}
}
