package com.kwt.legalbuddy;

/**
 * Created by MB on 8/8/2015.
 */
public class Questions {

    private String quest;
    private String ans;

    public Questions(String quest) {
        this.quest = quest;
        ans="hello";
    }
    public String getQuest() {
        return quest;
    }
    public void setQuest(String quest) {
        this.quest = quest;
    }
    public String getAns() {
        return ans;
    }
    public void setAns(String ans) {
        this.ans = ans;
    }

}
