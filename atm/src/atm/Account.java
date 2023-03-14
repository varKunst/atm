package atm;

public class Account {
	
	public static final int LIMIT = 3;
	
	private String id;
	private String account;
	private int money;
	
	public Account(String id, String account) {
		this.id = id;
		this.account = account;
	}
	
	public String getId() {
		return id;
	}
	
	public String getAccount() {
		return account;
	}
	
	public int getMoney() {
		return money;
	}
	
	public void setMoney(int money) {
		this.money = money;
	}
}
