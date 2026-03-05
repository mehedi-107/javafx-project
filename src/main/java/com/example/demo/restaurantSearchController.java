package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class restaurantSearchController {
    public Stage stage;
    public String str;
    @FXML
    private TextField firstInput;
    @FXML
    private TextField secondInput;
    @FXML
    public ListView<String> restaurantListShow;
    public void init(Stage stage,String str) throws IOException {
        this.stage = stage;
        this.str = str;
        if(str.equals("Search By Score"))
        {
            firstInput.setPromptText("Enter Lower Bound:");
            secondInput.setPromptText("Enter Upper Bound:");
            secondInput.setVisible(true);
        }
        else
        {
            secondInput.setVisible(false);
            firstInput.setPromptText(str);
        }
        System.out.println(str);
        restaurantListShow.setVisible(false);
    }
   @FXML
    public void searchConform() throws IOException, ClassNotFoundException {
        restaurantListShow.setVisible(true);
        SocketWrapper socketWrapper = new SocketWrapper("127.0.0.1",33333);
        socketWrapper.write("userRegistrationSocket");
        String temp = firstInput.getText();
        if(str.equals("Search By Score"))
        {
            String temp2 = secondInput.getText();
            System.out.println(temp2);
            System.out.println(temp);
            socketWrapper.write(str+","+temp+","+temp2);
        }
        else
        {
            socketWrapper.write(str+","+temp);
        }
        List<Restaurant> restaurantList = (List<Restaurant>) socketWrapper.read();
        List<String> tempStrList= new ArrayList<String>();
        for(Restaurant restaurant:restaurantList){
            String []ss= restaurant.getCategories();
            String str = "Name of the Restaurant: "+restaurant.getName()+"\n"+"Id of the Restaurant: "+restaurant.getId()+"\n"+"Score of the Restaurant: "+restaurant.getScore()+"\n"+"Price of the Restaurant: "+restaurant.getPrice()+"\n"+"ZipCode of the Restaurant: "+restaurant.getZipCode()+"\n"+"Categories of the Restaurant: ";
           for(int i=0;i<ss.length;i++)
            {
                str+=ss[i];
                if(ss[i].length()!=0)
                {
                    str+=", ";
                }
            }
            tempStrList.add(str);
        }
        for(Restaurant restaurant:restaurantList){
            restaurant.displayDetails();
        }
        ObservableList<String> items = FXCollections.observableArrayList(tempStrList);
        restaurantListShow.setItems(items);

        socketWrapper.closeConnection();
        //stage.close();
        }
    
    @FXML
    public void cancel() {
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
