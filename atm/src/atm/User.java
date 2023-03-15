package atm;

import java.util.*;

public class User {
	
	private String name;
	private String id;
	private String password;
	
	private ArrayList<Account> accs;
	
	public User(String name, String id, String password) {
		this.name = name;
		this.id = id;
		this.password = password;
		this.accs = new ArrayList<Account>();
	}

	public User(String name, String id, String password, ArrayList<Account> accs) {
		this.name = name;
		this.id = id;
		this.password = password;
		this.accs = accs;
	}
	
	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getAccountSize() {
		return this.accs.size();
	}
	
	public void addAccount(Account account) {
		this.accs.add(account);
	}
	
	public Account getAccount(int index) {
		return this.accs.get(index);
	}
	
	public void deleteAccount(String accountNum) {
		int index = 0;
		for(Account account: this.accs) {
			if(account.getAccNum().equals(accountNum)) 
				break;
			
			index++;
		}
		
		if(index>=0 && index<this.accs.size())
			this.accs.remove(index);
	}
	
	public ArrayList<Account> getAccountList() {
		return (ArrayList<Account>) this.accs.clone();
	}
}