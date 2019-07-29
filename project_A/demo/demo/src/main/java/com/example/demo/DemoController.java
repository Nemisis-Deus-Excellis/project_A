package com.example.demo;

import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.example.demo.encryptor.PasswordEncoderGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

@RestController
public class DemoController extends RestTemplate
{
	@GetMapping("/alluser")
    public ResponseEntity<String> GETalluser() throws IOException
    {
		//Get information.
		ResponseEntity<String> query = getForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w?sheet=user-account", String.class);
		//Anything other than an OK status code is echoed to frontend. Something's probably wrong, we'll stop here.
        if (query.getStatusCode() != HttpStatus.OK)
        {
        	System.out.println(query.getStatusCode().toString() + ": " + query.getBody());
        	return query;
        }
        //If nothing else, map them into an array of UserAccount and strip them of their passwords.
        UserAccount[] searchResults = new ObjectMapper().readValue(query.getBody(), UserAccount[].class);
        for (UserAccount account : searchResults)
        {
        	//Null fields are ignored when serialising into JSON.
        	account.password = null;
        	//The empty placeholder is replaced with an actually empty string.
    		if ("this is supposed to be empty".equals(account.first_name))
    			account.first_name = "";
    		if ("this is supposed to be empty".equals(account.last_name))
    			account.last_name = "";
    		if ("this is supposed to be empty".equals(account.dob))
    			account.dob = "";
    		if ("this is supposed to be empty".equals(account.contact_no))
    			account.contact_no = "";
    		if ("this is supposed to be empty".equals(account.nationality))
    			account.nationality = "";
    		if ("this is supposed to be empty".equals(account.school))
    			account.school = "";
    		if ("this is supposed to be empty".equals(account.highest_qualification))
    			account.highest_qualification = "";
    		if ("this is supposed to be empty".equals(account.gender))
    			account.gender = "";
    		if ("this is supposed to be empty".equals(account.goal))
    			account.goal = "";
    		if ("this is supposed to be empty".equals(account.resources))
    			account.resources = "";
    		if ("this is supposed to be empty".equals(account.action_plan))
    			account.action_plan = "";
        }
        //Serialise and return.
        String toFront = new ObjectMapper().writeValueAsString(searchResults);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<String>(toFront, header, HttpStatus.OK);
    }
	
	@GetMapping("/user")
    public ResponseEntity<String> GETuser(@RequestParam String email) throws IOException
    {
		//Get information.
		ResponseEntity<String> query = getForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/search?email=" + email + "&sheet=user-account", String.class);
		//Anything other than an OK status code is echoed to frontend. Something's probably wrong, we'll stop here.
        if (query.getStatusCode() != HttpStatus.OK)
        {
        	System.out.println(query.getStatusCode().toString() + ": " + query.getBody());
        	return query;
        }
        //Map the result into a UserAccount and strip off its password.
        UserAccount[] searchResults = new ObjectMapper().readValue(query.getBody(), UserAccount[].class);
        //If there was no match for the queried email, stop here and return a null array.
        if (searchResults.length != 1)
            return new ResponseEntity<String>("[]", HttpStatus.OK);
        searchResults[0].password = null;
    	//The empty placeholder is replaced with an actually empty string.
		if ("this is supposed to be empty".equals(searchResults[0].first_name))
			searchResults[0].first_name = "";
		if ("this is supposed to be empty".equals(searchResults[0].last_name))
			searchResults[0].last_name = "";
		if ("this is supposed to be empty".equals(searchResults[0].dob))
			searchResults[0].dob = "";
		if ("this is supposed to be empty".equals(searchResults[0].contact_no))
			searchResults[0].contact_no = "";
		if ("this is supposed to be empty".equals(searchResults[0].nationality))
			searchResults[0].nationality = "";
		if ("this is supposed to be empty".equals(searchResults[0].school))
			searchResults[0].school = "";
		if ("this is supposed to be empty".equals(searchResults[0].highest_qualification))
			searchResults[0].highest_qualification = "";
		if ("this is supposed to be empty".equals(searchResults[0].gender))
			searchResults[0].gender = "";
		if ("this is supposed to be empty".equals(searchResults[0].goal))
			searchResults[0].goal = "";
		if ("this is supposed to be empty".equals(searchResults[0].resources))
			searchResults[0].resources = "";
		if ("this is supposed to be empty".equals(searchResults[0].action_plan))
			searchResults[0].action_plan = "";
        //Serialise and return.
        String toFront = new ObjectMapper().writeValueAsString(searchResults);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<String>(toFront, header, HttpStatus.OK);
    }
	
