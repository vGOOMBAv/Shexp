package com.example.sharedexprences;

public class FinancePagePerson {
    private String name;
    private String surname;
    private Integer ammount;

    public FinancePagePerson(String name, String surname, Integer ammount) {
        this.name = name;
        this.surname = surname;
        this.ammount = ammount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAmmount() {
        return ammount;
    }

    public void setAmmount(Integer ammount) {
        this.ammount = ammount;
    }
}
