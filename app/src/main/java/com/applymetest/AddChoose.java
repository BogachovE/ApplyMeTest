package com.applymetest;

import android.content.Context;

import org.jdeferred.Promise;


public interface AddChoose {
    Promise<String, String, String> addChoose(Context context, String name);
    void updateDeferred();
}
