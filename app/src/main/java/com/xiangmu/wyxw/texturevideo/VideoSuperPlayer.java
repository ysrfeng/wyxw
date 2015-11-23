package com.xiangmu.wyxw.texturevideo;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xiangmu.wyxw.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/11/15.
 */
public class VideoSuperPlayer extends RelativeLayout implements
        TextureView.SurfaceTextureListener {
    private final int TIME_SHOW_CONTROLLER = 3000;
    private final int TIME_UPDATE_PLAY_TIME = 1000;

    private final int MSG_HIDE_CONTROLLER = 10;
    private final int MSG_UPDATE_PLAY_TIME = 11;
    private VideoMediaController.PageType mCurrPageType = VideoMediaController.PageType.SHRINK;// 当前是横屏还是竖屏

    private Context mContext;
    private TextureView mSuperVideoView;
    private VideoMediaController mMediaController;
    private Timer mUpdateTimer;
    private TimerTask mTimerTask;
    private VideoPlayCallbackImpl mVideoPlayCallback;

    private View mProgressBarView;
    private View mCloseBtnView;

    private MediaPlayer mPlayer;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_UPDATE_PLAY_TIME) {
                updatePlayTime();
                updatePlayProgress();
            } else if (msg.what == MSG_HIDE_CONTROLLER) {
                showOrHideController();
            }
        }
    };

    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.video_close_view) {
                mVideoPlayCallback.onCloseVideo();
            }
        }
    };

    private OnTouchListener mOnTouchVideoListener = new OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                showOrHideController();
            }
            return mCurrPageType == VideoMediaController.PageType.EXPAND ? true
                    : false;
        }
    };

    private VideoMediaController.MediaControlImpl mMediaControl = new VideoMediaController.MediaControlImpl() {
        @Override
        public void alwaysShowController() {
            VideoSuperPlayer.this.alwaysShowController();
        }

        @Override
        public void onPlayTurn() {
            if (mPlayer.isPlaying()) {
                pausePlay();
            } else {
                startPlayVideo(0);
            }
        }

        @Override
        public void onPageTurn() {
            mVideoPlayCallback.onSwitchPageType();
        }

        @Override
        public void onProgressTurn(VideoMediaController.ProgressState state,
                                   int progress) {
            if (state.equals(VideoMediaController.ProgressState.START)) {
                mHandler.removeMessages(MSG_HIDE_CONTROLLER);
            } else if (state.equals(VideoMediaController.ProgressState.STOP)) {
                resetHideTimer();
            } else {
                int time = progress * mPlayer.getDuration() / 100;
                mPlayer.seekTo(time);
                updatePlayTime();
            }
        }
    };

    private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            mPlayer.start();
            mProgressBarView.setVisibility(View.GONE);
            mCloseBtnView.setVisibility(VISIBLE);
            mSuperVideoView.requestLayout();
            mSuperVideoView.invalidate();
            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                        mProgressBarView.setVisibility(View.GONE);
                        mCloseBtnView.setVisibility(VISIBLE);
                        return true;
                    }
                    return false;
                }
            });
        }
    };

    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            stopUpdateTimer();
            stopHideTimer();
            mMediaController.playFinish(mPlayer.getDuration());
            mVideoPlayCallback.onPlayFinish();
            Toast.makeText(mContext, "视频播放完成", Toast.LENGTH_SHORT).show();
        }
    };

    public TextureView getSuperVideoView() {
        return mSuperVideoView;
    }

    public void setPageType(VideoMediaController.PageType pageType) {
        mMediaController.setPageType(pageType);
        mCurrPageType = pageType;
    }

    public void setVideoPlayCallback(VideoPlayCallbackImpl videoPlayCallback) {
        mVideoPlayCallback = videoPlayCallback;
    }

    public void pausePlay() {
        mPlayer.pause();
        mMediaController.setPlayState(VideoMediaController.PlayState.PAUSE);
        stopHideTimer();
    }

    public void stopPlay() {
        pausePlay();
        stopUpdateTimer();
    }

    public void close() {
        mMediaController.setPlayState(VideoMediaController.PlayState.PAUSE);
        stopHideTimer();
        stopUpdateTimer();
        if (mPlayer != null) {
            // mPlayer.stop();
            // mPlayer.release();
            mPlayer = null;
        }
        mSuperVideoView.setSurfaceTextureListener(null);
        mSuperVideoView.setVisibility(GONE);
    }

    public VideoSuperPlayer(Context context) {
        super(context);
        initView(context);
    }

    public VideoSuperPlayer(Context context, AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public VideoSuperPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView(Context context) {
        mContext = context;
        View.inflate(context, R.layout.super2_vodeo_player_layout, this);
        mSuperVideoView = (TextureView) findViewById(R.id.video_view);
        mSuperVideoView.setScaleX(1.00001f);
        mMediaController = (VideoMediaController) findViewById(R.id.controller);
        mProgressBarView = findViewById(R.id.progressbar);
        mCloseBtnView = findViewById(R.id.video_close_view);

        mMediaController.setMediaControl(mMediaControl);
        mSuperVideoView.setOnTouchListener(mOnTouchVideoListener);

        mCloseBtnView.setVisibility(INVISIBLE);
        mCloseBtnView.setOnClickListener(mOnClickListener);
        mProgressBarView.setVisibility(VISIBLE);

        this.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 拦截
                return true;
            }
        });
    }

    /***
     * 加载并开始播放视频
     *
     * @param videoUrl
     */
    public void loadAndPlay(MediaPlayer player, String videoUrl, int seekTime,
                            boolean isfull) {
        mProgressBarView.setVisibility(VISIBLE);
        if (seekTime == 0) {
            mCloseBtnView.setVisibility(GONE);
            mProgressBarView.setBackgroundResource(android.R.color.black);
        } else {
            mProgressBarView.setBackgroundResource(android.R.color.transparent);
        }
        if (TextUtils.isEmpty(videoUrl)) {
            Log.e("TAG", "videoUrl should not be null");
            return;
        }
        mSuperVideoView.setVisibility(VISIBLE);
        mPlayer = player;
        if (!isfull) {
            play(videoUrl);
        } else {
            mProgressBarView.setVisibility(View.GONE);
        }
        mSuperVideoView.setSurfaceTextureListener(this);
        startPlayVideo(seekTime);
    }

    private void play(String url) {
        try {
            // mPlayer.setOnCompletionListener(mOnCompletionListener);
            mPlayer.setOnPreparedListener(mOnPreparedListener);
            mPlayer.setDataSource(url);
            mPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放视频 should called after loadVideo()
     */
    private void startPlayVideo(int seekTime) {
        if (null == mUpdateTimer)
            resetUpdateTimer();
        resetHideTimer();
        resetUpdateTimer();
        mPlayer.start();
        if (seekTime > 0) {
            mPlayer.seekTo(seekTime);
        }
        mMediaController.setPlayState(VideoMediaController.PlayState.PLAY);
    }

    private void updatePlayTime() {
        if (mPlayer == null) {
            return;
        }
        try {
            int allTime = mPlayer.getDuration();
            int playTime = mPlayer.getCurrentPosition();
            mMediaController.setPlayProgressTxt(playTime, allTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatePlayProgress() {
        if (mPlayer == null) {
            return;
        }
        try {
            int allTime = mPlayer.getDuration();
            int playTime = mPlayer.getCurrentPosition();
            int progress = playTime * 100 / allTime;
            mMediaController.setProgressBar(progress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     *
     */
    private void showOrHideController() {
        if (mMediaController.getVisibility() == View.VISIBLE) {
            Animation animation = AnimationUtils.loadAnimation(mContext,
                    R.anim.anim_exit_from_bottom);
            animation.setAnimationListener(new AnimationImp() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    super.onAnimationEnd(animation);
                    mMediaController.setVisibility(View.GONE);
                }
            });
            mMediaController.startAnimation(animation);
        } else {
            mMediaController.setVisibility(View.VISIBLE);
            mMediaController.clearAnimation();
            Animation animation = AnimationUtils.loadAnimation(mContext,
                    R.anim.anim_enter_from_bottom);
            mMediaController.startAnimation(animation);
            resetHideTimer();
        }
    }

    private void alwaysShowController() {
        mHandler.removeMessages(MSG_HIDE_CONTROLLER);
        mMediaController.setVisibility(View.VISIBLE);
    }

    private void resetHideTimer() {
        mHandler.removeMessages(MSG_HIDE_CONTROLLER);
        mHandler.sendEmptyMessageDelayed(MSG_HIDE_CONTROLLER,
                TIME_SHOW_CONTROLLER);
    }

    private void stopHideTimer() {
        mHandler.removeMessages(MSG_HIDE_CONTROLLER);
        mMediaController.setVisibility(View.VISIBLE);
        mMediaController.clearAnimation();
        Animation animation = AnimationUtils.loadAnimation(mContext,
                R.anim.anim_enter_from_bottom);
        mMediaController.startAnimation(animation);
    }

    private void resetUpdateTimer() {
        stopUpdateTimer();
        mUpdateTimer = new Timer();
        mTimerTask = new TimerTask() {

            @Override
            public void run() {
                mHandler.sendEmptyMessage(MSG_UPDATE_PLAY_TIME);
            }
        };
        mUpdateTimer.schedule(mTimerTask, 0, TIME_UPDATE_PLAY_TIME);
    }

    private void stopUpdateTimer() {
        if (mUpdateTimer != null) {
            mUpdateTimer.cancel();
            mUpdateTimer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    private class AnimationImp implements Animation.AnimationListener {

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }
    }

    public interface VideoPlayCallbackImpl {
        void onCloseVideo();

        void onSwitchPageType();

        void onPlayFinish();
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture,
                                          int width, int height) {
        mPlayer.setSurface(new Surface(surfaceTexture));
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
                                            int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}