package com.smarts.date;

import java.text.ParseException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.smarts.utils.DateUtils;

public class TestDate {
	@Test
	public  void printProperties(){
//		System.out.println(System.getProperty("line.separator"));
		Properties ps = System.getProperties();
		Set<Object> set = ps.keySet();
		//
		for(Object o : set){
//			System.out.println("name : "+o+" :  value : "+ps.getProperty(o.toString()));
		}
		Map<String,String> map = System.getenv();
		for(Map.Entry<String,String> entry : map.entrySet()){
			System.out.println(entry.getKey() + " : " +entry.getValue());
		}
	}

	@Test
	public void dateChange() throws ParseException{
//		System.out.println(DateUtils.string2UnixTimeSecond("2014-07-29 23:59:59", "yyyy-MM-dd HH:mm:ss"));
//		System.out.println(DateUtils.string2UnixTimeSecond("2014-07-29 00:00:00", "yyyy-MM-dd HH:mm:ss"));
//		System.out.println(DateUtils.unixTime2String(1408599908));
//		System.out.println(DateUtils.unixTime2String(1408594942));
		String str = " a  d  b d e g  ";
		Pattern pattern = Pattern.compile(" ");
		Matcher m = pattern.matcher(str);
		if(m.find(0)){
			System.out.println(m.group());
		}
		if(m.find()){
			System.out.println("ddd:"+m.end()+" : "+m.start());
		}
		System.out.println(m.groupCount());
	}
}
