package org.seraph.mvprxjavaretrofit.preference;

/**
 * app的一些常量设置
 * date：2017/2/22 09:47
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class AppConstant {

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
     * 权限请求code
     */
    public static final int CODE_REQUEST_PERMISSIONS = 1000;
}
