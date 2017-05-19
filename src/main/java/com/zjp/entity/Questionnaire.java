package com.zjp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by pengwan on 2017/5/19.
 */
@Entity
@Table(name = "QUESTIONNAIRE")
public class Questionnaire {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
    @Column(name = "title")
    private String title;
    @Column(name = "starter")
    private String starter;
    @Column(name = "questionList")
    private String[] questionList;
    @Column(name = "ending")
    private String ending;
    @Column(name = "state")
    private int state;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStarter() {
        return starter;
    }

    public void setStarter(String starter) {
        this.starter = starter;
    }

    public String[] getQuestionList() {
        return questionList;
    }

    public void setQuestionList(String[] questionList) {
        this.questionList = questionList;
    }

    public String getEnding() {
        return ending;
    }

    public void setEnding(String ending) {
        this.ending = ending;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
