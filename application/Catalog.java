package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Catalog {
	
	int itemID;
	String itemname;
	String description;
	double regularPrice;
	double premiumPrice;
	private static String pathCatalog = "./src/application/files/catalog";
	
	public static int totalItem;
	
	{
		Catalog.totalItem = 0;
		this.itemID = 0;
		this.itemname ="Unkown";
		this.description="No description";
		this.regularPrice = 0;
		this.premiumPrice = 0;
		
	//	Catalog.pathCatalog 
	}
	
	
	
	
	
	public static Catalog[] loadCatalog() {
		Catalog catalogList[] = new Catalog[100];
		//System.out.println(Catalog.pathCatalog);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(Catalog.pathCatalog));
			String line = reader.readLine();
			String item[];
			line = reader.readLine();
			
			int i = 0;
			while(line != null) {
				item = line.split("\t");
				int itemID = Integer.parseInt(item[0]);
				String itemname = item[1];
				String description = item[2];
				double regularPrice = Double.parseDouble(item[3]);
				double premiumPrice = Double.parseDouble(item[4]);
				catalogList[i] = new Catalog(itemID, itemname, description, regularPrice, premiumPrice);
				totalItem = i;
				i++;
				line = reader.readLine();
			}
			
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return catalogList;
	}
	
	
	public Catalog() {
		
	}
	public Catalog(int itemID, String itemname, String description, double regularPrice, double premiumPrice) {
		super();
		this.itemID = itemID;
		this.itemname = itemname;
		this.description = description;
		this.regularPrice = regularPrice;
		this.premiumPrice = premiumPrice;
	}
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getRegularPrice() {
		return regularPrice;
	}
	public void setRegularPrice(double regularPrice) {
		this.regularPrice = regularPrice;
	}
	public double getPremiumPrice() {
		return premiumPrice;
	}
	public void setPremiumPrice(double premiumPrice) {
		this.premiumPrice = premiumPrice;
	}
	//Select Iterm Usercase
	public String[] requestCatalog(int customerID) {
		return null;
	}
	public static int getTotalItem() {
		return totalItem;
	}
	public static void setTotalItem(int totalItem) {
		Catalog.totalItem = totalItem;
	}
	public static void main(String agrs[]) {
		Catalog.loadCatalog();
	}

}
