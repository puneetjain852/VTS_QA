package utility;

import java.awt.Desktop;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.relevantcodes.extentreports.LogStatus;


import testCases.Driver;


public class HtmlReporter
{

	static File f,t;
	
	public static long startTime, endTime, tc_passedpercentages;
	static BufferedWriter bw, bt;
	public static int step_no, tc_no, tc_count, tc_pass, tc_fail, int_test_severefailure;
	public static boolean tc_result, test_failedstep;
	public static String screenDirPath, testCase_Itr, testCase_ID, testCase_Name, testStartTime, testEndTime, testTotalTime, testResult, testdescription, testcaseSeverity;
	static String appUrl, appName,apEnv,appCycle, htmlReport,captureScreen, dirPath, strSendEmailFlag, strSendToList, strSendCCList, strSendDebugTo, strSendDebugCC, hostName, NWdirPath, strSendExtentReport, screenForBNC;
	static String strHexGreen = "#00FF00";
	static String strHexRed = "#FF0000";
	static String strHexAmber = "#FFBF00";
	static String strBuildStatus = "UPDATE_BUILD_COLOR_CODE";
	static String strUpdatedBuildStatus = "";
	static String strUpdatedBuildStatusLabel = "TBD";
	static int sr_number = 0;
	
	
	public static void setUpReportFolder() throws Exception
	{
		
		ExcelUtils.setExcelFile(Constant.Path_TestData + Constant.File_TestData,"Configuration");
		htmlReport=ExcelUtils.getCellData(2,1);
		captureScreen=ExcelUtils.getCellData(3,1);
		appName=ExcelUtils.getCellData(4,1);
		apEnv=ExcelUtils.getCellData(5,1);
		appCycle=ExcelUtils.getCellData(6,1);
		appUrl=ExcelUtils.getCellData(7,1);
		strSendEmailFlag = ExcelUtils.getCellData(8,1);
		strSendToList = ExcelUtils.getCellData(9,1);
		strSendCCList = ExcelUtils.getCellData(10,1);
		strSendDebugTo = ExcelUtils.getCellData(11,1);
		strSendDebugCC = ExcelUtils.getCellData(12,1);
		strSendExtentReport = ExcelUtils.getCellData(13,1);
		tc_no=0;
		int_test_severefailure = 0;
		
		InetAddress addr;
		addr = InetAddress.getLocalHost();
		hostName = addr.getHostName();
		
		//dirPath=Driver.userDir + "\\TestResults\\" + appName + "-" + apEnv + "-" + appCycle + "-RESULT-" +  ((CommonUtils.getDate()).replace(':','-')).replaceAll("\\s","");
		dirPath= Constant.SharedReportPath + appName + "-" + apEnv + "-" + appCycle + "-RESULT-" +  ((CommonUtils.getDate()).replace(':','-')).replaceAll("\\s","");
//		System.out.print("Create a folder to save test results at path C:\\TestResults\\. Share the TestResults folder to access result files");
		File file = new File(dirPath);
		if (!file.exists()) 
		{
			if (file.mkdir()) 
			{
				//System.out.println("Directory is created!");
				String runTime=CommonUtils.getDate();
				String userName=System.getProperty("user.name");
				//Summary
				f = new File(dirPath + "\\TestResult_Summary.html");
				bw = new BufferedWriter(new FileWriter(f));
				tc_count=0;
				tc_pass=0;
				tc_fail=0;
				tc_result=true;
				test_failedstep= false;
				bw.write("<HTML><HEAD><TITLE>Selenium Summary Report </TITLE></HEAD>");
				bw.write("<BODY ALIGN=BOTTOM ><BR><TR COLS=2><TD WIDTH=94% BGCOLOR=WHITE><FONT COLOR=#215967 SIZE=4 FACE=\"barclays sans\"><CENTER><B>"+ appName +" Sanity Build Health Status - " + runTime + " on System " + hostName +" By User " + userName.toUpperCase() + "</B></FONT></CENTER></TD></TR>");
				bw.write("<BR><TABLE style=width:100% BORDER=1 BGCOLOR=#595959><TR><TD COLSPAN=5 BGCOLOR=#215967><FONT COLOR=WHITE SIZE=2 FACE=\"barclays sans\"><CENTER><B> Application Details </B></FONT></TD></TR>");
				bw.write("<TR><TD WIDTH=150 BGCOLOR=#98C2CD><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B> Application Name </B></CENTER></FONT></TD>");
				bw.write("<TD WIDTH=500  BGCOLOR=#98C2CD><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B> Application URL </B></CENTER></FONT></TD>");
				bw.write("<TD WIDTH=150  BGCOLOR=#98C2CD><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B> Environment </B></CENTER></FONT></TD>");
				bw.write("<TD WIDTH=150  BGCOLOR=#98C2CD><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B> Application Version </B></CENTER></FONT></TD>");
				bw.write("<TD WIDTH=150  BGCOLOR=#98C2CD><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B> Build Status </B></CENTER></FONT></TD></TR>");
				bw.write("<TR><TD WIDTH=150 BGCOLOR=#FFFFFF><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER> " + appName + "</CENTER></FONT></TD>");
				bw.write("<TD WIDTH=500  BGCOLOR=#FFFFFF><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER> " + appUrl + "</CENTER></FONT></TD>");
				bw.write("<TD WIDTH=150  BGCOLOR=#FFFFFF><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER> " + apEnv + "</CENTER></FONT></TD>");
				bw.write("<TD WIDTH=150  BGCOLOR=#FFFFFF><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER> " + appCycle + "</CENTER></FONT></TD>");
				bw.write("<TD WIDTH=150  BGCOLOR="+ strBuildStatus +"><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER> " + "            " + "</CENTER></FONT></TD></TR></TABLE>");
				bw.write("<BR><TABLE style=width:100% BORDER=1 BGCOLOR=#595959><TR><TD COLSPAN=6 BGCOLOR=#215967><FONT COLOR=WHITE SIZE=2 FACE=\"barclays sans\"><CENTER><B> Test Case Wise Report </B></CENTER></FONT></TD></TR>");
				bw.write("<TR><TD WIDTH=100 BGCOLOR=#98C2CD><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B> Sr No  </B></CENTER></FONT></TD>");
//				bw.write("<TD WIDTH=100  BGCOLOR=#98C2CD><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B>Test Case Name  </B></CENTER></FONT></TD>");
				bw.write("<TD WIDTH=500  BGCOLOR=#98C2CD><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B>Scenario  </B></CENTER></FONT></TD>");
				bw.write("<TD WIDTH=200  BGCOLOR=#98C2CD><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B> Start Time  </B></CENTER></FONT></TD>");
				bw.write("<TD WIDTH=200  BGCOLOR=#98C2CD><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B> End Time  </B></CENTER></FONT></TD>");
				bw.write("<TD WIDTH=150  BGCOLOR=#98C2CD><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B> Total Time  </B></CENTER></FONT></TD>");
				bw.write("<TD WIDTH=150  BGCOLOR=#98C2CD><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B> Test Case Result  </B></CENTER></TD></TR>");
				
				
				//Details Report
				t = new File(dirPath + "\\TestResult_Detail.html");
				bt = new BufferedWriter(new FileWriter(t));
				
				
				bt.write("<HTML><HEAD><TITLE>Selenium Detailed Report </TITLE></HEAD>");
				bt.write("<BR><BR><BODY><TABLE style=width:100% BORDER=0 BGCOLOR=BLACK CELLSPACING=1>");
				bt.write("<TR><TD COLSPAN=6 BGCOLOR=#215967><FONT COLOR=WHITE SIZE=3 FACE=\"barclays sans\"><CENTER><B> Detailed Report  </CENTER> </B></FONT></TD></TR>");
				bt.write("<TR><TD BGCOLOR=#98C2CD WIDTH=75><FONT COLOR=BLACK SIZE=2 FACE=\"barclays sans\"><CENTER><B> Step Number </B></CENTER></FONT></TD>");
				bt.write("<TD BGCOLOR=#98C2CD WIDTH=200><FONT COLOR=BLACK SIZE=2 FACE=\"barclays sans\"><CENTER><B> Test Case Description </B></CENTER></FONT></TD>");
				bt.write("<TD BGCOLOR=#98C2CD WIDTH=300><FONT COLOR=BLACK SIZE=2 FACE=\"barclays sans\"><CENTER><B> Expected Result </B></CENTER></FONT></TD>");
				bt.write("<TD BGCOLOR=#98C2CD WIDTH=300><FONT COLOR=BLACK SIZE=2 FACE=\"barclays sans\"><CENTER><B> Actual Result </B></CENTER></FONT></TD>");
				bt.write("<TD BGCOLOR=#98C2CD WIDTH=75><FONT COLOR=BLACK SIZE=2 FACE=\"barclays sans\"><CENTER><B> Status </B></CENTER></FONT></TD>");
				bt.write("<TD BGCOLOR=#98C2CD WIDTH=100><FONT COLOR=BLACK SIZE=2 FACE=\"barclays sans\"><CENTER><B> Screen Shot </B></CENTER></FONT></TD></TR>");
		
			} 
			else 
			{
				//System.out.println("Failed to create directory!");
			}
		}
		ExtentManager.getInstance();
	}

