package Test;

import static org.testng.Assert.assertNotEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.xmlbeans.impl.schema.SoapEncSchemaTypeSystem;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import Pages.OrageHRMLoginScreen;
import Pages.OrageHRMLogoutScreen;
import Utils.DataLayer;
public class OrangeHRMTestLogin {
	
	WebDriver driver = null;

	ExtentHtmlReporter htmlReporter = null;
	ExtentReports extent = null;

	ExtentTest test = null;
	String ExpectedLoggedInURL = null;

	@DataProvider(name = "readValidData")
	public Object[][] testValidData() throws IOException{
		String projectPath = System.getProperty("user.dir");
		DataLayer dl = new DataLayer();
		Object[][] data = dl.realExcel(projectPath+"/Excel/LoginData.xlsx", "Valid credentials");
		return data;
		
	}
	
	@DataProvider(name = "readInvalidData")
	public Object[][] testInvalidData() throws IOException{
		String projectPath = System.getProperty("user.dir");
		DataLayer dl = new DataLayer();
		Object[][] data = dl.realExcel(projectPath+"/Excel/LoginData.xlsx", "Invalid credentials");
		return data;
		
	}
	
	@Test
	public void setUpBrowser(){

		String CurrentProjectPath = System.getProperty("user.dir");

		System.setProperty("webdriver.chrome.driver", CurrentProjectPath+"/Drivers/ChromeDriver/chromedriver.exe");

		driver = new ChromeDriver();

		htmlReporter = new ExtentHtmlReporter("extentTestNGReport.html");

		extent = new ExtentReports();

		extent.attachReporter(htmlReporter);
	}
	
	@Test(priority = 1, dataProvider = "readValidData")
	public void loginToOrangeHRMWithValidCredentials(String username, String password) throws InterruptedException{


		System.out.println(username+"\t\t\t"+password);
		test = extent.createTest("Valid Login Test", "TestLoginFunctionality");

		driver.get("https://opensource-demo.orangehrmlive.com/");

		test.log(Status.INFO, "Login screen is opened");
		
		OrageHRMLoginScreen loginScreen = new OrageHRMLoginScreen(driver);
		
		loginScreen.EnterUsername(username);
		
		test.log(Status.INFO,"Entered valid username");
		
		loginScreen.EnterPassword(password);
	
		test.log(Status.INFO,"Entered valid password");

		loginScreen.ClickOnLoginButton();

		test.log(Status.INFO,"Clicked on login buttton");

		test.pass("User logged in successfully");

		ExpectedLoggedInURL = driver.getCurrentUrl();

		System.out.println("ExpectedLoggedInURL"+ExpectedLoggedInURL);
		
		OrangeHRMTestLogout logout = new OrangeHRMTestLogout(driver,htmlReporter,extent,test);
		
	}

	@Test(priority = 2, dataProvider = "readInvalidData")
	public void InvalidloginToOrangeHRM(String username, String password) throws Exception {
		
		System.out.println(username+"\t\t\t"+password);

		test = extent.createTest("Invalid Login Test", "TestLoginFunctionality");

		driver.get("https://opensource-demo.orangehrmlive.com/");
		
		OrageHRMLoginScreen loginScreen = new OrageHRMLoginScreen(driver);

		test.log(Status.INFO, "Login screen is opened");

		loginScreen.EnterUsername(username);

		test.log(Status.INFO,"Entered valid username");

		loginScreen.EnterPassword(password);
		
		test.log(Status.INFO,"Entered invalid password");

		loginScreen.ClickOnLoginButton();

		test.log(Status.INFO,"Clicked on login buttton");

		String ActualURL = driver.getCurrentUrl();

		System.out.println("ActualURL"+ActualURL);

		assertNotEquals(ActualURL, ExpectedLoggedInURL, "Invalid login test case fail");
		// log with snapshot
		System.out.println("Invalid login test case pass");
		test.pass("Invalid test case login test script passed");
	

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
				FileUtils.copyFile(source, new File("./Screenshots/"+result.getName()+".png"));

				System.out.println("Screenshot taken");
			} 
			catch (Exception e)
			{

				System.out.println("Exception while taking screenshot "+e.getMessage());
			}
		}else{
			
			System.out.println(result.getName() +" : Success");
		} 

	}

		@AfterSuite
		public void closeBrowser(){

			driver.close();
			driver.quit();
			test.log(Status.INFO, "Test completed");
			extent.flush();

		}

	}
