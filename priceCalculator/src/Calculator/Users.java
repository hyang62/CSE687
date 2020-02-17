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
	
}


