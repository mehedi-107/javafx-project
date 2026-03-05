package com.example.demo;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class UserHomeController {
    
    private HelloApplication main;
    String userName;
    @FXML
    private ComboBox dropDownChoice;
    @FXML
    private ComboBox restaurantDropDownChoice;
    @FXML
    private TextField firstInput;
    @FXML
    private TextField secondInput;
    @FXML
    private TextField thirdInput;
    @FXML
    private Button conformOdr;
    @FXML
    public TableView<FoodListForSend> cartTable = new TableView<FoodListForSend>();
    @FXML
    TableColumn<FoodListForSend,String> a;
    @FXML
    TableColumn<FoodListForSend,String> cartFoodNameColumn;
    @FXML
    TableColumn<FoodListForSend,String> cartFoodPriceColumn;
    @FXML
    TableColumn<FoodListForSend,String> quantity;
    @FXML
    TableColumn<FoodListForSend,Void> cartActionColumn;
    @FXML
    TableView<Food> table = new TableView<Food>();
    @FXML
    TableColumn<Food,String> foodNameColumn;
    @FXML
    TableColumn<Food,String> foodPriceColumn;
    @FXML
    TableColumn<Food,Void> actionColumn;
    @FXML
    TableColumn<Food,String>restaurantName;
    @FXML
    TableColumn<Food,String>category;
    @FXML
    private Button clearCrt;
    @FXML
    private Button displayCrt;
    @FXML
    private Button search;
    // @FXML
    // private TextField welcomeText;
    String restaurantNameOfSelectedFood;
    int flag=0;
    String firstInputText;
    String secondInputText;
    String thirdInputText;
    List<FoodListForSend> foodAtCart=new ArrayList<FoodListForSend>();
    List<Food> foodFromSearch= new ArrayList<Food>();
    List<Integer> foodCount=new ArrayList<Integer>();
    SocketWrapper socketWrapper;
    private String chosenOption;
    public void init(HelloApplication main,String userName) throws IOException, ClassNotFoundException{
        socketWrapper = new SocketWrapper("127.0.0.1", 33333);
        socketWrapper.write(userName);
        this.main=main;
        this.userName=userName;
         ObservableList<String> options = FXCollections.observableArrayList("Search A Restaurant By Name","Search By Food Name","Search By Food Name in a Restaurant","Search By Category","Search By Category in a Restaurant","Search By Price","Search By Price in a Restaurant","Display costliest food in a restaurant","List of Restaurants and Total Food Item on the Menu","None");
         dropDownChoice.setItems(options);
         ObservableList<String>option = FXCollections.observableArrayList("Search By Name","Search  By Category","Search By ZipCode","Search By price","Search By Score");
            restaurantDropDownChoice.setItems(option);
        //welcomeText.setText("Welcome "+userName+"! We are glad to have you again");
        firstInput.setVisible(false);
        secondInput.setVisible(false);
        thirdInput.setVisible(false);
        table.setVisible(false);
        cartTable.setVisible(false);
        conformOdr.setVisible(false);
        clearCrt.setVisible(false);
        displayCrt.setVisible(false);
        search.setVisible(false);
        realTimeAlertShow();
        PresentFoodImage presentFoodImage = new PresentFoodImage("displayFoodImage");
        
    }
    boolean breakthread=false;
    @FXML
    public void logout() throws IOException{
        socketWrapper.write("deleteU,"+userName);
        breakthread=true;
        socketWrapper.closeConnection();
        main.showLoginPage();
    }
    @FXML
    public void searchBySelected() throws IOException, ClassNotFoundException{
        clearCrt.setVisible(true);
        displayCrt.setVisible(true);
        conformOdr.setVisible(true);
        chosenOption=dropDownChoice.getValue().toString();
        if(chosenOption.equals("Search A Restaurant By Name"))
        {
            displayCrt.setText("Display Cart");
            firstInput.visibleProperty().set(false);
            secondInput.visibleProperty().set(false);
            thirdInput.visibleProperty().set(false);
            firstInput.clear();
            secondInput.clear();
            thirdInput.clear();
            table.setVisible(false);
            cartTable.setVisible(false);
            firstInput.visibleProperty().set(true);
            search.setVisible(true);
            firstInput.setPromptText("Enter the name of the restaurant");
        }
        else if(chosenOption.equals("Search By Food Name")){
            displayCrt.setText("Display Cart");
            firstInput.visibleProperty().set(false);
            secondInput.visibleProperty().set(false);
            thirdInput.visibleProperty().set(false);
            firstInput.clear();
            secondInput.clear();
            thirdInput.clear();
            table.setVisible(false);
            cartTable.setVisible(false);
            firstInput.visibleProperty().set(true);
            secondInput.visibleProperty().set(false);
            firstInput.setPromptText("Enter the name of the food item");
            search.setVisible(true);
        }
        else if(chosenOption.equals("Search By Food Name in a Restaurant")){
            displayCrt.setText("Display Cart");
            firstInput.visibleProperty().set(false);
            secondInput.visibleProperty().set(false);
            thirdInput.visibleProperty().set(false);
            firstInput.clear();
            secondInput.clear();
            thirdInput.clear();
            table.setVisible(false);
            cartTable.setVisible(false);
            firstInput.visibleProperty().set(true);
            secondInput.visibleProperty().set(true);
            firstInput.setPromptText("Enter the name of restaurant");
            secondInput.setPromptText("Enter the name of the food item");
            search.setVisible(true);
        }
        else if(chosenOption.equals("Search By Category")){
            displayCrt.setText("Display Cart");
            firstInput.visibleProperty().set(false);
            secondInput.visibleProperty().set(false);
            thirdInput.visibleProperty().set(false);
            firstInput.clear();
            secondInput.clear();
            thirdInput.clear();
            table.setVisible(false);
            cartTable.setVisible(false);
            firstInput.visibleProperty().set(true); 
            secondInput.visibleProperty().set(false);
            firstInput.setPromptText("Enter the category of the food item");
            search.setVisible(true);
        }
        else if(chosenOption.equals("Search By Category in a Restaurant")){
            displayCrt.setText("Display Cart");
            firstInput.visibleProperty().set(false);
            secondInput.visibleProperty().set(false);
            thirdInput.visibleProperty().set(false);
            firstInput.clear();
            secondInput.clear();
            thirdInput.clear();
            table.setVisible(false);
            cartTable.setVisible(false);
            firstInput.visibleProperty().set(true);
            secondInput.visibleProperty().set(true);
            firstInput.setPromptText("Enter the name of the restaurant");
            secondInput.setPromptText("Enter the category of the food item");
            search.setVisible(true);
        }
        else if(chosenOption.equals("Search By Price")){
            displayCrt.setText("Display Cart");
            firstInput.visibleProperty().set(false);
            secondInput.visibleProperty().set(false);
            thirdInput.visibleProperty().set(false);
            firstInput.clear();
            secondInput.clear();
            thirdInput.clear();
            table.setVisible(false);
            cartTable.setVisible(false);
            firstInput.visibleProperty().set(true);
            secondInput.visibleProperty().set(true);
            firstInput.setPromptText("Enter the minimum price of the food item");
            secondInput.setPromptText("Enter the maximum price of the food item");
            search.setVisible(true);
        }
        else if(chosenOption.equals("Search By Price in a Restaurant")){
            displayCrt.setText("Display Cart");
            firstInput.visibleProperty().set(false);
            secondInput.visibleProperty().set(false);
            thirdInput.visibleProperty().set(false);
            firstInput.clear();
            secondInput.clear();
            thirdInput.clear();
            table.setVisible(false);
            cartTable.setVisible(false);
            firstInput.visibleProperty().set(true);
            secondInput.visibleProperty().set(true);
            thirdInput.visibleProperty().set(true);
            firstInput.setPromptText("Enter the name of the restaurant");
            secondInput.setPromptText("Enter the minimum price of a food item");
            thirdInput.setPromptText("Enter the maximum price of a food item");
            search.setVisible(true);
            
        }
        else if(chosenOption.equals("Display costliest food in a restaurant")){
            displayCrt.setText("Display Cart");
            firstInput.visibleProperty().set(false);
            secondInput.visibleProperty().set(false);
            thirdInput.visibleProperty().set(false);
            firstInput.clear();
            secondInput.clear();
            thirdInput.clear();
            table.setVisible(false);
            cartTable.setVisible(false);
            firstInput.visibleProperty().set(true);
            firstInput.setPromptText("Enter the name of the restaurant");
            search.setVisible(true);
        }
        else if(chosenOption.equals("List of Restaurants and Total Food Item on the Menu")){
            firstInput.visibleProperty().set(false);
            secondInput.visibleProperty().set(false);
            thirdInput.visibleProperty().set(false);
            firstInput.clear();
            secondInput.clear();
            thirdInput.clear();
            table.setVisible(false);
            cartTable.setVisible(false);
            search.setVisible(false);
            conformOdr.setVisible(false);
            clearCrt.setVisible(false);
            displayCrt.setVisible(false);
        socketWrapper.write("ListOfRestaurantsAndTotalFoodItemOnTheMenu");
        Object o =socketWrapper.read();
        List<String> tempData=(List<String>)o;
        Object temp=socketWrapper.read();
        List<String> tempFood=(List<String>)temp;
        class datashow{
            String restaurantName;
            String totalFoodItem;
            datashow(String restaurantName,String totalFoodItem){
                this.restaurantName=restaurantName;
                this.totalFoodItem=totalFoodItem;
            }
            public String getRestaurantName(){
                return restaurantName;
            }
            public String getTotalFoodItem(){
                return totalFoodItem;
            }
        }
        List<datashow> data=new ArrayList<datashow>();
        for(int i=0;i<tempData.size();i++){
            data.add(new datashow(tempData.get(i),tempFood.get(i)));
            //System.out.println(tempData.get(i)+" "+tempFood.get(i));
        }
        ObservableList<datashow> products = FXCollections.observableArrayList(data);
        TableView table = new TableView();
        TableColumn<datashow,String> restaurantName = new TableColumn<>("Restaurant Name");
        restaurantName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRestaurantName()));
        TableColumn<datashow,String> totalFoodItem = new TableColumn<>("Total Food Item");
        totalFoodItem.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTotalFoodItem()));
        table.getColumns().add(restaurantName);
        table.getColumns().add(totalFoodItem);
        table.setItems(products);
        Stage stage = new Stage();
        Scene scene = new Scene(table,250, 400);
        stage.setTitle("List of Restaurants and Total Food Item on the Menu");
        stage.setScene(scene);
        stage.show();
    
}

        else if(chosenOption.equals("None")){
            firstInput.visibleProperty().set(false);
            secondInput.visibleProperty().set(false);
            thirdInput.visibleProperty().set(false);
            firstInput.clear();
            secondInput.clear();
            thirdInput.clear();
            table.setVisible(false);
            cartTable.setVisible(false);
            search.setVisible(false);
            conformOdr.setVisible(false);
            clearCrt.setVisible(false);
            displayCrt.setVisible(false);
        }
    }

    Map<Food,Boolean> foodAtCartMap=new HashMap<Food,Boolean>();
    @FXML
    public void searchConform() throws IOException, ClassNotFoundException{
        table.setVisible(true);
         firstInputText=firstInput.getText();
         secondInputText=secondInput.getText();
            thirdInputText=thirdInput.getText();
        if(chosenOption.equals("Search A Restaurant By Name"))
        socketWrapper.write("SearchRestaurantByName,"+firstInputText);
        else if(chosenOption.equals("Search By Food Name"))
        socketWrapper.write("SearchFood,"+firstInputText);
        else if(chosenOption.equals("Search By Food Name in a Restaurant"))
        socketWrapper.write("SearchFoodInAGivenRestaurant,"+firstInputText+","+secondInputText);
        else if(chosenOption.equals("Search By Category"))
        socketWrapper.write("SearchByCategory,"+firstInputText);
        else if(chosenOption.equals("Search By Category in a Restaurant"))
        socketWrapper.write("SearchByCategoryInARestaurant,"+firstInputText+","+secondInputText);
        else if(chosenOption.equals("Search By Price"))
        socketWrapper.write("SearchByPrice,"+firstInputText+","+secondInputText);
        else if(chosenOption.equals("Search By Price in a Restaurant"))
        socketWrapper.write("SearchByPriceInARestaurant,"+firstInputText+","+secondInputText+","+thirdInputText);
        else if(chosenOption.equals("Display costliest food in a restaurant")){
            socketWrapper.write("DisplayCostliestFoodInARestaurant,"+firstInputText);
        }
        System.out.println(firstInputText+" "+secondInputText);
        Object temp=socketWrapper.read();
        
        foodFromSearch=(List<Food>)temp;
        for(int i=0;i<foodFromSearch.size();i++){
            //System.out.println(foodFromSearch.get(i).getName());
        }
        foodListShow(); 
    }

    Map<String,List<FoodListForSend>> restaurantFoodMap=new HashMap<String,List<FoodListForSend>>();
    void foodListShow(){
        if(foodFromSearch!=null){
        ObservableList<Food> products = FXCollections.observableArrayList(foodFromSearch);
        restaurantName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRestaurantName()));
        category.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        foodNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        foodPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrice()));
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button addToCartButton = new Button("Add to Cart");

            {
                addToCartButton.setOnAction(event ->{
                    Food food = getTableView().getItems().get(getIndex());
                    if(foodAtCartMap.containsKey(food)){
                        //System.out.println("Already added");
                        int x=0;
                        for(int i=0;i<foodAtCart.size();i++){
                            if(foodAtCart.get(i).food.getName().equals(food.getName()) && foodAtCart.get(i).food.getRestaurantName().equals(food.getRestaurantName())){
                                foodAtCart.get(i).count++;
                                x=foodAtCart.get(i).count;
                                break;
                            }
                        }
                        for(Map.Entry<String,List<FoodListForSend>> entry:restaurantFoodMap.entrySet()){
                            if(entry.getKey().equals(food.getRestaurantId())){
                                List<FoodListForSend> temp=entry.getValue();
                                for(int i=0;i<temp.size();i++){
                                    if(temp.get(i).food.getName().equals(food.getName())){
                                        temp.get(i).count=x;
                                        break;
                                    }
                                }
                                restaurantFoodMap.put(food.getRestaurantId(),temp);
                                break;
                            }
                        }
                    }
                    else{
                        foodAtCartMap.put(food,true);
                        FoodListForSend temp = new FoodListForSend(food,1);
                        foodAtCart.add(temp);
                        if(restaurantFoodMap.containsKey(food.getRestaurantId())){
                            List<FoodListForSend> temp1=restaurantFoodMap.get(food.getRestaurantId());
                            temp1.add(temp);
                            restaurantFoodMap.put(food.getRestaurantId(),temp1);
                        }
                        else{
                            List<FoodListForSend> temp1=new ArrayList<FoodListForSend>();
                            temp1.add(temp);
                            restaurantFoodMap.put(food.getRestaurantId(),temp1);
                        }
                    }

                    // for(Map.Entry<Food,Boolean> entry:foodAtCartMap.entrySet()){
                    //     //System.out.println(entry.getKey().getName()+" "+entry.getValue());
                    // }
                    // for(int i=0;i<foodAtCart.size();i++){
                    //     //System.out.println(foodAtCart.get(i).food.getName()+" "+foodAtCart.get(i).count);
                    // }
                    cartShow();
                    
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(addToCartButton);
                }
            }
        });

        table.setItems(products);
    }
    }


    void cartShow(){
        ObservableList<FoodListForSend> products = FXCollections.observableArrayList(foodAtCart);
        a.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().food.getRestaurantName()));
        cartFoodNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().food.getName()));
        cartFoodPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().food.getPrice()));
        quantity.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCount())));
        cartActionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button removeFromCartButton = new Button("Remove from Cart");

            {
                removeFromCartButton.setOnAction(event -> {
                     FoodListForSend temp = getTableView().getItems().get(getIndex());
                    removeFromCart(temp);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(removeFromCartButton);
                }
            }
        });
        cartTable.setItems(products);
    }

    


    // private void addToCart(Food food) {
    //     if(restaurantFoodMap.containsKey(food.getRestaurantId())){
    //         List<Food> temp=restaurantFoodMap.get(food.getRestaurantId());
    //         temp.add(food);
    //         restaurantFoodMap.put(food.getRestaurantId(),temp);
    //     }
    //     else{
    //         List<Food> temp=new ArrayList<Food>();
    //         temp.add(food);
    //         restaurantFoodMap.put(food.getRestaurantId(),temp);
    //     }
    //     // if(flag==0 || restaurantNameOfSelectedFood.equals(food.getRestaurantName())){
    //     //     flag=1;
    //     //     restaurantNameOfSelectedFood=food.getRestaurantName();
    //     //     FoodListForSend temp = new FoodListForSend(food,1);
    //     //     foodAtCart.add(temp);
    //     // }
    //     // else{
    //     //     alert("You can't add food from different restaurant at a time");
    //     //     return;
    //     // }
        
    //     //System.out.println("Added to Cart: " + food.getName() + " - $" + food.getPrice());
    // }

    @FXML
    public void displayCart(){
        if(cartTable.isVisible()==false){
        cartTable.setVisible(true);
        displayCrt.setText("Hide Cart");
    }
    else{
        cartTable.setVisible(false);
        displayCrt.setText("Display Cart");
    }
        conformOdr.setVisible(true);
        
    }
    private void removeFromCart(FoodListForSend food) {
        if(food.count>1){
            food.count--;
        }
        else
        {
            List<FoodListForSend> temp=new ArrayList<FoodListForSend>();
            for(int i=0;i<foodAtCart.size();i++){
                if(foodAtCart.get(i).food.getName().equals(food.food.getName())){
                    continue;
                }
                else{
                    temp.add(foodAtCart.get(i));
                }
            }
            foodAtCart=temp;
            foodAtCartMap.remove(food.food);
        }
        if(foodAtCart.size()==0){
            flag=0;
        }
        for(int i=0;i<foodAtCart.size();i++){
            //System.out.println(foodAtCart.get(i).food.getName()+" "+foodAtCart.get(i).count);
        }
        cartShow();
        foodListShow();
        //System.out.println("Removed from Cart: " + food.food.getName() + " - $" + food.food.getPrice());
    }
    @FXML
    public void clearCart(){
        foodAtCart.clear();
        ObservableList<FoodListForSend> products = FXCollections.observableArrayList(foodAtCart);
        a.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().food.getRestaurantName()));
        cartFoodNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().food.getName()));
        cartFoodPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().food.getPrice()));
        quantity.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCount())));
        cartActionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button removeFromCartButton = new Button("Remove from Cart");

            {
                removeFromCartButton.setOnAction(event -> {
                    FoodListForSend food = getTableView().getItems().get(getIndex());
                    removeFromCart(food);
                    for(int i=0;i<foodAtCart.size();i++){
                        food.count =0;
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(removeFromCartButton);
                }
            }
        });
        products.clear();
        cartTable.setItems(products);
        foodAtCartMap.clear();
        for(Map.Entry<Food,Boolean> entry:foodAtCartMap.entrySet()){
            System.out.println(entry.getKey().getName()+" "+entry.getValue());
        }
        flag=0;
    }
    @FXML
    public void conformOrder() throws IOException, ClassNotFoundException{
        double totalCost=0;
        for(int i=0;i<foodAtCart.size();i++){
            totalCost+=Double.parseDouble(foodAtCart.get(i).food.getPrice())*foodAtCart.get(i).count;
        }
        for(Map.Entry<String,List<FoodListForSend>> entry:restaurantFoodMap.entrySet()){
            String restaurantId=entry.getKey();
            socketWrapper.write("sendOrder,"+restaurantId+","+userName);
            socketWrapper.write(entry.getValue());
        }
        restaurantFoodMap.clear();
        // String restaurantId=temp.food.getRestaurantId();
        // //System.out.println("flag: "+restaurantId);
        // socketWrapper.write("sendOrder,"+restaurantId+","+userName);
        // socketWrapper.write(foodAtCart);
        // }
        alert("Order Placed!!!"+"\nTotal Cost: "+totalCost+" Dollars \nThank You for using our service");
        clearCart();
        // FoodListForSend temp = foodAtCart.get(0);
        // double totalCost=0;
        // for(int i=0;i<foodAtCart.size();i++){
        //     totalCost+=Double.parseDouble(foodAtCart.get(i).food.getPrice())*foodAtCart.get(i).count;
        // }
        // String restaurantId=temp.food.getRestaurantId();
        // //System.out.println("flag: "+restaurantId);
        // socketWrapper.write("sendOrder,"+restaurantId+","+userName);
        // socketWrapper.write(foodAtCart);
        // alert("Order Placed to "+temp.food.getRestaurantName()+"\nTotal Cost: "+totalCost+" Dollars \nThank You for using our service");
        // clearCart();
      
    }
    void alert(String message){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        alert.getDialogPane().getStylesheets().add(
    getClass().getResource("login.css").toExternalForm());
    }
    @FXML
    public void restaurantComboBoxSelected() throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(ClientHomeController.class.getResource("restaurantListShow.fxml"));
        Parent root = loader.load();
        restaurantSearchController controller = loader.getController();
        controller.init(stage,restaurantDropDownChoice.getValue().toString());
        Scene scene = new Scene(root, 1050,667);
        stage.setTitle("Search Restaurant");
        stage.setScene(scene);
        stage.show();
    }
    public class RealTimeAlertThread implements Runnable {
       Thread t;
       RealTimeAlertThread(String name) {
              t = new Thread(this, name);
           //System.out.println("New thread: " + t);
           t.start();
       }
  
       @Override
       public void run() {
           try{
            SocketWrapper socketWrapper1 = new SocketWrapper("127.0.0.1", 33333);
            socketWrapper1.write(userName+"alert");
           while(true){
               Object obj = socketWrapper1.read();
                String s = (String) obj;
                //System.out.println("Alert: "+s  );
                alertShow(s);
       }
       }catch(Exception e){
           //System.out.println("Error in ordersThread");
       }
   }

    }
    
    public void realTimeAlertShow() {
        RealTimeAlertThread thr = new RealTimeAlertThread("alertThread");
        
    }
    
    public void alertShow(String message){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                alert(message);
            }
        });
    }

    @FXML
    public ImageView imageField1;
    @FXML
    public ImageView imageField2;
    @FXML
    public ImageView imageField3;
    @FXML
    public ImageView imageField4;

    public class PresentFoodImage implements Runnable{
        Thread t;
        PresentFoodImage(String name) {
               t = new Thread(this,name);
            //System.out.println("New thread: " + t);
            t.start();
            System.out.println("Thread started"+"imag thread");
        }
        @Override
        public void run() {
            try{int i=0;
                while(true){//
                    if(breakthread==true){
                        return;
                    }

                    System.out.println("i: "+i);
                    String path1 ="D:\\1-2\\cse-108\\test\\demo1\\src\\main\\resources\\com\\example\\demo\\image\\food"+(i%11)+".jpg";
                    Image image1 = new Image(path1);
                    imageField1.setImage(image1);
                    String path2 ="D:\\1-2\\cse-108\\test\\demo1\\src\\main\\resources\\com\\example\\demo\\image\\food"+((i+1)%11)+".jpg";
                    Image image2 = new Image(path2);
                    imageField2.setImage(image2);
                    String path3 ="D:\\1-2\\cse-108\\test\\demo1\\src\\main\\resources\\com\\example\\demo\\image\\food"+((i+2)%11)+".jpg";
                    Image image3 = new Image(path3);
                    imageField3.setImage(image3);
                    String path4 ="D:\\1-2\\cse-108\\test\\demo1\\src\\main\\resources\\com\\example\\demo\\image\\food"+((i+3)%11)+".jpg";
                    Image image4 = new Image(path4);
                    imageField4.setImage(image4);
                    String path5 ="D:\\1-2\\cse-108\\test\\demo1\\src\\main\\resources\\com\\example\\demo\\image\\food"+((i+4)%11)+".jpg";
                    Image image5 = new Image(path5);
                    imageField4.setImage(image5);
                    i=(i+1)%11;
                    Thread.sleep(3000);

                }
            }
            catch(Exception e){
                //System.out.println("Error in presentFoodImage");
            }
        }

    }

}
