package com.xiangmu.wyxw.activitys;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.utils.Md5Utils;
import com.xiangmu.wyxw.utils.MySqlOpenHelper;
import com.xiangmu.wyxw.utils.Utils;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView loginimage_back;
    EditText login_zhanghao,login_passwoed;
    TextView registerbutton;
    LinearLayout line;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }
    private void initView() {
        loginimage_back = (ImageView) findViewById(R.id.loginimage_back);
        login_zhanghao = (EditText) findViewById(R.id.login_zhanghao);
        login_passwoed = (EditText) findViewById(R.id.login_passwoed);
        registerbutton = (TextView) findViewById(R.id.registerbutton);
        line= (LinearLayout) findViewById(R.id.line2);

        loginimage_back.setOnClickListener(this);
        registerbutton.setOnClickListener(this);
        line.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginimage_back:
                finish();
                overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
                break;
            case R.id.registerbutton:
                String name = login_zhanghao.getText().toString().trim();
                String password = login_passwoed.getText().toString().trim();
                if ("".equals(name) || "".equals(password)) {
                    Toast.makeText(this, "亲,别闹,赶快注册吧....", Toast.LENGTH_SHORT).show();
                    break;
                }
                //是否是手机号
                if (Utils.isnumber(name)) {
                    MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(this);
                    SQLiteDatabase writableDatabase = mySqlOpenHelper.getWritableDatabase();
                    String names = Md5Utils.encodeBy32BitMD5(name);
                    Cursor cursor = writableDatabase.query("login_date", null, "name =?", new String[]{names}, null, null, null, null);
                    //数据库中是否存在此账号
                    if (cursor.moveToNext()) {
                        Toast.makeText(this, "亲,这个号码注册过了,赶快登陆去吧", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                        overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
                        break;
                    } else {//数据库中是否存在此账号 没有就写入数据库 然后跳回登陆界面
                        String passwords = Md5Utils.encodeBy32BitMD5(password);
                        ContentValues values = new ContentValues();
                        values.put("name", names);
                        values.put("password", passwords);
                        long login_date = writableDatabase.insert("login_date", null, values);
                        if (login_date > 0) {
                            startActivity(new Intent(this, MainActivity.class));
                            overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
                            Toast.makeText(this, "注册成功了,赶快登陆去吧", Toast.LENGTH_SHORT).show();
                            getSharedPreferences("useInfo", Context.MODE_PRIVATE).edit().putString("userName", name).commit();
                        }
                    }
                    cursor.close();
                    writableDatabase.close();
                    mySqlOpenHelper.close();
                    finish();
                }
                break;
            case R.id.line2:
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
        }
    }
}
