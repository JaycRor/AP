package lz.newone.mapandweather.presenter;

import android.content.Context;

import java.util.List;

import lz.newone.beans.WeatherBean;
import lz.newone.utils.ToolsUtil;
import lz.newone.mapandweather.model.WeatherModel;
import lz.newone.mapandweather.model.WeatherModelImpl;
import lz.newone.mapandweather.view.WeatherView;


/**
 * Description :
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 2015/12/22
 */
public class WeatherPresenterImpl implements WeatherPresenter, WeatherModelImpl.LoadWeatherListener {

    private WeatherView mWeatherView;
    private WeatherModel mWeatherModel;
    private Context mContext;
    private String cityName;

//    public WeatherPresenterImpl(Context context, WeatherView weatherView) {
//        this.mContext = context;
//        this.mWeatherView = weatherView;
//        mWeatherModel = new WeatherModelImpl();
//    }

    public WeatherPresenterImpl(Context context,String city, WeatherView weatherView) {
        this.cityName = city;           //讲城市名称传过来
        this.mContext = context;
        this.mWeatherView = weatherView;
        mWeatherModel = new WeatherModelImpl();
    }

    @Override
    public void loadWeatherData() {
        mWeatherView.showProgress();
        if(!ToolsUtil.isNetworkAvailable(mContext)) {
            mWeatherView.hideProgress();
            mWeatherView.showErrorToast("无网络连接");
            return;
        }
        mWeatherView.setCity(cityName);
        mWeatherModel.loadWeatherData(cityName, WeatherPresenterImpl.this);

    }

    @Override
    public void onSuccess(List<WeatherBean> list) {
        if(list != null && list.size() > 0) {
            WeatherBean todayWeather = list.remove(0);
            mWeatherView.setToday(todayWeather.getDate());
            mWeatherView.setTemperature(todayWeather.getTemperature());
            mWeatherView.setWeather(todayWeather.getWeather());
            mWeatherView.setWind(todayWeather.getWind());
            mWeatherView.setWeatherImage(todayWeather.getImageRes());
        }
        mWeatherView.setWeatherData(list);
        mWeatherView.hideProgress();
        mWeatherView.showWeatherLayout();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mWeatherView.hideProgress();
        mWeatherView.showErrorToast("获取天气数据失败");
    }

}
