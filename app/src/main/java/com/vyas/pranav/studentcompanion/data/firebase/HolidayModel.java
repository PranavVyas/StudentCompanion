package com.vyas.pranav.studentcompanion.data.firebase;

public class HolidayModel {
    String date;
    String name;

    public HolidayModel() {
    }

    public HolidayModel(String date, String name) {
        this.date = date;
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
