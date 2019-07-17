package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DemoJSONClass {
	public class Customer {
	    private String email;
	    private String password;
	    private String role;
	    private String first_name;
	    private String last_name;
	    private String dob;
	    private String contact_no;
	    private String nationality;
	    private String school;
	    private String highest_qualification;
	    private String gender;
	    private String goal;
	    private String resources;
	    private String action_plan;
	    
	    public String Get_email()
	    {
	    	return email;
	    }
	    public void Set_email(String email)
	    {
	    	this.email = email;
	    }
	    
	    public String Get_password()
	    {
	    	return password;
	    }
	    public void Set_password(String password)
	    {
	    	this.password = password;
	    }
	    
	    public String Get_role()
	    {
	    	return role;
	    }
	    public void Set_role(String role)
	    {
	    	this.role = role;
	    }
	    
	    public String Get_first_name()
	    {
	    	return first_name;
	    }
	    public void Set_first_name(String first_name)
	    {
	    	this.first_name = first_name;
	    }
	    
	    public String Get_last_name()
	    {
	    	return last_name;
	    }
	    public void Set_last_name(String last_name)
	    {
	    	this.last_name = last_name;
	    }
	    
	    public String Get_dob()
	    {
	    	return dob;
	    }
	    public void Set_dob(String dob)
	    {
	    	this.dob = dob;
	    }
	    
	    public String Get_contact_no()
	    {
	    	return contact_no;
	    }
	    public void Set_contact_no(String contact_no)
	    {
	    	this.contact_no = contact_no;
	    }
	    
	    public String Get_nationality()
	    {
	    	return nationality;
	    }
	    public void Set_nationality(String nationality)
	    {
	    	this.nationality = nationality;
	    }
	    
	    public String Get_school()
	    {
	    	return school;
	    }
	    public void Set_school(String school)
	    {
	    	this.school = school;
	    }
	    
	    public String Get_highest_qualification()
	    {
	    	return highest_qualification;
	    }
	    public void Set_highest_qualification(String highest_qualification)
	    {
	    	this.highest_qualification = highest_qualification;
	    }
	    
	    public String Get_gender()
	    {
	    	return gender;
	    }
	    public void Set_gender(String gender)
	    {
	    	this.gender = gender;
	    }
	    
	    public String Get_goal()
	    {
	    	return goal;
	    }
	    public void Set_goal(String goal)
	    {
	    	this.goal = goal;
	    }
	    
	    public String Get_resources()
	    {
	    	return resources;
	    }
	    public void Set_resources(String resources)
	    {
	    	this.resources = resources;
	    }
	    
	    public String Get_action_plan()
	    {
	    	return action_plan;
	    }
	    public void Set_action_plan(String action_plan)
	    {
	    	this.action_plan = action_plan;
	    }
	}
}
