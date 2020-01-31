package com.example.maptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.VisibleRegion;

public class MainActivity extends AppCompatActivity implements AMap.OnMapClickListener, AMap.OnMapLongClickListener, AMap.OnCameraChangeListener{
    private MapView mapView;
    private AMap aMap;
    private TextView mTapTextView;
    private TextView mCameraTextView;
    private TextView mWhetherVisibleView;
    public static final LatLng SHANGHAI = new LatLng(31.238068, 121.501654);// 上海市经纬度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = (MapView)findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        aMap.setOnMapClickListener(this);
        aMap.setOnMapLongClickListener(this);
        aMap.setOnCameraChangeListener(this);

        mTapTextView = (TextView) findViewById(R.id.tap_text);
        mCameraTextView = (TextView)findViewById(R.id.camera_text);
        mWhetherVisibleView = (TextView)findViewById(R.id.whether_visible);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onMapClick(LatLng pos){
        Log.d("JJJ", "clickcckckckckck!!!!!!!!!!!!!!");
        mTapTextView.setText("点击 = " + pos);
    }

    @Override
    public void onMapLongClick(LatLng pos){
        Log.d("JJJ", "long clickcckckckckck!!!!!!!!!!!!!!");
        mTapTextView.setText("长按 = " + pos);

    }

    @Override
    public void onCameraChange(CameraPosition camPos){
        mCameraTextView.setText("镜头位移 = " + camPos.toString());
    }

    @Override
    public void onCameraChangeFinish(CameraPosition camPos){

        mCameraTextView.setText("镜头位移结束 = " + camPos.toString());


        VisibleRegion visibleRegion = aMap.getProjection().getVisibleRegion();
        Log.d("JJJ", "finish!!! !!!!!!!! !!!!!!!!");

        Log.d("JJJ", "far left = " + visibleRegion.farLeft);
        Log.d("JJJ", "far right = " + visibleRegion.farRight);
        Log.d("JJJ", "near left = " + visibleRegion.nearLeft);
        Log.d("JJJ", "near right = " + visibleRegion.nearRight);
        Log.d("JJJ", "far left = " + visibleRegion.farLeft);


        Log.d("JJJ", "bound  northeast = " + visibleRegion.latLngBounds.northeast);
        Log.d("JJJ", "bound  southwest = " + visibleRegion.latLngBounds.southwest);

        LatLngBounds latLngBounds = visibleRegion.latLngBounds;
        boolean isContain = latLngBounds.contains(SHANGHAI);
        if(isContain){
            mWhetherVisibleView.setText("上海市在地图可见区域内");
        }else{
            mWhetherVisibleView.setText("上海市超出地图可见范围");

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
