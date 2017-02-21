package org.seraph.mvprxjavaretrofit.utlis;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * 用于对Fragment进行管理
 */
public class FragmentController {

    private FragmentManager fragmentManager;

    private int containerViewId;

    /**
     * tags集合
     */
    private String[] fragmentTags;


    public FragmentController(FragmentActivity activity, @IdRes int containerViewId) {
        this.fragmentManager = activity.getSupportFragmentManager();
        this.containerViewId = containerViewId;
    }


    public Fragment add(Class<? extends Fragment> clazz, String tag, Bundle bundle) {
        Fragment currentFragment = null;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (String tagName : fragmentTags) {
            Fragment fragment = fragmentManager.findFragmentByTag(tagName);
            if (fragment != null) {
                transaction.hide(fragment);
            }
        }
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            currentFragment = fragment;
            transaction.show(fragment);
        } else {
            try {
                fragment = clazz.newInstance();
                currentFragment = fragment;
                transaction.add(containerViewId, fragment, tag);
                if (bundle != null) {
                    fragment.setArguments(bundle);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        transaction.commitAllowingStateLoss();
        return currentFragment;
    }

    public void setFragmentTags(String[] fragmentTags) {
        this.fragmentTags = fragmentTags;
    }
}
