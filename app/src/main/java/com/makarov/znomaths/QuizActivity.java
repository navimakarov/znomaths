package com.makarov.znomaths;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.animation.ObjectAnimator;
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

    private String jsonFileString;
    private ProgressBar pb;
    private TextView question_field, rightAnswersCount;
    private Button answer1, answer2, answer3, answer4;
    private ImageView heart1, heart2, heart3;

    private boolean questionAnswered;

    private String rightAnswer;

    private Game game;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

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
                delay = 1000;
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
                //player.decrease_triesCount();
                delay = 5000;

                switch(player.get_triesCount()){
                    case 2:
                        heart3.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        heart2.setVisibility(View.INVISIBLE);
                        break;
                    case 0:
                        heart1.setVisibility(View.INVISIBLE);
                        break;
                }
            }
            //start_animation(delay);
            /*
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    update_game();
                }
            }, delay + 300);
             */
            update_game();
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
}
