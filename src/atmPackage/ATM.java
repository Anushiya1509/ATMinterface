package atmPackage;

import java.util.Scanner;

public class ATM {
	
	private static Scanner s = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("Enter Bank : ");
		Bank theBank = new Bank(s.nextLine());
		System.out.print("Enter your Firstname : ");
		String fname = s.nextLine();
		System.out.print("Enter your Lastname : ");
		String lname = s.nextLine();
		System.out.print("Enter your pin : ");
		String pin = s.nextLine();
		User aUser =  theBank.addUser(fname, lname, pin);
		Account newAccount = new Account("Checking", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		User curUser;
		while(true) {
			curUser = ATM.mainMenuPrompt(theBank);
			ATM.printUserMenu(curUser);
		}
	}
	
	public static User mainMenuPrompt(Bank theBank) {
		String userID;
		String pin;
		User authUser;
		
		do {
			System.out.printf("\n\nWelcome to %s\n\n",theBank.getName());
			System.out.println("Enter user ID : ");
			userID = s.nextLine();
			System.out.println("Enter pin : ");
			pin = s.nextLine();
			authUser = theBank.userLogin(userID, pin);
			if(authUser == null) {
				System.out.println("Error : Incorrect user ID / pin\nPlease try again.");
			}
		}while(authUser == null);
		return authUser;
	}
	
	public static void printUserMenu(User theUser) {
		theUser.printAccountSummary();
		int choice;
		do {
			System.out.printf("Welcome %s, what would you like to do?\n",theUser.getFirstName());
			System.out.println("1-Transaction history\n2-Withdraw\n3-Deposit\n4-Transfer\n5-Quit\n\nEnter your choice : ");
			choice = s.nextInt();
			if(choice<1 || choice>5)
				System.out.println("Invalid choice. Please choose 1 to 5");
		}while(choice<1 || choice>5);
		switch(choice) {
		case 1:
			ATM.showTansHistory(theUser);
			break;
		case 2:
			ATM.withdrawFunds(theUser);
			break;
		case 3:
			ATM.depositFunds(theUser);
			break;
		case 4:
			ATM.transferFunds(theUser);
			break;
		case 5:
			s.nextLine();
			break;
		}
		if(choice != 5)
			ATM.printUserMenu(theUser);
	}
	
	public static void showTansHistory(User theUser) {
		int theAccount;
		do {
			System.out.printf("Enter the number (1-%d) of the account whose transaction you wish to see : ",theUser.numAccounts());
			theAccount = s.nextInt()-1;
			if(theAccount<0 || theAccount>=theUser.numAccounts())
				System.out.println("Invalid account, Please try again.");
		}while(theAccount<0 || theAccount>=theUser.numAccounts());
		theUser.printAccTransHistory(theAccount);
	}

	public static void transferFunds(User theUser) {
		int fromAcc, toAcc;
		double amount, accBal;
		
		do {
			System.out.printf("Enter the number (1-%d) of the account from which you wish to tranfer fund : ", theUser.numAccounts());
			fromAcc = s.nextInt()-1;
			if(fromAcc<0 || fromAcc>=theUser.numAccounts())
				System.out.println("Invalid account, Please try again.");
		}while(fromAcc<0 || fromAcc>=theUser.numAccounts());
		
		accBal = theUser.getAccBalance(fromAcc);
		do {
			System.out.printf("Enter the number (1-%d) of the account to which you wish to tranfer fund : ", theUser.numAccounts());
			toAcc = s.nextInt()-1;
			if(toAcc<0 || toAcc>=theUser.numAccounts())
				System.out.println("Invalid account, Please try again.");
		}while(toAcc<0 || toAcc>=theUser.numAccounts());
		
		do {
			System.out.printf("Enter the amount to transfer (max $%.2f) : $", accBal);
			amount = s.nextDouble();
			if(amount<0)
				System.out.println("Amount must be greater than 0");
			else if(amount > accBal)
				System.out.println("Insufficient balance");
		}while(amount<0 || amount>accBal);
		
		theUser.addAccTransaction(fromAcc, -1*amount, String.format("Transfer to account %s", theUser.getAccUUID(toAcc)));
		theUser.addAccTransaction(toAcc, amount, String.format("Transferred from account %s", theUser.getAccUUID(fromAcc)));
	}
	
	public static void withdrawFunds(User theUser) {
		int fromAcc;
		double amount, accBal;
		String memo;
		
		do {
			System.out.printf("Enter the number (1-%d) of the account to which you wish to tranfer fund : ", theUser.numAccounts());
			fromAcc = s.nextInt()-1;
			if(fromAcc<0 || fromAcc>=theUser.numAccounts())
				System.out.println("Invalid account, Please try again.");
		}while(fromAcc<0 || fromAcc>=theUser.numAccounts());
		
		accBal = theUser.getAccBalance(fromAcc);
		
		do {
			System.out.printf("Enter the amount to withdraw (max $%.2f) : $", accBal);
			amount = s.nextDouble();
			if(amount<0)
				System.out.println("Amount must be greater than 0");
			else if(amount > accBal)
				System.out.println("Insufficient balance");
		}while(amount<0 || amount>accBal);
		
		s.nextLine();
		System.out.println("Enter a memo : ");
		memo = s.nextLine();
		
		theUser.addAccTransaction(fromAcc, -1*amount, memo);
	}
	
	public static void depositFunds(User theUser) {
		int toAcc;
		double amount, accBal;
		String memo;
		
		do {
			System.out.printf("Enter the number (1-%d) of the account to which you wish to tranfer fund : ", theUser.numAccounts());
			toAcc = s.nextInt()-1;
			if(toAcc<0 || toAcc>=theUser.numAccounts())
				System.out.println("Invalid account, Please try again.");
		}while(toAcc<0 || toAcc>=theUser.numAccounts());
		
		accBal = theUser.getAccBalance(toAcc);
		
		do {
			System.out.printf("Enter the amount to transfer (min $1) : $", accBal);
			amount = s.nextDouble();
			if(amount<0)
				System.out.println("Amount must be greater than 0");
			}while(amount<0);
		
		s.nextLine();
		System.out.println("Enter a memo : ");
		memo = s.nextLine();
		
		theUser.addAccTransaction(toAcc, amount, memo);
	}
}
