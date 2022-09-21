//Project 2: Building an ugly with a lot of bugs and lack of sercurity
//Developing team: An Lam, Christian Pantoja, Michael Storms, Steve Mwika

package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class ShoppingGUI extends Application
{
    Stage window;
    Scene scene, scene2, scene3, ProceedCartScene;;
 
    
    Scene viewStockSence;
    Stage secondStage = new Stage();
    Stage refillStage = new Stage();
    //end catalog
    //login
    private boolean isLogin;
    
    //save information
	private Account customerAccount = new Account();
	private Cart cart[] = new Cart[10];
	Catalog catalogList[] = Catalog.loadCatalog();
	
    private TextField usernameTextField = new TextField();
	private TextField passwordTextField = new TextField();
	private Label LoginFail;
	{
		this.LoginFail = new Label("Username or Password Is Not Corect!");
		LoginFail.setStyle("-fx-color: red");
		LoginFail.setVisible(false);
		this.isLogin = false;
	}
    //end login
	//email[0]	username[1]	password[2]	AccountType[3]	role[4]	FirstName[5]	LastName[6]	address[7]	phonenumber[8]
	//CreateAccount
	private TextField usernameText  = new TextField();
	private TextField passwordText  = new TextField();
	private TextField FirstNameText  = new TextField();
	private TextField LastNameText  = new TextField();
	private TextField addressText  = new TextField();
	private TextField creditcardText = new TextField();
	private TextField phoneNumberText = new TextField();
	private ChoiceBox<String> typeAccountBox ;
	
	private TextField ExpireDateText = new TextField();
	private TextField CSVText = new TextField();
	private TextField nameHolderText = new TextField();
	private  int tranNum = -1;
	
	//private	RadioButton premiumRad= new RadioButton("Check to Purchase Premimum: $40/year");
	private CheckBox premiumRad = new CheckBox("Check to Purchase Premimum: $40/year");
	
	{

		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			        "Customer",
			        "Supplier"
			    );
		typeAccountBox= new ChoiceBox<String>(options);
		typeAccountBox.getSelectionModel().selectFirst();
		
		premiumRad.setSelected(false);
		
	}
	//EndCreateAccount
	
    //Left menu bar
	Label userNameGlo = new Label("Customer");
    Button catalogButton = new Button("Browse Catalog");
    Button viewOrderButton = new Button("View Order");
