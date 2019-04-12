package com.google.pruff;

import java.lang.ref.SoftReference;

public class User {

    String fName,lName,Phone,Dob,Email_add;

    public User(){

    }
    public User(String fname,String lName,String Phone,String Dob,String Email_add)
    {
        this.fName=fname;
        this.lName=lName;
        this.Phone=Phone;
        this.Dob=Dob;
        this.Email_add=Email_add;
    }
    public void setfName(String fName){
        this.fName=fName;
    }
    public void setlName(String lName){
        this.lName=lName;
    }
    public void setPhone(String Phone){
        this.Phone=Phone;
    }
    public void setDob(String Dob){
        this.Dob=Dob;
    }
    public void setEmail_add(String Email_add){
        this.Email_add=Email_add;
    }
    public String getfName(){return fName;}
    public String getlName(){return lName;}
    public String getPhone(){return Phone;}
    public String getDob(){return Dob;}
    public String getEmail_add(){return Email_add;}


}
