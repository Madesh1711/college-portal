package com.example.springsecuritydemo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Marks {
    @Id
    String rollno;
    Integer tamil;
    Integer english;
    Integer organic;
    Integer inorganic;
    Integer physical;
    Integer total;
    float average;

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public Integer getTamil() {
        return tamil;
    }

    public void setTamil(Integer tamil) {
        this.tamil = tamil;
    }

    public Integer getEnglish() {
        return english;
    }

    public void setEnglish(Integer english) {
        this.english = english;
    }

    public Integer getOrganic() {
        return organic;
    }

    public void setOrganic(Integer organic) {
        this.organic = organic;
    }

    public Integer getInorganic() {
        return inorganic;
    }

    public void setInorganic(Integer inorganic) {
        this.inorganic = inorganic;
    }

    public Integer getPhysical() {
        return physical;
    }

    public void setPhysical(Integer physical) {
        this.physical = physical;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }
}
