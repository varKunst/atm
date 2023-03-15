package atm;

import java.util.*;

public class UserManager {

	private static ArrayList<User> list = new ArrayList<User>();

	public User addUser(User user) {
		
		User check = getUserById(user.getId());
		if(check==null) {
			list.add(user);
			return user;
		}

		return null;
	}
	
	public User getUser(int index) {
		User user = this.list.get(index);
		
		User reqObj = 
				new User(user.getName(), user.getId(), 
						user.getPassword(), user.getAccountList());
		return reqObj;
	}
	
	public User getUserById(String id) {
		User user = null;

		int index = indexOfById(id);
		if(index!=-1)
			user = getUser(index);
		
		return user;
	}
	
	public int indexOfById(String id) {
		int index = -1;
		for(User user: list) {
			if(user.getId().equals(id))
				index = list.indexOf(user);
		}
		
		return index;
	}
	
	public void setUser(int index, User user) {
		this.list.set(index, user);
	}

	public void setUser(User user, Account account) {
		int index = indexOfById(user.getId());
		
		list.get(index).addAccount(account);
	}
	
	public void deleteUser(int index) {
		this.list.remove(index);
	}
	
	public void deleteUserById(String id) {
		int index = indexOfById(id);
		
		if(index!=-1)
			deleteUser(index);
	}
}