package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class OrageHRMLoginScreen {

	WebDriver driver = null;
	By usernameTextbox = By.id("txtUsername");
	By passwordTextbox = By.id("txtPassword");
	By loginButton = By.id("btnLogin");
	By logoutLink = By.xpath(".//a[contains(text(),'Logout')]");
	
	public OrageHRMLoginScreen(WebDriver driver){
		
		this.driver = driver;
		
	}
	public void EnterUsername(String username){
		
		driver.findElement(usernameTextbox).click();		
		driver.findElement(usernameTextbox).sendKeys(username);
	}
	
	public void EnterPassword(String password){
		
		driver.findElement(passwordTextbox).click();		
		driver.findElement(passwordTextbox).sendKeys(password);
	}
	
	public void ClickOnLoginButton(){
		
		driver.findElement(loginButton).click();
	}
	
	
}
