package com.example.lunanting.swiperefreshdemo;

/**
 * Created by lunanting on 2016/10/12.
 */

public class ItemBean {
    private String mString;
    private boolean item;

    public ItemBean(String string, boolean item) {
        mString = string;
        this.item = item;
    }

    public String getString() {
        return mString;
    }

    public void setString(String string) {
        mString = string;
    }

    public boolean isItem() {
        return item;
    }

    public void setItem(boolean item) {
        this.item = item;
    }
}