//    Button profileButton = new Button("Profile");
    Button cartButton =  new Button("Cart");
    Button signoutandExit = new Button("Sign Out & Exit");
    Button logOutButton = new Button("Log Out");
    VBox layout ;
    //End LeftMenu
    int leftSceneWidth;
    int leftSceneHeight;
    //
   
    //Browser

    //End Browser
    
    //Supplier
    VBox supplierMenu;
    Label supplierName = new Label("Supplier");
    Button supLogout = new Button("Log Out");
    Button viewStock = new Button("View Stock");
    Button viewOrder = new Button("View Order");
    Button processOrder = new Button("Process Order");
    Button shipOrder = new Button("Ship Order");
    {
    	supplierName.setStyle("-fx-alignment: center ; ");
    	supLogout.setOnAction(new LogOutEvent());
    	this.leftSceneWidth = 700;
    	this.leftSceneHeight = 800;
    	supplierMenu = new VBox(20);
    	supplierMenu.setPrefWidth(20);
    	supplierMenu.getChildren().addAll( supplierName, supLogout, viewStock, viewOrder,  processOrder, shipOrder);
    	supplierMenu.setPadding(new Insets(10));
    	processOrder.setOnAction(e -> window.setScene(this.getSupplierScene("processOrder")));
    	shipOrder.setOnAction(e -> window.setScene(this.getSupplierScene("shipOrder")));
    	viewOrder.setOnAction(e -> window.setScene(this.getSupplierScene("supplierHome")));
   
        supplierMenu.setStyle("-fx-pref-width: 130px;");
        supplierMenu.getChildren().forEach(node ->
        		node.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-pref-width: 130px; -fx-alignment: center ; "));

        //Session
    	
    	
    	//End Sesson
        userNameGlo.setStyle("-fx-alignment: center ; ");
    	//Left customer menu
    	layout = new VBox(20);
        layout.setPrefWidth(20);
        layout.getChildren().addAll( userNameGlo, logOutButton,
        		 catalogButton, viewOrderButton, cartButton);
        layout.setPadding(new Insets(10));
        layout.getChildren().forEach(node ->
            node.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-pref-width: 130px; -fx-alignment: center ; "));
        layout.setStyle("-fx-pref-width: 130px;");
        //End left menu bar
        
        scene = this.getLoginScene();
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        window = primaryStage;
       
               
        //CataLog Actions
        catalogButton.setOnAction(e -> window.setScene(this.getScene("BrowseCataLog")));
        //End CataLog Actions
        logOutButton.setOnAction(new LogOutEvent());
        
       
        //Order Actions
        viewOrderButton.setOnAction(e -> window.setScene(this.getScene("ViewAllOrder")));
        //End Order Actions
        this.cartButton.setOnAction(e -> window.setScene(this.getSceneCart2("ProceedCart", false,0, 0)));
        
        viewStock.setOnAction(new viewStockEvent());
        layout.setPadding(new Insets(10));
        window.setScene(scene);
        window.setTitle("Store");
        window.show();

        
        
    }
    public Scene getSupplierOrderDetail(String choice, int orderID) {
    	             	//testtttttttttt
    	int gridRow=0;
    	Order customerOrder = new Order();
      	String[][] orderList=  customerOrder.requestOneOrder(orderID);
      	 int numberOfItems = Integer.parseInt(orderList[0][11]);
      	 Stock tempStock = new Stock();
       	Stock stock[] = new Stock[numberOfItems];
       
 
     	
  	    GridPane gridpane = new GridPane();
        gridpane.setHgap(6);
        gridpane.setVgap(20);
       // System.out.println(orderList[0][11]);
       
           	
        Label orderIDHeaderLable = new Label("Transaction Number:");
      	Label customerNameHeaderLable = new Label("Customer Name: ");
      	Label customerMemberShipHeaderLable = new Label("Membership:");
      	Label itemsName[] = new Label[numberOfItems];
      	Label numItem[] = new Label[numberOfItems];
      	/*Label instock[] = new Label[numberOfItems];
      	Label valiable[] = new Label[numberOfItems];
      	Label reserved[] = new Label[numberOfItems];
      	*/
      	Label status[] = new Label[numberOfItems];
      	Label orderDateHeaderLable= new Label("Order Date:");
      	Label paymentDateHeaderLable= new Label("Order Date:");
      	Label paymentMethodHeaderLable = new Label("Payment Method:");
      	Label numberOfItemsHeaderLabel = new Label("Items:");
      	Label totalHeaderLable= new Label("Total:");
      	Label statusHeaderLable= new Label("Status:");
      	Label messageLabel = new Label("Process sucessfully ");
      	 messageLabel.setVisible(false);
      	Button procOrder = new Button("Process Order");
      
      	
      	Label orderIDLable = new Label(orderList[0][0]);
      	Label customerNameLable = new Label(orderList[0][2]);
      	Label customerMemberShip = new Label(orderList[0][3]);
      	Label itemsPrice[] = new Label[numberOfItems];
      	Label orderDateLabel= new Label(orderList[0][4]);
      	Label paymentDateLable = new Label(orderList[0][5]);
      	Label paymentMethodLabel = new Label(orderList[0][8]);
      	Label numberOfItemsLabel = new Label(orderList[0][11]);
      	Label totalLable = new Label(orderList[0][9]);
      	Label statusLable= new Label(orderList[0][10]);
      	
       gridRow= 0;
      	//                               
      	gridpane.add(orderIDHeaderLable, 0, gridRow);  gridpane.add(orderIDLable, 1, gridRow);
      	gridRow++;
      	gridpane.add(customerNameHeaderLable, 0, gridRow);  gridpane.add(customerNameLable, 1, gridRow); 
      	gridRow++;
      	gridpane.add(customerMemberShipHeaderLable, 0, gridRow);  gridpane.add(customerMemberShip, 1, gridRow);
      	gridRow++;
      	
      	boolean isReadyToProceed = true;
      	try {
	      	int i = 0;
	      	
	    	String splitItem[] = null;
	      	for(i = 0;i<numberOfItems; i++) {
	      	          	
	      		splitItem =orderList[1][i].split(",");
	      	    itemsName[i] = new Label(splitItem[0]);
	      	    itemsPrice[i] = new Label(splitItem[1]);
	      	    stock[i]= tempStock.getOneStock(splitItem[0]);
	      	    numItem[i] = new Label(splitItem[2]);
	      	   // instock[i] = new Label(String.valueOf(stock[i].getNumberOfItems()));
	      	//	valiable[i] = new Label(String.valueOf(stock[i].getAvailable()));
	      		//reserved[i]  = new Label(String.valueOf(stock[i].getReserved()));
	      	    if(choice.equals("processOrder")) {
	      	    	if(Integer.parseInt(splitItem[2]) <= stock[i].getAvailable()) {
		      	    	status[i] = new Label("ready");
		      	    	//update stock: reserve + numItem; available - numItem
		      	    }else {
		      	    	status[i] = new Label("Require Refill");
		      	    	isReadyToProceed = false;	
		      	    }
	      	    }else if(choice.equals("shipOrder")){
	      	    	status[i] = new Label("ready");
	      	    }else {
	      	    	status[i] = new Label("");
	      	    }
	      	    
	      	    gridpane.add(itemsName[i], 0, gridRow);  gridpane.add(itemsPrice[i], 1, gridRow);
	      	    gridpane.add(numItem[i], 2, gridRow); gridpane.add(status[i] , 3, gridRow);
	      	   
	      	    gridRow++;
	      	}
	  	}catch(ArrayIndexOutOfBoundsException e) {
    		
    	}
      	
      	System.out.println("isReadyToProceed:" + isReadyToProceed);
      	if(isReadyToProceed == true) {
          	EventHandler<ActionEvent> proceedEvent =
    	                new EventHandler<ActionEvent>() {
    	          public void handle(ActionEvent e)
    	          {
    	        	  
    	        	  if(choice.equals("processOrder")) {
    	             	 //update ordered to ready
    	        		 //Update stock
    	        		  try {
    	        		      	int i = 0;
    	        		      	
    	        		    	String splitItem[] = null;
    	        		      	for(i = 0;i<numberOfItems; i++) {
    	        		      	          	
    	        		      		splitItem =orderList[1][i].split(",");
    	        		      		System.out.println(splitItem[0]);
    	        		      	    Order o = new Order();
    	        		      	    o.updateStatus(orderID, "ready");
    	        		      	    
    	        		      	    Stock s = new Stock();
    	        		      	    s.UpdateProccedStock(splitItem[0],Integer.parseInt(splitItem[2]));
    	        		      	  window.setScene(getSupplierOrderDetail("processOrder", orderID));
    	        		      	
    	        		      
    	        		      	}
    	        		  	}catch(ArrayIndexOutOfBoundsException eee) {
    	        	    		
    	        	    	}
    	             	
    	             }else if(choice.equals("shipOrder")){
    	            	//update ordered to ship
    	              	//display the message 
    	            	 try {
 	        		      	int i = 0;
 	        		      	
 	        		    	String splitItem[] = null;
 	        		      	for(i = 0;i<numberOfItems; i++) {
 	        		      	          	
 	        		      		splitItem =orderList[1][i].split(",");
 	        		      		System.out.println(splitItem[0]);
 	        		      	    Order o = new Order();
 	        		      	    o.updateStatus(orderID, "shipped");
 	        		      	    
 	        		      	    Stock s = new Stock();
 	        		      	    s.UpdateShipStock(splitItem[0],Integer.parseInt(splitItem[2]));
 	        		      	    
 	        		      	    window.setScene(getSupplierOrderDetail("shipOrder", orderID));
 	        		      
 	        		      	}
 	        		      	
 	        		  	}catch(ArrayIndexOutOfBoundsException eee) {
 	        	    		
 	        	    	}
    	            	  
    	             }else {
    	             	 
    	             }
    	          }
    		      };
    	      	procOrder.setOnAction(proceedEvent);
 
    	      


          	}else {
          	  
          	  messageLabel.setText("Some of stock must be refilled!");
      	      messageLabel.setVisible(true);
          	}
      	
      	
      	gridpane.add(orderDateHeaderLable, 0, gridRow);  gridpane.add(orderDateLabel, 1, gridRow);   
      	gridRow++;
      	gridpane.add(paymentDateHeaderLable, 0, gridRow);  gridpane.add(paymentDateLable, 1, gridRow);   
      	gridRow++;
      	gridpane.add(paymentMethodHeaderLable, 0, gridRow);  gridpane.add(paymentMethodLabel, 1, gridRow);   
      	gridRow++;
      	gridpane.add(numberOfItemsHeaderLabel, 0, gridRow);  gridpane.add(numberOfItemsLabel, 1, gridRow);   
      	gridRow++;
      	gridpane.add(totalHeaderLable, 0, gridRow);  gridpane.add(totalLable, 1, gridRow);   
      	gridRow++;
      	gridpane.add(statusHeaderLable, 0, gridRow);  gridpane.add(statusLable, 1, gridRow);   
      	gridRow++;
      	
 	      
      	if(statusLable.getText().equals("shipped") && choice.equals("shipOrder")) {
	      	  messageLabel.setText("Ship order successfully");
      	      messageLabel.setVisible(true);
      	    procOrder.setOnAction(null);
      	}
    	if(statusLable.getText().equals("ready") && choice.equals("processOrder")) {
	      	  messageLabel.setText("Proceed order successfully");
    	      messageLabel.setVisible(true);
    	    procOrder.setOnAction(null);
    	}
    	
      	gridpane.add(messageLabel, 0, gridRow, gridRow, 1);   
      	gridRow++;
      	
      	if(choice.equals("processOrder")) {
      		System.out.println("processOrder");
      		procOrder.setText("Proccess Order");
      		gridpane.add(procOrder, 0, gridRow); 
          	gridRow++;
      	}else if(choice.equals("shipOrder")) {
      		System.out.println("shipOrder");
      		procOrder.setText("Ship Order");
      		gridpane.add(procOrder, 0, gridRow); 
          	gridRow++;
      	}else {
      		System.out.println("viewOrder");
      	}
      	
      	
      	
      	
      	gridpane.setStyle("-fx-pref-width: 550px; -fx-pref-height: 20px;");
        HBox layout2 = new HBox(20);
        VBox leftBox = new VBox(gridpane);
        layout2.getChildren().addAll(supplierMenu, leftBox);

        scene2 = new Scene(layout2,leftSceneHeight, leftSceneWidth);
        return scene2;
	         
    }
    public Scene getSupplierScene(String choice) {
    	supplierName.setText(customerAccount.getFirstName() + " " + customerAccount.getLastName());
    	Label customerNameLable = null;
    	Order customerOrder = new Order();
	    //String[][] orderList=  customerOrder.requestAllOrders(this.customerID);
    	String[][] orderList = null;
        
        int gridRow=0;
        if(choice.equals("processOrder")) {
        	 customerNameLable = new Label("Proceed Orders ");
        	orderList =  customerOrder.requestAllOrderedOrders();
        }else if(choice.equals("shipOrder")){
        	 customerNameLable = new Label("Ship Orders ");
        	orderList =  customerOrder.requestAllReadyOrders();
        }else {
        	 customerNameLable = new Label("View All Orders");
        	orderList =  customerOrder.requestAllOrders();
        }
        
    	if(true) {
    		
  	    //Get Order Information

  	      	Button viewOrderDetailButton[] = new Button[10];
  	      	
	      	Label tranNumHeaderLable = new Label("Transaction Number");
	      	Label orderDateHeaderLable= new Label("Order Date");
	      	Label totalHeaderLable= new Label("Total");
	      	Label statusHeaderLable= new Label("Status");
	      	
  	      	Label tranNumLable ;
  	      	Label orderDateLable;
  	      	Label totalLable;
  	      	Label statusLable;
	  	    GridPane gridpane = new GridPane();
	        gridpane.setHgap(6);
	        gridpane.setVgap(10);
	        
	        gridpane.add(customerNameLable, 0, 0); 
	        gridpane.add(tranNumHeaderLable, 0, 1); 
	        gridpane.add(orderDateHeaderLable, 1, 1); 
	        gridpane.add(statusHeaderLable, 2, 1);
	        gridpane.add(totalHeaderLable, 3, 1);
	        
	        
	        gridRow =2;
	        for(int i=0; i<12; i+=2) {
	        	if(orderList[i][0] == null) break;
	        	//test
	        	//System.out.println(orderList[i][0]+" " + orderList[i][4]+" " + orderList[i][10] +" "+ orderList[i][9]);
	        	tranNumLable = new Label( orderList[i][0]);
	  	      	orderDateLable= new Label( orderList[i][4]);
	  	      	totalLable= new Label( orderList[i][10]);
	  	        statusLable= new Label( orderList[i][9]);
	  	        
	        	gridpane.add(tranNumLable, 0, gridRow); 
		        gridpane.add(orderDateLable, 1, gridRow); 
		        gridpane.add(totalLable, 2, gridRow);
		        gridpane.add(statusLable, 3, gridRow);
		        viewOrderDetailButton[gridRow] = new Button("Select");
		        gridpane.add(viewOrderDetailButton[gridRow], 4, gridRow);
		        
		        System.out.println(orderList[i][0]);
		        int orderID = Integer.parseInt(orderList[i][0]);

		        if(choice.equals("processOrder")) {
		        	 viewOrderDetailButton[gridRow].setOnAction(e -> window.setScene(this.getSupplierOrderDetail("processOrder", orderID)));  
		        }else if(choice.equals("shipOrder")){
		        	 viewOrderDetailButton[gridRow].setOnAction(e -> window.setScene(this.getSupplierOrderDetail("shipOrder", orderID)));  
		        }else {
		        	viewOrderDetailButton[gridRow].setOnAction(e -> window.setScene(this.getSupplierOrderDetail("viewOrder", orderID)));
		        }
		       	      
		        
		        gridRow++;
	        }
	      	  	   
            
            HBox layout2 = new HBox(20);
            VBox leftBox = new VBox(gridpane);
            layout2.getChildren().addAll(supplierMenu, leftBox);

            scene2 = new Scene(layout2,leftSceneHeight, leftSceneWidth);
  
    	}
    	 return scene2;
    	
    }
    public Scene getCreateAccountScene(String typeAccountChoice) {
    	//email[0]	username[1]	password[2]	AccountType[3]	role[4]	FirstName[5]	LastName[6]	address[7]	phonenumber[8]
    	
    	Label titleLabel = new Label("Create Account");
     	
     	Label typeAccountLabel = new Label("Type Account");
    	Label usernameLabel = new Label("Username:");
    	Label passWordLabel = new Label("Password:");
    	Label firstNameLabel = new Label("First Name:");
    	Label lastNameLabel = new Label("Last Name:");
    	Label addressLabel = new Label("Address");
    	Label phoneNumberLabel = new Label("Phone Number:");
    	Label creditCardLabel = new Label("Creditcard Number: ");
    
    	Label  ExpireDateLabel= new Label("MM/DD/YYYY");
    	Label  CSVLabel= new Label("CSV");
    	Label  nameHolderLabel= new Label("Name on Card");
    	
    	Label userNameMsgLabel = new Label("Username is empty");userNameMsgLabel.setVisible(false);
    	Label passwordMsgLabel = new Label("PassWords is empty");passwordMsgLabel.setVisible(false);
    	Label firstNameMsgLabel = new Label("Firstname is empty");firstNameMsgLabel.setVisible(false);
    	Label lastNameMsgLabel = new Label("Lastname is empty");lastNameMsgLabel.setVisible(false);
    	Label addressMsgLabel = new Label("Address is empty");addressMsgLabel.setVisible(false);
    	Label phonenumMsgLabel = new Label("PhoneNumber is empty");phonenumMsgLabel.setVisible(false);
    	Label creditcadMsgLabel = new Label("Creditcard is empty");creditcadMsgLabel.setVisible(false);
    	Label accountMsgLabel = new Label("Invalid creditcard");accountMsgLabel.setVisible(false);
    	
    	GridPane loginPane = new GridPane();
		loginPane.setHgap(3);
    	loginPane.setVgap(9);
    	
    	Label success = new Label("Create account successfully");
		Button moveHome = new Button("Home");
		//sence sucessfull create acc
    	if(typeAccountChoice == "createAccountSuccessfull") {
    		
    	
    		userNameGlo.setText(customerAccount.getFirstName() + " " + customerAccount.getLastName());
    		
    		loginPane.add(success, 0, 0);
    		loginPane.add(moveHome, 0, 1);
    		
    		EventHandler<ActionEvent> goHomeEvent =
                    new EventHandler<ActionEvent>() {
              public void handle(ActionEvent e)
              {
            	  if(customerAccount.getRole().equals("Customer")) {
          				window.setScene(getScene("BrowseCataLog"));
          		}else if(customerAccount.getRole().equals("Supplier")){
          			window.setScene(getSupplierScene("processOrder"));
          		}
              }
    	      };
    	      moveHome.setOnAction(goHomeEvent);
    		
    	
    		
    	//creating account scene
    	}else {
    		Button creatAccButton = new Button("Create Account");
            int gridrow = 0;
            loginPane.add(typeAccountLabel, 0, gridrow);  loginPane.add(typeAccountBox, 1, gridrow); 
            gridrow++;
            //System.out.println(typeAccountChoice + " " + typeAccountBox.getValue());
            
            //creating Supplier scene
        	if(typeAccountChoice.equals("Supplier")) {
                loginPane.add(usernameLabel, 0, gridrow);  loginPane.add(usernameText, 1, gridrow);
                gridrow++;
                loginPane.add(passWordLabel, 0, gridrow);  loginPane.add(passwordText, 1, gridrow); 
                gridrow++;
                FirstNameText.setText("");
                LastNameText.setText("");
                addressText.setText("");
                phoneNumberText.setText("");
                creditcardText.setText("");
                premiumRad.setSelected(false);
            //creating Customer scence
        	}
        	else if(typeAccountChoice.equals("Customer")){
						
                  loginPane.add(usernameLabel, 0, gridrow);  loginPane.add(usernameText, 1, gridrow); loginPane.add(userNameMsgLabel, 2, gridrow);
                  gridrow++;
                  loginPane.add(passWordLabel, 0, gridrow);  loginPane.add(passwordText, 1, gridrow); loginPane.add(passwordMsgLabel, 2, gridrow);
                  gridrow++;
                  loginPane.add(firstNameLabel, 0, gridrow);  loginPane.add(FirstNameText, 1, gridrow);loginPane.add(firstNameMsgLabel, 2, gridrow);
                  gridrow++;
                  loginPane.add(lastNameLabel, 0, gridrow);  loginPane.add(LastNameText, 1, gridrow); loginPane.add(lastNameMsgLabel, 2, gridrow);
                  gridrow++;
                  loginPane.add(addressLabel, 0, gridrow);  loginPane.add(addressText, 1, gridrow);loginPane.add(addressMsgLabel, 2, gridrow);
                  gridrow++;
                  loginPane.add(phoneNumberLabel, 0, gridrow);  loginPane.add(phoneNumberText, 1, gridrow);loginPane.add(phonenumMsgLabel, 2, gridrow);
                  gridrow++;
                  loginPane.add(creditCardLabel, 0, gridrow);  loginPane.add(creditcardText, 1, gridrow);loginPane.add(creditcadMsgLabel, 2, gridrow);
                  gridrow++;
                  loginPane.add(premiumRad, 0, gridrow, gridrow, 1);
                  gridrow++;
                  
                  if(premiumRad.isSelected() == true) {
                	  loginPane.add(ExpireDateLabel, 0, gridrow);  loginPane.add(ExpireDateText, 1, gridrow); loginPane.add(accountMsgLabel, 2, gridrow);
                      gridrow++;
                      loginPane.add(CSVLabel, 0, gridrow);  loginPane.add(CSVText, 1, gridrow);
                      gridrow++;
                      loginPane.add(nameHolderLabel, 0, gridrow);  loginPane.add(nameHolderText, 1, gridrow);
                      gridrow++;
                      
                  }
                  
          	      
          	    premiumRad.setOnAction(e->window.setScene(this.getCreateAccountScene("Customer")));
        	}
        	
            loginPane.add(creatAccButton, 0, gridrow, gridrow, 1);
            gridrow++;	
            
            //Chose Customer or Supplier
        	EventHandler<ActionEvent> changeFrameEvent =
                    new EventHandler<ActionEvent>() {
              public void handle(ActionEvent e)
              {
            	         	  window.setScene(getCreateAccountScene(typeAccountBox.getValue()));
              }
    	      };
    	      
    	      typeAccountBox.setOnAction(changeFrameEvent);

    	  //create accountt
          EventHandler<ActionEvent> createAccountButEven =
                    new EventHandler<ActionEvent>() {
              public void handle(ActionEvent e)
              {
            	 
            	 
            
            	if(usernameText.getText().isBlank()) {
            		userNameMsgLabel.setText("Username is empty");
            		userNameMsgLabel.setVisible(true);}
            	else{
            		if(customerAccount.isUserAvailable(usernameText.getText()) ==false) {
               		 userNameMsgLabel.setText("Username was used");
               		 userNameMsgLabel.setVisible(true);
               		 
               	 	}else {
               	 	userNameMsgLabel.setVisible(false);
               	 	}
            	}
            	
            	if(passwordText.getText().isBlank()) {passwordMsgLabel.setVisible(true);}
            	else{passwordMsgLabel.setVisible(false);}
            	if(FirstNameText.getText().isBlank()) {firstNameMsgLabel.setVisible(true);}
            	else{firstNameMsgLabel.setVisible(false);}
            	if(LastNameText.getText().isBlank()) {lastNameMsgLabel.setVisible(true);}
            	else{lastNameMsgLabel.setVisible(false);}
            	if(addressText.getText().isBlank()) {addressMsgLabel.setVisible(true);}
            	else{addressMsgLabel.setVisible(false);}
            	if(phoneNumberText.getText().isBlank()) {phonenumMsgLabel.setVisible(true);}
            	else{phonenumMsgLabel.setVisible(false);}
            	if(creditcardText.getText().isBlank()) {creditcadMsgLabel.setVisible(true);}
            	else{creditcadMsgLabel.setVisible(false);}
            	if(ExpireDateText.getText().isBlank() || CSVText.getText().isBlank()
            			|| nameHolderText.getText().isBlank() || tranNum == -1 ){
            	
            		accountMsgLabel.setVisible(true);
            		
            		}else {
            			accountMsgLabel.setVisible(false);
            		}

            	 String mesg = " ";
				if(typeAccountBox.getValue().equals("Customer")){
            		 
            		 if(customerAccount.isUserAvailable( usernameText.getText())) {
            			               		 
	            		 if(premiumRad.isSelected() == true) {
	            			 SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	            			
	            			 try {
	            				// purchase(String username, int accountNumber,/ Date ExpiredDate, int CSV, String nameHolder, double amount){
		            			String username = usernameText.getText();
		            			int accNum = Integer.parseInt(creditcardText.getText());
								Date expireDate = dateFormat.parse(ExpireDateText.getText());
								int csv = Integer.parseInt(CSVText.getText());
		            			String nameholder = nameHolderText.getText();
		            			
		            			 tranNum = customerAccount.purchase(username, accNum, expireDate, csv, nameholder, 40);
		            			
		            			 if(tranNum != -1 || tranNum != -2) {
		            				 mesg = customerAccount.createCustomerAccount(usernameText.getText(), passwordText.getText(), FirstNameText.getText(), 
		                        			 LastNameText.getText(), addressText.getText(), phoneNumberText.getText(), creditcardText.getText());
		            				 customerAccount.upgradePremimum(username);
		            				 accountMsgLabel.setVisible(true);

		            				 window.setScene(getCreateAccountScene("createAccountSuccessfull"));
		            			 }
							} catch (ParseException | NumberFormatException e1) {
								e1.printStackTrace();
							}
	            			 
	            		 }else {
	            			
	            			 mesg = customerAccount.createCustomerAccount(usernameText.getText(), passwordText.getText(), FirstNameText.getText(), 
                        			 LastNameText.getText(), addressText.getText(), phoneNumberText.getText(), creditcardText.getText());
	            			 if(mesg.equals("Create Account Successfully")) {
	            				 window.setScene(getCreateAccountScene("createAccountSuccessfull"));
	            			 }
	            		 }
	            		 
	            		// System.out.println("CreatAccount:" + mesg);
	            		 
            		 }
            	 }else if (typeAccountBox.getValue().equals("Supplier")){
            		 //asds;
            		 mesg = customerAccount.createSupplierAccount(usernameText.getText(), passwordText.getText());
            		 
            		 if(mesg.equals("Create Account Successfully")) {
            			 window.setScene(getCreateAccountScene("createAccountSuccessfull"));
            		 }
            		// System.out.println("Creating Supplier: " + mesg);
            	 }
            	 }
              
    	      };
    	      creatAccButton.setOnAction(createAccountButEven);
    	}
    	
        
        
        HBox layout2 = new HBox(20);
        VBox layout3 = new VBox(titleLabel, loginPane);
        layout3.setStyle("-fx-alignment: center ; ");
        layout2.getChildren().addAll(layout3);
        layout2.setStyle("-fx-alignment: center ; ");
        scene2 = new Scene(layout2,leftSceneHeight, leftSceneWidth);
        return scene2;
    }
    public Scene getLoginScene() {

    	GridPane loginPane = new GridPane();
    	Label usernameLabel = new Label("Username");
    	Label passWordLabel = new Label("Password");
    	
    	loginPane.setHgap(3);
    	loginPane.setVgap(4);
    	Button createAccountButton = new Button("Create Account");
        Button	loginButton = new Button("Log In");
        loginButton.setStyle("-fx-pref-width: 60px;");
        loginPane.setStyle("-fx-pref-width: 300px; -fx-alignment: center ; fx-border-color: black; -fx-border-width: 6px;");
        
        HBox buttBox = new HBox(loginButton, createAccountButton);
        loginPane.add(usernameLabel, 0, 0);  loginPane.add(usernameTextField, 1, 0); 
        loginPane.add(passWordLabel, 0, 1);  loginPane.add(passwordTextField, 1, 1); 
        									 loginPane.add(buttBox, 1, 2);
        loginPane.add(LoginFail, 1, 3);								 
        									 
        
        
		 //CataLog Actions
	      loginButton.setOnAction(new LoginEvent());
	      createAccountButton.setOnAction(e -> window.setScene(this.getCreateAccountScene("Customer")));
	     // System.out.println(this.isLogin);
	      //End CataLog Actions
        
        HBox layout2 = new HBox(20);
        layout2.getChildren().addAll(loginPane);
        layout2.setStyle("-fx-alignment: center ; ");
        scene2 = new Scene(layout2,leftSceneHeight, leftSceneWidth);
        return scene2;
        
        
    }
    
    public Scene getSceneViewOrderDetail(String sceneName, int orderID,String customerID) {
    	//Set up layout [leftMenu][right frame]
	  	    //[right frame]:  	
	  	    //			[leftMenu] [tranNum]		[value]
	  	    //			[leftMenu] [name]			[value] 
	  	    //			[leftMenu] [memberShip] 	[value]
	  	    //			[leftMenu] [OrderDate]		[value]
        
	      	//			[leftMenu] [Item 1]			[cost]
	      	//			[leftMenu] [Item n]			[cost]
        
	  	    //			[leftMenu] [PaymentDate]	[value]
	  	    //			[leftMenu] [CreditCardNum]	[value]
	  	    //			[leftMenu] [PaymentMethod]	[value]
	        //			[leftMenu] [Items		]	[value]
		  	//			[leftMenu] [Total]			[value]
	        //			[leftMenu] [Status]			[value]
    	System.out.println("See meeeee");
    	 if (sceneName.equals("ViewOneOrder")) {
         	//testtttttttttt
    		         
         	Order customerOrder = new Order();
   	      	String[][] orderList=  customerOrder.requestOneOrder(orderID, customerAccount.getUsername());
         	
 	  	    GridPane gridpane = new GridPane();
	        gridpane.setHgap(6);
	        gridpane.setVgap(20);
 	       // System.out.println(orderList[0][11]);
	        int numberOfItems = Integer.parseInt(orderList[0][11]);
 	           	
	        Label orderIDHeaderLable = new Label("Transaction Number:");
  	      	Label customerNameHeaderLable = new Label("Customer Name: ");
  	      	Label customerMemberShipHeaderLable = new Label("Membership:");
  	      	Label itemsName[] = new Label[numberOfItems];
  	      	Label numItem[] = new Label[numberOfItems];
	      	Label orderDateHeaderLable= new Label("Order Date:");
	      	Label paymentDateHeaderLable= new Label("Order Date:");
	      	Label paymentMethodHeaderLable = new Label("Payment Method:");
	      	Label numberOfItemsHeaderLabel = new Label("Items:");
	      	Label totalHeaderLable= new Label("Total:");
	      	Label statusHeaderLable= new Label("Status:");

	      	
  	      	Label orderIDLable = new Label(orderList[0][0]);
  	      	Label customerNameLable = new Label(orderList[0][2]);
  	      	Label customerMemberShip = new Label(orderList[0][3]);
  	      	Label itemsPrice[] = new Label[numberOfItems];
  	      	Label orderDateLabel= new Label(orderList[0][4]);
  	      	Label paymentDateLable = new Label(orderList[0][5]);
  	      	Label paymentMethodLabel = new Label(orderList[0][8]);
  	      	Label numberOfItemsLabel = new Label(orderList[0][11]);
  	      	Label totalLable = new Label(orderList[0][9]);
  	      	Label statusLable= new Label(orderList[0][10]);
  	      	
  	      int gridRow= 0;
  	      	//                               
  	      	gridpane.add(orderIDHeaderLable, 0, gridRow);  gridpane.add(orderIDLable, 1, gridRow);
  	      	gridRow++;
  	      	gridpane.add(customerNameHeaderLable, 0, gridRow);  gridpane.add(customerNameLable, 1, gridRow); 
  	      	gridRow++;
  	      	gridpane.add(customerMemberShipHeaderLable, 0, gridRow);  gridpane.add(customerMemberShip, 1, gridRow);
  	      	gridRow++;
  	      	
  	      	try {
	  	      	int i = 0;
	  	      	String splitItem[];
	  	      	for(i = 0;i<numberOfItems; i++) {
	  	      		if(orderList[1][i] == null) break;
	  	      		splitItem =orderList[1][i].split(",");
	  	      	
	  	      	    itemsName[i] = new Label(splitItem[0]);
	  	      	    itemsPrice[i] = new Label(splitItem[1]);
	  	      	    numItem[i]= new Label(splitItem[2]);
	  	      	    gridpane.add(itemsName[i], 0, gridRow);  gridpane.add(itemsPrice[i], 1, gridRow);gridpane.add(numItem[i], 2, gridRow);
	  	      	    gridRow++;
	  	      	}
  	      	}catch(ArrayIndexOutOfBoundsException e) {
  	      		
  	      	}
  	      	gridpane.add(orderDateHeaderLable, 0, gridRow);  gridpane.add(orderDateLabel, 1, gridRow);   
  	      	gridRow++;
  	      	gridpane.add(paymentDateHeaderLable, 0, gridRow);  gridpane.add(paymentDateLable, 1, gridRow);   
	      	gridRow++;
	      	gridpane.add(paymentMethodHeaderLable, 0, gridRow);  gridpane.add(paymentMethodLabel, 1, gridRow);   
  	      	gridRow++;
  	      	gridpane.add(numberOfItemsHeaderLabel, 0, gridRow);  gridpane.add(numberOfItemsLabel, 1, gridRow);   
	      	gridRow++;
	      	gridpane.add(totalHeaderLable, 0, gridRow);  gridpane.add(totalLable, 1, gridRow);   
  	      	gridRow++;
  	      	gridpane.add(statusHeaderLable, 0, gridRow);  gridpane.add(statusLable, 1, gridRow);   
	      	gridRow++;
  	      	
	      	gridpane.setStyle("-fx-pref-width: 550px; -fx-pref-height: 20px;");
            HBox layout2 = new HBox(20);
            VBox leftBox = new VBox(gridpane);
            layout2.getChildren().addAll(layout, leftBox);

            scene2 = new Scene(layout2,leftSceneHeight, leftSceneWidth);
            return scene2;
         	

         }
		return scene;
    }
    public Scene getSceneCart2(String dilivery, boolean isPayement, int tranNumCart, int OrderIDPar) {
    	Label cartSceneLabel = new Label("Cart");
    	//System.out.println("tranNumCArt = " + tranNumCart);
    	//load cart by username
    	
    	//Cart cart[] = Cart.loadItem(choice);
    	
    	//hardcode
    	cart= Cart.loadItem(customerAccount.getUsername());
    	
    	//end harcode
    	System.out.println(customerAccount.getUsername());
    	Label itemNameLabel = new Label("Item");
      	Label descriptLabel= new Label("Description ");
      	Label priceLabel= new Label("Price");
      	Label numsItemLabel= new Label("Total");
      	Label mesgLabel = new Label("");
    
      	Label itemNameVal[] = new Label[10];
      	Label descriptionVal[] = new Label[10];
      	Label priceVal[] = new Label[10];
      	Label nums[] = new Label[10];
    	
    	GridPane itemsGrid = new GridPane();
    	itemsGrid.setHgap(6);
    	itemsGrid.setVgap(20);
    	int gridrow =0;
    
        //title of the items 
        
    	itemsGrid.add(cartSceneLabel, 1, gridrow); 
        gridrow++;
        
        itemsGrid.add(itemNameLabel, 0, gridrow);   itemsGrid.add(descriptLabel, 1, gridrow);
        itemsGrid.add(priceLabel, 2, gridrow); itemsGrid.add(numsItemLabel, 3, gridrow); 
        gridrow++;
    	double total = 0;
        int i = 0;
        for(i = 0; i< 10; i++) {
        	if(cart[i] == null) break;
        	
        	total = total + cart[i].getPrice()*cart[i].getNumberOfItems();
        	
        	itemNameVal[i]=  new Label(cart[i].getItemName());
        	descriptionVal[i] = new Label(cart[i].getDescription());
        	priceVal[i] = new Label(String.valueOf(cart[i].getPrice()));
        	nums[i] = new Label(String.valueOf(cart[i].getNumberOfItems()));
        	
        	itemsGrid.add(itemNameVal[i], 0, gridrow);   itemsGrid.add(descriptionVal[i], 1, gridrow);
        	itemNameVal[i].setStyle("-fx-pref-width: 80;");
        	descriptionVal[i].setStyle("-fx-pref-width: 150;");
            itemsGrid.add(priceVal[i], 2, gridrow); itemsGrid.add(nums[i], 3, gridrow); 
            priceVal[i].setStyle("-fx-pref-width: 30; -fx-alignment: center");
            nums[i].setStyle("-fx-pref-width: 30; -fx-alignment: center");
            gridrow++;
            
        }
        Label totalLabel = new Label("Total:");
        
        Order ore = new Order();
        if(ore.isFirstOrderInyear(customerAccount.getUsername())) {
	      	Label firstPurLabel = new Label("First Purchase in a Year: Must buy Membership");
	      	Label preCostLabel = new Label("$40");
	      	itemsGrid.add(firstPurLabel, 1, gridrow); itemsGrid.add(preCostLabel, 2, gridrow);
	        gridrow++;
	        total = total + 40;
        }

        
        ChoiceBox<String> deliveryBox ;
		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			        "Pick up at store: free",
			        "Mail: $3 for delivery by mail"
			    );
		
		deliveryBox= new ChoiceBox<String>(options);
		
		if(dilivery.equals("Mail: $3 for delivery by mail")) {
			total = total + 3;
			deliveryBox.getSelectionModel().select(1);
		}else {
			deliveryBox.getSelectionModel().selectFirst();
		}
		
	     EventHandler<ActionEvent> deliveryBoxEvent =
	                new EventHandler<ActionEvent>() {
	          public void handle(ActionEvent e)
	          {
	        	   	  window.setScene(getSceneCart2(deliveryBox.getValue(), isPayement, 0,0));
	          }
		      };
		  
	      
		Label totalVal = new Label(String.valueOf(total));
        itemsGrid.add(totalLabel, 1, gridrow); itemsGrid.add(totalVal, 2, gridrow);
        gridrow++;
		deliveryBox.setStyle("-fx-pref-width: 180; -fx-alignment: center");
		deliveryBox.setOnAction(deliveryBoxEvent);
		
        
        Button makeOrder = new Button("Make Order");
        makeOrder.setOnAction(e-> window.setScene(getSceneCart2(deliveryBox.getValue(), true, 0,0)));
        
        
	      if(tranNumCart !=0 && tranNumCart !=-1 && tranNumCart !=-2) {
		    	 
	        	if(tranNumCart != -1 && tranNumCart != -2) {
	        		itemsGrid.add(new Label("Delivery method"), 0, gridrow);itemsGrid.add(new Label(deliveryBox.getValue()), 1, gridrow);
	        		gridrow++;
	        		Label transactionNumberLabel = new Label(String.valueOf(tranNumCart));
	                itemsGrid.add(new Label("Transaction Number:"), 0, gridrow); itemsGrid.add(transactionNumberLabel, 1, gridrow);
	                gridrow++;
	    			  mesgLabel.setText("Make order successfully!");
	    			  
	    			  Button viewOr = new Button("View Your Order");
	    			  System.out.println(OrderIDPar);
	    			  viewOr.setOnAction(e -> window.setScene(getSceneViewOrderDetail("ViewOneOrder", OrderIDPar, customerAccount.getUsername())));
	    			  itemsGrid.add(mesgLabel, 1, gridrow);
					  gridrow++;
					  itemsGrid.add(viewOr, 0, gridrow);
		              gridrow++;
		    	  }
				  
		  }
	      
	      if(tranNumCart ==0 || tranNumCart == -1 || tranNumCart ==-2) {

	          itemsGrid.add(new Label("Delivery method"), 0, gridrow);itemsGrid.add(deliveryBox, 1, gridrow);
	          gridrow++;
	          if(Cart.getNumberOfTypeItems() !=0) {
	        	  itemsGrid.add(makeOrder, 0, gridrow);
	              gridrow++;
	          }
	      }
	    
       
    	 
       // System.out.println(isPayement);
        if(isPayement == true) {
        	 TextField creditcardText1 = new TextField();
             TextField ExpireDateText1 = new TextField();
        	 TextField CSVText1 = new TextField();
        	 TextField nameHolderText1 = new TextField();
	    	Label CDLabel = new Label("Credit Card Number:");
	    	Label ExpLabel = new Label("Expired Date:");
	    	Label csvLabel = new Label("CSV:");
	    	Label nameHDLabel = new Label("Name Holder:");
	    	
	       	itemsGrid.add(CDLabel, 0, gridrow);   itemsGrid.add(creditcardText1, 1, gridrow);
	        gridrow++;
	        itemsGrid.add(nameHDLabel, 0, gridrow);   itemsGrid.add(nameHolderText1, 1, gridrow);
	        gridrow++;
	        itemsGrid.add(ExpLabel, 0, gridrow); itemsGrid.add(ExpireDateText1, 1, gridrow); itemsGrid.add(csvLabel, 2, gridrow); itemsGrid.add(CSVText1, 3, gridrow); 
	        gridrow++;
	       
	        Button makePaymentButton = new Button("Make Payment");
	        EventHandler<ActionEvent> makePaymentEvent =
	                new EventHandler<ActionEvent>() {
	          public void handle(ActionEvent e)
	          {
	        	
	        	  //if payment successfull false, else true
	        	  try {
   
	        	  SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
      				// purchase(String username, int accountNumber,/ Date ExpiredDate, int CSV, String nameHolder, double amount){
          			String username = customerAccount.getUsername();
          			int accNum = Integer.parseInt(creditcardText1.getText());
						Date expireDate = dateFormat.parse(ExpireDateText1.getText());
						int csv = Integer.parseInt(CSVText1.getText());
          			String nameholder = nameHolderText1.getText();
          			//make transaction
          			 tranNum = customerAccount.purchase(customerAccount.getUsername(), accNum, expireDate, csv, nameholder,Double.parseDouble(totalVal.getText()));
          			//System.out.println();
          			 if(tranNum == -1) {
          
          				 window.setScene(getSceneCart2(deliveryBox.getValue(), true, -1, 0));
          			 }else if (tranNum == -2) {
    
          				window.setScene(getSceneCart2(deliveryBox.getValue(), true, -2, 0));
          			 }else {
          				
          				//update Order
          				Cart itemsList[] = Cart.loadItem(username);
          				String []items = new String[200];
          				
          				int totalItems = 0;
          				//System.out.println(Cart.getNumberOfTypeItems());
          				try {
	          				for(int i =0; i<Cart.getNumberOfTypeItems(); i++) {
	          					if(itemsList[i] ==null)break;
	          					String item = itemsList[i].getItemName()+","+itemsList[i].getPrice()+","+itemsList[i].getNumberOfItems();
	          					items[i] = item;
	          					totalItems = totalItems + itemsList[i].getNumberOfItems();
	          				}
	          			//clear out card
	          				Cart.clearCart(username, true);
          				 
          				}catch(NullPointerException ee) {
          					ee.printStackTrace();
          				}
          				Order ord = new Order();
         				int orderIDint = ord.makeOrder(customerAccount.getUsername(), customerAccount.getAccountType(), customerAccount.getFirstName() +" "+customerAccount.getLastName(),
         						items, totalItems, Double.parseDouble(totalVal.getText()), nameholder, accNum);
          				window.setScene(getSceneCart2(deliveryBox.getValue(), false, tranNum, orderIDint));
          			 }
					} catch (ParseException | NumberFormatException e1) {
						//e1.printStackTrace();
			
          			
          				window.setScene(getSceneCart2(deliveryBox.getValue(), true, -1, 0));
					}
	        	  	 
	        	   
	          }
		      };
		     
		      if(tranNumCart == -1 ) {
    			  mesgLabel.setText("Creditcard is invalid");
    			  itemsGrid.add(mesgLabel, 1, gridrow);
    			  gridrow++;  
	    	  }else
	    	  if(tranNumCart == -2) {
    			  mesgLabel.setText("Over Credit Limit");
    			  itemsGrid.add(mesgLabel, 1, gridrow);
    			  gridrow++;  
	    	  }
	    	 
			  
			  makePaymentButton.setOnAction(makePaymentEvent);   
			  itemsGrid.add(makePaymentButton, 0, gridrow);
			  gridrow++;
		      
        }
        

		 
    	    	 
        HBox layout2 = new HBox(20);
        VBox leftBox = new VBox(itemsGrid);
        layout2.getChildren().addAll(layout, leftBox);

        scene2 = new Scene(layout2, leftSceneHeight, leftSceneWidth);
        return scene2;
  
    }
    
    
    
    public Scene getScene(String sceneName) {

        Scene scene = null;
        
        if (sceneName.equals("ViewAllOrder")) {
        	//Get Order Information
        	Order customerOrder = new Order();
  	      	//String[][] orderList=  customerOrder.requestAllOrders(this.customerID);
        	String[][] orderList=  customerOrder.requestAllOrders(customerAccount.getUsername());
  	      	Button viewOrderDetailButton[] = new Button[10];
  	      	
	      	Label tranNumHeaderLable = new Label("Transaction Number");
	      	Label orderDateHeaderLable= new Label("Order Date");
	      	Label totalHeaderLable= new Label("Total");
	      	Label statusHeaderLable= new Label("Status");
	      	
  	      	Label customerNameLable = new Label("Name: " + orderList[2][2]);
  	      	Label tranNumLable ;
  	      	Label orderDateLable;
  	      	Label totalLable;
  	      	Label statusLable;
	  	    GridPane gridpane = new GridPane();
	        gridpane.setHgap(6);
	        gridpane.setVgap(10);
	        
	        gridpane.add(customerNameLable, 0, 0); 
	        gridpane.add(tranNumHeaderLable, 0, 1); 
	        gridpane.add(orderDateHeaderLable, 1, 1); 
	        gridpane.add(statusHeaderLable, 2, 1);
	        gridpane.add(totalHeaderLable, 3, 1);
	        
	        int gridRow =2;
	        try {
		        for(int i=0; i<12; i+=2) {
		        	if(orderList[i][0] == null) break;
		        	//test
		        	//System.out.println(orderList[i][0]+" " + orderList[i][4]+" " + orderList[i][10] +" "+ orderList[i][9]);
		        	tranNumLable = new Label( orderList[i][0]);
		  	      	orderDateLable= new Label( orderList[i][4]);
		  	      	
		  	      	totalLable= new Label( orderList[i][10]);
		  	        statusLable= new Label( orderList[i][9]);
		  	        
		        	gridpane.add(tranNumLable, 0, gridRow); 
			        gridpane.add(orderDateLable, 1, gridRow); 
			        gridpane.add(totalLable, 2, gridRow);
			        gridpane.add(statusLable, 3, gridRow);
			        viewOrderDetailButton[gridRow] = new Button("View Detail");
			        gridpane.add(viewOrderDetailButton[gridRow], 4, gridRow);
			      
			        int orderID = Integer.parseInt(orderList[i][0]);
			        System.out.println(orderList[i][1]);
			        String CustomerID = orderList[i][1];
			        viewOrderDetailButton[gridRow].setOnAction(e -> window.setScene(this.getSceneViewOrderDetail("ViewOneOrder", orderID, CustomerID)));
			        
			        gridRow++;
		        }
	        }catch(ArrayIndexOutOfBoundsException eeee) {
	        	
	        }
	      	  	   
            
            HBox layout2 = new HBox(20);
            VBox leftBox = new VBox(gridpane);
            layout2.getChildren().addAll(layout, leftBox);

            scene2 = new Scene(layout2, leftSceneHeight, leftSceneWidth);
            return scene2;
            
        }
   
        
        if (sceneName.equals("BrowseCataLog")) {
        	//The customer name
        	userNameGlo.setText(customerAccount.getFirstName() + " " + customerAccount.getLastName());
        	Label browCatScene = new Label("Catalog");
        	//load catalog from file 
        
        	
        	
        	Label itemNameLabel = new Label("Item");
        	Label descriptionLabel = new Label("Description");
        	Label regularPriceLabel = new Label("Price");
        	Button[] addToCartButton = new Button[Catalog.getTotalItem()];
        	Label itemNameVal[] = new Label[Catalog.getTotalItem()];
        	Label descriptionVal[] = new Label[Catalog.getTotalItem()];
        	Label priceVal[] = new Label[Catalog.getTotalItem()];
        	Button checkOutButton = new Button("Check Out");
        	
        	GridPane gridpane = new GridPane();
	        gridpane.setHgap(6);
	        gridpane.setVgap(10);
	        
	        int gridrow =0;
	        //title of the items 
	        
	        gridpane.add(browCatScene, 1, gridrow); 
	        gridrow++;
	        gridpane.add(itemNameLabel, 0, gridrow);   gridpane.add(descriptionLabel, 1, gridrow); gridpane.add(regularPriceLabel, 2, gridrow); 
	        gridrow++;
		   
	        //load 10 items
	        try {
		        for(int i =0; i<10; i++) {
		        	itemNameVal[i]  = new Label( catalogList[i].getItemname());
		        	descriptionVal[i] = new Label(catalogList[i].getDescription());
		        	
		        	if(customerAccount.getAccountType().toLowerCase().equals("Premium")){
		        		priceVal[i] = new Label(String.valueOf(catalogList[i].getPremiumPrice()));
		        	}else {
		        		priceVal[i] = new Label(String.valueOf(catalogList[i].getRegularPrice()));
		        	}
		        	
		        	addToCartButton[i] = new Button("Add To Cart");
		        	gridpane.add(itemNameVal[i], 0, gridrow);   gridpane.add(descriptionVal[i], 1, gridrow); gridpane.add(priceVal[i] , 2, gridrow); gridpane.add(addToCartButton[i], 3, gridrow);  
			        gridrow++;
			        
			        switch(i) {
			        case 0: {addToCartButton[i].setOnAction(new addTocard0()); break;}
			        case 1: {addToCartButton[i].setOnAction(new addTocard1()); break;}
			        case 2: {addToCartButton[i].setOnAction(new addTocard2()); break;}
			        case 3: {addToCartButton[i].setOnAction(new addTocard3()); break;}
			        case 4: {addToCartButton[i].setOnAction(new addTocard4()); break;}
			        case 5: {addToCartButton[i].setOnAction(new addTocard5()); break;}
			        case 6: {addToCartButton[i].setOnAction(new addTocard6()); break;}
			        case 7: {addToCartButton[i].setOnAction(new addTocard7()); break;}
			        case 8: {addToCartButton[i].setOnAction(new addTocard8()); break;}
			        case 9: {addToCartButton[i].setOnAction(new addTocard9()); break;}
			        case 10: {addToCartButton[i].setOnAction(new addTocard10()); break;}
			        }
		        }
	        }
	        catch (ArrayIndexOutOfBoundsException e3) {
	        	
	        }
	        gridpane.add(checkOutButton, 0, gridrow);  
	        gridrow++;
	            
	        checkOutButton.setOnAction(e -> window.setScene(this.getSceneCart2("ProceedCart", false,0, 0)));
	        HBox layout2 = new HBox(20);
            VBox leftBox = new VBox(gridpane);
            layout2.getChildren().addAll(layout, leftBox);

            scene2 = new Scene(layout2,leftSceneHeight, leftSceneWidth);
            return scene2;
        }

      
              
        return scene;
        
    }
   
class LoginEvent implements EventHandler<ActionEvent>{
    	
		@Override
		public void handle(ActionEvent arg0) {
			//System.out.println(usernameTextField.getText().trim().toLowerCase() + " " + passwordTextField.getText().trim());
			isLogin = customerAccount.login(usernameTextField.getText().trim(), passwordTextField.getText().trim());
			//System.out.println("is log in: " + isLogin);
			if(isLogin == true) {
				//System.out.println(customerAccount.getFirstName() );
				//userNameGlo = new Label(customerAccount.getFirstName() + " " + customerAccount.getLastName());
				
				System.out.println(customerAccount.getRole() );
				if(customerAccount.getRole().equals("Customer")) {
					//userNameGlo = new Label(customerAccount.getFirstName() + " " + customerAccount.getLastName());
					//userNameLabel = new Label(customerAccount.getFirstName() + " " + customerAccount.getLastName());
					window.setScene(getScene("BrowseCataLog"));
				}else {
					//userNameLabel = new Label(customerAccount.getFirstName() + " " + customerAccount.getLastName());
					window.setScene(getSupplierScene("supplierHome"));
				}
		      }else {
		    	  LoginFail.setVisible(true);
		    	  
		      }
			
		}
    	
    }
int j=0;

class viewStockEvent implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent arg0) {
			++j;
			
