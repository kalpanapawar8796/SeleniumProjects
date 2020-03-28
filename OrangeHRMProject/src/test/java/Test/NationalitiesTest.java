package Test;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Pages.DashboardPage;
import Pages.LoginPage;
import Pages.NationalitiesPage;
import Utilities.DataLayer;

public class NationalitiesTest {

	WebDriver driver = null;
	private static Logger log = LogManager.getLogger("devpinoyLogger");
	String CurrentProjectPath = System.getProperty("user.dir");

	@DataProvider(name = "logindata")
	public Object[][] getLoginDetails() throws IOException {

		DataLayer dl = new DataLayer();
		Object[][] data = dl.realExcel(CurrentProjectPath + "/Excel/OrangeHRM.xlsx", "Login");
		return data;

	}

	@DataProvider(name = "nationalitydata")
	public Object[][] getNationalityDetails() throws IOException {

		DataLayer dl = new DataLayer();
		Object[][] data = dl.realExcel(CurrentProjectPath + "/Excel/OrangeHRM.xlsx", "Nationality");
		return data;

	}

	@BeforeClass
	public void setBrowesr() {

		System.setProperty("webdriver.chrome.driver", CurrentProjectPath + "/Drivers/ChromeDriver/chromedriver_3.exe");
		driver = new ChromeDriver();
		String log4jConfPath = "C:\\Users\\Win\\Desktop\\Seleniummaterial27thNov\\OrangeHRMProject\\src\\main\\java\\log4j2.properties";
		PropertyConfigurator.configure(log4jConfPath);
		driver.get("https://opensource-demo.orangehrmlive.com/");
		log.debug("opening webiste");
		LoginTest logintest = new LoginTest();

	}

	@Test(priority = 1, dataProvider = "logindata")
	public void Login(String Username, String Password) {
		LoginPage loginWebElement = new LoginPage(driver);
		loginWebElement.EnterUsername(Username);
		log.debug("Entered username as" + Username);
		loginWebElement.EnterPassword(Password);
		log.debug("Entered password as" + Password);
		loginWebElement.ClickOnLoginButton();
		log.debug("Clicked on Login button");

		DashboardPage dashboard = new DashboardPage(driver);
		assertEquals(dashboard.getUsername(), "Welcome " + Username);
		log.debug("User " + Username + " logged in successfully");

	}

	@Test(priority = 2)
	public void openNationaliitesPage() {
		System.out.println(driver.getCurrentUrl());
		DashboardPage dashboard = new DashboardPage(driver);
		dashboard.mouseHoverOverOnAdminMenu();
		log.debug("mouse hover on admin menu");
		dashboard.clickOnNationalitiesSubmenu();
		log.debug("Clicked on nationalities submenu");
		Assert.assertEquals(driver.getCurrentUrl(),
				"https://opensource-demo.orangehrmlive.com/index.php/admin/nationality");
		log.info("Nationality page is opened");

	}

	@Test(priority = 3, dataProvider = "nationalitydata")
	public void addNationality(String Name) {
		NationalitiesPage nationalitypage = new NationalitiesPage(driver);
		nationalitypage.clickOnAddBtn();
		log.debug("Clicked on add button");
		if (nationalitypage.checkNationalityNameIntoTable(Name) == false) {
			nationalitypage.enterNationalityName(Name);
			log.debug("entered nationality name");
			nationalitypage.clickOnSaveButton();
			log.debug("Clicked on save button");
			Assert.assertEquals(nationalitypage.getSavedNameIntoTable(Name), Name);
			log.debug("Saved successfully");

		} else {
			log.warn("Name already exist");
		}
	}
	@Test(priority = 4, dataProvider = "nationalitydata")
	public void deleteNationality(String Name) {
		NationalitiesPage nationalitypage = new NationalitiesPage(driver);
		
		if (nationalitypage.checkNationalityNameIntoTable(Name) == false) {
				log.debug("No name found");
		} else {
			log.info("Delete entered nationality");
			nationalitypage.deleteNationaltity(Name);
			log.debug("Performed delete functionality");
			Assert.assertEquals(nationalitypage.checkNationalityNameIntoTable(Name), false);
			log.debug("Nationality deleted successfully");
			
		}
	}
	// --------------------------------------Code For Taking Screenshot if
	// script fails-----------------------------------------------//
	@AfterMethod
	public void tearDown(ITestResult result) {

		System.out.println("ITestResult result : " + result);
		System.out.println("result.getStatus() : " + result.getStatus());
		System.out.println("result.getName() : " + result.getName());
		// Here will compare if test is failing then only it will enter into if
		// condition
		if (ITestResult.FAILURE == result.getStatus()) {
			System.out.println(result.getName() + "  : Failure");
			try {
				// Create reference of TakesScreenshot
				TakesScreenshot ts = (TakesScreenshot) driver;

				// Call method to capture screenshot
				File source = ts.getScreenshotAs(OutputType.FILE);

				// Copy files to specific location here it will save all
				// screenshot in our project home directory and
				// result.getName() will return name of test case so that
				// screenshot name will be same
				/*
				 * FileUtils.copyFile(source, new
				 * File("./Screenshots/"+result.getName()+".png"));
				 * 
				 * System.out.println("Screenshot taken");
				 */
			} catch (Exception e) {

				System.out.println("Exception while taking screenshot " + e.getMessage());
			}
		} else {

			System.out.println(result.getName() + " : Success");
		}

	}

	@AfterClass
	public void closeBrowser() {

		driver.close();
		driver.quit();

	}

}
