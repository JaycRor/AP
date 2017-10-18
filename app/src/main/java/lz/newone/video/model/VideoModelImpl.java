package lz.newone.video.model;

import java.util.List;

import lz.newone.beans.VideoBean;
import lz.newone.utils.LogUtils;
import lz.newone.utils.OkHttpUtils;
import lz.newone.video.VideoJsonUtils;

/**
 * Created by AdminJax on 2017/10/14.
 */

public class VideoModelImpl implements VideoModel {

    @Override
    public void loadVideo(String url, final OnLoadVideoListListener listener) {
        OkHttpUtils.ResultCallback<String> callback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                List<VideoBean> videoBeanList = VideoJsonUtils.getVideoList(response);
                listener.OnSuccess(videoBeanList);
                LogUtils.e("VideoBeanList",videoBeanList.toString());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("no video found",e);
            }
        };
        OkHttpUtils.get(url,callback);
    }
}
