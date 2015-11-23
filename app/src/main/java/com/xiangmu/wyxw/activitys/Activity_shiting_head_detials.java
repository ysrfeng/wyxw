package com.xiangmu.wyxw.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xiangmu.wyxw.Bean.ShitingBean;
import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.texturevideo.VideoSuperPlayer;
import com.xiangmu.wyxw.utils.CommonUtil;
import com.xiangmu.wyxw.utils.LogUtils;
import com.xiangmu.wyxw.utils.ServerURL;
import com.xiangmu.wyxw.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/18.
 */
public class Activity_shiting_head_detials extends Activity {
    private ListView mListView;
    private boolean isPlaying;
    private int indexPostion = -1;
    private MAdapter mAdapter;
    private HttpUtils httpUtils;
    private HttpHandler<String> httpHandler;
    private List<ShitingBean.V9LG4B3A0Entity> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_shiting_head_detials);
        mListView = (ListView)findViewById(R.id.shiting_head_list);
        initdata();
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if ((indexPostion < mListView.getFirstVisiblePosition() || indexPostion > mListView
                        .getLastVisiblePosition()) && isPlaying) {
                    indexPostion = -1;
                    isPlaying = false;
                    mAdapter.notifyDataSetChanged();
                    App.setMediaPlayerNull();
                }
            }
        });
    }

    private void initdata() {
        String result = SharedPreferencesUtil.getData(this, ServerURL.shiTingUrl, "");  //共享参数缓存  首先从缓存中获取数据,
        if (!TextUtils.isEmpty(result)) {//如果缓存有数据,直接Gson解析
            paserData(result);
        }
        getData(ServerURL.shiTingUrl, true);//如果无缓存再去请求网络
    }
    //解析数据并添加到集合
    private void paserData(String result) {
        ShitingBean shitingBean = new Gson().fromJson(result, ShitingBean.class);
        if (mAdapter == null) {
            mAdapter = new MAdapter(this, shitingBean.V9LG4B3A0);
            mListView.setAdapter(mAdapter);
        } else {
            list.addAll(mAdapter.getList());
            mAdapter.setList(list);
        }
    }

    //从网络获取数据
    private void getData(final String url, final boolean isRefresh) {
        LogUtils.e("----------", url);
        if (CommonUtil.isNetWork(this)) {
            if (!url.equals("")) {
                httpUtils = new HttpUtils();
                httpHandler = httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.e("--------", responseInfo.result);
                        SharedPreferencesUtil.saveData(getBaseContext(), url, responseInfo.result);
                        paserData(responseInfo.result);//Gson解析数据
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(getApplication(), "数据请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Toast.makeText(this, "请检查网络连接......", Toast.LENGTH_SHORT).show();
        }
    }

    //TODO
    class MAdapter extends BaseAdapter {
        private Context context;
        private List<ShitingBean.V9LG4B3A0Entity> list;

        public List<ShitingBean.V9LG4B3A0Entity> getList() {
            return list;
        }

        public void setList(List<ShitingBean.V9LG4B3A0Entity> list) {
            this.list = list;
        }

        public MAdapter(Context context, List<ShitingBean.V9LG4B3A0Entity> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public View getView(int position, View v, ViewGroup parent) {
            GameVideoViewHolder holder = null;
            if (v == null) {
                holder = new GameVideoViewHolder();
                v = LayoutInflater.from(context).inflate(R.layout.shiting_list_item, parent, false);
                holder.shiting_icon = (ImageView) v.findViewById(R.id.shiting_icon);
                holder.title = (TextView) v.findViewById(R.id.title);
                holder.description = (TextView) v.findViewById(R.id.description);
                holder.length = (TextView) v.findViewById(R.id.length);
                holder.playCount = (TextView) v.findViewById(R.id.playCount);
                holder.replyCount = (TextView) v.findViewById(R.id.replyCount);
                holder.mVideoViewLayout = (VideoSuperPlayer) v.findViewById(R.id.video);
                holder.mPlayBtnView = (ImageView) v.findViewById(R.id.play_btn);
                v.setTag(holder);
            } else {
                holder = (GameVideoViewHolder) v.getTag();
            }
            BitmapUtils bitmapUtils = new BitmapUtils(context);
            String mp4_url = list.get(position).getMp4_url();
            String cover = list.get(position).getCover();
            //预加载缩略图
            bitmapUtils.display(holder.shiting_icon, cover);

            //显示数据
            holder.title.setText(list.get(position).getTitle());//标题
            holder.description.setText(list.get(position).getDescription());//描述
            holder.length.setText(list.get(position).getLength() + "");//时长
            holder.playCount.setText(list.get(position).getPlayCount() + "");//观看次数
            holder.replyCount.setText(list.get(position).getReplyCount() + "");//跟帖数
            //监听
            holder.mPlayBtnView.setOnClickListener(new MyOnclick(mp4_url, holder.mVideoViewLayout, position));
            holder.mVideoViewLayout.setVideoPlayCallback(new MyVideoPlayCallback(holder.mPlayBtnView, holder.mVideoViewLayout));

            if (indexPostion == position) {
                holder.mVideoViewLayout.setVisibility(View.VISIBLE);
            } else {
                holder.mVideoViewLayout.setVisibility(View.GONE);
                holder.mVideoViewLayout.close();
            }
            return v;
        }

        class MyVideoPlayCallback implements VideoSuperPlayer.VideoPlayCallbackImpl {
            ImageView mPlayBtnView;
            VideoSuperPlayer mSuperVideoPlayer;

            public MyVideoPlayCallback(ImageView mPlayBtnView, VideoSuperPlayer mSuperVideoPlayer) {
                this.mPlayBtnView = mPlayBtnView;
                this.mSuperVideoPlayer = mSuperVideoPlayer;
            }

            @Override
            public void onCloseVideo() {
                isPlaying = false;
                indexPostion = -1;
                mSuperVideoPlayer.close();
                App.setMediaPlayerNull();
                mPlayBtnView.setVisibility(View.VISIBLE);
                mSuperVideoPlayer.setVisibility(View.GONE);
            }

            @Override
            public void onSwitchPageType() {
                if (((Activity) context).getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    Intent intent = new Intent(new Intent(context,
                            FullActivity.class));
                    context.startActivity(intent);
                }
            }

            @Override
            public void onPlayFinish() {

            }

        }

        class MyOnclick implements View.OnClickListener {
            String url;
            VideoSuperPlayer mSuperVideoPlayer;
            int position;

            public MyOnclick(String url, VideoSuperPlayer mSuperVideoPlayer,
                             int position) {
                this.url = url;
                this.position = position;
                this.mSuperVideoPlayer = mSuperVideoPlayer;
            }

            @Override
            public void onClick(View v) {
                App.setMediaPlayerNull();
                indexPostion = position;
                isPlaying = true;
                mSuperVideoPlayer.setVisibility(View.VISIBLE);
                mSuperVideoPlayer.loadAndPlay(App.getMediaPlayer(), url, 0,
                        false);
                notifyDataSetChanged();
            }
        }

        class GameVideoViewHolder {

            private VideoSuperPlayer mVideoViewLayout;
            private ImageView mPlayBtnView;
            private ImageView shiting_icon;
            public TextView title;
            public TextView description;
            public TextView length;
            public TextView playCount;
            public TextView replyCount;

        }

    }


    @Override
    public void onDestroy() {
        App.setMediaPlayerNull();
        super.onDestroy();
        if (httpUtils != null) {
            httpHandler.cancel();
        }
    }
}
