package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrageHRMLogoutScreen {

	WebDriver driver = null;
	
	By userProfileLink = By.id("welcome");
	
	By logoutLink = By.xpath(".//a[contains(text(),'Logout')]");

	
	public OrageHRMLogoutScreen(WebDriver driver) {

		this.driver = driver;

	}

	public void ClickOnLogoutLink() throws InterruptedException {
		
		Thread.sleep(5000);
		
		driver.findElement(userProfileLink).click();
		
		Thread.sleep(3000);

		driver.findElement(logoutLink).click();
	}

}
