package com.example.myapplication;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class LHZAccessibilityService extends AccessibilityService {

    private static final String TAG = "LHZAccessibilityService";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "onAccessibilityEvent" + event.toString());
        // 此方法是在主线程中回调过来的，所以消息是阻塞执行的
        // 获取包名
//        String pkgName = event.getPackageName().toString();
//        int eventType = event.getEventType();
//        System.out.println("pkgName " + pkgName);
//        System.out.println("eventType " + eventType);
        // AccessibilityOperator封装了辅助功能的界面查找与模拟点击事件等操作
//        AccessibilityOperator.getInstance().updateEvent(this, event);
//        AccessibilityLog.printLog("eventType: " + eventType + " pkgName: " + pkgName);
//        switch (eventType) {
//            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
//                break;
//        }
        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        rootNodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
//        int count = rootNodeInfo.getChildCount();
//        for (int i = 0; i < count; i++) {
//            AccessibilityNodeInfo child = rootNodeInfo.getChild(i);
//            System.out.println(child);
//        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        System.out.println(" 111111111111             onServiceConnected");
        AccessibilityServiceInfo serviceInfo = new AccessibilityServiceInfo();
        serviceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        serviceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        serviceInfo.packageNames = new String[]{"com.tencent.mm"};
        serviceInfo.notificationTimeout = 100;
        setServiceInfo(serviceInfo);

    }

}
