package com.example.demo;

import org.springframework.boot.json.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.IOException;
import java.util.*;

@RestController
public class DemoController extends RestTemplate
{
    
    /*@GetMapping("/account")
    public ResponseEntity<String> GetAccountByID(@RequestParam short id) throws IOException
    {
        ResponseEntity<String> entity = getForEntity("https://jsonplaceholder.typicode.com/todos/" + Integer.toString(id), String.class);
        String body = entity.getBody();
        //System.out.println(body + "i was edited lmao");
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String, Object> result = springParser.parseMap(body);
        return entity;
    }
    
    @PostMapping("/account")
    public ResponseEntity<String> PostAccount(@RequestParam short id) throws IOException
    {
        ResponseEntity<String> entity = getForEntity("https://jsonplaceholder.typicode.com/todos/" + Integer.toString(id), String.class);
        String body = entity.getBody();
        //System.out.println(body + "i was edited lmao");
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String, Object> result = springParser.parseMap(body);
        return entity;
    }
    
    @UpdateMapping("/account")
    public ResponseEntity<String> UpdateAccount(@RequestParam short id) throws IOException
    {
        ResponseEntity<String> entity = getForEntity("https://jsonplaceholder.typicode.com/todos/" + Integer.toString(id), String.class);
        String body = entity.getBody();
        //System.out.println(body + "i was edited lmao");
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String, Object> result = springParser.parseMap(body);
        return entity;
    }
    
    @DeleteMapping("/account")
    public ResponseEntity<String> DeleteAccount(@RequestParam short id) throws IOException
    {
        ResponseEntity<String> entity = getForEntity("https://jsonplaceholder.typicode.com/todos/" + Integer.toString(id), String.class);
        String body = entity.getBody();
        //System.out.println(body + "i was edited lmao");
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String, Object> result = springParser.parseMap(body);
        return entity;
    }*/
	
	@GetMapping("/alluser")
    public ResponseEntity<String> GETalluser() throws IOException
    {
        return getForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w?sheet=user-account", String.class);
        //String body = entity.getBody();
        //System.out.println(body + "i was edited lmao");
        //ObjectMapper objectMapper = new ObjectMapper();
    }
	
	@GetMapping("/user")
    public ResponseEntity<String> GETuser(@RequestParam String email) throws IOException
    {
        return getForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/search?email=" + email + "&sheet=user-account", String.class);
    }
	
	@PostMapping("/createuser")
	@ResponseBody
    public ResponseEntity<String> POSTcreateuser(@RequestBody DemoJSONClass account) throws IOException
    {
		System.out.println("Email is " + account.email + "\nDOB is " + account.dob + "\nPassword is " + account.password);
		//DemoJSONClass[] searchResults = getForObject("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/search?email=" + account.email + "&sheet=user-account", DemoJSONClass[].class);
		ResponseEntity<String> query = getForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/search?email=" + account.email + "&sheet=user-account", String.class);
        if (query.getStatusCode() != HttpStatus.OK)
        {
        	System.out.println(query.getStatusCode().toString() + ": " + query.getBody());
        	return query;
        }
        DemoJSONClass[] searchResults = new ObjectMapper().readValue(query.getBody(), DemoJSONClass[].class);
        if (searchResults.length > 0)
        {
        	//Account already exists lmao
        	return new ResponseEntity<String>("Email is already tied to an existing account.", HttpStatus.UNAUTHORIZED);
        }
        HttpEntity<String> credentials = new HttpEntity<String>(
        	"{"
        		+ "\"data\" :"
        		+ "["
        			+ "{"
        				+ "\"email\": \"" + account.email + "\","
        				+ "\"password\": \"" + account.password + "\","
                		+ "\"role\": \"Candidate\""
        			+ "}"
        		+ "]"
        + "}");
        System.out.println(credentials.getBody());
        //TODO fix this server error mess lol
		ResponseEntity<String> boyo = postForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w?sheet=user-account", credentials, String.class);
    	System.out.println(boyo.getStatusCode().toString() + ": " + boyo.getBody());
    	return new ResponseEntity<String>("Stick around lol.", HttpStatus.UNAUTHORIZED);
		//DONE Check for existing account, then reject it if there's an account. Otherwise send it to the database.
    }
	
	@PatchMapping("/edituser")
	@ResponseBody
    public ResponseEntity<String> PATCHedituser(@RequestBody DemoJSONClass account) throws IOException
    {
		System.out.println("Email is " + account.email + "\nDOB is " + account.dob + "\nPassword is " + account.password);
		//DemoJSONClass[] searchResults = getForObject("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/search?email=" + account.email + "&sheet=user-account", DemoJSONClass[].class);
		ResponseEntity<String> query = getForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/search?email=" + account.email + "&sheet=user-account", String.class);
        if (query.getStatusCode() != HttpStatus.OK)
        {
        	System.out.println(query.getStatusCode().toString() + ": " + query.getBody());
        	return query;
        }
        DemoJSONClass[] searchResults = new ObjectMapper().readValue(query.getBody(), DemoJSONClass[].class);
        if (searchResults.length > 0)
        {
        	//Account already exists lmao
        	return new ResponseEntity<String>("Email is already tied to an existing account.", HttpStatus.UNAUTHORIZED);
        }
        HttpEntity<String> credentials = new HttpEntity<String>(
        	"{"
        		+ "\"data\" :"
        		+ "["
        			+ "{"
        				+ "\"email\": \"" + account.email + "\","
        				+ "\"password\": \"" + account.password + "\","
                		+ "\"role\": \"Candidate\""
        			+ "}"
        		+ "]"
        + "}");
        System.out.println(credentials.getBody());
        //TODO fix this server error mess lol
		ResponseEntity<String> boyo = postForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w?sheet=user-account", credentials, String.class);
    	System.out.println(boyo.getStatusCode().toString() + ": " + boyo.getBody());
    	return new ResponseEntity<String>("Stick around lol.", HttpStatus.UNAUTHORIZED);
		//DONE Check for existing account, then reject it if there's an account. Otherwise send it to the database.
    }
}