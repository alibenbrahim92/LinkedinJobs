package com.linkedin.filters;

import java.util.Arrays;
import java.util.List;

public class FilterJobs {
	private FilterJobs() {}
	public static String CLICKJOBS = "ClickJobs.io";
	public static String CROSSOVER = "Crossover";
	public static String DESJARDINS = "Desjardins";
	public static String JOOBLE = "Jooble";
	public static String ACTALENTGRANBYENG = "Java Developer ;;; Actalent ;;; Granby";
	public static String ACTALENTGRANBYFR = "Développeur Java ;;; Actalent ;;; Granby";
	public static String ACTALENTMONTREALENG = "Java Developer ;;; Actalent ;;; Montreal";
	public static String ACTALENTMONTREALFR = "Développeur Java ;;; Actalent ;;; Montreal";
	public static String WORKATHOMEJOBBOARD= "WorkatHome-JobBoard";
	public static String VARSITY = "Varsity Tutors";
	public static List<String> filterList = Arrays.asList(WORKATHOMEJOBBOARD,ACTALENTGRANBYENG,ACTALENTGRANBYFR,ACTALENTMONTREALENG,ACTALENTMONTREALFR,CLICKJOBS,CROSSOVER,DESJARDINS,JOOBLE,VARSITY);
}
