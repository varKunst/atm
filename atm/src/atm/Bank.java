package atm;

import java.util.*;

public class Bank {
	
	private UserManager um;
	private AccountManager am;
	
	private Scanner scanner;
	
	private String name;
	
	public Bank(String name) {
		this.name = name;
		
		this.scanner = new Scanner(System.in);
		this.um = new UserManager();
		this.am = new AccountManager();
	}

	// Banking 관련 메소드
	
	private void deleteAcc() {
		System.out.print("아이디: ");
		String id = this.scanner.next();
		System.out.print("비밀번호: ");
		String pw = this.scanner.next();

		int index = getIndexOfUser(id, pw);

		if(index!=-1) {
			System.out.print("계좌번호: ");
			String acc = this.scanner.next();

			this.am.deleteAccountByAcc(acc);
			
			User user = this.um.getList().get(index);
			int size = user.getAccs().size();
			for(int i=0; i<size; i++) {
				String compareAcc = user.getAccs().get(index).getAccount();
				
				if(acc.equals(compareAcc)) {
					this.um.getList().get(index).getAccs().remove(i);	
				}		
			}
		}
	}
	
	private void withdraw() {
		System.out.print("출금할 계좌: ");
		String acc = this.scanner.next();
		
		System.out.print("출금할 금액: ");
		int money = this.scanner.nextInt();
		
		if(money>0) {
			this.am.withdraw(acc, money);
		} else {
			System.out.println("출금에 실패했습니다.");
		}
	}
	
	private void deposit() {
		System.out.print("입금할 계좌: ");
		String acc = this.scanner.next();
		
		System.out.print("입금할 금액: ");
		int money = this.scanner.nextInt();
		
		if(money>0) {
			this.am.deposit(acc, money);
		} else {
			System.out.println("입금에 실패했습니다.");
		}
	}

	private void printAccInfo(Account acc) {
		System.out.printf("%s님 %d원\n", acc.getId(), acc.getMoney());
	}
	
	private void getAccInfo() {
		System.out.print("계좌번호: ");
		String acc = this.scanner.next();
		
		Account account = this.am.getAccountByAcc(acc);		
		printAccInfo(account);				
	}
	
	private boolean isAccDupl(String acc) {
		for(int i=0; i<this.am.getList().size(); i++) {
			String compareAcc = this.am.getList().get(i).getAccount();
			
			if(acc.equals(compareAcc))
				return true;
		}
		
		return false;
	}

	private void createAcc() {
		System.out.print("아이디: ");
		String id = this.scanner.next();
		System.out.print("비밀번호: ");
		String pw = this.scanner.next();
		
		int index = getIndexOfUser(id, pw);
		
		if(index!=-1) {
			if(this.um.getList().get(index).getAccs().size()<Account.LIMIT) {
				System.out.print("계좌번호: ");
				String acc = this.scanner.next();
				
				Account account = new Account(id, acc);
				
				if(!isAccDupl(acc)) {
					am.addAccount(account);					

					User user = this.um.getList().get(index);
					this.um.getList().get(index).getAccs().add(account);	
					
				} else
					System.out.println("계좌번호는 중복될 수 없습니다.");
				
			} else {
				System.out.println("계좌 보유 한도가 꽉 찼습니다.");
			}
		}
	}

	private void deleteUser() {
		System.out.print("아이디: ");
		String id = this.scanner.next();
		System.out.print("비밀번호: ");
		String pw = this.scanner.next();
		
		if(checkLogIn(id, pw)) {
			this.um.deleteUserById(id);
			System.out.println("계정 삭제가 완료되었습니다.");
		}
	}
	
	public int getIndexOfUser(String id, String password) {
		int size = this.um.getList().size();
		
		for(int i=0; i<size; i++) {
			String userId = this.um.getList().get(i).getId();
			String userPw = this.um.getList().get(i).getPassword();

			if(id.equals(userId) && password.equals(userPw))
				return i;
		}
		
		return -1;
	}
	
	private boolean checkLogIn(String id, String pw) {
		int size = this.um.getList().size();
		
		for(int i=0; i<size; i++) {
			String userId = this.um.getList().get(i).getId();
			String userPw = this.um.getList().get(i).getPassword();
			
			if(id.equals(userId) && pw.equals(userPw)) {
				return true;
			}
		}
		
		return false;
	}
	
	private void updatePassword() {
		System.out.print("아이디: ");
		String id = this.scanner.next();
		System.out.print("비밀번호: ");
		String pw = this.scanner.next();
		
		int index = getIndexOfUser(id, pw);
		
		if(index!=-1) {
			System.out.print("새로운 비밀번호: ");
			String name = this.um.getList().get(index).getName();
			String newPw = this.scanner.next();
			
			User user = new User(name, id, newPw);
			this.um.setUser(index, user);
			
			System.out.println("비밀번호가 변경되었습니다.");
		} else {
			System.out.println("아이디와 비밀번호를 다시 확인해주세요.");
		}
	}
	
	private void printUserInfo(User user) {
		System.out.printf("%s / %s\n", user.getName(), user.getId());
		
		int size = user.getAccs().size();
		
		for(int i=0; i<size; i++) {
			String acc = user.getAccs().get(i).getAccount();
			int money = user.getAccs().get(i).getMoney();
			
			System.out.printf("%s: %d원\n", acc, money);
		}
	}

	private void getUserInfo() {
		System.out.print("아이디: ");
		String id = this.scanner.next();
		System.out.print("비밀번호: ");
		String pw = this.scanner.next();
		
		if(checkLogIn(id, pw)) {
			User user = this.um.getUserById(id);		
			printUserInfo(user);				
		} else {
			System.out.println("아이디와 비밀번호를 다시 확인해주세요.");
		}
	}
	
	private boolean isDuplication(String id) {
		for(int i=0; i<this.um.getList().size(); i++) {
			String compareId = this.um.getList().get(i).getId();
			
			if(id.equals(compareId))
				return true;
		}
		
		return false;
	}
	
	private void createUser() {
		System.out.print("이름: ");
		String name = this.scanner.next();
		
		System.out.print("아이디: ");
		String id = this.scanner.next();
		
		if(isDuplication(id)) {
			System.out.println("이미 존재하는 아이디입니다.");
			return;
		}
		
		System.out.print("비밀번호: ");
		String password = this.scanner.next();
		
		User user = new User(name, id, password);
		um.addUser(user);
		
		System.out.println("계정이 생성되었습니다.");
	}
	
	private void printMenu() {
		System.out.println("1. 계정 생성");
		System.out.println("2. 계정 조회");
		System.out.println("3. 비밀번호 변경");
		System.out.println("4. 계정 삭제");
		System.out.println("5. 계좌 생성");
		System.out.println("6. 계좌 조회");
		System.out.println("7. 입금");
		System.out.println("8. 출금");
		System.out.println("9. 계좌 삭제");
	}
	
	void run() {
		while(true) {
			printMenu();
			int menu = this.scanner.nextInt();
			
			if(menu==1)			createUser();
			else if(menu==2)	getUserInfo();
			else if(menu==3)	updatePassword();
			else if(menu==4)	deleteUser();
			else if(menu==5)	createAcc();
			else if(menu==6)	getAccInfo();
			else if(menu==7)	deposit();
			else if(menu==8)	withdraw();
			else if(menu==9)	deleteAcc();
		}
	}
}