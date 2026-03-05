package com.example.demo;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class userRegistraitonController {
    public Stage stage;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField fullNameField;
    public void init(Stage stage) throws IOException {
        this.stage = stage;
    }
   @FXML
    public void add() throws IOException, ClassNotFoundException {
        SocketWrapper socketWrapper = new SocketWrapper("127.0.0.1",33333);
        socketWrapper.write("userRegistrationSocket");
        String userName = userNameField.getText();
        socketWrapper.write("userValidityCheck,"+userName);
        Object o = socketWrapper.read();
        if((Boolean)o == false){
            alert("This username is already taken. Try another...");
        }
        else{
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        socketWrapper.write("newUser,"+userName+","+fullName+","+password);
        socketWrapper.closeConnection();
        stage.close();
        alert("Registration successful. You can now login.");
        }
    }
    
    @FXML
    public void cancel() {
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
