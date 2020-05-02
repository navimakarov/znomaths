package com.makarov.znomaths;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

    public void contact_button_onClick(View view){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("e-mail", "makarov18042003@gmail.com");
        clipboard.setPrimaryClip(clip);
        Toast toast = Toast.makeText(this, "The e-mail was copied", Toast.LENGTH_LONG);
        toast.show();
    }
}
