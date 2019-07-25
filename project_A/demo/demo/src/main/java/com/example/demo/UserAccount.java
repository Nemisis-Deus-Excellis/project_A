package com.example.demo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserAccount
{
	public String email;
    public String password;
    public String role;
    public String first_name;
    public String last_name;
    public String dob;
    public String contact_no;
    public String nationality;
    public String school;
    public String highest_qualification;
    public String gender;
    public String goal;
    public String resources;
    public String action_plan;
}
