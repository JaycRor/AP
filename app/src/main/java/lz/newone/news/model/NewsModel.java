package lz.newone.news.model;

/**
 * Created by AdminJax on 2017/10/13.
 */

public interface NewsModel {

    void loadNews(String url, int type, OnLoadNewsListListener listener);

    void loadNewsDetail(String docid, OnLoadNewsDetailListener listener);

}
