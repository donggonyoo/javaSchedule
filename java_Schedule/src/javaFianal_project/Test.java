package javaFianal_project;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.HashMap;

public class Test {
	public static void main(String[] args) {
		LocalDate ld = LocalDate.now();
		System.out.println("현재날짜 : "+ld);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
		
		String format = ld.format(formatter);
		System.out.println("format : "+format);
		
		String input = "2023년 10월 04일";
		LocalDate parse = LocalDate.parse(input,formatter);
		System.out.println("parse : "+parse);
		
		LocalDateTime ldt = LocalDateTime.of(2024, 10,20,13,30,00);
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초");
		String format2 = ldt.format(formatter2);
		System.out.println("ldt : "+ldt);
		System.out.println("format2 :"+format2);
		
		String input2 = "2000년 11월 20일 13시 20분 45초";
		LocalDateTime parse2 = LocalDateTime.parse(input2,formatter2);
		System.out.println("input : "+input2);
		System.out.println("parse2 : "+parse2);
		
		LocalDate of = LocalDate.of(2025, 12, 1);
		int monthValue = of.getMonthValue();
		LocalDate with = of.with(TemporalAdjusters.lastDayOfMonth());
		
		
		System.out.println("2025-02-16~~ 2025-02-22의 날짜");
		for (int i = 16; i <= 22; i++) {
			LocalDate date = LocalDate.of(2025, 2, i);
			DayOfWeek Week = date.getDayOfWeek();//날짜얻기
			int num = Week.getValue();//날짜에 해당하는 번호
			System.out.println(num+" : "+Week);
		}
		
		System.out.println("%7+1");
		for (int i = 16; i <= 22; i++) {
			LocalDate date = LocalDate.of(2025, 2, i);
			DayOfWeek Week = date.getDayOfWeek();//날짜얻기
			int num = (Week.getValue()%7)+1;//날짜에 해당하는 번호 %7 +1 
			System.out.println(num+" : "+Week);
		}
		
		
		
		
		

		
		
		

	
	}
	

}
