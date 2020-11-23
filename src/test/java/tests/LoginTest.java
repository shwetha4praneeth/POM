package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.ComposePage;
import pages.HomePage;
import pages.LoginPage;
import pages.LogoutPage;
import utilities.TestUtility;

public class LoginTest 
{
	public static void main(String[] args) throws Exception
	{
		//Access Excel file for test data
		String tdfpath=System.getProperty("user.dir")+"\\src\\test\\resources\\Book1.xlsx";
		File f=new File(tdfpath);
		FileInputStream fi=new FileInputStream(f);
		Workbook wb=WorkbookFactory.create(fi);
		Sheet sh=wb.getSheet("Sheet1");
		int nour=sh.getPhysicalNumberOfRows(); //Count of used rows
		int nouc=sh.getRow(0).getLastCellNum(); //Count of used columns
		SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
		Date dt=new Date();
		Cell rc=sh.getRow(0).createCell(nouc); //Result column is next to last used column
		rc.setCellValue("Login Test Results on "+sf.format(dt));
		//Create object to Utility class & activate properties file
		TestUtility util=new TestUtility();
		Properties pro=util.accessProperties();
		//Login functional testing with multiple test data in cross browser environment
		//Loop from 2nd row(index=1) in excel file due to 1st row has names of columns
		for(int i=1;i<nour;i++)
		{
			DataFormatter df=new DataFormatter();
			try
			{
				String bn=df.formatCellValue(sh.getRow(i).getCell(0));
				String u=df.formatCellValue(sh.getRow(i).getCell(1));
				String uc=df.formatCellValue(sh.getRow(i).getCell(2));
				String p=df.formatCellValue(sh.getRow(i).getCell(3));
				String pc=df.formatCellValue(sh.getRow(i).getCell(4));
				//Open Browser
				RemoteWebDriver driver=util.openBrowser(bn);
				//Create objects to page classes
				HomePage hp=new HomePage(driver);
				LoginPage lp=new LoginPage(driver);
				ComposePage cp=new ComposePage(driver);
				LogoutPage lop=new LogoutPage(driver);
				//Launch site
				util.launchSite(pro.getProperty("url"));
				int max=Integer.parseInt(pro.getProperty("maxwait"));
				WebDriverWait w=new WebDriverWait(driver,max);
				w.until(ExpectedConditions.visibilityOf(hp.uid));
				//Enter userid & click next
				hp.uidfill(u); //Parameterization
				hp.uidnextclick();
				Thread.sleep(5000); //Mandatory before checking o/p state
				//Userid testing
				if(u.length()==0)
				{
					try
					{
						ExpectedCondition<WebElement> con1=ExpectedConditions.visibilityOf(hp.blankuiderr);
						w.until(con1);
						Cell c=sh.getRow(i).createCell(nouc);
						c.setCellValue("Uid blank test passed");
					}
					catch(Exception ex1)
					{
						Cell c=sh.getRow(i).createCell(nouc);
						c.setCellValue("Uid blank test failed & see "+util.screenshot());
					}
				}
				else if(u.length()!=0 && uc.equalsIgnoreCase("Invalid"))
				{
					try
					{
						w.until(ExpectedConditions.visibilityOf(hp.invaliduiderr));
						Cell c=sh.getRow(i).createCell(nouc);
						c.setCellValue("Invalid UID test passed");
					}
					catch(Exception ex2)
					{
						Cell c=sh.getRow(i).createCell(nouc);
						c.setCellValue("Invalid Uid test failed & see "+util.screenshot());
					}
				}
				else //Uid is valid value
				{
					try
					{
						w.until(ExpectedConditions.visibilityOf(lp.pwd));
						//Fill password & click next
						lp.pwdfill(p); //parameterization
						lp.pwdnextclick();
						Thread.sleep(5000); //Mandatory before checking o/p state
						//Password testing
						if(p.length()==0)
						{
							try
							{
								w.until(ExpectedConditions.visibilityOf(lp.blankpwderr));
								Cell c=sh.getRow(i).createCell(nouc);
								c.setCellValue("Pwd blank test passed");
							}
							catch(Exception ex1)
							{
								Cell c=sh.getRow(i).createCell(nouc);
								c.setCellValue("Pwd blank test failed & see "+util.screenshot());
							}
						}
						else if(p.length()!=0 && pc.equalsIgnoreCase("invalid"))
						{
							try
							{
								w.until(ExpectedConditions.visibilityOf(lp.invalidpwderr));
								Cell c=sh.getRow(i).createCell(nouc);
								c.setCellValue("Invalid pwd test passed");
							}
							catch(Exception ex2)
							{
								Cell c=sh.getRow(i).createCell(nouc);
								c.setCellValue("Invalid pwd test failed & see "+util.screenshot());
							}
						}
						else //Pwd is valid value
						{
							try
							{
								w.until(ExpectedConditions.elementToBeClickable(cp.comp));
								Cell c=sh.getRow(i).createCell(nouc);
								c.setCellValue("Valid pwd test passed");
								//Do logout
								w.until(ExpectedConditions.elementToBeClickable(lop.profilepic));
								lop.clickProfilePic();
								w.until(ExpectedConditions.elementToBeClickable(lop.signout));
								lop.clickSignOut();
								w.until(ExpectedConditions.visibilityOf(lop.relogin));
							}
							catch(Exception ex3)
							{
								Cell c=sh.getRow(i).createCell(nouc);
								c.setCellValue("Valid pwd test failed & see "+util.screenshot());
							}
						}
					}
					catch(Exception ex4)
					{
						Cell c=sh.getRow(i).createCell(nouc);
						c.setCellValue("Valid Uid test failed & see "+util.screenshot());
					}
				}
			}
			catch(Exception ex)
			{
				Cell c=sh.getRow(i).createCell(nouc);
				c.setCellValue(ex.getMessage());
			}
			//Close site
			util.closeSite();
		} // loop ending
		//Save & close excel file
		sh.autoSizeColumn(nouc);
		FileOutputStream fo=new FileOutputStream(f);
		wb.write(fo);
		wb.close();
		fo.close();
		fi.close();
	}
}
