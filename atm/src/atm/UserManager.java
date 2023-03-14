package atm;

import java.util.*;

public class UserManager {

	private static ArrayList<User> list = new ArrayList<User>();

	public void addUser(User user) {
		this.list.add(user);
	}
	
	public User getUser(int index) {
		User user = this.list.get(index);
		
		User reqObj = new User(user.getName(), user.getId(), user.getPassword());
		reqObj.setAccs(user.getAccs());
		return reqObj;
	}
	
	public User getUserById(String id) {
		int index = -1;

		int size = this.list.size();
		
		for(int i=0; i<size; i++) {
			String userId = this.list.get(i).getId();

			if(id.equals(userId))
				index = i;
			
		}
		
		return getUser(index);
	}
	
	public void setUser(int index, User user) {
		this.list.set(index, user);
	}
	
	public void deleteUser(int index) {
		this.list.remove(index);
	}
	
	public void deleteUserById(String id) {

		int index = -1;

		int size = this.list.size();
		
		for(int i=0; i<size; i++) {
			String userId = this.list.get(i).getId();

			if(id.equals(userId))
				index = i;
		}
		
		deleteUser(index);
	}
	
	public static ArrayList<User> getList() {
		return list;
	}
}