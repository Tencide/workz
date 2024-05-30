package com.example.workoutgen;

public class Member {
    private String email;
    private String firstName;
    private String lastName;

    public Member (String firstName, String email,String lastName){
     this.firstName = email;
     this.lastName = lastName;
     this.email = email;


    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
     return   firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
