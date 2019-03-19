package com.mazealpha01.abhishekgowda.todo.Model;

public class Item {
    private String item;
    private String complete;
    private String tag;
    private Integer TaskID;
    private Integer ID;


    public Item() {

    }

    public Item(String item, String complete, String tag, Integer taskID, Integer ID) {
        this.item = item;
        this.complete = complete;
        this.tag = tag;
        TaskID = taskID;
        this.ID = ID;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
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

    public Integer getTaskID() {
        return TaskID;
    }

    public void setTaskID(Integer taskID) {
        TaskID = taskID;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Item{" +
                "item='" + item + '\'' +
                ", complete='" + complete + '\'' +
                ", tag='" + tag + '\'' +
                ", TaskID=" + TaskID +
                ", ID=" + ID +
                '}';
    }
}
