package lz.newone.video.model;

import java.util.List;

import lz.newone.beans.VideoBean;

/**
 * Created by AdminJax on 2017/10/14.
 */

public interface OnLoadVideoListListener {

    void OnSuccess(List<VideoBean> list);

    void onFailure(String msg, Exception e);
}
