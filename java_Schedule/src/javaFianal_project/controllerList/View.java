package javaFianal_project.controllerList;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javaFianal_project.Client;
import javaFianal_project.Event;
import javaFianal_project.Parse;

public class View {
	public View(Map<String, List<Event>> events) {
		Scanner scan = new Scanner(System.in);
		int count=0;
			System.out.print("조회할 날짜를 입력하세요 (yyyy-MM-dd): ");
			String date = scan.nextLine();
			//String->date (yyyy-MM-dd)형태로반환하자
			LocalDate date2 = Parse.StoDate(date);
			if(date2==null) {return;}
			List<Event> list = events.get(Client.name);
			if(list==null) {
				System.out.println("이벤트가 아무것도없습니다");
				return;//이벤트가아무것도없다면 호출했던곳으로 return;
			}
			//list는 strartTime을기준으로 Comparable을구현했음!
			Collections.sort(list);
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).date.equals(date2)) {
					System.out.println(list.get(i));//toString으로 출력을 정해놨음
					count++;
				}
			}
			if (count==0) {
				System.out.println("해당 날짜에 이벤트가 없습니다.");
			}	
	}
}
