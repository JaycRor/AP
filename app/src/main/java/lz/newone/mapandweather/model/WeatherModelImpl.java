package lz.newone.mapandweather.model;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


import lz.newone.beans.WeatherBean;
import lz.newone.url.Urls;
import lz.newone.utils.LogUtils;
import lz.newone.utils.OkHttpUtils;
import lz.newone.mapandweather.WeatherJsonUtils;

/**
 * Description :
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 2015/12/22
 */
public class WeatherModelImpl implements WeatherModel {

    private static final String TAG = "WeatherModelImpl";

    @Override
    public void loadWeatherData(String cityName, final LoadWeatherListener listener) {
        try {
            String url = Urls.WEATHER + URLEncoder.encode(cityName, "utf-8");
            OkHttpUtils.ResultCallback<String> callback = new OkHttpUtils.ResultCallback<String>() {
                @Override
                public void onSuccess(String response) {
                    List<WeatherBean> lists = WeatherJsonUtils.getWeatherInfo(response);
                    listener.onSuccess(lists);
                }

                @Override
                public void onFailure(Exception e) {
                    listener.onFailure("load weather data failure.", e);
                }
            };
            OkHttpUtils.get(url, callback);
        } catch (UnsupportedEncodingException e) {
            LogUtils.e(TAG, "url encode error.", e);
        }
    }



    public interface LoadWeatherListener {
        void onSuccess(List<WeatherBean> list);
        void onFailure(String msg, Exception e);
    }
}
