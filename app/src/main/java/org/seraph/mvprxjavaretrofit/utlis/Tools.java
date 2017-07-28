package org.seraph.mvprxjavaretrofit.utlis;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import org.seraph.mvprxjavaretrofit.AppConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具类
 */
@SuppressLint("SimpleDateFormat")
public class Tools {

    /**
     * 格式化价格(保留小数点后两位)
     */
    public static String getFloatDotStr(String argStr) {
        float arg = Float.valueOf(argStr);
        DecimalFormat fnum = new DecimalFormat("##0.00");
        return fnum.format(arg);
    }

    /**
     * 网络是否可用
     */
    public static boolean IsInternetValidate(final Context context) {
        try {
            ConnectivityManager manger = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manger.getActiveNetworkInfo();
            return (info != null && info.isConnected());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 检测sdcard是否可用
     *
     * @return true为可用，否则为不可用
     */
    public static boolean sdCardIsAvailable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return true;
        }
        return false;
    }


    /**
     * 得到应用版本名
     */
    public static String getVersionName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;

    }

    /**
     * 得到应用版本号
     */
    public static int getVersionCode(Context context) {
        int verCode = 0;
        try {
            verCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }


    /**
     * 判断 多个字段的值否为空
     */
    public static boolean isNull(String... ss) {
        for (int i = 0; i < ss.length; i++) {
            if (null == ss[i] || ss[i].equals("")
                    || ss[i].equalsIgnoreCase("null")) {
                return true;
            }
        }
        return false;
    }


    /**
     * 验证手机号码
     */
    public static boolean validatePhone(String phone) {
        if (isNull(phone))
            return false;
        if (phone.length() != 11) {
            return false;
        }
        String pattern = "^1[3,4,5,6,7,8,9]+\\d{9}$";
        return phone.matches(pattern);
    }


    /**
     * 验证邮箱
     */
    public static boolean validateEmail(String email) {
        if (isNull(email))
            return false;
        String pattern = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
        return email.matches(pattern);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 转md5
     */
    public static String getMD5(String url) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(url.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                String str = Integer.toHexString(b & 0xFF);
                if (str.length() == 1) {
                    sb.append("0");
                }
                sb.append(str);
            }
            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * bitmap转文件
     */
    public static void bitmapToFile(Bitmap mBitmap, File file) throws IOException {
        // String url = MediaStore.Images.Media.insertImage(mView.getContext().getContentResolver(), bitmap, "title", "description");
        FileOutputStream outputStream = new FileOutputStream(file);
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 获取相册文件路径
     */
    public static File getDCIMFile(String imageName) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) { // 文件可用
            File dirs = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), AppConfig.SAVE_IMAGE_FOLDERS_NAME);
            if (!dirs.exists())
                dirs.mkdirs();
            File file = new File(dirs, imageName);
            if (!file.exists()) {
                try {
                    //在指定的文件夹中创建文件
                    file.createNewFile();
                } catch (Exception e) {
                }
            }
            return file;
        } else {
            return null;
        }
    }

    /**
     * 扫描相册对应文件
     */
    public static void scanAppImageFile(Context context, String fileName) {
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" + AppConfig.SAVE_IMAGE_FOLDERS_NAME + "/" + fileName)));
    }


    /**
     * text文本所有关键字变色
     */
    public static CharSequence setColor(String text, String keyWord) {

        if (Tools.isNull(text)) {
            return "";
        }
        if (Tools.isNull(keyWord)) {
            return text;
        }

        // 获取关键字所有的开始下标
        List<Integer> ints = getStart(text, keyWord);

        if (ints.size() == 0) {
            return text;
        }

        SpannableStringBuilder style = new SpannableStringBuilder(text);
        for (int i : ints) {
            style.setSpan(new ForegroundColorSpan(Color.BLUE), i, i + keyWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return style;
    }


    /**
     * 获取开始下标集合
     */
    private static List<Integer> getStart(String text, String keyWord) {
        List<Integer> ints = new ArrayList<>();
        //取反，保证第一次从0开始查找
        int tempStart = ~keyWord.length() + 1;
        do {
            //如果找到了，则更新下次查找位置开始
            tempStart = text.indexOf(keyWord, tempStart + keyWord.length());
            if (tempStart != -1) {
                ints.add(tempStart);
            }
        } while (tempStart != -1);


        return ints;
    }

    /**
     * 设置外边距
     */
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }


    /**
     * 百度图片api
     *
     * @param key    关键字
     * @param pageNo 页数
     */
    public static String getBaiduImagesUrl(String key, int pageNo) {

        int pageSize = 48;//每次查询数据的条数
        int start = ((pageNo <= 0 ? 1 : pageNo) - 1) * pageSize; //开始查询的数据

        String base = "search/avatarjson?tn=resultjsonavatarnew&ie=utf-8&word=" + key + "&cg=star&";
        // 生成api
        return base + "pn=" + start + "&rn=" + pageSize + "&itg=0&z=0&fr=&width=&height=&lm=-1&ic=0&s=0&st=-1&gsm=3c";
    }

    /**
     * 原始宽高，根据新宽度等比返回对应的高度
     */
    public static int getNewHeight(int oldWidth, int oldHeight, int newWidth) {
        return newWidth * oldHeight / oldWidth;
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     */
    public static String subZeroAndDot(float s) {
        String tempS = String.valueOf(s);
        if (tempS.indexOf(".") > 0) {
            tempS = tempS.replaceAll("0+?$", "");//去掉多余的0
            tempS = tempS.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return tempS;
    }

    /**
     * 设置蒙层的透明度
     *
     * @param alpha 0-1之间
     */
    public static void setWindowAlpha(Activity activity, float alpha) {
        // 1. 设置半透明主题
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        // 2. 设置window的alpha值 (0.0 - 1.0)
        if (alpha < 0) {
            alpha = 0;
        } else if (alpha > 1) {
            alpha = 1;
        }
        lp.alpha = alpha;
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 判断此str是否只有数字
     */
    public static boolean stringIsNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }


    /**
     * 显示缺失权限提示
     */
    public static void showMissingPermissionDialog(final Context context, final View.OnClickListener onClickListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("帮助");
        builder.setMessage("缺少必要权限。\n请点击\"设置\"-\"权限\"-打开所需权限。");
        // 拒绝, 退出应用
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onClickListener != null) {
                    onClickListener.onClick(null);
                }

            }
        });
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings(context);
            }
        });
        builder.show();
    }


    /**
     * 启动应用的设置
     */
    public static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }
}