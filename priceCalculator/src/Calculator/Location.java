package Calculator;

import java.util.List;

public class Location {
	String type; // want to go, been there, current place
	List<String> city;
	
	public Location() {}
	
	public Location(String type, List<String> city) {
		this.type = type;
		this.city = city;
	}
}