			System.out.println("View stock event:" + j);
			Label IDLabel = new Label("ID");
	     	Label itemNameLabel = new Label("Item");
	    	Label itemTotalLabel = new Label("Total");
	    	Label isAvailableLabel = new Label("Available");
	    	Label isReservedLabel = new Label("Reserved");
	    	Button refeshButton = new Button("Refesh stock");
	    	Label itemNames[] = new Label[100];
	    	Label total[] = new Label[100];
	    	Label isAvailabel[] = new Label[100];
	    	Label isReserved[] = new Label[100];
	    	Label IDS[] = new Label[100];
	    	
	    	GridPane stockPane = new GridPane();
	    	stockPane.setHgap(3);
	    	stockPane.setVgap(9);
	    	//stockPane.setPrefWidth(500);
	    	//stockPane.setStyle("-fx-alignment: 30px ;");
			int gridrow = 0;
			stockPane.add(IDLabel, 0, gridrow); stockPane.add(itemNameLabel, 1, gridrow); stockPane.add(itemTotalLabel, 2, gridrow);  stockPane.add(isAvailableLabel, 3, gridrow);  stockPane.add(isReservedLabel, 4, gridrow); 
	    	gridrow++;
	    	
	    	Stock supplierStock = new Stock();
	    	Stock stock[] = supplierStock.getAllStocks();
	    	int i=0;
	    	while(stock[i] != null) {
	    		IDS[i] = new Label(String.valueOf(stock[i].getItemID()));
	    		itemNames[i] = new Label(stock[i].getItemName());
	    		itemNames[i].setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-pref-width: 130px; ");
	    		total[i] = new Label(String.valueOf(stock[i].getNumberOfItems()));
	    		total[i].setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-pref-width: 40px;");
	    		
	    		isAvailabel[i] =  new Label(String.valueOf(stock[i].getAvailable())); 
	    		isAvailabel[i].setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-pref-width: 40px;");
	    		isReserved[i] =  new Label(String.valueOf(stock[i].getReserved()));
	    		isReserved[i].setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-pref-width: 40px;");
	    		
	    		stockPane.add(IDS[i], 0, gridrow); stockPane.add(itemNames[i], 1, gridrow); stockPane.add(total[i] , 2, gridrow);  stockPane.add(isAvailabel[i], 3, gridrow);  stockPane.add(isReserved[i] , 4, gridrow);
	    		gridrow++;
	    		i++;
	    	}


