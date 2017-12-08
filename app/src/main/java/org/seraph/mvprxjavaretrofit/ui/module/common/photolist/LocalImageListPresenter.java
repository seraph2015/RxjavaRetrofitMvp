package org.seraph.mvprxjavaretrofit.ui.module.common.photolist;

import android.Manifest;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;

import com.blankj.utilcode.util.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 本地图库
 * date：2017/5/18 16:02
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class LocalImageListPresenter extends LocalImageListContract.Presenter {


    private AsyncImageQueryHandler mQueryHandler;

    private String[] projectionImages = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

    private RxPermissions mRxPermissions;

    @Inject
    public LocalImageListPresenter(ContentResolver contentResolver, RxPermissions rxPermissions) {
        mQueryHandler = new AsyncImageQueryHandler(contentResolver);
        mRxPermissions = rxPermissions;
    }


    private final int CODE_REQUEST = 1000;


    @Override
    public void start() {
        startAsyncQuery();
    }


    /**
     * 查询
     */
    private void startAsyncQuery() {
        //检查读取存储卡的权限
        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            mQueryHandler.startQuery(CODE_REQUEST, null, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projectionImages, null, null, "date_modified DESC");
                        } else {
                            ToastUtils.showShort("缺少SD卡权限，读取照片失败");
                        }
                    }
                });
    }

    public void save(ArrayList<String> arrayList) {
        if (arrayList.size() == 0) {
            ToastUtils.showShort("请选择图片");
            return;
        }
        //todo 可以进行一些其他处理。例如压缩
        mView.setResult(arrayList);
    }

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
                case CODE_REQUEST:
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
