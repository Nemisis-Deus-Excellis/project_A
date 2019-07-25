package com.example.demo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.example.demo.encryptor.PasswordEncoderGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@RestController
public class DemoController extends RestTemplate
{
    @GetMapping("/alluser")
    public ResponseEntity<String> GETalluser() throws IOException
    {
		//Get information
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
        }
        //Serialise and return.
        String toFront = new ObjectMapper().writeValueAsString(searchResults);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<String>(toFront, header, HttpStatus.OK);
        //String body = entity.getBody();
        //System.out.println(body + "i was edited lmao");
        //ObjectMapper objectMapper = new ObjectMapper();
    }
	
	@GetMapping("/user")
    public ResponseEntity<String> GETuser(@RequestParam String email) throws IOException
    {
		//Get information
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
		//Search for the email address used to register this new account.
		ResponseEntity<String> query = getForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/search?email=" + account.email + "&sheet=user-account", String.class);
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
        //Build the request and send it in. What we get is what the frontend will get too.
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
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
        + "}", header);
		return postForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w?sheet=user-account", credentials, String.class);
    }
	
	@PutMapping("/edituser")
	@ResponseBody
	//TODO ask if email can be changed by the end user, since it's kind of the only unique identifier.
    public ResponseEntity<String> PUTedituser(@RequestBody UserAccount account) throws IOException
    {
		//If no email is provided, there will be no account specified to update. Stop here.
		String email = account.email;
		if (email.isEmpty())
			return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
		//Password and role cannot be modified, email will become a parameter instead.
		account.email = null;
		account.password = null;
		account.role = null;
		//System.out.println("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/email/" + email + "?sheet=user-account");
		//System.out.println("{\"data\":[" + new ObjectMapper().writeValueAsString(account) + "]}");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> credentials = new HttpEntity<String>("{\"data\":[" + new ObjectMapper().writeValueAsString(account) + "]}", header);
		//String result = patchForObject("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/email/" + email + "?sheet=user-account", "{\"data\":[" + new ObjectMapper().writeValueAsString(account) + "]}", String.class);
		String result = exchange("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/email/" + email + "?sheet=user-account", HttpMethod.PATCH, credentials, String.class).getBody();
		//"Endpoint: https://sheetdb.io/api/v1/x7q7rbu7cdn5w/email/eejoe.chang@trampolene.org?sheet=user-account
		//Body: {""data"" :[{""contact-no"": ""222222"",""nationality"": ""singapore""}]}"
		//Search for the email address used to register this new account.
		/*ResponseEntity<String> query = getForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/search?email=" + account.email + "&sheet=user-account", String.class);
        if (query.getStatusCode() != HttpStatus.OK)
        {
        	System.out.println(query.getStatusCode().toString() + ": " + query.getBody());
        	return query;
        }
        UserAccount[] searchResults = new ObjectMapper().readValue(query.getBody(), UserAccount[].class);
        if (searchResults.length > 0)
        {
        	//Account already exists lmao
        	return new ResponseEntity<String>("Email is already tied to an existing account.", HttpStatus.UNAUTHORIZED);
        }
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
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
        + "}", header);
		ResponseEntity<String> boyo = postForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w?sheet=user-account", credentials, String.class);
    	return new ResponseEntity<String>("Stick around lol.", HttpStatus.UNAUTHORIZED);*/
		return new ResponseEntity<String>(result, HttpStatus.OK);
    }
	
	@DeleteMapping("/deleteuser")
    public ResponseEntity<String> DELETEdeleteuser(@RequestParam String email) throws IOException
    {
		if (getForObject("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/search?email=" + email + "&sheet=user-account", UserAccount[].class).length < 1)
			return new ResponseEntity<String>("Account does not exist", HttpStatus.NOT_FOUND);
        delete("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/email/" + email + "?sheet=user-account", String.class);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
	
	@GetMapping("/allprepareforchange")
	public ResponseEntity<String> GETallprepareforchange()
	{
		return getForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w?sheet=prepare-for-change", String.class);
	}
	
	@GetMapping("/prepareforchange")
	public ResponseEntity<String> GETprepareforchange(@RequestParam String title)
	{
		return getForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/search?title=" + title + "&sheet=prepare-for-change", String.class);
	}
	
	@PostMapping("/createprepareforchange")
	public ResponseEntity<String> POSTcreateprepareforchange(@RequestBody PrepareForChange pair)
	{
		//URLs can't have white spaces. Replace them with their ASCII identifier.
		String title = pair.title;
		title.replace(" ", "%20");
		//Search for a title-content entry with the given title.
		ResponseEntity<String> query = getForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w/search?title=" + title + "&sheet=prepare-for-change", String.class);
		//Anything other than an OK status code is echoed to frontend. Something's probably wrong, we'll stop here.
        if (query.getStatusCode() != HttpStatus.OK)
        {
        	System.out.println(query.getStatusCode().toString() + ": " + query.getBody());
        	return query;
        }
        //If nothing else, map them into an array of PrepareForChange. This array should be empty.
        PrepareForChange[] searchResults = new PrepareForChange[0];
		try
		{
			searchResults = new ObjectMapper().readValue(query.getBody(), PrepareForChange[].class);
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
			credentials = new HttpEntity<String>("{\"data\":[" + new ObjectMapper().writeValueAsString(pair) + "]}", header);
		}
		catch (JsonProcessingException e)
		{
			e.printStackTrace();
		}
		return postForEntity("https://sheetdb.io/api/v1/x7q7rbu7cdn5w?sheet=prepare-for-change", credentials, String.class);
	}
}