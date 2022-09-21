package application;

public interface SupplierStock extends Supplier{
	//requestStockStatus
	public  void UpdateShipStock(String ItemName, int numItems);
	public void UpdateProccedStock(String itemName, int numItems);
	public Stock getOneStock(String itemName) ;
	public void refillStock(int itemID, int numberItems);
	public Stock[] getAllStocks();
	public int getItemID();
	public void setItemID(int itemID) ;
	public String getPathStock() ;
	public void setPathStock(String pathStock) ;
	public Double getOrginalPrice() ;
	public void setOrginalPrice(Double orginalPrice) ;
	public String getItemName() ;
	public void setItemName(String itemName) ;
	public int getNumberOfItems();
	public void setNumberOfItems(int numberOfItems);
	public int getAvailable() ;
	public void setAvailable(int available);
	public int getReserved() ;
	public void setReserved(int reserved);
}
