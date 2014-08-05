package com.example.listmaker.test.helper;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;


public class EnvTestCase extends BaseTest {

  public void testEnv() {
	  Map<String, String> env = System.getenv();
	  for (String key : env.keySet()) {
		String value = env.get(key);
		System.out.println(key + "=" + value);
	}
  }
  
  public void testSystemProperties() {
	  Properties properties = System.getProperties();
	  Set<Entry<Object, Object>> entrySet = properties.entrySet();
	  for (Entry<Object, Object> e : entrySet) {
		System.out.println(e.getKey() + "=" + e.getValue());
	}
  }
}