package com.example.demo;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@RestController
public class DemoController {
	
	@GetMapping("/account")
	public String GetAccountByID(short id) throws IOException
	{
		String theString = "https://jsonplaceholder.typicode.com/todos/" + Integer.toString(id);
        RestTemplate restTemplate = new RestTemplate();
        theString = restTemplate.getForObject(theString, String.class);

        JsonParser springParser = JsonParserFactory.getJsonParser();

        //List<Object> list = springParser.parseList(theString);
        Map<String, Object> result = springParser.parseMap(theString);
        return theString;
        /*theString = "";
        for (Map.Entry<String, Object> entry : result.entrySet())
		{
			theString += entry.getKey() + " = " + entry.getValue() + "\n";
		}*/

        /*for(Object o : list)
        {
        	if(o instanceof Map)
        	{
        		Map<String,Object> map = (Map<String,Object>) o;
        		System.out.println("Items found: " + map.size());

        		for (Map.Entry<String, Object> entry : map.entrySet())
        		{
        			theString += entry.getKey() + " = " + entry.getValue() + "\n";
        		}

        	}
        }*/
	}

}
