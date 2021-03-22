package com.example.firetorecycle;

public class Model {
    String course,name,email,purl;

    public Model()
    {

    }

    public Model(String course, String name, String email, String purl)
    {
        this.course = course;
        this.name = name;
        this.email = email;
        this.purl = purl;
    }

    public String getCourse()
    {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
