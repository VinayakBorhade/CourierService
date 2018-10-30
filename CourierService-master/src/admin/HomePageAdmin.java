/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXPopup.PopupHPosition;
import com.jfoenix.controls.JFXPopup.PopupVPosition;
import com.jfoenix.controls.JFXRadioButton;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.IntegerStringConverter;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import db.src.database.Users;
import db.src.jdbcdemo.Driver;
import db.src.database.*;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import db.src.jdbcdemo.*;

/**
 *
 * @author Admin
 */
public class HomePageAdmin extends Application {
 
    // Three tabs at the top of home page
    Tab newOrder;
    Tab history;
    Tab tracking;
    Tab clients;
    Tab packetEstimation;
    Tab addEmp;
    
    Connection primaryConn;
    Statement st;
    PreparedStatement pStmt;
    
    //Creating an object to pass to different scenes
    //But they should have the same stage otherwise
    //there will be multiple windows
    Stage primaryStage;
    
    
    
    //Borderpane stores the layout of the scene
    BorderPane borderPane;
    
    //Hashmap is used to transfer the primaryStage and borderPane
    //across the scenes.
    HashMap<Stage, BorderPane> mapStagePane = new HashMap();
    
    double buttonRadius = 15;
    
    public GridPane sampleGridPane()
    {
        GridPane gridPane = new GridPane();
        
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        gridPane.setHgap(10);
        gridPane.setVgap(10);
    
        /*
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        */
        
        //Add a css class to so that all tabs will have the same effect 
        gridPane.getStyleClass().add("grid-pane");
        
        return gridPane;
    }
    
    public GridPane getGridPaneHelpTypePackage()
    {
        /*
            This function is used to create grid for
            the help button (?) button.
        */
        GridPane gridPaneHelpTypePackage = new GridPane();
        Label labelFragile = new Label("Fragile: Increase in cost for padding and protection");
        Label labelDurable = new Label("Durable: Decrease in cost for reducing padding and protection");
        Label labelOther = new Label("Other: Normal delivery with sufficient padding and protection");
        
        VBox vBoxLabel = new VBox();
        vBoxLabel.getChildren().addAll(
                      labelFragile,
                      labelDurable,
                      labelOther
        );
        gridPaneHelpTypePackage.getChildren().addAll(vBoxLabel);
        gridPaneHelpTypePackage.setStyle("-fx-background-color:white;-fx-border-color: black;-fx-hgap:3;-fx-vgap:5;");
        return gridPaneHelpTypePackage;
    }
    
    public GridPane getGridPaneHelpTypeDelivery()
    {
        /*
            This function is used to create grid for
            the help button (?) button.
        */
        
        GridPane gridPaneHelpTypeDelivery = new GridPane();
        Label labelNextDay = new Label("Next Day: Increase in cost for next day delivery");
        Label labelSpeed = new Label("Speed: Increase in cost for speed delivery");
        Label labelNormal = new Label("Normal: Normal delivery with no guarantee of speed delivery");
        
        VBox vBoxLabel = new VBox();
        vBoxLabel.getChildren().addAll(
                      labelNextDay,
                      labelSpeed,
                      labelNormal
        );
        gridPaneHelpTypeDelivery.getChildren().addAll(vBoxLabel);
        gridPaneHelpTypeDelivery.setStyle("-fx-background-color:white;-fx-border-color: black;-fx-hgap:3;-fx-vgap:5;"); 
        return gridPaneHelpTypeDelivery;
    }
   
    public GridPane  getConfirmOrderGridPane(JFXButton buttonConfirmOrder, JFXButton buttonAddObject, JFXButton buttonClose )
    {
       GridPane gridPane = new GridPane();
       gridPane.setPadding(new Insets(10, 10 ,10 ,10));
       Label labelConfirm = new Label("Do you want to confirm or add another object?");
      
       Separator separator = new Separator();

	HBox hBox = new HBox();
	hBox.setSpacing(10);
        hBox.getChildren().addAll(
	         buttonConfirmOrder,
		 buttonAddObject,
		 buttonClose
	);
       gridPane.add(labelConfirm, 0, 0);
       gridPane.add(separator, 0, 1);
       gridPane.add(hBox, 0, 2);
       return gridPane;
    }

