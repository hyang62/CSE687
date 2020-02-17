package Calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Calculator {
	// TicketsInformation t1;
	// TicketsInformation t2;

	/*
	 * Calculator(TicketsInformation t1, TicketsInformation t2){ this.t1 = t1;
	 * this.t2 = t2; }
	 */

	public static List<TicketsInformation> collectingTickets(List<Users> usersList, String date)
			throws IOException, InterruptedException {
		if (usersList.size() < 2) {
			return null;
		} else {
			// container for departure cities

			Set<String> departureSet = new HashSet<>();

			// container for arrival cities

			Set<String> commonArrivalCities = new HashSet<>();
			Users firstUser = usersList.get(0);

			commonArrivalCities.addAll(firstUser.locationList.get(2).city);

			for (Users user : usersList) {
				Location location3 = user.locationList.get(2);
				// get the destination list

				Set<String> tempSet = new HashSet<>();
				for (String cityName : location3.city) {
					if (commonArrivalCities.contains(cityName)) {
						tempSet.add(cityName);
					} else {
						continue;
					}
				}
				commonArrivalCities.clear();
				commonArrivalCities.addAll(tempSet);

				Location location1 = user.locationList.get(0);
				for (String cityName : location1.city) {
					if (!departureSet.contains(cityName)) {
						departureSet.add(cityName);
					} else {
						continue;
					}
				}

			}
			List<String> departureCities = new LinkedList<String>(departureSet);
			List<String> arrivalCities = new LinkedList<String>(commonArrivalCities);

			List<TicketsInformation> ticketsList = new LinkedList<>();
			for (String departure : departureCities) {
				for (String arrival : arrivalCities) {
					// call the search function every time

					List<TicketsInformation> tempList = searchTickets(departure, arrival, date);

					ticketsList.addAll(tempList);
				}
			}
			return ticketsList;
		}
	}

	// connect to database
	public static List<TicketsInformation> searchTickets(String departure, String arrival, String date)
			throws IOException, InterruptedException {
		if (departure == null || arrival == null || date == null) {
			return null;
		}
//		switch(departure) {
//		case "New York":
//			if(arrival.equals("London")) {
//				return Arrays.asList(new TicketsInformation("New York", "London", "2020-05-01", "10:15", "23:15", "07:00", "British Airlines", 200, 0, ""));
//			}
//			else {
//				return Arrays.asList(new TicketsInformation("New York", "Paris", "2020-05-01", "10:15", "23:15", "07:00",
//						"France Airlines", 200, 0, ""));
//			}
//		case "Shenzhen":
//			if(arrival.equals("London")) {
//				return Arrays.asList(new TicketsInformation("Shenzhen", "London", "2020-05-01", "17:15", "21:15", "12:00",
//						"China Airlines", 300, 0, ""));
//			}
//			else {
//				return Arrays.asList(new TicketsInformation("Shenzhen", "Paris", "2020-05-01", "17:15", "21:15", "12:00",
//				"China Airlines", 260, 0, ""));
//			}
//		}
//		return null;
		String filePath = "C:\\Users\\mayu1\\OneDrive\\Documents\\2020Spring\\CSE687OOD\\TermProject\\cse687\\communivate_with_java_test.py";
		ProcessBuilder pb = new ProcessBuilder().command("py", "-u", filePath);
		Process p = pb.start();
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		StringBuilder buffer = new StringBuilder();
		String line = null;
		line = in.readLine();
		while ((line = in.readLine()) != null) {
			buffer.append(line);
			buffer.append('\n');
		}
		p.waitFor();
		String str = buffer.toString();

		// System.out.println("Process exit value:" + exitCode);
		in.close();
		return null;
	}

	// calculate the best price
	public static List<TicketsUnit> calculation(List<TicketsInformation> ticketsList) {
		if (ticketsList == null) {
			return null;
		}
		// the container for the result
		List<TicketsUnit> res = new ArrayList<>();
		// The container for the destination
		Set<String> destinationsSet = new HashSet<>();
		// The container for the departure cities
		Set<String> departureSet = new HashSet<>();
		for (TicketsInformation t : ticketsList) {
			destinationsSet.add(t.destination);
			departureSet.add(t.departure);
		}

		Set<TicketsUnit> resultSet = new HashSet<>();
		Set<TicketsUnit> checkSet = new HashSet<>();

		for (String arrival : destinationsSet) {
			for (String departure : departureSet) {
				// LinkedList<TicketsInformation> combineList = new LinkedList<>();
				for (TicketsInformation t1 : ticketsList) {
					if (t1.departure.equals(departure) && t1.destination.equals(arrival)) {
						// combineList.add(t1);
						for (TicketsInformation t2 : ticketsList) {
							if (t2.equals(t1)) {
								continue;
							} else {
								if (t2.destination.equals(arrival)) {
									// combineList.add(t2);
									TicketsUnit tu1 = new TicketsUnit(t1, t2, t1.price + t2.price);
									TicketsUnit tu2 = new TicketsUnit(t2, t1, t1.price + t2.price);
									boolean found = false;
									for (TicketsUnit t : checkSet) {
										if ((tu1.t1.equals(t.t1) && tu1.t2.equals(t.t2))
												|| (tu1.t1.equals(t.t2) && tu1.t2.equals(t.t1))) {
											found = true;
											break;
										}
									}
									if (!found) {
										resultSet.add(tu1);
										checkSet.add(tu1);
										checkSet.add(tu2);
									}
									// combineList.removeLast();
								} else {
									continue;
								}
							}
						}
					} else {
						continue;
					}
				}
			}
		}
		res.addAll(resultSet);
		// Sort the units with the lower cost in front
		res.sort(new Comparator<TicketsUnit>() {
			public int compare(TicketsUnit t1, TicketsUnit t2) {
				return (int) t1.priceSum - (int) t2.priceSum;
			}
		});
		return res;

	}

	// reverse the linked list
	public static LinkedList<TicketsInformation> reverseLinkedList(LinkedList<TicketsInformation> list) {
		if (list == null || list.size() <= 1) {
			return list;
		} else {
			LinkedList<TicketsInformation> res = new LinkedList<>();
			for (TicketsInformation t : list) {
				res.add(0, t);
			}
			return res;
		}
	}

	// calculate the sum of the tickets' prices
	public static double sumPrice(LinkedList<TicketsInformation> list) {
		double sum = 0;
		for (TicketsInformation t : list) {
			sum += t == null ? 0 : t.price;
		}
		return sum;
	}

	// print the ticket's information
	public static void printTicket(TicketsInformation t) {
		System.out.println("Departure: " + t.departure + "\nArrival: " + t.destination + "\nDeparture Time: "
				+ t.departureTime + " Arrival Time: " + t.arrivalTime + "\nPrice: " + t.price);
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
		for (TicketsUnit unit : list) {
			System.out.println("Option " + id + ":");
			printTicketsUnit(unit);
			id++;
		}
		return;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
//		TicketsInformation t1 = new TicketsInformation("New York", "London", "2020-05-01", "10:15", "23:15", "07:00",
//				"British Airlines", 200, 0, "");
//		TicketsInformation t2 = new TicketsInformation("Shenzhen", "London", "2020-05-01", "17:15", "21:15", "12:00",
//				"China Airlines", 300, 0, "");
//		TicketsInformation t3 = new TicketsInformation("New York", "Paris", "2020-05-01", "10:15", "23:15", "07:00",
//				"France Airlines", 200, 0, "");
//		TicketsInformation t4 = new TicketsInformation("Shenzhen", "Paris", "2020-05-01", "17:15", "21:15", "12:00",
//				"China Airlines", 260, 0, "");
//		List<TicketsInformation> rawTicketsList = new ArrayList<>();
//		rawTicketsList.add(t1);
//		rawTicketsList.add(t3);
//		rawTicketsList.add(t2);
//		rawTicketsList.add(t4);

		Location l11 = new Location("base", Arrays.asList("New York"));
		Location l12 = new Location("beenThere", Arrays.asList("Los Angeles", "Tokyo"));
		Location l13 = new Location("desirePlaces", Arrays.asList("London", "Paris"));

		Location l21 = new Location("base", Arrays.asList("Shenzhen"));
		Location l22 = new Location("beenThere", Arrays.asList("Shanghai", "Osaka"));
		Location l23 = new Location("desirePlaces", Arrays.asList("London", "Paris"));

		List<Location> ll1 = Arrays.asList(l11, l12, l13);
		List<Location> ll2 = Arrays.asList(l21, l22, l23);

		Users u1 = new Users(1, "Rui", "rtao@syr.edu", "3158985109", ll1);
		Users u2 = new Users(2, "Yu", "yma@syr.edu", "3158985108", ll2);

		List<TicketsInformation> raw = collectingTickets(Arrays.asList(u1, u2), "2020-05-10");

		// testCombinedTickets.add(t1);
		// testCombinedTickets.add(t2);
		// printCombinedTickets(testCombinedTickets);
		List<TicketsUnit> list = calculation(raw);
		// list.add(testCombinedTickets);
		printList(list);
		// printTicket(t1);
	}
}
