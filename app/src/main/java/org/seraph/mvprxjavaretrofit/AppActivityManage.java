package org.seraph.mvprxjavaretrofit;

import android.app.Activity;

import org.seraph.mvprxjavaretrofit.data.network.rx.RxDisposableHelp;

import java.util.Stack;

/**
 * AppActivity管理
 * date：2017/7/31 14:35
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class AppActivityManage {

    private static Stack<Activity> stack = new Stack<>();

    private static AppActivityManage instance;

    private AppActivityManage() {
    }

    public static AppActivityManage getInstance() {
        if (instance == null) {
            instance = new AppActivityManage();
        }
        return instance;
    }

    /**
     * 添加到stack
     */
    public void addActivity(Activity activity) {
        stack.add(activity);
    }

    /**
     * 关闭activity
     */
    public void closeActivity(Activity activity) {
        if (activity != null) {
            stack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 关闭所有的Activity
     */
    public void closeAllActivity() {
        for (Activity activity : stack) {
            if (activity != null) {
                activity.finish();
            }
        }
        stack.clear();
    }


    /**
     * 获取管理栈
     */
    public Stack<Activity> getStack() {
        return stack;
    }

    /**
     * 获取当前栈顶的Activity
     */
    public Activity currentActivity() {
        return stack.lastElement();
    }


    /**
     * 退出程序
     */
    public void appExit() {
        RxDisposableHelp.dispose();
        closeAllActivity();
        System.exit(0);
    }

}
