package atm;

import java.util.*;

public class Bank {
	
	private UserManager um;
	private AccountManager am;
	
	private Scanner scanner;
	private String brandName;
	
	private int log;
		
	public Bank(String name) {
		this.brandName = name;
		this.scanner = new Scanner(System.in);
		this.um = new UserManager();
		this.am = new AccountManager();
		this.log = -1;
	}

	private void printMainMenu() {
		System.out.println("1. 회원가입");
		System.out.println("2. 회원탈퇴");
		System.out.println("3. 계좌신청");
		System.out.println("4. 계좌철회");
		System.out.println("5. 로그인");
		System.out.println("6. 로그아웃");
		System.out.println("7. 회원정보관리");
		System.out.println("8. 뱅킹업무");
		System.out.println("0. 종료");
	}
	
	private int inputNumber() {
		int number = -1;
		
		System.out.print("input: ");
		String input = this.scanner.next();
		
		try {
			number = Integer.parseInt(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return number;
	}

	void run() {
		while(true) {
			printMainMenu();
			int menu = inputNumber();
			
			if(menu==1)			joinUser();
			else if(menu==2)	leaveUser();
			else if(menu==3)	createAccount();
			else if(menu==4)	deleteAccount();
			else if(menu==5)	logIn();
			else if(menu==6)	logOut();
			else if(menu==7)	manageUser();
			else if(menu==8)	taskBanking();
			else if(menu==0)	break;
		}
	}

	private void joinUser() {
		if(this.log==-1) {
			System.out.print("이름: ");
			String name = this.scanner.next();
			System.out.print("아이디: ");
			String id = this.scanner.next();
			System.out.print("비밀번호: ");
			String password = this.scanner.next();
			
			User user = new User(name, id, password);
			if(this.um.addUser(user)!=null) {
				System.out.println("회원으로 가입되었습니다.");
			} else {
				System.out.println("중복된 아이디가 존재합니다.");
			}
		} else {
			System.out.println("로그인 상태입니다.");
		}
	}

	private void leaveUser() {
		if(this.log!=-1) {
			System.out.print("아이디: ");
			String id = this.scanner.next();
			System.out.print("비밀번호: ");
			String pw = this.scanner.next();
			
			if(checkLogIn(id, pw)) {
				this.um.deleteUserById(id);
				
				for(int i=0; i<this.am.getListSize(); i++) 
					if(this.am.getAccount(i).getUserId().equals(id)) 
						this.am.deleteAccount(i);
				
				System.out.println("계정 탈퇴가 완료되었습니다.");
				this.log = -1;
			}
		} else {
			System.out.println("로그인 후 이용 가능합니다.");
		}
	}

	private void createAccount() {
		System.out.print("아이디: ");
		String id = this.scanner.next();
		System.out.print("비밀번호: ");
		String password = this.scanner.next();
		
		User user = this.um.getUserById(id);
		
		if(user!=null) {
			if(user.getPassword().equals(password)) {
				if(user.getAccountSize()<Account.LIMIT) {
					Account account = this.am.createAccount(new Account(id));
					this.um.setUser(user, account);
				} else {
					System.out.println("계좌를 더 이상 개설할 수 없습니다.");
				}
			} else {
				System.out.println("비밀번호가 일치하지 않습니다.");
			}
		} else {
			System.out.println("회원정보를 확인하세요.");
		}
	}

	private void deleteAccount() {
		System.out.print("아이디: ");
		String id = this.scanner.next();
		System.out.print("비밀번호: ");
		String password = this.scanner.next();
		
		User user = this.um.getUserById(id);
		
		if(user!=null) {
			if(user.getPassword().equals(password)) {
				if(user.getAccountSize()>0) {
					System.out.print("계좌번호: ");
					String accountNum = scanner.next();
					
					user.deleteAccount(accountNum);
					this.um.setUser(log, user);
					
					int index = am.indexOfByNum(accountNum);
					am.deleteAccount(index);
				}
			} else {
				System.out.println("비밀번호가 일치하지 않습니다.");
			}
		} else {
			System.out.println("회원정보를 확인하세요.");
		}
	}

	private void logIn() {
		System.out.print("아이디: ");
		String id = this.scanner.next();
		System.out.print("비밀번호: ");
		String password = this.scanner.next();
		
		if(checkLogIn(id,password)) {
			this.log = this.um.indexOfById(id);
		} else {
			System.out.println("회원 정보를 다시 확인해주세요.");
		}
	}
	
	private boolean checkLogIn(String id, String password) {
		int index = this.um.indexOfById(id);
		
		if(index!=-1) {
			User user = this.um.getUser(index);
			
			if(id.equals(user.getId()) && password.equals(user.getPassword())) {
				System.out.println("로그인 되었습니다.");
				return true;
			} 
		}
		
		return false;
	}
	
	private void logOut() {
		this.log = -1;
		System.out.println("로그아웃 되었습니다.");
	}
	
	private void manageUser() {
		if(this.log!=-1) {
			while(true) {
				printUserMenu();
				int menu = inputNumber();
				
				if(menu==1)			getUserInfo();
				else if(menu==2)	updatePassword();
				else if(menu==0)	break;
			}			
		} else {
			System.out.println("로그인 후 이용 가능합니다.");
		}
	}
	
	private void printUserMenu() {
		System.out.println("1. 정보 조회");
		System.out.println("2. 비밀번호 변경");
		System.out.println("0. 뒤로");
	}

	private void getUserInfo() {
		User user = this.um.getUser(this.log);		
		printUserInfo(user);			
		
	}

	private void printUserInfo(User user) {
		System.out.printf("%s / %s\n", user.getName(), user.getId());
		
		for(int i=0; i<user.getAccountSize(); i++) {
			Account account = user.getAccount(i);
			String accNum = account.getAccNum();
			int money = account.getMoney();
			
			System.out.printf("%s: %d원\n", accNum, money);
		}
	}

	private void updatePassword() {
		System.out.print("새로운 비밀번호: ");
		String newPw = this.scanner.next();
		
		User user = um.getUser(this.log);
		user.setPassword(newPw);
		
		this.um.setUser(this.log, user);
		
		System.out.println("비밀번호가 변경되었습니다.");
	}
	
	private void taskBanking() {
		if(this.log!=-1) {
			while(true) {
				printBankMenu();
				int menu = inputNumber();
				
				if(menu==1)			deposit();
				else if(menu==2)	withdraw();
				else if(menu==3)	transfer();
				else if(menu==4)	getAccInfo();
				else if(menu==0)	break;
			}			
		} else {
			System.out.println("로그인 후 이용 가능합니다.");
		}
	}
	
	private void printBankMenu() {
		System.out.println("1. 입금");
		System.out.println("2. 출금");
		System.out.println("3. 이체");
		System.out.println("4. 조회");
		System.out.println("0. 뒤로");
	}

	private void deposit() {
		System.out.print("입금할 계좌: ");
		String accNum = this.scanner.next();
		
		System.out.print("입금할 금액: ");
		int money = this.scanner.nextInt();
		
		if(money>0) {
			Account account = am.getAccountByNum(accNum);
			if(account!=null) {
				int index = am.indexOfByNum(accNum);
				account.setMoney(account.getMoney() + money);
				
				this.am.setAccount(index, account);
			}
			
		} else {
			System.out.println("입금에 실패했습니다.");
		}
	}
	
	private void withdraw() {
		System.out.print("출금할 계좌: ");
		String accNum = this.scanner.next();
		
		System.out.print("출금할 금액: ");
		int money = this.scanner.nextInt();
		
		if(money>0) {
			Account account = am.getAccountByNum(accNum);
			if(account!=null && account.getMoney()>=money) {
				int index = am.indexOfByNum(accNum);
				account.setMoney(account.getMoney() - money);
				
				this.am.setAccount(index, account);
				
			} else if(account!=null && account.getMoney()<money) {
				System.out.println("잔액이 부족합니다.");
			}
		} else {
			System.out.println("출금에 실패했습니다.");
		}
	}

	private void transfer() {
		System.out.print("이체하실 계좌: ");
		String toAccNum = this.scanner.next();
		
		System.out.print("이체하실 금액: ");
		int transMoney = this.scanner.nextInt();
		
		if(transMoney<=0) {
			System.out.println("0원 이하의 금액은 이체할 수 없습니다.");
			return;
		}

		System.out.print("출금하실 계좌: ");
		String fromAccNum = this.scanner.next();

		Account receive = this.am.getAccountByNum(toAccNum);
		Account send = this.am.getAccountByNum(fromAccNum);

		if(send.getMoney()<transMoney) {
			System.out.println("잔액이 부족합니다.");
			return;
		}

		receive.setMoney(receive.getMoney() + transMoney);
		send.setMoney(send.getMoney() - transMoney);

		int receiveIndex = this.am.indexOfByNum(toAccNum);		
		int sendIndex = this.am.indexOfByNum(fromAccNum);
		
		this.am.setAccount(receiveIndex, receive);
		this.am.setAccount(sendIndex, send);
	}
	
	private void getAccInfo() {
		System.out.print("계좌번호: ");
		String accNum = this.scanner.next();
		
		Account account = this.am.getAccountByNum(accNum);
		if(account!=null)
			printAccInfo(account);
		else
			System.out.println("존재하지 않는 계좌입니다.");
	}

	private void printAccInfo(Account account) {
		System.out.printf("%s님 %d원\n", account.getUserId(), 
				account.getMoney());
	}
}