package com.makarov.znomaths;

import java.util.List;

public class Question {
    private String question, rightAnswer;
    private List<String> wrongAnswers;

    public String get_question() {
        return question;
    }

    public String get_rightAnswer() {
        return rightAnswer;
    }

    public List<String> get_wrongAnswers() {
        return wrongAnswers;
    }

}
