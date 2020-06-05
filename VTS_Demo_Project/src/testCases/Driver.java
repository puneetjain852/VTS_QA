package testCases;
/*##############################################################################
'Class Name: Driver.java
'Description: 
'Prepared By: Minhaj Bakhsh
'Prepared On: 07/22/2015
'Updated By:
'Updated On:
'##############################################################################*/

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utility.ExtentManager;
import utility.HtmlReporter;
import utility.Launcher;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;
import org.openqa.selenium.winium.DesktopOptions;

public class Driver {
	public static WebDriver driver = null;		
	
	public static Map<String, String> dictionary = new HashMap<String, String>();
	public static String userDir;
	public static String strFCRLaunchWindow;
	public static String strFCRAppWindow="";
	public static ExtentReports rep;
	public static ExtentTest extentTest;
	public static WiniumDriverService service;
	public static WiniumDriver winiumDriver;
	public static DesktopOptions options;
	static String sNumber;
	public static String getScreenValue;
	public static boolean blnNavigatFPScreen = false;
	public static String TC_FastPath = ""; 
		  
	public static void AppInovker(String appName, String fn_name)
			throws Exception {

		
			TestFlowVTS.executeTC(Driver.driver, fn_name);
		 		
		}	
	
	
	public void mainDriver() throws Exception {
		userDir = System.getProperty("user.dir");	
		// userDir=System.getProperty("C:\\Users\\G01016745\\Desktop\\Automation\\ABSA_Selenium_Framework\\TestData\\TestData.xlsx");
		Launcher.InvokeLauncher();
	}	

	public static void main(String ar[]) throws Exception {  
		Driver ob = new Driver();
		ob.mainDriver();
	}
	
			
	public static void passTestCases()
	{			
		rep = ExtentManager.getInstance();	

		extentTest = rep.startTest(HtmlReporter.testCase_ID,
				HtmlReporter.testCase_Name);
		
		extentTest.log(LogStatus.PASS, "Test Case Pass");
		rep.endTest(extentTest);
		rep.flush();		
		
	}		
	
	
	
	public static void failedTestCases()
	{
		rep = ExtentManager.getInstance();

		extentTest = rep.startTest(HtmlReporter.testCase_ID,
				HtmlReporter.testCase_Name);
		
		extentTest.log(LogStatus.FAIL, "Test Case Failed");
		rep.endTest(extentTest);
		rep.flush();
	}
	
	public static Map<String, String> calcNumPad_ObjectRepository;
    {
        calcNumPad_ObjectRepository = new HashMap<String, String>();
        calcNumPad_ObjectRepository.put("0", "130");
        calcNumPad_ObjectRepository.put("1", "131");
        calcNumPad_ObjectRepository.put("2", "132");
        calcNumPad_ObjectRepository.put("3", "133");		
        calcNumPad_ObjectRepository.put("4", "134");
        calcNumPad_ObjectRepository.put("5", "135");
        calcNumPad_ObjectRepository.put("6", "136");
        calcNumPad_ObjectRepository.put("7", "137");
        calcNumPad_ObjectRepository.put("8", "138");
        calcNumPad_ObjectRepository.put("9", "139");
        calcNumPad_ObjectRepository.put("+", "93");
        calcNumPad_ObjectRepository.put("-", "94");
        calcNumPad_ObjectRepository.put("*", "92");
        calcNumPad_ObjectRepository.put("/", "91");
        calcNumPad_ObjectRepository.put("=", "121");
        calcNumPad_ObjectRepository.put("clear", "81");
        calcNumPad_ObjectRepository.put(".", "84");
        calcNumPad_ObjectRepository.put(",", "150");
        calcNumPad_ObjectRepository.put("resultScreen", "150");
    }
	
	public static void setUpBeforeClass() throws Exception {
        DesktopOptions options = new DesktopOptions();
        options.setApplicationPath("C:\\Windows\\System32\\calc.exe");
        File driverPath = new File("Z:\\winium\\Winium.Desktop.Driver.exe");
        service = new WiniumDriverService.Builder().usingDriverExecutable(driverPath).usingPort(9999).withVerbose(true)
                .withSilent(false).buildDesktopService();
        winiumDriver = new WiniumDriver(service, options);
        service.start();
        Thread.sleep(1000);
    }
	
	public static void clickNumber(String number) throws InterruptedException {

        String str1 = "-10=";
        String ash = number + str1;

        String[] res = new String[ash.length()];
        for (int i = 0; i < ash.length(); i++) {

            sNumber = String.valueOf(res[i] = Character.toString(ash.charAt(i)));
            String id_ = calcNumPad_ObjectRepository.get(sNumber);
            winiumDriver.findElement(By.id(id_)).click();
            Thread.sleep(500);
        }
    }
	
	public static String getResults(String resultScreen) throws InterruptedException {
        String id_ = calcNumPad_ObjectRepository.get(resultScreen);
        return winiumDriver.findElement(By.id(id_)).getAttribute("Name");
    }
	
	public static String formateValue(String formatedValue) throws InterruptedException {
    	double value = Double.parseDouble(formatedValue);
        double price = value;
        String value1= String.format("%,.2f", price);
        return value1;
    }
	
	public static void closeService() {
		winiumDriver.close();
        service.stop();
    }
}
