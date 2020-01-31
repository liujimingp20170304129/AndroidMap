package androidclass.jakeli.com.weathertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.weather.LocalDayWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;

import java.util.List;

public class MainActivity extends AppCompatActivity implements WeatherSearch.OnWeatherSearchListener{
    private static final String CITYNAME = "江门";
    private TextView city;
    private TextView forcast;
    private TextView reporttime1;
    private TextView reporttime2;
    private TextView weather;
    private TextView temperature;
    private TextView wind;
    private TextView humidity;
    private WeatherSearchQuery query;
    private WeatherSearch weatherSearch;
    private LocalWeatherLive weatherLive;
    private LocalWeatherForecast weatherForecast;
    private List<LocalDayWeatherForecast> forcastList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        searchLiveWeather();
        searchForcastWeather();
    }


    private void init(){
        forcast = (TextView)findViewById(R.id.forcast);
        city = (TextView)findViewById(R.id.city);
        city.setText(CITYNAME);
        reporttime1 = (TextView)findViewById(R.id.reporttime1);
        reporttime2 = (TextView)findViewById(R.id.reporttime2);
        weather = (TextView)findViewById(R.id.weather);
        temperature = (TextView)findViewById(R.id.temp);
        wind = (TextView)findViewById(R.id.wind);
        humidity = (TextView)findViewById(R.id.humidity);
    }

    private void searchLiveWeather(){
        query = new WeatherSearchQuery(CITYNAME, WeatherSearchQuery.WEATHER_TYPE_LIVE);
        weatherSearch = new WeatherSearch(this);
        weatherSearch.setOnWeatherSearchListener(this);
        weatherSearch.setQuery(query);
        weatherSearch.searchWeatherAsyn();
    }

    private void searchForcastWeather(){
        query = new WeatherSearchQuery(CITYNAME, WeatherSearchQuery.WEATHER_TYPE_FORECAST);
        weatherSearch = new WeatherSearch(this);
        weatherSearch.setOnWeatherSearchListener(this);
        weatherSearch.setQuery(query);
        weatherSearch.searchWeatherAsyn();
    }



    public void onWeatherLiveSearched(LocalWeatherLiveResult result, int errorCode){
        if(errorCode == AMapException.CODE_AMAP_SUCCESS){
            if(result != null && result.getLiveResult() != null){
                weatherLive = result.getLiveResult();
                reporttime1.setText(weatherLive.getReportTime() + "发布");
                weather.setText(weatherLive.getWeather());
                wind.setText(weatherLive.getWindDirection() + "风    " + weatherLive.getWindPower() + "级");
                temperature.setText(weatherLive.getTemperature() + "^");
                humidity.setText("湿度         " + weatherLive.getHumidity());
            }
        }else{
            Toast.makeText(this, "" + errorCode, Toast.LENGTH_SHORT).show();
        }

    }

    public void onWeatherForecastSearched(LocalWeatherForecastResult result, int errorCode){
        if (errorCode  == AMapException.CODE_AMAP_SUCCESS) {
            if (result!=null && result.getForecastResult()!=null
                        && result.getForecastResult().getWeatherForecast()!=null
                        && result.getForecastResult().getWeatherForecast().size()>0) {
                weatherForecast = result.getForecastResult();
                forcastList= weatherForecast.getWeatherForecast();
                fillforecast();

            }else {
                Toast.makeText(MainActivity.this, "xxxx", Toast.LENGTH_SHORT);
            }
        }else {
            Toast.makeText(MainActivity.this, "xxxx" + errorCode, Toast.LENGTH_SHORT);
        }

    }

    private void fillforecast(){
        reporttime2.setText(weatherForecast.getReportTime()+"发布");
        String forecast="";
        for (int i = 0; i < forcastList.size(); i++) {
            LocalDayWeatherForecast localdayweatherforecast=forcastList.get(i);
            String week = null;
            switch (Integer.valueOf(localdayweatherforecast.getWeek())) {
                case 1:
                    week = "周一";
                    break;
                case 2:
                    week = "周二";
                    break;
                case 3:
                    week = "周三";
                    break;
                case 4:
                    week = "周四";
                    break;
                case 5:
                    week = "周五";
                    break;
                case 6:
                    week = "周六";
                    break;
                case 7:
                    week = "周日";
                    break;
                default:
                    break;
            }
            String temp =String.format("%-3s/%3s",
                    localdayweatherforecast.getDayTemp()+"°",
                    localdayweatherforecast.getNightTemp()+"°");
            String date = localdayweatherforecast.getDate();
            forecast+=date+"  "+week+"                       "+temp+"\n\n";
        }
        forcast.setText(forecast);
    }



}

