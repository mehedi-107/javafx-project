package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientRegistrationController {
    SocketWrapper socketWrapper;
    public Stage stage;
    @FXML
    private TextField restaurantIdField;
    @FXML
    private TextField restaurantNameField;
    @FXML
    private TextField restaurantScoreField;
    @FXML
    private TextField restaurantPriceField;
    @FXML
    private TextField restaurantZipCodeField;
    @FXML
    private TextField category1Field;
    @FXML
    private TextField category2Field;
    @FXML
    private TextField category3Field;
    @FXML
    private PasswordField password;
    public void init(Stage stage) throws IOException {
        this.stage = stage;
    }
   @FXML
    public void register() throws IOException, ClassNotFoundException {
        socketWrapper = new SocketWrapper("127.0.0.1", 33333);
        socketWrapper.write("clientRegistrationSocket");
        String restaurantId = restaurantIdField.getText();
        socketWrapper.write("isRestaurantIdValid,"+restaurantId);
        Object o = socketWrapper.read();
        if((Boolean)o == false){
            alert("This Id is already taken. Try another...");
        }
        else{
        String restaurantName = restaurantNameField.getText();
        socketWrapper.write("isRestaurantNameValid,"+restaurantName);
        Object o2 = socketWrapper.read();
        if(!(Boolean)o2)
            {
                alert("Restaurant Name already exists. Try another name unique name.");
                
            }
            else{
        String restaurantScore = restaurantScoreField.getText();
        System.out.println(restaurantScore );
        String restaurantPrice = restaurantPriceField.getText();
        System.out.println(restaurantPrice);
        String restaurantZipCode = restaurantZipCodeField.getText();
        System.out.println(restaurantZipCode);
        String category1 = category1Field.getText();
        System.out.println(category1);
        String category2 = category2Field.getText();
        System.out.println(category2);
        String category3 = category3Field.getText();
        System.out.println(category3);
        String pass = password.getText();
        System.out.println(pass);
        List<String> categories = new ArrayList<>();
        if(category1.length()>0)
            categories.add(category1);
        if(category2.length()>0)
            categories.add(category2);
        if(category3.length()>0)
            categories.add(category3);
        if(categories.size()==0)
            {
                alert("Please enter at least one category");
                return;
            }
        String []categoriesArray = new String[3];
        if(categories.size()==1)
            {
                categoriesArray[0] = categories.get(0);
                categoriesArray[1] = "";
                categoriesArray[2] = "";
            }
        if(categories.size()==2)
            {
                categoriesArray[0] = categories.get(0);
                categoriesArray[1] = categories.get(1);
                categoriesArray[2] = "";
            }
        if(categories.size()==3)
            {
                categoriesArray[0] = categories.get(0);
                categoriesArray[1] = categories.get(1);
                categoriesArray[2] = categories.get(2);
            }
        Restaurant restaurant = new Restaurant(restaurantId,restaurantName,restaurantScore,restaurantPrice,restaurantZipCode,categoriesArray);
        socketWrapper.write(restaurant);
        socketWrapper.write("RecordClientCredentials,"+restaurantId+","+pass);
        socketWrapper.closeConnection();
        stage.close();
        alert("Registration successful. Please login to continue.");
        }
    }
    }
    @FXML
    public void cancel() throws IOException {
        
        stage.close();
    }

    void alert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
