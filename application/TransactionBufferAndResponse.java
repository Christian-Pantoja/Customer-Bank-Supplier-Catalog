package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionBufferAndResponse{
	private String nameHolder;
	private Date expiredDate;
	private int accountNumber;
	private int CSV;
	private double amount;
	private int transactionNumber;
	private boolean request = false;
	private boolean response = false;
	private SimpleDateFormat dateFormat;
	synchronized public int getTransactionNumber() {
		return transactionNumber;
	}
	
	public void setTransactionNumber(int transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	{
		this.transactionNumber=-1;
		this.dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	}
	static int makeTransaction(String username, int accountNumber, Date ExpiredDate, int CSV, String nameHolder, double amount) {
		TransactionBufferAndResponse transConnector = new TransactionBufferAndResponse();
		BankTransaction bank = new BankTransaction(transConnector);
		AccountTransaction custTran;
		
		try {
			
			custTran = new AccountTransaction(transConnector,username , accountNumber, ExpiredDate, 
					CSV, nameHolder, amount);
			transConnector.getTransactionNumber();
			bank.thread.join();
			custTran.getThread().join();
		
		} catch ( InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transConnector.getTransaction();
	}
	
	synchronized int requestCharge( String nameHolder, Date expiredDate, int accountNumber, int CSV, int transactionNumber, double amount) {
		 System.out.println("Account:\t is requesting charge");
		
	   	  this.nameHolder = nameHolder;
	   	  this.expiredDate = expiredDate;
	   	  this.accountNumber = accountNumber;
	   	  this.CSV = CSV;
	   	  this.amount = amount;
	   	  this.request = true;
		 notify();
		//while not response, send request
		while(this.response == false) {
		      try {
		    	  System.out.println("------------------------------");
		    	  System.out.println("Account:\t is waiting for response\n");
		    	  
		    	  wait();
		    	  
		      } catch(InterruptedException e) {
		        System.out.println("InterruptedException caught");
		      }
		      System.out.println("------------------------------");
		      System.out.println("Account:\t Transaction number: " + this.transactionNumber);
		      return this.transactionNumber;
		}
		return this.transactionNumber;
		
	}
	synchronized int getTransaction() {
	      
	      //get response, clear response
	  this.request = false;
      this.response = false;
      notify();
      return this.transactionNumber;
	}
	synchronized void receive() {
		//if recieve request, return transaction status/value
		while(request == false) {
			
			      try {
			    	System.out.println("------------------------------");
			        System.out.println("Banking:\t is waiting for a transaction request"); 
			       // System.out.println(this.accountNumber);
			      //response for the request
			        wait();
			        

			      } catch(InterruptedException | NumberFormatException e) {
			        System.out.println("InterruptedException caught");
			      }
			      
		}
	}
	synchronized int responseCharge(int transNum) {
		
		 this.transactionNumber = transNum;
		  response = true;
	      this.request = false;
	      notify();
	      return this.transactionNumber;
	}

	synchronized public boolean isRequest() {
		return request;
	}

	synchronized public void setRequest(boolean request) {
		this.request = request;
	}

	synchronized public boolean isResponse() {
		return response;
	}

	public void setResponse(boolean response) {
		this.response = response;
	}
	synchronized public String getNameHolder() {
		return nameHolder;
	}

	synchronized public void setNameHolder(String nameHolder) {
		this.nameHolder = nameHolder;
	}

	synchronized public Date getExpiredDate() {
		return expiredDate;
	}

	synchronized public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	synchronized public int getAccountNumber() {
		return accountNumber;
	}

	synchronized public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	synchronized public int getCSV() {
		return CSV;
	}

	synchronized public void setCSV(int cSV) {
		CSV = cSV;
	}

	synchronized public double getAmount() {
		return amount;
	}

	synchronized public void setAmount(double amount) {
		this.amount = amount;
	}
}