    public void newOrderTabScene()
    {
        /*
            This function creates the gridPane for the neworder tab
            in the tab pane.
        */
        GridPane gridPane = sampleGridPane();
        

        // Add address
        JFXTextField textFieldDestinationAddress = new JFXTextField();
        textFieldDestinationAddress.setPromptText("Destination Address");
        textFieldDestinationAddress.setPrefWidth(500);
        
        JFXTextField textFieldSourceAddress = new JFXTextField();
        textFieldSourceAddress.setPromptText("Source Address");
        textFieldSourceAddress.setPrefWidth(500);
        textFieldSourceAddress.setVisible(false);
        
        CheckBox checkBoxEnableSourceAddress = new CheckBox("Use different source address");
        checkBoxEnableSourceAddress.setOnAction(e -> {
            //Add a listener so that button toggles the visibility of the source text field
            textFieldSourceAddress.setVisible(!textFieldSourceAddress.isVisible());
        });
        
        JFXTextField textFieldName = new JFXTextField();
        textFieldName.setPromptText("Package name");
        textFieldName.setPrefWidth(500);

        //Add type of package
        Label labelTypePackage = new Label("Type of package: ");
        labelTypePackage.setPadding(new Insets(10, 0, 0, 20));
        final ToggleGroup groupTypePackage = new ToggleGroup();

        JFXRadioButton radioFragileTypePackage = new JFXRadioButton("Fragile");
        radioFragileTypePackage.setPadding(new Insets(10));
        radioFragileTypePackage.setToggleGroup(groupTypePackage);

        JFXRadioButton radioDurableTypePackage = new JFXRadioButton("Durable");
        radioDurableTypePackage.setPadding(new Insets(10));
        radioDurableTypePackage.setToggleGroup(groupTypePackage);
        
        JFXRadioButton radioOtherTypePackage = new JFXRadioButton("Other");
        radioOtherTypePackage.setPadding(new Insets(10));
        radioOtherTypePackage.setToggleGroup(groupTypePackage);
        radioOtherTypePackage.setSelected(true);
        
        
        HBox hBoxRadioButtonsTypePackage = new HBox();
        hBoxRadioButtonsTypePackage.getChildren().addAll(radioFragileTypePackage, 
                                                         radioDurableTypePackage,  
                                                         radioOtherTypePackage);
        
        //Create a circular button
        JFXButton buttonHelpTypePackage = new JFXButton("?");
        buttonHelpTypePackage.setShape(new Circle(buttonRadius));
        buttonHelpTypePackage.setMinSize(2*buttonRadius, 2*buttonRadius);
        buttonHelpTypePackage.setMaxSize(2*buttonRadius, 2*buttonRadius);

        HBox hBoxTypePackage = new HBox();
        hBoxTypePackage.getChildren().addAll(radioFragileTypePackage, 
                                             radioDurableTypePackage,  
                                             radioOtherTypePackage,
                                             buttonHelpTypePackage);
        //Add popup for the circular question mark button
        GridPane gridPaneHelpTypePackage = getGridPaneHelpTypePackage();
        JFXPopup popupHelpTypePackage = new JFXPopup(gridPaneHelpTypePackage); 
        popupHelpTypePackage.setPrefHeight(100);
        buttonHelpTypePackage.setOnAction( e-> {
             popupHelpTypePackage.show(buttonHelpTypePackage, PopupVPosition.TOP, PopupHPosition.LEFT);
        });
        
        //Add type of delivery
        Label labelTypeDelivery = new Label("Type of delivery: ");
        labelTypeDelivery.setPadding(new Insets(10, 0, 0, 20));
        final ToggleGroup groupTypeDelivery = new ToggleGroup();

        JFXRadioButton radioNextDayTypeDelivery = new JFXRadioButton("Next day");
        radioNextDayTypeDelivery.setPadding(new Insets(10));
        radioNextDayTypeDelivery.setToggleGroup(groupTypeDelivery);

        JFXRadioButton radioSpeedTypeDelivery = new JFXRadioButton("Speed");
        radioSpeedTypeDelivery.setPadding(new Insets(10));
        radioSpeedTypeDelivery.setToggleGroup(groupTypeDelivery);
        
        JFXRadioButton radioNormalTypeDelivery = new JFXRadioButton("Normal");
        radioNormalTypeDelivery.setPadding(new Insets(10));
        radioNormalTypeDelivery.setToggleGroup(groupTypeDelivery);
        radioNormalTypeDelivery.setSelected(true);

        HBox hBoxRadioButtonsTypeDelivery = new HBox();
        hBoxRadioButtonsTypeDelivery.getChildren().addAll(radioNextDayTypeDelivery,
                                                          radioSpeedTypeDelivery,
                                                          radioNormalTypeDelivery);
        
        JFXButton buttonHelpTypeDelivery = new JFXButton("?");
        buttonHelpTypeDelivery.setShape(new Circle(buttonRadius));
        buttonHelpTypeDelivery.setMinSize(2*buttonRadius, 2*buttonRadius);
        buttonHelpTypeDelivery.setMaxSize(2*buttonRadius, 2*buttonRadius);

        GridPane gridPaneHelpTypeDelivery = getGridPaneHelpTypeDelivery();
        JFXPopup popupHelpTypeDelivery = new JFXPopup(gridPaneHelpTypeDelivery); 
        popupHelpTypeDelivery.setPrefHeight(100);
        buttonHelpTypeDelivery.setOnAction( e-> {
             popupHelpTypeDelivery.show(buttonHelpTypeDelivery, PopupVPosition.TOP, PopupHPosition.LEFT);
        });
        
        HBox hBoxTypeDelivery = new HBox();
        hBoxTypeDelivery.getChildren().addAll(radioNextDayTypeDelivery,
                                             radioSpeedTypeDelivery,
                                             radioNormalTypeDelivery,
                                             buttonHelpTypeDelivery);
        
        JFXTextArea textAreaOtherDetails = new JFXTextArea();
        textAreaOtherDetails.setPromptText("Any other details");
        textAreaOtherDetails.setPrefSize(500, 50);
        textAreaOtherDetails.setMaxHeight(50);
        int intMaxCharLimit = 200;
        //Limit the other details text field to 200 characters 
        //So that when it will be printed for the delivery boy
        //It wont go off paper
        textAreaOtherDetails.setTextFormatter(new TextFormatter<String>(change -> 
                change.getControlNewText().length() <= intMaxCharLimit ? change : null));

        Label labelPickUpTime = new Label("Preferred time to pickup");
        JFXTimePicker timePicker = new JFXTimePicker();
        timePicker.setValue(LocalTime.NOON);
        
        JFXButton buttonConfirm = new JFXButton("Confirm");
	List<Package> listPackage = new ArrayList<Package>();

        buttonConfirm.setOnAction(e -> {
            boolean enterOrder = true;
            String hour = Integer.toString(timePicker.getValue().getHour());
            String minute = Integer.toString(timePicker.getValue().getMinute());
            
            String destinationAddress = textFieldDestinationAddress.getText();
            
            if(destinationAddress.trim().isEmpty())
            {
                JFXPopup popup = Login.showPopup("Please fill the destination address");
                popup.show(textFieldDestinationAddress, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
                enterOrder = false;
            }
            String sourceAddress;
            if(checkBoxEnableSourceAddress.isSelected())
            {
                sourceAddress = textFieldSourceAddress.getText();
                if (sourceAddress.trim().isEmpty())
	        {
                    JFXPopup popup = Login.showPopup("Please fill the source address or uncheck the box");
                    popup.show(textFieldSourceAddress, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
                    enterOrder = false;
                         
                }
            }
            else
            {
                /*
			Get source address from db
                        =====================
                        Database support here
                        =====================
                */
            }
            //All clear get details 
            String typePackage = ((JFXRadioButton)groupTypePackage.getSelectedToggle()).getText();
            String typeName = textFieldName.getText();
	    Package pack = new Package(typePackage, typeName);
	    listPackage.add(pack);

            String typeDelivery = ((JFXRadioButton)groupTypeDelivery.getSelectedToggle()).getText();
            
            String details = textAreaOtherDetails.getText();
            if(enterOrder)
            {
                /*
                        =====================
                        Database support here
                        =====================
                */
		JFXButton buttonConfirmOrder = new JFXButton("Confirm");
                JFXButton buttonAddObject  = new JFXButton("Add");
                JFXButton buttonClose = new JFXButton("Delete order");
		GridPane gridPaneConfirm = getConfirmOrderGridPane(buttonConfirmOrder, buttonAddObject, buttonClose);
		JFXPopup popupConfirm = new JFXPopup(gridPaneConfirm);
                popupConfirm.setPrefSize(500, 300);
                //Bounds rootBounds = borderPane.getLayoutBounds();
		popupConfirm.show(buttonConfirm , JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
                          // (rootBounds.getWidth() - popupConfirm.getPrefWidth()) / 2,
                           //(rootBounds.getHeight() - popupConfirm.getPrefHeight()) / 2);
                
                
		buttonAddObject.setOnAction( ev -> {
	               textFieldSourceAddress.setEditable(false);
		       textFieldDestinationAddress.setEditable(false);
		       textAreaOtherDetails.setEditable(false);
                       groupTypeDelivery.getToggles().forEach(t -> {
                                Node node = (Node) t;
                                node.setDisable(true);
                       });
                       popupConfirm.hide();
                       textFieldName.requestFocus();
                });
                
		buttonClose.setOnAction( ev -> {
	               textFieldSourceAddress.setEditable(true);
		       textFieldDestinationAddress.setEditable(true);
		       textAreaOtherDetails.setEditable(true); 
                       groupTypeDelivery.getToggles().forEach(t -> {
                                Node node = (Node) t;
                                node.setDisable(false);
                       });
                       popupConfirm.hide();  
		});

		buttonConfirmOrder.setOnAction( ev -> {
                /*
			Get package from `listPackage`
                        =====================
                 aaa       Database support here
                        =====================
                */
                        for(Package p: listPackage)
                        {
                            System.out.println(p.name+" "+p.typePackage);
                        }
	               textFieldSourceAddress.setEditable(true);
		       textFieldDestinationAddress.setEditable(true);
		       textAreaOtherDetails.setEditable(true); 
                       groupTypeDelivery.getToggles().forEach(t -> {
                                Node node = (Node) t;
                                node.setDisable(false);
                       });
                       popupConfirm.hide();   
		});
            }
           
        });
        
        //Add everything to grid
        VBox vBox = new VBox(5);
        vBox.setSpacing(10);
        vBox.getChildren().addAll(
                textFieldDestinationAddress,
                textFieldSourceAddress,
                checkBoxEnableSourceAddress,
                textFieldName,
                labelTypePackage, 
                hBoxTypePackage,
                labelTypeDelivery,
                hBoxTypeDelivery,
                textAreaOtherDetails,
                labelPickUpTime,
                timePicker,
                buttonConfirm
                );
        
        gridPane.getChildren().addAll(vBox);
        newOrder.setContent(gridPane);
    }
    
    /*public GridPane getOrderDetails(Order orderSelected, JFXButton showDetails)
    {
        
            Get the order from the Order object and 
            Display every detail
        
               ======================
               Database support here!
               ======================
        
        GridPane gridOrderDetails = new GridPane();
        
        HashMap<String, Label> orderDetails = orderSelected.getDetails();
        
        VBox vBox = new VBox();
        
        for (HashMap.Entry< String,Label> m:orderDetails.entrySet())
        {
           vBox.getChildren().add(m.getValue());
        }
        
        gridOrderDetails.getChildren().addAll(vBox);
        return gridOrderDetails;
    }
    
    public void historyTabScene()
    {
        
            Make grid for the history tab
        
        GridPane gPane = sampleGridPane();
        gPane.getStyleClass().add("grid-pane");

        Label labelHistory = new Label("Check your history of couriers here!");
        
        
                Add random data for now
               ======================
               Database support here!
               ======================
        
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        orderList.add(new Order("Phone", "123456789", "1", 1, 2));
        orderList.add(new Order("Letter", "123456789", "2", 1, 3));
        orderList.add(new Order("Document", "123456789", "3", 2, 1));
        ListView<Order> listViewOrder = new ListView<Order>(orderList);
        listViewOrder.getSelectionModel().select(0);
        
        //For custom object `Order` as a listview we use cell factory
        listViewOrder.setCellFactory(param -> new ListCell<Order>() {
            @Override
            protected void updateItem(Order item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
        listViewOrder.setPrefSize(500, 200);

        JFXButton buttonGetDetails = new JFXButton("Find");        
        
        //Flow pane is used here for the drawer pane (slider)
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        
        flowPane.getChildren().addAll(labelHistory, listViewOrder, buttonGetDetails);
        flowPane.setMaxSize(200, 200);
        
        JFXDrawer rightDrawer = new JFXDrawer();
        StackPane rightDrawerPane = new StackPane();
        rightDrawerPane.getStyleClass().add("blue-400");
        
        rightDrawer.setDirection(DrawerDirection.RIGHT);
        rightDrawer.setDefaultDrawerSize(200);
        rightDrawer.setSidePane(rightDrawerPane);
        rightDrawer.setOverLayVisible(false);
        rightDrawer.setResizableOnDrag(true);

        JFXDrawersStack drawersStack = new JFXDrawersStack();
        drawersStack.setContent(flowPane);

        
        buttonGetDetails.setOnAction(e -> {
            System.out.println("Getting the order details");
            Order orderSelected = listViewOrder.getSelectionModel().getSelectedItem();
            GridPane gridShowOrderDetail = getOrderDetails(orderSelected, buttonGetDetails);
            
            rightDrawerPane.getChildren().clear();
            rightDrawerPane.getChildren().add(gridShowOrderDetail);
            drawersStack.toggle(rightDrawer);
            
        });
        
        
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        
        hBox.getChildren().addAll(drawersStack);
        
        gPane.getChildren().addAll(hBox);

        history.setContent(gPane);
    }*/
        
    private String[] getLocation(Order order)
    {
        //Write the part which gets location history 
        //from database
        /*
               ======================
               Database support here!
               ======================
        */
        String[] locHistory = {"Dispatched", "At the stop", "Reached"};
        return locHistory;
    }
    /*
    public void trackingTabScene()
    {
        GridPane gPane = sampleGridPane();
        gPane.getStyleClass().add("grid-pane");

        Label labelTracking = new Label("Track your current order here!");
        
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        
       
	To Do: Dynamically change this for getting current order
               ======================
               Database support here!
               ======================	
	
        orderList.add(new Order("Phone", "123456789", "1", 1, 2));
        orderList.add(new Order("Document", "123456789", "1", 2, 3));
        ListView<Order> listViewOrder = new ListView<Order>(orderList);
        listViewOrder.getSelectionModel().select(0);
        
        //For custom object `Order` as a listview we use cell factory
        listViewOrder.setCellFactory(param -> new ListCell<Order>() {
            @Override
            protected void updateItem(Order item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
        listViewOrder.setPrefSize(500, 200);
        
        ObservableList<String> locationList = FXCollections.observableArrayList();
        locationList.add(new String("Dispatched"));
        JFXListView<String> listViewLoc = new JFXListView<String>();
        
        // For now display text viz. the last known address of the order
        listViewOrder.setOnMouseClicked( e-> {
                Order orderSelected = listViewOrder.getSelectionModel().getSelectedItem();
                String[] locationStringList = getLocation(orderSelected);
                listViewLoc.getItems().clear();
                for(String loc: locationStringList)
                {
                    listViewLoc.getItems().add(loc);
                }
        });
        
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(listViewOrder, listViewLoc);
        gPane.getChildren().addAll(hBox);

        tracking.setContent(gPane);
    }
    */
    public GridPane getGridNotifications(JFXDrawersStack drawersStack, JFXDrawer rightDrawerNotifications)
    {
    	/*
		Returns the grid for the notficaton
		side drawer pane
	*/
        GridPane gridPaneNotifications = new GridPane();
        
        JFXCheckBox checkShipment = new JFXCheckBox("Shipment Notifications");
        JFXCheckBox checkAlerts = new JFXCheckBox("Alert notifications");
        JFXCheckBox checkNewsLetters = new JFXCheckBox("Email notifications");
       
       	//Add a separator (a line) in between 
	//checkboxes and close button
        Separator separator = new Separator();
        
        JFXButton buttonClose = new JFXButton("Close");
        
        buttonClose.setOnAction( e -> {
            drawersStack.toggle(rightDrawerNotifications);
        });
        
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().addAll(
                        checkShipment,
                        checkAlerts,
                        checkNewsLetters,
                        separator,
                        buttonClose
        );
        
        gridPaneNotifications.getChildren().addAll(vBox);
        return gridPaneNotifications;
    }
            
    public GridPane getGridSettings(JFXDrawersStack drawersStack, JFXDrawer rightDrawerSettings, JFXDrawer rightDrawerNotifications)
    {
    	/*
		Returns the grid for the settings pane 

	*/
        GridPane gridPaneSettings = new GridPane();
        
        JFXButton buttonSignOut = new JFXButton("Sign Out");
        JFXButton buttonNotifications = new JFXButton("Notifications");
        
       	//Add a separator (a line) in between 
	//checkboxes and close button
        Separator seperator = new Separator();
        
        JFXButton buttonClose = new JFXButton("Close");
        
        buttonSignOut.setOnAction(e -> {
            Login login = new Login();
            HashMap<Stage, BorderPane> mapStPn = login.makeScene(primaryStage);
            Stage stage = (Stage)mapStPn.keySet().toArray()[0];
            primaryStage.getScene().setRoot(mapStPn.get(stage));
        });
        buttonNotifications.setOnAction( e -> {
	    // Toggle the notification drawer
            drawersStack.toggle(rightDrawerNotifications);
        });
        buttonClose.setOnAction( e -> {
	    // Toggle the settings  drawer
            drawersStack.toggle(rightDrawerSettings);
        });
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().addAll(buttonSignOut, 
                                  buttonNotifications,
                                  seperator,
                                  buttonClose                         
                );
        
        gridPaneSettings.getChildren().addAll(vBox);
        return gridPaneSettings;
    }
    
    public void clientsTabScene()  {

        
    	try {
    	GridPane gPane = sampleGridPane();
    	gPane.getStyleClass().add("grid-pane");

    	Label labelTracking = new Label("Track your current order here!");
    	
    	
    	TableView<Users> table = new TableView<Users>();
        ArrayList<Users> list=new ArrayList<Users>();
        //list.
        
        /*query here for all users */
        st=primaryConn.createStatement();
        ResultSet myRs=st.executeQuery("select * from users");
		//myStmt.executeQuery("CREATE TABLE IF NOT EXISTS `test` (id int ) ");
        System.out.println("query executed");
		while(myRs.next() ) {
			String n=myRs.getString("username");
			String e=myRs.getString("email");
			String p=myRs.getString("phone");
			String loc=myRs.getString("sourceaddress");
			list.add(new Users(n,e,"",loc,p,null));
			System.out.println(  "name: "+n/*(new java.util.Date(myRs.getTimestamp("created_at").getTime())).toString()*/ );
		}
		
		
        //list.add(new Users("tushar","tushar@tushar.com","abcdefgh","mumbai","1234567890",null));
        //list.add(new Users("tushar","tushar@tushar.com","abcdefgh","mumbai","1234567890",null));
        //list.add(new Users("tushar","tushar@tushar.com","abcdefgh","mumbai","1234567890",null));
        ObservableList<Users> data =
                FXCollections.observableArrayList(list
                /*new Person("Jacob", "Smith", "jacob.smith@example.com"),
                new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
                new Person("Ethan", "Williams", "ethan.williams@example.com"),
                new Person("Emma", "Jones", "emma.jones@example.com"),
                new Person("Michael", "Brown", "michael.brown@example.com")*/);
        final HBox hb = new HBox();
    	
    	// For now display text viz. the last known address of the order
    	
    	
    	
    	final Label label = new Label("Details of all Clients ");
    	label.setFont(new Font("Arial", 20));

    	//table.setEditable(true);

    	TableColumn firstNameCol = new TableColumn("First Name");
    	firstNameCol.setMinWidth(100);
    	firstNameCol.setCellValueFactory(
    			new PropertyValueFactory<Users, String>("username"));

    	TableColumn phoneCol = new TableColumn("phone");
    	phoneCol.setMinWidth(100);
    	phoneCol.setCellValueFactory(
    			new PropertyValueFactory<Users, String>("phone"));

    	TableColumn emailCol = new TableColumn("Email");
    	emailCol.setMinWidth(200);
    	emailCol.setCellValueFactory(
    			new PropertyValueFactory<Users, String>("email"));
    	
    	TableColumn addrCol = new TableColumn("Address");
    	addrCol.setMinWidth(200);
    	addrCol.setCellValueFactory(
    			new PropertyValueFactory<Users, String>("sourceAddress"));

    	table.setItems(data);
    	table.getColumns().addAll(firstNameCol, phoneCol, emailCol,addrCol);
    	
    	/*
    	final TextField addFirstName = new TextField();
    	addFirstName.setPromptText("First Name");
    	addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
    	final TextField addLastName = new TextField();
    	addLastName.setMaxWidth(phoneCol.getPrefWidth());
    	addLastName.setPromptText("Last Name");
    	final TextField addEmail = new TextField();
    	addEmail.setMaxWidth(emailCol.getPrefWidth());
    	addEmail.setPromptText("Email");
    	final TextField Email = new TextField();
    	addEmail.setMaxWidth(emailCol.getPrefWidth());
    	addEmail.setPromptText("Email");
    	*/
    	
    	/* uncomment if adding row needed
    	final Button addButton = new Button("Add");
    	addButton.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			data.add(new (
    					addFirstName.getText(),
    					addLastName.getText(),
    					addEmail.getText()));
    			addFirstName.clear();
    			addLastName.clear();
    			addEmail.clear();
    		}
    	});*/

    	//hb.getChildren().addAll(addFirstName, addLastName, addEmail/*, addButton*/);
    	//hb.setSpacing(3);

    	final VBox vbox = new VBox();
    	vbox.setSpacing(5);
    	vbox.setPadding(new Insets(10, 0, 0, 10));
    	vbox.getChildren().addAll(label, table, hb);

    	/*make a grid pane and add all vbox */
    	gPane.getChildren().addAll(vbox);
    	clients.setContent(table);
    	
    	/*
    	hBox.setSpacing(10);
    	hBox.getChildren().addAll(listViewOrder, listViewLoc);
    	gPane.getChildren().addAll(hBox);

    	tracking.setContent(gPane);
    	*/
    	} catch(Exception e) {
    		
    	}
    }
    
    public void packetEstimationTabScene() {
    	GridPane gPane = sampleGridPane();
    	gPane.getStyleClass().add("grid-pane");

    	Label labelTracking = new Label("Track your current order here!");


    	TableView<Order> table = new TableView<Order>();
    	ArrayList<Order> list=new ArrayList<Order>();
    	//list.
    	
    	/*query db for the list of all orders of every user*/
    	try{
	    	st=primaryConn.createStatement();
	        ResultSet myRs=st.executeQuery("select * from orders where assigned=0");
			//myStmt.executeQuery("CREATE TABLE IF NOT EXISTS `test` (id int ) ");
	        System.out.println("query executed");
			while(myRs.next() ) {
				String s=myRs.getString("source");String d=myRs.getString("destination");
				String dtype=myRs.getString("deliveryType");
				String details=myRs.getString("details");
				int pr=0;
				int id=myRs.getInt("orderid");
				Order o=new Order(s,d,dtype,details,primaryConn);
				o.setOrderid(id);
				list.add(o);
				//System.out.println(  "name: "+n/*(new java.util.Date(myRs.getTimestamp("created_at").getTime())).toString()*/ );
			}
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	//list.add(new Order("mumbai","delhi","next day","this is box1",null));
    	//list.add(new Order("mumbai","delhi","next day","this is box2",null));
    	//list.add(new Order("mumbai","delhi","next day","this is box3",null));
    	
    	ObservableList<Order> data =
    			FXCollections.observableArrayList(list
    			/*new Person("Jacob", "Smith", "jacob.smith@example.com"),
    			new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
    			new Person("Ethan", "Williams", "ethan.williams@example.com"),
    			new Person("Emma", "Jones", "emma.jones@example.com"),
    			new Person("Michael", "Brown", "michael.brown@example.com")*/);
    	final HBox hb = new HBox();

    	// For now display text viz. the last known address of the order



    	final Label label = new Label("Details of all Orders, edit the packet cost ");
    	label.setFont(new Font("Arial", 20));

    	table.setEditable(true);

    	TableColumn<Order,String> sourceCol = new TableColumn<>("Source");
    	sourceCol.setMinWidth(100);
    	sourceCol.setCellValueFactory(
    			new PropertyValueFactory<Order, String>("source"));
    	

    	TableColumn<Order,String> destCol = new TableColumn<>("Destination");
    	destCol.setMinWidth(100);
    	destCol.setCellValueFactory(
    			new PropertyValueFactory<Order, String>("destination"));
    	
    	TableColumn<Order,String> delTypeCol = new TableColumn<>("Delivery Type");
    	delTypeCol.setMinWidth(100);
    	delTypeCol.setCellValueFactory(
    			new PropertyValueFactory<Order, String>("deliveryType"));
    	
    	TableColumn<Order,String> detCol = new TableColumn<>("Details");
    	detCol.setMinWidth(100);
    	detCol.setCellValueFactory(
    			new PropertyValueFactory<Order, String>("details"));
    	
    	TableColumn typeCountCol=new TableColumn("Counts of each Object Types");
    	TableColumn fragileCol = new TableColumn("Fragile");
    	fragileCol.setMinWidth(50);
    	fragileCol.setCellValueFactory(new PropertyValueFactory<Order,Integer>("fragileCount"));
    	TableColumn durableCol = new TableColumn("Durable");
    	durableCol.setMinWidth(50);
    	durableCol.setCellValueFactory(new PropertyValueFactory<Order,Integer>("durableCount"));
    	TableColumn otherCol = new TableColumn("Other");
    	otherCol.setMinWidth(50);
    	otherCol.setCellValueFactory(new PropertyValueFactory<Order,Integer>("otherCount"));

    	typeCountCol.getColumns().addAll(fragileCol, durableCol, otherCol );

    	TableColumn<Order,Integer> priceCol = new TableColumn<>("Price");
    	priceCol.setMinWidth(200);
    	priceCol.setCellValueFactory(
    			new PropertyValueFactory<Order, Integer>("price"));
    	priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    	priceCol.setOnEditCommit(
    		new EventHandler<CellEditEvent<Order, Integer>>() {
    			@Override
    			public void handle(CellEditEvent<Order, Integer> t) {
    				(t.getTableView().getItems().get(
    					t.getTablePosition().getRow())
    					).setPrice(t.getNewValue());
    				
    				/* assign this packet to any available employee
    				 * if any employee is unavailable then bring a pop-up showing all employees are busy.
    				 *  */
    				Order o=(t.getTableView().getItems().get(
        					t.getTablePosition().getRow())
        					);
    				try {
    				pStmt=primaryConn.prepareStatement("UPDATE orders SET assigned=1 WHERE orderid = ?;" );
    				pStmt.setInt(1, o.getOrderid());
    				pStmt.executeUpdate();
    				
    				pStmt=primaryConn.prepareStatement("UPDATE userhistory SET price=? WHERE orderid = ?;" );
    				pStmt.setInt(1,t.getNewValue());
    				pStmt.setInt(2, o.getOrderid());
    				pStmt.executeUpdate();
    				} catch(Exception e) {e.printStackTrace();}
    				System.out.println("id clicked; "+o.getOrderid());
    				System.out.println("new price ; "+t.getNewValue());
    			}
    		}
    	);

    	table.setItems(data);
    	table.getColumns().addAll(sourceCol,destCol,delTypeCol,detCol,typeCountCol,priceCol);

    	/*final TextField source_tf = new TextField();
    	source_tf.setPromptText("Source");
    	source_tf.setMaxWidth(sourceCol.getPrefWidth());
    	final TextField dest_tf = new TextField();
    	dest_tf.setPromptText("Destination");
    	dest_tf.setMaxWidth(sourceCol.getPrefWidth());
    	final TextField delType_tf = new TextField();
    	delType_tf.setPromptText("Delivery Type");
    	delType_tf.setMaxWidth(sourceCol.getPrefWidth());
    	final TextField det_tf = new TextField();
    	det_tf.setPromptText("Details");
    	det_tf.setMaxWidth(sourceCol.getPrefWidth());
    	*/


    	/* uncomment if adding row needed
    	final Button addButton = new Button("Add");
    	addButton.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			data.add(new Person(
    					addFirstName.getText(),
    					addLastName.getText(),
    					addEmail.getText()));
    			addFirstName.clear();
    			addLastName.clear();
    			addEmail.clear();
    		}
    	});*/

    	//hb.getChildren().addAll(addFirstName, addLastName, addEmail/*, addButton*/);
    	//hb.setSpacing(3);

    	final VBox vbox = new VBox();
    	vbox.setSpacing(5);
    	vbox.setPadding(new Insets(10, 0, 0, 10));
    	vbox.getChildren().addAll(label, table, hb);


    	gPane.getChildren().addAll(vbox);
    	packetEstimation.setContent(gPane);
    }
    
    public void addEmpTabScene() {
    	GridPane gPane = sampleGridPane();
    	gPane.getStyleClass().add("grid-pane");

    	Label labelTracking = new Label("Add Employees here");


    	TableView<Employee> table = new TableView<Employee>();
    	ArrayList<Employee> list=new ArrayList<Employee>();
    	//list.
    	
    	/*query db for the list of all orders of every user*/
    	
    	list.add(new Employee("ramesh","abcdefgh","1234567890","mumbai",true,null));
    	list.add(new Employee("ramesh","abcdefgh","1234567890","mumbai",true,null));
    	list.add(new Employee("ramesh","abcdefgh","1234567890","mumbai",true,null));
    	
    	ObservableList<Employee> data =
    			FXCollections.observableArrayList(list
    			/*new Person("Jacob", "Smith", "jacob.smith@example.com"),
    			new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
    			new Person("Ethan", "Williams", "ethan.williams@example.com"),
    			new Person("Emma", "Jones", "emma.jones@example.com"),
    			new Person("Michael", "Brown", "michael.brown@example.com")*/);
    	final HBox hb = new HBox();

    	// For now display text viz. the last known address of the order



    	final Label label = new Label("Adding and Deleting Employee ");
    	label.setFont(new Font("Arial", 20));

    	table.setEditable(true);

    	TableColumn<Employee,String> sourceCol = new TableColumn<>("Employee Name");
    	sourceCol.setMinWidth(100);
    	sourceCol.setCellValueFactory(
    			new PropertyValueFactory<Employee, String>("employeename"));
    	sourceCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	sourceCol.setOnEditCommit(
    		new EventHandler<CellEditEvent<Employee, String>>() {
    			@Override
    			public void handle(CellEditEvent<Employee, String> t) {
    				String temp=t.getNewValue();
    				((Employee)t.getTableView().getItems().get(
    					t.getTablePosition().getRow())
    					).setEmployeename(temp);
    				System.out.println("employee name changed: "+temp);
    			}
    		}
    	);
    	
    	TableColumn<Employee,String> passCol = new TableColumn<>("Password");
    	passCol.setMinWidth(100);
    	passCol.setCellValueFactory(
    			new PropertyValueFactory<Employee, String>("password"));
    	passCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	passCol.setOnEditCommit(
    		new EventHandler<CellEditEvent<Employee, String>>() {
    			@Override
    			public void handle(CellEditEvent<Employee, String> t) {
    				((Employee)t.getTableView().getItems().get(
    					t.getTablePosition().getRow())
    					).setPassword(t.getNewValue());
    			}
    		}
    	);

    	TableColumn<Employee,String> destCol = new TableColumn<>("Phone");
    	destCol.setMinWidth(100);
    	destCol.setCellValueFactory(
    			new PropertyValueFactory<Employee, String>("phone"));
    	destCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	destCol.setOnEditCommit(
    		new EventHandler<CellEditEvent<Employee, String>>() {
    			@Override
    			public void handle(CellEditEvent<Employee, String> t) {
    				((Employee)t.getTableView().getItems().get(
    					t.getTablePosition().getRow())
    					).setPhone(t.getNewValue());
    			}
    		}
    	);
    	
    	TableColumn<Employee,String> addrCol = new TableColumn<>("Address");
    	addrCol.setMinWidth(100);
    	addrCol.setCellValueFactory(
    			new PropertyValueFactory<Employee, String>("address"));
    	addrCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	addrCol.setOnEditCommit(
    		new EventHandler<CellEditEvent<Employee, String>>() {
    			@Override
    			public void handle(CellEditEvent<Employee, String> t) {
    				(t.getTableView().getItems().get(
    					t.getTablePosition().getRow())
    					).setAddress(t.getNewValue());
    			}
    		}
    	);
    	
    	TableColumn<Employee,Boolean> detCol = new TableColumn<>("Available");
    	detCol.setMinWidth(100);
    	detCol.setCellValueFactory(
    			new PropertyValueFactory<Employee, Boolean>("available"));
    	detCol.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter() ));
    	detCol.setOnEditCommit(
    		new EventHandler<CellEditEvent<Employee, Boolean>>() {
    			@Override
    			public void handle(CellEditEvent<Employee, Boolean> t) {
    				(t.getTableView().getItems().get(
    					t.getTablePosition().getRow())
    					).setAvailable(t.getNewValue());
    			}
    		}
    	);

    	table.setItems(data);
    	table.getColumns().addAll(sourceCol,passCol,destCol,addrCol,detCol);

    	final TextField e_name_tf = new TextField();
    	e_name_tf.setPromptText("Name");
    	e_name_tf.setMaxWidth(sourceCol.getPrefWidth());
    	final TextField pass_tf = new TextField();
    	pass_tf.setPromptText("password");
    	pass_tf.setMaxWidth(sourceCol.getPrefWidth());
    	final TextField phone_tf = new TextField();
    	phone_tf.setPromptText("Phone");
    	phone_tf.setMaxWidth(sourceCol.getPrefWidth());
    	final TextField addr_tf = new TextField();
    	addr_tf.setPromptText("Address");
    	addr_tf.setMaxWidth(sourceCol.getPrefWidth());
    	


    	/* uncomment if adding row needed */
    	final Button addButton = new Button("Add");
    	addButton.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if( (e_name_tf.getText().toString().length()==0) || (pass_tf.getText().toString().length()==0) ||
    				(phone_tf.getText().toString().length()==0) || (addr_tf.getText().toString().length()==0) ) {
	    				final Stage dialog = new Stage();
	                    dialog.initModality(Modality.APPLICATION_MODAL);
	                    dialog.initOwner(primaryStage);
	                    VBox dialogVbox = new VBox(20);
	                    dialogVbox.getChildren().add(new Text("ERROR:\n Enter all the fields for Employee"));
	                    Scene dialogScene = new Scene(dialogVbox, 300, 200);
	                    dialog.setScene(dialogScene);
	                    dialog.show();
	                    return;    				
    			}
    			data.add(new Employee(
    					e_name_tf.getText().toString(),
    					pass_tf.getText().toString(),
    					phone_tf.getText().toString(),
    					addr_tf.getText().toString(),
    					true,null
    					));
    			e_name_tf.clear();
    			pass_tf.clear();
    			phone_tf.clear();
    			addr_tf.clear();
    		}
    	});

