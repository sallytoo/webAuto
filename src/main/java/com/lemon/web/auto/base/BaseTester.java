package com.lemon.web.auto.base;

import com.lemon.web.auto.pojo.Locator;
import com.lemon.web.auto.util.PagesUtils;
import com.lemon.web.auto.util.PropertiesUtils;
import com.lemon.web.auto.util.SeleniumUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.lang.reflect.Method;
import java.util.List;

public abstract class BaseTester {
    private static WebDriver driver;


    public static WebDriver getDriver() {
        return driver;
    }
    private static final Logger logger =Logger.getLogger(BaseTester.class);



    //在执行用例之前创造一个驱动
    @BeforeSuite
    @Parameters(value = {"broswerType", "seleniumVersion", "driverExepath"})
    public void beforeSuite(String broswerType, String seleniumVersion, String driverExepath) {
        driver = SeleniumUtils.getWebDriver(broswerType, seleniumVersion, driverExepath);
    }


    //在执行用例最后休眠2秒后退出浏览器
    @AfterSuite
    public void afterSuite() throws InterruptedException {
        Thread.sleep(4000);
        driver.quit();
    }


    //打开某个URL（网址）
    public static void to(String urlKey) {
        driver.get(PropertiesUtils.getUrl(urlKey));
    }


    //刷新
    public void  refresh(){
        driver.navigate().refresh();
    }

    //回退
    public void back(){
        driver.navigate().back();
    }

    //前进
    public void forward(){
        driver.navigate().forward();
    }

    //窗口最大化
    public void maxmize(){
        driver.manage().window().maximize();
    }


    public WebElement getElement(String keyword) {
        //要知道一个页面某元素的定位信息，只要知道1：页面的名称，2：元素的名称
        //获得子类的类名---》页面的名称---》通过页面名称可以找到对应页面的所有定位信息
        //1、获得当前页面的名称---》获得指定页面名称
        return getElementInPage(this.getClass(), keyword);
    }


    /**
     * @param pageClazz 指定页面对应的类的字节码
     * @param keyword   元素关键描述信息
     * @return 定位到元素
     */


    /* if ("id".equals(type)){
            by= By.id(value);
        }else if("name".equals(type)){
            by= By.name(value);
        }else if("className".equals(type)){
            by= By.className(value);
        }else if("cssSelector".equals(type)){
            by= By.cssSelector(value);
        }else if("linkText".equals(type)){
            by= By.linkText(value);
        }else if("partialLinkText".equals(type)){
            by= By.partialLinkText(value);
        }else if("tagName".equals(type)){
            by= By.tagName(value);
        }else if("xpath".equals(type)){
            by= By.xpath(value);
        }else {
            System.out.println("不存在这样的定位方式" );
        }*/
    public WebElement getElementInPage(Class pageClazz, String keyword) {

        String pageName = pageClazz.getSimpleName();
        Locator locator = PagesUtils.getLocatorByPageNameAndElementName(pageName, keyword);

        String type = locator.getType();
        String value = locator.getValue();
        String clickable = locator.getClickable();
        String displayed = locator.getDisplayed();

        WebElement element = null;
        try {
            Class byClazz = By.class;
            Method byMethod = byClazz.getMethod(type, String.class);
            By by = (By) byMethod.invoke(null, value);
            if (displayed != null) {
                ExpectedConditions.visibilityOfElementLocated(by);
                element = driver.findElement(by);
            } else if (clickable != null) {
                ExpectedConditions.elementToBeClickable(by);
                element = driver.findElement(by);
            } else {//智能等待的方法
                WebDriverWait wait = new WebDriverWait(driver, 5);
                element = wait.until(new ExpectedCondition<WebElement>() {

                    @Override
                    public WebElement apply(WebDriver input) {

                        return driver.findElement(by);
                    }
                });

            }
        } catch (Exception e) {
            // TODO日志

        }
        return element;

    }


    /**
     * 定位元素列表
     *
     * @param pageClazz 测试类名（页面的标识）
     * @param keyword   关键字
     * @return
     */
    public List<WebElement> getElemenstInPage(Class pageClazz, String keyword) {
       return getElemenstInPage(pageClazz,keyword,5);

    };


    public List<WebElement> getElemenstInPage(Class pageClazz, String keyword,long timeInSecond) {

        //最后调用到这实例方法的事哪个类型的对象
        String pageName = pageClazz.getSimpleName();
        Locator locator = PagesUtils.getLocatorByPageNameAndElementName(pageName, keyword);

        String type = locator.getType();
        String value = locator.getValue();
        //是否要等待可点击
        String clickable = locator.getClickable();
        //是否要等待可显示
        String displayed = locator.getDisplayed();


        List<WebElement> elementList = null;
        try {
            Class byClazz = By.class;
            Method byMethod = byClazz.getMethod(type, String.class);
            By by = (By) byMethod.invoke(null, value);
            if (displayed != null) {
                ExpectedConditions.visibilityOfElementLocated(by);
                elementList = driver.findElements(by);
            } else if (clickable != null) {
                ExpectedConditions.elementToBeClickable(by);
                elementList = driver.findElements(by);
            } else {//智能等待的方法
                WebDriverWait wait = new WebDriverWait(driver, timeInSecond);
                elementList = wait.until(new ExpectedCondition<List<WebElement>>() {

                    @Override
                    public List<WebElement> apply(WebDriver input) {

                        return driver.findElements(by);
                    }
                });

            }
        } catch (Exception e) {
            // TODO日志
            logger.error("定位信息出错");

        }
        return elementList;

    }


    public void type(String keyword, String content) {
        logger.info("往"+keyword+"输入:"+content);
        getElement(keyword).sendKeys(content);
    }


    public void click(String keyword) {
        getElement(keyword).click();
    }


    public String getTest(String keyword) {
        return getElement(keyword).getText();
    }



    /**统一的检查点技术
     * 断言页面元素文本值为某文本
     * @param keyword 关键字
     * @param expectedText 期望的文本
     */
    public void asserTextPresent(String keyword,String expectedText){
        String actualText = getTest(keyword);
        Assert.assertEquals(actualText,expectedText);

    }

    /**
     * 断言页面元素文本值包含某文本
     * @param keyword  关键字
     * @param expectedText 要包含的文本
     */
    public void assertPartialTextPresent(String keyword,String expectedText){
        Assert.assertTrue(getTest(keyword).contains(expectedText));
    }

}