	public static void testWiseReport() throws Exception
	{
		bt.write("<TR COLS=5><TD BGCOLOR=#EEEEEE WIDTH=75><FONT SIZE=2 FACE=\"barclays sans\">" + step_no + "</FONT></TD>");
		bt.write("<TD BGCOLOR=#EEEEEE WIDTH=200><FONT SIZE=2 FACE=\"barclays sans\">Test Case " + tc_no + "</FONT></TD>");
		bt.write("<TD BGCOLOR=#EEEEEE WIDTH=300><FONT SIZE=2 FACE=\"barclays sans\">Test Case " + tc_no + "- " + testCase_Name + " ENDS HERE</FONT></TD>");
		bt.write("<TD BGCOLOR=#EEEEEE WIDTH=300><FONT SIZE=2 FACE=\"barclays sans\"></A></FONT></TD>");
		bt.write("<TD WIDTH=75 BGCOLOR=#EEEEEE></TD><TD WIDTH=100 BGCOLOR=#EEEEEE></TD></TR>");
		tc_count++;
		sr_number++;
//		bw.write("<TR><TD WIDTH=100 BGCOLOR=#FFFFFF><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER> " + testCase_ID + " </CENTER></FONT></TD>");
		bw.write("<TR><TD WIDTH=100 BGCOLOR=#FFFFFF><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER> " + sr_number + " </CENTER></FONT></TD>");
//		bw.write("<TD WIDTH=500  BGCOLOR=#FFFFFF><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"> " + testCase_Name + "</FONT></TD>");
		bw.write("<TD WIDTH=500  BGCOLOR=#FFFFFF><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"> " + testdescription + "</FONT></TD>");
		bw.write("<TD WIDTH=200  BGCOLOR=#FFFFFF><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER> " + testStartTime + " </CENTER></FONT></TD>");
		bw.write("<TD WIDTH=200  BGCOLOR=#FFFFFF><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER> " + testEndTime + "</CENTER></FONT></TD>");
		bw.write("<TD WIDTH=150  BGCOLOR=#FFFFFF><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER> "+TimeUnit.MILLISECONDS.toSeconds(endTime - startTime)+" Sec</CENTER></FONT></TD>");
		System.out.println("Start Time: "+ testStartTime);
		System.out.println("Start Time: "+ testEndTime);
		if((tc_result) && (! test_failedstep))
		{
			tc_pass++;
			System.out.println("Status: PASSED");
			bw.write("<TD WIDTH=150  BGCOLOR=#FFFFFF><CENTER><FONT COLOR=GREEN SIZE=5 FACE='WINGDINGS 2'></FONT><B><A HREF=\"#" + testCase_ID + "\"><FONT COLOR=GREEN SIZE=2 FACE=\"barclays sans\"> Pass </B></CENTER></FONT></A></TD></TR>");
			
				
		}
		else
		{
			tc_fail++;
			System.out.println("Status: FAILED");
			bw.write("<TD WIDTH=150  BGCOLOR=#FFFFFF><CENTER><FONT COLOR=RED SIZE=5 FACE='WINGDINGS 2'></FONT><B><A HREF=\"#" + testCase_ID + "\"><FONT COLOR=RED SIZE=2 FACE=\"barclays sans\"> Fail </B></CENTER></FONT></A></TD></TR>");
			test_failedstep = false;
			if (testcaseSeverity.equals("High"))
				int_test_severefailure = int_test_severefailure + 1;
		}	
		System.out.println("Test Case " + tc_no + "- " + testCase_Name + " END HERE - @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		Driver.TC_FastPath = "";
		ExtentManager.extent.flush();
		ExtentManager.extent.endTest(Driver.extentTest);
	}
	public static void endTestReport() throws Exception
	{	
		tc_passedpercentages = ((tc_pass*100)/tc_count);
		
		bw.write("</table>");
		
		bt.write("</TABLE>");
		
		bw.write("<BR><TABLE style=width:100% BORDER=0 BGCOLOR=#595959><TR><TD COLSPAN=5 BGCOLOR=#215967><FONT COLOR=WHITE SIZE=2 FACE=\"barclays sans\"><CENTER><B> Summary Report </B></CENTER></FONT></TD></TR>");
		
		bw.write("<TR><TD BGCOLOR=#98C2CD><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B> Total Test Cases For Execution </B></CENTER></FONT></TD>");
		
		bw.write("<TD BGCOLOR=#98C2CD><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B> Total Test Cases Executed </B></CENTER></FONT></TD>");
		
		bw.write("<TD BGCOLOR=#98C2CD><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B> Total Test Cases Passed </B></CENTER></FONT></TD>");
		
		bw.write("<TD BGCOLOR=#98C2CD><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B> Total Test Cases Failed </B></CENTER></TD>");
		
		bw.write("<TD BGCOLOR=#98C2CD><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B> Total Test Cases Passed(%) </B></CENTER></TD></TR>");
		
		bw.write("<TR><TD BGCOLOR=#FFFFFF><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B> " + tc_count + " </B></CENTER></FONT></TD>");
		
		bw.write("<TD BGCOLOR=#FFFFFF><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B> " + (tc_pass + tc_fail) + " </B></CENTER></FONT></TD>");
		
		bw.write("<TD BGCOLOR=#FFFFFF><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B> " + tc_pass + " </B></CENTER></FONT></TD>");
		
		bw.write("<TD BGCOLOR=#FFFFFF><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B> " + tc_fail + " </B></CENTER></TD>");
		
		bw.write("<TD BGCOLOR=#FFFFFF><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"><CENTER><B> " + tc_passedpercentages + " %" + " </B></CENTER></TD></TR></TABLE></BODY></HTML>");
		//file closing
		bw.close();
		bt.close();
		if (tc_passedpercentages < 40)
		{
			strUpdatedBuildStatus = strHexRed;
			strUpdatedBuildStatusLabel = "RED";
		}
		else if(tc_passedpercentages == 100)
		{
			strUpdatedBuildStatus = strHexGreen;
			strUpdatedBuildStatusLabel = "GREEN";
		}
		else if((tc_passedpercentages >= 40)  && (tc_passedpercentages < 100)) {
			if (int_test_severefailure > 0){
				strUpdatedBuildStatus = strHexRed;
				strUpdatedBuildStatusLabel = "RED";
			}
			else{
				strUpdatedBuildStatus = strHexAmber;
				strUpdatedBuildStatusLabel = "AMBER";
			}
		}
		HtmlReporter.modifyFile(dirPath + "\\TestResult_Summary.html", strBuildStatus, strUpdatedBuildStatus);
		File[] files = new File[2];
		files[0]=f;
		files[1]=t;
		File mergedFile = new File(dirPath + "\\TestReport.html");
		CommonUtils.mergeFiles(files, mergedFile);
		File htmlFile = new File(dirPath + "\\TestReport.html");
		ExtentManager.endreport();
		//Desktop.getDesktop().browse(htmlFile.toURI());
		Desktop.getDesktop().open(htmlFile);
		String strVBfile = Driver.userDir + "\\Plugin\\SendEmail.vbs";
		String strReportName = NWdirPath+"\\TestReport.html";
		String strSubject = "Sanity%Test%Execution%Status%" + appName + "-Build%Status-" + strUpdatedBuildStatusLabel;
		if(strSendEmailFlag.equals("Yes")){
			if((tc_fail==0) || (tc_fail>0)){
				try{
					Runtime.getRuntime().exec("wscript "+ strVBfile+ " " + strSubject +" "+strSendToList+" "+strSendCCList+" "+ strReportName);
					System.out.println("wscript "+ strVBfile+ " " + strSubject +" "+strSendToList+" "+strSendCCList+" "+ strReportName);
					}catch(Exception e){
						e.printStackTrace();
					}
			}
			Thread.sleep(5000);
			if (tc_fail!=0){
				Runtime.getRuntime().exec("wscript "+ strVBfile+ " " + strSubject +" "+strSendDebugTo+" "+strSendDebugCC+" "+ strReportName);
				System.out.println("wscript "+ strVBfile+ " " + strSubject +" "+strSendDebugTo+" "+strSendDebugCC+" "+ strReportName);
			}
		}
		if((strSendEmailFlag.equals("OnlyOnSuccess"))){
			if ((tc_fail==0) && (tc_pass>0)){
				try{
					Runtime.getRuntime().exec("wscript "+ strVBfile+ " " + strSubject +" "+strSendToList+" "+strSendCCList+" "+ strReportName);
					System.out.println("wscript "+ strVBfile+ " " + strSubject +" "+strSendToList+" "+strSendCCList+" "+ strReportName);
					}catch(Exception e){
						e.printStackTrace();
					}
			}
			Thread.sleep(5000);
			if (tc_fail!=0){
				Runtime.getRuntime().exec("wscript "+ strVBfile+ " " + strSubject +" "+strSendDebugTo+" "+strSendDebugCC+" "+ strReportName);
				System.out.println("wscript "+ strVBfile+ " " + strSubject +" "+strSendDebugTo+" "+strSendDebugCC+" "+ strReportName);
			}
		}
		}
	
	
	public static void WriteStep(String decription, String expected, String actual, boolean status) throws Exception
	{
		String linkPath=null;
		if (step_no==0)
		{			
			screenDirPath=dirPath + "\\Screenshots\\" + testCase_Name + "-" + testCase_ID + "-" + testCase_Itr;
			screenForBNC = dirPath + "\\Screenshots\\FCRBNC_SNAPSHOTS\\";
			linkPath="./Screenshots/" + testCase_Name + "-" + testCase_ID + "-" + testCase_Itr;
			//"./Screenshots/CIB_COB_MVP1001.2_TC001-1-1/Step-2.png"
			File file = new File(screenDirPath);
			if (!file.exists()) 
			{
				if (file.mkdirs()) 
				{
					
					//System.out.println("Screenshort dir created");
				}
			}
			
		/*	File fileBNC = new File(screenForBNC);
			if (!fileBNC.exists()) 
			{
				if (fileBNC.mkdirs()) 
				{
					//System.out.println("Screenshort dir created");
				}
			}
			*/
					
			//if (dirPath.contains(Constant.SharedReportDriveLetter)){
			if (dirPath.contains(":")){
				NWdirPath = dirPath.replaceFirst(Constant.SharedReportDriveLetter, hostName);
				NWdirPath = "\\\\" + NWdirPath;
			}else{
				NWdirPath = dirPath;
			}
			
			screenDirPath=NWdirPath + "\\Screenshots\\" + testCase_Name + "-" + testCase_ID + "-" + testCase_Itr;
			
			bt.write("<TR COLS=2><TD BGCOLOR=#a7d8e3 WIDTH=75><FONT COLOR=BLACK SIZE=2 FACE=\"barclays sans\"><B> Test Case </B></FONT></TD>");
			bt.write("<TD ID=\""+ testCase_ID + "\" COLSPAN=5 BGCOLOR=#a7d8e3 WIDTH=800><FONT COLOR=BLACK SIZE=2 FACE=\"barclays sans\"><B>" + testCase_ID + "- " + testdescription + " (Iteration-" + testCase_Itr + ") </B></FONT></TD> </TR>");
			bt.write("<TR COLS=5><TD BGCOLOR=#EEEEEE WIDTH=75><FONT SIZE=2 FACE=\"barclays sans\">1</FONT></TD>");
			bt.write("<TD BGCOLOR=#EEEEEE WIDTH=200><FONT SIZE=2 FACE=\"barclays sans\">Test Case " + tc_no + "</FONT></TD>");
			bt.write("<TD BGCOLOR=#EEEEEE WIDTH=300><FONT SIZE=2 FACE=\"barclays sans\">Test Case " + tc_no + "-" + testCase_Name + " STARTS HERE</FONT></TD>");
			bt.write("<TD BGCOLOR=#EEEEEE WIDTH=300><FONT SIZE=2 FACE=\"barclays sans\"></A></FONT></TD>");
			bt.write("<TD WIDTH=75 BGCOLOR=#EEEEEE></TD><TD WIDTH=100 BGCOLOR=#EEEEEE></TD></TR>");
			System.out.println("Step No: "+ (step_no + 1) + ">>Test Case " + tc_no + "- " + testCase_Name + " STARTS HERE -   ####################################################");
			step_no=2;
			Driver.extentTest=ExtentManager.extent.startTest(testdescription);
		}
		
		bt.write("<TR COLS=5><TD BGCOLOR=#edf7f9 WIDTH=75><FONT SIZE=2 FACE=\"barclays sans\">" + step_no + "</FONT></TD>");
		bt.write("<TD BGCOLOR=#edf7f9 WIDTH=200><FONT SIZE=2 FACE=\"barclays sans\">" + decription + "</FONT></TD>");
		bt.write("<TD BGCOLOR=#edf7f9 WIDTH=300><FONT SIZE=2 FACE=\"barclays sans\">" + expected + "</FONT></TD>");
		bt.write("<TD BGCOLOR=#edf7f9 WIDTH=300><FONT SIZE=2 FACE=\"barclays sans\">" + actual + "</A></FONT></TD>");
		
		System.out.print("Step No: "+step_no);
		System.out.print(">> DESCRIPTION: "+decription);
		System.out.print(">> EXPECTED: "+expected);
		System.out.print(">> ACTUAL: "+actual);
		if(status==true)
		{
			bt.write("<TD BGCOLOR=#edf7f9 WIDTH=75><FONT COLOR=GREEN  SIZE=5 FACE='WINGDINGS 2'></FONT><FONT COLOR=GREEN  SIZE=2 FACE=\"barclays sans\"><B> Pass </B></FONT></TD>");
			System.out.print(">> Status : PASS");
			tc_result=true;
			
			Driver.extentTest.log(LogStatus.PASS, expected,actual);
		}
		else
		{
			tc_result=false;
			test_failedstep = true;
			System.out.print(">> Status : FAIL");
			bt.write("<TD BGCOLOR=#edf7f9 WIDTH=75><FONT COLOR=RED  SIZE=5 FACE='WINGDINGS 2'></FONT><FONT COLOR=RED  SIZE=2 FACE=\"barclays sans\"><B> Fail </B></FONT></TD>");
			Driver.extentTest.log(LogStatus.FAIL, expected,actual);
		}
		System.out.println("");
		CaptureScreenShot(tc_result);
		
		//bt.write("<TD WIDTH=100 BGCOLOR=#EEEEEE><A HREF=\"" + linkPath + "/Step-" + step_no + ".png\" TARGET=_BLNAK><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"> Click Here </FONT></A></TD>");
//		bt.write("<TD WIDTH=100 BGCOLOR=#edf7f9><A HREF=\"" + screenDirPath + "\\Step-" + step_no + ".png\" TARGET=_BLNAK><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"> Click Here </FONT></A></TD>");
		bt.write("<TD WIDTH=100 BGCOLOR=#edf7f9><A HREF=\"" + screenDirPath + "\\" + Driver.TC_FastPath + "Step-" + step_no + ".png\" TARGET=_BLNAK><FONT COLOR=#000000 SIZE=2 FACE=\"barclays sans\"> Click Here </FONT></A></TD>");
		step_no++;
	}
	
//	public static void CaptureScreenShot(boolean c_status) throws Exception
//	{
//		boolean caputre=false;
//		if(captureScreen.equals("On Every Step"))
//		{
//			caputre=true;
//		}
//		else if(captureScreen.equals("Never"))
//		{
//			caputre=false;
//		}
//		else if(c_status==false)
//		{
//			caputre=true;
//		}
//		
//		if(caputre==true)
//		{	
//			try{
//				File scrFile = ((TakesScreenshot)Driver.driver).getScreenshotAs(OutputType.FILE);
//				FileUtils.copyFile(scrFile, new File(screenDirPath + "\\Step-" + step_no + ".png"));
//			}catch(Exception alertimage){
//				BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
//				ImageIO.write(image, "png", new File(screenDirPath + "\\Step-" + step_no + ".png"));
//				System.out.println(alertimage.getMessage());
//			}
//		}
//	}
	
	public static void CaptureScreenShot(boolean c_status) throws Exception
	{
		boolean caputre=false;
		String ImgFileName = "";
		if(captureScreen.equals("On Every Step"))
		{
			caputre=true;
		}
		else if(captureScreen.equals("Never"))
		{
			caputre=false;
		}
		else if(c_status==false)
		{
			caputre=true;
		}
		
		if(caputre==true)
		{	
			ImgFileName = "\\" + Driver.TC_FastPath + "Step-" + step_no + ".png";
			try{
//				File scrFile = ((TakesScreenshot)Driver.driver).getScreenshotAs(OutputType.FILE);
//				FileUtils.copyFile(scrFile, new File(screenDirPath + "\\Step-" + step_no + ".png"));
				
				BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
				ImageIO.write(image, "png", new File(screenDirPath + ImgFileName));
				
				
			
			}catch(Exception alertimage){
//				BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
//				ImageIO.write(image, "png", new File(screenDirPath + "\\Step-" + step_no + ".png"));
				System.out.println(alertimage.getMessage());
			}
		}
	}
	
	  
    public static void modifyFile(String filePath, String oldString, String newString)
	    {
	        File fileToBeModified = new File(filePath);
	        String oldContent = "";
	        BufferedReader reader = null;
	        FileWriter writer = null;
	        try
	        {
	            reader = new BufferedReader(new FileReader(fileToBeModified));
	            //Reading all the lines of input text file into oldContent
	            String line = reader.readLine();
	            while (line != null) 
	            {
	                oldContent = oldContent + line + System.lineSeparator();
	                line = reader.readLine();
	            }
	            //Replacing oldString with newString in the oldContent
	            String newContent = oldContent.replaceAll(oldString, newString);
	            //Rewriting the input text file with newContent
	            writer = new FileWriter(fileToBeModified);
	            writer.write(newContent);
	        }
	        catch (IOException e)
	        {
	            e.printStackTrace();
	        }
	        finally
	        {
	            try
	            {
	                //Closing the resources
	                reader.close();
	                writer.close();
	            } 
	            catch (IOException e) 
	            {
	                e.printStackTrace();
	            }
	        }
	    }
}

