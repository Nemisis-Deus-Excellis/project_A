package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DemoJSONClass {
	public class Customer {
	    @JsonProperty("userId")
	    private int userId;
	    @JsonProperty("id")
	    private int id;
	    @JsonProperty("title")
	    private String title;
	    @JsonProperty("body")
	    private String body;
	}
}
