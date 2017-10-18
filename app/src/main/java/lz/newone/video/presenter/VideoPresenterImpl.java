package lz.newone.video.presenter;

import java.util.List;

import lz.newone.beans.VideoBean;
import lz.newone.url.Urls;
import lz.newone.utils.LogUtils;
import lz.newone.video.model.OnLoadVideoListListener;
import lz.newone.video.model.VideoModel;
import lz.newone.video.model.VideoModelImpl;
import lz.newone.video.view.VideoView;

/**
 * Created by AdminJax on 2017/10/14.
 */

public class VideoPresenterImpl implements VideoPresenter,OnLoadVideoListListener {

    private static final String TAG = "VideoPresenterImpl";

    private VideoModel videoModel;
    private VideoView videoView;

    public VideoPresenterImpl(VideoView videoView) {
        this.videoView = videoView;
        this.videoModel = new VideoModelImpl();
    }

    @Override
    public void loadVideo(List<VideoBean> list) {
        String url = Urls.ZX_VIDEO;
        LogUtils.d(TAG, url);

        videoView.showProgress();
        videoModel.loadVideo(url, this);
        videoView.hideProgress();
    }

    @Override
    public void OnSuccess(List<VideoBean> list) {
        videoView.hideProgress();
        videoView.addNews(list);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        videoView.hideProgress();
        videoView.showLoadFailMsg();
    }
}
