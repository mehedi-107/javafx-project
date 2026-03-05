package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    Stage stage;
    //Server server = new Server();
    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
        showLoginPage();
    }
    public void showLoginPage(){
        try{
            System.out.println("sdk");
        FXMLLoader loginLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        System.out.println("fxml loaded");
        Parent root = loginLoader.load();
        System.out.println("root loaded");
        HelloController controller=loginLoader.getController();
        System.out.println("controller loaded");
        controller.setMain(this);
        System.out.println("main set");
        Scene scene = new Scene(root, 1104, 892);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e->{
            try{
                
                return;
            } catch(Exception ex){
                System.out.println("Error in logout");
            }
        });
        } catch(Exception e){
            System.out.println("Error in showLoginPage");
        }
    }
    public void showUserHomePage(String userName){
        try{
            System.out.println(userName);
        FXMLLoader userLoader = new FXMLLoader(HelloApplication.class.getResource("userHome.fxml"));
        Parent root = userLoader.load();
        UserHomeController controller=userLoader.getController();
        controller.init(this,userName);
        Scene scene = new Scene(root, 1255, 970);
        stage.setTitle("Hello user!");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e->{
            try{
                controller.logout();
                return;

            } catch(Exception ex){
                System.out.println("Error in logout");
            }
        });
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error in showUserHomePage");
        }
    }

    public void showClientHomePage(String id){
        try{
            System.out.println("Mehedi "+id);
        FXMLLoader clientLoader = new FXMLLoader(HelloApplication.class.getResource("clientHome.fxml"));
        Parent root = clientLoader.load();
        ClientHomeController controller=clientLoader.getController();
        controller.init(this,id);
        Scene scene = new Scene(root, 1104, 892);
        stage.setTitle("Hello Client!");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e->{
            try{
                controller.logout();
                return;
            } catch(Exception ex){
                System.out.println("Error in logout");
            }
        });
        }
        catch(Exception e){
            System.out.println("Error in showClientHomePage");
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        launch();
    }
    
}