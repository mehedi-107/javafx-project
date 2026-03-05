package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ordersThread implements Runnable{
    Thread thr;
    
    SocketWrapper socketWrapper;
    TableView<String> ordersTable;
    TableColumn<String,String> orders;
    TableColumn<String,Void>detail;
    public Map<String,List<FoodListForSend>> OrdersList= new HashMap<>();
    public List<String> userNames;
    public Map<String,Integer> userToIndex = new HashMap<>();
    List<String> str = new ArrayList<>();
    String restaurantName;
    public ordersThread(SocketWrapper socketWrapper,TableView<String> ordersTable,TableColumn<String,String> orders,TableColumn<String,Void>detail,String restaurantName){
        thr = new Thread(this);
        //thr.start();
        this.socketWrapper = socketWrapper;
        this.ordersTable = ordersTable;
        this.orders = orders;
        this.detail = detail;
        this.restaurantName = restaurantName;
        thr.start();
    }
    @Override
    public void run() {
        try{
        while(true){
            
        orders.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        detail.setCellFactory(param -> new TableCell<>() {
            private final Button detailsButton = new Button("Details");
            {
                detailsButton.setOnAction(event -> {
                    String currentOrder = getTableView().getItems().get(getIndex());
                    try {
                        viewDetails(currentOrder);
                    } catch (IOException e) {
                        System.out.println("button error");
                        e.printStackTrace();
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : detailsButton);
            }
        });
        ObservableList<String> ordersList = FXCollections.observableArrayList(str);
        ordersTable.setItems(ordersList);
        Object obj = socketWrapper.read();
       
            String s = (String) obj;
            if(userToIndex.containsKey(s)){
               /* if(userToIndex.containskey)  then append value+1 to the string s and put it to userToIndex and update tha value of userIndex.get(s) by +1*/
                int index = userToIndex.get(s);
                index++;
                userToIndex.put(s,index);
                s = s+"(Order No."+Integer.toString(index)+")";
                str.add(s);

            }
            else{
                userToIndex.put(s,1);
                str.add(s);
            }
            
           System.out.println("Orders: "+s);
        

        Object o = socketWrapper.read();
        List<FoodListForSend> tempFoods = (List<FoodListForSend>) o;
        OrdersList.put(s,tempFoods);
        for(FoodListForSend f:tempFoods){
            System.out.println("Orders: "+f.food.getName());
        }
    }
    }catch(Exception e){
        System.out.println("Error in ordersThread");
    }
}
    private void viewDetails(String currentOrder) throws IOException {
         Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(ClientHomeController.class.getResource("viewOrder.fxml"));
        Parent root = loader.load();
        ViewOrder controller = loader.getController();
        controller.init(stage,OrdersList.get(currentOrder),ordersTable,str,currentOrder,restaurantName);
        Scene scene = new Scene(root, 861,708);
        stage.setTitle("Search Restaurant");
        stage.setScene(scene);
        stage.show();
        if(controller.hadAcceptOrder==true){
            OrdersList.remove(currentOrder);
            for(int i=0;i<str.size();i++){
                if(str.get(i).equals(currentOrder)){
                    System.out.println("Removing: "+str.get(i));
                    str.remove(i);
                    break;
                }
            }
            for(int i=0;i<str.size();i++){
                System.out.println(str.get(i));
            }
            String[] temp = currentOrder.split("(");
            System.out.println(temp[0]);
            String tempString = temp[0];
            int index = userToIndex.get(tempString);
            index--;
            userToIndex.put(tempString,index);
            ObservableList<String> ordersList = FXCollections.observableArrayList(str);
        ordersTable.setItems(ordersList);
        }
        System.out.println("Details of: " + currentOrder);
    }
}
