package com.makarov.znomaths;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class QuizActivity extends AppCompatActivity {

    private final String RECORD_FILENAME = "record.txt";
    private String jsonFileString;
    private ProgressBar pb;
    private TextView question_field, rightAnswersCount, record_field;
    private Button answer1, answer2, answer3, answer4;
    private ImageView heart1, heart2, heart3;

    private boolean questionAnswered;
    private String rightAnswer;

    private Game game;
    private Player player;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        if(height < 1300) {
            getSupportActionBar().hide();
        }

        if(height <= 320){
            setContentView(R.layout.activity_quiz_height320);
        }
        else if(height <= 800){
            setContentView(R.layout.activity_quiz_height800);
        }
        else{
            setContentView(R.layout.activity_quiz);
        }

        adView = findViewById(R.id.adView);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                System.out.println("onAdLoaded() CALLBACK");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                System.out.println("onAdFailedToLoad() CALLBACK code: " + errorCode);
            }
        });

        pb = (ProgressBar) findViewById(R.id.pb);

        question_field = (TextView) findViewById(R.id.question);
        question_field.setMovementMethod(new ScrollingMovementMethod());

        answer1 = (Button) findViewById(R.id.answer1);
        answer2 = (Button) findViewById(R.id.answer2);
        answer3 = (Button) findViewById(R.id.answer3);
        answer4 = (Button) findViewById(R.id.answer4);

        heart1 = (ImageView) findViewById(R.id.heart1);
        heart2 = (ImageView) findViewById(R.id.heart2);
        heart3 = (ImageView) findViewById(R.id.heart3);

        rightAnswersCount = (TextView) findViewById(R.id.rightAnswers);
        record_field = (TextView) findViewById(R.id.record_field);
        String record = "";
        try {
            record = readRecord();
        }
        catch (Exception e){
            System.out.println(e);
        }
        if(!record.equals("")) {
            record_field.setText(record);
        }

        jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "db.json");
        Gson gson = new Gson();
        Type listQuestionType = new TypeToken<List<Question>>() { }.getType();
        List<Question> questions = gson.fromJson(jsonFileString, listQuestionType);

        player = new Player();

        game = new Game(questions, player);
        update_game();

    }

    public void onClick(View view){
        Button thisButton = (Button) view;
        String thisAnswer = thisButton.getText().toString();

        if(!questionAnswered) {
            questionAnswered = true;
            int delay = 0;
            if (thisAnswer.equals(rightAnswer)) {
                view.setBackgroundResource(R.drawable.rounded_button_green);
                player.increase_rightAnswersCount();
                rightAnswersCount.setText(String.valueOf(player.get_rightAnswersCount()));
                delay = 4000;
            } else {
                view.setBackgroundResource(R.drawable.rounded_button_red);
                if(answer1.getText().toString().equals(rightAnswer)){
                    answer1.setBackgroundResource(R.drawable.rounded_button_green);
                }
                else if(answer2.getText().toString().equals(rightAnswer)){
                    answer2.setBackgroundResource(R.drawable.rounded_button_green);
                }
                else if(answer3.getText().toString().equals(rightAnswer)){
                    answer3.setBackgroundResource(R.drawable.rounded_button_green);
                }
                else if(answer4.getText().toString().equals(rightAnswer)){
                    answer4.setBackgroundResource(R.drawable.rounded_button_green);
                }
                player.decrease_triesCount();
                delay = 5000;

                switch(player.get_triesCount()){
                    case 2:
                        heart3.setImageResource(R.drawable.heart_broken);
                        break;
                    case 1:
                        heart2.setImageResource(R.drawable.heart_broken);
                        break;
                    case 0:
                        heart1.setImageResource(R.drawable.heart_broken);
                        break;
                }
            }
            start_animation(delay);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    update_game();
                }
            }, delay + 300);
        }
    }

    public void start_animation(int delay) {
        pb.setVisibility(View.VISIBLE);
        ObjectAnimator pbAnimator = ObjectAnimator.ofInt(pb, "progress", 0, 100);
        pbAnimator.setDuration(delay);
        pbAnimator.start();
    }

    public void update_game() {
        if(game.game_over()){
            String current_record = readRecord();
            if(current_record.equals("")){
                writeRecord(String.valueOf(player.get_rightAnswersCount()));
            }
            else{
                try {
                    int record = Integer.parseInt(current_record);
                    if(player.get_rightAnswersCount() > record){
                        record = player.get_rightAnswersCount();
                        writeRecord(String.valueOf(record));
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    writeRecord(String.valueOf(player.get_rightAnswersCount()));
                }
            }
            finish();
        }
        else {
            questionAnswered = false;
            pb.setVisibility(View.INVISIBLE);

            answer1.setBackgroundResource(R.drawable.rounded_button_yellow);
            answer2.setBackgroundResource(R.drawable.rounded_button_yellow);
            answer3.setBackgroundResource(R.drawable.rounded_button_yellow);
            answer4.setBackgroundResource(R.drawable.rounded_button_yellow);

            String question = game.get_question();
            rightAnswer = game.get_answer();
            List <String> all_answers = game.get_answers();

            List<Integer> indexes = Arrays.asList(0, 1, 2, 3);
            Collections.shuffle(indexes);

            question_field.setText(question);
            answer1.setText(all_answers.get(indexes.get(0)));
            answer2.setText(all_answers.get(indexes.get(1)));
            answer3.setText(all_answers.get(indexes.get(2)));
            answer4.setText(all_answers.get(indexes.get(3)));

        }
    }

    public String readRecord() {
        String file_content = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(RECORD_FILENAME)));
            file_content = br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file_content;
    }

    public void writeRecord(String record) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(RECORD_FILENAME, MODE_PRIVATE)));
            bw.write(record);
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}