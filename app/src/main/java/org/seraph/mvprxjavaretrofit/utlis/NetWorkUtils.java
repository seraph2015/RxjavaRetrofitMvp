package org.seraph.mvprxjavaretrofit.utlis;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络工具
 * date：2017/3/15 14:55
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class NetWorkUtils {

    /**
     * 判断是否有网络连接
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 获取当前的网络状态 ：没有网络-1   移动网络0   WIFI网络1
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = connMgr.getActiveNetworkInfo();
        if (mNetworkInfo == null || !mNetworkInfo.isAvailable()) {
            return -1;
        }
        return mNetworkInfo.getType();
    }


}
