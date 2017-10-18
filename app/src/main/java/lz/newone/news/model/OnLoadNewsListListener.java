package lz.newone.news.model;

import java.util.List;

import lz.newone.beans.NewsBean;

/**
 * Created by AdminJax on 2017/10/13.
 */

public interface OnLoadNewsListListener {

    void onSuccess(List<NewsBean> list);

    void onFailure(String msg, Exception e);
}
