package org.seraph.mvprxjavaretrofit.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import org.seraph.mvprxjavaretrofit.AppConfig;

import java.util.Map;

import javax.inject.Inject;

/**
 * 本地偏好数据管理
 * date：2017/4/5 15:48
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class PreferencesManager {


    private SharedPreferences sp;

    @Inject
    public PreferencesManager(Context context) {
        sp = context.getSharedPreferences(AppConfig.PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 保存
     */
    public void save(String key, Object value) {
        SharedPreferences.Editor editor = sp.edit();
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            editor.putString(key, String.valueOf(value));
        }
        editor.apply();
    }

    /**
     * 取数据
     */
    public Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getFloat(key, (Long) defaultObject);
        } else {
            return null;
        }
    }

    /**
     * 删除对应数据
     */
    public void remove(String key) {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 删除所有数据
     */
    public void clear() {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 检查key是否存
     */
    public boolean contains(String key) {
        return sp.contains(key);
    }

    /**
     * 返回所有数据Map数组
     */
    public Map<String, ?> getAll() {
        return sp.getAll();
    }
}
