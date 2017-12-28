package org.seraph.mvprxjavaretrofit;

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
     * 最大缓存 1G
     */
    public static final long CACHE_MAX_SIZE = 1024 * 1024 * 1024;
    /**
     * 图片保存文件夹名称
     * 为相册文件夹{@link android.os.Environment#DIRECTORY_DCIM}中的子文件夹名
     */
    public static final String SAVE_IMAGE_FOLDERS_NAME = "Seraph";


    /**
     * 是否在debug模式
     */
    public static final boolean DEBUG = true;


    /**
     * HTTPS访问证书名称
     * 设置放在Assets文件夹下的证书的路径,如果不需要设置证书。此处路径使用 null 或者 ""
     * xxx.cer
     */
    public static final String HTTPS_CER_NAME = "";

    /**
     * 设置放在Assets文件夹下的字体的路径,如果不需要设置字体。此处路径使用 null 或者 ""
     * fonts/xxx.ttc
     */
    public static final String FONTS_ASSETS_DIR = "fonts/hksn.ttc";


}
