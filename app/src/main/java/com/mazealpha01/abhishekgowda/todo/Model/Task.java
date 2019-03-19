package com.mazealpha01.abhishekgowda.todo.Model;

public class Task {
    private String tasktodo;
    private String time;
    private String date;
    private String sound;
    private String complete;
    private String tag;
    private String note;
    private Integer ID;
    private Integer CalendarMark;
    private Integer alarmID;


    public Task() {

    }

    public Task(String tasktodo, String time, String date, String sound, String complete, String tag, String note, Integer ID, Integer notificationID, Integer alarmID) {
        this.tasktodo = tasktodo;
        this.time = time;
        this.date = date;
        this.sound = sound;
        this.complete = complete;
        this.tag = tag;
        this.note = note;
        this.ID = ID;
        this.CalendarMark = notificationID;
        this.alarmID = alarmID;
    }

    public String getTasktodo() {
        return tasktodo;
    }

    public void setTasktodo(String tasktodo) {
        this.tasktodo = tasktodo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getCalendarMark() {
        return CalendarMark;
    }

    public void setCalendarMark(Integer calendarMark) {
        this.CalendarMark = calendarMark;
    }

    public Integer getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(Integer alarmID) {
        this.alarmID = alarmID;
    }

    @Override
    public String toString() {
        return "Task{" +
                "tasktodo='" + tasktodo + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", sound='" + sound + '\'' +
                ", complete='" + complete + '\'' +
                ", tag='" + tag + '\'' +
                ", note='" + note + '\'' +
                ", ID=" + ID +
                ", CalendarMark=" + CalendarMark +
                ", alarmID=" + alarmID +
                '}';
    }
}
