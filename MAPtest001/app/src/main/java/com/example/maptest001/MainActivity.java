package com.example.maptest001;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.SupportMapFragment;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;

public class MainActivity extends AppCompatActivity {
    private AMap aMap;
    private MapView mapView;
    private Circle circle;
    private static final LatLng JiangMen = new LatLng(22.626494,113.107612);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //方法一
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        init();
        //方法二
//        mapView = (MapView)findViewById(R.id.map);
//        mapView.onCreate(savedInstanceState);
//
//        aMap = mapView.getMap();
        //方法三
//        init();

    }

//    //显示地图
//    public void init(){
//        if(aMap == null){
//            aMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
//        }
//    }
   // 方法一
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }
    private void setUpMap(){
        //todo 这里应该画一个圆形。

        circle = aMap.addCircle(new CircleOptions().center(JiangMen).radius(4000)
                .strokeColor(Color.argb(50, 1, 1, 1)).fillColor(Color.argb(50, 1, 1, 1)));



    }

}
