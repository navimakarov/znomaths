package com.makarov.znomaths;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.util.Log;
import java.lang.reflect.Type;
import java.util.List;
import android.os.Bundle;

public class QuizActivity extends AppCompatActivity {

    private String jsonFileString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "db.json");
        Gson gson = new Gson();
        Type listQuestionType = new TypeToken<List<Question>>() { }.getType();
        List<Question> questions = gson.fromJson(jsonFileString, listQuestionType);

    }
}
