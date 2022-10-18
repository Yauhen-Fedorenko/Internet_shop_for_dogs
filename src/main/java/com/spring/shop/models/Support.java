package com.spring.shop.models;


import javax.persistence.*;

@Entity
@Table(name = "supports")
public class Support {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.EAGER)
    private MyUser user;

    private String question, answer;

    public Support() {}

    public Support(String question, String answer, MyUser myUser) {
        this.question = question;
        this.answer = answer;
        this.user = myUser;
    }

    public MyUser getUser() {
        return user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public String toString(){
        return id+". " +question;
    }
}
