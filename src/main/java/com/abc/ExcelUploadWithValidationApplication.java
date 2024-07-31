package com.abc;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExcelUploadWithValidationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExcelUploadWithValidationApplication.class, args);
		
		String str="mumbai";
		Map<Character, Long> collect = str.chars().mapToObj(c->(char)c).collect(Collectors.groupingBy(c->c,Collectors.counting()));
	  System.out.println(collect);
	}

}
