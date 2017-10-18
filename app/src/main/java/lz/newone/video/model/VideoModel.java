package lz.newone.video.model;

/**
 * Created by AdminJax on 2017/10/14.
 */

public interface VideoModel {

    void loadVideo(String url, OnLoadVideoListListener listener);
}
