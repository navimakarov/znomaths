package com.makarov.znomaths;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class QuizActivity extends AppCompatActivity {

    private String jsonFileString;
    private ProgressBar pbHorizontal;
    private TextView question_field;
    private Button answer1, answer2, answer3, answer4;

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        pbHorizontal = (ProgressBar) findViewById(R.id.pb);
        pbHorizontal.setProgress(33);

        question_field = (TextView) findViewById(R.id.question);
        answer1 = (Button) findViewById(R.id.answer1);
        answer2 = (Button) findViewById(R.id.answer2);
        answer3 = (Button) findViewById(R.id.answer3);
        answer4 = (Button) findViewById(R.id.answer4);

        jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "db.json");
        Gson gson = new Gson();
        Type listQuestionType = new TypeToken<List<Question>>() { }.getType();
        List<Question> questions = gson.fromJson(jsonFileString, listQuestionType);

        game = new Game(questions);
        update();


    }

    public void onClick(View view){

    }

    public void update() {
        if(game.game_over()){

        }
        else {
            String question = game.get_question();
            String right_answer = game.get_answer();
            List<String> all_answers = game.get_wrong_answers();
            all_answers.add(right_answer);
            Collections.shuffle(all_answers);
            question_field.setText(question);
            answer1.setText(all_answers.get(0));
            answer2.setText(all_answers.get(1));
            answer3.setText(all_answers.get(2));
            answer4.setText(all_answers.get(3));
        }
    }
}
