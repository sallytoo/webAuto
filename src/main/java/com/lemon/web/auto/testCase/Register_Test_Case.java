package com.lemon.web.auto.testCase;

import com.lemon.web.auto.base.BaseTester;
import com.lemon.web.auto.util.ExcelUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



/*
怎么描述页面（描述页面的元素----》描述元素定位的方式、定位值）
 */
public class Register_Test_Case extends BaseTester {

    private static final Logger logger =Logger.getLogger(Register_Test_Case.class);

    @DataProvider
    public Object[][] getData() {
       /* Object[][] data = {
                {"", "", "", "用户名不能为空"},
                {"lemon", "", "", "非法的手机号"},
                {"18258442456", "", "", "密码不能为空"},
                {"18258442456", "123456", "123457", "密码不一致"}

        };*/
        return ExcelUtils.readExcel("/register.xlsx",1);
    }


    @DataProvider
    public Object[][] getSuccessData() {
        Object[][] data = {
                {"18211442450", "123455", "123455"},
                {"18211442451", "123455", "123455"},
                {"18221442451", "123455", "123455"},
                {"18211442451", "123455", "123456"},

        };
        return data;
    }


    /*
    痛点：元素定位的方式，定位的值可能会发生变化

     */
    //反向测试用例
    @Test(dataProvider = "getData" )
    public void test_case_1(String mobilephone, String password, String pwdConfirm, String expected) {

        to("register_url");
        type("手机输入框",mobilephone);
        logger.info("正在输入手机号");
        type("密码输入框",password);
        type("重复密码输入框",pwdConfirm);
        //输入万能验证码（可以叫开发GG搞一个）
        type("验证码输入框","LM19");
        click("注册按钮");
        //断言提示信息元素文本值为期望值
        asserTextPresent("提示信息元素",expected);


    }


    //正向测试用例
    @Test(dataProvider = "getSuccessData",enabled = false)
    public void test_case_1(String mobilephone, String password, String pwdConfirm) {
        to("register_url");
        type("手机输入框",mobilephone);
        type("密码输入框",password);
        type("重复密码输入框",pwdConfirm);
        //输入万能验证码（可以叫开发GG搞一个）
        type("验证码输入框","LM19");
        click("注册按钮");
        //判断是否到了登录页面 1：获得此时的URL 2:判断页面是否出现了登录按钮
        //断言
       // Assert.assertTrue(getDriver().getCurrentUrl().contains("login.html"));
  //      Assert.assertNotNull(getElement("登录按钮"));;//在注册页面找登录按钮找不着
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(getElementInPage(login_Test_Case.class,"登录按钮"));





    }


}
