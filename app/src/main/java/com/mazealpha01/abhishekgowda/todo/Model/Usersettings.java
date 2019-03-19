package com.mazealpha01.abhishekgowda.todo.Model;

public class Usersettings {
    private String colourtheme;
    private Integer showDate;
    private Integer longpress;
    private Integer disablenotification;
    private Integer reminderlayout;
    private Integer hidecompletedtask;
    private String sleeptimenotify;
    private Integer walkthrough;
    private Integer notificationtune;


    public Usersettings() {

    }


    public Usersettings(String colourtheme, Integer showDate, Integer longpress, Integer disablenotification, Integer reminderlayout, Integer hidecompletedtask, String sleeptimenotify, Integer walkthrough, Integer notificationtune) {
        this.colourtheme = colourtheme;
        this.showDate = showDate;
        this.longpress = longpress;
        this.disablenotification = disablenotification;
        this.reminderlayout = reminderlayout;
        this.hidecompletedtask = hidecompletedtask;
        this.sleeptimenotify = sleeptimenotify;
        this.walkthrough = walkthrough;
        this.notificationtune = notificationtune;
    }

    public String getColourtheme() {
        return colourtheme;
    }

    public void setColourtheme(String colourtheme) {
        this.colourtheme = colourtheme;
    }

    public Integer getShowDate() {
        return showDate;
    }

    public void setShowDate(Integer showDate) {
        this.showDate = showDate;
    }

    public Integer getLongpress() {
        return longpress;
    }

    public void setLongpress(Integer longpress) {
        this.longpress = longpress;
    }

    public Integer getDisablenotification() {
        return disablenotification;
    }

    public void setDisablenotification(Integer disablenotification) {
        this.disablenotification = disablenotification;
    }

    public Integer getReminderlayout() {
        return reminderlayout;
    }

    public void setReminderlayout(Integer reminderlayout) {
        this.reminderlayout = reminderlayout;
    }

    public Integer getHidecompletedtask() {
        return hidecompletedtask;
    }

    public void setHidecompletedtask(Integer hidecompletedtask) {
        this.hidecompletedtask = hidecompletedtask;
    }

    public String getSleeptimenotify() {
        return sleeptimenotify;
    }

    public void setSleeptimenotify(String sleeptimenotify) {
        this.sleeptimenotify = sleeptimenotify;
    }

    public Integer getWalkthrough() {
        return walkthrough;
    }

    public void setWalkthrough(Integer walkthrough) {
        this.walkthrough = walkthrough;
    }

    public Integer getNotificationtune() {
        return notificationtune;
    }

    public void setNotificationtune(Integer notificationtune) {
        this.notificationtune = notificationtune;
    }

    @Override
    public String toString() {
        return "Usersettings{" +
                "colourtheme='" + colourtheme + '\'' +
                ", showDate=" + showDate +
                ", longpress=" + longpress +
                ", disablenotification=" + disablenotification +
                ", reminderlayout=" + reminderlayout +
                ", hidecompletedtask=" + hidecompletedtask +
                ", sleeptimenotify='" + sleeptimenotify + '\'' +
                ", walkthrough=" + walkthrough +
                ", notificationtune=" + notificationtune +
                '}';
    }
}