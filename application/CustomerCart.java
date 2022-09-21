package application;

interface CustomerCart {
	public Cart[] loadItem(String username) ;
	public void	addItem(String username, int ID, String itemName, String description, double price);
	
	public int getNumberOfTypeItems();
	public  void setNumberOfTypeItems(int numberOfTypeItems);
	public boolean clearCart(String username, boolean isPaid);
	
	public int getItemID();


	public void setItemID(int itemID);

	public String getItemName() ;

	public void setItemName(String itemName);

	public String getDescription() ;

	public void setDescription(String description) ;

	public int getNumberOfItems() ;

	public void setNumberOfItems(int numberOfItems) ;


	public double getPrice() ;

	public void setPrice(double price) ;
}
