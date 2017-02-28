package org.seraph.mvprxjavaretrofit.permission;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import org.seraph.mvprxjavaretrofit.R;

/**
 * 6.0权限管理界面
 * date：2017/2/28 11:55
 * author：xiongj
 * mail：417753393@qq.com
 **/
@RequiresApi(api = Build.VERSION_CODES.M)
public class PermissionsActivity extends AppCompatActivity {

    public static final int PERMISSIONS_GRANTED = 0; // 权限授权
    public static final int PERMISSIONS_DENIED = 1; // 权限拒绝

    private static final int PERMISSION_REQUEST_CODE = 0; // 系统权限管理页面的参数

    private static final String EXTRA_PERMISSIONS = "extra_permission"; // 权限参数

    /**
     * 是否需要系统权限检测, 防止和系统提示框重叠
     */
    private boolean isRequireCheck;

    /**
     * 启动当前权限页面的公开接口
     */
    public static void startActivityForResult(Activity activity, int requestCode, String[] permissions) {
        Intent intent = new Intent(activity, PermissionsActivity.class);
        intent.putExtra(EXTRA_PERMISSIONS, permissions);
        ActivityCompat.startActivityForResult(activity, intent, requestCode, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || !getIntent().hasExtra(EXTRA_PERMISSIONS)) {
            throw new RuntimeException("PermissionsActivity需要使用静态startActivityForResult方法启动!");
        }
        setContentView(R.layout.activity_permissions);
        isRequireCheck = true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isRequireCheck) {
            String[] permissions = getPermissions();
            if (PermissionManagement.lacksPermissions(this, permissions)) {
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE); // 请求权限
            } else {
                allPermissionsGranted(); // 全部权限都已获取
            }
        } else {
            isRequireCheck = true;
        }
    }

    /**
     * 返回传递的权限参数
     */
    private String[] getPermissions() {
        return getIntent().getStringArrayExtra(EXTRA_PERMISSIONS);
    }

    /**
     * 全部权限均已获取
     */
    private void allPermissionsGranted() {
        setResult(PERMISSIONS_GRANTED);
        finish();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && PermissionManagement.hasAllPermissionsGranted(grantResults)) {
            isRequireCheck = true;
            allPermissionsGranted();
        } else {
            isRequireCheck = false;
            showMissingPermissionDialog();
        }
    }

    /**
     * 显示缺失权限提示
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PermissionsActivity.this);
        builder.setTitle("帮助");
        builder.setMessage("缺少必要权限。\n请点击\"设置\"-\"权限\"-打开所需权限。");
        // 拒绝, 退出应用
        builder.setNegativeButton("退出", (DialogInterface dialog, int which) -> {
            setResult(PERMISSIONS_DENIED);
            finish();
        });
        builder.setPositiveButton("设置", (DialogInterface dialog, int which) -> {
            startAppSettings();
        });
        builder.show();
    }

    /**
     * 启动应用的设置
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

}