	//.getChildren().forEach(node -> node.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-pref-width: 130px;"));
	       	Button refil = new Button("Refill");
	    	refeshButton.setOnAction(new viewStockEvent());
	    	refil.setOnAction(new refileStock());
	    	
	    	refeshButton.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-pref-width: 120px;");
	    	stockPane.add(refeshButton, 0, gridrow); stockPane.add(refil, 1, gridrow); 
	    	gridrow++;

	    	
	        HBox layout2 = new HBox(20);
	        VBox layout3 = new VBox(20);
	        layout2.getChildren().addAll(layout3);
	        layout3.getChildren().addAll(stockPane);
	        layout2.setStyle("-fx-alignment: center ; ");
	        layout3.setStyle("-fx-alignment: center ; ");
	        Scene viewStockSence = new Scene(layout2, 500, 700);
	       
	        
	    	
	        secondStage.setScene(viewStockSence);
	        secondStage.setTitle("Stock");
	        secondStage.setAlwaysOnTop(true);
	        secondStage.show();
		}

    }

class refileStock implements EventHandler<ActionEvent>{
	
	@Override
	public void handle(ActionEvent arg0) {
		
		Label IDLabel = new Label("ID");
		Label NumberLabel = new Label("Number");
		TextField IDText = new TextField();
		TextField NumberText = new TextField();
	
		Button refilBut = new Button("Refill");
		//System.out.println("View stock event:");
		GridPane stockPane = new GridPane();
    	stockPane.setHgap(3);
    	stockPane.setVgap(9);
    	//stockPane.setPrefWidth(500);
    	//stockPane.setStyle("-fx-alignment: 30px ;");
		int gridrow = 0;
		stockPane.add(IDLabel, 0, gridrow); stockPane.add(NumberLabel, 1, gridrow);
    	gridrow++;
    	stockPane.add(IDText, 0, gridrow); stockPane.add(NumberText, 1, gridrow);
    	gridrow++;
    	stockPane.add(refilBut, 0, gridrow); 
    	gridrow++;
    	
    	EventHandler<ActionEvent> refilEvent =
                new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e)
          {
        	   	 Stock stock = new Stock();
        	   	 stock.refillStock(Integer.parseInt(IDText.getText()), Integer.parseInt(NumberText.getText()));
        	   	 
          }
	      };
	      refilBut.setOnAction(refilEvent);


        HBox layout2 = new HBox(20);
        VBox layout3 = new VBox(20);
        layout2.getChildren().addAll(layout3);
        layout3.getChildren().addAll(stockPane);
        layout2.setStyle("-fx-alignment: center ; ");
        layout3.setStyle("-fx-alignment: center ; ");
        Scene viewStockSence = new Scene(layout2, 400, 200);
        

        
    	
        refillStage.setScene(viewStockSence);
        refillStage.setTitle("Refill");
        refillStage.setAlwaysOnTop(true);
        refillStage.show();
	}

}

