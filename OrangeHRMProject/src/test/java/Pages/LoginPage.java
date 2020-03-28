package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

	WebDriver driver;
	
	public LoginPage(WebDriver driver){
		this.driver = driver;
	}
	
	By UsernameTextBox = By.name("txtUsername");
	
	By PasswordTextBox = By.name("txtPassword");
	
	By LoginBtn = By.name("Submit");
	
	public void EnterUsername(String Username){
		driver.findElement(UsernameTextBox).clear();
		driver.findElement(UsernameTextBox).sendKeys(Username);	
	}
	
	public void EnterPassword(String Password){
		driver.findElement(PasswordTextBox).clear();
		driver.findElement(PasswordTextBox).sendKeys(Password);
	}
	
	public void ClickOnLoginButton(){
		driver.findElement(LoginBtn).click();
	}
	
}
