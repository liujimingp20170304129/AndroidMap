package com.example.user_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    OkHttpClient client;
    TextView txtString;
    Button synchronousGet;
    private final String url = "http://139.159.230.230:8000/api/custinfo/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new OkHttpClient();
        synchronousGet = (Button)findViewById(R.id.synchronousGet);
        synchronousGet.setOnClickListener(this);
        txtString = (TextView)findViewById(R.id.txtString);
    }
    private void parseJSONWithGSON(String jsonData) {
        //使用轻量级的Gson解析得到的json
        Gson gson = new Gson();
        List<App> appList = gson.fromJson(jsonData, new TypeToken<List<App>>() {}.getType());
        for (App app : appList) {
            //控制台输出结果，便于查看
            Log.d("MainActivity", "id" + app.getId());
            Log.d("MainActivity", "content" + app.getContent());
            Log.d("MainActivity", "name" + app.getName());
            Log.d("MainActivity", "create_date" + app.getCreate_date());
            Log.d("MainActivity", "cert_num" + app.getCert_num());
            Log.d("MainActivity", "sex" + app.getSex());
            Log.d("MainActivity", "contact" + app.getContact());
            Log.d("MainActivity", "agency" + app.getAgency());
            Log.d("MainActivity", "agency_fee" + app.getAgency_fee());
            Log.d("MainActivity", "charges" + app.getCharges());
            Log.d("MainActivity", "company_fee" + app.getCompany_fee());
            Log.d("MainActivity", "charge_amount" + app.getCharge_amount());
            Log.d("MainActivity", "success_date" + app.getSuccess_date());
            Log.d("MainActivity", "connect_detail" + app.getConnct_detail());
            Log.d("MainActivity", "user" + app.getUser());

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.synchronousGet:
                OKHttpHandler okHttpHandler = new OKHttpHandler(txtString, client);
                okHttpHandler.execute(url);
                break;
        }
    }
}
