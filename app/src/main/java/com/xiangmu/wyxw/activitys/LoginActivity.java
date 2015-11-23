package com.xiangmu.wyxw.activitys;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.utils.HttpPostThread;
import com.xiangmu.wyxw.utils.ThreadPoolUtils;
import com.xiangmu.wyxw.utils.Utils;

import java.util.Map;
import java.util.Set;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView loginback;
    private TextView login_password_back,register;
    private Button login_button;
    private LinearLayout weixin_login,qq_login,xinlang_login;
    private EditText login_zhanghao,login_password;
    UMSocialService mController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }
    private void initView() {
        login_password_back = (TextView) findViewById(R.id.login_password_back);//找回密码
        register = (TextView) findViewById(R.id.register);//注册
        loginback = (ImageView) findViewById(R.id.loginimage_back);//返回
        login_zhanghao = (EditText) findViewById(R.id.login_zhanghao);//账号
        login_password = (EditText) findViewById(R.id.login_password);//密码
        weixin_login = (LinearLayout) findViewById(R.id.weixin_login);//微信登陆
        qq_login = (LinearLayout) findViewById(R.id.qq_login);//QQ登录
        xinlang_login = (LinearLayout) findViewById(R.id.xinlang_login);//新浪登陆
        login_button = (Button) findViewById(R.id.login_button);//登陆按钮

        mController = UMServiceFactory.getUMSocialService("com.umeng.login");
//        xinlang = (Button) findViewById(R.id.main_btn1);
//        qq = (Button) findViewById(R.id.main_btn2);
//        weixin = (Button) findViewById(R.id.main_btn3);
        //设置新浪SSO handler
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1104966900",
                "voYmRxQSvtpCnGUE");
        qqSsoHandler.addToSocialSDK();

        loginback.setOnClickListener(this);
        weixin_login.setOnClickListener(this);
        qq_login.setOnClickListener(this);
        xinlang_login.setOnClickListener(this);
        login_button.setOnClickListener(this);
        login_password_back.setOnClickListener(this);
        register.setOnClickListener(this);

        login_zhanghao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable s) {
                String s1 = login_password.getText().toString().trim();
                String s2 = s.toString().trim();
                if (!"".equals(s1) && !"".equals(s2)) {
                    login_button.setEnabled(true);
                    Drawable drawable = getResources().getDrawable(R.drawable.login_button_a);
                    login_button.setBackground(drawable);
                } else {
                    login_button.setEnabled(false);
                    login_button.setBackground(getResources().getDrawable(R.drawable.biankuang));
                }
            }
        });
        login_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable s) {
                String s1 = login_zhanghao.getText().toString().trim();
                String s2 = s.toString().trim();
                if (!"".equals(s1) && !"".equals(s2)) {
                    login_button.setEnabled(true);
                    Drawable drawable = getResources().getDrawable(R.drawable.login_button_a);
                    login_button.setBackground(drawable);
                } else {
                    login_button.setEnabled(false);
                    login_button.setBackground(getResources().getDrawable(R.drawable.biankuang));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginimage_back://暂不登陆,返回
                finish();
                overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
                break;
            case R.id.login_password_back://找回密码
                startActivity(new Intent(this,BackpasswordActivity.class));
                finish();
                overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out);
                break;
            case R.id.register://注册
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out);
                break;
            case R.id.weixin_login://微信登录
                Toast.makeText(this,"暂不能用...",Toast.LENGTH_SHORT).show();
                break;
            case R.id.qq_login://QQ登录
                mController.doOauthVerify(LoginActivity.this, SHARE_MEDIA.QQ, new SocializeListeners.UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA platform) {
                        Toast.makeText(LoginActivity.this, "授权开始", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(SocializeException e, SHARE_MEDIA platform) {
                        Toast.makeText(LoginActivity.this, "授权错误", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete(Bundle value, SHARE_MEDIA platform) {
                        Toast.makeText(LoginActivity.this, "授权完成", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(LoginActivity.this, Main2Activity.class));
                        //获取相关授权信息
                        mController.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, new SocializeListeners.UMDataListener() {
                            @Override
                            public void onStart() {
                                Toast.makeText(LoginActivity.this, "获取平台数据开始...", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onComplete(int status, Map<String, Object> info) {
                                if(status == 200 && info != null){
                                    StringBuilder sb = new StringBuilder();
                                    Set<String> keys = info.keySet();
                                    for(String key : keys){
                                        sb.append(key+"="+info.get(key).toString()+"\r\n");
                                    }
                                    Log.d("TestData",sb.toString());
                                    String userName = (String)info.get("screen_name");
                                    String profile_image_url = (String)info.get("profile_image_url");
                                    getSharedPreferences("useInfo", Context.MODE_PRIVATE).edit().putString("userName", userName).putString("pic_path",profile_image_url).commit();
                                }else{
                                    Log.d("TestData","发生错误："+status);
                                }
                            }
                        });
                    }
                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();
                    }
                } );


                break;
            case R.id.xinlang_login://新浪登陆
                mController.doOauthVerify(LoginActivity.this, SHARE_MEDIA.SINA,new SocializeListeners.UMAuthListener() {
                    @Override
                    public void onError(SocializeException e, SHARE_MEDIA platform) {
                    }
                    @Override
                    public void onComplete(Bundle value, SHARE_MEDIA platform) {
                        if (value != null && !TextUtils.isEmpty(value.getString("uid"))) {
                            Toast.makeText(LoginActivity.this, "授权成功.", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(LoginActivity.this,Main2Activity.class));
                            mController.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.SINA, new SocializeListeners.UMDataListener() {
                                @Override
                                public void onStart() {
                                    Toast.makeText(LoginActivity.this, "获取平台数据开始...", Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onComplete(int status, Map<String, Object> info) {
                                    if (status == 200 && info != null) {
                                        StringBuilder sb = new StringBuilder();
                                        Set<String> keys = info.keySet();
                                        for (String key : keys) {
                                            sb.append(key + "=" + info.get(key).toString() + "\r\n");
                                        }
                                        String userName = (String)info.get("screen_name");
                                        String profile_image_url = (String)info.get("profile_image_url");
                                        getSharedPreferences("useInfo", Context.MODE_PRIVATE).edit().putString("userName", userName).putString("pic_path",profile_image_url).commit();
                                    } else {
                                        Log.d("TestData", "发生错误：" + status);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancel(SHARE_MEDIA platform) {}
                    @Override
                    public void onStart(SHARE_MEDIA platform) {}
                });
                break;
            case R.id.login_button://登陆按钮
                String zhanghao = login_zhanghao.getText().toString().trim();
                String password = login_password.getText().toString().trim();
                if (Utils.isnumber(zhanghao)) {
                    login(zhanghao, password);
                } else {
                    Toast.makeText(this, "亲 ~,别闹,你账号输错了", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void login(String zhanghao,String password) {
        ThreadPoolUtils.execute(new HttpPostThread(this, zhanghao, password, hand));
    }
    Handler hand=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 200) {
                boolean b= (boolean) msg.obj;
                if (b) {
                    Toast.makeText(LoginActivity.this, "登陆成功,恭喜你回家!!!", Toast.LENGTH_SHORT).show();
                    finish();
                    overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
                } else {
                    Toast.makeText(LoginActivity.this,"账号或密码不正确!请重新输入",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

}

