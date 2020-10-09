package atmPackage;

import java.util.ArrayList;

public class Account {

	private String name, uuid;
	private User holder;
	private ArrayList<Transaction> transactions;
	
	public Account (String name, User holder, Bank theBank) {
		this.name = name;
		this.holder = holder;
		this.uuid = theBank.getNewAccountUUID();
		this.transactions = new ArrayList<Transaction>();
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public String getSummaryLine() {
		double balance = this.getBalance();
		if(balance >= 0) 
			return String.format("%s : $%.2f : %s", this.uuid, balance, this.name);
		else
			return String.format("%s : $(%.2d) : %s", this.uuid,  balance, this.name);
	}
	
	public double getBalance() {
		double balance = 0;
		for(Transaction t : transactions)
			balance += t.getAmount();
		return balance;
	}
	
	public void printTansHistory() {
		System.out.printf("\nTransaction history for account %s\n", this.uuid);
		for(int t=this.transactions.size()-1; t>=0; t--)
			System.out.println(this.transactions.get(t).getSummaryLine());
	}
	
	public void addTransaction(double amount, String memo) {
		Transaction newTransaction = new Transaction(amount, memo, this);
		this.transactions.add(newTransaction);
	}
}
