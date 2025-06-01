package javaFianal_project;


import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Client {
	 public static String name;

	public static void main(String[] args) throws IOException, ClassNotFoundException, ParseException{

		Scanner scan = new Scanner(System.in);
		System.out.print("사용자 아이디를 입력하세요 : ");
		name = scan.nextLine();
		Controller controller = new Controller();
		Map<String, List<Event>> events = controller.LoadEvent();
											//Load 클래스의 클래스멤버이며 
									//Map<String, List<Event>>을 리턴하는 메서드				
		while(true) {
			System.out.print
			("메뉴번호를 입력하세요(1.종료, 2. 달력조회, 3.이벤트추가, 4.이벤트 조회, 5이벤트 변경, 6.이벤트 삭제)");
			int choice = scan.nextInt();
			scan.nextLine();
			switch(choice) {
			case 1 -> {
				controller.Save(events);
				System.out.println("프로그램을 종료합니다.");
				return;//while문 종료 break;는 해당루프만 빠져나온다는 뜻임
				}
			case 2 -> controller.CalendarPrint(events);
			//객체를 코드를 실행할떄마다 만드니 다른아이디와겹칠 일 없음
			case 3 -> controller.Add(events);
			case 4 -> controller.View(events);
			case 5 -> controller.Change(events);
			case 6 -> controller.Delete(events);
			default->
				System.out.println("잘못된 입력입니다. 다시 선택하세요.");
			}
		}
	}
}
