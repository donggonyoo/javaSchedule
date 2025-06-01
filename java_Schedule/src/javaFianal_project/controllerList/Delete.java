package javaFianal_project.controllerList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javaFianal_project.Client;
import javaFianal_project.Event;
import javaFianal_project.Parse;

public class Delete {
	public Delete(Map<String, List<Event>> events) {
		Scanner scan = new Scanner(System.in);
		System.out.print("삭제할 이벤트의 날짜를 입력하세요 (yyyy-MM-dd): ");
		String date = scan.nextLine();
		LocalDate date2 = Parse.StoDate(date);//String->date (yyyy-MM-dd)형태로반환하자
		if(date2==null) {return;}
		int count=0;
		List<Event> list = events.get(Client.name);
		List<Event> delList = new ArrayList<Event>();
		if(list==null) {
			System.out.println("이벤트가 아무것도없어요");return;
			}
		Collections.sort(list);
		for (Event e : list) {
			if(e.date.equals(date2)) {
				count++;
				delList.add(e);
				
			}
		}
		if(count==0) {
			System.out.println("해당날짜엔 삭제할게없어요");
			return;
		}
		
		try {
			for (int i = 1; i <= count; i++) {
				System.out.println(i+"번 : "+delList.get(i-1));
			}
			System.out.println("삭제할 이벤트의 번호를 입력해주세요(안하려면 문자입력)");
			int num = scan.nextInt();
			System.out.println(delList.get(num-1)+" => !! 삭제완료 !!");
			list.remove(delList.get(num-1));
			events.clear();
			events.put(Client.name, list);

		}catch(InputMismatchException e) {
			System.out.println("삭제하지않겠습니다");
			scan.next();
			return;
		}
		
	}

}
