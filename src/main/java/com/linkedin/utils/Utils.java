package com.linkedin.utils;

import java.util.List;
import java.util.Map;

public interface Utils {
	String mapToString(Map<String,String> fromMap);
	
	String mapToSubject(Map<String,String> fromMap);
	
	String mapToSubject(Map<String,String> fromMap, List<String> cities, String keyword);

}
