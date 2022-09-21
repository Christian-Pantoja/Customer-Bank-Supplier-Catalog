package application;

import java.util.Date;

interface CustomerAccount {
	public boolean isUserAvailable(String Username);
    public boolean login(String username, String password);
    public boolean logout();
    public String createCustomerAccount( String username, String password, 
    		String firsName, String lastName, String address, String phoneNumber, String customerAccount);
    public int purchase(String username, int accountNumber, Date ExpiredDate, int CSV, String nameHolder, double amount);
    public void upgradePremimum(String username);
    
    public String getEmail();

	public void setEmail(String email) ;

	public String getUsername();

	public void setUsername(String username) ;

	public String getAccountType();

	public void setAccountType(String accountType) ;

	public String getRole();

	public void setRole(String role) ;

	public String getFirstName() ;

	public void setFirstName(String firstName);

	public String getLastName() ;

	public void setLastName(String lastName);

	public String getAddress();

	public void setAddress(String address) ;

	public String getPhoneNumber();

	public void setPhoneNumber(String phoneNumber) ;
}