class LogOutEvent implements EventHandler<ActionEvent>{
    	
		@Override
		public void handle(ActionEvent arg0) {
			
			isLogin = false;
			customerAccount.logout();
			usernameTextField.setText("");
			passwordTextField.setText("");
			window.setScene(getLoginScene());
			
		}

    }
   
class addTocard0 implements EventHandler<ActionEvent>{
	
	@Override
	public void handle(ActionEvent arg0) {
		
		Cart cart = new Cart();
		double pice = catalogList[0].getRegularPrice();
		if(customerAccount.getAccountType().toLowerCase().equals("Premium")){
			pice = catalogList[0].getPremiumPrice();
    	}
		cart.addItem(customerAccount.getUsername(), catalogList[0].getItemID(), catalogList[0].getItemname(), catalogList[0].getDescription(),pice); 
	}

}

class addTocard1 implements EventHandler<ActionEvent>{
	
	@Override
	public void handle(ActionEvent arg0) {
		
		Cart cart = new Cart();
		double pice = catalogList[1].getRegularPrice();
		if(customerAccount.getAccountType().toLowerCase().equals("Premium")){
			pice = catalogList[1].getPremiumPrice();
    	}
		cart.addItem(customerAccount.getUsername(), catalogList[1].getItemID(), catalogList[1].getItemname(), catalogList[1].getDescription(),pice); 
	}

}

