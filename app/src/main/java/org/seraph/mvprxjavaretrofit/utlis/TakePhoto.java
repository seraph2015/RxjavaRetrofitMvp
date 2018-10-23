package org.seraph.mvprxjavaretrofit.utlis;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhy.base.fileprovider.FileProvider7;

import java.io.File;
import java.util.UUID;

import javax.inject.Inject;

/**
 * 拍照相关工具
 * date：2017/5/12 09:32
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class TakePhoto {


    /**
     * 请求照相机后，回调时的一个标识
     */
    public static final int CAMERA_WITH_DATA = 3023;
    /**
     * 请求画廊
     */
    public static final int PHOTO_WITH_DATA = 3021;

    private Activity mContext;

    //拍照存储文件路径
    private File PHOTO_DIR = null;

    private File mCurrentPhotoFile = null;

    private RxPermissions rxPermissions;

    @Inject
    public TakePhoto(Activity activity, RxPermissions rxPermissions) {
        this.mContext = activity;
        this.rxPermissions = rxPermissions;
        //获取上传文件的文件路径(直接使用应用缓存文件夹)
        PHOTO_DIR = FileUtils.getCacheDirectory(mContext, null);
    }

    /**
     * 请求照相机拍照
     */

    public void doTakePhoto() {
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(aBoolean -> {
            if (aBoolean) {
                try {
                    //给新照的照片文件命名
                    mCurrentPhotoFile = getNewPhotoFile();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
                    //Uri.fromFile(mCurrentPhotoFile)
                    //适配7.0文件共享
                    Uri fileUri = FileProvider7.getUriForFile(mContext, mCurrentPhotoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    mContext.startActivityForResult(intent, CAMERA_WITH_DATA);
                } catch (ActivityNotFoundException e) {
                    ToastUtils.showShort("没有程序执行拍照操作");
                    e.printStackTrace();
                }
            } else {
                ToastUtils.showShort("获取权限失败");
            }
        });
    }


    /**
     * 请求照相机拍照
     */

    public void doTakePhoto(final Fragment fragment) {
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        try {
                            //给新照的照片文件命名
                            mCurrentPhotoFile = getNewPhotoFile();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
                            //Uri.fromFile(mCurrentPhotoFile)
                            //适配7.0文件共享
                            Uri fileUri = FileProvider7.getUriForFile(fragment.getContext(), mCurrentPhotoFile);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            fragment.startActivityForResult(intent, CAMERA_WITH_DATA);
                        } catch (ActivityNotFoundException e) {
                            ToastUtils.showShort("没有程序执行拍照操作");
                            e.printStackTrace();
                        }
                    } else {
                        ToastUtils.showShort("获取权限失败");
                    }
                });
    }

    /**
     * 获取一个新的图片输出路径
     */
    public File getNewPhotoFile() {
        return new File(PHOTO_DIR, UUID.randomUUID() + ".jpg");
    }

    /**
     * 获取当前的图片路径
     */
    public File getCurrentPhotoFile() {
        return mCurrentPhotoFile;
    }

    public void setmCurrentPhotoFile(File mCurrentPhotoFile) {
        this.mCurrentPhotoFile = mCurrentPhotoFile;
    }

    // 请求Gallery程序
    public void doPickPhotoFromGallery() {
        try {
            PHOTO_DIR.mkdirs();// 创建照片的存储目录
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            mContext.startActivityForResult(intent, PHOTO_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mContext, "没有找到相应的程序执行该操作", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    /**
     * 获取图片路径（包括4.4）
     */
    @SuppressLint("NewApi")
    public static String getKITKATPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
