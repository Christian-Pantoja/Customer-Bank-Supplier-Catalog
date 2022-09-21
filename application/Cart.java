package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Cart {
	int itemID;
	String itemName;
	String description;
	double price;
	int numberOfItems;
	static int numberOfTypeItems=0;
	private static String pathCart  ="./src/application/files/carts/cart_";

	
	{
		this.itemID =0;
		this.itemName="Unknown";
		this.description="Unknown";
		this.price=0;
		this.numberOfItems=0;

	}
	
	
	public static Cart[] loadItem(String username) {
		Cart itemList[] = new Cart[100];
		
		try {
	
			File file= new File(Cart.pathCart+username);
			
			String item ="";
			//System.out.println(Cart.pathCart+username);	
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				 item = reader.readLine();
				item = reader.readLine();
				int i = 0;
				while(item!=null) {
					String breakItem[] = item.split("\t");
					itemList[i] = new Cart();
					itemList[i].itemID = Integer.parseInt(breakItem[0]);
					itemList[i].itemName = breakItem[1];
					itemList[i].description = breakItem[2];
					itemList[i].price = Double.parseDouble(breakItem[3]);
					itemList[i].numberOfItems = Integer.parseInt(breakItem[4]);
					
					item = reader.readLine();
					i++;
					numberOfTypeItems++;
				}
				
				reader.close();
			}
			catch(FileNotFoundException e3) {
				System.out.println("Cart->LoadItem: Cart is empty");
					
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return itemList;
	}
	public static int getNumberOfTypeItems() {
		return numberOfTypeItems;
	}
	public static void setNumberOfTypeItems(int numberOfTypeItems) {
		Cart.numberOfTypeItems = numberOfTypeItems;
	}
	

	public void	addItem(String username, int ID, String itemName, String description, double price) {
		try {
			String header = "ID[0]\titemName[1]\tDescription[2]\tPrice[3]\tnumberOfItems[4]";
			String oldString = header;
			File file= new File(Cart.pathCart+username);
			String item ="";
			//System.out.println(Cart.pathCart+username);	
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				 item = reader.readLine();
				item = reader.readLine();
				
			
				boolean isIn = false;
				
				while(item!=null) {
					String breakItem[] = item.split("\t");
					if( breakItem[1].toLowerCase().equals(itemName.toLowerCase().trim())) {
					
						int numItem = Integer.parseInt(breakItem[4]);
						numItem = numItem + 1;
						
						item = breakItem[0]+"\t"+breakItem[1]+"\t"+breakItem[2]+"\t"+breakItem[3]+"\t"+String.valueOf(numItem);
						isIn = true;
					}
					oldString = oldString +"\n"+ item ;
					item = reader.readLine();
				}
				if(isIn == false) {
					
					item = String.valueOf(ID) +"\t"+itemName+"\t"+ description+"\t"+String.valueOf(price)+"\t"+"1";
					oldString = oldString +"\n"+ item;
				}
				reader.close();
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));;
				writer.write(oldString);
				writer.close();
			}
			catch(FileNotFoundException e3) {
				item = String.valueOf(ID) +"\t"+itemName+"\t"+ description+"\t"+String.valueOf(price)+"\t"+"1";
				oldString = oldString +"\n"+ item;
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));;
				writer.write(oldString);
				writer.close();
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	static boolean clearCart(String username, boolean isPaid) {
		//Load the file of the username,
		if(isPaid==true) {
			File file= new File(Cart.pathCart+username);
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				String header = "ID[0]\titemName[1]\tDescription[2]\tPrice[3]\tnumberOfItems[4]";
				writer.write(header);
				writer.close();
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return false;
		
	}
	
	public int getItemID() {
		return itemID;
	}


	public void setItemID(int itemID) {
		this.itemID = itemID;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	


	public int getNumberOfItems() {
		return numberOfItems;
	}


	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}


	public Cart() {
	
	}


	public Cart(int itemID, String itemName, String description, double regularPrice, double premiumPrice,
			int numberOfItems) {
		super();
		this.itemID = itemID;
		this.itemName = itemName;
		this.description = description;
		this.price = regularPrice;
		this.numberOfItems = numberOfItems;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
