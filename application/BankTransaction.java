package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//implements Bank interface
public class BankTransaction implements Bank{
	private Date transactionDate;
	private int confirmNumber;
	private boolean status;
	private int currentTransactionNumber;
	private double chargedAmount;
	private CreditCard credit;
	private String fileTransaction;
	private String fileTransactionCurrentNumber;
	private SimpleDateFormat dateFormat;
	private int CSV;
	TransactionBufferAndResponse transConnector;
	
	Thread thread;
	public BankTransaction(TransactionBufferAndResponse trans){
		//System.out.println("Create an object of BankTransaction");
		this.transConnector = trans;
		thread = new Thread(this, "Banking");
		thread.start();
	}
	public BankTransaction() {
		// TODO Auto-generated constructor stub
	}
	//initial variables
	{
		//get directory of transaction file and transactionCurrent number file
		this.fileTransaction = "./src/application/files/transaction";
		this.fileTransactionCurrentNumber = "./src/application/files/TransactionCurrentNumber";
		
		//initial value for: 
		this.transactionDate = Calendar.getInstance().getTime();
		this.confirmNumber = -1;
		this.status = false;
		this.chargedAmount = 0;
		this.credit = new CreditCard();
		this.CSV = -1;
		
		//get newest transaction number, this is a bad way to encypt the transaction number
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.fileTransactionCurrentNumber));
			
			String line = reader.readLine();
			this.currentTransactionNumber = Integer.parseInt(line);
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.currentTransactionNumber = -1;
		}
		
		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	}
	
	@Override
	public boolean verifyTransaction(int AcountNumber, Date ExpiredDate, String nameHolder) {
		// Check if credit card is valid
		CreditCard cret = new CreditCard( AcountNumber, ExpiredDate, nameHolder);
		if(cret.verifyAccount() == false) {
			System.out.println("\t\tBankingTransaction: Information does not match!");
			return false;
		}
		if(cret.iskExpiredDate() == true) {
			System.out.println("\t\tBankingTransaction: Account is expired!");
			return false;
		}
		
		return true;
	}

	@Override
	public int charge(int AcountNumber, Date ExpiredDate, String nameHolder, int CVs, double amount) {
		
		CreditCard cret = new CreditCard( AcountNumber, ExpiredDate, nameHolder, CVs);
		System.out.println("Banking:\tChargeFunction: Account number = " + AcountNumber);
		//if the transaction is valid(check creditcard expired day) 
		if(this.verifyTransaction(AcountNumber, ExpiredDate, nameHolder) == true) {
			//if the balance is valid, update information to the variables then save it to the file, return the confirmNumber, and charge on creditcard
			if(cret.checkBalance(amount) == true) {
				//test
				//System.out.println("in");
				
				//update information to the variables
				this.transactionDate = Calendar.getInstance().getTime();
				this.confirmNumber = ++this.currentTransactionNumber;
				this.status = true;
				this.chargedAmount = amount;
				this.credit = cret;
				
				//save transaction
				this.saveTransaction();
				
				//charge
				this.credit.charge(amount);
				
				//test
				//System.out.println("Charge Function: ");
				//System.out.println(this.transactionDate + " " + this.confirmNumber + " " + this.status +" " + this.chargedAmount + " " + this.credit.getBalance()
				//									+ " " + this.credit.getAccountNumber() + " " +this.credit.getNameHolder() + " " + this.credit.getExpiredDate());
				
				return this.currentTransactionNumber;
			}else {
				return -2;
			}
		}
		// TODO Auto-generated method stub
		return -1;
	}
	
	
	private void saveTransaction() {
		
		//concating the transaction information
		String saveString = "\n" + String.valueOf(this.confirmNumber)+"\t"
							+String.valueOf(this.credit.getAccountNumber())+"\t"
							+String.valueOf(dateFormat.format(this.credit.getExpiredDate()))+"\t"
							+this.credit.getNameHolder()+"\t"
							+String.valueOf(this.chargedAmount)+"\t"
							+String.valueOf(dateFormat.format(this.transactionDate))+"\t"
							+String.valueOf(this.status) ;
		
		//write transaction to the end of the file
		BufferedWriter writer;
		try {
			//write to transaction file
			writer = new BufferedWriter(new FileWriter(fileTransaction, true));
		    writer.append(saveString);
		    writer.close();
		    
		    //write to TransactionCurrentNumber
		    writer = new BufferedWriter(new FileWriter(fileTransactionCurrentNumber));
		    writer.append(String.valueOf(this.currentTransactionNumber));
		    writer.close();
		} catch (IOException ioe) {
		    System.err.format("IOException: %s%n", ioe);
		    
		}

			
			
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Banking is running");
		this.transConnector.receive();
		System.out.println("Banking:\t Start to making the transaction request");
		
		
		//charge
		int accNum = this.transConnector.getAccountNumber();
		Date expdDate;
		expdDate = this.transConnector.getExpiredDate();
		String nameHold = this.transConnector.getNameHolder();
		int CSVNum = this.transConnector.getCSV();
		double amountCharge = this.transConnector.getAmount();
		
		int transNum = this.charge(accNum, expdDate, nameHold, CSVNum, amountCharge);
		
		
		//set back transaction number for conectors. clear request.
		System.out.println("Banking:\tTransaction Number: " + transNum);
		this.transConnector.setTransactionNumber(transNum);
		this.transConnector.responseCharge(transNum);

	}

		
	
}
