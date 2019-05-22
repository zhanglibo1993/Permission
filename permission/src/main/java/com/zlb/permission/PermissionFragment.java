package com.zlb.permission;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.zlb.permission.callback.PermissionCallBack;

public class PermissionFragment extends Fragment {
    private String TAG = PermissionFragment.class.getSimpleName();
    private int PERMISSION_REQUEST_CODE = 0x1234;
    private static PermissionFragment instance;
    private PermissionCallBack callBack;

    public PermissionFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
        Log.d(TAG, "permission fragment create");

    }

    public static PermissionFragment newInstance() {
        if (instance == null) {
            synchronized (PermissionFragment.class) {
                if (instance == null) {
                    instance = new PermissionFragment();
                }
            }
        }

        return instance;
    }

    public void requestPermission(String[] permissions) {
        ActivityCompat.requestPermissions(getActivity(), permissions, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (callBack != null) {
                    callBack.onSuccess();
                    Log.d(TAG,"权限申请成功");
                }
            } else {
                if (callBack != null) {
                    callBack.onFailt();
                    Log.d(TAG,"权限申请失败");
                }
            }
        }


    }

    public void setOnPermissionCallBack(PermissionCallBack callBack) {
        this.callBack = callBack;
    }
}
