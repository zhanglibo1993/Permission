package com.zlb.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;

import com.zlb.permission.anno.RequestPermissions;
import com.zlb.permission.callback.PermissionCallBack;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class PermissionManager {
    static String TAG = PermissionManager.class.getSimpleName();
    private Activity mActivity;
    private PermissionFragment permissionFragment;
    private PermissionCallBack callBack;

    private PermissionManager() {

    }

    public PermissionManager(FragmentActivity activity) {
        this.mActivity = activity;
        permissionFragment = PermissionFragment.newInstance();
        addFragment(activity.getSupportFragmentManager());

    }

    public PermissionManager(Fragment fragment) {
        mActivity = fragment.getActivity();
        addFragment(fragment.getChildFragmentManager());
    }

    public void addFragment(final FragmentManager fm) {
        fm.beginTransaction().add(permissionFragment, TAG).commitNow();
        permissionFragment.setOnPermissionCallBack(new PermissionCallBack() {
            @Override
            public void onSuccess() {
                removeFragment(fm);
                if (callBack != null) {
                    callBack.onSuccess();
                }
            }

            @Override
            public void onFailt() {
                removeFragment(fm);
                if (callBack != null) {
                    callBack.onFailt();
                }
            }
        });

    }

    public void removeFragment(FragmentManager fm) {
        fm.beginTransaction().remove(permissionFragment).commitNow();
    }

    public void request(PermissionCallBack callBack) {
        this.callBack = callBack;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        Method[] methods = mActivity.getClass().getDeclaredMethods();

        if (methods == null || methods.length == 0) return;
        for (Method method : methods) {
            method.setAccessible(true);

            RequestPermissions permissions = method.getAnnotation(RequestPermissions.class);
            if (permissions == null) {
                continue;
            }


            String[] permissionList = permissions.permission();
            ArrayList<String> request = new ArrayList<>();
            for (String s : permissionList) {
                int result = ContextCompat.checkSelfPermission(mActivity, s);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    request.add(s);
                }


            }

            String[] requestCodes = new String[request.size()];
            request.toArray(requestCodes);
            if (requestCodes.length > 0) {
                permissionFragment.requestPermission(requestCodes);
            } else {
                callBack.onSuccess();
            }


        }


    }


}
