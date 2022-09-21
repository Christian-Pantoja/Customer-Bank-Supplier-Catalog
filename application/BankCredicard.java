package application;

import java.util.Date;

interface BankCredicard {
	public boolean verifyAccount();
	public boolean checkBalance(double chargedAmount);
	public boolean iskExpiredDate();
	public void charge(double amount);
	public double getBalance() ;
	public void setBalance(double balance);

	public int getAccountNumber() ;

	public void setAccountNumber(int accountNumber) ;


	public Date getExpiredDate() ;

	public void setExpiredDate(Date expiredDate);

	public String getNameHolder();

	public void setNameHolder(String nameHolder);
}
