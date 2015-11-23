package com.xiangmu.wyxw.activitys;

import android.content.Intent;
import android.database.Cursor;
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
import com.xiangmu.wyxw.utils.Utils;

public class BackpasswordActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView loginimage_back;
    EditText number;
    LinearLayout next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backpassword);
        initView();
    }
    private void initView() {
        loginimage_back = (ImageView) findViewById(R.id.loginimage_back);//返回按钮
        number = (EditText) findViewById(R.id.number);//要更改的手机号
        next = (LinearLayout) findViewById(R.id.next);//提交按钮

        loginimage_back.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginimage_back:
                finish();
                overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
                break;
            case R.id.next:
                String numbers = number.getText().toString().trim();
                if (Utils.isnumber(numbers)) {
                    MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(this);
                    SQLiteDatabase writableDatabase = mySqlOpenHelper.getWritableDatabase();
                    String names = Md5Utils.encodeBy32BitMD5(numbers);
                    Cursor cursor = writableDatabase.query("login_date", null, "name =?", new String[]{names}, null, null, null, null);
                    if (!cursor.moveToNext()) {
                        Toast.makeText(this, "要修改的账号不正确,请查证...", Toast.LENGTH_SHORT).show();
                        cursor.close();
                        writableDatabase.close();
                        mySqlOpenHelper.close();
                    } else {
                        cursor.close();
                        writableDatabase.close();
                        mySqlOpenHelper.close();
                        Intent intent=new Intent(this,UpdaterActivity.class);
                        intent.putExtra("number", numbers);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out);
                    }
                } else {
                    Toast.makeText(this, "要修改的账号不正确,请查证...", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}