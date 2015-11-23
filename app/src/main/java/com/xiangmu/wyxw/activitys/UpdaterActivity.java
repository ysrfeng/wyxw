package com.xiangmu.wyxw.activitys;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.utils.Md5Utils;
import com.xiangmu.wyxw.utils.MySqlOpenHelper;

public class UpdaterActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView loginimage_back;
    EditText password1,password2;
    LinearLayout updater;
    String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updater);
        Intent intent = getIntent();
        number=intent.getStringExtra("number");
        initView();
    }
    private void initView() {
        loginimage_back = (ImageView) findViewById(R.id.loginimage_back);//返回按钮
        password1 = (EditText) findViewById(R.id.password1);//第一次输入
        password2 = (EditText) findViewById(R.id.password2);//第二次输入
        updater = (LinearLayout) findViewById(R.id.updater);//提交按钮

        loginimage_back.setOnClickListener(this);
        updater.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginimage_back:
                finish();
                overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
                break;
            case R.id.updater:
                String p1 = password1.getText().toString().trim();
                String p2 = password2.getText().toString().trim();
                //两次输入不能为空,且必须相同
                if (!"".equals(p1) && !"".equals(p2) && p1.equals(p2)) {
                    MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(this);
                    SQLiteDatabase writableDatabase = mySqlOpenHelper.getWritableDatabase();
                    String names = Md5Utils.encodeBy32BitMD5(number);
                    String passwords = Md5Utils.encodeBy32BitMD5(p1);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("password", passwords);
                    int biao = writableDatabase.update("login_date", contentValues, "name=?", new String[]{names});
                    if (biao > 0) {
                        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                        overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
                        break;
                    }
                    break;
                }
                Toast.makeText(this, "密码输入有误,请重新输入", Toast.LENGTH_SHORT).show();
                password1.setText("");
                password2.setText("");
                break;
        }
    }
}
