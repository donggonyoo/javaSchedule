package javaFianal_project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parse {

	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	//날짜 포맷을 위한 formatter
	public static DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	//시간을 입력받을때 초는 생략하기위한포맷형식( String->LocalDateTime)

//--------------------------날짜를 입력받아 LocalDate로 변환------------------------------------
	public static LocalDate  StoDate(String s) {
		try {
			LocalDate localDate = LocalDate.parse(s,formatter);
			return localDate;
		}
		catch(DateTimeParseException e) {
			System.out.println("날짜형식은 yyyy-MM-dd");
			return null;
		}
	}
//---------------------------------시간을 입력받아 LocalDateTime으로 반환-------------------------------------
	public static LocalDateTime  StoTime(LocalDate date ,String s) {
		try {
			LocalDateTime localDateTime = LocalDateTime.parse(date+" "+s,formatter2);
			return localDateTime;
		}
		catch(DateTimeParseException e) {
			System.out.println("시간은 HH:mm 만 입력");
			return null;
		}
	}
}