class addTocard2 implements EventHandler<ActionEvent>{
	
	@Override
	public void handle(ActionEvent arg0) {
		
		Cart cart = new Cart();
		double pice = catalogList[2].getRegularPrice();
		if(customerAccount.getAccountType().toLowerCase().equals("Premium")){
			pice = catalogList[2].getPremiumPrice();
    	}
		cart.addItem(customerAccount.getUsername(), catalogList[2].getItemID(), catalogList[2].getItemname(), catalogList[2].getDescription(),pice); 
	}

}

class addTocard3 implements EventHandler<ActionEvent>{
	
	@Override
	public void handle(ActionEvent arg0) {
		
		Cart cart = new Cart();
		double pice = catalogList[3].getRegularPrice();
		if(customerAccount.getAccountType().toLowerCase().equals("Premium")){
			pice = catalogList[3].getPremiumPrice();
    	}
		cart.addItem(customerAccount.getUsername(), catalogList[3].getItemID(), catalogList[3].getItemname(), catalogList[3].getDescription(),pice); 
	}

}

class addTocard4 implements EventHandler<ActionEvent>{
	
	@Override
	public void handle(ActionEvent arg0) {
		
		Cart cart = new Cart();
		double pice = catalogList[4].getRegularPrice();
		if(customerAccount.getAccountType().toLowerCase().equals("Premium")){
			pice = catalogList[4].getPremiumPrice();
    	}
		cart.addItem(customerAccount.getUsername(), catalogList[4].getItemID(), catalogList[4].getItemname(), catalogList[4].getDescription(),pice); 
	}

}

