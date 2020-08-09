package com.yong.spring.jpa.jms.commons;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JacksonUtil {
	private static ObjectMapper mapper = new ObjectMapper();  
	static {
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
	}

	public static String objectToJson(Object obj) {
		try {
			StringWriter sw = new StringWriter();
	        JsonGenerator gen;
			gen = new JsonFactory().createGenerator(sw);
	        mapper.writeValue(gen, obj);
	        gen.close();
	        return sw.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }  
    
	public static String objectToJson(Object obj, PropertyNamingStrategy strategy) {
		try {
		    
			if (strategy != null) {
			    ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
		        mapper.setPropertyNamingStrategy(strategy);
		        StringWriter sw = new StringWriter();
	            JsonGenerator gen = new JsonFactory().createGenerator(sw);
	            mapper.writeValue(gen, obj);
	            gen.close();
	            return sw.toString();
	        }else {
	            return objectToJson(obj);
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }  
  
    public static <T> T jsonToObject(String jsonStr, Class<T> objClass)  
            throws JsonParseException, JsonMappingException, IOException {  
    	return mapper.readValue(jsonStr, objClass);  
    }
    
    /**
     * 支持普通类和泛型类 反序列化支持   
     * <br/>
     * <br/>泛型类： RequestList&ltTicketCmd3001RequestDTO&gt tmp = 
     * <br/>JacksonUtil.jsonToObject(lststr, new TypeReference&ltRequestList&ltTicketCmd3001RequestDTO&gt&gt(){});
     * <br/>
     * <br/>普通类: TicketCmd3001RequestDTO tmpd2 = 
     * <br/>JacksonUtil.jsonToObject(s2, new TypeReference&ltTicketCmd3001RequestDTO&gt() {});
     * <br/>
     */
    public static <T> T jsonToObject(String jsonStr, TypeReference<T> typeReference)  
            throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(jsonStr, typeReference);  
    }
}
