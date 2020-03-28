package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NationalitiesPage {

	WebDriver driver;
	Actions action;

	public NationalitiesPage(WebDriver driver) {
		this.driver = driver;
	}

	By AddBtn = By.name("btnAdd");
	By DeleteBtn = By.name("btnDelete");
	By NameTextbox = By.xpath("//*[@id='nationality_name']");
	By SaveBtn = By.name("btnSave");
	By CancelBtn = By.name("btnCancel");

	public void clickOnAddBtn() {
		driver.findElement(AddBtn).click();
	}

	public void clickOnDeleteBtn() {
		driver.findElement(DeleteBtn).click();
	}

	public void enterNationalityName(String Name) {
		driver.findElement(NameTextbox).sendKeys(Name);
	}

	public void clickOnSaveButton() {
		driver.findElement(SaveBtn).click();
	}

	public void clickOnCancelButton() {
		driver.findElement(CancelBtn).click();
	}

	public String getSavedNameIntoTable(String Name) {

		By CheckName = By.linkText(Name);
		String SavedName = driver.findElement(CheckName).getText();

		return SavedName;

	}

	public boolean checkNationalityNameIntoTable(String Name) {
		if (driver.findElements(By.linkText(Name)).size() != 0) {
			return true;
		} else {
			return false;
		}

	}
	
	public void deleteNationaltity(String Name){
		System.out.println("Into function deleteNationaltity");
		WebElement deleteNationality = driver.findElement(By.xpath("//a[contains(text(),'"+Name+"')]/../preceding-sibling::td"));
		System.out.println("Searched checkbox");
		deleteNationality.click();
		System.out.println("selected checkbox");
		clickOnDeleteBtn();
		System.out.println("clicked on delete button");
		driver.findElement(By.xpath("//input[@id='dialogDeleteBtn']")).click();
		System.out.println("Click on delete button of dialog box");
	
	}
}
