package com.example.demo;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;


public class HelloController {
    private HelloApplication main;
    SocketWrapper socketWrapper;
    @FXML
    public Button logUser;
    @FXML
    public Button logClient;
    @FXML
    public TextField userName;
    @FXML
    public Button loginButton;
    @FXML
    public PasswordField pass;
    @FXML Button back;
    @FXML Button clientRegisterButton;
    @FXML Button userRegisterButton;
    private int flag=0;
    @FXML
    void initialize() throws IOException {
        userName.setVisible(false);
        pass.setVisible(false);
        back.setVisible(false);
        loginButton.setVisible(false);
        System.out.println("Hello");
        socketWrapper = new SocketWrapper("127.0.0.1", 33333);
        socketWrapper.write("HelloController");
    }
    @FXML
    public void logUser (){
        logClient.setVisible(false);
        userRegisterButton.setVisible(false);
        clientRegisterButton.setVisible(false);
        TranslateTransition temp=new TranslateTransition(Duration.seconds(0.5),logUser);
        temp.setFromX(0);
        temp.setFromY(0);
        temp.setToX(-615);
        temp.setToY(380);
        temp.play();
        userName.setVisible(true);
        pass.setVisible(true);
        back.setVisible(true);
        loginButton.setVisible(true);
        flag=0;
    }
    @FXML
    public void logClient(){
        logUser.setVisible(false);
        userRegisterButton.setVisible(false);
        clientRegisterButton.setVisible(false);
        TranslateTransition temp=new TranslateTransition(Duration.seconds(0.5),logClient);
        temp.setFromX(0);
        temp.setFromY(0);
        temp.setToX(-615);
        temp.setToY(330);
        temp.play();
        userName.setVisible(true);
        pass.setVisible(true);
        back.setVisible(true);
        loginButton.setVisible(true);
        flag=1;
    }
    @FXML
    public void userRegistration() throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(ClientHomeController.class.getResource("userRegistration.fxml"));
        Parent root = loader.load();
        userRegistraitonController controller = loader.getController();
        controller.init(stage);
        Scene scene = new Scene(root, 561,531);
        stage.setTitle("User Registration");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void clientRegistration() throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(ClientHomeController.class.getResource("clientRegistration.fxml"));
        Parent root = loader.load();
        ClientRegistrationController controller = loader.getController();
        controller.init(stage);
        Scene scene = new Scene(root, 561,743);
        stage.setTitle("Add Food");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void back(){
        TranslateTransition temp;
        userRegisterButton.setVisible(true);
        clientRegisterButton.setVisible(true);
        if(logUser.isVisible()==true)
        { temp=new TranslateTransition(Duration.seconds(0.5),logUser);
            logUser.setVisible(true);
            logClient.setVisible(true);
            temp.setFromX(-615);
            temp.setFromY(380);
            temp.setToX(0);
            temp.setToY(0);
            temp.play();}
        else
        {temp=new TranslateTransition(Duration.seconds(0.5),logClient); logUser.setVisible(true);logClient.setVisible(true);
            temp.setFromX(-615);
            temp.setFromY(330);
            temp.setToX(0);
            temp.setToY(0);
            temp.play();}

        userName.setVisible(false);
        pass.setVisible(false);
        back.setVisible(false);
        loginButton.setVisible(false);
    }

   @FXML
   void loginButtonPressed() throws IOException, ClassNotFoundException{
        if(flag==0) {
            String userN = userName.getText();
            System.out.println(userN);
            String getPass = pass.getText();
            System.out.println(getPass);
            socketWrapper.write("VerifyUser,"+userN+","+getPass);
            Object temp = socketWrapper.read();
            Boolean isRealUser = (Boolean) temp;
            if (isRealUser){
                try{main.showUserHomePage(userN);}catch(Exception e){e.printStackTrace();}  
            } else {
                alert("Invalid Credentials");
            }
        }
        else{

            String userN = userName.getText();
            String getPass = pass.getText();
            socketWrapper.write("VerifyClient,"+userN+","+getPass);
            Object temp = socketWrapper.read();
            Boolean isRealClient = (Boolean) temp;
            if (isRealClient) {
                try{main.showClientHomePage(userN);}catch(Exception e){e.printStackTrace();}
            } else {
                alert("Invalid Credentials");
            }
        }
   }
    void setMain(HelloApplication main){
        this.main=main;
    }
    void alert(String message){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
       // String cssPath = "D:\\1-2\\cse-108\\test\\demo11\\src\\main\\resources\\com\\example\\demo\\alertStyle.css"; // Replace with the actual path

        //alert.getDialogPane().getStylesheets().add(cssPath);    }
}
}