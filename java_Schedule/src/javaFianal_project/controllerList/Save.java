package javaFianal_project.controllerList;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

import javaFianal_project.Client;
import javaFianal_project.Event;

public class Save {

	public Save(Map<String, List<Event>> events) throws IOException {
		ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(Client.name+".ser"));
		writer.writeObject(events);
		writer.flush();
		writer.close();
	}
	
	

}
