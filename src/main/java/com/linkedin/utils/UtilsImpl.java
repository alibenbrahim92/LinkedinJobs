package com.linkedin.utils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class UtilsImpl implements Utils{

	@Override
	public String mapToString(Map<String, String> fromMap) {
		String[] keyElements;
		String link;
		StringBuilder result = new StringBuilder();
		for(Entry<String, String> entry : fromMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			link = value.split(";;;")[1].trim();
			keyElements = key.split(";;;");
			String role = keyElements[0].trim();
			String companyName = keyElements[1].trim();
			String location = keyElements[2].trim();
			result.append("*** Role : ").append(role).append(" ***\n");
			result.append("- Company : ").append(companyName).append("\n");
			result.append("- Location : ").append(location).append("\n");
			result.append("- Link : ").append(link).append("\n");
			result.append("****************************\n");
		}
		return result.toString();
	}
	
	@Override
	public String mapToSubject(Map<String,String> fromMap) {
		
		return String.format("%s new Java jobs at %s", fromMap.size(), LocalDateTime.now());
		
	}
	
	@Override
	public String mapToSubject(Map<String,String> fromMap, List<String> cities, String keyword) {
		
		String jobCities = fromMap.keySet()
			.stream()
			.map(e -> e.split(";;;")[2].trim())
			.flatMap(e -> Arrays.stream(e.split(",")))
			.map(e -> e.trim())
			.distinct()
			.filter(cities::contains)
			.collect(Collectors.joining(", "));
		
		Boolean keywordFound = fromMap.keySet()
				.stream()
				.map(String::toUpperCase)
				.anyMatch(e -> e.contains(keyword.toUpperCase()));
		
		// Print either "Java" or keyword in UpperCase
		String newKeyword = keywordFound ? keyword.toUpperCase() : "Java";
		
		return String.format("%s : %s new %s jobs at %s", jobCities, fromMap.size(), newKeyword, LocalDateTime.now());
		
	}
	
}
