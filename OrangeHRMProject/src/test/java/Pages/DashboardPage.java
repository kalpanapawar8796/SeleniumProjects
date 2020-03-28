package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class DashboardPage {
	WebDriver driver;
	Actions action;
	public DashboardPage(WebDriver driver){
		this.driver = driver;
	}
	
	By LoggedInUsername = By.linkText("Welcome Admin");
	By AdminMenu = By.id("menu_admin_viewAdminModule");
	By NationalitiesSubmenu = By.id("menu_admin_nationality");
	
	public String getUsername(){
	
		String username = driver.findElement(LoggedInUsername).getText();
		return username;
	}
	
	public void mouseHoverOverOnAdminMenu(){
		action = new Actions(driver);
		action.moveToElement(driver.findElement(AdminMenu)).build().perform();
		
	}
	
	public void clickOnNationalitiesSubmenu(){
		driver.findElement(NationalitiesSubmenu).click();
	}
	
}