    	hb.getChildren().addAll(e_name_tf,pass_tf,phone_tf,addr_tf,addButton);
    	hb.setSpacing(3);

    	final VBox vbox = new VBox();
    	vbox.setSpacing(5);
    	vbox.setPadding(new Insets(10, 0, 0, 10));
    	vbox.getChildren().addAll(label, table, hb);


    	gPane.getChildren().addAll(vbox);
    	addEmp.setContent(gPane);
    }
    
    public BorderPane makeScene(Stage newStage,Connection mConn)
    {
    	System.out.println("inside makeScene of homepageadmin");
    	try {			
			System.out.println("making conn with server");
			
			primaryConn=mConn;
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	/*
		This function creates the whole scene
		Also this function is called from different
		Classes to switch between scenes
	*/
	//Important assignment, this 
	//assignment preserves only one window
        primaryStage = newStage;
        BorderPane borderPane = new BorderPane();
        
        newOrder = new Tab("New Order");
        //history = new Tab("History");
        //tracking = new Tab("Tracking");
        clients = new Tab("Clients");
        packetEstimation=new Tab("Packet Estimation");
        addEmp=new Tab("Add Employee");
        
        newOrderTabScene();
        //historyTabScene();
        //trackingTabScene();
        clientsTabScene();
        packetEstimationTabScene();
        //trackingTabScene();
        addEmpTabScene();
        
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        
        tabPane.getTabs().addAll(newOrder, /*history, tracking,*/ clients, packetEstimation,addEmp);
        
        tabPane.getSelectionModel().selectedItemProperty().addListener( (ov, oldTab, newTab) -> {
                System.out.println(oldTab.getText() + " changed to " + newTab.getText());
		//Switch tab when the corresponding tab is pressed.
                tabPane.getSelectionModel().select(newTab);
        });        
        
        
        JFXButton buttonSettings = new JFXButton("Settings ");
        
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(2));

        hBox.getChildren().addAll(buttonSettings);
       
        // Anchor pane is used to keep tabpane and settings button together
	// HBox cannot be used because button will occupy all space 
	// below it and it will remain empty
        AnchorPane anchorPane = new AnchorPane();
        AnchorPane.setTopAnchor(buttonSettings, 6.0);
        AnchorPane.setRightAnchor(buttonSettings, 5.0);
        AnchorPane.setTopAnchor(tabPane, 1.0);
        AnchorPane.setRightAnchor(tabPane, 1.0);
        AnchorPane.setLeftAnchor(tabPane, 1.0);
        AnchorPane.setBottomAnchor(tabPane, 1.0);

        anchorPane.getChildren().addAll(tabPane, buttonSettings);
        
	// Add the settings drawer
        JFXDrawer rightDrawerSettings = new JFXDrawer();
        JFXDrawer rightDrawerNotifications = new JFXDrawer();
        StackPane rightDrawerPaneSettings = new StackPane();
        rightDrawerPaneSettings.getStyleClass().add("blue-400");
        
        rightDrawerSettings.setDirection(DrawerDirection.RIGHT);
        rightDrawerSettings.setDefaultDrawerSize(200);
        rightDrawerSettings.setSidePane(rightDrawerPaneSettings);
        rightDrawerSettings.setOverLayVisible(false);
        rightDrawerSettings.setResizableOnDrag(true);
        
	//Add the notifications drawer
        StackPane rightDrawerPaneNotifications = new StackPane();
        rightDrawerNotifications.setDirection(DrawerDirection.RIGHT);
        rightDrawerNotifications.setDefaultDrawerSize(200);
        rightDrawerNotifications.setSidePane(rightDrawerPaneNotifications);
        rightDrawerNotifications.setOverLayVisible(false);
        rightDrawerNotifications.setResizableOnDrag(true);

        
        JFXDrawersStack drawersStack = new JFXDrawersStack();
                
        drawersStack.setContent(anchorPane);
        GridPane gridSettings = getGridSettings(drawersStack, rightDrawerSettings, rightDrawerNotifications);
        GridPane gridNotifications = getGridNotifications(drawersStack, rightDrawerNotifications);
        rightDrawerPaneSettings.getChildren().add(gridSettings);
        rightDrawerPaneNotifications.getChildren().add(gridNotifications);
            
        buttonSettings.setOnAction(e -> {
            drawersStack.toggle(rightDrawerSettings);
        });

        borderPane.setCenter(drawersStack);
        System.out.println("end of makeScene of homepageadmin");
        return borderPane;
    }
    
    @Override
    public void start(Stage stage) {
    	/*
  		The stage argument is the one that
		creates the window and should be passed
		along different scenes using makeScene's argument
	*/
    	
        primaryStage = stage;
        borderPane = makeScene(primaryStage,primaryConn);
        mapStagePane.put(primaryStage, borderPane);
        Scene scene = new Scene(borderPane, 500, 500);
        scene.getStylesheets().add(HomePageAdmin.class.getResource("HomePage.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
           launch(args);
    }
}
