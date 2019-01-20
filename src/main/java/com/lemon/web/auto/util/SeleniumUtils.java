package com.lemon.web.auto.util;

import com.lemon.web.auto.base.BaseTester;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.util.Date;


public class SeleniumUtils {
        private static final String CHROME_BROWSER_NAME = "chrome";
        private static final String FIREFOX_BROWSER_NAME = "firefox";
        private static final String SELENIUM_VERSION_3_X = "3.x";
        private static final String SELENIUM_VERSION_2_X = "2.x";
        /*
         * 根据浏览器类型，获得对应的驱动对象
         */

        public static WebDriver getWebDriver(String broswerType, String seleniumVersion, String driverExePath) {
            if (CHROME_BROWSER_NAME.equalsIgnoreCase(broswerType)) {
                return getChromeDriver(driverExePath);
            } else if (FIREFOX_BROWSER_NAME.equalsIgnoreCase(broswerType)) {
                return getFirefoxDriver(seleniumVersion, driverExePath);
            } else if ("safari".equalsIgnoreCase(broswerType)) {
                return getSafariDriver();
            } else {
            }
            return null;

        }

        /*
         * 根据浏览器类型，获得对应的驱动对象。默认是采用2.x的selenium
         */
        public static WebDriver getWebDriver(String broswerType, String driverExePath) {
            return getWebDriver(broswerType, SELENIUM_VERSION_2_X, driverExePath);
        }

        /*
         * 获得Safari的驱动对象
         */
        private static WebDriver getSafariDriver() {
            return new SafariDriver();
        }

        /*
         * 获得Chrome的驱动对象
         */
        private static WebDriver getChromeDriver(String driverExePath) {
            System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, driverExePath);
            //在创建驱动的时间，增加一些能力
            DesiredCapabilities capabilities = new DesiredCapabilities();
            //忽觉安全域的设值
            capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            //忽略浏览器缩放
            capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
            return new ChromeDriver();
        }

        /*
         * 获得Firefox的驱动对象
         */
        private static WebDriver getFirefoxDriver(String seleniumVersion, String driverExePath) {
            //3.X的版本需要驱动路径
            if (SELENIUM_VERSION_3_X.equals(seleniumVersion)) {
                System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, driverExePath);
            }
            return new FirefoxDriver();
        }


        /**
         * 截图的方法，并把图保存在指定的地方
         */
        public static  void  takeScreenshot (ITestResult tr){
            WebDriver drive = BaseTester.getDriver();
            TakesScreenshot takesScreenshot = (TakesScreenshot) drive;
            File srcFile=takesScreenshot.getScreenshotAs(OutputType.FILE);
            File desFile=new File("/Users/sally/Idea/webAuto/test-output/screenshot/"+new Date().getTime()+".jpg");
            try {
                //把文件保存到指定的地方


                FileUtils.copyFile(srcFile,desFile);
            } catch (IOException e) {
                e.printStackTrace();
            }


}




    }
