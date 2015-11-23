package com.xiangmu.wyxw.activitys;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.texturevideo.VideoMediaController;
import com.xiangmu.wyxw.texturevideo.VideoSuperPlayer;

/**
 * Created by Administrator on 2015/11/16.
 */
public class FullActivity extends Activity {
    VideoSuperPlayer video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 横屏
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.full_activity);
        video = (VideoSuperPlayer) findViewById(R.id.textureview);
        video.loadAndPlay(App.getMediaPlayer(), "url", 0, true);
        video.setPageType(VideoMediaController.PageType.EXPAND);
        video.setVideoPlayCallback(new VideoSuperPlayer.VideoPlayCallbackImpl() {

            @Override
            public void onSwitchPageType() {
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    finish();
                }
            }

            @Override
            public void onPlayFinish() {

            }

            @Override
            public void onCloseVideo() {

            }
        });
    }
}
