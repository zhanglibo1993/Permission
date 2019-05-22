package com.zlb.permission.sample;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zlb.permission.PermissionManager;
import com.zlb.permission.anno.RequestPermissions;
import com.zlb.permission.callback.PermissionCallBack;

public class MainActivity extends AppCompatActivity {
   private String TAG=getClass().getSimpleName();
    @RequestPermissions(permission = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_CONTACTS})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionManager pm = new PermissionManager(MainActivity.this);
                pm.request(new PermissionCallBack() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG,"权限申请成功");
                    }

                    @Override
                    public void onFailt() {
                        Log.d(TAG,"权限申请失败");
                    }
                });
            }
        });
    }


}
