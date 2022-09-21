package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Order implements SupplierOrder, CustomerInvoice, CustomerOrder{
	private int OrderID;
	private String CustomerID;
	private String customerMembership;
	private String CustomerName;
	private Date OrderDate;
	private String Items[]; //itermName price
	private double TotalAmount;
	private Date PaymentDate;
	private String PaymentName;
	private int PaymentAccountNumber;
	private String PaymentMethod;
	private String pathOrder;
	private String pathorderCurrentNumber;
	private SimpleDateFormat dateFormat;
	
	
	{
		this.OrderID = 0;
		this.CustomerID = "";
		this.customerMembership = "Regular";
		this.CustomerName = "";
		this.Items = new String[10000];
		this.TotalAmount = 0;
		this.PaymentAccountNumber = 0;
		this.PaymentMethod = "Credit Card";
		this.pathOrder = "./src/application/files/order";
		this.pathorderCurrentNumber = "./src/application/files/orderCurrentNumber";
		
		this.dateFormat = new SimpleDateFormat("MM/dd/yyy");
		
		try {
			this.PaymentDate = this.dateFormat.parse("01/01/1711");
			this.OrderDate = this.dateFormat.parse("01/01/1711");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Order() {
		
	}
	
	
	public Order(int orderID, String customerID, String customerMembership, String customerName, Date orderDate,
			String[] items, double totalAmount, Date paymentDate, String paymentName, int paymentAccountNumber,
			String paymentMethod) {
		super();
		OrderID = orderID;
		CustomerID = customerID;
		this.customerMembership = customerMembership;
		CustomerName = customerName;
		OrderDate = orderDate;
		Items = items;
		TotalAmount = totalAmount;
		PaymentDate = paymentDate;
		PaymentName = paymentName;
		PaymentAccountNumber = paymentAccountNumber;
		PaymentMethod = paymentMethod;
	}

	
	public int makeOrder(String customerID, String customerMembership, String customerName,
			String[] items, int numberofItems, double totalAmount, String paymentNameHolder, int paymentAccountNumber) {
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.pathorderCurrentNumber));
			int orderIDint;
			orderIDint = Integer.parseInt(reader.readLine()) + 1;
			reader.close();
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.pathorderCurrentNumber));
			writer.write(String.valueOf(orderIDint));
			writer.close();
			
			String orderID = String.valueOf(orderIDint);
			String customerIDStr = String.valueOf(customerID);
			//CustomerName
			//customerMembership
			String orderDate = this.dateFormat.format(Calendar.getInstance().getTime());
			String payDate = orderDate;
			//paymentNameHolder
			String accNumber = String.valueOf(paymentAccountNumber);
			String paymentMethod = "Credit Card";
			String payAmount = String.valueOf(totalAmount);
			String status ="ordered";
			String numItems = String.valueOf(numberofItems);
			
			String orderInfo = "\n"
								+ orderID + "\t"
								+ customerIDStr + "\t"
								+ customerName+"\t"
								+ customerMembership +"\t"
								+ orderDate + "\t" 
								+ payDate + "\t"
								+ customerName+"\t"
								+ accNumber + "\t"
								+ paymentMethod + "\t"
								+ payAmount + "\t"
								+ status + "\t"
								+ numItems ;
			String itemStr = "\n";
			for(int i=0; i<numberofItems; i++) {
				if(items[i] == null)break;
				itemStr = itemStr + items[i] + "\t";
			}
			writer = new BufferedWriter(new FileWriter(this.pathOrder, true));
			writer.append(orderInfo);
			writer.append(itemStr);
			writer.close();
			
			return orderIDint;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}
	@Override
	public String[][] requestAllOrders(String CustomerID) {
		// TODO Auto-generated method stub
		//OrderID	CustomerID	CustomerName CustomerMemberShip	OrderDate	PaymentDate	PaymentName	PaymentAccountNumber	PaymentMethod
		//Item1Name,price	Item2Name,price	Item3,price
		String orderInfo;
		String breakOrderInfo[] = new String[30];
		String orderItems;
		String order[][] = new String[100000][30];
		int i=0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.pathOrder));
			orderInfo= reader.readLine();
			orderItems = reader.readLine();
			while(orderInfo != null) {
				
				breakOrderInfo = orderInfo.split("\t");
				//System.out.println(breakOrderInfo[1].toLowerCase() +"=="+ CustomerID.toLowerCase());
				if(breakOrderInfo[1].toLowerCase().equals(CustomerID.toLowerCase())) {
					order[i++] = breakOrderInfo;
					order[i++] = orderItems.split("\t");
				}
				
				orderInfo= reader.readLine();
				orderItems = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
		
		return order;
	}

	public boolean isFirstOrderInyear(String CustomerID) {
		// TODO Auto-generated method stub
		//OrderID	CustomerID	CustomerName CustomerMemberShip	OrderDate	PaymentDate	PaymentName	PaymentAccountNumber	PaymentMethod
		//Item1Name,price	Item2Name,price	Item3,price
		String orderInfo;
		String breakOrderInfo[] = new String[30];

		try {
			String orderItems;
			BufferedReader reader = new BufferedReader(new FileReader(this.pathOrder));
			orderInfo= reader.readLine();
			orderItems = reader.readLine();
			orderInfo= reader.readLine();
			orderItems = reader.readLine();
			
			String ordDate ="01/01/1771";
			while(orderInfo != null) {
				breakOrderInfo = orderInfo.split("\t");
				
				if(CustomerID.toLowerCase().trim().equals(breakOrderInfo[1].toLowerCase())) {
					
					ordDate = breakOrderInfo[4];
				}

				orderInfo= reader.readLine();
				orderItems = reader.readLine();
			}
			reader.close();
			
			
			try {
				Calendar calendar  = Calendar.getInstance();
				calendar.add(Calendar.YEAR, -1);
				Date expireDate = calendar.getTime();
				
				//System.out.println(expireDate);
				
				Date orderDate = this.dateFormat.parse(ordDate);
				//System.out.println(orderDate);
				if(orderDate.before(expireDate)) {
					return true;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public String[][] requestAllOrders() {
		// TODO Auto-generated method stub
		//OrderID	CustomerID	CustomerName CustomerMemberShip	OrderDate	PaymentDate	PaymentName	PaymentAccountNumber	PaymentMethod
		//Item1Name,price	Item2Name,price	Item3,price
		String orderInfo;
		String breakOrderInfo[] = new String[30];
		String orderItems;
		String order[][] = new String[100000][30];
		int i=0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.pathOrder));
			orderInfo= reader.readLine();
			orderItems = reader.readLine();
			orderInfo= reader.readLine();
			orderItems = reader.readLine();
			while(orderInfo != null) {
				
				breakOrderInfo = orderInfo.split("\t");
				//System.out.println(breakOrderInfo[1].toLowerCase() +"=="+ CustomerID.toLowerCase());
				order[i++] = breakOrderInfo;
				order[i++] = orderItems.split("\t");
	
				orderInfo= reader.readLine();
				orderItems = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
		
		return order;
	}
	
	public void updateStatus(int OrderID, String status)
    {
      
        try {
        	String OldContent = "";
        	
            BufferedReader reader = new BufferedReader(new FileReader(this.pathOrder));
            String line = reader.readLine();
            OldContent = OldContent + line + "\n";
            line = reader.readLine();
            OldContent = OldContent + line;
            
            String Name[];
            
            line = reader.readLine();//3rd line
            
            while (line != null)
            {
                Name = line.split("\t");
 
                if(Name[0].equals(String.valueOf(OrderID)))
                {
                	Name[10] = status;
                	
                	line = Name[0] +"\t" + Name[1] +"\t"+
                			Name[2] +"\t" + Name[3] +"\t"+
                			Name[4] +"\t" + Name[5] +"\t"+
                			Name[6] +"\t" + Name[7] +"\t"+
                			Name[8] +"\t" + Name[9] +"\t"+
                			Name[10] +"\t" + Name[11];
                	OldContent = OldContent + "\n" + line;
                }
                else{
                    OldContent = OldContent + "\n" + line;
                }
                line = reader.readLine();
            }
            reader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.pathOrder));
            writer.write(OldContent);
            writer.close();
        }
        catch (IOException  e)
        {
            // TODO Auto-generated catch block
            System.out.print("Account is invalid");
        }
    }
	
	

	public String[][] requestAllOrderedOrders() {
		// TODO Auto-generated method stub
		//OrderID	CustomerID	CustomerName CustomerMemberShip	OrderDate	PaymentDate	PaymentName	PaymentAccountNumber	PaymentMethod
		//Item1Name,price	Item2Name,price	Item3,price
		
		String orderInfo;
		String breakOrderInfo[] = new String[30];
		String orderItems;
		String order[][] = new String[100000][30];
		int i=0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.pathOrder));
			orderInfo= reader.readLine();
			orderItems = reader.readLine();
			orderInfo= reader.readLine();
			orderItems = reader.readLine();
			while(orderInfo != null) {
			
				breakOrderInfo = orderInfo.split("\t");
				//System.out.println(breakOrderInfo[10].toLowerCase() );
				
				if(breakOrderInfo[10].toLowerCase().equals("ordered")) {
					order[i++] = breakOrderInfo;
					order[i++] = orderItems.split("\t");
				}
				orderInfo= reader.readLine();
				orderItems = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
		
		return order;
	}
	
	public String[][] requestAllReadyOrders() {
		// TODO Auto-generated method stub
		//OrderID	CustomerID	CustomerName CustomerMemberShip	OrderDate	PaymentDate	PaymentName	PaymentAccountNumber	PaymentMethod
		//Item1Name,price	Item2Name,price	Item3,price
		
		String orderInfo;
		String breakOrderInfo[] = new String[30];
		String orderItems;
		String order[][] = new String[100000][30];
		int i=0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.pathOrder));
			orderInfo= reader.readLine();
			orderItems = reader.readLine();
			orderInfo= reader.readLine();
			orderItems = reader.readLine();
			while(orderInfo != null) {
				
				breakOrderInfo = orderInfo.split("\t");
				
				if(breakOrderInfo[10].toLowerCase().equals("ready")) {
					order[i++] = breakOrderInfo;
					order[i++] = orderItems.split("\t");
				}
				orderInfo= reader.readLine();
				orderItems = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
		
		return order;
	}
	
	public String[][] requestAllShippedOrders() {
		// TODO Auto-generated method stub
		//OrderID	CustomerID	CustomerName CustomerMemberShip	OrderDate	PaymentDate	PaymentName	PaymentAccountNumber	PaymentMethod
		//Item1Name,price	Item2Name,price	Item3,price
		
		String orderInfo;
		String breakOrderInfo[] = new String[30];
		String orderItems;
		String order[][] = new String[100000][30];
		int i=0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.pathOrder));
			orderInfo= reader.readLine();
			orderItems = reader.readLine();
			orderInfo= reader.readLine();
			orderItems = reader.readLine();
			while(orderInfo != null) {
				
				breakOrderInfo = orderInfo.split("\t");
				
				if(breakOrderInfo[10].toLowerCase().equals("shipped")) {
					order[i++] = breakOrderInfo;
					order[i++] = orderItems.split("\t");
				}
				orderInfo= reader.readLine();
				orderItems = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
		
		return order;
	}
	
	
	@Override
	public String[][] requestOneOrder(int OrderID, String CustomerID) {
		// TODO Auto-generated method stub
		String orderInfo;
		String breakOrderInfo[];
		String orderItems;
		String orderList[][] = new String[2][30];
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.pathOrder));
			orderInfo= reader.readLine();
			orderItems = reader.readLine();
			orderInfo= reader.readLine();
			orderItems = reader.readLine();
			while(orderItems != null) {
				
			
				breakOrderInfo = orderInfo.split("\t");
			
				if(breakOrderInfo[0].equals(String.valueOf(OrderID)) && breakOrderInfo[1].toLowerCase().equals(CustomerID.toLowerCase())) {
					orderList[0] = breakOrderInfo;
					orderList[1] = orderItems.split("\t");
					break;
				}
				
				orderInfo= reader.readLine();
				orderItems = reader.readLine();
			}
			reader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return orderList;
	}
	public String[][] requestOneOrder(int OrderID) {
		// TODO Auto-generated method stub
		String orderInfo;
		String breakOrderInfo[];
		String orderItems;
		String orderList[][] = new String[2][30];
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.pathOrder));
			orderInfo= reader.readLine();
			orderItems = reader.readLine();
			orderInfo= reader.readLine();
			orderItems = reader.readLine();
			while(orderItems != null) {
				
			
				breakOrderInfo = orderInfo.split("\t");
			
				if(breakOrderInfo[0].equals(String.valueOf(OrderID))) {
					orderList[0] = breakOrderInfo;
					orderList[1] = orderItems.split("\t");
					break;
				}
				
				orderInfo= reader.readLine();
				orderItems = reader.readLine();
			}
			reader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return orderList;
	}
}
