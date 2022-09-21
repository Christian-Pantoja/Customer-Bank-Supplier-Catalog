package application;

interface CustomerOrder {
	
	public String[][] requestAllOrders(String username);
	public boolean isFirstOrderInyear(String CustomerID);
	public int makeOrder(String customerID, String customerMembership, String customerName,
			String[] items, int numberofItems, double totalAmount, String paymentNameHolder, int paymentAccountNumber);
}
