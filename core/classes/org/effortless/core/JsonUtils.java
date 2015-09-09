package org.effortless.core;

import java.util.HashMap;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils extends Object {

	protected JsonUtils () {
		super();
		initiate();
	}
	
	protected void initiate() {
	}
	
    public static java.util.Map toMap(String input) {
    	java.util.Map result = null;
    	try {
	//    	String INPUT = "{a:[1,2,{b:true},3],c:'3'}";
	    	
	    	ObjectMapper mapper = new ObjectMapper();
	    	mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	//    	mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, false);
	    	mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
	    	
	    	result = mapper.readValue(input, new TypeReference<HashMap>(){});
    	}
		catch (Throwable t) {
			throw new UnusualException(t);
		}
    	return result;
    }
	

    public static String toJson(java.util.Map map) {
    	String result = null;
    	try {
	    	ObjectMapper mapper = new ObjectMapper();
	    	mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	//    	mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, false);
	    	mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    	
	    	result = mapper.writeValueAsString(map);
    	}
		catch (Throwable t) {
			throw new UnusualException(t);
		}
    	return result;
    }

}
