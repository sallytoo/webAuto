package com.lemon.web.auto.testCase;

import com.lemon.web.auto.base.BaseTester;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class login_Test_Case extends BaseTester {
    public static final Logger logger =Logger.getLogger(login_Test_Case.class);


    public static void main(String[] args) {
         System.out.println(login_Test_Case.class.getSimpleName() );

        }

    @DataProvider
    public Object[][]getData(){
        Object[][] data={
                {"","","用户名不能为空"},
                {"lemon","","非法的手机号"},
                {"18258442456","","密码不能为空"},

        };
        return data;
    }

    @Test(dataProvider = "getData")
    public void test_case_1(String mobilephone, String password,String expected){
        to("login_url");
        type("手机输入框",mobilephone);
        type("密码输入框",password);
        click("登录按钮");
        assertPartialTextPresent("提示信息元素",expected);
        logger.info("验证断言");

    }


}
