package com.example.administrator.gametest;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements View.OnClickListener {
        Button button;
        ImageButton imageButton;
        ToggleButton toggleButton;
        TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)this.findViewById(R.id.text01);
        button = (Button)this.findViewById(R.id.BTN);
        imageButton = (ImageButton)this.findViewById(R.id.imageBTN);
        toggleButton = (ToggleButton)this.findViewById(R.id.openoffBTN);
        button.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        toggleButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v ==button ){
            textView.setText("你点击了普通按钮");
        }
        else if(v == imageButton){
            textView.setText("你点击了图片按钮");
        }
        else if(v == toggleButton){
            textView.setText("你点击了开关按钮");
        }
    }
}
