package pages;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.sun.glass.events.KeyEvent;

public class ComposePage 
{
	//Properties for locating elements
	public RemoteWebDriver driver;
	
	@FindBy(how=How.XPATH, using="//*[text()='Compose']")
	public WebElement comp;
	
	@FindBy(how=How.NAME, using="to")
	public WebElement toaddress;
	
	@FindBy(how=How.NAME, using="subjectbox")
	public WebElement subject;
	
	@FindBy(how=How.XPATH, using="//div[@aria-label='Message Body']")
	public WebElement body;
	
	@FindBy(how=How.XPATH, using="//div[@aria-label='Attach files']/div/div")
	public WebElement attach;
	
	@FindBy(how=How.XPATH, using="//*[text()='Send']")
	public WebElement send;
	
	@FindBy(how=How.XPATH, using="//*[contains(text(),'Message sent')]")
	public WebElement outputmsg;
	
	@FindBy(how=How.XPATH, using="//*[contains(@aria-label,'Attachment:')]")
	public WebElement uploaded;
	
	//Constructor method for connecting runner classes(via Association)
	public ComposePage(RemoteWebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	//Operational methods to operate elements
	public void clickCompose()
	{
		comp.click();
	}
	
	public void fillTo(String x)
	{
		toaddress.sendKeys(x);
	}
	
	public void fillSubject(String x)
	{
		subject.sendKeys(x);
	}
	
	public void fillBody(String x)
	{
		body.sendKeys(x);
	}
	
	public void attachment(String x) throws Exception
	{
		attach.click();
		Thread.sleep(5000); //Wait for file upload window to open
		StringSelection f=new StringSelection(x);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(f, null);
		Robot r=new Robot();
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(5000);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(1000); //Time to close upload window
	}
	
	public void clickSend()
	{
		send.click();
	}
}
