package com.example.administrator.mapzuobiao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.VisibleRegion;

public class MainActivity extends AppCompatActivity implements AMap.OnMapClickListener, AMap.OnCameraChangeListener{
    private MapView mapView;
    private  AMap aMap;
    private TextView TapTextView;
    private  TextView CameraTextView;
    private  TextView FanWeiText;
    public static final LatLng SHANGHAI = new LatLng(31.238068,121.501654);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = (MapView)findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        aMap = mapView.getMap();
        aMap.setOnMapClickListener(this);
        aMap.setOnCameraChangeListener(this);

        TapTextView = (TextView)findViewById(R.id.tap_text);
        CameraTextView = (TextView)findViewById(R.id.camer_text);
        FanWeiText = (TextView)findViewById(R.id.fanwei_text);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onMapClick(LatLng pos) {
        TapTextView.setText("点击 = " + pos);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        CameraTextView.setText("镜头位移 = " + cameraPosition);
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        CameraTextView.setText("镜头位移结束 = " + cameraPosition.toString());
        VisibleRegion visibleRegion = aMap.getProjection().getVisibleRegion();
        LatLngBounds latLngBounds = visibleRegion.latLngBounds;
        boolean isContain = latLngBounds.contains(SHANGHAI);
        if(isContain){
            FanWeiText.setText("上海市在地图可见区域内");
        }else {
            FanWeiText.setText("提示！！！上海市超出地图可见范围");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
