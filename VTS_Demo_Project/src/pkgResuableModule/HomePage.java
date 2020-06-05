package pkgResuableModule;

import pkgPageObjects.VTS_HomePage_POM;
import testCases.Driver;
import utility.HtmlReporter;
import utility.WebDr;

public class HomePage extends Driver {
	public static void Login_Link() throws Exception {
		

		try {
			VTS_HomePage_POM.MenuBar();
			WebDr.click("Login", "Click on Login CTA");
			WebDr.SwitchToLatestWindow();
			Thread.sleep(5000);
			String newPageSource = driver.getPageSource();
			String LoginPageURL = "https://app.vts.com/users/sign_in";
			if (WebDr.isElementExist("Login_Email", "Check if Email Input Field")
					&& (newPageSource.contains(LoginPageURL))) {
				HtmlReporter.WriteStep("Verify the Login tab on the Menu bar",
						"User should be redirected to Login Page ", "User is redirected to Login Page", true);
			} else {
				HtmlReporter.WriteStep("Verify the Login tab on the Menu bar",
						"User should be redirected to Login Page ", "User is not redirected to Login Page", false);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			HtmlReporter.WriteStep("Verify the Login tab on the Menu bar", "User should be redirected to Login Page ",
					"User is not redirected to Login Page", false);
		}

	}

	public static void Oxford_Webinar_Link() throws Exception {

		try {
			VTS_HomePage_POM.HomePage();
			WebDr.click("WebinarLink", "Click on Webinar CTA");
			WebDr.SwitchToLatestWindow();
			Thread.sleep(5000);
			String newPageSource = driver.getPageSource();
			String WebinarPageURL = "https://www.vts.com/blog/oxford-properties-coo-dean-hopkins-navigating-our-new-normal-webinar";
			if (WebDr.isElementExist("BlogPageLink", "Check if VTS Blog link exists")
					&& (newPageSource.contains(WebinarPageURL))) {
				HtmlReporter.WriteStep("Check the Oxford webinar link",
						"User should be redirected to VTS oxford blog page ", "User is redirected to VTS blog Page",
						true);
			} else {
				HtmlReporter.WriteStep("Check the Oxford webinar link",
						"User should be redirected to VTS oxford blog page  ",
						"User is not redirected to VTS blog Page", false);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			HtmlReporter.WriteStep("Check the Oxford webinar link",
					"User should be redirected to VTS oxford blog page  ", "User is not redirected to VTS blog Page",
					false);
		}
	}
	
	public static void Landlord_Link() throws Exception {

		try {
			VTS_HomePage_POM.HomePage();
			WebDr.click("LandlordLink", "Click on Landlord tile");
			WebDr.waitForPageLoaded();
			String newPageSource = driver.getPageSource();
			String LandlordPageURL = "https://www.vts.com/landlord";
			String LandlordPageHEader = WebDr.getText("LandlordPage_IntroHeading");
			if (!(LandlordPageHEader.isEmpty()) && (newPageSource.contains(LandlordPageURL))) {
				HtmlReporter.WriteStep("Verify the redirection for Landlords info tile",
						"User should be redirected to VTS Landlord page ", "User is redirected to VTS Landlord Page",
						true);
			} else {
				HtmlReporter.WriteStep("Verify the redirection for Landlords info tile",
						"User should be redirected to VTS  Landlord page  ",
						"User is not redirected to VTS Landlord Page", false);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			HtmlReporter.WriteStep("Verify the redirection for Landlords info tile",
					"User should be redirected to VTS Landlord page  ", "User is not redirected to VTS Landlord Page",
					false);
		}
	}
}
