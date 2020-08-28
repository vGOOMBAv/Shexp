package com.example.sharedexprences;

class GEdataInUserPage {
    private Integer geID;
    private String geName;

    public GEdataInUserPage(Integer geID, String geName) {
        this.geID = geID;
        this.geName = geName;
    }

    public Integer getGeID() {
        return geID;
    }

    public void setGeID(Integer geID) {
        this.geID = geID;
    }

    public String getGeName() {
        return geName;
    }

    public void setGeName(String geName) {
        this.geName = geName;
    }
}
