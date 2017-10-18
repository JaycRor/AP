package lz.newone.video.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import lz.newone.R;
import lz.newone.beans.VideoBean;
import lz.newone.url.Urls;
import lz.newone.utils.LogUtils;
import lz.newone.video.VideoAdapter;
import lz.newone.video.presenter.VideoPresenter;
import lz.newone.video.presenter.VideoPresenterImpl;
import lz.newone.video.view.VideoView;

/**
 * Created by AdminJax on 2017/10/14.
 */

public class VideoFragment extends Fragment implements VideoView,SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "VideoFragment";

    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private VideoAdapter mAdapter;
    private List<VideoBean> mData;
    private VideoPresenter mVideoPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVideoPresenter = new VideoPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video,null);
        mRecyclerView = view.findViewById(R.id.video_recycle_view);
        mSwipeRefreshWidget = view.findViewById(R.id.swipe_refresh_widget_video);



        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new VideoAdapter(getActivity().getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        onRefresh();

        return view;
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                //加载更多
                LogUtils.d(TAG, "loading more data");
                mVideoPresenter.loadVideo(mData);
            }
        }
    };

    @Override
    public void onRefresh() {
        if(mData != null) {
            mData.clear();
        }

        mVideoPresenter.loadVideo(mData);
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void showProgress() {
        mSwipeRefreshWidget.setRefreshing(true);
    }

    @Override
    public void addNews(List<VideoBean> videoList) {

        if (mData ==null){
            mData = new ArrayList<>();
        }
        mData.addAll(videoList);
        mAdapter.setDate(mData);
        mAdapter.notifyDataSetChanged();
        showProgress();
        hideProgress();
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshWidget.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg() {

    }
}
