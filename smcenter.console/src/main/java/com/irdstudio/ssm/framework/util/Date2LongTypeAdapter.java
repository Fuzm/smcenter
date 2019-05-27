package com.irdstudio.ssm.framework.util;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * 
 * @author liwy
 *
 * time:2016年8月23日下午6:54:12
 */
public class Date2LongTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

	
    /* (non-Javadoc)
     * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
     */
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
    	Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
    	String format = "yyyy-MM-dd";
    	Matcher m = pattern.matcher(json.getAsJsonPrimitive().getAsString());
    	
    	SimpleDateFormat formatter = new SimpleDateFormat(format);
    	boolean dateFlag = m.matches();
    	
    	if(json.getAsJsonPrimitive().getAsString()!=null){
    		if(dateFlag) {
    			try {
					return formatter.parse(json.getAsJsonPrimitive().getAsString());
				} catch (ParseException e) {
				    
				}  
    		}else{
    			return new java.util.Date(json.getAsJsonPrimitive().getAsLong());        
    		}
    	}else{
    		return null;
    	}
		return null;
    }

    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getTime());          
    }

}

