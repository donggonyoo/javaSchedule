package javaFianal_project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Controller {

//--------------------------------------------------------파일 저장 ---------------------------------------------------------------
	public void Save(Map<String, List<Event>> events) throws IOException {
		ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(Client.name+".ser"));
		writer.writeObject(events);
		writer.flush();
		writer.close();
	}

//---------------------------------------------------파일(ser) 로드 ------------------------------------------------------------------------------
	public  Map<String, List<Event>> LoadEvent() throws FileNotFoundException, IOException, ClassNotFoundException {
		File file = new File(Client.name + ".ser");//입력받은 name이름과같은 ser파일을 불러옴

		if (file.exists()) {
			ObjectInputStream reader=null;
			try {
				reader = new ObjectInputStream(new FileInputStream(file));
				return (Map<String, List<Event>>)reader.readObject();
				//ser파일을 읽어서 Map<String,List<Event>>타입으로 반환해준다/
			}finally {
				reader.close();//항상 reader를 닫아줘야함(끝없이 로딩되는거 방지)
			}
		}
		else {
			// 파일이 없다면 catch로 잡아서 예외를처리하는것이 아닌
			//HashMap<String,List<Event>>을 만들어줘서반환
			return new HashMap<String,List<Event>>();
		}
	}
	
//----------------------------------------------이벤트 추가 ------------------------------------------	
	public void Add(Map<String, List<Event>> events) {
		Scanner scan = new Scanner(System.in);
		try {
			System.out.println("날짜을 입력하세요 : yyyy-MM-dd");
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
	
//----------------------------------------------달력 출력----------------------------------------------------------------------------	
	public void CalendarPrint(Map<String, List<Event>> events) {
		List<Event> list = events.get(Client.name);

		try{
			Scanner scan = new Scanner(System.in);
			System.out.print("조회할 년도를 입력하세요 : ");
			int year = scan.nextInt();
			System.out.print("조회할 월을 입력하세요: ");
			int month = scan.nextInt();
			scan.nextLine();
			LocalDate of = LocalDate.of(year, month, 1);
			DayOfWeek dayOfWeek = of.getDayOfWeek();
			int firstweek = (dayOfWeek.getValue()%7)+1;
			//첫번쨰날의 요일   7123456 -> %7 -> 0123456(일:0 토:6) +1 -> 
			//일월화수목금토 : 1234567 로 바뀌게됨 

			/*
			 * Calendar ca1 = Calendar.getInstance(); ca1.set(year, month-1,1); int
			 * firstweek =ca1.get(Calendar.DAY_OF_WEEK);//첫째날의 요일 int lastday =
			 * Calendar의요일은 1234567(일월화수목금토) 로 이루어져있다.
			 * ca1.getActualMaximum(Calendar.DATE);
			 */
			LocalDate with = of.with(TemporalAdjusters.lastDayOfMonth());//이번달의 마지막 날짜
			int lastday = with.getDayOfMonth();
			System.out.println("\t"+year + "년 " + month + "월");
			System.out.printf("%-3c %-3c %-3c%-3c %-3c %-2c %-3c",'일','월','화','수','목','금','토');
			System.out.println();
			for(int i=1,day=1;day<=lastday;i++) {
				int count=0;
				if(list==null) {}//list에 아무것도들어있지않다면 for문을 무시함
				else {
					for (Event e : list) {//date는 연,월,일 추출이너무힘들어서 LocalDate 사용함
						int monthValue = e.date.getMonthValue();//리스트의요소에서 달을 출력
						int year2 = e.date.getYear();//리스트의요소에서 연을 출력
						int dayofMonth = e.date.getDayOfMonth();//리스트의요소에서 일을 출력
						if(monthValue==month && year==year2 && day==dayofMonth) {
							count++;
							//연,월,일이 입력한날짜과 같다면 count++; (루프가한번돌면 count는 초기화됨)
						}
					}
				}
				if(i < (firstweek)) {
					System.out.printf("%-4s"," ");
				}// i가 첫째날의 요일(0:일 6:토)이 될떄까지 간격을띄워줌
				else {
					if(count>0) {//이벤트가있다면 글씨의간격을 줄이고 (count)를 붙여줌
						System.out.printf("%-2d(%d)",day++,count);

					}
					else {//없다면 4개의간격으로 날짜를 출력함
						System.out.printf("%-4d",day++);
					}
				}
				if(i%7==0)System.out.println();
			}System.out.println();
		}
		catch(InputMismatchException e) {
			System.out.println("올바른 형식이아닙니다");
			return;
		}
	}
//--------------------------------------------이벤트 조회--------------------------------------------------------------------------------------
	public void View(Map<String, List<Event>> events) {
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
//--------------------------------------------------------이벤트 변경 --------------------------------------------
	public void Change(Map<String, List<Event>> events) {
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
				count++; //해당날짜의 이벤트 갯수
				newList.add(e);//새로운리스트에 추가	
			}
		}

		if(count==0) {System.out.println("해당 날짜는 변경할 이벤트가 없어요");return;}

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
			//Parse.StoTime(LocalDate d,String s)  
			//Parse클래스에서 String->LocalDate 와 예외처리를 다루고있음
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
	}
//-----------------------------------------------------이벤트 삭제 ----------------------------------------------------------
	public void Delete(Map<String, List<Event>> events) {
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
			return;}
		
		try {
			for (int i = 1; i <= count; i++) {
				System.out.println(i+"번 : "+delList.get(i-1));
			}
			System.out.println("삭제할 이벤트의 번호를 입력해주세요(안하려면 문자입력)");
			int num = scan.nextInt();
			System.out.println(delList.get(num-1)+" => !! 삭제완료 !!");
			list.remove(delList.get(num-1));
			events.put(Client.name, list);

		}catch(InputMismatchException e) {
			System.out.println("삭제하지않겠습니다");
			scan.next();
			return;
		}
	}
}
