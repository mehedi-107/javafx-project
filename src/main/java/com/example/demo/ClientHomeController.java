package com.example.demo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ClientHomeController {
    @FXML
    public Label resName;
    @FXML
    public Label resId;
    @FXML
    public Label score;
    @FXML
    public Label price;
    @FXML
    public Label zipCode;
    @FXML
    public Label Categories;
    @FXML
    public ImageView restaurantLogo;
    @FXML
    public TableView foodItemTable;
    @FXML
    public TableColumn<Food,String> categoryColumn;
    @FXML
    public TableColumn<Food,String>foodNameColumn;
    @FXML
    public TableColumn<Food,String> foodPriceColumn;
    @FXML
    public TableView<String> ordersTable;
    @FXML
    public TableColumn<String,String> orders;
    @FXML
    public TableColumn<String,Void>detail;
    private HelloApplication main;
    public List<Food> foodList;
    @FXML
    private Button logoutButton;
    @FXML
    private Button addFoodButton;
    @FXML

    private Restaurant restaurant;
    SocketWrapper socketWrapper;
    public ClientHomeController(){
    try {
        socketWrapper = new SocketWrapper("127.0.0.1", 33333);
        //System.out.println("");
    } catch (IOException e) {
        System.out.println("Error in creating socketWrapper");
        e.printStackTrace();
    }
    }
    public void init(HelloApplication main, String userName) throws IOException, ClassNotFoundException  {
            this.main = main;
    
            if(Integer.parseInt(userName)<5){
            String imagePath =  "D:\\1-2\\cse-108\\test\\demo1\\src\\main\\resources\\com\\example\\demo\\image\\"+userName+".png";
        Image image = new Image(imagePath);
        restaurantLogo.setImage(image);
            }
                socketWrapper.write("Client"+userName);
                socketWrapper.write("getRestaurantDetails,"+userName);
                Object temp1 = socketWrapper.read();
                restaurant = (Restaurant) temp1;
            restaurant.displayDetails();
        resName.setText(restaurant.getName());
        resId.setText(restaurant.getId());
        score.setText(restaurant.getScore());
        price.setText(restaurant.getPrice());
        zipCode.setText(restaurant.getZipCode());
        String[] categories = restaurant.getCategories();
        String tempString = "";
        for (int i = 0; i < categories.length; i++) {//extraction  of categories
            tempString += categories[i];
            if (i != categories.length - 1) {
                tempString += ", ";
            }
        }
        Categories.setText(tempString);
        socketWrapper.write("SearchRestaurantByName,"+restaurant.getName());
        Object temp = socketWrapper.read();
         foodList = (List<Food>) temp;
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        foodNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        foodPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrice()));
        ObservableList<Food> foodItems = FXCollections.observableArrayList(foodList);
        foodItemTable.setItems(foodItems);
        ordersThread ordersThread = new ordersThread(socketWrapper,ordersTable,orders,detail,restaurant.getName());
        
    }
    public void viewDetails(String currentOrder){
        System.out.println(currentOrder);
    }

    @FXML
    public void logout() {
        try {
            socketWrapper.write("deletC,"+restaurant.getId());
            socketWrapper.closeConnection();
            main.showLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void addFoodButton() throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(ClientHomeController.class.getResource("addFood.fxml"));
        Parent root = loader.load();
        AddFoodController controller = loader.getController();
        controller.init(restaurant.getId(),restaurant.getName(),stage,foodList,foodItemTable,categoryColumn,foodNameColumn,foodPriceColumn);
        Scene scene = new Scene(root, 561,531);
        stage.setTitle("Add Food");
        stage.setScene(scene);
        stage.show();
    }
    
    
}
