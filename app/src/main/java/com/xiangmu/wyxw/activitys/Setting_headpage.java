package com.xiangmu.wyxw.activitys;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.Setting_Utils.QieYuanTu;
import com.xiangmu.wyxw.Setting_Utils.SearchDB;
import com.xiangmu.wyxw.Setting_Utils.TouXiangCache;
import com.xiangmu.wyxw.conent_frament.SheZhiFrament;

import java.io.File;

/**
 * Created by Administrator on 2015/11/12.
 */
public class Setting_headpage extends AppCompatActivity {
    ImageView back;
    TextView mylable;
    ImageView header;
    TextView userName;
    TextView zhicheng;
    Bitmap bp;
    int number;
    String email;
    View view;
    String emailAddress;
    //    //头像图片地址路径
    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "head_image.jpg";
    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 100;
    private static int output_Y = 100;
    private ImageView headImage = null;
    private String user_name;
    private String pic_path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hgz_head_page);
        intiView();
        pic_path = SearchDB.TouXiangDb(this, IMAGE_FILE_NAME);
        user_name = SearchDB.createDb(this, "userName");
        if (pic_path != null) {
            Log.e("aaaaaaaa------>", "---pic_path--" + pic_path);
            Bitmap getphoto = TouXiangCache.getphoto("storage/sdcard0/" + pic_path);
            header.setImageBitmap(getphoto);
        }
        if (user_name != null) {

            userName.setText(user_name);
            zhicheng.setText("跟帖局科员");
        }

    }

    private void intiView() {
        back = (ImageView) findViewById(R.id.back);
        header = (ImageView) findViewById(R.id.header);
        mylable = (TextView) findViewById(R.id.mylable);
        userName = (TextView) findViewById(R.id.user_name);
        zhicheng = (TextView) findViewById(R.id.zhicheng);
    }

    public void OnClick(View view) {
        switch (view.getId()) {
            // TODO: 2015/11/18   返回设置
            case R.id.back:
                overridePendingTransition(R.anim.push_left_out, R.anim.push_left_in);
                finish();
                break;
            // TODO: 2015/11/18 我的标签
            case R.id.mylable:
                Intent intent1 = new Intent(this, Setting_biaoqianPage.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.push_left_out, R.anim.push_left_in);
                finish();
                break;
            // TODO: 2015/11/18 头像
            case R.id.header:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                Log.e("---------->", "header");
                builder.setTitle("设置你的靓照");
                View view1 = LayoutInflater.from(this).inflate(R.layout.hgz_head_dialog, null);
                ImageView photo_selector = (ImageView) view1.findViewById(R.id.head_photo_selector);
                ImageView pic_selector = (ImageView) view1.findViewById(R.id.head_pic_selector);
                ImageView selector_sina = (ImageView) view1.findViewById(R.id.head_selector_sina);
                photo_selector.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        choseHeadImageFromCameraCapture();
                    }
                });
                pic_selector.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        choseHeadImageFromGallery();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setView(view1);
                builder.create();
                builder.show();
                break;
        }
    }

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intent = new Intent();
        // 设置文件类型
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CODE_GALLERY_REQUEST);
    }

    // 启动手机相机拍摄照片作为头像
    public void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }
        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }
        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(intent.getData());
                break;
            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG).show();
                }
                break;
            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            final Bitmap photo = extras.getParcelable("data");
            if (photo != null) {
                bp = photo;
                Bitmap roundedCornerBitmap = QieYuanTu.getRoundedCornerBitmap(bp);
                pic_path = IMAGE_FILE_NAME;
                TouXiangCache.saveMyBitmap(roundedCornerBitmap, pic_path);
                String touxiang = SearchDB.TouXiangDb(this, pic_path);
                if (touxiang==null){
                    SharedPreferences preferences = this.getSharedPreferences("useInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("pic_path",pic_path).commit();
                }
                Log.e("---------------->", "--------roundedCornerBitmap---------->"+pic_path);
                QieYuanTu.setRoundConner(header, roundedCornerBitmap);
                Message message = new Message();
                message.what = 2;
                message.obj = roundedCornerBitmap;
                SheZhiFrament.handle.sendMessage(message);
            }
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

}
