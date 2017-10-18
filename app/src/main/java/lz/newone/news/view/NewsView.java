package lz.newone.news.view;

import java.util.List;
import lz.newone.beans.NewsBean;


public interface NewsView {

    void showProgress();

    void addNews(List<NewsBean> newsList);

    void hideProgress();

    void showLoadFailMsg();
}
