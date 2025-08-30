package com.example.mathgame;

public class QuestionClass {
    private String question;
    private int answer;

    public QuestionClass(String questionIn, int answerIn) {
        this.question = questionIn;
        this.answer = answerIn;
    }

    public String getQuestion() {
        return question;
    }

    public int getAnswer() {
        return answer;
    }

    public void setQuestion(String questionIn) {
        this.question = questionIn;
    }

    public void setAnswer(int answerIn) {
        this.answer = answerIn;
    }
}
