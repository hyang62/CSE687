package Calculator;

public class TicketsInformation {
	String departure;
	String destination;
	String date;
	String departureTime;
	String arrivalTime;
	String duration;
	String airlines;
	double price;
	int stops; // stops shouldn't be negative numbers
	String transferPlaces; // The transfer places are separated with "," , the number of "," plus one should equal number of stops
	
	public TicketsInformation() {}
	
	public TicketsInformation(String departure, String destination, String date, String departureTime, String arrivalTime, String duration, String airlines, double price, int stops, String transferPlaces) {
		this.departure = departure;
		this.destination = destination;
		this.date = date;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.duration = duration;
		this.airlines = airlines;
		this.price = price;
		this.stops = stops;
		this.transferPlaces = transferPlaces;
	}
}
