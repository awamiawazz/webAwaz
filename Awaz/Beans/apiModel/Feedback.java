package com.Beans.apiModel;

import com.Beans.Report;
import com.Beans.User;

/**
 * Created by Asad on 4/12/2019.
 */
public class Feedback {
    private double ranking;
    int user_id,r_id,feedback_id;
    private String comment;
    private User user;
    private Report report;

    public Feedback() {
    }

    public Feedback(double ranking, int user_id, int r_id, String comment) {
        this.ranking = ranking;
        this.user_id = user_id;
        this.r_id = r_id;
        this.comment = comment;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public double getRanking() {
        return ranking;
    }

    public void setRanking(double ranking) {
        this.ranking = ranking;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(int feedback_id) {
        this.feedback_id = feedback_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
