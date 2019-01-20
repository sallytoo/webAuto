package com.lemon.web.auto.util;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class ScreenShotListener extends TestListenerAdapter {
    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        //失败的时候会截图保存下来
        SeleniumUtils.takeScreenshot(tr);
    }
}
