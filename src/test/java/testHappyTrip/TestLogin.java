package testHappyTrip;

import static org.testng.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestLogin {
	
	private WebDriver driver;
	private String chromeDriverPath = "./src/test/resources/drivers/chromedriver.exe";
	private String URL = "http://localhost:8085/HappyTrip";
	private WebDriverWait wait; 
	
	@BeforeTest
	public void setup() {
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(URL);
		wait = new WebDriverWait (driver, 20);
	}
	
	@Test
	public void testSearchFlightwithNoData() {
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("button_flight_search")))).click();
		assertTrue(driver.findElement(By.xpath("//*[@id='errorContainer']/li[2]")).getText().contains("Loc ation is empty"));
	}
	
	@Test
	public void testSearchFlightwithData() {
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("button_flight_search")))).click();
		assertTrue(driver.findElement(By.xpath("//*[@id='errorContainer']/li[2]")).getText().contains("Loca tion is empty"));
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
