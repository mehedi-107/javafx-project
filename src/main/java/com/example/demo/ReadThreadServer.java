package com.example.demo;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Alert;

public class ReadThreadServer implements Runnable {
    private Thread thr;
    private SocketWrapper socketWrapper;
    public HashMap<String, SocketWrapper> clientMap;
    public RestaurantDatabase restaurantDatabase;
    public Map<String,String> clientCredentials;
    public Map<String,String> userCredentials;
    public ReadThreadServer(HashMap<String, SocketWrapper> map, SocketWrapper socketWrapper, RestaurantDatabase restaurantDatabase, Map<String,String> clientCredentials,Map<String,String> userCredentials) {
        this.restaurantDatabase = restaurantDatabase;
        this.clientCredentials = clientCredentials;
        this.userCredentials = userCredentials;
        this.clientMap = map;
        this.socketWrapper = socketWrapper;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = socketWrapper.read();
                if(o instanceof String){
                    String stringData = (String) o;
                    String []ss = stringData.split(",");
                    if(ss[0].equals("getRestaurantDetails")){
                        
                        Restaurant temp = restaurantDatabase.getRestaurantFromId(ss[1]);
                        temp.displayDetails();
                        //temp.displayDetails();
                        socketWrapper.write(temp);
                    }
                   else if(ss[0].equals("SearchRestaurantByName")){
                        socketWrapper.write(restaurantDatabase.getFoodListFromRestaurantName(ss[1]));
                    }
                    // else if(ss[0].equals("getFoodListFromName")){
                    //     restaurantDatabase.searchFoodInAGivenRestaurant(ss[1],ss[2]);
                    // }
                    else if(ss[0].equals("SearchFoodInAGivenRestaurant")){
                        System.out.println(ss[2]);
                        System.out.println(ss[1]);
                        socketWrapper.write(restaurantDatabase.searchFoodInAGivenRestaurant(ss[2],ss[1]));
                        System.out.println("SearchFoodInAGivenRestaurant");
                    }
                    else if(ss[0].equals("getRestaurantIdFromName")){
                        socketWrapper.write(restaurantDatabase.getIdFromNameSingle(ss[1]));
                    }
                    else if(ss[0].equals("SearchFood")){
                        socketWrapper.write(restaurantDatabase.searchFoodByName(ss[1]));
                    }
                    else if(ss[0].equals("SearchByCategory")){
                        socketWrapper.write(restaurantDatabase.searchFoodByCategory(ss[1]));
                    }
                    else if(ss[0].equals("SearchByCategoryInARestaurant")){
                        socketWrapper.write(restaurantDatabase.searchCategoryInAGivenRestaurant(ss[2],ss[1]));
                    }
                    else if(ss[0].equals("SearchByPrice")){
                        System.out.println(ss[1]+" "+ss[2]);
                        socketWrapper.write(restaurantDatabase.searchFoodByPriceRange(Double.parseDouble(ss[1]),Double.parseDouble(ss[2])));
                    }
                    else if(ss[0].equals("SearchByPriceInARestaurant")){
                        socketWrapper.write(restaurantDatabase.searchPriceRangeInAGivenRestaurant(Double.parseDouble(ss[2]),Double.parseDouble(ss[3]),ss[1]));
                    }
                    else if(ss[0].equals("ListOfRestaurantsAndTotalFoodItemOnTheMenu")){
                        Map<String,Integer> mp= restaurantDatabase.displayListOfRestaurantsAndTotalItem();
                        List<String> temp = new ArrayList<>();
                        for(Map.Entry<String,Integer> entry:mp.entrySet()){
                            temp.add(entry.getKey());
                            System.out.println(entry.getKey());
                        }
                        List<String> temp2 = new ArrayList<>();
                        for(Map.Entry<String,Integer> entry:mp.entrySet()){
                            temp2.add(entry.getValue().toString());
                            System.out.println(entry.getValue());
                        }
                        socketWrapper.write(temp);
                        socketWrapper.write(temp2);
                    }
                    else if(ss[0].equals("DisplayCostliestFoodInARestaurant")){
                        socketWrapper.write(restaurantDatabase.displayCostliestFoodItem(ss[1]));
                    }
                    else if(ss[0].equals("sendOrder")){
                        if(clientMap.containsKey("Client"+ss[1])){
                            System.out.println("Yes");
                        }
                        SocketWrapper to = clientMap.get("Client"+ss[1]);
                        System.out.println("Mehedi "+ss[1]);
                        Object o1 = socketWrapper.read();
                        List<FoodListForSend> tempFoods = (List<FoodListForSend>) o1;
                        for(FoodListForSend f:tempFoods){
                            f.displayDetails();
                        }
                        if(to!=null){

                            to.write("newOrderFrom,"+ss[2]);
                            to.write(o1);
                            //to.write("New Order From "+ss[2]);
                        }
                    }
                    else if(ss[0].equals("VerifyUser")){
                        System.out.println(ss[1]);
                        System.out.println(ss[2]);
                        if(userCredentials.containsKey(ss[1]) && userCredentials.get(ss[1]).equals(ss[2])){
                            System.out.println(ss[1]);
                            System.out.println(ss[2]);
                            socketWrapper.write(true);
                        }
                        else{
                            socketWrapper.write(false);
                        }
                        for(Map.Entry<String,String> entry:userCredentials.entrySet()){
                            System.out.println(entry.getKey()+" "+entry.getValue());
                        }
                    }
                    else if(ss[0].equals("VerifyClient")){
                        if(clientCredentials.containsKey(ss[1]) && clientCredentials.get(ss[1]).equals(ss[2])){
                            socketWrapper.write(true);
                        }
                        else{
                            socketWrapper.write(false);
                        }
                    }
                    else if(ss[0].equals("isRestaurantIdValid")){
                         if(clientCredentials.containsKey(ss[1]))
                        socketWrapper.write(false);
                        else
                        socketWrapper.write(true);
                    }
                    else if(ss[0].equals("isRestaurantNameValid")){
                        if(restaurantDatabase.isRestaurantNameExist(ss[1])){
                            socketWrapper.write(false);
                        }
                        else{
                            socketWrapper.write(true);
                        }
                    }
                    else if(ss[0].equals("RecordClientCredentials")){
                        clientCredentials.put(ss[1],ss[2]);
                        System.out.println(ss[1]+" "+ss[2]);
                        String path = "D:\\1-2\\cse-108\\test\\demo1\\src\\main\\java\\com\\example\\demo\\clientCredentials.txt";
                        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
                        for(Map.Entry<String,String> i:clientCredentials.entrySet()){
                            bw.write(i.getKey()+","+i.getValue()+"\n");
                        }
                        bw.close();
                    }
                    else if(ss[0].equals("newUser")){
                        userCredentials.put(ss[1],ss[3]);
                        String path = "D:\\1-2\\cse-108\\test\\demo1\\src\\main\\java\\com\\example\\demo\\userCredentials.txt";
                        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
                        for(Map.Entry<String,String> i:userCredentials.entrySet()){
                            bw.write(i.getKey()+","+i.getValue()+"\n");
                        }
                        bw.close();
                    }
                    else if(ss[0].equals("userValidityCheck")){
                        if(userCredentials.containsKey(ss[1]))
                        socketWrapper.write(false);
                        else
                        socketWrapper.write(true);
                    }
                    else if(ss[0].equals("Search By Score")){
                        System.out.println(ss[1]+" "+ss[2]);
                        socketWrapper.write(restaurantDatabase.searchRestaurantByScore(Double.parseDouble(ss[1]),Double.parseDouble(ss[2])));
                    }
                    else if(ss[0].equals("Search By price")){
                        socketWrapper.write(restaurantDatabase.searchRestaurantByPrice(ss[1]));
                    }
                    else if(ss[0].equals("Search By ZipCode")){
                        socketWrapper.write(restaurantDatabase.searchRestaurantByZipCode(ss[1]));
                    }
                    else if(ss[0].equals("Search  By Category")){
                        socketWrapper.write(restaurantDatabase.searchRestaurantByCategory(ss[1]));
                    }
                    else if(ss[0].equals("Search By Name")){
                        socketWrapper.write(restaurantDatabase.searchRestaurantByName(ss[1]));
                    }
                    else if(ss[0].equals("deleteU")){
                        clientMap.remove(ss[1]);
                        System.out.println(ss[1]+"removed");
                    }
                    else if(ss[0].equals("deleteC")){
                        clientMap.remove(ss[1]);
                        System.out.println(ss[1]+"removed");
                    }
                    else if(ss[0].equals("acceptOrder")){
                        System.out.println("Accepting order");
                        System.out.println(ss[1]);
                        System.out.println(ss[2]);
                        System.out.println(ss[3]);
                        if(clientMap.containsKey(ss[1])){
                            System.out.println("Yes");
                            SocketWrapper to = clientMap.get(ss[1]+"alert");
                            System.out.println("data send to "+ss[1]+"alert");
                            to.write("Your "+ ss[2]+" has been accepted by "+ss[3]);
                        }
                    }
                    else if(ss[0].equals("rejectOrder")){
                        System.out.println("Rejecting order");
                        System.out.println(ss[1]);
                        System.out.println(ss[2]);
                        System.out.println(ss[3]);
                        if(clientMap.containsKey(ss[1])){
                            System.out.println("Yes");
                            SocketWrapper to = clientMap.get(ss[1]+"alert");
                            System.out.println("data send to "+ss[1]+" alert");
                            to.write("Your "+ ss[2]+" has been rejected by "+ss[3]);
                        }
                    }
                }
                
                else if(o instanceof Food){
                    restaurantDatabase.addAFoodItem((Food) o);
                    restaurantDatabase.storeAllData();
                    Food tempFood = (Food) o;
                    tempFood.displayDetails();
                }
                else if(o instanceof Restaurant){
                    restaurantDatabase.addARestaurant((Restaurant) o);
                    restaurantDatabase.storeAllData();
                    Restaurant tempRestaurant = (Restaurant) o;
                    //restaurantDatabase.restaura
                    tempRestaurant.displayDetails();
                }
                

                // if (o instanceof Message) {
                //     Message obj = (Message) o;
                //     String to = obj.getTo();
                //     SocketWrapper nu = clientMap.get(to);
                //     if (nu != null) {
                //         nu.write(obj);
                //     }
                // }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                socketWrapper.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
