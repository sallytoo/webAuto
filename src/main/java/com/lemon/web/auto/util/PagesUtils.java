package com.lemon.web.auto.util;

import com.lemon.web.auto.pojo.Locator;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PagesUtils {

    private static Map<String, Map<String, Locator>> allPagesMap;
    static {
        allPagesMap=loadLocators();

    }

    /**
     * 给定一个页面名称，返回该页面所有元素定位信息
     * pagesName 页面的名称
     * @return
     */

    public static Map<String,Locator> getLocatorByPageName(String pageName){
        return allPagesMap.get(pageName);
    }

    /**
     *给定一个页面的名称和元素名称，找到该页面也元素的定位信息
     * @param pageName 页面的名称
     * @param elementName 元素的名称：元素基本的描述信息
     * @return
     */
    public static Locator getLocatorByPageNameAndElementName(String pageName,String elementName){
        return getLocatorByPageName(pageName).get(elementName);


    }


   public static Map<String,Map<String,Locator>> loadLocators() {
       // SAXReader saxReader = new SAXReader();
        try {
            //读取出整个文档
           // Document document = saxReader.read(PagesUtils.class.getResourceAsStream("/pages.xml"));
            File f =new File("src/main/resources/pages.xml");
            SAXReader reader = new SAXReader();
            Document document = reader.read(f);
            Element rootElement = document.getRootElement();
            List<Element> pages = rootElement.elements("page");

            //获取根元素
           // Element rootElement = document.getRootElement();
            // System.out.println(rootElement );
            //获得每个page子元素
           // List<Element> pages = rootElement.elements("page");

            //创建一个容器，把所有页面的locatorMap保存起来
            //给我一个页面的名称，得到该页面所有的定位信息
            //给我登录页面的所有元素信息
            Map<String, Map<String, Locator>> allPagesMap = new HashMap< >();

            //遍历每个page

            for(Element page : pages){
                //获得这个页面的名称
                String pageName = page.attributeValue("name");
                //遍历每一个定位器，获取对应元素的定位信息
                List<Element> locators = page.elements("locator");
                //创建一个容器，存放当前页面的所有的定位信息

                Map<String, Locator> locatorMap = new HashMap<>();
                for (Element locatorElement : locators) {
                    //1:描述信息
                    String name = locatorElement.attributeValue("name");
                    //2:定位的方式
                    String type = locatorElement.attributeValue("type");
                    //3:定位的值
                    String value = locatorElement.attributeValue("value");
                    //4.是否需要点击
                    String clickable = locatorElement.attributeValue("clickable");
                    //5.是否需要等待显示
                    String displayed = locatorElement.attributeValue("displayed");



                    //对于上面一系列信息--->放到内存中间什么类型的变量去？
                    //如果这些属性名称会变化---》map
                    //如果这些属性名不会变化---》类
                    //locator一个元素的定位信息
                    //告诉我元素的名称，然后返回给我对应的元素信息--》map
                    Locator locator = new Locator(name,type,value,clickable,displayed);
                    locatorMap.put(name,locator);
                }
                allPagesMap.put(pageName, locatorMap);

            }

            return allPagesMap;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    public static void main(String[] args) {
        Map<String, Map<String, Locator>> allPagesMap = loadLocators();
        /*Set<String> keyset = allPagesMap.keySet();
        for (String key : keyset) {

            Map<String,Locator> locatorMap = allPagesMap.get(key);
            Set<String> locatorKeySet = locatorMap.keySet();
            for (String locatorSet:locatorKeySet){
                 System.out.println(locatorSet+"--->"+locatorMap.get(locatorSet) );

            }
             System.out.println("-----------------------" );
        }*/
        //给我登录页面登录按钮的定位信息
//        Locator locator=allPagesMap.get("登录页面").get("登录按钮");
//        System.out.println(locator );

//        Locator locator = PagesUtils.getLocatorByPageNameAndElementName("登录页面","手机输入框");
//         System.out.println(locator);


    }

}
