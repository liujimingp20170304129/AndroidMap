package com.example.administrator.sample_0_1;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends Activity {
    Animation myAnimation;                             //动画的引用
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myAnimation = AnimationUtils.loadAnimation(this,R.anim.myanim);     //加载动画
        imageView = (ImageView) findViewById(R.id.myimageview);
        imageView.startAnimation(myAnimation);
    }
}
