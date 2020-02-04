package Calculator;

public class Location {
	String type; // want to go, been there, current place
	MyLinkedList city;
	
	public Location(String type, MyLinkedList city) {
		this.type = type;
		this.city = city;
	}
}
