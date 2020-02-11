package Calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Calculator {
	//TicketsInformation t1;
	//TicketsInformation t2;
	
	/*Calculator(TicketsInformation t1, TicketsInformation t2){
		this.t1 = t1;
		this.t2 = t2;
	}*/
	
	public List<TicketsInformation> collectingTickets(LinkedList<Users> usersList, String date) {
		if(usersList.size() < 2) {
			return null;
		}
		else {
			// container for departure cities
			
			Set<String> departureSet = new HashSet<>();
			
			// container for arrival cities
			
			Set<String> commonArrivalCities = new HashSet<>();
			Users firstUser = usersList.getFirst();
			if(firstUser.location.type == "desirePlaces") {
				commonArrivalCities.addAll(firstUser.location.city);
			}
			for(Users user : usersList) {
				Location location = user.location;
				// get the destination list
				if(location.type == "desirePlaces") {
					Set<String> tempSet = new HashSet<>();
					for(String cityName : location.city) {
						if(commonArrivalCities.contains(cityName)) {
							tempSet.add(cityName);
						}
						else {
							continue;
						}
					}
					commonArrivalCities.clear();
					commonArrivalCities.addAll(tempSet);
				}
				else if(location.type == "base") {
					for(String cityName : location.city) {
						if(!departureSet.contains(cityName)) {
							departureSet.add(cityName);
						}
						else {
							continue;
						}
					}
				}
			}
			List<String> departureCities = new LinkedList<String>(departureSet);
			List<String> arrivalCities = new LinkedList<String>(commonArrivalCities);
			
			List<TicketsInformation> ticketsList = new LinkedList<>(); 
			for(String departure : departureCities) {
				for(String arrival : arrivalCities) {
					// call the search function every time
					ticketsList.add(searchTickets(departure, arrival, date));
				}
			}
			return ticketsList;
		}
	}
	
	// connect to database
	public TicketsInformation searchTickets(String departure, String arrival, String date){
		return null;
	}
	
	// calculate the best price
	public static List<TicketsUnit> calculation(List<TicketsInformation> ticketsList){
		if(ticketsList == null) {
			return null;
		}
		// the container for the result
		List<TicketsUnit> res = new ArrayList<>();
		// The container for the destination
		Set<String> destinationsSet = new HashSet<>();
		// The container for the departure cities
		Set<String> departureSet = new HashSet<>();
		for(TicketsInformation t : ticketsList) {
			destinationsSet.add(t.destination);
			departureSet.add(t.departure);
		}
		
		Set<TicketsUnit> resultSet = new HashSet<>();
		Set<TicketsUnit> checkSet = new HashSet<>();
		
		// Still have to fix this shit!!! 2020-02-07   10:50 pm
		for(String arrival : destinationsSet) {
			for(String departure : departureSet) {
				//LinkedList<TicketsInformation> combineList = new LinkedList<>();
				for(TicketsInformation t1 : ticketsList) {
					if(t1.departure.equals(departure) && t1.destination.equals(arrival)) {
						//combineList.add(t1);
						for(TicketsInformation t2 : ticketsList) {
							if(t2.equals(t1)) {
								continue;
							}
							else {
								if(t2.destination.equals(arrival)) {
									//combineList.add(t2);
									TicketsUnit tu1 = new TicketsUnit(t1, t2, t1.price + t2.price);
									TicketsUnit tu2 = new TicketsUnit(t2, t1, t1.price + t2.price);
									boolean found = false;
									for(TicketsUnit t : checkSet) {
										if((tu1.t1.equals(t.t1) && tu1.t2.equals(t.t2)) || (tu1.t1.equals(t.t2) && tu1.t2.equals(t.t1))) {
											found = true;
											break;
										}
									}
									if(!found) {
										resultSet.add(tu1);
										checkSet.add(tu1);
										checkSet.add(tu2);
									}
									//combineList.removeLast();
								}
								else {
									continue;
								}
							}
						}
					}
					else {
						continue;
					}
				}
			}
		}
		res.addAll(resultSet);
		// Sort the units with the lower cost in front
		res.sort(new Comparator<TicketsUnit>() {
			public int compare(TicketsUnit t1, TicketsUnit t2) {
				return (int)t1.priceSum - (int)t2.priceSum;
			}
		});
		return res;
		
	}
	
	// reverse the linked list
	public static LinkedList<TicketsInformation> reverseLinkedList(LinkedList<TicketsInformation> list) {
		if(list == null || list.size() <= 1){
			return list;
		}
		else {
			LinkedList<TicketsInformation> res = new LinkedList<>();
			for(TicketsInformation t : list) {
				res.add(0, t);
			}
			return res;
		}
	}
	
	
	// calculate the sum of the tickets' prices
	public static double sumPrice(LinkedList<TicketsInformation> list) {
		double sum = 0;
		for(TicketsInformation t : list) {
			sum += t == null ? 0 : t.price;
		}
		return sum;
	}
	
	// print the ticket's information
	public static void printTicket(TicketsInformation t) {
		System.out.println(
			"Departure: " + t.departure
			+ "\nArrival: " + t.destination
			+ "\nDeparture Time: " + t.departureTime + " Arrival Time: " + t.arrivalTime
			+ "\nPrice: " + t.price
		);
		return;
	}
	
	public static void printTicketsUnit(TicketsUnit unit) {
		
		System.out.println("Ticket 1:");
		printTicket(unit.t1);
		System.out.println();
		
		System.out.println("Ticket 2:");
		printTicket(unit.t2);
		System.out.println();
		
		System.out.println("Totol Price: " + unit.priceSum);
		System.out.println();
	}
	
	public static void printList(List<TicketsUnit> list) {
		int id = 1;
		for(TicketsUnit unit : list) {
			System.out.println("Option " + id + ":");
			printTicketsUnit(unit);
			id ++;
		}
		return;
	}
	
	public static void main(String[] args) {
		TicketsInformation t1 = new TicketsInformation("New York", "London", "2020-05-01", "10:15", "23:15", "07:00", "British Airlines", 200, 0, "");
		TicketsInformation t2 = new TicketsInformation("Shenzhen", "London", "2020-05-01", "17:15", "21:15", "12:00", "China Airlines", 300, 0, "");
		TicketsInformation t3 = new TicketsInformation("New York", "Paris", "2020-05-01", "10:15", "23:15", "07:00", "France Airlines", 200, 0, "");
		TicketsInformation t4 = new TicketsInformation("Shenzhen", "Paris", "2020-05-01", "17:15", "21:15", "12:00", "China Airlines", 260, 0, "");
		List<TicketsInformation> rawTicketsList = new ArrayList<>();
		rawTicketsList.add(t1);
		rawTicketsList.add(t3);
		rawTicketsList.add(t2);
		rawTicketsList.add(t4);
		
		//testCombinedTickets.add(t1);
		//testCombinedTickets.add(t2);
		//printCombinedTickets(testCombinedTickets);
		List<TicketsUnit> list = calculation(rawTicketsList);
		//list.add(testCombinedTickets);
		printList(list);
		//printTicket(t1);
	}
}
