package org.seraph.mvprxjavaretrofit.ui.module.common.photolist;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;

import org.seraph.mvprxjavaretrofit.AppConfig;
import org.seraph.mvprxjavaretrofit.ui.module.common.permission.PermissionManagement;
import org.seraph.mvprxjavaretrofit.ui.module.common.permission.PermissionsActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 本地图库
 * date：2017/5/18 16:02
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class LocalImageListPresenter implements LocalImageListContract.Presenter {

    private LocalImageListContract.View mView;

    private ContentResolver mContentResolver;

    private AsyncImageQueryHandler mQueryHandler;

    private String[] projectionImages = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

    @Inject
    public LocalImageListPresenter(ContentResolver contentResolver) {
        this.mContentResolver = contentResolver;
        mQueryHandler = new AsyncImageQueryHandler(contentResolver);
    }

    @Override
    public void setView(LocalImageListContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        startAsyncQuery();
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void onPermissionsRequest(int resultCode) {
        switch (resultCode) {
            case PermissionsActivity.PERMISSIONS_GRANTED://权限授权
                startAsyncQuery();
                break;
            case PermissionsActivity.PERMISSIONS_DENIED://权限拒绝
                mView.showToast("获取授权失败");
                break;
        }
    }

    /**
     * 查询
     */
    private void startAsyncQuery() {
        //检查读取存储卡的权限
        if (PermissionManagement.lacksPermissions(mView.getContext(), AppConfig.PERMISSIONS_SDCARD)) {
            mView.requestPermission(AppConfig.PERMISSIONS_SDCARD);
            return;
        }
        mQueryHandler.startQuery(AppConfig.PERMISSIONS_CODE_REQUEST_1, null, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projectionImages, null, null, "date_modified DESC");
    }

    @Override
    public void save(ArrayList<String> arrayList) {
        if (arrayList.size() == 0) {
            mView.showToast("请选择图片");
            return;
        }
        //可以进行一些其他处理。例如压缩
        mView.setResult(arrayList);
    }

    @Override
    public void setIntent(Intent intent) {
        ArrayList<String> arrayList = intent.getStringArrayListExtra(LocalImageListActivity.SELECTED_PATH);
        mView.setSelectedPath(arrayList);
    }


    private class AsyncImageQueryHandler extends AsyncQueryHandler {

        AsyncImageQueryHandler(ContentResolver contentResolver) {
            super(contentResolver);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            switch (token) {
                case AppConfig.PERMISSIONS_CODE_REQUEST_1:
                    mView.setQueryImageList(initImageList(cursor));
                    break;
            }
        }
    }

    /**
     * 获取数据
     */
    private List<LocalImageBean> initImageList(Cursor cursor) {
        List<LocalImageBean> list = null;
        if (cursor != null && cursor.getCount() > 0) {
            list = new ArrayList<>();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                if (new File(path).exists()) {
                    LocalImageBean bean = new LocalImageBean(id, path);
                    list.add(bean);
                }
            }
            cursor.close();
        }
        return list;
    }


//    private void addPic() {
//        ArrayList<String> list = new ArrayList<String>();
//        if (mSelectedPathList != null && mSelectedPathList.size() > 0) {
//            Bitmap bitmap = null;
//            String compressFile = "";
//            for (int i = 0; i < mSelectedPathList.size(); i++) {
//                String url = mSelectedPathList.get(i);
//                bitmap = BitmapHelper.decodeSampledBitmapFromResource(url, dm.widthPixels);
//                // 图片压缩
//                if (bitmap != null) {
//                    compressFile = BitmapHelper.saveBitmap2file(bitmap, 100);
//                    list.add(compressFile);
//                }
//            }
//            if (bitmap != null) {
//                bitmap.recycle();
//            }
//        }
//
//        Intent intent = new Intent();
//        intent.putStringArrayListExtra("select_path", list);
//        setResult(-1, intent);
//        finish();
//    }

}