	@PostMapping("/createuser")
	@ResponseBody
    public ResponseEntity<String> POSTcreateuser(@RequestBody UserAccount account) throws IOException
    {
		//URLs can't have white spaces. Replace them with their ASCII identifier.
		String email = account.email;
		email.replace(" ", "%20");
		//Search for the email address used to register this new account.
		ResponseEntity<String> query = getForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/search?email=" + email + "&sheet=user-account", String.class);
		//Anything other than an OK status code is echoed to frontend. Something's probably wrong, we'll stop here.
        if (query.getStatusCode() != HttpStatus.OK)
        {
        	System.out.println(query.getStatusCode().toString() + ": " + query.getBody());
        	return query;
        }
        //If nothing else, map them into an array of UserAccount. This array should be empty.
        UserAccount[] searchResults = new ObjectMapper().readValue(query.getBody(), UserAccount[].class);
        //If the array has an account, it means the new email was already used to register for an account in the database.
        if (searchResults.length > 0)
        {
        	//All accounts must have a unique email. Duplicate accounts with the same email can't be set up.
        	return new ResponseEntity<String>("Email is already tied to an existing account.", HttpStatus.UNAUTHORIZED);
        }
        //Encrypt the password
        account.password = new PasswordEncoderGenerator().encryptPassword(account.password);
        //The account's role is always Candidate.
        account.role = "Candidate";
        //Every other field has a placeholder newline.
        account.first_name = "this is supposed to be empty";
        account.last_name = "this is supposed to be empty";
        account.dob = "this is supposed to be empty";
        account.contact_no = "this is supposed to be empty";
        account.nationality = "this is supposed to be empty";
        account.school = "this is supposed to be empty";
        account.highest_qualification = "this is supposed to be empty";
        account.gender = "this is supposed to be empty";
        account.goal = "this is supposed to be empty";
        account.resources = "this is supposed to be empty";
        account.action_plan = "this is supposed to be empty";
        //Build the request and send it in. What we get is what the frontend will get too.
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> credentials = new HttpEntity<String>("{\"data\":[" + new ObjectMapper().writeValueAsString(account) + "]}", header);
		return postForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w?sheet=user-account", credentials, String.class);
    }
	
