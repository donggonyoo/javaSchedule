package javaFianal_project.controllerList;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javaFianal_project.Client;
import javaFianal_project.Event;

public class CalendarPrint {

	public CalendarPrint(Map<String, List<Event>> events) {
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
			int lastday = of.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();//이번달의 마지막요일
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
}
