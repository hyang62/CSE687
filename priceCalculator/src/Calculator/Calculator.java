package Calculator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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
					// Thread.sleep(1);
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

		String arguments = "C:\\Users\\mayu1\\OneDrive\\Documents\\2020Spring\\CSE687OOD\\TermProject\\cse687\\communicate_with_java_test.py"
				+ " " + departure + " " + arrival + " " + date;

		Process p = Runtime.getRuntime().exec("py "+arguments);
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		StringBuilder buffer = new StringBuilder();
		String line = null;
		//line = in.readLine();

		/*
		 * deal with the issue that py file can not be called.
		 */
		while ((line = in.readLine()) != null) {
			buffer.append(line);
			buffer.append('\n');
		}
		p.waitFor();
		String str = buffer.toString();
//		String str = "1 152.52 27.5 Priceline\r\n" + 
//				"2 176.18 0.0 OneTravel\r\n" + 
//				"2 176.18 0.0 CheapoAir\r\n" + 
//				"2 246.17 0.0 CheapTickets\r\n" + 
//				"2 246.17 0.0 Orbitz\r\n" + 
//				"2 246.18 0.0 Expedia\r\n" + 
//				"3 190.79 0.0 CheapTickets\r\n" + 
//				"3 190.79 0.0 Orbitz\r\n" + 
//				"3 190.79 0.0 Expedia\r\n" + 
//				"3 190.80005 28.80825 OneTravel\r\n" + 
//				"3 190.82 28.8 Priceline\r\n" + 
//				"4 190.80005 28.80825 OneTravel\r\n" + 
//				"5 197.18 0.0 OneTravel\r\n" + 
//				"5 197.18 0.0 CheapoAir\r\n" + 
//				"5 259.17 0.0 CheapTickets\r\n" + 
//				"5 259.17 0.0 Orbitz\r\n" + 
//				"5 259.17 0.0 Expedia\r\n" + 
//				"6 206.18 0.0 CheapoAir\r\n" + 
//				"6 210.18 9.176496 OneTravel\r\n" + 
//				"6 286.18 0.0 Expedia\r\n" + 
//				"6 286.18 0.0 Orbitz\r\n" + 
//				"6 286.18 0.0 CheapTickets\r\n" + 
//				"6 286.22 23.2 Priceline\r\n" + 
//				"7 214.2 0.0 FlightNetwork\r\n" + 
//				"7 224.2 23.1965 OneTravel\r\n" + 
//				"8 227.17 0.0 CheapoAir\r\n" + 
//				"8 227.18 0.0 OneTravel\r\n" + 
//				"8 299.17 0.0 Expedia\r\n" + 
//				"8 299.17 0.0 CheapTickets\r\n" + 
//				"8 299.17 0.0 Orbitz\r\n" + 
//				"9 228.78 0.0 Orbitz\r\n" + 
//				"9 228.78 0.0 CheapTickets\r\n" + 
//				"9 228.80005 28.81775 OneTravel\r\n" + 
//				"10 234.80005 28.79775 OneTravel\r\n" + 
//				"11 235.48001 11.9015 OneTravel\r\n" + 
//				"11 235.48001 11.9015 CheapoAir\r\n" + 
//				"12 251.8 28.8235 OneTravel\r\n" + 
//				"13 251.8 28.8235 OneTravel\r\n" + 
//				"14 257.18 0.0 OneTravel\r\n" + 
//				"14 261.18 5.351496 CheapoAir\r\n" + 
//				"14 267.21 23.2 Priceline\r\n" + 
//				"0 F9 2186 EWR ONT 2020-05-20T07:29:00-04:00 2020-05-20T10:32:00-07:00\r\n" + 
//				"0 F9 2186 ONT LAS 2020-05-20T11:22:00-07:00 2020-05-20T12:25:00-07:00\r\n" + 
//				"0 F9 2163 LAS LAX 2020-05-20T13:50:00-07:00 2020-05-20T15:20:00-07:00\r\n" + 
//				"1 F9 2186 EWR ONT 2020-05-20T07:29:00-04:00 2020-05-20T10:32:00-07:00\r\n" + 
//				"1 F9 2186 ONT LAS 2020-05-20T11:22:00-07:00 2020-05-20T12:25:00-07:00\r\n" + 
//				"1 F9 2163 LAS LAX 2020-05-20T13:50:00-07:00 2020-05-20T15:20:00-07:00\r\n" + 
//				"2 NK 917 LGA DFW 2020-05-20T13:49:00-04:00 2020-05-20T16:45:00-05:00\r\n" + 
//				"2 NK 869 DFW LAX 2020-05-20T17:37:00-05:00 2020-05-20T19:02:00-07:00\r\n" + 
//				"3 F9 259 EWR DEN 2020-05-20T05:30:00-04:00 2020-05-20T08:02:00-06:00\r\n" + 
//				"3 F9 405 DEN LAX 2020-05-20T12:36:00-06:00 2020-05-20T14:11:00-07:00\r\n" + 
//				"4 F9 259 EWR DEN 2020-05-20T05:30:00-04:00 2020-05-20T08:02:00-06:00\r\n" + 
//				"4 F9 407 DEN LAX 2020-05-20T22:01:00-06:00 2020-05-20T23:35:00-07:00\r\n" + 
//				"5 NK 703 EWR IAH 2020-05-20T12:00:00-04:00 2020-05-20T14:48:00-05:00\r\n" + 
//				"5 NK 327 IAH LAX 2020-05-20T15:48:00-05:00 2020-05-20T17:28:00-07:00\r\n" + 
//				"6 NK 1174 EWR BNA 2020-05-20T10:00:00-04:00 2020-05-20T11:30:00";
		String[] strArr = str.split("\n");
		Set<Integer> alreadyChecked = new HashSet<>();

		List<TicketsInformation> res = new ArrayList<>();

		/*
		 * get very detail information from the return string above
		 */
		for (String s : strArr) {
			if (s.length() <= 40) {
				StringBuilder sb = new StringBuilder();
				sb.append(s.charAt(0)).append(s.charAt(1));
				String singleTicket = sb.toString();
				int ticketId = Integer.parseInt(singleTicket.trim());
				if (!alreadyChecked.contains(ticketId)) {
					boolean start = false;
					boolean end = false;
					StringBuilder newSb = new StringBuilder();
					for (char c : s.toCharArray()) {
						if (c != ' ' && start) {
							newSb.append(c);
							end = true;
						} else if (c == ' ' && !start) {
							start = true;
						} else if (c == ' ' && end) {
							break;
						}
					}
					if (Double.parseDouble(newSb.toString()) != 0) {
						TicketsInformation newTicket = new TicketsInformation(departure, arrival, date, "", "", "", "",
								Double.parseDouble(newSb.toString()), 0, "");
						res.add(newTicket);
					}

					alreadyChecked.add(ticketId);
				}
			} else {
				continue;
			}
		}

		// System.out.println("Process exit value:" + exitCode);
		in.close();
		return res;
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
								if (t2.destination.equals(arrival) && !t2.departure.equals(departure)) {
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

	static HashMap<Integer, Users> userDict = new HashMap<Integer, Users>();

	static int userUniqueId = 0;

	static void addUser() {
		Scanner sc = new Scanner(System.in);

		System.out.println("Create a user name:");
		String userName = sc.next();
		System.out.println("Input your email address:");
		String email = sc.next();
		System.out.println("Input your phone number:");
		String phone = sc.next();

		List<Location> list = new ArrayList<Location>();
		Location base = new Location("base");
		Location beenThere = new Location("beenThere");
		Location desirePlaces = new Location("desirePlaces");
		list.add(base);
		list.add(beenThere);
		list.add(desirePlaces);

		String answer = "";
		while (!answer.equals("Yes") || !answer.equals("yes") || !answer.equals("No") || !answer.equals("no")) {
			System.out.println("Set your default location now? Please enter: 'Yes' or 'No'.\n");
			answer = sc.next();

			if (answer.equals("Yes") || answer.equals("yes")) {

				// Adding the base city.
				System.out.println("Enter a city name as your base: \n");
				String cityName = sc.nextLine();
				list.get(0).city.add(cityName);

				// Adding the cities that you have been to.

				boolean finish = sc.hasNext();
				while (!finish) {
					System.out.println("Enter a city name that you have been there:");

					cityName = sc.nextLine();

					list.get(1).city.add(cityName);

					boolean finishAdding = !sc.hasNext();

					while (!finishAdding) {
						System.out.println("Finish adding the cities you have been to? Please enter: 'Yes' or 'No'.\n");
						String finishAnswer = sc.nextLine();

						if (finishAnswer.equals("Yes") || finishAnswer.equals("yes")) {
							finish = true;
							break;
						} else if (finishAnswer.equals("No") || finishAnswer.equals("no")) {
							break;
						} else {
							System.out.println("The input is invalid! Please enter the proper answer.");
						}
					}

				}

				// Adding the cities that you want to go.
				finish = false;
				while (!finish) {

					System.out.println("Enter a city name that you want to go: \n");
					cityName = sc.next();
					list.get(2).city.add(cityName);

					while (true) {
						System.out.println("Finish adding the cities you want to go? Please enter: 'Yes' or 'No'.\n");
						String finishAnswer = sc.next();

						if (finishAnswer.equals("Yes") || finishAnswer.equals("yes")) {
							finish = true;
							break;
						} else if (finishAnswer.equals("No") || finishAnswer.equals("no")) {
							break;
						} else {
							System.out.println("The input is invalid! Please enter the proper answer.");
						}
					}

				}
			} else if (answer.equals("No") || answer.equals("no")) {
				break;
			} else {
				System.out.println("The input is invalid! Please enter the proper answer.");
			}
		}

		userUniqueId++;
		userDict.put(userUniqueId, new Users(userUniqueId, userName, email, phone, list));
		System.out.println("User adding finished.");
	};

	static void deleteUser() {
		System.out.println("Enter the id of the user you want to delete: ");
		Scanner sc = new Scanner(System.in);
		int id = sc.nextInt();

		while (true) {
			System.out.println("Are you sure you want to delete user: " + userDict.get(id).userName
					+ "? Please enter 'Yes' or 'No'.");
			String answer = sc.next();
			if (answer.equals("Yes") || answer.equals("yes")) {
				userDict.remove(id);
				System.out.println("The user has been deleted.");
				return;
			} else if (answer.equals("No") || answer.equals("no")) {
				System.out.println("Deleting cancelled.");
				return;
			} else {
				System.out.println("The input is invalid! Please enter the proper answer.");
			}
		}
	};

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

		
		Location l11 = new Location("base", Arrays.asList("NYC"));
		Location l12 = new Location("beenThere", Arrays.asList("Los Angeles", "Tokyo"));
		Location l13 = new Location("desirePlaces", Arrays.asList("LON", "SYD", "ZQN"));
		Location l14 = new Location("desirePlaces", Arrays.asList("SFO", "MCO", "DEN"));
		
		Location l21 = new Location("base", Arrays.asList("SZX"));
		Location l28 = new Location("base", Arrays.asList("LAX"));
		Location l22 = new Location("beenThere", Arrays.asList("Shanghai", "Osaka"));
		Location l23 = new Location("desirePlaces", Arrays.asList("LON", "MEX", "ZQN"));
		Location l24 = new Location("desirePlaces", Arrays.asList("MCO", "DEN", "ORD"));
		
		List<Location> ll1 = Arrays.asList(l11, l12, l14);
		List<Location> ll2 = Arrays.asList(l28, l22, l24);

		Users u1 = new Users(1, "Yu", "yma164@syr.edu", "3158985108", ll1);
		Users u2 = new Users(2, "Symphony", "symphony945@hotmail.com", "18028707040", ll2);
		
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