	@PutMapping("/edituser")
	@ResponseBody
    public ResponseEntity<String> PUTedituser(@RequestBody UserAccount account) throws IOException
    {
		RestTemplate special = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		//If no email is provided, there will be no account specified to update. Stop here.
		String email = account.email;
		if (email == null)
			return new ResponseEntity<String>("Email was not provided.", HttpStatus.NOT_ACCEPTABLE);
		//Password and role cannot be modified, email will become a parameter instead.
		account.email = null;
		account.password = null;
		account.role = null;
		//Empty strings are replaced with a special message that is unlikely to be reproduced by the user.
		if ("".equals(account.first_name))
			account.first_name = "this is supposed to be empty";
		if ("".equals(account.last_name))
			account.last_name = "this is supposed to be empty";
		if ("".equals(account.dob))
			account.dob = "this is supposed to be empty";
		if ("".equals(account.contact_no))
			account.contact_no = "this is supposed to be empty";
		if ("".equals(account.nationality))
			account.nationality = "this is supposed to be empty";
		if ("".equals(account.school))
			account.school = "this is supposed to be empty";
		if ("".equals(account.highest_qualification))
			account.highest_qualification = "this is supposed to be empty";
		if ("".equals(account.gender))
			account.gender = "this is supposed to be empty";
		if ("".equals(account.goal))
			account.goal = "this is supposed to be empty";
		if ("".equals(account.resources))
			account.resources = "this is supposed to be empty";
		if ("".equals(account.action_plan))
			account.action_plan = "this is supposed to be empty";
        //Build the request and send it in.
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> credentials = new HttpEntity<String>("{\"data\":[" + new ObjectMapper().writeValueAsString(account) + "]}", header);
		String result = special.patchForObject("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/email/" + email + "?sheet=user-account", credentials, String.class);
		//It's not easy to find out whether the request has succeeded. Just assume it's OK if it comes to this point and return with a 200.
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
	
	@DeleteMapping("/deleteuser")
    public ResponseEntity<String> DELETEdeleteuser(@RequestParam String email) throws IOException
    {
		//URLs can't have white spaces. Replace them with their ASCII identifier.
		email.replace(" ", "%20");
		//Search for the email address of the requested account to delete. If it doesn't exist, stop here.
		if (getForObject("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/search?email=" + email + "&sheet=user-account", UserAccount[].class).length < 1)
			return new ResponseEntity<String>("Account does not exist", HttpStatus.NOT_FOUND);
		//Delete it if it exists, and assume everything's OK.
        delete("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/email/" + email + "?sheet=user-account", String.class);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
	
	@GetMapping("/loginuser")
	public ResponseEntity<String> GETloginuser(@RequestBody UserAccount account) throws IOException
	{
		//URLs can't have white spaces. Replace them with their ASCII identifier.
		String email = account.email;
		email.replace(" ", "%20");
		//Search for the account associated with this email.
		ResponseEntity<String> query = getForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/search?email=" + email + "&sheet=user-account", String.class);
		//Anything other than an OK status code is echoed to frontend. Something's probably wrong, we'll stop here.
        if (query.getStatusCode() != HttpStatus.OK)
        {
        	System.out.println(query.getStatusCode().toString() + ": " + query.getBody());
        	return query;
        }
        //Map the result into a UserAccount.
        UserAccount[] searchResults = new ObjectMapper().readValue(query.getBody(), UserAccount[].class);
        //If there was no match for the queried email, stop here and return a null array.
        if (searchResults.length != 1)
            return new ResponseEntity<String>("Account not found.", HttpStatus.NOT_FOUND);
		//Compare the password against an encrypted hash stored in the database. Return accordingly.
		if (new PasswordEncoderGenerator().validatePassword(account.password, searchResults[0].password))
			return new ResponseEntity<String>("Logged in.", HttpStatus.OK);
		else return new ResponseEntity<String>("Wrong password.", HttpStatus.UNAUTHORIZED);
	}
	
	public ResponseEntity<String> GETall(String sheet)
	{
		//Get information.
		ResponseEntity<String> query = getForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w?sheet=" + sheet, String.class);
		//Anything other than an OK status code is echoed to frontend. Something's probably wrong, we'll stop here.
        if (query.getStatusCode() != HttpStatus.OK)
        {
        	System.out.println(query.getStatusCode().toString() + ": " + query.getBody());
        	return query;
        }
        //If nothing else, map them into an array of Entry.
        Entry[] searchResults = new Entry[0];
		try
		{
			searchResults = new ObjectMapper().readValue(query.getBody(), Entry[].class);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
        for (Entry entry : searchResults)
        {
        	//The empty placeholder is replaced with an actually empty string.
    		if ("this is supposed to be empty".equals(entry.content))
    			entry.content = "";
        }
        //Serialise and return.
        String toFront = "";
		try
		{
			toFront = new ObjectMapper().writeValueAsString(searchResults);
		}
		catch (JsonProcessingException e)
		{
			e.printStackTrace();
		}
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<String>(toFront, header, HttpStatus.OK);
	}
	
	public ResponseEntity<String> GETspecific(@RequestParam String title, String sheet)
	{
		//Get information.
		ResponseEntity<String> query = getForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/search?title=" + title + "&sheet=" + sheet, String.class);
		//Anything other than an OK status code is echoed to frontend. Something's probably wrong, we'll stop here.
        if (query.getStatusCode() != HttpStatus.OK)
        {
        	System.out.println(query.getStatusCode().toString() + ": " + query.getBody());
        	return query;
        }
        //Map the result into an Entry.
        Entry[] searchResults = new Entry[0];
		try
		{
			searchResults = new ObjectMapper().readValue(query.getBody(), Entry[].class);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
        //If there was no match for the queried title, stop here and return a null array.
        if (searchResults.length != 1)
            return new ResponseEntity<String>("[]", HttpStatus.OK);
    	//The empty placeholder is replaced with an actually empty string.
		if ("this is supposed to be empty".equals(searchResults[0].content))
			searchResults[0].content = "";
        //Serialise and return.
        String toFront = "";
		try
		{
			toFront = new ObjectMapper().writeValueAsString(searchResults);
		}
		catch (JsonProcessingException e)
		{
			e.printStackTrace();
		}
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<String>(toFront, header, HttpStatus.OK);
	}
	
	public ResponseEntity<String> POSTnew(@RequestBody Entry entry, String sheet)
	{
		//URLs can't have white spaces. Replace them with their ASCII identifier.
		String title = entry.title;
		title.replace(" ", "%20");
		//Search for a title-content entry with the given title.
		ResponseEntity<String> query = getForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/search?title=" + title + "&sheet=" + sheet, String.class);
		//Anything other than an OK status code is echoed to frontend. Something's probably wrong, we'll stop here.
        if (query.getStatusCode() != HttpStatus.OK)
        {
        	System.out.println(query.getStatusCode().toString() + ": " + query.getBody());
        	return query;
        }
        //If nothing else, map them into an array of PrepareForChange. This array should be empty.
        Entry[] searchResults = new Entry[0];
		try
		{
			searchResults = new ObjectMapper().readValue(query.getBody(), Entry[].class);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
        //If the array contains a entry, it means there is already an entry in the database with the new title.
        if (searchResults.length > 0)
        {
        	//All entries must have a unique title. Duplicate entries with the same titles can't be submitted.
        	return new ResponseEntity<String>("Title is already used.", HttpStatus.UNAUTHORIZED);
        }
        //Build the request and send it in. What we get is what the frontend will get too.
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> credentials = new HttpEntity<String>("");
		try
		{
			credentials = new HttpEntity<String>("{\"data\":[" + new ObjectMapper().writeValueAsString(entry) + "]}", header);
		}
		catch (JsonProcessingException e)
		{
			e.printStackTrace();
		}
		return postForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w?sheet=" + sheet, credentials, String.class);
	}
	
	public ResponseEntity<String> PUTedit(@RequestBody Entry entry, String sheet)
	{
		RestTemplate special = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		//If no title is provided, there will be no entry specified to update. Stop here.
		if (entry.title == null)
			return new ResponseEntity<String>("Title was not provided.", HttpStatus.NOT_ACCEPTABLE);
		//The content is the only modifiable field. If no content is provided, then there's no point in updating. Stop here.
		if (entry.content == null)
			return new ResponseEntity<String>("Provide content to update.", HttpStatus.NOT_ACCEPTABLE);
        //Build the request and send it in.
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> credentials = new HttpEntity<String>("{\"data\":[{\"content\":\"" + entry.content + "\"]}", header);
		String result = special.patchForObject("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/email/" + entry.title + "?sheet=user-account", credentials, String.class);
		//It's not easy to find out whether the request has succeeded. Just assume it's OK if it comes to this point and return with a 200.
        return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	public ResponseEntity<String> DELETEentry(@RequestParam String title, String sheet)
	{
		//Search for the email address of the requested account to delete. If it doesn't exist, stop here.
		if (getForObject("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/search?title=" + title + "&sheet=" + sheet, Entry[].class).length < 1)
			return new ResponseEntity<String>("Entry does not exist", HttpStatus.NOT_FOUND);
		//Delete it if it exists, and assume everything's OK.
        delete("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/title/" + title + "?sheet=" + sheet, String.class);
        return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@GetMapping("/allprepareforchange")
	public ResponseEntity<String> GETallprepareforchange()
	{
		return GETall("prepare-for-change");
	}
	
	@GetMapping("/prepareforchange")
	public ResponseEntity<String> GETprepareforchange(@RequestParam String title)
	{
		return GETspecific(title, "prepare-for-change");
	}
	
	@PostMapping("/createprepareforchange")
	public ResponseEntity<String> POSTcreateprepareforchange(@RequestBody Entry entry)
	{
		return POSTnew(entry, "prepare-for-change");
	}
	
	@PutMapping("/editprepareforchange")
	public ResponseEntity<String> PUTeditprepareforchange(@RequestBody Entry entry)
	{
		return PUTedit(entry, "prepare-for-change");
	}
	
	@DeleteMapping("/deleteprepareforchange")
	public ResponseEntity<String> DELETEdeleteprepareforchange(@RequestParam String title)
	{
		return DELETEentry(title, "prepare-for-change");
	}
	
	@GetMapping("/allknowyourself")
	public ResponseEntity<String> GETallknowyourself()
	{
		return GETall("know-yourself");
	}
	
	@GetMapping("/knowyourself")
	public ResponseEntity<String> GETknowyourself(@RequestParam String title)
	{
		return GETspecific(title, "know-yourself");
	}
	
	@PostMapping("/createknowyourself")
	public ResponseEntity<String> POSTcreateknowyourself(@RequestBody Entry entry)
	{
		return POSTnew(entry, "know-yourself");
	}
	
	@PutMapping("/editknowyourself")
	public ResponseEntity<String> PUTeditknowyourself(@RequestBody Entry entry)
	{
		return PUTedit(entry, "know-yourself");
	}
	
	@DeleteMapping("/deleteknowyourself")
	public ResponseEntity<String> DELETEdeleteknowyourself(@RequestParam String title)
	{
		return DELETEentry(title, "know-yourself");
	}
	
	@GetMapping("/allexploreopportunities")
	public ResponseEntity<String> GETallexploreopportunities()
	{
		return GETall("explore-opportunities");
	}
	
	@GetMapping("/exploreopportunities")
	public ResponseEntity<String> GETexploreopportunities(@RequestParam String title)
	{
		return GETspecific(title, "explore-opportunities");
	}
	
	@PostMapping("/createexploreopportunities")
	public ResponseEntity<String> POSTcreateexploreopportunities(@RequestBody Entry entry)
	{
		return POSTnew(entry, "explore-opportunities");
	}
	
	@PutMapping("/editexploreopportunities")
	public ResponseEntity<String> PUTeditexploreopportunities(@RequestBody Entry entry)
	{
		return PUTedit(entry, "explore-opportunities");
	}
	
	@DeleteMapping("/deleteexploreopportunities")
	public ResponseEntity<String> DELETEdeleteexploreopportunities(@RequestParam String title)
	{
		return DELETEentry(title, "explore-opportunities");
	}
}