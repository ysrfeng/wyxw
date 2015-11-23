package com.xiangmu.wyxw.conent_frament;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
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
import com.xiangmu.wyxw.Setting_Utils.ShareUtils;
import com.xiangmu.wyxw.activitys.App;
import com.xiangmu.wyxw.activitys.FullActivity;
import com.xiangmu.wyxw.pullrefreshview.PullToRefreshBase;
import com.xiangmu.wyxw.pullrefreshview.PullToRefreshListView;
import com.xiangmu.wyxw.texturevideo.VideoSuperPlayer;
import com.xiangmu.wyxw.utils.CommonUtil;
import com.xiangmu.wyxw.utils.LogUtils;
import com.xiangmu.wyxw.utils.ServerURL;
import com.xiangmu.wyxw.utils.SharedPreferencesUtil;
import com.xiangmu.wyxw.utils.ShiTingUrl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/9.
 */
public class ShiTingFrament extends Fragment {
    private PullToRefreshListView mListView;
    private boolean isPlaying;
    private int indexPostion = -1;
    private MAdapter mAdapter;
    private HttpUtils httpUtils;
    private HttpHandler<String> httpHandler;
    private List<ShitingBean.V9LG4B3A0Entity> list = new ArrayList<>();
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            if (view == null) {
                view = initview(inflater);
            }
            return view;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //初始化控件
    private View initview(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.shiting_frament, null, false);

        httpUtils = new HttpUtils();
        initPullTorefresh(view);
        return view;
    }

    //初始化数据
    private void initdata() {
        String result = SharedPreferencesUtil.getData(getActivity(), ServerURL.shiTingUrl, "");  //共享参数缓存  首先从缓存中获取数据,
        if (!TextUtils.isEmpty(result)) {//如果缓存有数据,直接Gson解析
            paserData(result, false);
        }
        getData(ServerURL.shiTingUrl, false);//如果无缓存再去请求网络
    }

    //解析数据并添加到集合
    private void paserData(String result, boolean isfenye) {
        if (!isfenye) {
            list.clear();
        }
        ShitingBean shitingBean = new Gson().fromJson(result, ShitingBean.class);
        if (mAdapter == null) {
            mListView.getRefreshableView().addHeaderView(View.inflate(getActivity(), R.layout.shiting_list_header, null));
            mAdapter = new MAdapter(getContext(), shitingBean.V9LG4B3A0);
            mListView.getRefreshableView().setAdapter(mAdapter);
        } else {
            list.addAll(shitingBean.V9LG4B3A0);
            mAdapter.setList(list);
        }
        mListView.onPullDownRefreshComplete();
        mListView.onPullUpRefreshComplete();
    }

    boolean isfenye;

    //从网络获取数据
    private void getData(final String url, final boolean isfenye) {
        LogUtils.e("----------", url);
        if (CommonUtil.isNetWork(getActivity())) {
            if (!url.equals("")) {
                httpUtils = new HttpUtils();
                httpHandler = httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.e("--------", responseInfo.result);
                        SharedPreferencesUtil.saveData(getActivity(), url, responseInfo.result);
                        paserData(responseInfo.result, isfenye);//Gson解析数据
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(getActivity(), "数据请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Toast.makeText(getActivity(), "请检查网络连接......", Toast.LENGTH_SHORT).show();
        }
    }


    private void initPullTorefresh(View view) {
        mListView = (PullToRefreshListView) view.findViewById(R.id.shiting_refresh);

        initdata();
        mListView.setPullLoadEnabled(true);  //上拉加载，屏蔽
        mListView.setPullRefreshEnabled(true);
        //   mListView.setScrollLoadEnabled(true); //设置滚动加载可用
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getData(ServerURL.shiTingUrl, false);
                String stringDate = CommonUtil.getStringDate();
                mListView.setLastUpdatedLabel(stringDate);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                ShiTingUrl url = new ShiTingUrl();
                url.setStratPage(url.getStratPage() + 20);
                String urlfen = url.getShiTingUrl();
                getData(urlfen, true);
            }
        });
        mListView.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if ((indexPostion < mListView.getRefreshableView().getFirstVisiblePosition() || indexPostion
                        > mListView.getRefreshableView()
                        .getLastVisiblePosition()) && isPlaying) {
                    indexPostion = -1;
                    isPlaying = false;
                    mAdapter.notifyDataSetChanged();
                    App.setMediaPlayerNull();
                }
            }
        });
    }

    String title;
    String mp4_url;

    //TODO
    class MAdapter extends BaseAdapter {
        private Context context;
        private List<ShitingBean.V9LG4B3A0Entity> list;

        public List<ShitingBean.V9LG4B3A0Entity> getList() {
            return list;
        }

        public void setList(List<ShitingBean.V9LG4B3A0Entity> list) {
            this.list = list;
            notifyDataSetChanged();
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
                v = LayoutInflater.from(getContext()).inflate(R.layout.shiting_list_item, parent, false);
                holder.shiting_icon = (ImageView) v.findViewById(R.id.shiting_icon);
                holder.title = (TextView) v.findViewById(R.id.title);
                holder.description = (TextView) v.findViewById(R.id.description);
                holder.length = (TextView) v.findViewById(R.id.length);
                holder.iv_share = (ImageView) v.findViewById(R.id.iv_share);
                holder.playCount = (TextView) v.findViewById(R.id.playCount);
                holder.replyCount = (TextView) v.findViewById(R.id.replyCount);
                holder.mVideoViewLayout = (VideoSuperPlayer) v.findViewById(R.id.video);
                holder.mPlayBtnView = (ImageView) v.findViewById(R.id.play_btn);
                v.setTag(holder);
            } else {
                holder = (GameVideoViewHolder) v.getTag();
            }
            BitmapUtils bitmapUtils = new BitmapUtils(getContext());

            title = list.get(position).getTitle();


            mp4_url = list.get(position).getMp4_url();
            String cover = list.get(position).getCover();
            //预加载缩略图
            bitmapUtils.display(holder.shiting_icon, cover);

            //显示数据
            holder.title.setText(list.get(position).getTitle());//标题
            holder.description.setText(list.get(position).getDescription());//描述
            //TODO:时间格式转换
            long time = list.get(position).getLength();
            int a = (int) (time / 60);
            int b = (int) (time % 60);
            if (b < 10) {
                holder.length.setText("0" + a + ":" + "0" + b);//时长
            } else {
                holder.length.setText("0" + a + ":" + b);//时长
            }
            Log.e("MMM", time + "");
            holder.playCount.setText(list.get(position).getPlayCount() + "");//观看次数
            holder.replyCount.setText(list.get(position).getReplyCount() + "");//跟帖数
            //播放监听
            holder.mPlayBtnView.setOnClickListener(new MyOnclick(mp4_url, holder.mVideoViewLayout, position));
            holder.mVideoViewLayout.setVideoPlayCallback(new MyVideoPlayCallback(holder.mPlayBtnView, holder.mVideoViewLayout));

            // TODO: 2015/11/19 分享
            holder.iv_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareUtils.shareContent(getActivity(), title, mp4_url);
                }
            });
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
            private ImageView iv_share;
            public TextView title;
            public TextView description;
            public TextView length;
            public TextView playCount;
            public TextView replyCount;

        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (httpUtils != null) {
            if (httpHandler != null) {
                httpHandler.cancel();
            }
        }
    }

    @Override
    public void onDestroy() {
        App.setMediaPlayerNull();
        super.onDestroy();
    }
}
