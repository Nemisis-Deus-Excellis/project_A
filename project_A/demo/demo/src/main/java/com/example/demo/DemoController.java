package com.example.demo;

import org.springframework.boot.json.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	@PostMapping("/createuser")
	@ResponseBody
    public ResponseEntity<String> POSTcreateuser(@RequestBody DemoJSONClass account) throws IOException
    //public ResponseEntity<String> POSTcreateuser(DemoJSONClass account) throws IOException
    {
		System.out.println("Email is " + account.email + "\nDOB is " + account.dob + "\nPassword is " + account.password);
        //return getForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w?sheet=user-account", String.class);
		return new ResponseEntity<String>("good job my g", HttpStatus.OK);
		//TODO Check for existing account, then reject it if there's an account. Otherwise send it to the database.
    }
}