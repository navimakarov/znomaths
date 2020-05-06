package com.makarov.znomaths;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        if(height < 1300) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);

        if(height <= 320) {
            setContentView(R.layout.activity_main_height320);
        }
        else if(height <= 800){
            setContentView(R.layout.activity_main_height800);
        }
        else {
            setContentView(R.layout.activity_main);
        }
    }

    public void button_exit_onClick(View view){
        finishAffinity();
    }

    public void button_about_onClick(View view){
        Intent intent_about = new Intent(this, About.class);
        startActivity(intent_about);
    }

    public void button_start_onClick(View view){
        Intent intent_quiz = new Intent(this, QuizActivity.class);
        startActivity(intent_quiz);
    }
}
