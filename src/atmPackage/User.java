package atmPackage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

	private String firstName, lastName, uuid;	//Unique Universal ID
	private byte pinHash[];
	private ArrayList<Account> accounts;
	
	public User(String firstName, String lastName, String pin, Bank theBank) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.uuid = theBank.getNewUserUUID();
		this.accounts = new ArrayList<Account>();
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error : No Such Algorithm Exception");
			e.printStackTrace();
			System.exit(1);
		}

		System.out.printf("New user %s %s with ID %s created.\n", firstName, lastName, this.uuid);
		
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void addAccount(Account acc) {
		this.accounts.add(acc);
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public boolean validatePin(String pin) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error : No Such Algorithm Exception");
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}
	
	public void printAccountSummary() {
		System.out.printf("\n\n%s's acccounts summary\n", this.firstName);
		for(int i=0; i<accounts.size(); i++)
			System.out.printf("%d %s\n", i+1, this.accounts.get(i).getSummaryLine());
		System.out.println();
	}
	
	public int numAccounts() {
		return this.accounts.size();
	}
	
	public void printAccTransHistory(int accIndex) {
		this.accounts.get(accIndex).printTansHistory();
	}
	
	public double getAccBalance(int accIndex) {
		return this.accounts.get(accIndex).getBalance();
	}
	
	public String getAccUUID(int accIndex) {
		return this.accounts.get(accIndex).getUUID();
	}

	public void addAccTransaction(int accIndex, double amount, String memo) {
		this.accounts.get(accIndex).addTransaction(amount, memo);
	}
}