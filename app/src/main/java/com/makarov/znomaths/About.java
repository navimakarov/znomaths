package com.makarov.znomaths;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void menu_button_onCLick(View view){
        Intent intent_menu = new Intent(this, MainActivity.class);
        startActivity(intent_menu);
    }
}
