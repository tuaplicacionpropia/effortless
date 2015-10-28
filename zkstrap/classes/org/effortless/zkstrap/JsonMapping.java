package org.effortless.zkstrap;

import java.io.IOException;
import java.util.HashMap;

import org.effortless.core.EnumString;
import org.effortless.core.UnusualException;
import org.effortless.orm.Entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class JsonMapping {

	protected JsonMapping () {
		super();
		initiate();
	}
	
	protected void initiate() {
	}
	
	public static class EnumStringSerializer extends JsonSerializer {

		public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) 
	      throws IOException, JsonProcessingException {
			jgen.writeString(((EnumString)value).name());
	    }
	}
	
	public static class EnumStringDeserializer extends JsonDeserializer {

		public Object deserialize(JsonParser parser, DeserializationContext ctx) throws IOException, JsonProcessingException {
			Object result = null;
			// TODO Auto-generated method stub
			return result;
		}
	}
	
	public static class EntitySerializer extends JsonSerializer {

		public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) 
	      throws IOException, JsonProcessingException {
			Entity entity = (Entity)value;
			jgen.writeString(entity.toLabel());
	    }
	}
	
	public static class EntityDeserializer extends JsonDeserializer {

		public Object deserialize(JsonParser parser, DeserializationContext ctx) throws IOException, JsonProcessingException {
			Object result = null;
			// TODO Auto-generated method stub
			return result;
		}
	}
	
    public static java.util.Map toMap(String input) {
    	java.util.Map result = null;
    	try {
	//    	String INPUT = "{a:[1,2,{b:true},3],c:'3'}";
	    	
	    	ObjectMapper mapper = new ObjectMapper();
	    	mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	//    	mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, false);
	    	mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

	    	SimpleModule module = new SimpleModule();
	    	module.addDeserializer(EnumString.class, new EnumStringDeserializer());
	    	module.addDeserializer(Entity.class, new EntityDeserializer());
	    	mapper.registerModule(module);	    	
	    	
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
	    	
//	    	module.addSerializer(Item.class, new ItemSerializer());	    	
	    	mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	//    	mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, false);
	    	mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    	
	    	SimpleModule module = new SimpleModule();
	    	module.addSerializer(EnumString.class, new EnumStringSerializer());
	    	module.addSerializer(Entity.class, new EntitySerializer());
	    	mapper.registerModule(module);	    	
	    	
	    	result = mapper.writeValueAsString(map);
    	}
		catch (Throwable t) {
			throw new UnusualException(t);
		}
    	return result;
    }

}
