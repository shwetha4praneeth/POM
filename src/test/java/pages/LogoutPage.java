package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LogoutPage 
{
	//Properties for locating elements
	public RemoteWebDriver driver;
	
	@FindBy(how=How.XPATH, using="//a[starts-with(@aria-label,'Google Account')]/img")
	public WebElement profilepic;
	
	@FindBy(how=How.LINK_TEXT, using="Sign out")
	public WebElement signout;
	
	@FindBy(how=How.XPATH, using="//*[text()='Choose an account']")
	public WebElement relogin;
	
	//Constructor method for connecting with runner classes(via Association)
	public LogoutPage(RemoteWebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	//Operational methods to operate elements
	public void clickProfilePic()
	{
		profilepic.click();
	}
	
	public void clickSignOut()
	{
		signout.click();
	}
}
