package Calculator;

import java.util.ArrayList;
import java.util.List;

public class Location {
	String type; // base, beenThere, desirePlaces
	List<String> city;
	
	public Location() {}
	
	public Location(String type) {
		this.type = type;
		this.city = new ArrayList<String>();
	}
	
	public Location(String type, List<String> city) {
		this.type = type;
		this.city = city;
	}
}
