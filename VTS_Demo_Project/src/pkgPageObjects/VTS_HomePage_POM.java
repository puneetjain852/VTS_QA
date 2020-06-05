package pkgPageObjects;

import java.util.HashMap;
import java.util.Map;

import utility.WebDr;

public class VTS_HomePage_POM {
	public static void MenuBar(){
		 Map<String, String> My_Page_Objects = new HashMap<String, String>();
		 My_Page_Objects.put("Login","XPATH|//a[@class = 'header-navigation__link change-app-link']");
		 My_Page_Objects.put("Login_Email", "XPATH|//input[@id='user_email']");
		 WebDr.page_Objects = My_Page_Objects;
	}
	
	
	public static void HomePage(){
		Map<String, String> My_Page_Objects = new HashMap<String, String>();
		 My_Page_Objects.put("WebinarLink","XPATH|//a[@id = 'Announcement CTA']");
		 My_Page_Objects.put("BlogPageLink","XPATH|//a[@class = 'blog-top__link']");
		 My_Page_Objects.put("LandlordLink","XPATH|//a[@id = 'Landlord Module']");
		 My_Page_Objects.put("LandlordPage_IntroHeading","XPATH|//h1[@class = 'intro__heading']");
		 WebDr.page_Objects = My_Page_Objects;
	
		 
	}
}
