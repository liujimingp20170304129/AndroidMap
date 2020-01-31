package com.example.map_demo3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private MapView mapView;
    private AMap aMap;
    public static final LatLng ZHONGGUANCHUN = new LatLng(39.983926,116.316412);
    public static final LatLng JIANGZHI = new LatLng(22.626522,113.107242);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = (MapView)findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        Button ZHONGGUANCHUNBTN = (Button)findViewById(R.id.zhongguanchun);
        ZHONGGUANCHUNBTN.setOnClickListener(this);

        Button JIANGZHI =(Button)findViewById(R.id.Jiangzhi) ;
        JIANGZHI.setOnClickListener(this);
        init();
    }
    private  void init(){
        if (aMap == null){
            aMap = mapView.getMap();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.zhongguanchun:
//                封装前的代码
//                CameraUpdate cu = CameraUpdateFactory.newCameraPosition(new CameraPosition(ZHONGGUANCHUN, 18 , 0 , 0));
//                aMap.moveCamera(cu);
//                aMap.clear();
//                aMap.addMarker(new MarkerOptions().position(ZHONGGUANCHUN).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
//                封装后
                moveCamera(ZHONGGUANCHUN);
                aMap.clear();
                addMarker(ZHONGGUANCHUN);
                break;
            case R.id.Jiangzhi:
//                封装前的代码
//                CameraUpdate jiang = CameraUpdateFactory.newCameraPosition(new CameraPosition(JIANGZHI, 18 , 0 , 0));
//                aMap.moveCamera(jiang);
//                aMap.clear();
//                aMap.addMarker(new MarkerOptions().position(JIANGZHI).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                //                封装后
                moveCamera(JIANGZHI);
                aMap.clear();
                addMarker(JIANGZHI);
                break;
        }

    }
//    将Cameraupdate、moveCamera、addMarker封装起来

    private  void moveCamera(LatLng pos){
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(pos, 18 , 0 , 0)));
    }
    private void addMarker(LatLng pos){
        aMap.addMarker(new MarkerOptions().position(pos).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
    }
}
