package lz.newone.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
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
import com.tencent.tencentmap.mapsdk.maps.model.CircleOptions;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import lz.newone.R;
import lz.newone.utils.LogUtils;

/**
 * 该包下的全部Fragment，先作为框架填充效果，后面会被替换掉
 * Created by AdminJax on 2017/10/12.
 */

public class MineFragment extends Fragment implements TencentLocationListener{
    private MapView mMapView;
    private TencentLocationManager mLocationManager;
    private TencentLocation mLocation;
    private SeekBar sbWidth;
    private SeekBar sbTransparency;
    private SeekBar sbHue;
    private String city;
    private Marker locationMark;
    private TextView mTextView;
    private TencentMap tencentMap;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout, (ViewGroup) getActivity().findViewById(R.id.vp_content), false);
        mMapView = rootView.findViewById(R.id.map);
//        mTextView = rootView.findViewById(R.id.location_city);
        tencentMap = mMapView.getMap();

        Context context = getActivity();
        mLocationManager = TencentLocationManager.getInstance(context);

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

        return rootView;
    }

    private void Stoplocation() {

        mLocationManager.removeUpdates(this);
    }

    private void startLocation() {
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setInterval(7000);
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA);
        LogUtils.d("调试","设置返回参数");
        mLocationManager.requestLocationUpdates(request, this);
        LogUtils.d("调试","请求定位");
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
//            mTextView.setText(city);

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
//            tencentMap.addCircle(new CircleOptions().center(mLatLng).radius(1000d)
//                      .fillColor(Color.HSVToColor(sbTransparency.getProgress(),
//                              new float[]{sbHue.getProgress(), 1f, 1f}))
//                      .strokeColor(0xff000000).strokeWidth(1000));

            LogUtils.d("CityName",city+add);
            Stoplocation();
        }else {
            LogUtils.d("location_Fail","failes");
        }
    }
    //请求位置回调2
    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

}
