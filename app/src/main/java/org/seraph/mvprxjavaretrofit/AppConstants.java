package org.seraph.mvprxjavaretrofit;

/**
 * 对应项目通用常量
 * date：2017/11/13 10:51
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class AppConstants {

    /**
     * 偏好常量
     **/
    public class SPAction {

        public static final String SP_NAME = "seraph";

        /**
         * 是否第一次进入APP
         */
        public static final String IS_FIRST = "is_first";

        /**
         * 是否推送
         */
        public static final String IS_PUSH = "is_push";

        /**
         * 保存登录用户的账号密码
         */
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
    }

    /**
     * RxBus事件总线Tags
     */
    public final class RxBusAction{

        //首页菜单跳转
        public final static String TAG_MAIN_MENU = "MAIN_MENU";

        //登录信息更新
        public static final String TAG_LOGIN = "LOGIN";
    }

}
