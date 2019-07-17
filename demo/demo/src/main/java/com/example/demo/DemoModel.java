package com.example.demo;

import java.util.Map;

public class DemoModel {

    private String dob;
    private String password;
    private String email;
    private String role;
    private String firstname;
    private String contactno;
    private String lastname;
    private String school;
    private String gender;
    private String goal;
    private String actionplan;
    private String nationality;
    private String highestqualification;
    private String resources;

    public DemoModel() {

    }

    public DemoModel(String dob, String password, String email, String role, String firstname, String contactno, String lastname, String nationality, String school, String highestqualification, String gender, String goal, String resources, String actionplan) {
        this.dob = dob;
        this.password = password;
        this.email = email;
        this.role = role;
        this.firstname = firstname;
        this.lastname =lastname;
        this.contactno = contactno;
        this.school = school;
        this.nationality = nationality;
        this.gender =gender;
        this.goal =goal;
        this.actionplan =actionplan;
        this.highestqualification= highestqualification;
        this.resources = resources;
    }

    public String getdob() {
        return dob;
    }

    public void setdob(String dob) {
        this.dob = dob;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }
    
    public String getfirstname() {
        return firstname;
    }

    public void setfirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getlastname() {
        return lastname;
    }

    public void setlastname(String lastname) {
        this.lastname = lastname;
    }
    public String getnationality() {
        return nationality;
    }

    public void setnationality(String nationality) {
        this.nationality = nationality;
    }
    public String gethighestqualification() {
        return highestqualification;
    }

    public void sethighestqualification(String highestqualification) {
        this.highestqualification = highestqualification;
    }
    public String getgender() {
        return gender;
    }

    public void setgender(String gender) {
        this.gender = gender;
    }
    public String getcontactno() {
        return contactno;
    }

    public void setcontactno(String contactno) {
        this.contactno = contactno;
    }
    public String getschool() {
        return school;
    }

    public void setschool(String school) {
        this.school = school;
    }
    public String getresources() {
        return resources;
    }

    public void setresources(String resources) {
        this.resources = resources;
    }
    public String getrole() {
        return role;
    }

    public void setrole(String role) {
        this.role = role;
    }
    public String getgoal() {
        return goal;
    }

    public void setgoal(String goal) {
        this.goal = goal;
    }
    public String getactionplan() {
        return actionplan;
    }

    public void setactionplan(String actionplan) {
        this.actionplan = actionplan;
    }

	public Map<String, ?> getId() {
		return null;
	}
}