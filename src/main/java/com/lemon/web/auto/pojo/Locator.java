package com.lemon.web.auto.pojo;

public class Locator {


    //元素的名称：描述信息
    private String name;
    //定位的方式
    private String type;
    //定位的值
    private String value;

    private String clickable;

    private String displayed;

    public String getClickable() {
        return clickable;
    }

    public void setClickable(String clickable) {
        this.clickable = clickable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDisplayed() {
        return displayed;
    }

    public void setDisplayed(String displayed) {
        this.displayed = displayed;
    }

    public Locator(String name, String type, String value, String clickable, String displayed) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.clickable = clickable;
        this.displayed = displayed;
    }

    @Override
    public String toString() {
        return "Locator{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", clickable='" + clickable + '\'' +
                ", displayed='" + displayed + '\'' +
                '}';
    }
}