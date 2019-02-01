package com.haulmont.testtask.model;


import com.haulmont.testtask.data.entities.ClientEntity;

public class Client  {

    private int id;
    private String firstname;
    private String secondname;
    private String thirdname;
    private String phonenumber;

    public Client()
    {

    }

    public Client(ClientEntity clientEntity)
    {
        id = clientEntity.getId();
        firstname = clientEntity.getFirstname();
        secondname = clientEntity.getSecondname();
        thirdname = clientEntity.getThirdname();
        phonenumber = clientEntity.getPhonenumber();
    }

    public Client(String firstname, String secondname, String thirdname, String phonenumber) {
        this.firstname = firstname;
        this.secondname = secondname;
        this.thirdname = thirdname;
        this.phonenumber = phonenumber;
    }

    public Client(int id, String firstname, String secondname, String thirdname, String phonenumber) {
        this.id = id;
        this.firstname = firstname;
        this.secondname = secondname;
        this.thirdname = thirdname;
        this.phonenumber = phonenumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getThirdname() {
        return thirdname;
    }

    public void setThirdname(String thirdname) {
        this.thirdname = thirdname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Override
    public String toString() {
        return firstname + " " + secondname;
    }
}
