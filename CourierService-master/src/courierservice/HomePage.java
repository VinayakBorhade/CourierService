/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courierservice;

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
import javafx.stage.Stage;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;


/**
 *
 * @author Admin
 */
public class HomePage extends Application {
 
    // Three tabs at the top of home page
    Tab newOrder;
    Tab history;
    Tab tracking;
    
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
    
    public GridPane getOrderDetails(Order orderSelected, JFXButton showDetails)
    {
        /*
            Get the order from the Order object and 
            Display every detail
        
               ======================
               Database support here!
               ======================
        */
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
        /*
            Make grid for the history tab
        */
        GridPane gPane = sampleGridPane();
        gPane.getStyleClass().add("grid-pane");

        Label labelHistory = new Label("Check your history of couriers here!");
        
        /*
                Add random data for now
               ======================
               Database support here!
               ======================
        */
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
    }
        
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
    
    public void trackingTabScene()
    {
        GridPane gPane = sampleGridPane();
        gPane.getStyleClass().add("grid-pane");

        Label labelTracking = new Label("Track your current order here!");
        
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        
        /*
	To Do: Dynamically change this for getting current order
               ======================
               Database support here!
               ======================	
	*/
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
    
    public BorderPane makeScene(Stage newStage)
    {
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
        history = new Tab("History");
        tracking = new Tab("Tracking");
        
        newOrderTabScene();
        historyTabScene();
        trackingTabScene();
        
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        
        tabPane.getTabs().addAll(newOrder, history, tracking);
        
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
        borderPane = makeScene(primaryStage);
        mapStagePane.put(primaryStage, borderPane);
        Scene scene = new Scene(borderPane, 500, 500);
        scene.getStylesheets().add(HomePage.class.getResource("HomePage.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
           launch(args);
    }
}
