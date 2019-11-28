package Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentReportTestNGExample {

	WebDriver driver = null;

	ExtentHtmlReporter htmlReporter = null;
	ExtentReports extent = null;

	ExtentTest test = null;
	String ExpectedLoggedInURL = null;

	@BeforeSuite
	public void setUpBrowser(){

		String CurrentProjectPath = System.getProperty("user.dir");

		System.setProperty("webdriver.chrome.driver", CurrentProjectPath+"/Drivers/ChromeDriver/chromedriver.exe");

		driver = new ChromeDriver();

		htmlReporter = new ExtentHtmlReporter("extentTestNGReport.html");

		extent = new ExtentReports();

		extent.attachReporter(htmlReporter);
	}

	@Test(priority = 1)
	public void loginToOrangeHRM(){


		test = extent.createTest("Valid Login Test", "TestLoginFunctionality");

		driver.get("https://opensource-demo.orangehrmlive.com/");

		test.log(Status.INFO, "Login screen is opened");

		driver.findElement(By.id("txtUsername")).click();

		driver.findElement(By.id("txtUsername")).sendKeys("Admin");

		test.log(Status.INFO,"Entered valid username");

		driver.findElement(By.id("txtPassword")).click();

		driver.findElement(By.id("txtPassword")).sendKeys("admin123");

		test.log(Status.INFO,"Entered valid password");

		driver.findElement(By.id("btnLogin")).click();

		test.log(Status.INFO,"Clicked on login buttton");

		test.pass("User logged in successfully");

		ExpectedLoggedInURL = driver.getCurrentUrl();

		System.out.println("ExpectedLoggedInURL"+ExpectedLoggedInURL);
	}

	@Test(priority = 2)
	public void Logout() throws InterruptedException{

		Thread.sleep(5000);

		test.log(Status.INFO,"Logout Test Functionality");

		driver.findElement(By.id("welcome")).click();

		test.log(Status.INFO,"Clicked on welcome link");

		Thread.sleep(3000);

		driver.findElement(By.xpath(".//a[contains(text(),'Logout')]")).click();

		//driver.findElement(By.xpath(".//a[contains(text(),'Abmelden')]")).click();

		test.log(Status.INFO,"Clicked on logout link");

		test.pass("Logged out successfully");


	}

	@Test(priority = 3)
	public void InvalidloginToOrangeHRM() throws Exception {

		test = extent.createTest("Invalid Login Test", "TestLoginFunctionality");

		driver.get("https://opensource-demo.orangehrmlive.com/");

		test.log(Status.INFO, "Login screen is opened");

		driver.findElement(By.id("txtUsername")).click();

		driver.findElement(By.id("txtUsername")).sendKeys("Admin");

		test.log(Status.INFO,"Entered valid username");

		driver.findElement(By.id("txtPassword")).click();

		driver.findElement(By.id("txtPassword")).sendKeys("admin1234");

		test.log(Status.INFO,"Entered invalid password");

		driver.findElement(By.id("btnLogin")).click();

		test.log(Status.INFO,"Clicked on login buttton");

		String ActualURL = driver.getCurrentUrl();

		System.out.println("ActualURL"+ActualURL);

		assertNotEquals(ActualURL, ExpectedLoggedInURL, "Invalid login test case fail");
		// log with snapshot
		System.out.println("Invalid login test case pass");
		test.pass("Invalid test case login test script passed");
		takeScreenshot();

	}

	public void takeScreenshot() throws Exception {
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"/screenshotFolder/failed-test.png"));
	}
	// It will execute after every test execution 
	@AfterMethod
	public void tearDown(ITestResult result)
	{

	/*	System.out.println("ITestResult result : "+result);
		System.out.println("result.getStatus() : "+result.getStatus());
		System.out.println("result.getName() : "+result.getName());
	*/	// Here will compare if test is failing then only it will enter into if condition
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
