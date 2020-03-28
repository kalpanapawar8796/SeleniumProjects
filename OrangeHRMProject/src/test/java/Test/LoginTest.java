package Test;


import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
//import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Pages.DashboardPage;
import Pages.LoginPage;
import Utilities.DataLayer;

public class LoginTest {
	
	WebDriver driver = null;
	private static Logger log = LogManager.getLogger("devpinoyLogger");
	String CurrentProjectPath = System.getProperty("user.dir");
	
	@DataProvider(name = "logindata")
	public Object[][] getLoginDetails() throws IOException{
		
		DataLayer dl = new DataLayer();
		Object[][] data = dl.realExcel(CurrentProjectPath+"/Excel/OrangeHRM.xlsx", "Login");
		return data;
		
	}
	
	@BeforeClass
	public void setBrowesr(){
		
		System.setProperty("webdriver.chrome.driver", CurrentProjectPath+"/Drivers/ChromeDriver/chromedriver_3.exe");
		driver = new ChromeDriver();
		String log4jConfPath = "C:\\Users\\Win\\Desktop\\Seleniummaterial27thNov\\OrangeHRMProject\\src\\main\\java\\log4j2.properties";
		PropertyConfigurator.configure(log4jConfPath);
		driver.get("https://opensource-demo.orangehrmlive.com/");
		log.debug("opening webiste");
	}
	
	@Test(priority = 1, dataProvider = "logindata")
	public void Login(String Username, String Password){
		LoginPage loginWebElement = new LoginPage(driver);
		loginWebElement.EnterUsername(Username);
		log.debug("Entered username as"+Username);
		loginWebElement.EnterPassword(Password);
		log.debug("Entered password as"+Password);
		loginWebElement.ClickOnLoginButton();
		log.debug("Clicked on Login button");

		DashboardPage dashboard = new DashboardPage(driver);
		assertEquals(dashboard.getUsername(), "Welcome "+Username);
		log.debug("User "+Username+" logged in successfully");
		
	}
	
	//--------------------------------------Code For Taking Screenshot if script fails-----------------------------------------------//
		@AfterMethod
		public void tearDown(ITestResult result)
		{

			System.out.println("ITestResult result : "+result);
			System.out.println("result.getStatus() : "+result.getStatus());
			System.out.println("result.getName() : "+result.getName());
			// Here will compare if test is failing then only it will enter into if condition
			if(ITestResult.FAILURE==result.getStatus())
			{
				System.out.println(result.getName() +"  : Failure");
				try 
				{
					// Create reference of TakesScreenshot
					TakesScreenshot ts=(TakesScreenshot)driver;

					// Call method to capture screenshot
					File source=ts.getScreenshotAs(OutputType.FILE);

					// Copy files to specific location here it will save all screenshot in our project home directory and
					// result.getName() will return name of test case so that screenshot name will be same
				/*	FileUtils.copyFile(source, new File("./Screenshots/"+result.getName()+".png"));

					System.out.println("Screenshot taken");*/
				} 
				catch (Exception e)
				{

					System.out.println("Exception while taking screenshot "+e.getMessage());
				}
			}else{
				
				System.out.println(result.getName() +" : Success");
			} 

		}

			@AfterClass
			public void closeBrowser(){

				driver.close();
				driver.quit();
				
			}

		}
