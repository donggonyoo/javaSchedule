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

public class Change {
	public Change(Map<String, List<Event>> events) {
		Scanner scan = new Scanner(System.in);
		System.out.print("수정할 이벤트의 날짜를 입력하세요 (yyyy-MM-dd): ");
		String date = scan.nextLine();
		int count=0;
		//String->date (yyyy-MM-dd)형태로반환하자 
		//(형태변환(Parse) 와 예외를 잡게 만든 Parse클래스의 StoDate클래스이용)
		LocalDate date2 = Parse.StoDate(date);
		if(date2==null) {return;}
		List<Event> list = events.get(Client.name);
		List<Event> newList = new ArrayList<Event>();
		if(list==null) {
			System.out.println(Client.name+"님은 아무이벤트가없어요");
			return;
		}//아이디에 아무이벤트가없다면 foreach문 자체에서 예외발생하므로 처리!
		
		for (Event e : list) {
			if(e.date.equals(date2)) {
				count++;
				newList.add(e);//새로운리스트에 추가	
				//			int indexOf = list.indexOf(e);
				//			System.out.println((indexOf+1)+"번"+e);
				//같은날짜에 이벤트가 2개인 경우라면인덱스를 반환해서
				//사용자가 지정한 인덱스를 삭제하기위함!
			}
		}

		if(count==0) {System.out.println("변경할 이벤트가 없어요");return;}

		try {
			for (int i = 1; i <= count; i++) {
				System.out.println(i+"번 : "+newList.get(i-1));
			}

			System.out.println("변경할 제목의 번호를 입력하세요 : (안하려면 문자를 입력하세요)");
			int index = scan.nextInt();

			scan.nextLine();

			System.out.print(" 제목을 입력하세요: ");
			String title = scan.nextLine();
			System.out.print(" 시작 시간을 입력하세요:(HH:mm) ");
			String startTime = scan.nextLine();
			//Parse.StoTime(LocalDate d,String s) 입력받아 메인코드의 포맷형식과맞다면
				//Date와 s를더해 LocalDateTime 반환
			LocalDateTime start = Parse.StoTime(date2, startTime);
			if(start==null) {return;}
			System.out.print(" 종료시간을 입력하세요:(HH:mm) ");
			String lastTime = scan.nextLine();
			LocalDateTime last = Parse.StoTime(date2, lastTime); 
			if(last==null) {return;}
			System.out.print(" 상세 정보를 입력하세요: ");
			String details = scan.nextLine();
			System.out.println("이벤트를 변경 하실건가요??(y)");
			String y = scan.nextLine();
			if(y.equalsIgnoreCase("y")) {
				System.out.print(newList.get(index-1).title+"-->"+title+"\n");
				list.remove(newList.get(index-1)); 
				System.out.println("변경완료");
				list.add(new Event(date2, title, start, last, details));
				events.clear();//한번 싹 지우고
				events.put(Client.name, list);//바꾼리스트를 넣는판단이 맞는거같다!!!
			}
			else {
				System.out.println("변경취소"); //아니라면 아무 변화도 주지않는다 
				return;
			}

			//그렇지않다면 events맵에 변화가 전혀없을거다 list는 반환한 객체기때문에 events의 변경과 무관함
		}
		catch(InputMismatchException e) {
			scan.next();
			System.out.println("메뉴선택으로 돌아감");
			return;
		}
		catch(DateTimeParseException e) {
			System.out.println("올바른 날짜형식이 아닙니다");
			return;
		}
	}
}


