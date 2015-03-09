package com.smarts.selenium.phantomjs;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.smarts.selenium.phantomjs.driver.PhantomJSDriver;
import com.smarts.selenium.phantomjs.driver.PhantomJSDriverService;

public class PhantomJsFacetory{

	private static DesiredCapabilities scaps;
	private static Properties sconfig;
	private static final String DRIVER_PHANTOMJS = "phantomjs";
	static{
		   sconfig = new Properties();
	       try {
			sconfig.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("Config.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	        
		scaps = new DesiredCapabilities();
		scaps.setJavascriptEnabled(true);
		scaps.setCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS, true);
		scaps.setCapability(CapabilityType.TAKES_SCREENSHOT, false);
	            // "phantomjs_exec_path"
	            if (sconfig.getProperty("phantomjs.exec.path") != null) {
	                scaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, sconfig.getProperty("phantomjs_exec_path"));
	            } else {
	                try {
						throw new IOException(String.format("Property '%s' not set!", PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY));
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	            // "phantomjs_driver_path"
	            if (sconfig.getProperty("phantomjs.driver.path") != null) {
	                System.out.println("Test will use an external GhostDriver");
	                scaps.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_PATH_PROPERTY, sconfig.getProperty("phantomjs_driver_path"));
	            } else {
	                System.out.println("Test will use PhantomJS internal GhostDriver");
	            }

	        // Disable "web-security", enable all possible "ssl-protocols" and "ignore-ssl-errors" for PhantomJSDriver
//	        sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] {
//	            "--web-security=false",
//	            "--ssl-protocol=any",
//	            "--ignore-ssl-errors=true"
//	        });
	        ArrayList<String> cliArgsCap = new ArrayList<String>();
	        cliArgsCap.add("--web-security=false");
	        cliArgsCap.add("--ssl-protocol=any");
	        cliArgsCap.add("--ignore-ssl-errors=true");
	        scaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);

	        // Control LogLevel for GhostDriver, via CLI arguments
	        scaps.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS, new String[] {
	            "--logLevel=" + (sconfig.getProperty("phantomjs_driver_loglevel") != null ? sconfig.getProperty("phantomjs_driver_loglevel") : "INFO")
	        });
		scaps.setCapability("phantomjs.page.settings.userAgent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.93 Safari/537.36");
	}
	
	public static WebDriver getDriver() throws MalformedURLException{
        String driver = sconfig.getProperty("driver", DRIVER_PHANTOMJS);
        WebDriver mDriver;
        // Start appropriate Driver
        if (isUrl(driver)) {
            scaps.setBrowserName("phantomjs");
            mDriver = new RemoteWebDriver(new URL(driver), scaps);
        } else{
            mDriver = new PhantomJSDriver(scaps);
        }
        return mDriver;
	}
	
	 private static boolean isUrl(String urlString) {
	        try {
	            new URL(urlString);
	            return true;
	        } catch (MalformedURLException mue) {
	            return false;
	        }
	    }
	public static DesiredCapabilities phantomJs(){
		return scaps;
	}
}
