package org.seraph.mvprxjavaretrofit.data.local;

import com.blankj.utilcode.util.SPUtils;

import java.util.Map;

import javax.inject.Inject;

/**
 * 本地偏好数据管理（基于开源框架的二次封装）
 * date：2017/4/5 15:48
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class AppSPManager {


    private SPUtils mSPUtils;

    @Inject
    public AppSPManager() {
        mSPUtils = new SPUtils(AppSPConstant.SP_NAME);
    }

    /**
     * 保存
     */
    public void save(String key, Object value) {
        if (value instanceof Boolean) {
            mSPUtils.put(key, (Boolean) value);
        } else if (value instanceof Float) {
            mSPUtils.put(key, (Float) value);
        } else if (value instanceof Integer) {
            mSPUtils.put(key, (Integer) value);
        } else if (value instanceof Long) {
            mSPUtils.put(key, (Long) value);
        } else if (value instanceof String) {
            mSPUtils.put(key, String.valueOf(value));
        }
    }

    /**
     * 取数据
     */
    public Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return mSPUtils.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return mSPUtils.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return mSPUtils.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return mSPUtils.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return mSPUtils.getFloat(key, (Long) defaultObject);
        } else {
            return null;
        }
    }

    /**
     * 删除对应数据
     */
    public void remove(String key) {
        mSPUtils.remove(key);
    }

    /**
     * 删除所有数据
     */
    public void clear() {
        mSPUtils.clear();
    }

    /**
     * 检查key是否存
     */
    public boolean contains(String key) {
        return mSPUtils.contains(key);
    }

    /**
     * 返回所有数据Map数组
     */
    public Map<String, ?> getAll() {
        return mSPUtils.getAll();
    }
}
