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
    public static final String DB_NAME = "xxx-db";

    /**
     * 本地缓存文件夹相对总路径
     */
    public static final String CACHE_DIRECTORY = "okHttpCache";

    /**
     * 网络超时
     */
    public static final int DEFAULT_TIMEOUT = 30;
    /**
     * 图片最大缓存 1G
     */
    public static final long CACHE_IMAGE_MAX_SIZE = 1024 * 1024 * 1024;
    /**
     * 有网络时 设置图片缓存超时分钟数
     */
    public static final int MAX_IMAGE_AGE = 60 * 24 * 30;


    /**
     * 接口请求缓存文件夹
     */
    public static final String API_RESPONSES = CACHE_DIRECTORY + "/apiResponses";

    /**
     * 图片请求缓存文件夹
     */
    public static final String IMAGE_RESPONSES = CACHE_DIRECTORY + "/imageResponses";
}
