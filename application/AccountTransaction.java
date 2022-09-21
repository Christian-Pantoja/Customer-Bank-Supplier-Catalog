package application;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountTransaction implements Runnable{
	private int accountNumber;
	private String nameHolder;
	private Date expiredDate;
	private int CVS;
	private int transactionNumber;
	private double amount;
	private String username;
	private Thread thread;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private TransactionBufferAndResponse transConnector;
	
	AccountTransaction(TransactionBufferAndResponse transConnector,String username, int accountNumber, Date ExpiredDate, int CSV, String nameHolder, double amount){
	
		this.username = username;
		this.accountNumber = accountNumber;
		this.expiredDate = ExpiredDate;
		this.CVS = CSV;
		this.nameHolder = nameHolder;
		this.amount = amount;
		this.transConnector = transConnector;
		
		this.transConnector = transConnector;
		
		thread = new Thread(this, "PurchasePremimum");
		thread.start();
	}
	
	
	
	
	public Thread getThread() {
		return thread;
	}




	public void setThread(Thread thread) {
		this.thread = thread;
	}




	public TransactionBufferAndResponse getTransConnector() {
		return transConnector;
	}




	public void setTransConnector(TransactionBufferAndResponse transConnector) {
		this.transConnector = transConnector;
	}




	public int getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}


	public String getNameHolder() {
		return nameHolder;
	}


	public void setNameHolder(String nameHolder) {
		this.nameHolder = nameHolder;
	}


	public int getTransactionNumber() {
		return transactionNumber;
	}


	public void setTransactionNumber(int transactionNumber) {
		this.transactionNumber = transactionNumber;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Account: purchasePremium - RUN");
		this.transactionNumber =  this.transConnector.requestCharge(this.nameHolder, this.expiredDate, this.accountNumber, this.CVS, this.transactionNumber, this.amount);
		//this.transactionNumber = this.transConnector.getTransactionNumber();
		this.transConnector.getTransaction();
		System.out.println("Account:\t" + this.transactionNumber);
	}
}
