package com.makarov.znomaths;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Question> questions;
    private Player player;
    private int index;
    public Game(List<Question> questions, Player player) {
        this.questions = questions;
        this.player = player;
    }

    public boolean game_over(){
        if(player.get_triesCount() == 0)
            return true;
        return false;
    }

    public String get_question() {
        index = (int) ( Math.random() * questions.size());
        return questions.get(index).get_question();
    }

    public List<String> get_answers() {
        List<String> answers = new ArrayList<String>(4);
        answers = questions.get(index).get_wrongAnswers();
        answers.add(questions.get(index).get_rightAnswer());
        return answers;
    }

    public String get_answer() {
        return questions.get(index).get_rightAnswer();
    }

}
