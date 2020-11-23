package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HomePage 
{
	//Properties for locating elements
	public RemoteWebDriver driver;
	
	@FindBy(how=How.NAME, using="identifier")
	public WebElement uid;
	
	@FindBy(how=How.XPATH, using="//*[text()='Next']/following-sibling::div")
	public WebElement uidnext;
	
	@FindBy(how=How.XPATH, using="//div[contains(text(),'Enter an email')]")
	public WebElement blankuiderr;
	
	@FindBy(how=How.XPATH, using="//div[text()=\"Couldn't find your Google Account\" or contains(text(),'Enter a valid email')]")
	public WebElement invaliduiderr;
	
	//Constructor method for connecting runner classes(via association in oops)
	public HomePage(RemoteWebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	//Operational methods to operate elements
	public void uidfill(String x)
	{
		uid.sendKeys(x);
	}
	
	public void uidnextclick()
	{
		uidnext.click();
	}
}
