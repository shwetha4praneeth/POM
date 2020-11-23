package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage 
{
	//Properties for locating elements
	public RemoteWebDriver driver;
	
	@FindBy(how=How.NAME, using="password")
	public WebElement pwd;
	
	@FindBy(how=How.XPATH, using="//*[text()='Next']/following-sibling::div")
	public WebElement pwdnext;
	
	@FindBy(how=How.XPATH, using="//*[text()='Enter a password']")
	public WebElement blankpwderr;
	
	@FindBy(how=How.XPATH, using="//*[contains(text(),'Wrong password') or contains(text(),'Your password was changed')]")
	public WebElement invalidpwderr;
	
	//Constructor method for connecting with runner classes(via association)
	public LoginPage(RemoteWebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	//Operational methods to operate elements
	public void pwdfill(String x)
	{
		pwd.sendKeys(x);
	}
	
	public void pwdnextclick()
	{
		pwdnext.click();
	}
}
