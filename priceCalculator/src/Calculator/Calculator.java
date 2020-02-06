package Calculator;

import java.util.ArrayList;
import java.util.Collections;
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
	public List<List<TicketsInformation>> calculation(List<TicketsInformation> ticketsList){
		// the container for the result
		List<List<TicketsInformation>> res = new LinkedList<>();
		// The container for the destination
		Set<String> destinationsSet = new HashSet<>();
		// The container for the departure cities
		Set<String> departureSet = new HashSet<>();
		for(TicketsInformation t : ticketsList) {
			destinationsSet.add(t.destination);
			departureSet.add(t.departure);
		}
		
		String departureCity = ticketsList.get(0).departure;
		for(String arrival : destinationsSet) {
			Set<String> checkSet = new HashSet<>();
			for(String departure : departureSet) {
				LinkedList<TicketsInformation> combineList = new LinkedList<>();
				for(TicketsInformation t1 : ticketsList) {
					if(t1.equals(departure)) {
						combineList.add(t1);
						for(TicketsInformation t2 : ticketsList) {
							if(checkSet.add(t1.departure + t2.departure) && checkSet.add(t2.departure + t1.departure)) {
								combineList.add(t2);
								res.add(combineList);
								combineList.removeLast();
							}
							else {
								continue;
							}
						}
					}
					else {
						continue;
					}
				}
			}
		}
		
		return res;
	}
	
	public double sumPrice(List<TicketsInformation> combineList) {
		double sum = 0;
		for(TicketsInformation t : combineList) {
			sum += t == null ? 0 : t.price;
		}
		return sum;
	}
}
