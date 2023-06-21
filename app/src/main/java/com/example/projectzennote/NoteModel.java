package com.example.projectzennote;

import io.realm.RealmObject;

public class NoteModel extends RealmObject {
    long createdTime;
    long sendingTime;
    String text;
    int moodBefore;
    int moodAfter;


    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(long sendingTime) {
        this.sendingTime = sendingTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMoodBefore() {
        return moodBefore;
    }

    public void setMoodBefore(int moodBefore) {
        this.moodBefore = moodBefore;
    }

    public int getMoodAfter() {
        return moodAfter;
    }

    public void setMoodAfter(int moodAfter) {
        this.moodAfter = moodAfter;
    }

    public NoteModel(){}

    public NoteModel(long createdTime, long sendingTime, String text, int moodBefore, int moodAfter) {
        this.createdTime = createdTime;
        this.sendingTime = sendingTime;
        this.text = text;
        this.moodBefore = moodBefore;
        this.moodAfter = moodAfter;
    }
}
