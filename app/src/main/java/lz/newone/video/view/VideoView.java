package lz.newone.video.view;

import java.util.List;

import lz.newone.beans.VideoBean;

/**
 * Created by AdminJax on 2017/10/14.
 */

public interface VideoView {
    void showProgress();

    void addNews(List<VideoBean> videoList);

    void hideProgress();

    void showLoadFailMsg();
}
