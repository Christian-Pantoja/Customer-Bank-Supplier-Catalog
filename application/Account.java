package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Account implements CustomerAccount,SupplierAccount{
	private String email;
	private String username;
	private String password;
	private String accountType;
	private String role;
	private String firstName;
	private String lastName;
	private String address;
	private String phoneNumber;
	String fileAccount;
	private String creditCardNumber;
	
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	


	{
		this.email ="";
		this.username="";
		this.password="";
		this.accountType="";
		this.role="";
		this.firstName="";
		this.lastName="";
		this.address="";
		this.phoneNumber="";
		this.creditCardNumber = "00000000000000";
		
		this.fileAccount ="./src/application/files/account";
	}
	
	public Account(String email, String username, String password, String firstName,
			String lastName, String address, String phoneNumber) {
	
		this.email = email;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}
	
	public Account() {
	
	}


	public boolean isUserAvailable(String Username){ // method to check if username is taken
        String userInfo;
        String breakUserInfo[];
        try {
        	BufferedReader br = new BufferedReader(new FileReader(this.fileAccount));
        	userInfo = br.readLine();
        	
            while (userInfo != null) {
            	userInfo = br.readLine();
                breakUserInfo = userInfo.split("\t");
                
                //test
             	//System.out.println(breakUserInfo[1].toLowerCase() + " " +Username.toLowerCase());
                if(breakUserInfo[1].toLowerCase().equals(Username.toLowerCase())){
                    // Username is already taken
                	br.close();
                    return false;
                }
            }
            br.close();
        } catch (FileNotFoundException fnfex) {
            System.out.println("The file was not found");
            return true;
        }        catch(IOException e){
        	
            System.out.println("Error reading file");
            return true;
        }catch(NullPointerException e) {
        	return true;
        }
        //Username is valid
        return true;
    }
	
    
    
    public boolean login(String username, String password){ // method to check if username is taken
        String userInfo;
        String breakUserInfo[];
        try {
        	BufferedReader br = new BufferedReader(new FileReader(this.fileAccount));
        	userInfo = br.readLine();
        	
            while (userInfo != null) {
            	userInfo = br.readLine();
                breakUserInfo = userInfo.split("\t");
                
                //test
             	//System.out.println(breakUserInfo[1].toLowerCase() + " " +username.toLowerCase());
                if(breakUserInfo[1].trim().toLowerCase().equals(username.toLowerCase().trim())) {
                	//System.out.println(breakUserInfo[2] + " "+ password);
                	if( breakUserInfo[2].equals(password)){
                		System.out.println(breakUserInfo[2] + " "+ password);
	                	{
	                		this.email = breakUserInfo[0];
	                		this.username = breakUserInfo[1];
	                		this.accountType = breakUserInfo[3];
	                		this.role= breakUserInfo[4];
	                		this.firstName = breakUserInfo[5];
	                		this.lastName =  breakUserInfo[6];
	                		this.address =  breakUserInfo[7];
	                		this.phoneNumber = breakUserInfo[8];
	   
	                	}
	                	br.close();
	                    return true;
                	}else {
                		br.close();
                		return false;
                	}
                	
                }
            }
            br.close();
        } catch (FileNotFoundException fnfex) {
            System.out.println("The file was not found");
            System.exit(0);
        }        catch(IOException e){
            System.out.println("Error reading file");
        }catch(NullPointerException e) {
        	return false;
        }
   
        return false;
    } 
    
    public boolean logout() {
    	this.email ="";
		this.username="";
		this.password="";
		this.accountType="";
		this.role="";
		this.firstName="";
		this.lastName="";
		this.address="Non";
		this.phoneNumber="0000000";

    	return true;
    }
    
    public String createCustomerAccount( String username, String password, 
    		String firsName, String lastName, String address, String phoneNumber, String crediCardNumber) {
    	//if(email.trim() =="") return "Email is empty";
    	if(username.trim().isBlank()) return "Username is empty";
    	if(password.trim().isBlank()) return "Password is empty";
    	if(firsName.trim().isBlank()) return "First name is empty";
    	if(lastName.trim().isBlank()) return "Last name is empty";
    	if(creditCardNumber.isBlank()) return "Creditcard is empty";
    	if(address.isBlank()) return "Address is empty";
    	if(phoneNumber.isBlank()) return "Phone Number is empty";
    	if(this.isUserAvailable(username) == false) return "Username Was Used";
    	//if(this.isEmailAvailable(email)== false) return "Email Was Used";
    	
		this.username=username;
		this.password=password;
		this.accountType="regular";
		this.role="Customer";
		this.firstName=firsName;
		this.lastName=lastName;
		this.address=address;
		this.phoneNumber=phoneNumber;
		this.creditCardNumber = crediCardNumber;

		
		String outputString = "\n"+"email@notyet" + "\t" 
								+ this.username +"\t"
								+this.password +"\t"
								+this.accountType +"\t"
								+this.role+"\t"
								+this.firstName +"\t"
								+this.lastName+"\t"
								+this.address+"\t"
								+this.phoneNumber+"\t"
								+this.creditCardNumber;
		try {
			BufferedWriter wr = new BufferedWriter(new FileWriter(this.fileAccount, true));
			wr.append(outputString);
			wr.close();
			return "Create Account Successfully";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "Fail";
		}
    }
    
    
    public String createSupplierAccount(String username, String password) {
    	if(username.trim().isBlank()) return "Username is empty";
    	if(password.trim().isBlank()) return "Password is empty";
    	boolean isValid = this.isUserAvailable(username);
    	if(isValid == false) return "Username was used";
    	if(isValid == true) {
	    	this.email = username+"Supplier@company.com";
			this.username=username;
			this.password=password;
			this.accountType="supplier";
			this.role="Supplier";
			this.firstName="first";
			this.lastName="last";
			this.address="Address Supplier";
			this.phoneNumber="1111";

			
			String outputString = "\n"+this.email + "\t" 
									+ this.username +"\t"
									+this.password +"\t"
									+this.accountType +"\t"
									+this.role+"\t"
									+this.firstName +"\t"
									+this.lastName+"\t"
									+this.address+"\t"
									+this.phoneNumber +"\t"
									+this.creditCardNumber;
			try {
				BufferedWriter wr = new BufferedWriter(new FileWriter(this.fileAccount, true));
				wr.append(outputString);
				wr.close();
				return "Create Account Successfully";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return "False";
			}
    	}
		return "False";
    	
    }
    public int purchase(String username, int accountNumber, Date ExpiredDate, int CSV, String nameHolder, double amount){
    	//File file = new File(this.fileAccount);
    	int transaction = -1;
    	transaction =  TransactionBufferAndResponse.makeTransaction(username, accountNumber, ExpiredDate, CSV, nameHolder, amount);
    	return transaction;
    }
    
    public void upgradePremimum(String username) {
    	//File file = new File(this.fileAccount);
    	try {
    		
			BufferedReader br = new BufferedReader(new FileReader(this.fileAccount));
			
			String oldContent="";
			String accountInfo = br.readLine();
			oldContent = accountInfo;
			String breakAccountInfo[];
			
			while(accountInfo != null) {
				accountInfo = br.readLine();
				if(accountInfo == null) break;
				
				breakAccountInfo = accountInfo.split("\t");
				if(username.toLowerCase().equals(breakAccountInfo[1].trim())) {
					this.accountType= breakAccountInfo[3] = "Premium";
											    	
					//Concate String;
					accountInfo = breakAccountInfo[0];
					for(int i =1; i<breakAccountInfo.length;i++) {
						if(breakAccountInfo[i] != null)
							accountInfo = accountInfo + "\t" + breakAccountInfo[i] ;
					}
					
					
				}
				
				oldContent = oldContent + "\n" + accountInfo;
				
			}

			br.close();
			BufferedWriter bw = new BufferedWriter(new FileWriter(this.fileAccount));
			bw.write(oldContent);
			bw.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
    }
    	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
