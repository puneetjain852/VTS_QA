package utility;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestResult;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import testCases.Driver;

public class ExtentManager {

	public static ExtentReports extent;
	public static String reportsPath;
	
	public static ExtentReports getInstance() {
		if (extent == null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
			Date date = new Date();
			String time = dateFormat.format(date);
			reportsPath = System.getProperty("user.dir")+"\\ExecutionSummary\\"+ HtmlReporter.appName+ "_" +time+".html";

			extent = new ExtentReports(reportsPath, true,
					DisplayOrder.OLDEST_FIRST);

			extent.loadConfig(new File(System.getProperty("user.dir")
					+ "//ReportsConfig.xml"));

			extent.addSystemInfo("Selenium version", "2.45.0").addSystemInfo(
					"Environment", "UAT");
		}
		return extent;
	}
	
	 public void getResult(ITestResult result)
	  {
	        if(result.getStatus()==ITestResult.FAILURE)
	        {
	        	Driver.extentTest.log(LogStatus.FAIL, result.getThrowable());
	             
	        }
	        extent.endTest(Driver.extentTest);
	  }
	 
	public static void endreport()
    {
        extent.flush();
        extent.close();
    }

  
}
