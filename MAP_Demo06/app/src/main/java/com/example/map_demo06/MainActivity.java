package com.example.map_demo06;


import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.animation.BaseInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.Projection;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.VisibleRegion;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AMap.OnMarkerClickListener, SeekBar.OnSeekBarChangeListener{
    private MapView mapView;
    private AMap aMap;
    private MarkerOptions markerOption;
    private Circle circle;
    private SeekBar colorBar;
    private SeekBar alphaBar;
    private SeekBar widthBar;

    private static final int SCROLLBY = 100;
    private static final LatLng ZHONGGUANCUN = new LatLng(39.983, 116.315);//中关村坐标
    private static final LatLng SHANGHAI = new LatLng(31.238, 121.501); //上海坐标
    private static final int ZOOM_ANIME_DURATION = 2000;
    private static final int HUE_MAX = 255;
    private static final int WIDTH_MAX = 50;
    private static final int ALPHA_MAX = 255;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        //上海，中关村按钮
        Button zhongguancunBtn =(Button)findViewById(R.id.Zhongguancun);
        zhongguancunBtn.setOnClickListener(this);
        Button shanghaiBtn = (Button)findViewById(R.id.Shanghai);
        shanghaiBtn.setOnClickListener(this);

        //上，下，左，右按钮
        Button left = (Button)findViewById(R.id.scroll_left);
        left.setOnClickListener(this);

        Button up = (Button)findViewById(R.id.scroll_up);
        up.setOnClickListener(this);

        Button down = (Button)findViewById(R.id.scroll_down);
        down.setOnClickListener(this);

        Button right = (Button)findViewById(R.id.scroll_right);
        right.setOnClickListener(this);

        Button zoomIn = (Button)findViewById(R.id.zoom_in);
        zoomIn.setOnClickListener(this);

        Button zoomOut = (Button)findViewById(R.id.zoom_out);
        zoomOut.setOnClickListener(this);

        colorBar = (SeekBar)findViewById(R.id.hueSeekBar);
        colorBar.setMax(HUE_MAX);
        colorBar.setProgress(50);

        alphaBar = (SeekBar)findViewById(R.id.alphaSeekBar);
        alphaBar.setMax(ALPHA_MAX);
        alphaBar.setProgress(50);

        widthBar = (SeekBar)findViewById(R.id.widthSeekBar);
        widthBar.setMax(WIDTH_MAX);
        widthBar.setProgress(50);


        init();
        //tips:  amap.moveCamera接口
    }
        //显示地图
    private void init(){
        if(aMap == null){
            aMap = mapView.getMap();
            setUpMap();
        }

        aMap.setOnMarkerClickListener(this);

    }

    private void setUpMap(){
        colorBar.setOnSeekBarChangeListener(this);
        alphaBar.setOnSeekBarChangeListener(this);
        widthBar.setOnSeekBarChangeListener(this);

        //todo 这里应该画一个圆形。

        circle = aMap.addCircle(new CircleOptions().center(SHANGHAI).radius(4000)
                .strokeColor(Color.argb(50, 1, 1, 1)).fillColor(Color.argb(50, 1, 1, 1)));



    }


    public void onClick(View view){
        switch(view.getId()){
            case R.id.Zhongguancun:
                moveCam(ZHONGGUANCUN);
//                aMap.clear();
                addMarker(ZHONGGUANCUN);
                break;
            case R.id.Shanghai:
                moveCam(SHANGHAI);
//                aMap.clear();
                addMarker(SHANGHAI);
                break;
            case R.id.scroll_left:
                moveCamWithAnim(CameraUpdateFactory.scrollBy(-SCROLLBY, 0));
                break;
            case R.id.scroll_up:
                moveCamWithAnim(CameraUpdateFactory.scrollBy(0, SCROLLBY));
                break;
            case R.id.scroll_down:
                moveCamWithAnim(CameraUpdateFactory.scrollBy(0, -SCROLLBY));
                break;
            case R.id.scroll_right:
                moveCamWithAnim(CameraUpdateFactory.scrollBy(SCROLLBY, 0));
                break;
            case R.id.zoom_in:
//                aMap.animateCamera(CameraUpdateFactory.zoomIn(), ZOOM_ANIME_DURATION, null);
                aMap.moveCamera(CameraUpdateFactory.zoomIn());
                break;
            case R.id.zoom_out:
//                aMap.animateCamera(CameraUpdateFactory.zoomOut(), ZOOM_ANIME_DURATION, null);
                aMap.moveCamera(CameraUpdateFactory.zoomOut());
                break;
            default:
                break;

        }
    }
    private void moveCamWithAnim(CameraUpdate cu){
        aMap.animateCamera(cu, 1000, null);

    }

    private void moveCam(LatLng pos){
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(pos, 12, 0, 0)));
    }

    @Override
    public boolean onMarkerClick(final Marker marker){
        jumpPoint(marker);
        Toast.makeText(MainActivity.this, "marker clicked ", Toast.LENGTH_SHORT).show();
        return true;
    }


    private void addMarker(LatLng pos){
        markerOption = new MarkerOptions().position(pos).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        aMap.addMarker(markerOption);
    }

    public void jumpPoint(final Marker marker){
        final Handler handler = new Handler();

        final long start = SystemClock.uptimeMillis();
        Projection proj = aMap.getProjection();
        final LatLng markerLatlng = marker.getPosition();
        Point point = proj.toScreenLocation(markerLatlng);
        point.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(point);
        final long duration = 1500;

        final Interpolator interpolator =  new BounceInterpolator();

        handler.post(new Runnable(){
            @Override
            public void run(){
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float)elapsed / duration);
                double lng = t* markerLatlng.longitude + (1-t) * startLatLng.longitude;
                double lat = t * markerLatlng.latitude + (1-t) * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if(t < 1.0){
                    handler.postDelayed(this, 60);
                }
            }
        });

    }

    @Override
    public void onProgressChanged(SeekBar var1, int var2, boolean var3){


    }

    @Override
    public void onStartTrackingTouch(SeekBar var1){

    }

    @Override
    public void onStopTrackingTouch(SeekBar var1){

    }



}