class addTocard5 implements EventHandler<ActionEvent>{
	
	@Override
	public void handle(ActionEvent arg0) {
		
		Cart cart = new Cart();
		double pice = catalogList[5].getRegularPrice();
		if(customerAccount.getAccountType().toLowerCase().equals("Premium")){
			pice = catalogList[5].getPremiumPrice();
    	}
		cart.addItem(customerAccount.getUsername(), catalogList[5].getItemID(), catalogList[5].getItemname(), catalogList[5].getDescription(),pice); 
	}

}

class addTocard6 implements EventHandler<ActionEvent>{
	
	@Override
	public void handle(ActionEvent arg0) {
		
		Cart cart = new Cart();
		double pice = catalogList[6].getRegularPrice();
		if(customerAccount.getAccountType().toLowerCase().equals("Premium")){
			pice = catalogList[6].getPremiumPrice();
    	}
		cart.addItem(customerAccount.getUsername(), catalogList[6].getItemID(), catalogList[6].getItemname(), catalogList[6].getDescription(),pice); 
	}

}

class addTocard7 implements EventHandler<ActionEvent>{
	
	@Override
	public void handle(ActionEvent arg0) {
		
		Cart cart = new Cart();
		double pice = catalogList[7].getRegularPrice();
		if(customerAccount.getAccountType().toLowerCase().equals("Premium")){
			pice = catalogList[7].getPremiumPrice();
    	}
		cart.addItem(customerAccount.getUsername(), catalogList[7].getItemID(), catalogList[7].getItemname(), catalogList[7].getDescription(),pice); 
	}

}

