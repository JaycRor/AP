package lz.newone.news.model;

import lz.newone.beans.NewsDetailBean;

/**
 * Created by AdminJax on 2017/10/13.
 */

public interface OnLoadNewsDetailListener {

    void onSuccess(NewsDetailBean newsDetailBean);

    void onFailure(String msg, Exception e);
}
