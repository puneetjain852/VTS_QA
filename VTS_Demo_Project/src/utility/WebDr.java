////*##############################################################################
//'Class Name: WebDr.java
//'Description: 
//'Prepared By: Minhaj Bakhsh
//'Prepared On: 07/22/2015
//'Updated By:
//'Updated On:
//'##############################################################################*/

package utility;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.google.common.base.Predicate;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;


import testCases.Driver;

public class WebDr extends Driver {
	public static Map<String, String> page_Objects = new HashMap<String, String>();
	public static WebElement element = null;
	public static String getExcelValue;
	public static String getValue;
	public static String actualText;
	public static String actualText1;
	public static String actualText_1;
	public static String actualText2;
	public static String getXpath;
	public static String parentWindowID;
	public static String getFirstSelectedOption;
	public static int getActualTextBoxLength;
	public static int getActualTextBoxValue;
	public static Robot r;
	public static String AccountNo = "";
	public static String BatchNo = "";
	public static String CustID = "";
	public static String TDAccountNo = "";
	public static String strFCRCountry;
	public static String strFCRBNC;
	public static WebDriverWait wait;
	public static Boolean readyState=false;
	public static ExtentReports rep;
	
	public static ExtentTest extentTest;
	public static String SerialNo = "";
	
	public static void openApplication(String browser, String URL) {
		DesiredCapabilities oCap = null;
		try {
			// DesiredCapabilities oCap = null;
			if (Launcher.remoteFlag.equals("Yes")) {

				switch (browser) {
				case "IE":
					oCap = DesiredCapabilities.internetExplorer();
					oCap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
					oCap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					oCap.setBrowserName("ie");
					break;
				case "Chrome":
					oCap = DesiredCapabilities.chrome();
					oCap.setBrowserName("chrome");
					break;

				case "Firefox": // Driver.driver = new FirefoxDriver();break;
					oCap = DesiredCapabilities.firefox();
					oCap.setBrowserName("firefox");
					break;

				default:
					break;
				}
				switch (Launcher.nodePlatform) {
				case "XP":
					oCap.setPlatform(Platform.XP);
					break;
				case "VISTA":
					oCap.setPlatform(Platform.VISTA);
					break;
				case "WIN8":
					oCap.setPlatform(Platform.WIN8);
					break;
				default:
					break;
				}

				Driver.driver = new RemoteWebDriver(new URL(Launcher.nodeURL
						+ "/wd/hub"), oCap);
			} else {

				switch (browser) {
				case "IE":
					System.setProperty("webdriver.ie.driver",
							utility.Constant.IE_Driver);
				//	oCap.setCapability("ignoreZoomSetting", true);
					oCap = DesiredCapabilities.internetExplorer();
					oCap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
					oCap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					oCap.setBrowserName("ie");
					Driver.driver = new InternetExplorerDriver(oCap);
					Driver.driver.get("www.google.com");

					/*driver.manage().deleteAllCookies();
					Driver.driver
							.navigate()
							.to("javascript:document.getElementById('overridelink').click()");*/
					break;
				case "Chrome":
					System.setProperty("webdriver.chrome.driver",
							utility.Constant.Chrome_Driver);
					oCap = DesiredCapabilities.chrome();
					oCap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					Driver.driver = new ChromeDriver();
					Driver.driver.get(URL);
					driver.manage().window().maximize();
					WebDr.frame("ifrmCookieBanner");
					WebElement CookieAccept = driver.findElement(By.xpath("//button[@class = 'intro-banner-btn']"));
					CookieAccept.click();
					Driver.driver.switchTo().defaultContent();
					break;

				case "Firefox":
					// WebDriver driver = new FirefoxDriver();
					File oBinary = new File(
							"C:\\Program Files\\Mozilla Firefox\\firefox.exe");
					FirefoxBinary oFB = new FirefoxBinary(oBinary);
					FirefoxProfile oFP = new FirefoxProfile();
					//Driver.driver = new FirefoxDriver();
					Driver.driver.get(URL);
					break;
				default:
					break;
				}
			}
			Driver.driver.manage().window().maximize();
			Driver.driver.manage().timeouts()
					.implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Failed to create driver object -"
					+ " Method openApplication in WebDr");
		}
	}
	
	 

	//
	public static String getValue(String objectName) {
		try {
			if (Driver.dictionary.containsKey(objectName))
				return Driver.dictionary.get(objectName);
			else
			{
				System.out.println("unable to find value for the field = " + objectName);
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	// #######################################################################################
	public static WebElement getElement(String str) throws InterruptedException {
	   while(readyState=false){
		   Thread.sleep(1000);}
		System.out.println("WebDr.java- getElement Invoked");
		String desc = page_Objects.get(str);
		System.out.println(str+" "+desc);
		String[] a = desc.split("\\|");
		System.out.println(a.length);
		try{
		switch (a[0]) {
			case "ID":
				
				if(desc.split("\\|").length==3){
					frame(desc.split("\\|")[2]);
				}
				WebElement x= Driver.driver.findElement(By.id(a[1]));
				return x;
			
			case "NAME":
				
				if(a.length==3){
					frame(desc.split("\\|")[2]);
				}
				x= Driver.driver.findElement(By.name(a[1]));
				return x;
				
			case "XPATH":
				if(desc.split("\\|").length==3){
				frame(desc.split("\\|")[2]);
			}
				x =  Driver.driver.findElement(By.xpath(a[1]));
				return x;
				
			case "LINKTEXT":
				if(desc.split("\\|").length==3){
				frame(desc.split("\\|")[2]);
			}
				x =  Driver.driver.findElement(By.linkText(a[1]));
				return x;
			default:
				System.out.println("element check");
				break;
			}
			return null;
		}catch(NoSuchElementException e){
			e.printStackTrace();
			return null;
		}
	   
}
	
	

	// #######################################################################################
	public static List<WebElement> getElements(String str) {
		// System.out.println("WebDr.java- getElement Invoked");
		String desc = page_Objects.get(str);
		System.out.println(desc);
		String[] a = desc.split("\\|");
		switch (a[0]) {

		case "ID":
			return Driver.driver.findElements(By.id(a[1]));

		case "XPATH":
			return Driver.driver.findElements(By.xpath(a[1]));
		default:
			System.out.println("element check");
			break;
		}
		return null;
	}

	// +++++++++++++++++++++++++++++++++++++++

	// ******************************************************************************************
	public static boolean exists(String elementName, boolean expected,
			String description) throws Exception {
		// System.out.println("WebDr.java- exists Invoked");
		WebElement elmn = getElement(elementName);
		boolean state = false;

		if (elmn.isDisplayed() && expected == true) {
			HtmlReporter.WriteStep(description, "Exists", "Exists", true);
			state = true;
		} else if (elmn.isDisplayed() && expected == false) {
			HtmlReporter.WriteStep(description, "Should not Exists", "Exists",
					false);
			state = false;
		} else if (!elmn.isDisplayed() && expected == true) {
			HtmlReporter.WriteStep(description, "Exists", "Does not exists",
					false);
			state = true;
		} else if (!elmn.isDisplayed() && expected == false) {
			HtmlReporter.WriteStep(description, "Does not exists",
					"Does not exists", true);
			state = false;
		}
		return state;

	}
	
	// ******************************************************************************************
	public static boolean isElementExist(String elementName, String description) throws Exception {
		try{
			// System.out.println("WebDr.java- exists Invoked");
			WebElement elmn = getElement(elementName);
			if (elmn.isDisplayed()) {
				//HtmlReporter.WriteStep(description, "Exists", "Exists", true);
				return true;
			} else  {
				//HtmlReporter.WriteStep(description, "Should not Exists", "Exists",false);
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	// ******************************************************************************************
	public static boolean isElementDisabled(String elementName, String description) throws Exception {
		try{
			// System.out.println("WebDr.java- exists Invoked");
			WebElement elmn = getElement(elementName);
			if (elmn.isEnabled()) {
				HtmlReporter.WriteStep(description, "Should be disabled", "Element is enabled", false);
				return false;
			} else  {
				HtmlReporter.WriteStep(description, "Should be disabled", "Element is disabled", true);
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	// ******************************************************************************************
	public static boolean enabled(String elementName, boolean expected,
			String description) throws Exception {
		// System.out.println("WebDr.java- exists Invoked");
		WebElement elmn = getElement(elementName);
		boolean state = false;

		if (elmn.isEnabled() && expected == true) {
			HtmlReporter.WriteStep(description, "Exists", "Exists", true);
			state = true;
		} else if (elmn.isEnabled() && expected == false) {
			HtmlReporter.WriteStep(description, "Should not Exists", "Exists",
					false);
			state = false;
		} else if (!elmn.isEnabled() && expected == true) {
			HtmlReporter.WriteStep(description, "Exists", "Does not exists",
					false);
			state = true;
		} else if (!elmn.isEnabled() && expected == false) {
			HtmlReporter.WriteStep(description, "Does not exists",
					"Does not exists", true);
			state = false;
		}
		return state;

	}

	// ******************************************************************************************
	public static boolean IsCheckboxSelected(String elementName,
	String description) throws Exception{
		boolean state = false;
		try{
			WebElement elmn = getElement(elementName);
			if((elmn.isSelected()) && (elmn.isEnabled())){
				HtmlReporter.WriteStep(description, "Checkbox Exists", "Is Selected", true);
				state = true;
			}
			else{
				HtmlReporter.WriteStep(description, "Checkbox Exists", "Is not Selected", true);
				state = false;
			}
			return state;
			}
		catch(NoSuchElementException e){
			HtmlReporter.WriteStep(description,"Checkbox does not Exist", "Not Clicked as Object doesn't exist - ", false);
			return state;	
		}
	}
	// ******************************************************************************************
	
	// ******************************************************************************************
		public static boolean SelectCheckbox(String elementName, String description) throws Exception{
			boolean state = false;
			try{
				WebElement elmn = getElement(elementName);
				
				
				if((elmn.isSelected()) && (elmn.isEnabled())){
					HtmlReporter.WriteStep(description, "Checkbox Exists", "Checkbox Already Selected", true);
					state = true;
				}
				else if((!elmn.isSelected()) && (elmn.isEnabled())){
					elmn.click();
					HtmlReporter.WriteStep(description, "Checkbox Exists", "Checkbox Selected", true);
					state = true;
				}
				else{
					HtmlReporter.WriteStep(description, "Checkbox Exists", "Checkbox Not Selected", true);
					state = false;
				}
				return state;
			}
			catch(NoSuchElementException e){
				HtmlReporter.WriteStep(description,"Checkbox does not Exist", "Checkbox Not Clicked as Object doesn't exist - ", false);
				return state;	
			}
		}
	// ******************************************************************************************
	
	
	public static boolean displayed(String elementName, boolean expected,
			String description) throws Exception {
		// System.out.println("WebDr.java- exists Invoked");
		WebElement elmn = getElement(elementName);
		boolean state = false;

		if (elmn.isDisplayed() && expected == true) {
			HtmlReporter.WriteStep(description, "Exists", "Exists", true);
			state = true;
		} else if (elmn.isDisplayed() && expected == false) {
			HtmlReporter.WriteStep(description, "Should not Exists", "Exists",
					false);
			state = false;
		} else if (!elmn.isDisplayed() && expected == true) {
			HtmlReporter.WriteStep(description, "Exists", "Does not exists",
					false);
			state = true;
		} else if (!elmn.isDisplayed() && expected == false) {
			HtmlReporter.WriteStep(description, "Does not exists",
					"Does not exists", true);
			state = false;
		}
		return state;

	}

	// ******************************************************************************************
	public static void click(String elementName, String description)
			throws Exception {
		// System.out.println("WebDr.java- exists Invoked");
		try{
			WebElement elmn = getElement(elementName);
			
			if (elmn.isDisplayed() && elmn.isEnabled()) {
				try{
					elmn.click();
					HtmlReporter.WriteStep(description,"Click", "Clicked successfully", true);
				}catch(UnhandledAlertException e){
					if (WebDr.isAlertPresent() == true){
						WebDr.getTextOnAlertPopup("Check for error popup message");
						WebDr.alertAccept();
						HtmlReporter.WriteStep(description,"Click", "Unable to proceed ahead due to unexpected popup message", false);
					}
					e.printStackTrace();
				}
			} else {
				HtmlReporter.WriteStep("Object not visible - " + description,
						"Click", "Not Clicked", false);
			}
		}catch(NoSuchElementException e){
			HtmlReporter.WriteStep(description,"Click", "Not Clicked as Object doesn't exist - ", false);
		}
		
	}
	
	public static void alertAcceptAndContinue() throws TimeoutException, NoAlertPresentException{
		try{
			WebDriverWait wait1=new WebDriverWait(driver,20);
			wait1.until(ExpectedConditions.alertIsPresent());
			Alert alert = Driver.driver.switchTo().alert();
			String alertMsg=alert.getText();
            System.out.println("Alert box text " + alert.getText());
             //if(! alertMsg.isEmpty()) 
            if (alertMsg.contains("Do You Want to continue")){
            	alert.accept();
            }
            else{
            	Alert alertcontinue = Driver.driver.switchTo().alert();
	            String alertMsg2=alertcontinue.getText();
	            System.out.println("Alert box text " + alertMsg2);
	            if (alertMsg2.contains("Do You Want to continue")){
	            	alertcontinue.accept();
	            	System.out.println("done with alert and continue");
	             }
            }
		} catch (Exception noAlert) {
			noAlert.getMessage();
		}
	}
	
	// ******************************************************************************************
	public static void clickwithmouse(String elementName, String description)
			throws Exception {
		// System.out.println("WebDr.java- exists Invoked");
		WebElement elmn = getElement(elementName);
		Actions builder = new Actions(driver);
		if (elmn.isDisplayed() && elmn.isEnabled()) {
			try{
				builder.click(elmn).build().perform();
				HtmlReporter.WriteStep(description, "Click", "Clicked", true);
			}catch(Exception e){
					e.printStackTrace();
			}
		} else {
			HtmlReporter.WriteStep("Object not visible - " + description,
					"Click", "Not Clicked", false);
		}
	}
	// ******************************************************************************************
	// ******************************************************************************************
		public static void clickwithmouse_BNC(String elementName, String description)
				throws Exception {
			try{
				// System.out.println("WebDr.java- exists Invoked");
				WebElement elmn = getElement(elementName);
				Actions builder = new Actions(driver);
				if (elmn.isDisplayed() && elmn.isEnabled()) {
					try{
						builder.click(elmn).build().perform();
						System.out.println(description + "Click" +" Clicked successfully");
					}catch(Exception e){
							e.printStackTrace();
					}
				} else {
					System.out.println(description + "Click" +" Unable to Clicked ");
				}
			}catch(Exception err){
				err.printStackTrace();
			}
		}
		// ******************************************************************************************
		public static void doubleclickwithmouse(String elementName, String description)
				throws Exception {
			// System.out.println("WebDr.java- exists Invoked");
			WebElement elmn = getElement(elementName);
			Actions builder = new Actions(driver);
			if (elmn.isDisplayed() && elmn.isEnabled()) {
				try{
					builder.doubleClick(elmn).build().perform();
					HtmlReporter.WriteStep(description, "Double Click", "Double Clicked", true);
				}catch(Exception e){
						e.printStackTrace();
				}
			} else {
				HtmlReporter.WriteStep("Object not visible - " + description,
						"Double Click", "Not Clicked", false);
			}
		}
	// ******************************************************************************************
		public static void clickactivatewithmouse(String elementName, String description)
				throws Exception {
			// System.out.println("WebDr.java- exists Invoked");
			try{
			WebElement elmn = getElement(elementName);
			Actions builder = new Actions(driver);
			if (elmn.isDisplayed()) {
				try{
					builder.click(elmn).build().perform();
					HtmlReporter.WriteStep(description, "Click", "Clicked", true);
				}catch(Exception e){
						e.printStackTrace();
				}
			} else {
//				HtmlReporter.WriteStep("Object not visible - " + description,"Click", "Not Clicked", false);
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	//*************************************************************************************************
	public static void clickwithmouseandacceptalert(String elementName, String description)
			throws Exception {
		// System.out.println("WebDr.java- exists Invoked");
		WebElement elmn = getElement(elementName);
		Actions builder = new Actions(driver);
		if (elmn.isDisplayed() && elmn.isEnabled()) {
			try{
				builder.click(elmn).build().perform();
				String strtext = Driver.driver.switchTo().alert().getText();
				WebDr.alertAccept();
				HtmlReporter.WriteStep(description, "Click", "Clicked with text message " + strtext, true);
			}catch(Exception e){
					String strtext = Driver.driver.switchTo().alert().getText();
					HtmlReporter.WriteStep(description, "Click", "Clicked with text message " + strtext, true);
					if (WebDr.isAlertPresent())
						WebDr.alertAccept();
					e.printStackTrace();
			}
		} else {
			HtmlReporter.WriteStep("Object not visible - " + description,
					"Click", "Not Clicked", false);
		}
	}
	// ******************************************************************************************
	public static void clickwithhold(String elementName, String description)
			throws Exception {
		// System.out.println("WebDr.java- exists Invoked");
		WebElement elmn = getElement(elementName);
		Actions builder = new Actions(driver);
		if (elmn.isDisplayed() && elmn.isEnabled()) {
			try{
				builder.moveToElement(elmn).clickAndHold().build().perform();
				Thread.sleep(4000);
				builder.release().build().perform();
				HtmlReporter.WriteStep(description, "Click", "Clicked", true);
			}catch(Exception e){
					e.printStackTrace();
			}
		} else {
			HtmlReporter.WriteStep("Object not visible - " + description,
					"Click", "Not Clicked", false);
		}
	}
	//************************************************************************************************
	public static void safeJavaScriptClick(String elementName) throws Exception {
		try {
			WebElement elmn = getElement(elementName);
			if (elmn.isEnabled() && elmn.isDisplayed()) {
				System.out.println("Clicking on element with using java script click");

				((JavascriptExecutor) Driver.driver).executeScript("arguments[0].click();", elmn);
			} else {
				System.out.println("Unable to click on element");
			}
		} catch (StaleElementReferenceException e) {
			System.out.println("Element is not attached to the page document "+ e.getStackTrace());
		} catch (NoSuchElementException e) {
			System.out.println("Element was not found in DOM "+ e.getStackTrace());
		} catch (Exception e) {
			System.out.println("Unable to click on element "+ e.getStackTrace());
		}
	}
	// ******************************************************************************************
	public static void setText(String elementName, String textToSet,
			String description) throws Exception {
		// System.out.println("WebDr.java- setText Invoked");

		if (textToSet != null) {
			WebElement elmn = getElement(elementName);
			if (elmn.isDisplayed()) {
				elmn.sendKeys(textToSet);
				HtmlReporter.WriteStep(description, "Enter Text", "Entered - "
						+ textToSet, true);

			} else {
				HtmlReporter.WriteStep("Object not visible - " + description,
						"Enter Text", "Not Entered - " + textToSet, false);
			}
		}

	}

	// *****************************************

	public static void verifyText(String elementName, boolean matchSubString,
			String expectedText, String description) throws Exception {
		WebElement elmn = getElement(elementName);
		if (elmn.isDisplayed()) {
			String actualText = elmn.getText();
			if (matchSubString == true) {
				if (actualText.contains(expectedText)) {
					HtmlReporter.WriteStep(description, "Verify Text",
							"Verified - " + expectedText, true);
				} else {
					HtmlReporter.WriteStep(description, "Verify Text -"
							+ expectedText, "Verification failed - "
							+ actualText, false);
				}
			} else if (matchSubString == false) {
				if (actualText.equals(expectedText)) {
					HtmlReporter.WriteStep(description, "Verify Text",
							"Verified - " + expectedText, true);
				} else {
					HtmlReporter.WriteStep(description, "Verify Text -"
							+ expectedText, "Verification failed - "
							+ actualText, false);
				}
			}
		} else {
			HtmlReporter.WriteStep("Object not visible - " + description,
					"Verify Text -" + expectedText, "Not Verified", false);
		}

	}

	public static void selectListValue(String elementName,
			String expectedTextValue, String description) throws Exception {
		List<WebElement> value = driver.findElements(By.xpath(elementName));
		for (WebElement element : value) {
			if (element.getText().equalsIgnoreCase(expectedTextValue)) {
				System.out.println(element.getText());
				element.click();
				HtmlReporter.WriteStep(description, "Click on value as===>"
						+ expectedTextValue + "", "Click on value as===>"
						+ expectedTextValue + "", true);
			} else {
				HtmlReporter.WriteStep(description,
						"Unable to click on value as===>" + expectedTextValue
								+ "", "Unable to click on value as===>"
								+ expectedTextValue + "", false);
			}
		}
	}

	public static void selectDropValue(String elementName,
			String expectedTextValue, String description) throws Exception {
		List<WebElement> elmn = getElements(elementName);

		for (WebElement element : elmn) {
			if (element.getText().equalsIgnoreCase(expectedTextValue)) {
				element.click();
				HtmlReporter.WriteStep(description,
						"Select drop down value as===>" + expectedTextValue
								+ "", "Select drop down value as===>"
								+ expectedTextValue + "", true);
			} else {
				HtmlReporter.WriteStep(description,
						"Unable to select drop value as===>"
								+ expectedTextValue + "",
						"Unable to select drop value as===>"
								+ expectedTextValue + "", false);
			}
		}
	}

	public static void selectDropValueByVisibleText(String elementName,
			String index, String description) throws Exception {
		WebElement elmn = getElement(elementName);

		if (elmn.isDisplayed()) {
			Select select = new Select(elmn);
			select.selectByVisibleText(index);
			HtmlReporter.WriteStep(description, "Select drop down value as===>"
					+ index + "", "Select drop down value as===>" + index + "",
					true);
		} else {
			HtmlReporter.WriteStep("Object not visible - " + description,
					"Enter Text", "Object not visible - ", false);
		}

	}
	
	public static String getSelectedDropdownValue(String elementName,
			String description) throws Exception {
		WebElement elmn = getElement(elementName);
		String strVal = "";
		if (elmn.isDisplayed()) {
			Select select = new Select(elmn);
			strVal = select.getFirstSelectedOption().getText();
			if (! strVal.isEmpty())
			HtmlReporter.WriteStep(description, "Get selected drop down value"
					, "Selected drop down value is retrieved as ===>" + strVal + "",
					true);
			else {
						HtmlReporter.WriteStep(description,"Get selected drop down value"
								, "Selected drop down value is retrieved as ===>" + strVal + "", false);
					}
			
		} else {
			HtmlReporter.WriteStep("Object not visible - " + description,
					"Enter Text", "Object not visible - ", false);
		}
		return strVal;
	}

	public static void selectDropValueByIndex(String elementName, int index,
			String description) throws Exception {
		WebElement elmn = getElement(elementName);
		

		if (elmn.isDisplayed()) {
			Select select = new Select(elmn);
			select.selectByIndex(index);
			
			HtmlReporter.WriteStep(description, "Select drop down value as===>"
					+ index + "", "Select drop down value as===>" + index + "",
					true);
		} else {
			HtmlReporter.WriteStep("Object not visible - " + description,
					"Enter Text", "Object not visible - ", false);
		}
	}
			/*	HtmlReporter.WriteStep(
							description,
							"Select drop down value as===>" + index + "",
							"Select drop down value as===>xxxx xx53 05 - BANK-KES KES 3,816.70",
							true);

			extentTest.log(LogStatus.PASS, "Test Case Pass");
			rep.endTest(extentTest);
			rep.flush();
		} else {
			HtmlReporter.WriteStep("Object not visible - " + description,
					"Enter Text", "Object not visible - ", false);
		}
*/
	

	public static void getText(String elementName, String expectedText,
			String description) throws Exception {
		WebElement elmn = getElement(elementName);

		String getTextValue = elmn.getText();
		if (elmn.isDisplayed() && getTextValue.equalsIgnoreCase(expectedText)
		/* || getTextValue.contains(expectedText) */) {
			HtmlReporter.WriteStep(description, "Expected get text value is==>"
					+ expectedText + " is displayed as per expected outcome",
					"Actual get text value as==>" + getTextValue
							+ " should display as per requirment", true);
		} else {
			HtmlReporter.WriteStep(description, "Expected get text value is==>"
					+ expectedText + " is displayed as per expected outcome",
					"Actual get text value as==>" + getTextValue
							+ " should display as per requirment", false);
		}

	}
	
	public static String getText(String elementName) throws Exception {
	
		try{
			WebElement elmn = getElement(elementName);
			String getTextValue = elmn.getText();	
			return getTextValue;
		}catch(Exception err){
			err.printStackTrace();
			return null;
		}
		
	}
	
	public static  String getTextTable(String elementName, 
			String description) throws Exception {
		WebElement elmn = getElement(elementName);

		String getTextValue = elmn.getText();
		if (elmn.isDisplayed() 
		/* || getTextValue.contains(expectedText) */) {
			HtmlReporter.WriteStep(description, "Textbox value should be displayed",
					"Actual  text value  displayed as==>" + getTextValue , true);
			
		} else {
			HtmlReporter.WriteStep(description, "Textbox value should be displayed",
					"Actual  text value displayed as==>" + getTextValue , false);
		}
		return getTextValue;
	}


	public static void getText1(String elementName, String expectedText,
			String description) throws Exception {
		WebElement elmn = getElement(elementName);

		String getTextValue = elmn.getText();
		if (elmn.isDisplayed() && getTextValue.equalsIgnoreCase(expectedText)
		/* || getTextValue.contains(expectedText) */) {
			HtmlReporter.WriteStep(description, "Expected get text value is==>"
					+ expectedText + " is displayed as per expected outcome",
					"Actual get text value as==>" + getTextValue
							+ " should display as per requirment", true);

		} else {
			HtmlReporter.WriteStep(description, "Expected get text value is==>"
					+ expectedText + " is displayed as per expected outcome",
					"Actual get text value as==>" + getTextValue
							+ " should display as per requirment", false);

		}

	}

	public static void getListValue(String elementName, int index,
			String expectedTextValue, String description) throws Exception {
		List<WebElement> elmn = getElements(elementName);
		// int x = elmn.size();
		getValue = elmn.get(index).getText();
		if (elmn.get(index).getText().equalsIgnoreCase(getValue)) {
			elmn.get(index).click();
			HtmlReporter.WriteStep(description, "Select drop down value as===>"
					+ getValue + "", "Select drop down value as===>" + getValue
					+ "", true);
		} else {
			HtmlReporter.WriteStep("Object not visible - " + description,
					"Enter Text", "Object not visible - ", false);
		}
	}

	public static void getTextBasedOnListIndex(String elementName, int index,
			String expectedText, String description) throws Exception {
		List<WebElement> elmn = getElements(elementName);

		actualText = elmn.get(index).getText().trim();
		if (actualText.equalsIgnoreCase(expectedText)) {
			System.out.println("=============>" + elmn.get(index).getText());
			HtmlReporter.WriteStep(description, "Expected get text value is==>"
					+ elmn.get(index).getText() + "",
					"Actual get text value as==>" + elmn.get(index).getText()
							+ "", true);


		} else {
			HtmlReporter.WriteStep(description, "Expected get text value is==>"
					+ expectedText + " is displayed as per expected outcome",
					"Actual get text value as==>" + elmn.get(index).getText()
							+ "", false);


		}

	}

	public static void getTextBasedOnListIndex1(String elementName, int index,
			String expectedText, String description) throws Exception {
		List<WebElement> elmn = getElements(elementName);

		actualText = elmn.get(index).getText().trim();
		if (actualText.equalsIgnoreCase(expectedText)) {
			System.out.println("=============>" + elmn.get(index).getText());
			HtmlReporter.WriteStep(description, "Expected get text value is==>"
					+ elmn.get(index).getText() + "",
					"Actual get text value as==>" + elmn.get(index).getText()
							+ "", true);
		} else {
			HtmlReporter.WriteStep(description, "Expected get text value is==>"
					+ expectedText + " is displayed as per expected outcome",
					"Actual get text value as==>" + elmn.get(index).getText()
							+ "", false);
		}

	}

	public static void getTextBasedOnListIndex_1(String elementName, int index,
			String expectedText, String description) throws Exception {
		List<WebElement> elmn = getElements(elementName);
		// actualText1 = elmn.get(index).getText();
		actualText_1 = elmn.get(index).getText().trim();
		if (actualText_1.equalsIgnoreCase(expectedText)) {
			System.out.println("=============>" + elmn.get(index).getText());
			HtmlReporter.WriteStep(description, "Expected get text value is==>"
					+ elmn.get(index).getText() + "",
					"Actual get text value as==>" + elmn.get(index).getText()
							+ "", true);
		} else {
			HtmlReporter.WriteStep(description, "Expected get text value is==>"
					+ expectedText + " is displayed as per expected outcome",
					"Actual get text value as==>" + elmn.get(index).getText()
							+ "", false);
		}

	}

	public static void getTextBasedOnListIndex1(String elementName, int index)
			throws Exception {
		List<WebElement> elmn = getElements(elementName);
		actualText = elmn.get(index).getText().trim();
	}

	public static void getTextBasedOnListIndex2(String elementName, int index)
			throws Exception {
		List<WebElement> elmn = getElements(elementName);
		actualText2 = elmn.get(index).getText().trim();
	}

	public static void clickByIterantion(String elementName,
			String expectedText, String description) throws Exception {
		List<WebElement> elmn = getElements(elementName);
		for (WebElement webElement : elmn) {
			if (webElement.getText().contains(expectedText)) {
				webElement.click();
				HtmlReporter.WriteStep(description, "Select value is==>"
						+ expectedText + " ", "Selected value as==>"
						+ expectedText + "", true);
			}
		}

	}

	public static void getTextByIteration(String elementName,
			String expectedText, String description) throws Exception {
		List<WebElement> elmn = getElements(elementName);
		// actualText1 = elmn.get(index).getText();
		for (WebElement webElement : elmn) {
			if (webElement.getText().contains(expectedText)){
				HtmlReporter.WriteStep(description, "Select value is==>"
						+ expectedText + " ", "Select value is==>"
						+ expectedText + " ", true);
				break;
			} else {
				HtmlReporter.WriteStep(description, "Select value is==>"
						+ expectedText + " ", "Select value is==>"
						+ expectedText + " ", false);
			}
		}

	}
	
	public static void getTextByIteration_ConditionUnmatched(
			String elementName, String expectedText, String description)
			throws Exception {
		List<WebElement> elmn = getElements(elementName);
		// actualText1 = elmn.get(index).getText();
		for (WebElement webElement : elmn) {
			if (webElement.getText().contains(expectedText)) {
				HtmlReporter.WriteStep(description, "Select value is==>"
						+ expectedText + " ", "Select value is==>"
						+ expectedText + " ", true);
				break;
			} else {
				HtmlReporter.WriteStep(description, "Select value is==>"
						+ expectedText + " ", "Select value is==>"
						+ expectedText + " ", false);
			}
		}
	}
	
	public static void getTextByIteration_ConditionUnmatched1(
			String elementName, String expectedText, String description)
			throws Exception {
		List<WebElement> elmn = getElements(elementName);
		// actualText1 = elmn.get(index).getText();
		for (WebElement webElement : elmn) {
			if (webElement.getText().contains(expectedText)) {
				HtmlReporter.WriteStep(description, "Select value is==>"
						+ expectedText + " ", "Select value is==>"
						+ expectedText + " ", false);
			} else {
				HtmlReporter.WriteStep(description, "Select value is==>"
						+ expectedText + " ", "Select value is==>"
						+ expectedText + " ", true);
			}
		}
	}

	public static void getTextByIteration_1(String elementName,
			String description) throws Exception {
		List<WebElement> elmn = getElements(elementName);
		// actualText1 = elmn.get(index).getText();
		for (WebElement webElement : elmn) {

			HtmlReporter.WriteStep(description, webElement.getText(),
					webElement.getText(), true);
		}
	}

	public static void clearMethod(String elementName) throws Exception {
		WebDr.waitForElement(elementName);
		WebElement elmn = getElement(elementName);
		elmn.clear();
	}
	
	public static void clearMethod_BNC(String elementName) throws Exception {
		try{
			if (WebDr.isElementExist("Close", "Check element existence"))
				WebDr.clickwithmouse_BNC("Close", "Close Screen");
				
			WebDr.waitForElement(elementName);
			WebElement elmn = getElement(elementName);
			elmn.clear();
		}catch(Exception err){
			err.printStackTrace();
		}
	}

	public static void clickBasedOnList(String elementName, int index,
			String description) throws Exception {
		List<WebElement> elmn = getElements(elementName);

		if (elmn.size() > 0) {
			elmn.get(index).click();
			HtmlReporter.WriteStep(description, "Select option as==>"
					+ elmn.get(index).getText() + "", "Selected option as==>"
					+ elmn.get(index).getText() + " ", true);
		} else {
			HtmlReporter.WriteStep("Object not visible - " + description,
					"Click", "Object not visible - ", false);
		}
	}

	public static void clickBasedOnList1(String elementName, String textValue,
			String description) throws Exception {
		List<WebElement> elmn = getElements(elementName);

		for (WebElement webElement : elmn) {
			if (webElement.getText().equalsIgnoreCase(textValue)) {
				webElement.click();
				/*
				 * HtmlReporter.WriteStep(description, "Click option as==>" +
				 * textValue + " from LHS menu", "Clicked option as==>" +
				 * textValue + " from LHS menu", true);
				 */
			} else {
				/*
				 * HtmlReporter.WriteStep("Object not visible - " + description,
				 * "Click webElement", "Object not visible - ", true);
				 */
			}
		}
	}

	public static void actionClass(String elementName) throws Exception {

		Actions action = new Actions(driver);

		WebElement elmn = getElement(elementName);

		action.moveToElement(elmn).build().perform();
	}

	
	public static void StaleElementHandleByID(String elementID) throws InterruptedException {
		int count = 0;
		while (count < 4) {
			try {
				WebElement elmn = getElement(elementID);
				elmn.click();
			} catch (StaleElementReferenceException e) {
				e.toString();
				System.out.println("Trying to recover from a stale element :"
						+ e.getMessage());
				count = count + 1;
			}
			count = count + 4;
		}
	}

	public static void enterTextBoxValueBasedOnList(String elementName,
			int index, String textBoxName, String textBoxValue,
			String description) throws Exception {
		List<WebElement> elmn = getElements(elementName);

		if (elmn.size() > 0) {
			elmn.get(index).sendKeys(textBoxValue);
			HtmlReporter.WriteStep(description, "Enter text box value as==>"
					+ textBoxValue + " in the text box==>" + textBoxName + "",
					"Entered text box value as==>" + textBoxValue
							+ " in the text box==>" + textBoxName + "", true);
		} else {
			HtmlReporter.WriteStep("Object not visible - " + description,
					"Enter Text", "Object not visible - ", false);
		}
	}
	
	public static void waitForNumberOfWindowsToEqual(final int numberOfWindows) {
		new WebDriverWait(driver, 60) {
		}.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return (driver.getWindowHandles().size() == numberOfWindows);
			}
		});
	}

	public static void handleMultipleWindows() throws InterruptedException {
		Thread.sleep(8000);
		for (String winHandle : driver.getWindowHandles()) {
			System.out.println(winHandle);
			driver.switchTo().window(winHandle);
		}

	}

	public static void handleParentWindow() {
		String getWindowId = driver.getWindowHandle();
		parentWindowID = getWindowId;
		System.out.println("Parent Window IDS=========>" + parentWindowID);
	}

	public static void swtichOnParentWindow() throws InterruptedException {
		Thread.sleep(4000);
		System.out
				.println("Switch on parent window=========>" + parentWindowID);
		driver.switchTo().window(parentWindowID);
	}

	public static void waitForElement(String elementName) throws InterruptedException {
		try{
			WebElement elmn = getElement(elementName);
			WebDriverWait wait = new WebDriverWait(driver, 50);
			WebElement element = wait.until(ExpectedConditions.visibilityOf(elmn));
			element.getSize();
			System.out.println(elementName + " size------" + element.getSize());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void waitForElementToBeAvailable(String elementName, int timeout) throws Exception {
		try{
			WebElement elmn = getElement(elementName);
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			WebElement element = wait.until(ExpectedConditions.visibilityOf(elmn));
			element.getSize();
			System.out.println( elementName + " webelement is loaded successfully " + element.getSize());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void waitForPageLoaded() throws Exception {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver)
						.executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(expectation);
		} catch (Throwable error) {

		}
	}
	
	
	public static boolean waitForPageLoaded_Long(int Time) throws Exception {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver)
						.executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, Time);
			wait.until(expectation);
		} catch (Throwable error) {

		}
		return readyState;
	}
	
	
	public static void waitForPageLoad() throws Exception {
	    WebDriverWait wait = new WebDriverWait(driver, 60);

	    Predicate<WebDriver> pageLoaded = new Predicate<WebDriver>() {

	        @Override
	        public boolean apply(WebDriver input) {
	            return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
	        }

	    };
	    wait.until(pageLoaded);
	}

	public static void getTextOnAlertPopup(String description) throws Exception {
		String actualText = driver.switchTo().alert().getText();
		if (! actualText.isEmpty()) {
			HtmlReporter.WriteStep(description, "Get text value from popup",
					"Actual popup text value is==>" + actualText, true);
		} else {
			HtmlReporter.WriteStep(description, "Get text value from popup",
					"Actual popup text value is blank" + actualText, false);
		}
	}
	
	public static String returnTextOnAlertPopup(String description) throws Exception {
		try{
			WebDriverWait wait1=new WebDriverWait(driver,30);
			wait1.until(ExpectedConditions.alertIsPresent());
			String actualText = driver.switchTo().alert().getText();
			if (! actualText.isEmpty()) {
				HtmlReporter.WriteStep(description, "Get text value from popup",
						"Actual popup text value is==>" + actualText, true);
				System.out.println(actualText);
				return actualText;
			} else {
				HtmlReporter.WriteStep(description, "Get text value from popup",
						"Actual popup text value is blank" + actualText, false);
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	

	
	public static void verifyTextOnAlertPopup(String elementName, int index,
			String expectedText, String description) throws Exception {
		String actualText = driver.switchTo().alert().getText();
		if (actualText.equalsIgnoreCase(expectedText)) {
			HtmlReporter.WriteStep(description, "Expected get text value is==>"
					+ expectedText + " is displayed as per expected outcome",
					"Actual get text value as==>" + actualText
							+ " should display as per requirment", true);
		} else {
			HtmlReporter.WriteStep(description, "Expected get text value is==>"
					+ expectedText + " is displayed as per expected outcome",
					"Actual get text value as==>" + actualText
							+ " should display as per requirment", false);
		}
	}

	public static void doubleClick(String elementName) throws InterruptedException {
		WebElement elmn = getElement(elementName);
		Actions action = new Actions(driver);
		action.moveToElement(elmn).doubleClick().build().perform();
	}

	public static void checkPagination(String elementName, String element,
			String description) throws Exception {
		List<WebElement> elmn = getElements(elementName);
		List<WebElement> elmn1 = getElements(elementName);
		if (elmn.size() >= 10 && elmn1.size() > 1) {
			HtmlReporter
					.WriteStep(
							description,
							"Should see the View history details with all transaction If more than 10 transaction",
							"Able to see the View history details with all transaction If more than 10 transaction as per expected outcome",
							true);
			

		} else {
			HtmlReporter
					.WriteStep(
							description,
							"Should not see the View history details with all transaction If more than 10 transaction",
							"Able to see the View history details with all transaction If more than 10 transaction as per expected outcome",
							false);
	
		}
	}

	public static void byDefaultSelectedValue(String elementName)
			throws Exception {
		WebElement elmn = getElement(elementName);
		Select mySelect = new Select(elmn);
		WebElement option = mySelect.getFirstSelectedOption();
		getFirstSelectedOption = option.getText();
	}

	public static void optionSizeInDDL(String elementName, String description,
			String expectedOutcome, String actualOutcome) throws Exception {
		List<WebElement> optionsValue = getElements(elementName);
		if (optionsValue.size() >= 1) {
			HtmlReporter.WriteStep(description, expectedOutcome, actualOutcome,
					true);
		} else {
			HtmlReporter.WriteStep(description,
					"Test cases out put not matched as per the exected",
					"Test cases out put not matched as per the exected", false);
		}
	}

	public static void getTextBoxValueSize(String elementName,
			int expectedLength, String description) throws Exception {
		WebElement elmn = getElement(elementName);

		getActualTextBoxLength = elmn.getAttribute("value").length();

		if (getActualTextBoxLength == expectedLength) {
			HtmlReporter.WriteStep(description, "Field size value "
					+ expectedLength + " characters", "Field size value "
					+ expectedLength + " characters", true);

		} else {
			HtmlReporter.WriteStep(description, "Field size value "
					+ expectedLength + " characters", "Field accepts"
					+ getActualTextBoxLength + " characters", false);

		}
	}
	
	public static String getTextBoxValue(String elementName, String description)
			throws Exception {
		WebElement elmn = getElement(elementName);
		getActualTextBoxValue = elmn.getAttribute("value").length();
		if (getActualTextBoxValue == 0){
			Thread.sleep(10000);
			getActualTextBoxValue = elmn.getAttribute("value").length();
		}
		if (getActualTextBoxValue > 0) {
			HtmlReporter.WriteStep(description, "Value should be populated", "Value is displayed successfully as " + elmn.getAttribute("value"), true);
		} else {
			HtmlReporter.WriteStep(description, "Value should be populated", "Value is not displayed successfully. Actual value is " + elmn.getAttribute("value"), false);
		}
		return elmn.getAttribute("value");
	}
	
	public static  void  setTextIfFieldEmpty(String elementName, String textToSet,  String description)
			throws Exception {
		
		WebElement elmn = getElement(elementName);
		int getFieldValue = elmn.getAttribute("value").length();
		 
		if ((elmn.isEnabled()) && (getFieldValue == 0)){
			Thread.sleep(5000);
			WebDr.setText(elementName, textToSet, description);
			HtmlReporter.WriteStep(description, "Field is Empty","Entered - "
						+ textToSet , true);
			getFieldValue = elmn.getAttribute("value").length();
		}
		else if ((elmn.isEnabled()) && (getFieldValue > 0)) {
			HtmlReporter.WriteStep(description, "Field is  Not Empty","Text Not entered" , true);
		} else {
			HtmlReporter.WriteStep(description, "Field is  disabled","Text Not entered" , false);
		}
	}
	
	public static String getCellTextBoxValue(String elementName, String description)
			throws Exception {
		String strCellVal = null;
		WebElement elmn = getElement(elementName);
		getActualTextBoxValue = elmn.getText().length();
		if (getActualTextBoxValue == 0){
			Thread.sleep(10000);
			getActualTextBoxValue = elmn.getText().length();
			//strCellVal = elmn.getText();
		}
		else if (getActualTextBoxValue > 0) {
			
			strCellVal = elmn.getText();
			HtmlReporter.WriteStep(description, "Value should be populated", "Value is displayed successfully as " + strCellVal, true);
		} else {
			HtmlReporter.WriteStep(description, "Value should be populated", "Value is not displayed successfully. Actual value is " + strCellVal, false);
		}
		return strCellVal;
	}

	public static void getTextBoxListValue(String elementName, int index,
			String expectedText, String description) throws Exception {
		List<WebElement> optionsValue = getElements(elementName);

		String getValue = optionsValue.get(index).getAttribute("value");

		if (getValue.equalsIgnoreCase(expectedText)) {
			HtmlReporter.WriteStep(description, "Actual get Text Box value==>"
					+ getValue + "", "Expected get Text Box value==>"
					+ expectedText + "", true);
		} else {
			HtmlReporter.WriteStep(description, "Actual get Text Box value==>"
					+ getValue + "", "Expected get Text Box value==>"
					+ expectedText + "", false);
		}
	}

	public static void getTextBoxValueGraterThanSize(String elementName,
			int expectedLength, String description) throws Exception {
		WebElement elmn = getElement(elementName);

		getActualTextBoxLength = elmn.getAttribute("value").length();

		if (getActualTextBoxLength < expectedLength) {
			HtmlReporter.WriteStep(description, "Field size value "
					+ expectedLength + " characters", "Field accepts"
					+ getActualTextBoxLength + " characters", false);
	
		} else {
			HtmlReporter.WriteStep(description, "Field size value "
					+ expectedLength + " characters", "Field accepts"
					+ getActualTextBoxLength + " characters", true);
		
		}
	}
	
	

	public static void getTextBoxValue(String elementName, int expectedValue)
			throws Exception {
		WebElement elmn = getElement(elementName);

		getActualTextBoxValue = elmn.getAttribute("value").length();
	}

	public static void isExist(String elementName, String description)
			throws Exception {
		Thread.sleep(5000);
		List<WebElement> elmn = getElements(elementName);

		if (elmn.size() > 0) {
			HtmlReporter.WriteStep(description, "Exists", "Exists", false);
		} else {
			HtmlReporter.WriteStep(description, "Should not exists",
					"Does not exists", true);
		}

	}

	public static void verifyFieldEditable(String elementName,
			String expectedLength, String description) throws Exception {
		WebElement elmn = getElement(elementName);

		String getAttributeValue = elmn.getAttribute("readonly");

		if (getAttributeValue.equalsIgnoreCase(expectedLength)) {
			HtmlReporter.WriteStep(description,
					"field is an un editable field",
					"field is an un editable field", true);
		} else {
			HtmlReporter.WriteStep(description,
					"field is not an un editable field",
					"field is not an un editable field", false);
		}
	}
	
//	public static void authWithComment() throws InterruptedException, AWTException, NoAlertPresentException, TimeoutException, Exception{
//		//driver.switchTo().alert().accept();
//		boolean blnrepeat = true;
//		try{
////			while (WebDr.isAuthoriseAlertPresent() == true){
////			while (WebDr.SwitchToLatestAlertCheck("Authorisation required")){
//			while ((WebDr.waitElementImageSikuli("AuthoriseWindow.PNG", 5, "Verify authorization window")==true) && blnrepeat){
//			
//				if (WebDr.isAlertPresent()){
//					System.out.println("clicked on accept in the if loop");
//					WebDr.alertAccept();
//				}
//				
//				Thread.sleep(2000);
//				//put code here
//					try{
//						WebDr.clickElementImageSikuli("NoSuperOKButton.PNG", 5, "Click No Supervisor Logged In");
//					}catch(Exception e){
//						e.printStackTrace();
//					}
//				
//					//WebDr.SwitchToLatestWindow();//--added for NBC loan scenario 1413
//					WebDr.SwitchToLatestWindow();
//					WebDr.waitForPageLoaded();
//	//				r.keyPress(KeyEvent.VK_TAB);
//	//				r.keyRelease(KeyEvent.VK_TAB);
//					WebDr.rTab();
//					
//					Thread.sleep(1000);
//					WebDr.clickElementImageSikuli("LocalClick.png", 5, "Click on Local Radio Button" );
//					
//	//				r.keyPress(KeyEvent.VK_LEFT);
//	//				r.keyRelease(KeyEvent.VK_LEFT);
//					//WebDr.rLeft();
//					
//					
//	//				WebDr.clickElementImageSikuli("LocalRadioAuth.PNG", 5, "Click on radio button");
//					
//					Thread.sleep(1000);
//	//				keyboard("SVICKY");
//					keyboard(WebDr.getValue("S_Username"));
//					r.keyPress(KeyEvent.VK_TAB);
//					r.keyRelease(KeyEvent.VK_TAB);
//					
//					Thread.sleep(1000);
//	//				keyboard("Quality10");
//					keyboard(WebDr.getValue("S_Password"));
//					r.keyPress(KeyEvent.VK_TAB);
//					r.keyRelease(KeyEvent.VK_TAB);
//					// r.mousePress(InputEvent.BUTTON1_MASK);
//					Thread.sleep(1000);
//					// keyboard("N");
//					
//					r.keyPress(KeyEvent.VK_TAB);
//					r.keyRelease(KeyEvent.VK_TAB);
//					
//	//				keyboard("Quality10");
//					keyboard(WebDr.getValue("AuthoriseComment"));
//					Thread.sleep(1000);
//					
//					r.keyPress(KeyEvent.VK_TAB);
//					r.keyRelease(KeyEvent.VK_TAB);
//					
//					r.keyPress(KeyEvent.VK_ENTER);
//					r.keyRelease(KeyEvent.VK_ENTER);		
//					
//	//				WebDr.alertClickWithText("kool");
//					if (WebDr.isAuthoriseAlertPresent())
//						blnrepeat = true;
//					else
//						blnrepeat = false;
//				}
//			}catch(Exception e1){
//				e1.printStackTrace();
//			}
//	}
	
	public static void authWithComment() throws Exception{
		//driver.switchTo().alert().accept();
		while (WebDr.isAuthoriseAlertPresent()){
			//WebDr.alertAccept();
			
			if (WebDr.strFCRCountry.contains("BBK")){
				WebDr.alertClickWithText("Rollback");
			}
			
			Thread.sleep(2000);
			//put code here
			try{
				WebDr.clickElementImageSikuli("NoSuperOKButton.PNG", 5, "Click No Supervisor Logged In");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				//WebDr.SwitchToLatestWindow();//--added for NBC loan scenario 1413
				WebDr.clickElementImageSikuli("LocalRadioAuth.PNG", 5, "Click on radio button");
			/*	r.keyPress(KeyEvent.VK_TAB);
				r.keyRelease(KeyEvent.VK_TAB);
				
				r.keyPress(KeyEvent.VK_LEFT);
				r.keyRelease(KeyEvent.VK_LEFT);*/
				
				Thread.sleep(1000);
//				keyboard("SVICKY");
				keyboard(WebDr.getValue("S_Username"));
				r.keyPress(KeyEvent.VK_TAB);
				r.keyRelease(KeyEvent.VK_TAB);
				
				Thread.sleep(1000);
//				keyboard("Quality10");
				keyboard(WebDr.getValue("S_Password"));
				r.keyPress(KeyEvent.VK_TAB);
				r.keyRelease(KeyEvent.VK_TAB);
				// r.mousePress(InputEvent.BUTTON1_MASK);
				Thread.sleep(1000);
				// keyboard("N");
				
				if (! WebDr.strFCRCountry.contains("BBK")){
					r.keyPress(KeyEvent.VK_TAB);
					r.keyRelease(KeyEvent.VK_TAB);
				}
				
//				keyboard("Quality10");
				keyboard(WebDr.getValue("AuthoriseComment"));
				Thread.sleep(1000);
				
				r.keyPress(KeyEvent.VK_TAB);
				r.keyRelease(KeyEvent.VK_TAB);
				
				r.keyPress(KeyEvent.VK_ENTER);
				r.keyRelease(KeyEvent.VK_ENTER);		
				
				WebDr.alertClickWithText("kool");
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
	}
	
	
	
	public static void authWithAlertComment() throws InterruptedException, AWTException, NoAlertPresentException, TimeoutException{
		//driver.switchTo().alert().accept();
		while (WebDr.isAuthoriseAlertPresent()){
			//WebDr.alertAccept();
			Thread.sleep(2000);
			
			//put code here
			try{
				WebDr.clickElementImageSikuli("NoSuperOKButton.PNG", 5, "Click No Supervisor Logged In");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				//WebDr.SwitchToLatestWindow();//--added for NBC loan scenario 1413
				WebDr.clickElementImageSikuli("LocalRadioAuth.PNG", 5, "Click on radio button");
			/*	r.keyPress(KeyEvent.VK_TAB);
				r.keyRelease(KeyEvent.VK_TAB);
				
				r.keyPress(KeyEvent.VK_LEFT);
				r.keyRelease(KeyEvent.VK_LEFT);*/
				
				Thread.sleep(1000);
//				keyboard("SVICKY");
				keyboard(WebDr.getValue("S_Username"));
				r.keyPress(KeyEvent.VK_TAB);
				r.keyRelease(KeyEvent.VK_TAB);
				
				Thread.sleep(1000);
//				keyboard("Quality10");
				keyboard(WebDr.getValue("S_Password"));
				r.keyPress(KeyEvent.VK_TAB);
				r.keyRelease(KeyEvent.VK_TAB);
				// r.mousePress(InputEvent.BUTTON1_MASK);
				Thread.sleep(1000);
				// keyboard("N");
				
				r.keyPress(KeyEvent.VK_TAB);
				
				r.keyRelease(KeyEvent.VK_TAB);
				
//				keyboard("Quality10");
				keyboard(WebDr.getValue("AuthoriseComment"));
				Thread.sleep(1000);
				
				r.keyPress(KeyEvent.VK_TAB);
				r.keyRelease(KeyEvent.VK_TAB);
				
				r.keyPress(KeyEvent.VK_ENTER);
				r.keyRelease(KeyEvent.VK_ENTER);		
				
				//WebDr.alertClickWithText("Account:");
				//WebDr.clickElementImageSikuli("AccountNoOk.PNG", 5, "Click on Account No Button");

			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
	}
	
	public static void authWithCommentOnlyOnce() throws InterruptedException, AWTException, NoAlertPresentException, TimeoutException, Exception{
		//driver.switchTo().alert().accept();
		try{
			if (WebDr.isAuthoriseAlertPresent()){
			//WebDr.alertAccept();
			Thread.sleep(2000);
			if (WebDr.strFCRCountry.contains("BBK")){
				WebDr.alertClickWithText("Rollback");
			}
			//put code here
			try{
				WebDr.clickElementImageSikuli("NoSuperOKButton.PNG", 5, "Click No Supervisor Logged In");
			}catch(Exception e){
				e.printStackTrace();
			}
			
				//WebDr.SwitchToLatestWindow();//--added for NBC loan scenario 1413
				WebDr.SwitchToLatestWindow();
				WebDr.waitForPageLoaded();
//				r.keyPress(KeyEvent.VK_TAB);
//				r.keyRelease(KeyEvent.VK_TAB);
				WebDr.rTab();
				
				Thread.sleep(1000);
				WebDr.clickElementImageSikuli("LocalRadioAuth.png", 5, "Click on Local Radio Button" );
				
//				r.keyPress(KeyEvent.VK_LEFT);
//				r.keyRelease(KeyEvent.VK_LEFT);
				//WebDr.rLeft();
				
				
//				WebDr.clickElementImageSikuli("LocalRadioAuth.PNG", 5, "Click on radio button");
				
				Thread.sleep(1000);
//				keyboard("SVICKY");
				keyboard(WebDr.getValue("S_Username"));
				r.keyPress(KeyEvent.VK_TAB);
				r.keyRelease(KeyEvent.VK_TAB);
				
				Thread.sleep(1000);
//				keyboard("Quality10");
				keyboard(WebDr.getValue("S_Password"));
				r.keyPress(KeyEvent.VK_TAB);
				r.keyRelease(KeyEvent.VK_TAB);
				// r.mousePress(InputEvent.BUTTON1_MASK);
				Thread.sleep(1000);
				// keyboard("N");
				
				if (! WebDr.strFCRCountry.contains("BBK")){
					r.keyPress(KeyEvent.VK_TAB);
					r.keyRelease(KeyEvent.VK_TAB);
				}
//				keyboard("Quality10");
				keyboard(WebDr.getValue("AuthoriseComment"));
				Thread.sleep(1000);
				
				r.keyPress(KeyEvent.VK_TAB);
				r.keyRelease(KeyEvent.VK_TAB);
				
				r.keyPress(KeyEvent.VK_ENTER);
				r.keyRelease(KeyEvent.VK_ENTER);		
				
				WebDr.alertClickWithText("kool");
			}
		}catch(Exception e1){
			e1.printStackTrace();
		}	
	}
	
	public static void clickElementImageSikuli(String elementImageName, int elementWaitTime, String description) throws Exception{
		try{
			Screen objscreen = new Screen();
			Pattern sikuliElement = new Pattern(Constant.SikuliScreenPath + elementImageName).similar((float) 0.6);
			objscreen.wait(sikuliElement,elementWaitTime);
			if (objscreen.exists(sikuliElement)!=null){
				objscreen.click(sikuliElement);
				System.out.println("clicked using sikuli");
				HtmlReporter.WriteStep(description, "Click element image -" + elementImageName, "Clicked element successfully", true);
				Thread.sleep(1000);
			}else{
				HtmlReporter.WriteStep(description, "Optional - Click element image -" + elementImageName, "No such element to click", true);
			}
		}catch(Exception sikulierror){
			sikulierror.printStackTrace();
			System.out.println("unable to click using sikuli");
		}
	}
	
	public static void clickElementImageSikuli_BNC(String elementImageName, int elementWaitTime, String description) throws Exception{
		try{
			Screen objscreen = new Screen();
			Pattern sikuliElement = new Pattern(Constant.SikuliScreenPath + elementImageName).similar((float) 0.6);
			objscreen.wait(sikuliElement,elementWaitTime);
			if (objscreen.exists(sikuliElement)!=null){
				objscreen.click(sikuliElement);
				System.out.println("clicked using sikuli");
				//HtmlReporter.WriteStep(description, "Click element image -" + elementImageName, "Clicked element successfully", true);
				Thread.sleep(1000);
			}
		}catch(Exception sikulierror){
			sikulierror.printStackTrace();
			System.out.println("unable to click using sikuli");
		}
	}
	
	public static boolean clickAlwaysImageSikuli(String elementImageName, int elementWaitTime, String description) throws Exception{
		try{
			Screen objscreen = new Screen();
			Pattern sikuliElement = new Pattern(Constant.SikuliScreenPath + elementImageName).similar((float) 0.6);
			objscreen.wait(sikuliElement,elementWaitTime);
			if (objscreen.exists(sikuliElement)!=null){
				objscreen.click(sikuliElement);
				System.out.println("clicked using sikuli");
				HtmlReporter.WriteStep(description, "Click element image -" + elementImageName, "Clicked element successfully", true);
				Thread.sleep(1000);
			}
		}catch(Exception sikulierror){
			sikulierror.printStackTrace();
			System.out.println("unable to click using sikuli");
			return false;
		}
		return true;
	}
	
	public static boolean clickAlwaysPartialImageSikuli(String elementImageName, int elementWaitTime,double similar, String description) throws Exception{
		try{
			Screen objscreen = new Screen();
			Pattern sikuliElement = new Pattern(Constant.SikuliScreenPath + elementImageName).similar((float) similar);
			objscreen.wait(sikuliElement,elementWaitTime);
			if (objscreen.exists(sikuliElement)!=null){
				objscreen.click(sikuliElement);
				System.out.println("clicked using sikuli");
				HtmlReporter.WriteStep(description, "Click element image -" + elementImageName, "Clicked element successfully", true);
				Thread.sleep(1000);
			}
		}catch(Exception sikulierror){
			sikulierror.printStackTrace();
			System.out.println("unable to click using sikuli");
			return false;
		}
		return true;
	}
	
	public static boolean clickAlwaysImageIndexSikuli(String elementImageName, int elementWaitTime, int index, String description) throws Exception{
		try{
			int i = 0;
			Screen objscreen = new Screen();
			Pattern sikuliElement = new Pattern(Constant.SikuliScreenPath + elementImageName).similar((float) 0.6);
			Iterator<Match> itr = objscreen.findAll(sikuliElement);
			Object lastElement = itr.next();
			
			while(itr.hasNext()){
				lastElement = itr.next();
				i=i+1;
			}
			
			System.out.println("size............................" + i);
			
			objscreen.wait(lastElement,elementWaitTime);
			if (objscreen.exists(lastElement)!=null){
				objscreen.click(lastElement);
				System.out.println("clicked using sikuli");
				HtmlReporter.WriteStep(description, "Click element image -" + elementImageName, "Clicked element successfully", true);
				Thread.sleep(1000);
			}
		}catch(Exception sikulierror){
			sikulierror.printStackTrace();
			System.out.println("unable to click using sikuli");
			return false;
		}
		return true;
	}
	
	public static boolean waitElementImageSikuli(String elementImageName, int elementWaitTime, String description) throws Exception{
		boolean result=false; 
		try{
			Screen objscreen = new Screen();
			Pattern sikuliElement = new Pattern(Constant.SikuliScreenPath + elementImageName).similar((float) 0.6);
			objscreen.wait(sikuliElement,elementWaitTime);
			if (objscreen.exists(sikuliElement)!=null){
				System.out.println("wait using sikuli");
//				HtmlReporter.WriteStep(description, "Click element image -" + elementImageName, "Clicked element successfully", true);
				Thread.sleep(1000);
				result = true;
			}
		}catch(Exception sikulierror){
			sikulierror.printStackTrace();
			System.out.println("unable to get element using sikuli");
			return result;
		}
		return result;
	}
		
	
	
	public static void authOnlyCreds() throws AWTException, InterruptedException, NoAlertPresentException, TimeoutException{
		//Thread.sleep(5000);
		//driver.switchTo().alert().accept();
		WebDr.alertAccept();
		SwitchToLatestWindow();
//		keyboard("SVICKY");
		keyboard(WebDr.getValue("S_Username"));
		Thread.sleep(2000);
		rTab();
		//Thread.sleep(5000);
		keyboard(WebDr.getValue("S_Password"));
		Thread.sleep(2000);
		//Thread.sleep(5000);
		rTab();
		Thread.sleep(2000);
		rEnter();
		//Thread.sleep(2000);
	}
	
	public static boolean alertClickWithText(String strAlertText) throws Exception{
		try{
			WebDriverWait objWait = new WebDriverWait(Driver.driver, 10);
			objWait.until(ExpectedConditions.alertIsPresent());
			Driver.driver.switchTo().alert();
			String strText = Driver.driver.switchTo().alert().getText();
			if (strText.contains(strAlertText))
				Driver.driver.switchTo().alert().accept();
			else
				return false;
			}catch(Exception ex){
				System.out.println("No such alert window with text " + strAlertText);
				return false;
			}
		return true;
	}
	
	public static boolean VerifyStatusBarText(String elementName,int intWaitTime,String strOutputText, String description) throws Exception{
		try{
			boolean blnResult;
			String msg="";
			WebElement elmn = getElement(elementName);
			WebDriverWait objWait = new WebDriverWait(Driver.driver, intWaitTime);
			blnResult = objWait.until(ExpectedConditions.textToBePresentInElementValue(elmn, strOutputText));
			msg =  elmn.getAttribute("value");					
			if (msg.contains(strOutputText)){
				HtmlReporter.WriteStep(description, "Status bar with message should be displayed", "Status bar is displayed with message - " + msg, true);
				return true;
			}
			else{
				HtmlReporter.WriteStep(description, "Status bar with message should be displayed", "Status bar is not displayed with message - " + msg, false);
				return false;
			}
			}catch(Exception ex){
				System.out.println("No such result  with text " + strOutputText);
				return false;
			}
	}
	
	public static boolean alertHandlingForErrors(String strAlertText, String description) throws Exception{
		
			WebDriverWait objWait = new WebDriverWait(Driver.driver, 30);
			objWait.until(ExpectedConditions.alertIsPresent());
			try{
				String strText = Driver.driver.switchTo().alert().getText();
				System.out.println(strText);
				if (strText.contains(strAlertText)){
					Driver.driver.switchTo().alert().accept();
					//HtmlReporter.WriteStep(description, "Alert window with message should be displayed", "Clicked alert successfully with message - " + strText, true);
					return true;
				}
				else{				
					Driver.driver.switchTo().alert().accept();
					HtmlReporter.WriteStep(description, "Alert window with message should be displayed", "Unexpected alert is displayed with message - " + strText, false);
					return false;
				}
			}catch(Exception ex){
				ex.printStackTrace();
				HtmlReporter.WriteStep(description, "Alert window with message should be displayed", "No alert is displayed", false);
				return false;
			}
	}
	
	public static boolean multiplealertHandlingForErrors(String strAlertText, String description) throws Exception{
		
		WebDriverWait objWait = new WebDriverWait(Driver.driver, 20);
		objWait.until(ExpectedConditions.alertIsPresent());
		try{
			String strText = Driver.driver.switchTo().alert().getText();
			if (strText.contains(strAlertText)){
				Driver.driver.switchTo().alert().accept();
				HtmlReporter.WriteStep(description, "Alert window with message should be displayed", "Clicked alert successfully with message - " + strText, true);
				return true;
				
			}
			else{				
				Driver.driver.switchTo().alert().accept();
				HtmlReporter.WriteStep(description, "Alert window with message should be displayed", "Unexpected alert is displayed with message - " + strText, false);
				return false;
			}
		}catch(UnhandledAlertException alerterror){
			HtmlReporter.WriteStep(description, "Alert window with message should be displayed", "Alert is displayed " + alerterror.getMessage(), true);
			return true;
		}catch(Exception ex){
			HtmlReporter.WriteStep(description, "Alert window with message should be displayed", "No alert is displayed", false);
			return false;
		}
	}
	
	
	public static void rClearSelSendkeys(String elementName ,String Value) throws AWTException, InterruptedException{
		Thread.sleep(1000);
		WebElement elmn = getElement(elementName);
		elmn.sendKeys("");
		//driver.findElement(By.name(string.split("\\/")[0])).sendKeys("");
		Thread.sleep(2000);
		try{
		r.keyPress(KeyEvent.VK_SHIFT);
		r.keyPress(KeyEvent.VK_HOME);
		Thread.sleep(2000);
		r.keyRelease(KeyEvent.VK_HOME);
		r.keyRelease(KeyEvent.VK_SHIFT);
		r.keyPress(KeyEvent.VK_DELETE);
		Thread.sleep(1000);
		r.keyRelease(KeyEvent.VK_DELETE);
		//driver.findElement(By.name(string.split("\\/")[0])).sendKeys(string.split("\\/")[1]);
		elmn.sendKeys(Value);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void rClearSendkeys(String string) throws AWTException{
		r.keyPress(KeyEvent.VK_SHIFT);
		r.keyPress(KeyEvent.VK_HOME);
		r.keyRelease(KeyEvent.VK_SHIFT);
		r.keyRelease(KeyEvent.VK_HOME);
		r.keyPress(KeyEvent.VK_DELETE);
		keyboard(string);
	}
	
	public static void rClearUpdateTextField(String elementName ,String value, String description) throws Exception{
		try{
		Thread.sleep(1000);
		WebElement elmn = getElement(elementName);
		 //select the value which is typed in FirstName field
		Actions objAct = new Actions(Driver.driver);
		objAct.click(elmn)
			.sendKeys(Keys.END)
			.keyDown(Keys.SHIFT)
			.sendKeys(Keys.HOME)
			.keyUp(Keys.SHIFT)
			.sendKeys(Keys.DELETE)
			.perform();
		elmn.sendKeys(value);
		WebDr.rTab();
		if ((elmn.getAttribute("value").replaceAll(",", "")).contains(value))
			HtmlReporter.WriteStep(description,"Enter the text in the field - " + value,"Field is updated with value " + value, true);
		else
			HtmlReporter.WriteStep(description,"Enter the text in the field - " + value,"Field is not updated with value " + value, false);
		}catch(Exception e){
			e.printStackTrace();
			HtmlReporter.WriteStep(description,"Enter the text in the field - " + value,"Field is not updated with value -"+ value+" due to " + e.toString() , false);
		}
	}
	
	public static void DenominationEntry(String description) throws Exception{	
        String strDenomXPath = WebDr.page_Objects.get("Denomination");
        for (String key : Driver.dictionary.keySet()) {
            System.out.println("Key = " + key);
            String val = Driver.dictionary.get(key);
            if ((key.contains("DENOM_") && (!val.isEmpty()))){
            	String strkey = key.replaceAll("DENOM_", "");            	
            	strkey = strkey.replaceAll("_", " ");
            	String strCountXPath = strDenomXPath.replaceFirst("UPDATEDENOMINATION", strkey);
				try {
					//WebDr.rClearSelSendkeys(strCountXPath, val);
					String[] arrXPath = strCountXPath.split("\\|");
					if(arrXPath.length==3){
						frame(arrXPath[2]);
					}	
						Actions objAct = new Actions(Driver.driver);
						objAct.click(Driver.driver.findElement(By.xpath(arrXPath[1])))
							.sendKeys(Keys.END)
							.keyDown(Keys.SHIFT)
							.sendKeys(Keys.HOME)
							.keyUp(Keys.SHIFT)
							.sendKeys(Keys.DELETE)
							.perform();
						Driver.driver.findElement(By.xpath(arrXPath[1])).sendKeys(val);
						WebDr.rTab();
						HtmlReporter.WriteStep(description,"Enter the denomination  in the field - " + strkey,"Field is updated with value " + val, true);		
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					HtmlReporter.WriteStep(description,"Enter the denomination  in the field - " + strkey,"Field is updated with value " + val, false);
				}
            }
        }          
	}
	
	public static void keyboard(String word) throws AWTException {
		/*for (int i = 0; i < word.split("(?<!^)").length; i++) {
			r.keyPress((int) word.charAt(i));
			r.keyRelease((int) word.charAt(i));
			System.out.println((int) word.charAt(i));
		}*/
			int i = 0;
			r=new Robot();
			String strCAPS = word.toUpperCase();
			char[] arrSetWord = word.toCharArray();
			char[] arrUpperCaseWord = strCAPS.toCharArray();
			String colon = ":";
			String period = ".";
			String slash = "\\";
			String underscore = "_";
			char charcolon = colon.charAt(0);
			
			for(i = 0; i < word.length(); i++){
				if (Character.isLetter(arrUpperCaseWord[i])){
					if (arrSetWord[i] == arrUpperCaseWord[i]){
						r.keyPress(KeyEvent.VK_SHIFT);
					}
					r.keyPress(arrUpperCaseWord[i]);
					r.keyRelease(arrUpperCaseWord[i]);
					r.keyRelease(KeyEvent.VK_SHIFT);
				}
				else if(Character.isDigit((arrUpperCaseWord[i]))){
					r.keyPress(arrUpperCaseWord[i]);
					r.keyRelease(arrUpperCaseWord[i]);
					r.keyRelease(KeyEvent.VK_SHIFT);
				}
				else if (arrUpperCaseWord[i] == colon.charAt(0)){
					r.delay(20);
					r.keyPress(KeyEvent.VK_ALT);
					r.delay(20);
					r.keyPress(KeyEvent.VK_NUMPAD5);
					r.keyRelease(KeyEvent.VK_NUMPAD5);
					r.delay(20);
					r.keyPress(KeyEvent.VK_NUMPAD8);
					r.keyRelease(KeyEvent.VK_NUMPAD8);
					r.delay(20);
					r.keyRelease(KeyEvent.VK_ALT);
					r.delay(20);
				}
				else if(arrUpperCaseWord[i] == period.charAt(0)){
					r.delay(20);
					r.keyPress(KeyEvent.VK_ALT);
					r.delay(20);
					r.keyPress(KeyEvent.VK_NUMPAD4);
					r.keyRelease(KeyEvent.VK_NUMPAD4);
					r.delay(20);
					r.keyPress(KeyEvent.VK_NUMPAD6);
					r.keyRelease(KeyEvent.VK_NUMPAD6);
					r.delay(20);
					r.keyRelease(KeyEvent.VK_ALT);
					r.delay(20);
				}
				else if(arrUpperCaseWord[i] == slash.charAt(0)){
					r.delay(20);
					r.keyPress(KeyEvent.VK_ALT);
					r.delay(20);
					r.keyPress(KeyEvent.VK_NUMPAD9);
					r.keyRelease(KeyEvent.VK_NUMPAD9);
					r.delay(20);
					r.keyPress(KeyEvent.VK_NUMPAD2);
					r.keyRelease(KeyEvent.VK_NUMPAD2);
					r.delay(20);
					r.keyRelease(KeyEvent.VK_ALT);
					r.delay(20);
				}
				else if(arrUpperCaseWord[i] == underscore.charAt(0)){
					r.delay(20);
					r.keyPress(KeyEvent.VK_ALT);
					r.delay(20);
					r.keyPress(KeyEvent.VK_NUMPAD9);
					r.keyRelease(KeyEvent.VK_NUMPAD9);
					r.delay(20);
					r.keyPress(KeyEvent.VK_NUMPAD5);
					r.keyRelease(KeyEvent.VK_NUMPAD5);
					r.delay(20);
					r.keyRelease(KeyEvent.VK_ALT);
					r.delay(20);
				}
					
					
				}
				
		}
			
	
	
	
	public static void fastPath(String fastpath) throws Exception, UnhandledAlertException {
		try{
			Driver.TC_FastPath = fastpath + "_";
			WebDr.clearMethod("FastPath");
			WebDr.setText("FastPath", fastpath, "Enter Fastpath in the field");
			System.out.println("navigate to " + fastpath);
			WebDr.clickwithmouse("FastPath_OK", "Click on the Go button to navigate to Fastpath Screen");
			WebDr.waitForPageLoaded();
			HtmlReporter.WriteStep("Navigate to Fastpath Screen " + fastpath,
					"User should be able to open fastpath screen",
					fastpath + " Screen opened successfully", true);
		}catch(Exception err){
			HtmlReporter.WriteStep("Navigate to Fastpath Screen " + fastpath,
					"User should be able to open fastpath screen",
					fastpath + " Screen is not opened successfully", false);
			err.printStackTrace();
		}
	}
	

	
	public static boolean fastPathWithAlert(String fastpath, String strAlertText) throws Exception, UnhandledAlertException {
		try{
		WebDr.clearMethod("FastPath");
		WebDr.setText("FastPath", fastpath, "Enter Fastpath in the field");
		System.out.println("navigate to " + fastpath);
		WebDr.clickwithmouse("FastPath_OK", "Click on the Go button to navigate to Fastpath Screen");
		WebDriverWait objWait = new WebDriverWait(Driver.driver, 10);
		objWait.until(ExpectedConditions.alertIsPresent());
		Driver.driver.switchTo().alert();
		String strText = Driver.driver.switchTo().alert().getText();
		if (strText.contains(strAlertText)){
			Driver.driver.switchTo().alert().accept();
			return true;
		}
		else
			return false;
		}catch(Exception ex){
			System.out.println("No such alert window with text " + strAlertText);
			return false;
		}
		
	}
	

	public static void openTTVbatch(String VoucherNumber) throws Exception, UnhandledAlertException {
		try{
		WebDr.fastPath("9021");
		WebDr.waitForPageLoaded();
		WebDr.setText("VoucherNumber", VoucherNumber, "Enter Voucher number in the field");
		WebDr.setText("VoucherSrNumber", WebDr.getTimeStamp(), "Enter Voucher Serial Number in the field");
		WebDr.clickwithmouse("OKButton_9021", "Click on the Ok button");
		WebDr.waitForPageLoaded();
		HtmlReporter.WriteStep("Check whether user is able to open TTV batch",
				"User should be able to open TTV batch for a teller ID",
				"TTV batch opened successfully with voucher number "+VoucherNumber , true);
		}
		catch(Exception error1){
			error1.printStackTrace();
			HtmlReporter.WriteStep("Check whether user is able to open TTV batch",
					"User should be able to open TTV batch for a teller ID",
					"TTV batch could not be  opened successfully " +error1.toString(), false);
		}
		
	}
	
	public static String ChangeDatebyDays(String duration) throws Exception  {
		
		String sysdate = WebDr.getTextBoxValue("SystemDate", "System date is fetched and displayed");
		int days = Integer.parseInt(duration);
		SimpleDateFormat newdate = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(newdate.parse(sysdate));
		c.add(Calendar.DATE, days);  // number of days to add
		String op = newdate.format(c.getTime()); 
		System.out.println(op);
		return op;
	}
	public static String ChangeDatebyStaticDays(int days) throws Exception  {
		
		String sysdate = WebDr.getTextBoxValue("SystemDate", "System date is fetched and displayed");
		
		SimpleDateFormat newdate = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(newdate.parse(sysdate));
		c.add(Calendar.DATE, days);  // number of days to add
		String op = newdate.format(c.getTime()); 
		System.out.println(op);
		return op;
	}
	
	public static void SwitchToLatestWindow() throws NoSuchWindowException, InterruptedException {
		try{
			Thread.sleep(5000);
			wait=new WebDriverWait(driver,50);
			Set<String> handle=driver.getWindowHandles();
			for (String handleNo : handle) {
				driver.switchTo().window(handleNo);
			}
			System.out.println("Window count------" + handle.size());
			System.out.println("WindowSwitched------" + handle);
			//driver.switchTo().defaultContent();
		}catch(Exception error){
			error.printStackTrace();
		}
	}
	
	public static boolean SwitchToLatestAlertCheck(String strAlertMessage) throws Exception {
		try{
			Thread.sleep(9000);
			Set<String> handle=driver.getWindowHandles();
			for (String handleNo : handle) {
				driver.switchTo().window(handleNo);
			}
			System.out.println("Window count------" + handle.size());
			System.out.println("WindowSwitched------" + handle);
			return false;
			//driver.switchTo().defaultContent();
			}catch(UnhandledAlertException error){
				error.printStackTrace();
				System.out.println(error.getAlertText());
				if (error.getAlertText().contains(strAlertMessage)){
					HtmlReporter.WriteStep("Check alert window message on the screen",
							"User should be able to verify alert message on the screen",
							"Alert message verified successfully with text " +error.getAlertText() , true);
						return true;
				}
				else{
					HtmlReporter.WriteStep("Check alert window message on the screen",
							"User should be able to verify alert message on the screen",
							"Alert message is not verified successfully with text " +error.getAlertText() , false);
						return false;
				}
		}catch(Exception error1){
			error1.printStackTrace();
			return false;
		}
	}
	
	public static void SwitchToWindow(String strWindow) throws NoSuchWindowException {
		Set<String> handle=driver.getWindowHandles();
		driver.switchTo().window(strWindow);
		System.out.println("WindowSwitched------" + handle);
		//driver.switchTo().defaultContent();
	}
	
	public static void SelectTopAcct() throws InterruptedException, AWTException {
		// TODO Auto-generated method stub
		Thread.sleep(1000);
		rTab();
		rTab();
		rDown();
		rEnter();
	}
	
	public static void rTab() throws AWTException{
		r=new Robot();
		r.keyPress(KeyEvent.VK_TAB);
		r.keyRelease(KeyEvent.VK_TAB);
		
			
		/*Actions action = new Actions(driver);
		action.sendKeys(Keys.TAB).build().perform();*/
	}
	
	public static void rDelete() throws AWTException{
		r=new Robot();
		r.keyPress(KeyEvent.VK_DELETE);
		r.keyRelease(KeyEvent.VK_DELETE);
		
			
		/*Actions action = new Actions(driver);
		action.sendKeys(Keys.DELETE).build().perform();*/
	}
	
	public static void rLeft() throws AWTException{
		r=new Robot();
		r.keyPress(KeyEvent.VK_TAB);
		r.keyRelease(KeyEvent.VK_TAB);
		
		/*Actions action = new Actions(driver);
		action.sendKeys(Keys.LEFT).build().perform();*/
	}

	public static void rDown() throws AWTException{
		r=new Robot();
		r.keyPress(KeyEvent.VK_DOWN);
		r.keyRelease(KeyEvent.VK_DOWN);
	}
	
	public static void rEnter() throws AWTException{
		r=new Robot();
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		/*Actions action = new Actions(driver);
		action.sendKeys(Keys.ENTER).build().perform();*/
	}
	
	public static void rShiftTab() throws AWTException{
		r=new Robot();
		r.keyPress(KeyEvent.VK_SHIFT);
		r.keyPress(KeyEvent.VK_TAB);
		r.keyRelease(KeyEvent.VK_TAB);
		r.keyRelease(KeyEvent.VK_SHIFT);
		
	}
	
	public static void frame(String frameName) throws InterruptedException{
		driver.switchTo().defaultContent();
		wait=new WebDriverWait(driver,50);
		int transits= frameName.split("\\/").length;
		//System.out.println("Transits--->" +transits);
		for(int i=transits-1;i>=0;i--){
			if ((frameName.split("\\/")[i]).length() >0 ){
				wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName.split("\\/")[i]));
				//driver.switchTo().frame(frameName.split("\\/")[i]);
				Thread.sleep(1000);
				System.out.println("FrameSwitched To: "+frameName.split("\\/")[i]);
			}
		}
	}
	
	public static String getAccountNo() throws Exception{
		//Thread.sleep(3000);
		try{
			WebDriverWait wait1=new WebDriverWait(driver,10);
			wait1.until(ExpectedConditions.alertIsPresent());
			String alertMsg=driver.switchTo().alert().getText();
			AccountNo=alertMsg.split(" ")[alertMsg.split(" ").length - 1];
			System.out.println("Account No. =====================" +AccountNo);
			return AccountNo;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public static String getTDAccountNo() throws Exception{
		//Thread.sleep(3000);
		try{
			//WebDriverWait wait1=new WebDriverWait(driver,10);
			//wait1.until(ExpectedConditions.alertIsPresent());
			String alertMsg=driver.switchTo().alert().getText();
			AccountNo=alertMsg.split(" ")[alertMsg.split(" ").length - 1];
			//System.out.println("Account No. =====================" +AccountNo);
			return AccountNo;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	

	public static String getSerialNo() throws InterruptedException, NoAlertPresentException{
		//Thread.sleep(3000);
		WebDriverWait wait1=new WebDriverWait(driver,10);
		wait1.until(ExpectedConditions.alertIsPresent());
		String alertMsg=driver.switchTo().alert().getText();
		SerialNo=alertMsg.split(" ")[alertMsg.split(" ").length - 1];
		System.out.println("Serial No. ====================="+SerialNo);
		return SerialNo;
	}
	
	public static String getSerialNo_8302() throws InterruptedException, NoAlertPresentException{
		//Thread.sleep(3000);
		WebDriverWait wait1=new WebDriverWait(driver,10);
		wait1.until(ExpectedConditions.alertIsPresent());
		String alertMsg=driver.switchTo().alert().getText();
		System.out.println("Alert msg ====================="+alertMsg);
		
		String SerialNo = alertMsg.split(" ")[alertMsg.split(" ").length - 6];
		System.out.println("Serial No1. ====================="+SerialNo);
		return SerialNo;
	}
	
	public static String getBatchNo() throws InterruptedException, NoAlertPresentException{
		//Thread.sleep(3000);
		WebDriverWait wait1=new WebDriverWait(driver,10);
		wait1.until(ExpectedConditions.alertIsPresent());
		String alertMsg=driver.switchTo().alert().getText();
		BatchNo=alertMsg.split(": ")[alertMsg.split(": ").length - 1];
		System.out.println("Account No. ====================="+BatchNo);
		return BatchNo;
	}
	
	public static void alertAccept() throws TimeoutException, NoAlertPresentException{
		try{
			System.out.println("entered alert");
			WebDriverWait wait1=new WebDriverWait(driver,20);
			wait1.until(ExpectedConditions.alertIsPresent());
			Alert alert = Driver.driver.switchTo().alert();
			String alertMsg=alert.getText();
             System.out.println("Alert box text " + alert.getText());
             //if(! alertMsg.isEmpty()) 
            	 alert.accept();
             System.out.println("done with alert");
		} catch (Exception noAlert) {
			noAlert.getMessage();
		}
	}
	
	public static void alertCancel() throws TimeoutException, NoAlertPresentException{
		try{
			System.out.println("entered alert");
			WebDriverWait wait1=new WebDriverWait(driver,20);
			wait1.until(ExpectedConditions.alertIsPresent());
			Alert alert = Driver.driver.switchTo().alert();
			String alertMsg=alert.getText();
             System.out.println("Alert box text " + alert.getText());
             if(! alertMsg.isEmpty()) 
            	 alert.dismiss();
             System.out.println("done with alert");
		} catch (Exception noAlert) {
			noAlert.getMessage();
		}
	}



	public static boolean isAlertPresent() {
		// TODO Auto-generated method stub
		try{
			WebDriverWait wait1=new WebDriverWait(driver,30);
			wait1.until(ExpectedConditions.alertIsPresent());
			Driver.driver.switchTo().alert();
			return true;
			}catch(NoAlertPresentException ex){
				return false;
			}
			catch(Exception ex1){
				return false;
			}
	}
	
	public static boolean isAlertPresentWithinTime(int alerttimeout) throws Exception {
		// TODO Auto-generated method stub
		try{
			WebDriverWait wait1=new WebDriverWait(driver,alerttimeout);
			wait1.until(ExpectedConditions.alertIsPresent());
			Driver.driver.switchTo().alert();
			return true;
			}catch(NoAlertPresentException ex){
				return false;
			}
			catch(Exception ex1){
				return false;
			}
	}
	
	public static boolean isAuthoriseAlertPresent() throws TimeoutException {
				try{
					WebDriverWait wait1=new WebDriverWait(driver,20);
					wait1.until(ExpectedConditions.alertIsPresent());
					Alert alert = Driver.driver.switchTo().alert();
					String alertMsg=alert.getText();
					if (alertMsg.contains("Authorisation"))
						Driver.driver.switchTo().alert().accept();
					else{
						//Driver.driver.switchTo().alert().accept();
						return false;
					}
					}catch(NoAlertPresentException ex){
						ex.printStackTrace();
						return false;
					}
				return true;
	}

	public static String getTimeStamp(){
		Date dt1 = new Date();
		long time = dt1.getTime();
		Timestamp ts = new Timestamp(time);
		String append = ts.toString();
		append = append.replaceAll(" ", "");
		append = append.replaceAll(":", "");
		append = append.replaceAll("-", "");
		append = append.substring(5);
		String[] finalappend = append.split("\\.");
		return finalappend[0];
	}
	
	public static void CloseWindowWithTitle(String strWindowTitle)
	{
	    Set <String> windows = driver.getWindowHandles();
	    String mainwindow = driver.getWindowHandle();

	    for (String handle: windows)
	    {
	        driver.switchTo().window(handle);
	        System.out.println("switched to "+driver.getTitle()+"  Window");
	        String pagetitle = driver.getTitle();
	        if(pagetitle.contains(strWindowTitle))
	        {
	            driver.close();
	            System.out.println("Closed the  '"+pagetitle+"' Tab now ...");
	        }
	    }
	}
	
	public static void CloseWindowWithNumber(int intWindow) throws NoSuchWindowException, InterruptedException
	{	
		try{
	    Set <String> windows = driver.getWindowHandles();
	    String mainwindow = driver.getWindowHandle();
	    int intcnt = 0;
	    for (String handle: windows)
	    {
	    	intcnt = intcnt + 1;
	        driver.switchTo().window(handle);
	        System.out.println("switched to "+driver.getTitle()+"  Window");
	        if(intcnt == intWindow)
	        {
	            driver.close();
	            System.out.println("Closed the  window number'"+intWindow+"' Tab now ...");
	            WebDr.SwitchToLatestWindow();
	            break;
	        }
	    }
		}
		catch
			(Exception error){
				error.printStackTrace();
				 System.out.println("Couldn't Close the  window number'"+intWindow+"' Tab now ...");
		}
	}
	
	public static void AlertHandleOfflineChargeErrors() {
		// TODO Auto-generated method stub
		try {
				if(WebDr.isAlertPresent()){
					WebDr.alertAccept();
					WebDr.alertAccept();
				}
				if(WebDr.isAlertPresent()){
					WebDr.alertAccept();
					WebDr.alertAccept();
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static boolean AlertHandleForUnknownErrors() {
		// TODO Auto-generated method stub
		boolean status=false;
		try {
			for(int intcnt = 1; intcnt<5;intcnt++){
				if(WebDr.isAlertPresent()){
					WebDr.getTextOnAlertPopup("Verify alert popup");
					WebDr.alertAccept();
					status = true;
				}
			}
				return status;
		} catch (Exception e) {
			e.printStackTrace();
			return status;
		}
		
	}




	

}