class addTocard8 implements EventHandler<ActionEvent>{
	
	@Override
	public void handle(ActionEvent arg0) {
		
		Cart cart = new Cart();
		double pice = catalogList[8].getRegularPrice();
		if(customerAccount.getAccountType().toLowerCase().equals("Premium")){
			pice = catalogList[8].getPremiumPrice();
    	}
		cart.addItem(customerAccount.getUsername(), catalogList[8].getItemID(), catalogList[8].getItemname(), catalogList[8].getDescription(),pice); 
	}

}

class addTocard9 implements EventHandler<ActionEvent>{
	
	@Override
	public void handle(ActionEvent arg0) {
		
		Cart cart = new Cart();
		double pice = catalogList[9].getRegularPrice();
		if(customerAccount.getAccountType().toLowerCase().equals("Premium")){
			pice = catalogList[9].getPremiumPrice();
    	}
		cart.addItem(customerAccount.getUsername(), catalogList[9].getItemID(), catalogList[9].getItemname(), catalogList[9].getDescription(),pice); 
	}

}

class addTocard10 implements EventHandler<ActionEvent>{
	
	@Override
	public void handle(ActionEvent arg0) {
		
		Cart cart = new Cart();
		double pice = catalogList[10].getRegularPrice();
		if(customerAccount.getAccountType().toLowerCase().equals("Premium")){
			pice = catalogList[10].getPremiumPrice();
    	}
		Order o = new Order();
		if(o.isFirstOrderInyear(customerAccount.getUsername()))
		customerAccount.upgradePremimum(customerAccount.getUsername());
		cart.addItem(customerAccount.getUsername(), catalogList[10].getItemID(), catalogList[10].getItemname(), catalogList[10].getDescription(),pice); 
	}

}

}


