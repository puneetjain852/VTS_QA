package testCases;

import org.openqa.selenium.WebDriver;

import pkgResuableModule.HomePage;

public class TestFlowVTS {
	public static void executeTC(WebDriver driver, String str_tc) throws Exception {
		// System.out.println("TestFlowBIR.java- executeTC Invoked");

		switch (str_tc) {
		case "TC_02":
			TC_02(driver);
			break;
		case "TC_10":
			TC_10(driver);
			break;

		case "TC_14":
			TC_14(driver);
			break;
		}
	}

	private static void TC_02(WebDriver driver) throws Exception {

		HomePage.Login_Link();

	}

	private static void TC_10(WebDriver driver) throws Exception {

		HomePage.Oxford_Webinar_Link();

	}

	private static void TC_14(WebDriver driver) throws Exception {

		HomePage.Landlord_Link();
		;

	}

}