#### Permisstion是封装的一个动态权限申请的库，使用它无需编写重复的代码，三行代码即可完成权限的申请目前是基于反射实现，后续会引入apt的支持
#### 1.集成Permission库，支持源码集成和gradle集成 
#### implementation 'com.github.zhanglibo1993:Permission:1.0'
#### 2.在要执行的方法上添加注解,比如在Activity的onCreate方法上 <br/>
#### @RequestPermissions(permission = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE})
#### 3.请求权限，在回调中获取申请权限的结果 <br/>
#### 示例代码如下:
#### @RequestPermissions(permission = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_CONTACTS})
####    @Override
####    protected void onCreate(Bundle savedInstanceState) {
####        super.onCreate(savedInstanceState);
####        setContentView(R.layout.activity_main);
####        findViewById(R.id.btn_request).setOnClickListener(new View.OnClickListener() {
####            @Override
####            public void onClick(View v) {
####                PermissionManager pm = new PermissionManager(MainActivity.this);
####                pm.request(new PermissionCallBack() {
####                    @Override
####                    public void onSuccess() {
####                        Log.d(TAG,"权限申请成功");
####                    }
####             @Override
####            public void onFailt() {
####                        Log.d(TAG,"权限申请失败");
####                    }
####                });
####            }
####        });
####    }
