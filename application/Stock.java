package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Stock {
	int itemID;
	String itemName;
	int numberOfItems;
	int available;
	int reserved;
	String pathStock;
	Double orginalPrice;
	{
		this.pathStock = "./src/application/files/stock";
		this.itemID = 0;
		this.itemName="";
		this.numberOfItems=0;
		this.available=0;
		this.reserved = 0;
	}
	public static void main(String agrs[]) {
		Stock newStock = new Stock();
		newStock.refillStock(1, 2);
	}
	
	public  void getItemAvailable() {
		
	}
	public  void UpdateShipStock(String ItemName, int numItems)
    {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(this.pathStock));
            String line  = reader.readLine();
            String OldContent = line;
            line = reader.readLine();

            String Name[];
            while(line != null)
            {
                Name = line.split("\t");

                if(Name[1].toLowerCase().equals(ItemName.toLowerCase().trim()))
                {
                   // System.out.println("in");
                    int res = Integer.parseInt(Name[4]);
                    res = res - numItems;
                    int inStock = Integer.parseInt(Name[2]);
                    inStock = inStock - 2;

                    OldContent = OldContent + "\n" + Name[0] + "\t" + Name[1] + "\t" +  String.valueOf(inStock) + "\t" + Name[3] + "\t" + String.valueOf(res) + "\t" + Name[5];

                }
                else{
                    OldContent = OldContent + "\n" + line;
                }
                line = reader.readLine();

            }
            reader.close();
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.pathStock));
            writer.write(OldContent);
            writer.close();
        }
        catch (IOException  e)
        {
            // TODO Auto-generated catch block
            System.out.print("Account is invalid");
        }

    }
	public void UpdateProccedStock(String itemName, int numItems)
    {
		
        try{
            BufferedReader reader = new BufferedReader(new FileReader(this.pathStock));
            String line  = reader.readLine();
            
            String OldContent = line;
            line = reader.readLine();

            String Name[];
            
            while (line != null)
            {
                Name = line.split("\t");
             
                if(Name[1].toLowerCase().equals(itemName.toLowerCase().trim())) {
                	
                	
                	int avail = Integer.parseInt(Name[3]);
                	avail = avail - numItems;
                	
                	int res = Integer.parseInt(Name[4]);
                	res = res + numItems;
                	
                    this.reserved = this.reserved + numItems;
                    OldContent = OldContent + "\n" + Name[0] + "\t" + Name[1] + "\t" +  Name[2] + "\t" + String.valueOf(avail)+ "\t" + String.valueOf(res) + "\t" + Name[5];
                }
                else{
                    OldContent = OldContent + "\n" + line;
                }
                line = reader.readLine();
            }
            reader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.pathStock));
            writer.write(OldContent);
            writer.close();
        }
        catch (IOException  e) 
        {
            // TODO Auto-generated catch block
            System.out.print("Account is invalid");
        }

    }
	
	public Stock getOneStock(String itemName) {
		Stock stock = new Stock();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.pathStock));
			String line = reader.readLine();
			String breakLine[];
			line = reader.readLine();
			
			//nt ID, String itemName,  int numberOfitems, boolean isAvailable, boolean isReseve
			while(line != null) {
				
				breakLine = line.split("\t");
				//System.out.println(breakLine[3] + "---"+ stringToBoolean(breakLine[3]));
				if( breakLine[1].toLowerCase().equals(itemName.toLowerCase())) {
					stock = new Stock(Integer.parseInt(breakLine[0]),
							breakLine[1],
							Integer.parseInt(breakLine[2]),
							Integer.parseInt(breakLine[3]),
							Integer.parseInt(breakLine[4]),
							Double.parseDouble(breakLine[5])
				
						);
					reader.close();
					break;
				}
			
				//System.out.println(breakLine[0] + " "  + breakLine[1]+ " "+ breakLine[2] + " " + stringToBoolean(breakLine[3]) + " "+stringToBoolean(breakLine[4]) + " " + breakLine[5]);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stock;
	}
	public void refillStock(int itemID, int numberItems) {

		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.pathStock));
			String oldline="";
			String line = reader.readLine();
			oldline = oldline + line;
			String breakLine[];
			line = reader.readLine();
			
			//nt ID, String itemName,  int numberOfitems, boolean isAvailable, boolean isReseve
		
			while(line != null) {
				
				breakLine = line.split("\t");
				//System.out.println(breakLine[3] + "---"+ stringToBoolean(breakLine[3]));
				
				if(Integer.parseInt(breakLine[0]) ==itemID) {
					int Avai = numberItems + Integer.parseInt(breakLine[3]);
					numberItems = numberItems + Integer.parseInt(breakLine[2]);
					
					line = breakLine[0] + "\t" + breakLine[1]+"\t"+String.valueOf(numberItems)
								+"\t"+String.valueOf(Avai) +"\t"+breakLine[4]+"\t"+breakLine[5];
				}
				oldline = oldline + "\n" + line;
				//System.out.println(breakLine[0] + " "  + breakLine[1]+ " "+ breakLine[2] + " " + stringToBoolean(breakLine[3]) + " "+stringToBoolean(breakLine[4]) + " " + breakLine[5]);
				line = reader.readLine();
				
			}
			
			reader.close();
			BufferedWriter wt = new BufferedWriter(new FileWriter(this.pathStock));
			wt.write(oldline);
			wt.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Stock[] getAllStocks() {

		Stock stocks[] = new Stock[1000];
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.pathStock));
			String line = reader.readLine();
			String breakLine[];
			line = reader.readLine();
			
			//nt ID, String itemName,  int numberOfitems, boolean isAvailable, boolean isReseve
			int i = 0;
			while(line != null) {
				
				breakLine = line.split("\t");
				//System.out.println(breakLine[3] + "---"+ stringToBoolean(breakLine[3]));
				
				stocks[i] = new Stock(Integer.parseInt(breakLine[0]),
									breakLine[1],
									Integer.parseInt(breakLine[2]),
									Integer.parseInt(breakLine[3]),
									Integer.parseInt(breakLine[4]),
									Double.parseDouble(breakLine[5])
						);
			
				//System.out.println(breakLine[0] + " "  + breakLine[1]+ " "+ breakLine[2] + " " + stringToBoolean(breakLine[3]) + " "+stringToBoolean(breakLine[4]) + " " + breakLine[5]);
				line = reader.readLine();
				i++;
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return stocks;
		
	}
	
    public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getPathStock() {
		return pathStock;
	}

	public void setPathStock(String pathStock) {
		this.pathStock = pathStock;
	}

	public Double getOrginalPrice() {
		return orginalPrice;
	}

	public void setOrginalPrice(Double orginalPrice) {
		this.orginalPrice = orginalPrice;
	}

	Stock(){
		
	}
	Stock(int ID, String itemName,  int numberOfitems, int available, int reserved, double orginalPrice){
		this.itemID = ID;
		this.itemName = itemName;
		this.numberOfItems = numberOfitems;
		this.available = available;
		this.reserved = reserved;
		this.orginalPrice = orginalPrice;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getNumberOfItems() {
		return numberOfItems;
	}
	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public int getReserved() {
		return reserved;
	}

	public void setReserved(int reserved) {
		this.reserved = reserved;
	}
	
	
	
}
