package com.example.sharedexprences;

public class EventDataInGElist {
    private Integer eventID;
    private String name;
    private Integer sum;

    public EventDataInGElist(Integer eventID, String name, Integer sum) {
        this.eventID = eventID;
        this.name = name;
        this.sum = sum;
    }

    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}
