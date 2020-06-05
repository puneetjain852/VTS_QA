
package utility;

import testCases.Driver;



public class Constant 
{
	public static final String Path_TestData = Driver.userDir + "\\TestData\\";
   //public static final String Path_TestData = "C:" + "\\TestData\\";
   public static final String File_TestData = "TestData.xlsx";     
   public static final String Chrome_Driver = Driver.userDir + "\\Plugin\\chromedriver.exe";
   public static final String IE_Driver= Driver.userDir + "\\Plugin\\IEDriverServer.exe";
   //public static final String IE_Driver= "C:\\Plugin\\IEDriverServer.exe";
   //public static final String SharedReportDriveLetter= "C:";
   public static final String SharedReportDriveLetter="C:";
   public static final String SharedReportFolder= "\\FCRTestResults\\";
   public static final String SharedReportPath = SharedReportDriveLetter + SharedReportFolder;
   public static final String SikuliScreenPath = Driver.userDir + "\\SikuliScreens\\";
  
   
}
