package lz.newone.mapandweather;

import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import lz.newone.R;
import lz.newone.beans.WeatherBean;

public class WeatherJsonUtils {

    /**
     * 获取天气信息
     * @param json
     * @return
     */
    public static List<WeatherBean> getWeatherInfo(String json) {
        List<WeatherBean> list = new ArrayList<WeatherBean>();
        if (TextUtils.isEmpty(json)) {
            return list;
        }
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(json).getAsJsonObject();
        String status = jsonObj.get("status").getAsString();
        if("1000".equals(status)) {
            JsonArray jsonArray = jsonObj.getAsJsonObject("data").getAsJsonArray("forecast");
            for (int i = 0; i < jsonArray.size(); i++) {
                WeatherBean weatherBean = getWeatherBeanFromJson(jsonArray.get(i).getAsJsonObject());
                list.add(weatherBean);
            }
        }
        return list;
    }

    private static WeatherBean getWeatherBeanFromJson(JsonObject jsonObject) {

        String temperature = jsonObject.get("high").getAsString() + " " + jsonObject.get("low").getAsString();
        String weather = jsonObject.get("type").getAsString();
        String wind = jsonObject.get("fengxiang").getAsString();
        String date = jsonObject.get("date").getAsString();

        WeatherBean weatherBean = new WeatherBean();

        weatherBean.setDate(date);
        weatherBean.setTemperature(temperature);
        weatherBean.setWeather(weather);
        weatherBean.setWeek(date.substring(date.length()-3));
        weatherBean.setWind(wind);
        weatherBean.setImageRes(getWeatherImage(weather));
        return weatherBean;
    }

    public static int getWeatherImage(String weather) {
        if (weather.equals("多云") || weather.equals("多云转阴") || weather.equals("多云转晴")) {
            return R.drawable.weather_icon_mostcloudy_day;
        } else if (weather.equals("中雨") || weather.equals("中到大雨")) {
            return R.drawable.weather_icon_moderate_rain_day;
        } else if (weather.equals("雷阵雨")) {
            return R.drawable.weather_icon_thunderstorm_day;
        } else if (weather.equals("阵雨") || weather.equals("阵雨转多云")) {
            return R.drawable.weather_icon_rain_day;
        } else if (weather.equals("暴雪")) {
            return R.drawable.weather_icon_snowstorm_day;
        } else if (weather.equals("暴雨")) {
            return R.drawable.weather_icon_heavy_rain_day;
        } else if (weather.equals("大暴雨")) {
            return R.drawable.weather_icon_heavy_rain_day;
        } else if (weather.equals("大雪")) {
            return R.drawable.weather_icon_heavy_snow_day;
        } else if (weather.equals("大雨") || weather.equals("大雨转中雨")) {
            return R.drawable.weather_icon_heavy_rain_day;
        } else if (weather.equals("晴")) {
            return R.drawable.weather_icon_sunny_day;
        } else if (weather.equals("沙尘暴")) {
            return R.drawable.weather_icon_sand_devil_day;
        } else if (weather.equals("特大暴雨")) {
            return R.drawable.weather_icon_heavy_rain_day;
        } else if (weather.equals("雾") || weather.equals("雾霾")) {
            return R.drawable.weather_icon_fog_day;
        } else if (weather.equals("小雪")) {
            return R.drawable.weather_icon_light_snow_day;
        } else if (weather.equals("小雨")) {
            return R.drawable.weather_icon_light_rain_day;
        } else if (weather.equals("阴")) {
            return R.drawable.weather_icon_cloud_day;
        } else if (weather.equals("雨夹雪")) {
            return R.drawable.weather_icon_rain_day;
        } else if (weather.equals("阵雪")) {
            return R.drawable.weather_icon_light_snow_day;
        } else if (weather.equals("中雪")) {
            return R.drawable.weather_icon_moderate_snow_day;
        } else {
            return R.drawable.weather_icon_cloud_day;
        }
    }

}
