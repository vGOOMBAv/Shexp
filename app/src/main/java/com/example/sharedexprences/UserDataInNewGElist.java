package com.example.sharedexprences;

public class UserDataInNewGElist {
    private String Name;
    private String Surname;
    private Integer ID;
    boolean is_chosen = false;

    public UserDataInNewGElist(Integer id, String name, String surname) {
        ID = id;
        Name = name;
        Surname = surname;
    }

    public boolean getIs_chosen() {
        return is_chosen;
    }

    public void setIs_chosen(boolean is_chosen) {
        this.is_chosen = is_chosen;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }
}
