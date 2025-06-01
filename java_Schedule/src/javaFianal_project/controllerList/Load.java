package javaFianal_project.controllerList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javaFianal_project.Client;
import javaFianal_project.Event;

public class Load {
																	//파일존재X 시 발생
	
	public static Map<String, List<Event>> LoadEvent() throws FileNotFoundException, IOException, ClassNotFoundException {
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
	

}
