package Calculator;

import java.util.List;

public class Users {
	int id;
	String userName;
	String email;
	String phone;
	List<Location> locationList;
	
	public Users() {}
	public Users(int id, String userName, String email, String phone, List<Location> locationList) {
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.phone = phone;
		this.locationList = locationList;
	}
	
	public void addLocation(String locationType, String cityName) {
		switch(locationType) {
		case "base":
			addBaseLocation(cityName);
			break;
		case "beenThere":
			addBeenThereLocation(cityName);
			break;
		case "desirePlaces":
			addDesireLocation(cityName);
			break;
		default:
			break;
		}
	}
	
	public void deleteLocation(String locationType, String cityName) {
		switch(locationType) {
		case "base":
			deleteBaseLocation(cityName);
			break;
		case "beenThere":
			deleteBeenThereLocation(cityName);
			break;
		case "desirePlaces":
			deleteDesireLocation(cityName);
			break;
		default:
			break;
		}
	}
	
	private void addBaseLocation(String cityName) {
		locationList.get(0).city.add(cityName);
	}
	
	private void deleteBaseLocation(String cityName) {
		locationList.get(0).city.remove(cityName);
	}
	
	private void addBeenThereLocation(String cityName) {
		locationList.get(1).city.add(cityName);
	}
	
	private void deleteBeenThereLocation(String cityName) {
		locationList.get(1).city.remove(cityName);
	}
	
	private void addDesireLocation(String cityName) {
		locationList.get(2).city.add(cityName);
	}
	
	private void deleteDesireLocation(String cityName) {
		locationList.get(2).city.remove(cityName);
	}
}


