package application;

public interface SupplierOrder{
	
	public String[][] requestAllOrders() ;
	public void updateStatus(int OrderID, String status);
	public String[][] requestAllOrderedOrders();
	public String[][] requestAllReadyOrders() ;
	public String[][] requestAllShippedOrders();
	public String[][] requestOneOrder(int OrderID);
	
}
