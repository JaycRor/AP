package lz.newone.mapandweather.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import lz.newone.R;
import lz.newone.beans.WeatherBean;
import lz.newone.utils.LogUtils;
import lz.newone.mapandweather.presenter.WeatherPresenter;
import lz.newone.mapandweather.presenter.WeatherPresenterImpl;

/**
 * Created by AdminJax on 2017/10/12.
 */

public class LzFragment extends Fragment implements TencentLocationListener,WeatherView{
    private MapView mMapView;
    private TencentLocationManager mLocationManager;
    private String city;
    private TencentMap tencentMap;

    private WeatherPresenter mWeatherPresenter;
    private TextView mTodayTV;
    private ImageView mTodayWeatherImage;
    private TextView mTodayTemperatureTV;
    private TextView mTodayWindTV;
    private TextView mTodayWeatherTV;
    private TextView mCityTV;
    private ProgressBar mProgressBar;
    private LinearLayout mWeatherLayout;
    private LinearLayout mWeatherContentLayout;
    private FrameLayout mRootLayout;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, (ViewGroup) getActivity().findViewById(R.id.vp_content), false);
        //绑定布局
        initView(view);

        tencentMap = mMapView.getMap();
        Context context = getActivity();
        mLocationManager = TencentLocationManager.getInstance(context);
        //申请权限
        getPermissions();

        return view;
    }

    private void initView(View view) {
        mMapView = view.findViewById(R.id.map);
        mTodayTV = view.findViewById(R.id.today);
        mTodayWeatherImage =  view.findViewById(R.id.weatherImage);
        mTodayTemperatureTV =  view.findViewById(R.id.weatherTemp);
        mTodayWindTV = view.findViewById(R.id.wind);
        mTodayWeatherTV =  view.findViewById(R.id.weather);
        mCityTV = view.findViewById(R.id.city);
        mProgressBar =  view.findViewById(R.id.progress);
        mWeatherLayout =  view.findViewById(R.id.weather_layout);
        mWeatherContentLayout = view.findViewById(R.id.weather_content);
        mRootLayout = view.findViewById(R.id.root_layout);
    }

    private void getPermissions() {
        // 需要请求权限
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

            if (ActivityCompat.checkSelfPermission(getActivity(),permissions[0]) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(getActivity(),permissions, 0);
            }
        }
    }

    private void Stoplocation() {

        mLocationManager.removeUpdates(this);
    }

    private void startLocation() {
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setInterval(7000);
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA);
        mLocationManager.requestLocationUpdates(request, this);
    }


    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        startLocation();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
        Stoplocation();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    //请求权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtils.d("请求权限成功","=======>开始定位");
        startLocation();
    }

    //请求位置回调1
    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {

        LogUtils.d("调试","回调成功");
        if (TencentLocation.ERROR_OK == i){
            //success location
            city = tencentLocation.getCity();
            String add = tencentLocation.getAddress();

            LatLng mLatLng = new LatLng(tencentLocation.getLatitude(),tencentLocation.getLongitude());

            //移动地图
            CameraUpdate cameraSigma = CameraUpdateFactory.newCameraPosition(new CameraPosition(
                    mLatLng, //新的中心点坐标
                    13,     // 新的缩放级别
                    0f,     // 俯仰角 0~45° (垂直地图时为0)
                    0f));   // 偏航角 0~360° (正北方为0)
            // 移动地图
            tencentMap.moveCamera(cameraSigma);
            tencentMap.addMarker(new MarkerOptions().position(mLatLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(0x800000))
                    .title(city).snippet("DefaultMarker"));
            LogUtils.d("CityName",city+add);
            Stoplocation();
        }else {
            LogUtils.d("location_Fail","failes");
        }
        mWeatherPresenter = new WeatherPresenterImpl(getActivity().getApplication(),city, this);
        mWeatherPresenter.loadWeatherData();
    }
    //请求位置回调2
    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showWeatherLayout() {
        mWeatherLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCity(String city) {
        mCityTV.setText(city);
    }

    @Override
    public void setToday(String data) {
        mTodayTV.setText(data);
    }

    @Override
    public void setTemperature(String temperature) {
        mTodayTemperatureTV.setText(temperature);
    }

    @Override
    public void setWind(String wind) {
        mTodayWindTV.setText(wind);
    }

    @Override
    public void setWeather(String weather) {
        mTodayWeatherTV.setText(weather);
    }

    @Override
    public void setWeatherImage(int res) {
        mTodayWeatherImage.setImageResource(res);
    }

    @Override
    public void setWeatherData(List<WeatherBean> lists) {
        List<View> adapterList = new ArrayList<View>();
        for (WeatherBean weatherBean : lists) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_weather, null, false);
            TextView dateTV = view.findViewById(R.id.date);
            ImageView todayWeatherImage =  view.findViewById(R.id.weatherImage);
            TextView todayTemperatureTV =  view.findViewById(R.id.weatherTemp);
            TextView todayWindTV =  view.findViewById(R.id.wind);
            TextView todayWeatherTV =  view.findViewById(R.id.weather);

            dateTV.setText(weatherBean.getWeek());
            todayTemperatureTV.setText(weatherBean.getTemperature());
            todayWindTV.setText(weatherBean.getWind());
            todayWeatherTV.setText(weatherBean.getWeather());
            todayWeatherImage.setImageResource(weatherBean.getImageRes());
            mWeatherContentLayout.addView(view);
            adapterList.add(view);
        }
    }

    @Override
    public void showErrorToast(String msg) {

    }
}
