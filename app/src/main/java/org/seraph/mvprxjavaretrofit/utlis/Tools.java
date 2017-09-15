package org.seraph.mvprxjavaretrofit.utlis;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;

import com.blankj.utilcode.util.SDCardUtils;

import org.seraph.mvprxjavaretrofit.AppConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具类
 * 一些工具集见：https://github.com/Blankj/AndroidUtilCode
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
     * 判断 多个字段的值否为空
     */
    public static boolean isNull(String... ss) {
        for (String s : ss) {
            if (null == s || s.equals("") || s.equalsIgnoreCase("null")) {
                return true;
            }
        }
        return false;
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
        //如果sd卡不可用返回null
        if (!SDCardUtils.isSDCardEnable()) {
            return null;
        }
        //获取对应存储照片的文件夹
        File dirs = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), AppConfig.SAVE_IMAGE_FOLDERS_NAME);
        //如果没有此文件夹，并且创建失败，则返回null
        if (!dirs.exists() && !dirs.mkdirs()) {
            return null;
        }
        //获取对应文件
        File file = new File(dirs, imageName);
        try {
            //有此文件，或者没有此文件但是创建成就，则返回对应的文件
            if (file.exists() || file.createNewFile()) {
                return file;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加照片到画廊
     * @param currentPhotoPath 照片路径
     */
    private static void galleryAddPic(Context context,String currentPhotoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }


    /**
     * 扫描相册对应文件
     */
    public static void scanAppImageFile(Context context, String fileName) {
        String photoPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) +"/"  + AppConfig.SAVE_IMAGE_FOLDERS_NAME + "/" + fileName;
        galleryAddPic(context,photoPath);
       // context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" + AppConfig.SAVE_IMAGE_FOLDERS_NAME + "/" + fileName)));
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

    /**
     * html适配手机(背景透明，文本字颜色为灰色)
     *
     * @param htmlContent html文本
     */
    public static String setHtmlHeadBody(String htmlContent) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset=\"utf-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">" +
                "<meta content=\"yes\" name=\"apple-mobile-web-app-capable\">" +
                "<meta content=\"black\" name=\"apple-mobile-web-app-status-bar-style\">" +
                "<meta content=\"telephone=no\" name=\"format-detection\">" +
                "<style type=\"text/css\">" +
                "body { font-family: Arial,\"microsoft yahei\",Verdana; padding:0; margin:0; font-size:13px; color:#666666; background: none; overflow-x:hidden; }" +
                "body,div,fieldset,form,h1,h2,h3,h4,h5,h6,html,p,span { -webkit-text-size-adjust: none}" +
                "h1,h2,h3,h4,h5,h6 { font-weight:normal; }" +
                "applet,dd,div,dl,dt,h1,h2,h3,h4,h5,h6,html,iframe,img,object,p,span {	padding: 0;	margin: 0;	border: none}" +
                "img {padding:0; margin:0; vertical-align:top; border: none}" +
                "li,ul {list-style: none outside none; padding: 0; margin: 0}" +
                "input[type=text],select {-webkit-appearance:none; -moz-appearance: none; margin:0; padding:0; background:none; border:none; font-size:14px; text-indent:3px; font-family: Arial,\"microsoft yahei\",Verdana;}" +
                "body { width:100%; padding:10px; box-sizing:border-box;}" +
                "p { color:#666; line-height:1.6em;} " +
                "img { max-width:100%; width:auto !important; height:auto !important;}" +
                "</style>" +
                "</head>" +
                "<body>" +
                htmlContent +
                "</body>" +
                "</html>";
    }

}