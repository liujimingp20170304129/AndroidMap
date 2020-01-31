package com.example.map_430;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.weather.LocalWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;

import java.util.List;

public class MainActivity extends AppCompatActivity implements WeatherSearch.OnWeatherSearchListener{
        private static final String CTIYNAME = "北京";
        private TextView city;
    private TextView reporttime1;
    private TextView reporttime2;
    private TextView weather;
    private TextView temperature;
    private TextView wind;
    private TextView humidity;
    private WeatherSearchQuery query;
    private WeatherSearch weatherSearch;
    private LocalWeatherForecast weatherForecast;
    private LocalWeatherLive weatherLive;
    private List<LocalWeatherForecast> forecastList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        searchLiveWeather();
        searchFoecastWeather();
    }
    private void init(){
        city = (TextView)findViewById(R.id.city);
        city.setText(CTIYNAME);
        reporttime1 = (TextView)findViewById(R.id.reporttime1);
        reporttime2 = (TextView)findViewById(R.id.reporttime2);
        weather = (TextView)findViewById(R.id.weather);
        temperature = (TextView)findViewById(R.id.temp);
        wind = (TextView)findViewById(R.id.wind);
        humidity = (TextView)findViewById(R.id.humidity);
    }
    private void searchLiveWeather(){
        query = new WeatherSearchQuery(CTIYNAME,WeatherSearchQuery.WEATHER_TYPE_FORECAST);
        weatherSearch = new WeatherSearch(this);
        weatherSearch.setOnWeatherSearchListener(this);
        weatherSearch.setQuery(query);
        weatherSearch.searchWeatherAsyn();
    }
    private void searchFoecastWeather(){
        query = new WeatherSearchQuery(CTIYNAME,WeatherSearchQuery.WEATHER_TYPE_FORECAST);
        weatherSearch = new WeatherSearch(this);
        weatherSearch.setOnWeatherSearchListener(this);
        weatherSearch.setQuery(query);
        weatherSearch.searchWeatherAsyn();
    }
    public void onWeatherLiveSearched(LocalWeatherLiveResult result, int errorCode){
        if (errorCode == AMapException.CODE_AMAP_SUCCESS){
            if(result !=null && result.getLiveResult() !=null){
                weatherLive = result.getLiveResult();
                reporttime1.setText(weatherLive.getReportTime() + "发布");
                weather.setText(weatherLive.getWeather());
                wind.setText(weatherLive.getWindDirection() + "风" + );
            }
        }else {
            Toast.makeText(this, ""+ errorCode,Toast.LENGTH_LONG).show();
        }
    }
    public void onWeatherForecastSearched(LocalWeatherForecastResult result, int errorCode){

    }

}
