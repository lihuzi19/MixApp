package com.example.myapplication;//package com.example.lihuzi.cleanup;
//
//import android.app.ActivityManager;
//import android.app.usage.UsageStats;
//import android.app.usage.UsageStatsManager;
//import android.content.Context;
//import android.content.pm.ApplicationInfo;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.os.Debug;
//import android.util.Log;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static android.content.ContentValues.TAG;
//
//public class ProcessUtils {
//    public static int size;
//
//    /**
//     * 获取进程数的方法
//     * @param context
//     * @return
//     */
//    public static int getProcessCount(Context context){
//        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            size = getTopApp(context);
//        }else {
//            ActivityManager activityManager= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//            //获取进程的集合
//            runningAppProcesses= activityManager.getRunningAppProcesses();
//            size = runningAppProcesses.size();
//
//        }
//        return size;
//
//    }
//
//    /**
//     * 6.0获取进程数
//     * @param context
//     * @return
//     */
//    private static int getTopApp(Context context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            UsageStatsManager m = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
//            if (m != null) {
//                long now = System.currentTimeMillis();
//                //获取60秒之内的应用数据
//                List<UsageStats> stats = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 60 * 1000, now);
//                Log.i(TAG, "Running app number in last 60 seconds : " + stats.size());
//                String topActivity = "";
//                //取得最近运行的一个app，即当前运行的app
//                if ((stats != null) && (!stats.isEmpty())) {
//                    int j = 0;
//                    for (int i = 0; i < stats.size(); i++) {
//                        if (stats.get(i).getLastTimeUsed() > stats.get(j).getLastTimeUsed()) {
//                            j = i;
//                        }
//                        topActivity = stats.get(j).getPackageName();
//                        Log.i(TAG, "top running app is : "+topActivity);
//                    }
//
//                }
//                return stats.size();
//            }
//        }
//        return 0;
//    }
//
//    /**
//     *获取栈顶的包名
//     */
//
//    public static String getTopActivityApp(Context context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            UsageStatsManager m = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
//            if (m != null) {
//                long now = System.currentTimeMillis();
//                //获取60秒之内的应用数据
//                List<UsageStats> stats = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 60 * 1000, now);
//                Log.i(TAG, "Running app number in last 60 seconds : " + stats.size());
//
//                String topActivity = "";
//
//                //取得最近运行的一个app，即当前运行的app
//                if ((stats != null) && (!stats.isEmpty())) {
//                    int j = 0;
//                    for (int i = 0; i < stats.size(); i++) {
//                        if (stats.get(i).getLastTimeUsed() > stats.get(j).getLastTimeUsed()) {
//                            j = i;
//                        }
//                    }
//                    topActivity = stats.get(j).getPackageName();
//                    return topActivity;
//                }
//                Log.i(TAG, "top running app is : "+topActivity);
//            }
//        }
//        return null;
//    }
//    /**
//     * 获取可用内存的数据大小 ，单位为byte
//     * @param context
//     * @return
//     */
//    public static long getAvailSpace(Context context){
//        //获取activityManager
//        ActivityManager activityManager= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        //构建可用内存对象
//        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
//
//
//
//        activityManager.getMemoryInfo(memoryInfo);
//
//        long availMem = memoryInfo.availMem;
//        return availMem;
//    }
//
//    public static long getTotalSpace(Context context){
//        //获取activityManager
//        ActivityManager activityManager= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        //构建可用内存对象
//        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
//
//        activityManager.getMemoryInfo(memoryInfo);
//
//        return memoryInfo.totalMem;
//    }
//
//    /**
//     * 获取全部的内存空间
//     * @return
//     */
//    public static long getTotalSpace(){
//        FileReader fileReader=null;
//        BufferedReader bufferedReader=null;
//        try {
//            fileReader=new FileReader("proc/meminfo");
//
//            bufferedReader=new BufferedReader(fileReader);
//
//            String lineOne = bufferedReader.readLine();
//
//            char[] chars = lineOne.toCharArray();
//            StringBuffer stringBuffer = new StringBuffer();
//            for (char aChar : chars) {
//                if (aChar>='0'&&aChar<='9'){
//                    stringBuffer.append(aChar);
//                }
//            }
//            return Long.parseLong(stringBuffer.toString())*1024;
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }finally {
//            if (fileReader!=null&&bufferedReader!=null){
//                try {
//                    bufferedReader.close();
//                    fileReader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return 0;
//    }
//
//    /**
//     * 获取进程的信息
//     * @param context
//     * @return
//     */
//    public  static List<ProcessInfoBean> getProcessInfo(Context context){
//
//        ArrayList<ProcessInfoBean> processInfoList = new ArrayList<>();
//        ActivityManager systemService = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//
//        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = systemService.getRunningAppProcesses();
//
//        PackageManager PM = context.getPackageManager();
//
//        for (ActivityManager.RunningAppProcessInfo runningAppProcess : runningAppProcesses) {
//
//            ProcessInfoBean processInfo=new ProcessInfoBean();
//
//            processInfo.packageName= runningAppProcess.processName;
//
//            //获取系统占用的内存大小
//            int pid = runningAppProcess.pid;
//            Debug.MemoryInfo[] processMemoryInfo = systemService.getProcessMemoryInfo(new int[]{pid});
//
//            //获取已使用的大小：
//            processInfo.memeSize= processMemoryInfo[0].getTotalPrivateDirty()*1024;
//
//            //获取应用的名称
//            try {
//                ApplicationInfo applicationInfo = PM.getApplicationInfo(processInfo.getPackageName(), 0);
//
//                processInfo.name= applicationInfo.loadLabel(PM).toString();
//
//                processInfo.icon= applicationInfo.loadIcon(PM);
//
//                if ((applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)==ApplicationInfo.FLAG_SYSTEM){
//                    processInfo.isSystem=true;
//                }else {
//                    processInfo.isSystem=false;
//                }
//            } catch (PackageManager.NameNotFoundException e) {
//                processInfo.name=processInfo.packageName;
//                processInfo.icon=context.getResources().getDrawable(R.mipmap.ic_launcher);
//                processInfo.isSystem=true;
//                e.printStackTrace();
//            }
//            processInfoList.add(processInfo);
//        }
//        return processInfoList;
//    }
//
//    /**
//     * 6.0版本获取相应的进程信息
//     * @param context
//     * @return
//     */
//    public static List<ProcessInfoBean> getProcess6Info(Context context){
//        ArrayList<ProcessInfoBean> processInfoList = new ArrayList<>();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            UsageStatsManager m = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
//            if (m != null) {
//                long now = System.currentTimeMillis();
//                //获取60秒之内的应用数据
//                List<UsageStats> stats = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 60 * 1000, now);
//                ActivityManager systemService = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//                Log.i(TAG, "Running app number in last 60 seconds : " + stats.size());
//                //取得最近运行的一个app，即当前运行的app
//                if ((stats != null) && (!stats.isEmpty())) {
//                    for (int i = 0; i < stats.size(); i++) {
//                       /* if (stats.get(i).getLastTimeUsed() > stats.get(j).getLastTimeUsed()) {
//                            j = i;
//                        }*/
//                        int i1 = stats.get(i).describeContents();
//                        String processName = stats.get(i).getPackageName();
//                        Log.i(TAG, "top running app is : " + processName);
//                        PackageManager PM = context.getPackageManager();
//                        ProcessInfoBean processInfo=new ProcessInfoBean();
//                        int uidForName = Process.getUidForName(processName);
//                        /***
//                         * 此方法未能成功获取进程的内存信息
//                         */
//                        Debug.MemoryInfo[] processMemoryInfo = systemService.getProcessMemoryInfo(new int[]{uidForName});
//                        //获取已使用的大小：
//                        processInfo.memeSize= processMemoryInfo[0].getTotalPrivateDirty()*1024;
//                        processInfo.packageName= processName;
//                        processInfo.appPid=uidForName;
//                        //获取应用的名称
//                        try {
//                            ApplicationInfo applicationInfo = PM.getApplicationInfo(processInfo.getPackageName(), 0);
//
//                            processInfo.name= applicationInfo.loadLabel(PM).toString();
//
//                            processInfo.icon= applicationInfo.loadIcon(PM);
//
//                            if ((applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)== ApplicationInfo.FLAG_SYSTEM){
//                                processInfo.isSystem=true;
//                            }else {
//                                processInfo.isSystem=false;
//                            }
//                        } catch (PackageManager.NameNotFoundException e) {
//                            processInfo.name=processInfo.packageName;
//                            processInfo.icon=context.getResources().getDrawable(R.mipmap.ic_launcher);
//                            processInfo.isSystem=true;
//                            e.printStackTrace();
//                        }
//                        processInfoList.add(processInfo);
//                    }
//                }
//            }
//        }
//
//        return processInfoList;
//    }
//
//}
