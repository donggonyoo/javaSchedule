package javaFianal_project.controllerList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javaFianal_project.Client;
import javaFianal_project.Event;
import javaFianal_project.Parse;
import javaFianal_project.Client;
public class Add {
	public Add(Map<String, List<Event>> events) {
		Scanner scan = new Scanner(System.in);
		try {
			System.out.println("시간을 입력하세요 : yyyy-MM-dd");
			String a = scan.nextLine();
			LocalDate date = Parse.StoDate(a);
			if(date==null) {return;}
			System.out.print("이벤트 제목을 입력하세요: ");
			String title = scan.nextLine();
			System.out.print("이벤트 시작 시간을 입력하세요:(HH:mm) ");
			String startTime = scan.nextLine();
			LocalDateTime start = Parse.StoTime(date, startTime);
			if(start==null) {return;}
			System.out.print("이벤트 종료 시간을 입력하세요:(HH:mm) ");
			String endTime = scan.nextLine();
			LocalDateTime last = Parse.StoTime(date, endTime);
			if(last==null) {return;}
			System.out.print("이벤트 상세 정보를 입력하세요: ");
			String details = scan.nextLine();
			Event event = new Event(date,title, start,last, details);
			List<Event> list = events.get(Client.name);
			if(list==null) {
				list=new ArrayList<Event>();
			}
			list.add(event);
			events.put(Client.name, list);
			System.out.println("이벤트가 추가되었습니다.");	   
		} catch (InputMismatchException e) { 
			System.out.println("형식을 맞추세요");
			System.out.println(e.getMessage());
			return;
		}

	}

}
