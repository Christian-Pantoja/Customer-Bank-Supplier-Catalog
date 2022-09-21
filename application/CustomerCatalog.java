package application;


interface CustomerCatalog
{
	public Catalog[] loadCatalog();

	public String[] requestCatalog(int customerID) ;
	
	
	public int getItemID() ;
	public void setItemID(int itemID);
	public String getItemname() ;
	public void setItemname(String itemname) ;
	public String getDescription() ;
	public void setDescription(String description);
	public double getRegularPrice();
	public void setRegularPrice(double regularPrice);
	public double getPremiumPrice() ;
	public void setPremiumPrice(double premiumPrice) ;
	public  int getTotalItem();
	public  void setTotalItem(int totalItem);


}


