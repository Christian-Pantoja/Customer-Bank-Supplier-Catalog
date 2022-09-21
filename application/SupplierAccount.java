package application;

import java.util.Date;

interface SupplierAccount extends Supplier{
	public boolean isUserAvailable(String Username);
    public boolean login(String username, String password);
    public boolean logout();

    public String createSupplierAccount(String username, String password);
 
   
    

	public String getUsername();

	public void setUsername(String username) ;

	public String getRole();

	public void setRole(String role) ;

	public String getFirstName() ;

	public void setFirstName(String firstName);

	public String getLastName() ;

	public void setLastName(String lastName);

	
}
