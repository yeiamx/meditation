package com.mrxia.meditation.bean;

public class Qa {
    private String id;
    private String question;
    private String answer;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }



    public Qa(String id, String question, String answer) {
        super();
        this.id = id;
        this.question = question;
        this.answer = answer;
    }
    public Qa() {

    }
}
