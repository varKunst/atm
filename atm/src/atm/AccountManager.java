package atm;

import java.util.*;

public class AccountManager {

	private static ArrayList<Account> list = new ArrayList<Account>();

	public void addAccount(Account acc) {
		this.list.add(acc);
	}
	
	public Account getAccount(int index) {
		Account acc = this.list.get(index);
		
		Account reqObj = new Account(acc.getId(), acc.getAccount());
		reqObj.setMoney(acc.getMoney());
		
		return reqObj;
	}
	
	public Account getAccountByAcc(String acc) {
		int index = -1;

		int size = this.list.size();
		
		for(int i=0; i<size; i++) {
			String userAcc = this.list.get(i).getAccount();

			if(acc.equals(userAcc))
				index = i;
		}
		
		if(index!=-1)
			return getAccount(index);
		else
			return new Account("존재하지 않는 계좌", "없습니다");
	}
	
	public void deposit(String account, int money) {
		int size = this.list.size();
		
		for(int i=0; i<size; i++) {
			String userAcc = this.list.get(i).getAccount();
			int userMoney = this.list.get(i).getMoney();
			
			if(account.equals(userAcc)) {
				userMoney += money;
				this.list.get(i).setMoney(userMoney);
			}
		}
	}

	public void withdraw(String account, int money) {
		int size = this.list.size();
		
		for(int i=0; i<size; i++) {
			String userAcc = this.list.get(i).getAccount();
			int userMoney = this.list.get(i).getMoney();
			
			if(account.equals(userAcc) && userMoney>=money) {
				userMoney -= money;
				this.list.get(i).setMoney(userMoney);
			}
		}
	}
	
	public void deleteAccount(int index) {
		this.list.remove(index);
	}
	
	public void deleteAccountByAcc(String acc) {

		int index = -1;

		int size = this.list.size();
		
		for(int i=0; i<size; i++) {
			String userAcc = this.list.get(i).getAccount();

			if(acc.equals(userAcc))
				index = i;
		}
		
		deleteAccount(index);
	}
	
	public static ArrayList<Account> getList() {
		return list;
	}
}