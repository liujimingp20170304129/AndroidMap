package com.example.maptest_cam;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;

import com.amap.api.maps2d.SupportMapFragment;
import com.amap.api.maps2d.model.CameraPosition;

import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.overlay.PoiOverlay;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements View.OnClickListener, AMap.OnMarkerClickListener, AMap.InfoWindowAdapter, PoiSearch.OnPoiSearchListener, TextWatcher, Inputtips.InputtipsListener{
    private MapView mapView;
    private AMap aMap;
    private AutoCompleteTextView searchText;
    private EditText cityText;
    private String keyword;
    private ProgressDialog progDialog = null;
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private int currentPage = 0;
    private PoiResult poiResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        if(aMap == null){
            aMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            setUpMap();
        }
    }

    private void setUpMap(){
        Button searButton = (Button)findViewById(R.id.searchButton);
        searButton.setOnClickListener(this);
        Button nextButton = (Button)findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);
        searchText = (AutoCompleteTextView)findViewById(R.id.keyword);
        searchText.addTextChangedListener(this);

        cityText = (EditText)findViewById(R.id.city);
        aMap.setOnMarkerClickListener(this);
        aMap.setInfoWindowAdapter(this);
    }

    private void doSearchQuery(){
        showProgressDialog();
        currentPage = 0;

        query = new PoiSearch.Query(keyword, "", cityText.getText().toString());
        query.setPageSize(10);//设置每页最多多少条poi item
        query.setPageNum(currentPage);
        query.setCityLimit(true);

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();

    }


    private void searchButton(){
        keyword = searchText.getText().toString();

        if("".equals(keyword)){
            Toast.makeText(MainActivity.this, "请输入", Toast.LENGTH_SHORT).show();
        }else{
            doSearchQuery();
        }
    }

    private void nextButton(){

    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.searchButton:
                searchButton();
                break;
            case R.id.nextButton:
                nextButton();
                break;
            default:
                break;
        }

    }

    public boolean onMarkerClick(Marker marker){
        return true;
    }


    public View getInfoWindow(Marker marker){
        return null;
    }

    public View getInfoContents(Marker marker){
        return null;
    }


    private void showProgressDialog(){
        if(progDialog == null){
            progDialog = new ProgressDialog(this);
        }

        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("正在搜索" + keyword);
        progDialog.show();
    }

    private void dismissProgressDialog(){
        if(progDialog != null){
            progDialog.dismiss();
        }

    }

    @Override
    public void onPoiSearched(PoiResult result, int errorCode){
        dismissProgressDialog();
        if(errorCode == AMapException.CODE_AMAP_SUCCESS){
            if(result != null && result.getQuery() != null){
                if(result.getQuery().equals(query)){
                    poiResult = result;

                    List<PoiItem> poiItems = poiResult.getPois();

                    List<SuggestionCity> suggestionCityList = poiResult.getSearchSuggestionCitys();

                    if(poiItems != null && poiItems.size() > 0){
                        aMap.clear();
                        PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
                        poiOverlay.removeFromMap();
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();
                    }
                }
            }else{
                Toast.makeText(MainActivity.this, "对不起没有搜索到数据", Toast.LENGTH_SHORT);
            }
        }else{
            Toast.makeText(MainActivity.this, "错误代码：" + errorCode, Toast.LENGTH_SHORT);
        }
    }
    @Override
    public void onPoiItemSearched(PoiItem poiItem, int errorCode){
    }
    public void beforeTextChanged(CharSequence s, int start, int count, int after){  }

    public void onTextChanged(CharSequence s, int start, int before, int count){
        String newText = s.toString().trim();
        if(!newText.isEmpty()){
            InputtipsQuery inputquery = new InputtipsQuery(newText, cityText.getText().toString());
            Inputtips inputTips = new Inputtips(MainActivity.this, inputquery);
            inputTips.setInputtipsListener(this);
            inputTips.requestInputtipsAsyn();
        }
    }
    public void afterTextChanged(Editable s){  }
    public void onGetInputtips(List<Tip> list, int rCode){
        if(rCode == AMapException.CODE_AMAP_SUCCESS){
            List<String> listString = new ArrayList<>();
            for(int i = 0; i < list.size(); i++){
                listString.add(list.get(i).getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.route_inputs, listString);
            searchText.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            Toast.makeText(MainActivity.this, " " + rCode, Toast.LENGTH_LONG);
        }
    }
}
