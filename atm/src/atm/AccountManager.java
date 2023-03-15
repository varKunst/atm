package atm;

import java.util.*;

public class AccountManager {

	private static ArrayList<Account> list = new ArrayList<Account>();

	public Account createAccount(Account account) {
		String accountNum = accNumGenerator();
		account.setAccNum(accountNum);
		list.add(account);
		return account;
	}
	
	public void loadAcount(Account account) {
		list.add(account);
	}
	
	public Account getAccount(int index) {
		Account account = list.get(index);		
		Account reqObj = new Account(account.getUserId(), account.getAccNum(), account.getMoney());

		return reqObj;
	}
	
	public Account getAccountByNum(String accountNum) {
		Account account = null;
		
		for(Account object: list) {
			if(object.getAccNum().equals(accountNum))
				account = object;
		}
		
		return account;
	}

	public int indexOfByNum(String accNum) {
		int index = -1;
		for(Account account: list) {
			if(account.getAccNum().equals(accNum))
				index = list.indexOf(account);
		}
		
		return index;
	}

	public int getListSize() {
		return list.size();
	}
	
	public void setAccount(int index, Account account) {
		list.set(index, account);
	}
	
	public void deleteAccount(int index) {
		this.list.remove(index);
	}
	
	private String accNumGenerator() {
		String num = "";
		
		Random ran = new Random();
		
		while(true) {
			int first = ran.nextInt(8999) + 1000;
			int second = ran.nextInt(8999) + 1000;
			
			num = first + "-" + second;
			
			Account account = getAccountByNum(num);
			
			if(account==null)
				break;
		}
		
		return num;
	}
}