package newbike;

import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.chrome.ChromeOptions;

public class Bike {
  @Test
  public void chrome() {
	  ChromeOptions options = new ChromeOptions();
	  options.addArguments("--disable-notifications");
	  
	  options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
	  WebDriver driver =new ChromeDriver(options);
	 
	  datafecth(driver);
	
	  
			
  }
 public  static void datafecth(WebDriver driver) {
	  
	  try {
			
			File f = new File("src/test/resources/Data/data.xlsx");
			XSSFWorkbook wb = new XSSFWorkbook(f);
			XSSFSheet sheet = wb.getSheetAt(0);
			int rowCount = sheet.getLastRowNum()+1;
			String bikeName="";
			String Location="";
			String car="";
			String email="";
			for(int i=1; i<rowCount; i++) {
				XSSFRow r=sheet.getRow(i);
				bikeName=r.getCell(0).getStringCellValue();
				Location=r.getCell(1).getStringCellValue();
				car=r.getCell(2).getStringCellValue();
				email=r.getCell(3).getStringCellValue();
				run(driver,bikeName,Location,car,email);
			}
			driver.quit();
			wb.close();
	  }catch(Exception e) {
		  System.out.println(e);
	  }
  }
  public static void run(WebDriver driver,String bikeName,String Location,String car,String email) throws InterruptedException, IOException {
	  driver.get("https://www.zigwheels.com");
	  driver.manage().window().maximize();
	  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	  // bike handle start
	  driver.findElement(By.linkText("NEW BIKES")).click();
	  wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//li[@data-track-label='upcoming-tab']"))));
	  Thread.sleep(1000);
	  driver.findElement(By.xpath("//li[@data-track-label='upcoming-tab']")).click();


	  wait.until(ExpectedConditions.textToBe(By.xpath("//a[normalize-space()='Upcoming Bikes']"), "Upcoming Bikes"));
	  WebElement iframe = driver.findElement(By.xpath("//a[normalize-space()='Upcoming Bikes']"));
	  new Actions(driver)
	  .scrollToElement(iframe)
              .perform();
	  wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[normalize-space()='Upcoming Bikes']"))));
	  	driver.findElement(By.xpath("//a[normalize-space()='Upcoming Bikes']")).click();

	  
		  wait.until(ExpectedConditions.textToBe(By.xpath("//a[normalize-space()='"+bikeName+"']"), bikeName));
		  iframe = driver.findElement(By.xpath("//a[normalize-space()='"+bikeName+"']"));
		  new Actions(driver)
		  .scrollToElement(iframe)
	              .perform();
		  	driver.findElement(By.xpath("//a[normalize-space()='"+bikeName+"']")).click();
		  	List<WebElement> ob=driver.findElements(By.xpath("//div[@class='b fnt-15']"));
		  	for(WebElement l:ob) {
		  		String s=l.getText();
		  		if(s.contains("Lakh")) {
		  			double a=Double.parseDouble(s.substring(4, s.indexOf('L')-1));
		  			if(a<4) {
		  				System.out.println(l.getAttribute("title"));
		  			}	
		  		}
		  		else {
		  			System.out.println(l.getAttribute("title"));
		  		}	
		  	}
		  	System.out.println("___________________________");
		  	// Bike handle end
		  	
		  	iframe=driver.findElement(By.xpath("//a[normalize-space()='NEW BIKES']"));
			new Actions(driver)
				.scrollToElement(iframe)
				.perform();
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[@class='zw i-b mt-10 pt-2 zw-srch-logo']"))));
			driver.findElement(By.xpath("//a[@class='zw i-b mt-10 pt-2 zw-srch-logo']")).click();
		  	
		  	// Car handle  Start
			WebElement clickMoreSpan = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='c-p icon-down-arrow']")));
	        clickMoreSpan.click();
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[normalize-space()='Used Cars']"))));
			Thread.sleep(2000);
			WebElement used_cars= driver.findElement(By.xpath("//a[normalize-space()='Used Cars']"));
			used_cars.click();
			
			WebElement city= driver.findElement(By.xpath("//input[@id='gs_input5']"));
			city.sendKeys(Location);
			driver.findElement(By.xpath("//a[@title='"+Location+"']")).click();
			WebElement scroll_frame= driver.findElement(By.xpath("//body/div[@class='zw-cmn-containerColor']/div[@class='container m-w-p0']/div[@class='row']/div[@class='col-lg-3 col-md-3 hidden-xs hidden-sm filterParent']/div[@class='zm-cmn-WhiteBG zw-cmn-shadowBorder']/div[@class='zw-sr-filterWrap']/ul[@class='zw-sr-sortLevelFst zm-cmn-colorBlack']/li[2]"));
			new Actions(driver)
				.scrollToElement(scroll_frame)
				.perform();
			
			Thread.sleep(1000);
			
			 WebElement carName= wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//label[normalize-space()='"+car+"']"))));
			carName.click();
	
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h1[starts-with(text(),'Used "+car.split(" ")[0]+"')]"))));
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[@data-track-label='Car-name']"))));
			List<WebElement> li= driver.findElements(By.xpath("//a[@data-track-label='Car-name']"));
			

			for (int i=0;i<5;i++) {
			
			    System.out.println(li.get(i).getText());
			}
			System.out.println("___________________________");
		
			scroll_frame= driver.findElement(By.xpath("//a[normalize-space()='NEW BIKES']"));
			new Actions(driver)
				.scrollToElement(scroll_frame)
				.perform();
			
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[@class='zw i-b mt-10 pt-2 zw-srch-logo']"))));
			driver.findElement(By.xpath("//a[@class='zw i-b mt-10 pt-2 zw-srch-logo']")).click();
			driver.findElement(By.xpath("//div[@id='forum_login_wrap_lg']")).click();
			String parentWinHandle= driver.getWindowHandle();
			
//			wait.until(ExpectedConditions.textToBe(By.xpath("//span[normalize-space()='Google']"), "Google"));
			Thread.sleep(3000);
			driver.findElement(By.xpath("//span[normalize-space()='Google']")).click();
			Thread.sleep(4000);
			Set<String> allHandle= driver.getWindowHandles();
			String childWinHandle="";
			for(String s: allHandle)
			{
				if(!s.equals(parentWinHandle))
				{
					childWinHandle= s;
					break;
				}
			}
			driver.switchTo().window(childWinHandle);
			driver.findElement(By.id("identifierId")).sendKeys(email);
			WebElement but=driver.findElements(By.tagName("button")).get(3);
			but.click();

				Thread.sleep(1000);
			
			TakesScreenshot ts = (TakesScreenshot)driver;
			File src = ts.getScreenshotAs(OutputType.FILE);
			FileOutputStream dest = new FileOutputStream("src\\test\\resources\\error_images\\error1.png");
			FileUtils.copyFile(src, dest);
			dest.close();
			driver.close();
			driver.switchTo().window(parentWinHandle);
			driver.manage().deleteAllCookies();
			
			
  }
 
}
 

