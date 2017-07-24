package org.seraph.mvprxjavaretrofit;

import android.Manifest;

/**
 * app的一些常量设置
 * date：2017/2/22 09:47
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class AppConfig {

    /**
     * 本地数据库名称
     */
    public static final String DB_NAME = "seraph-db";

    /**
     * 网络超时
     */
    public static final int DEFAULT_TIMEOUT = 30;
    /**
     * 图片最大缓存 1G
     */
    public static final long CACHE_IMAGE_MAX_SIZE = 1024 * 1024 * 1024;
    /**
     * 图片保存文件夹名称
     */
    public static final String SAVE_FOLDER_NAME = "Seraph";

    /**
     * 本地偏好文件名字
     */
    public static final String PREFERENCES_NAME = "Seraph";

    /**
     * 权限请求code
     */
    public static final int CODE_REQUEST_PERMISSIONS = 1000;
    /**
     * 是否在debug模式
     */
    public static final boolean DEBUG = true;

    /**
     * 是否启用证书
     */
    public static final boolean IS_ENABLED_CER= false;
    /**
     * 使用的证书名称
     */
    public static final String HTTPS_CER_NAME = "srca12306.cer";

    /**
     * 权限请求code1
     */
    public static final int PERMISSIONS_CODE_REQUEST_1 = 1000;
    /**
     * 权限请求code2
     */
    public static final int PERMISSIONS_CODE_REQUEST_2 = 1001;

    /**
     * 读写SD卡权限
     */
    public static final String[] PERMISSIONS_SDCARD = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

}
