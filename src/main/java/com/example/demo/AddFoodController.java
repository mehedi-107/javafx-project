package com.example.demo;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddFoodController {
    public Stage stage;
    @FXML
    private TextField foodCategoryField;
    @FXML
    private TextField foodNameField;
    @FXML
    private TextField foodPriceField;
    public TableColumn<Food,String> categoryColumn;
    public TableColumn<Food,String>foodNameColumn;
    public TableColumn<Food,String> foodPriceColumn;
    String restaurantId;
    String restaurantName;
    List<Food> foodList;
    public TableView table;
    public void init(String id,String restaurantName, Stage stage,List<Food>foodList,TableView table,TableColumn<Food,String>categoryColumn,TableColumn<Food,String>foodNameColumn,TableColumn<Food,String>foodPriceColumn) throws IOException {
        this.restaurantId = id;
        this.restaurantName = restaurantName;
        this.stage = stage;
        this.foodList=foodList;
        this.table= table;
        this.categoryColumn=categoryColumn;
        this.foodPriceColumn=foodPriceColumn;
        this.foodNameColumn = foodNameColumn;
    }
   @FXML
    public void add() throws IOException {
        SocketWrapper socketWrapper = new SocketWrapper("127.0.0.1",33333);
        socketWrapper.write("addFoodSocket");
        String foodCategory = foodCategoryField.getText();
        String foodName = foodNameField.getText();
        String foodPrice = foodPriceField.getText();
        Food food = new Food(restaurantId,foodName,foodCategory,foodPrice);
        food.setRestaurantName(restaurantName);
        socketWrapper.write(food);
        foodList.add(food);
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        foodNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        foodPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrice()));
        ObservableList<Food> foodItems = FXCollections.observableArrayList(foodList);
        table.setItems(foodItems);
        socketWrapper.closeConnection();
        stage.close();
    }
    @FXML
    public void cancel() {
        stage.close();
    }
}
