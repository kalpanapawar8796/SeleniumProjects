package Test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import Pages.OrageHRMLogoutScreen;

public class OrangeHRMTestLogout {

	WebDriver driver = null;

	ExtentHtmlReporter htmlReporter = null;
	ExtentReports extent = null;

	ExtentTest test = null;
	String ExpectedLoggedInURL = null;
	
	public OrangeHRMTestLogout(WebDriver driver,ExtentHtmlReporter htmlReporter,ExtentReports extent,ExtentTest test ) throws InterruptedException{
		
		this.driver = driver;
		
		this.htmlReporter = htmlReporter;
		
		this.extent = extent;
		
		this.test = test;

		//test = extent.createTest("Test Logout", "TestLogoutFunctionality");
		
		test.log(Status.INFO,"Logout Test Functionality");
		
		OrageHRMLogoutScreen logoutScreen = new OrageHRMLogoutScreen(driver);
		
		logoutScreen.ClickOnLogoutLink();

		test.log(Status.INFO,"Clicked on logout link");

		test.pass("Logged out successfully");

	}
	
}
