package com.example.map_demo05;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.VisibleRegion;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MapView mapView;
    private AMap aMap;

    private static final int SCROLLBY = 100;
    private static final LatLng ZHONGGUANCUN = new LatLng(39.983, 116.315);
    private static final LatLng SHANGHAI = new LatLng(31.238, 121.501);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        Button zhongguancunBtn =(Button)findViewById(R.id.Zhongguancun);
        zhongguancunBtn.setOnClickListener(this);
        Button shanghaiBtn = (Button)findViewById(R.id.Lujiazui);
        shanghaiBtn.setOnClickListener(this);

        Button left = (Button)findViewById(R.id.scroll_left);
        left.setOnClickListener(this);

        Button up = (Button)findViewById(R.id.scroll_up);
        up.setOnClickListener(this);

        Button down = (Button)findViewById(R.id.scroll_down);
        down.setOnClickListener(this);

        Button right = (Button)findViewById(R.id.scroll_right);
        right.setOnClickListener(this);


        init();
        //tips:  amap.moveCamera接口
    }

    private void init(){
        if(aMap == null){
            aMap = mapView.getMap();
        }

    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.Zhongguancun:
                moveCam(ZHONGGUANCUN);
                aMap.clear();
                addMarker(ZHONGGUANCUN);
                break;
            case R.id.Lujiazui:
                moveCam(SHANGHAI);
                aMap.clear();
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

        }
    }
    private void moveCamWithAnim(CameraUpdate cu){
        aMap.animateCamera(cu, 1000, null);

    }

    private void moveCam(LatLng pos){
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(pos, 18, 0, 0)));
    }

    private void addMarker(LatLng pos){
        aMap.addMarker(new MarkerOptions().position(pos).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }



}
