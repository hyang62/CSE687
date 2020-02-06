package Calculator;

import java.util.LinkedList;

public class Location {
	String type; // want to go, been there, current place
	LinkedList<String> city;
	
	public Location(String type, LinkedList<String> city) {
		this.type = type;
		this.city = city;
	}
}
