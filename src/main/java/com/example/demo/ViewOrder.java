package com.example.demo;

import java.io.IOException;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ViewOrder {
     public Stage stage;
     @FXML
     TableView<FoodListForSend> foodItemTable;
        @FXML
        TableColumn<FoodListForSend,String> categoryColumn;
        @FXML
        TableColumn<FoodListForSend,String>foodNameColumn;
        @FXML
        TableColumn<FoodListForSend,String> foodPriceColumn;
        @FXML
        TableColumn<FoodListForSend,String> quantityColumn;
        TableView<String> ordersTable;
        List<String> str;
        List<FoodListForSend> foodList;
        String currentOrder;
        String restaurantName;
        boolean hadAcceptOrder=false;
    public void init(Stage stage,List<FoodListForSend>list,TableView<String>ordersTable,List<String>str,String currentOrder,String restaurantName) throws IOException {
        this.stage = stage;
        this.foodList=list;
        this.ordersTable=ordersTable;
        this.str=str;
        this.currentOrder=currentOrder;
        this.restaurantName=restaurantName;
        for(FoodListForSend food:foodList){
            System.out.println(food.getName());
        }
       ObservableList<FoodListForSend> products = FXCollections.observableArrayList(foodList);
        foodNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().food.getName()));
        foodPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().food.getPrice()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCount())));
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().food.getCategory()));
        foodItemTable.setItems(products);
    }
   @FXML
    public void acceptOrder() throws IOException, ClassNotFoundException {
        hadAcceptOrder=true;
        str.remove(currentOrder);
        ordersTable.getItems().remove(currentOrder);
        SocketWrapper socketWrapper = new SocketWrapper("127.0.0.1",33333);
        socketWrapper.write("acceptOrder");
        System.out.println(currentOrder);
        String []ss = currentOrder.split("[,()]");
        for(String s:ss){
            System.out.println(s);
        }
        if(ss.length>2){
            socketWrapper.write("acceptOrder,"+ss[1]+","+ss[2]+","+restaurantName);
        }
        else{
            socketWrapper.write("acceptOrder,"+ss[1]+",Order No.1,"+restaurantName);
            System.out.println("acceptOrder,"+ss[1]+",Order No.1,"+restaurantName);
        }
        stage.close();
    }
    @FXML
    public void cancel() throws IOException {
        str.remove(currentOrder);
        ordersTable.getItems().remove(currentOrder);
        SocketWrapper socketWrapper = new SocketWrapper("127.0.0.1",33333);
        socketWrapper.write("rejectOrder");
        System.out.println(currentOrder);
        String []ss = currentOrder.split("[,()]");
        for(String s:ss){
            System.out.println(s);
        }
        if(ss.length>2){
            socketWrapper.write("rejectOrder,"+ss[1]+","+ss[2]+","+restaurantName);
        }
        else{
            socketWrapper.write("rejectOrder,"+ss[1]+",Order No.1,"+restaurantName);
            System.out.println("rejectOrder,"+ss[1]+",Order No.1,"+restaurantName);
        }
        System.out.println("Cancel");
        stage.close();
    }
    void alert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
