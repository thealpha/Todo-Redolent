package com.mazealpha01.abhishekgowda.todo.Model;

public class CalenderMark {

    private  String Date;
    private  String Month;
    private  String Year;
    private  Integer taskid;
    public CalenderMark() {

    }
    public CalenderMark(String date, String month, String year, Integer taskid) {
        Date = date;
        Month = month;
        Year = year;
        this.taskid = taskid;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    @Override
    public String toString() {
        return "CalenderMark{" +
                "Date='" + Date + '\'' +
                ", Month='" + Month + '\'' +
                ", Year='" + Year + '\'' +
                ", taskid=" + taskid +
                '}';
    }
}
