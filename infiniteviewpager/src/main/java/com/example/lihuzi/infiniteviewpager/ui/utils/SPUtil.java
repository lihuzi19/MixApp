package com.example.lihuzi.infiniteviewpager.ui.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class SPUtil {

    private final static String TAG = "SpUtil";
    private static SharedPreferences sp = null;

    public static SPUtil getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private final static SPUtil INSTANCE = new SPUtil();
    }

    private SPUtil() {

    }

    public synchronized void init(Context context, File file) {
        if (sp == null) {
            printLog(file.getAbsolutePath());
            if (!file.exists()) {
                try {
                    boolean result = file.createNewFile();
                    if (!result) {
                        printLog("could not create sp file");
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
            try {
                Object contextWrapper = ((ContextWrapper) context).getBaseContext();
                if (contextWrapper instanceof ContextWrapper) {
                    Object contextImplObj = ((ContextWrapper) contextWrapper).getBaseContext();
                    printLog("getBaseContext is " + (contextImplObj.getClass().getSimpleName().equals("ContextImpl") ? "contextImplObj" : "not contextImplObj"));
                    printLog(contextImplObj.getClass().getName());
                    Method getSPMethod = null;
                    for (Method method : contextImplObj.getClass().getMethods()) {
                        if (method.getName().equals("getSharedPreferences")) {
                            if (method.getParameterTypes()[0].equals(File.class)) {
                                printLog("get right method");
                                getSPMethod = method;
                                break;
                            } else {
                                printLog("method parameter is wrong");
                            }
                        }
                    }
                    if (getSPMethod == null) {
                        for (Method method : contextImplObj.getClass().getDeclaredMethods()) {
                            if (method.getName().equals("getSharedPreferences")) {
                                if (method.getParameterTypes()[0].equals(File.class)) {
                                    getSPMethod = method;
                                    printLog("get right method");
                                    break;
                                } else {
                                    printLog("method parameter is wrong");
                                }
                            }
                        }
                    }
                    if (getSPMethod == null) {
                        getSPMethod = contextImplObj.getClass().getMethod("getSharedPreferences", File.class, Integer.class);
                    }
                    Object resultObj = getSPMethod.invoke(contextImplObj, file, Context.MODE_PRIVATE);
                    if (resultObj instanceof SharedPreferences) {
                        sp = (SharedPreferences) resultObj;
                        printLog("create sp success");
                    } else {
                        printLog("resultObj is not SharedPreferences");
                    }
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                printLog("call method wrong");
                e.printStackTrace();
            }
        }
    }

    private static void printLog(String content) {
        Log.e(TAG, "---------------------------" + content);
    }

    @SuppressLint("CommitPrefEdits")
    public void addBoolean(String key, Boolean value) {
        if (sp == null) {
            return;
        }
        sp.edit().putBoolean(key, value).apply();
    }

    @SuppressLint("CommitPrefEdits")
    public void addString(String key, String value) {
        if (sp == null) {
            return;
        }
        sp.edit().putString(key, value).apply();
    }

    public String getString(String key, String defaultValue) {
        if (sp == null) {
            return null;
        }
        return sp.getString(key, defaultValue);
    }
    public String getString(String key) {
        return getString(key, null);
    }
}
